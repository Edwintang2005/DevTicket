# DevTicket
## Introduction
This is a Slack App designed to be run by workspace admins to provide functionality of creating tickets with a simple slash command. Designed for the Kosine project, team members are able to create tickets that notify the development team on issues that need to be worked on. This app was created as no current app on the marketplace exactly satisfy the needs of the project. 

## Use
The app at the moment consists of 5 commands:
- /welcome -> Run this as a slack admin to show your team how to use the bot and print out a welcome message announcing the app's existence
- /help -> Run this to pull up a help menu that lists out all the commands that exist
- /hello -> Run this to say hi to the bot, this command can be used to test that the backend is correctly configured and responds to commands on the workspace
- /ping -> Run this as a interactivity test, used to ensure that interactive functionality is enabled in the workspace
- /newticket -> Pulls up a new view for users to create a new ticket in whichever channel they run the command in ~ This only works in channels and group chats, not in private messages

## Installation
To install and configure the app at the moment is slightly challenging as it isn't hosted online. However, you can follow the Slack documentation to use [ngrok](https://tools.slack.dev/node-slack-sdk/tutorials/local-development/) to set up a local server capable of hosting the app. The app will also require the following environment variables:
    `SLACK_BOT_TOKEN` and `SLACK_SIGNING_SECRET`.
These variables can be set by using the following commands:

```
    // Set by running
export SLACK_BOT_TOKEN={the token}
export SLACK_SIGNING_SECRET={signing secret}
```
After this, simply configure the slack app in your workplace will all the commands and interactivity set up as listed in the ngrok tutorial above. 

Happy coding!!
