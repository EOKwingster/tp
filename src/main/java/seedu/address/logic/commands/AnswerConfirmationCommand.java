package seedu.address.logic.commands;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AnswerConfirmationCommand extends Command {

    public static final String COMMAND_WORD_YES = "Y";
    public static final String COMMAND_WORD_NO = "N";

    public static final String MESSAGE_UNKNOWN_ANSWER = """
            Unknown answer: %s.
            Expected answers are:
                %s (for yes)
                %s (for no)
            """;
    public static final String MESSAGE_COMMAND_CANCELLED =
            "Command Cancelled!";
    public static final String MESSAGE_NO_PENDING_COMMAND = "No pending command to answer for.";

    private final AnswerType answerType;
    private final Command pendingCommand;

    public AnswerConfirmationCommand(AnswerType answerType) {
        this(answerType, null);
    }

    public AnswerConfirmationCommand(AnswerType answerType, Command pendingCommand) {
        Objects.requireNonNull(answerType);
        this.answerType = answerType;
        this.pendingCommand = pendingCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (pendingCommand == null) {
            throw new CommandException(MESSAGE_NO_PENDING_COMMAND);
        }
        switch (answerType) {
            case YES -> {
                return pendingCommand.execute(model);
            }
            case NO -> {
                return new CommandResult(MESSAGE_COMMAND_CANCELLED);
            }
            default -> throw new CommandException(String.format(
                    MESSAGE_UNKNOWN_ANSWER,
                    answerType,
                    COMMAND_WORD_YES,
                    COMMAND_WORD_NO
            ));
        }
    }

    public AnswerType getAnswerType() {
        return answerType;
    }

    public enum AnswerType {
        YES,
        NO
    }
}
