package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format("New student added: %1$s", Messages.format(validPerson)),
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model,
                AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        // Different name but same phone number as existing person
        Person personWithDuplicatePhone = new PersonBuilder()
                .withName("Unique Name")
                .withPhone(personInList.getPhone().value)
                .withEmail("unique@example.com")
                .withUsername("uniqueuser")
                .build();
        assertCommandFailure(new AddCommand(personWithDuplicatePhone), model,
                AddCommand.MESSAGE_DUPLICATE_PHONE);
    }

    @Test
    public void execute_duplicateEmail_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        // Different name but same email as existing person
        Person personWithDuplicateEmail = new PersonBuilder()
                .withName("Unique Name")
                .withPhone("11119999")
                .withEmail(personInList.getEmail().value)
                .withUsername("uniqueuser")
                .build();
        assertCommandFailure(new AddCommand(personWithDuplicateEmail), model,
                AddCommand.MESSAGE_DUPLICATE_EMAIL);
    }

    @Test
    public void execute_duplicateUsername_throwsCommandException() {
        Person personInList = model.getAddressBook().getPersonList().get(0);
        // Different name but same username as existing person
        Person personWithDuplicateUsername = new PersonBuilder()
                .withName("Unique Name")
                .withPhone("11119999")
                .withEmail("unique@example.com")
                .withUsername(personInList.getUsername().value)
                .build();
        assertCommandFailure(new AddCommand(personWithDuplicateUsername), model,
                AddCommand.MESSAGE_DUPLICATE_USERNAME);
    }

}
