package devticket;

import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.App;
import static com.slack.api.model.view.Views.*;

public class MyApp {
    static String channelId = "";

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
    // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
    // Set by running
    // export SLACK_BOT_TOKEN={the token}
    // export SLACK_SIGNING_SECRET={signing secret}
    App app = new App();

    app.command("/welcome", (req, ctx) -> {
      return ctx.ack(res-> res.responseType("in_channel").blocks(BlockBuilder.welcomeBlock()));
    });

    app.command("/ping", (req, ctx) -> {
      return ctx.ack(BlockBuilder.pingBlock());
    });

    app.blockAction("delete_message", (req, ctx) -> {
      var messageContainer = req.getPayload().getContainer();
      ctx.client().chatDelete(r -> r.channel(messageContainer.getChannelId()).ts(messageContainer.getMessageTs()));
      return ctx.ack();
    });
    
    app.blockAction("ping-again", (req, ctx) -> {
      ctx.respond(BlockBuilder.pingBlock());
      return ctx.ack();
    });

    app.viewSubmission("new_ticket_submission", (req, ctx) -> {
      // Get the state values from the modal view
      var stateValues = req.getPayload().getView().getState().getValues();
      // Extract the values for each block
      String strategyName = stateValues.get("name_block").get("name_input").getValue();
      String notionLink = stateValues.get("link_block").get("link_input").getValue();

      // Extract the selected priority option
      String priority = stateValues.get("priority_block").get("priority_selection").getSelectedOption().getText().getText();
      // Perform further processing here (e.g., create a ticket, notify a channel, etc.)
      ctx.client().chatPostMessage(r -> r
        .channel(channelId).blocks(BlockBuilder.ticketBlock(strategyName, notionLink, priority, req.getPayload().getUser().getName()))
        .text("New Ticket Available")
      );
      // Acknowledge the view submission and clear the modal
      return ctx.ack();
    });

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(res-> res.responseType("in_channel").text(":wave: Hi from devTicket"));
    });

    app.command("/help", (req, ctx) -> {
      ctx.respond(BlockBuilder.helpBlock());
      return ctx.ack();
    });

    app.command("/newticket", (req, ctx) -> {
      channelId = ctx.getChannelId();
      var modalView = view(view -> view.type("modal")
      .clearOnClose(true)
      .callbackId("new_ticket_submission") // Used to identify the modal on submission
      .title(viewTitle(vt -> vt.type("plain_text").text("Create New Ticket")))
      .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
      .close(viewClose(vc -> vc.type("plain_text").text("Cancel")))
      .blocks(BlockBuilder.ticketCreationBlock()));
      ctx.client().viewsOpen(r -> r
        .triggerId(req.getPayload().getTriggerId())
        .view(modalView)
    );
    return ctx.ack();
    });
    
    // Before running app, make sure to run ngrok http 3000 as this is the webserver we are using at the moment
    SlackAppServer server = new SlackAppServer(app);
    server.start(); // http://localhost:3000/slack/events
  }
}
