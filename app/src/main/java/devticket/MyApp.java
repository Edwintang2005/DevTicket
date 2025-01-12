package devticket;

import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.bolt.App;

public class MyApp {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
    // App expects env variables (SLACK_BOT_TOKEN, SLACK_SIGNING_SECRET)
    App app = new App();

    app.command("/hello", (req, ctx) -> {
      return ctx.ack(":wave: Hello!");
    });

    SlackAppServer server = new SlackAppServer(app);
    server.start(); // http://localhost:3000/slack/events
  }
}
