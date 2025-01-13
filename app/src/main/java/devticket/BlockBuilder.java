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
    // Block for the help display
    static List<LayoutBlock> helpBlock() {
        return asBlocks(
            header(header -> header.text(plainText(":wave: Here is a brief description of how to use the app!"))),
            section(section -> section.text(markdownText("These following commands are available:"))),
            section(section -> section.text(markdownText("- /devHelp"))),
            section(section -> section.text(markdownText("- /hello"))),
            section(section -> section.text(markdownText("- /ping"))),
            section(section -> section.text(markdownText("- /newTicket")))
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
            section(s->s.text(markdownText(String.format("*%s*", priority)))),
            section(s -> s.text(markdownText(String.format("Created by: %s", user)))),
            actions(actions -> actions
                    .elements(asElements(
                            button(b -> b.actionId("delete_message").text(plainText(pt -> pt.text("Delete This Ticket"))).value("delete"))
                    ))
            )
        );
    }
}
