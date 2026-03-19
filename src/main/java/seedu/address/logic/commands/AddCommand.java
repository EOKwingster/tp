package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TeachingStaff;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_USERNAME + "USERNAME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_USERNAME + "johndoe123 "
            + PREFIX_TAG + "friends";

    public static final String MESSAGE_STAFF_USAGE = COMMAND_WORD
            + " staff: Adds a teaching staff to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_USERNAME + "USERNAME] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example (name only, position defaults to Teaching Assistant): " + COMMAND_WORD + " staff "
            + PREFIX_NAME + "Jane Smith\n"
            + "Example (full): " + COMMAND_WORD + " staff "
            + PREFIX_NAME + "Jane Smith "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "janes@example.com "
            + PREFIX_USERNAME + "janesmith "
            + PREFIX_POSITION + "Professors "
            + PREFIX_TAG + "colleagues";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_DUPLICATE_PHONE = "This phone number already exists in the address book";
    public static final String MESSAGE_DUPLICATE_EMAIL = "This email already exists in the address book";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified person (student or teaching staff).
     *
     * @param person The person to add; must not be null.
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        boolean hasDuplicatePhone = model.getAddressBook().getPersonList().stream()
                .anyMatch(p -> p.getPhone().equals(toAdd.getPhone()));
        if (hasDuplicatePhone) {
            throw new CommandException(MESSAGE_DUPLICATE_PHONE);
        }

        boolean hasDuplicateEmail = model.getAddressBook().getPersonList().stream()
                .anyMatch(p -> p.getEmail().equals(toAdd.getEmail()));
        if (hasDuplicateEmail) {
            throw new CommandException(MESSAGE_DUPLICATE_EMAIL);
        }

        boolean hasDuplicateUsername = model.getAddressBook().getPersonList().stream()
                .anyMatch(p -> p.getUsername().equals(toAdd.getUsername()));
        if (hasDuplicateUsername) {
            throw new CommandException(MESSAGE_DUPLICATE_USERNAME);
        }

        model.addPerson(toAdd);
        String successMsg = toAdd instanceof TeachingStaff
                ? "New teaching staff added: %1$s"
                : "New student added: %1$s";
        return new CommandResult(String.format(successMsg, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
