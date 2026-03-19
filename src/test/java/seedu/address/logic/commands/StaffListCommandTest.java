package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.TeachingStaff;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StaffListCommand.
 */
public class StaffListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_staffListIsNotFiltered_showsOnlyStaff() {
        long staffCount = model.getFilteredPersonList().stream()
                .filter(p -> p instanceof TeachingStaff)
                .count();
        String expectedMessage = String.format(StaffListCommand.MESSAGE_SUCCESS, staffCount);
        expectedModel.updateFilteredPersonList(person -> person instanceof TeachingStaff);
        assertCommandSuccess(new StaffListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_staffListIsFiltered_showsOnlyStaff() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        long staffCount = model.getAddressBook().getPersonList().stream()
                .filter(p -> p instanceof TeachingStaff)
                .count();
        String expectedMessage = String.format(StaffListCommand.MESSAGE_SUCCESS, staffCount);
        expectedModel.updateFilteredPersonList(person -> person instanceof TeachingStaff);
        assertCommandSuccess(new StaffListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_showsEmptyMessage() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandSuccess(new StaffListCommand(), emptyModel,
                StaffListCommand.MESSAGE_EMPTY, expectedEmptyModel);
    }

    @Test
    public void execute_addressBookWithOnlyStudents_showsEmptyMessage() {
        AddressBook studentsOnly = new AddressBook();
        for (Person p : getTypicalAddressBook().getPersonList()) {
            if (!(p instanceof TeachingStaff)) {
                studentsOnly.addPerson(p);
            }
        }
        Model studentsOnlyModel = new ModelManager(studentsOnly, new UserPrefs());
        Model expectedModel = new ModelManager(studentsOnly, new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person instanceof TeachingStaff);
        assertCommandSuccess(new StaffListCommand(), studentsOnlyModel,
                StaffListCommand.MESSAGE_EMPTY, expectedModel);
    }

    @Test
    public void execute_addressBookWithOnlyStaff_showsAllAsStaff() {
        AddressBook staffOnly = new AddressBook();
        staffOnly.addPerson(BENSON);
        Model staffOnlyModel = new ModelManager(staffOnly, new UserPrefs());
        Model expectedModel = new ModelManager(staffOnly, new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person instanceof TeachingStaff);
        String expectedMessage = String.format(StaffListCommand.MESSAGE_SUCCESS, 1);
        assertCommandSuccess(new StaffListCommand(), staffOnlyModel, expectedMessage, expectedModel);
        assertEquals(1, staffOnlyModel.getFilteredPersonList().size());
    }

    @Test
    public void execute_filteredListShowsCorrectStaffCount() {
        expectedModel.updateFilteredPersonList(person -> person instanceof TeachingStaff);
        int staffCount = (int) expectedModel.getAddressBook().getPersonList().stream()
                .filter(p -> p instanceof TeachingStaff)
                .count();
        assertCommandSuccess(new StaffListCommand(), model,
                String.format(StaffListCommand.MESSAGE_SUCCESS, staffCount), expectedModel);
        assertEquals(staffCount, model.getFilteredPersonList().size());
    }
}
