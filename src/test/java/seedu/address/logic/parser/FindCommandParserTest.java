package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagsContainsTagPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand.FindPersonDescriptor fd = new FindCommand.FindPersonDescriptor();
        fd.setName(Set.of("Alice", "Bob"));
        FindCommand expectedFindCommand = new FindCommand(fd);
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsWithTag_returnsFindCommand() {
        // name keywords with tag
        FindCommand.FindPersonDescriptor fd = new FindCommand.FindPersonDescriptor();
        fd.setName(Set.of("Alice", "Bob"));
        fd.setTags(Set.of(new Tag("friends")));
        FindCommand expectedFindCommand = new FindCommand(fd);
        assertParseSuccess(parser, "Alice Bob t/friends", expectedFindCommand);

        // multiple whitespaces with tags
        assertParseSuccess(parser, " \n Alice \n \t Bob \t t/friends", expectedFindCommand);
    }

    @Test
    public void parse_validArgsWithTagOnly_returnsFindCommand() {
        // tag only, no name keywords
        FindCommand.FindPersonDescriptor fd = new FindCommand.FindPersonDescriptor();
        fd.setTags(Set.of(new Tag("friends")));
        FindCommand expectedFindCommand = new FindCommand(fd);
        assertParseSuccess(parser, " t/friends", expectedFindCommand);
    }

}
