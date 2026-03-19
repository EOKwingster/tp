package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TeachingStaff;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StudentListCommand.
 */
public class StudentListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_studentListIsNotFiltered_showsOnlyStudents() {
        long studentCount = model.getFilteredPersonList().stream()
                .filter(p -> !(p instanceof TeachingStaff))
                .count();
        String expectedMessage = String.format(StudentListCommand.MESSAGE_SUCCESS, studentCount);
        expectedModel.updateFilteredPersonList(person -> !(person instanceof TeachingStaff));
        assertCommandSuccess(new StudentListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_studentListIsFiltered_showsOnlyStudents() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        long studentCount = model.getAddressBook().getPersonList().stream()
                .filter(p -> !(p instanceof TeachingStaff))
                .count();
        String expectedMessage = String.format(StudentListCommand.MESSAGE_SUCCESS, studentCount);
        expectedModel.updateFilteredPersonList(person -> !(person instanceof TeachingStaff));
        assertCommandSuccess(new StudentListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_showsEmptyMessage() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandSuccess(new StudentListCommand(), emptyModel,
                StudentListCommand.MESSAGE_EMPTY, expectedEmptyModel);
    }

    @Test
    public void execute_addressBookWithOnlyStaff_showsEmptyMessage() {
        AddressBook staffOnlyBook = new AddressBook();
        staffOnlyBook.addPerson(BENSON);
        Model staffOnlyModel = new ModelManager(staffOnlyBook, new UserPrefs());
        Model expectedModel = new ModelManager(staffOnlyBook, new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> !(person instanceof TeachingStaff));
        assertCommandSuccess(new StudentListCommand(), staffOnlyModel,
                StudentListCommand.MESSAGE_EMPTY, expectedModel);
    }

    @Test
    public void execute_addressBookWithOnlyStudents_showsAllAsStudents() {
        AddressBook studentsOnly = new AddressBook();
        studentsOnly.addPerson(ALICE);
        Model studentsOnlyModel = new ModelManager(studentsOnly, new UserPrefs());
        Model expectedModel = new ModelManager(studentsOnly, new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> !(person instanceof TeachingStaff));
        String expectedMessage = String.format(StudentListCommand.MESSAGE_SUCCESS, 1);
        assertCommandSuccess(new StudentListCommand(), studentsOnlyModel, expectedMessage, expectedModel);
        assertEquals(1, studentsOnlyModel.getFilteredPersonList().size());
    }

    @Test
    public void execute_filteredListShowsCorrectStudentCount() {
        expectedModel.updateFilteredPersonList(person -> !(person instanceof TeachingStaff));
        int studentCount = (int) expectedModel.getAddressBook().getPersonList().stream()
                .filter(p -> !(p instanceof TeachingStaff))
                .count();
        assertCommandSuccess(new StudentListCommand(), model,
                String.format(StudentListCommand.MESSAGE_SUCCESS, studentCount), expectedModel);
        assertEquals(studentCount, model.getFilteredPersonList().size());
    }
}
