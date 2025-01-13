package devticket;

import java.util.List;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.element.PlainTextInputElement;
import com.slack.api.model.block.element.StaticSelectElement;
import com.slack.api.model.block.element.URLTextInputElement;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.asOptions;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

public class BlockBuilder {
    // Block for the ping functionality
    static List<LayoutBlock> pingBlock() {
        return asBlocks(
            section(section -> section.text(markdownText(":wave: pong"))),
            actions(actions -> actions
                    .elements(asElements(
                            button(b -> b.actionId("ping-again").text(plainText(pt -> pt.text("Ping"))).value("ping"))
                    ))
            )
          );
    }
    // Block for the welcome display
    static List<LayoutBlock> welcomeBlock() {
        return asBlocks(
          header(header -> header.text(plainText(":wave: Hello and Welcome to DevTicket!!"))),
          section(section -> section.text(markdownText("I'm a little tool to help you guys notify your dev team of any tasks that need to be done! \n\n" +
                                                        "To get started, run the command _/newticket_! This will bring up an interface you can use to create a ticket. " + 
                                                        "The ticket will be made in whatever channel you run the command in, and if you make a mistake fear not, there's a delete button so you can try again!! \n" + 
                                                        "From here, your dev team can easily keep track of issues and hopefully assign themselves to tasks in the threads.\n\n" + 
                                                        "Unfortunately, i'm only a couple days old, which means that some things might not work or there might be some things that you want me to do that I can't :sob:. " + 
                                                        "In the event that this does happen, feel free to just let Edwin know (or hopefully the rest of the dev team as well soon), and we will try make whatever it is happen asap! " +
                                                        "If you want to see what else I can do though, feel free to use _/help_ to find all my other wonderful features!" +
                                                        "\n\n\n" + 
                                                        "*HAPPY RESEARCHING AND BEST OF LUCK!!*\n" +
                                                        "> From DevTicket"))),
        section(section -> section.text(markdownText("_*At the moment, i'm only online when i'm running on a pc, so to use me feel free to ping Edwin and he'll start me up asap._")))  
        );
    }

    // Block for the help display
    static List<LayoutBlock> helpBlock() {
        return asBlocks(
            header(header -> header.text(plainText("Here is a brief description of how to use the app."))),
            section(section -> section.text(markdownText("These following commands are available:"))),
            section(section -> section.text(markdownText("• _/welcome_ -> Run this as a slack admin to show your team how to use the bot :blush:"))),
            section(section -> section.text(markdownText("• _/help_ -> Run this to pull up this menu! Congrats you've already found it :smile:"))),
            section(section -> section.text(markdownText("• _/hello_ -> Run this to say hi to the bot :wave:"))),
            section(section -> section.text(markdownText("• _/ping_ -> Fancy a game of ping pong? :table_tennis_paddle_and_ball:"))),
            section(section -> section.text(markdownText("• _/newticket_ -> Pulls up a new menu for you to create a new ticket in your current channel!!")))
          );
    }

    // Block for the ticket creation form
    static List<LayoutBlock> ticketCreationBlock() {
        return asBlocks(
            header(header -> header.text(plainText("Lets create a new ticket for the dev team :blush:!"))),
            input(input -> input
                    .blockId("name_block")
                    .label(plainText("Name of the Strategy"))
                    .element(PlainTextInputElement.builder()
                        .actionId("name_input")
                        .build())
                ),
            input(input -> input
                    .blockId("link_block")
                    .label(plainText("Notion Link:"))
                    .element(URLTextInputElement.builder()
                        .actionId("link_input")
                        .build())
                ),
            input(input -> input
                .blockId("priority_block")
                .label(plainText("Select a priority option:"))
                .element(StaticSelectElement.builder()
                    .actionId("priority_selection")
                    .options(asOptions(
                        OptionObject.builder().text(BlockCompositions.plainText("Low")).value("low").build(),
                        OptionObject.builder().text(BlockCompositions.plainText("Medium")).value("medium").build(),
                        OptionObject.builder().text(BlockCompositions.plainText("High")).value("high").build()
                    )).build())
            )
        );
    }

    // Block list for the actual ticket
    static List<LayoutBlock> ticketBlock(String name, String link, String priority, String user) {
        return asBlocks(
            header(h -> h.text(plainText(String.format("Ticket for %s!", name)))),
            section(s -> s.text(markdownText(String.format("Notion Link available here: <%s|link>", link)))),
            section(s->s.text(markdownText(String.format("Priority: *%s*", priority)))),
            section(s -> s.text(markdownText(String.format("> Created by: %s", user)))),
            actions(actions -> actions
                    .elements(asElements(
                            button(b -> b.actionId("delete_message").text(plainText(pt -> pt.text("Delete This Ticket"))).value("delete"))
                    ))
            )
        );
    }
}
