// package devticket;
// import com.slack.api.bolt.App;
// import com.slack.api.bolt.jetty.SlackAppServer;

// public class App {
//     public String getGreeting() {
//         return "Hello World!";
//     }

//     public static void main(String[] args) throws Exception {
//         App app = new App();

//         app.command("/hello", (req, ctx) -> {
//           return ctx.ack(":wave: Hello!");
//         });

//         SlackAppServer server = new SlackAppServer(app);
//         server.start(); // http://localhost:3000/slack/events
//     }
// }
