package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.TeachingStaff;
import seedu.address.model.person.TimeSlot;

/**
 * Contains integration and unit tests for TutorDashboardCommand.
 */
public class TutorDashboardCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_typicalAddressBook_showsAllStaffNoSlots() {
        // Typical address book has BENSON, DANIEL, GEORGE as TeachingStaff with no slots
        String expectedMessage = String.format(TutorDashboardCommand.MESSAGE_SUCCESS, 3,
                "1. Benson Meier: (no slots set)\n"
                + "2. Daniel Meier: (no slots set)\n"
                + "3. George Best: (no slots set)");
        assertCommandSuccess(new TutorDashboardCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_staffWithSlots_showsSlotsInSortedOrder() {
        // Add slots to Benson (index 1 in full list)
        TeachingStaff bensonBefore = (TeachingStaff) model.getFilteredPersonList().get(1);
        Set<TimeSlot> slots = new HashSet<>();
        slots.add(new TimeSlot("wed-14-16"));
        slots.add(new TimeSlot("mon-10-12"));
        TeachingStaff bensonAfter = new TeachingStaff(
                bensonBefore.getName(), bensonBefore.getPhone(), bensonBefore.getEmail(),
                bensonBefore.getUsername(), bensonBefore.getPosition(), bensonBefore.getTags(), slots);
        model.setPerson(bensonBefore, bensonAfter);
        expectedModel.setPerson(
                (TeachingStaff) expectedModel.getFilteredPersonList().get(1), bensonAfter);

        // Slots should appear sorted by day (Mon before Wed)
        String expectedMessage = String.format(TutorDashboardCommand.MESSAGE_SUCCESS, 3,
                "1. Benson Meier: Mon 10:00-12:00, Wed 14:00-16:00\n"
                + "2. Daniel Meier: (no slots set)\n"
                + "3. George Best: (no slots set)");
        assertCommandSuccess(new TutorDashboardCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyAddressBook_showsEmptyMessage() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmpty = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandSuccess(new TutorDashboardCommand(), emptyModel,
                TutorDashboardCommand.MESSAGE_EMPTY, expectedEmpty);
    }

    @Test
    public void execute_noTeachingStaff_showsEmptyMessage() {
        AddressBook studentsOnly = new AddressBook();
        for (Person p : getTypicalAddressBook().getPersonList()) {
            if (!(p instanceof TeachingStaff)) {
                studentsOnly.addPerson(p);
            }
        }
        Model studentsModel = new ModelManager(studentsOnly, new UserPrefs());
        Model expectedStudentsModel = new ModelManager(studentsOnly, new UserPrefs());
        assertCommandSuccess(new TutorDashboardCommand(), studentsModel,
                TutorDashboardCommand.MESSAGE_EMPTY, expectedStudentsModel);
    }

    @Test
    public void execute_filteredList_showsAllStaffRegardlessOfFilter() {
        // Filter to only students — dashboard should still show all teaching staff
        model.updateFilteredPersonList(p -> !(p instanceof TeachingStaff));
        // expectedModel is unfiltered, but dashboard reads from addressBook not filtered list,
        // so expected message and model state should match
        expectedModel.updateFilteredPersonList(p -> !(p instanceof TeachingStaff));
        String expectedMessage = String.format(TutorDashboardCommand.MESSAGE_SUCCESS, 3,
                "1. Benson Meier: (no slots set)\n"
                + "2. Daniel Meier: (no slots set)\n"
                + "3. George Best: (no slots set)");
        assertCommandSuccess(new TutorDashboardCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_singleStaffAddressBook_showsSingleEntry() {
        AddressBook singleStaff = new AddressBook();
        singleStaff.addPerson(BENSON);
        Model singleModel = new ModelManager(singleStaff, new UserPrefs());
        Model expectedSingle = new ModelManager(singleStaff, new UserPrefs());
        String expectedMessage = String.format(TutorDashboardCommand.MESSAGE_SUCCESS, 1,
                "1. Benson Meier: (no slots set)");
        assertCommandSuccess(new TutorDashboardCommand(), singleModel, expectedMessage, expectedSingle);
    }

    @Test
    public void execute_multipleStaffWithSlots_showsCorrectDashboard() {
        AddressBook staffBook = new AddressBook();
        TeachingStaff bensonBase = (TeachingStaff) BENSON;
        TeachingStaff danielBase = (TeachingStaff) DANIEL;
        TeachingStaff georgeBase = (TeachingStaff) GEORGE;

        Set<TimeSlot> bensonSlots = new HashSet<>();
        bensonSlots.add(new TimeSlot("fri-9-11"));
        TeachingStaff bensonWithSlot = new TeachingStaff(
                bensonBase.getName(), bensonBase.getPhone(), bensonBase.getEmail(),
                bensonBase.getUsername(), bensonBase.getPosition(), bensonBase.getTags(), bensonSlots);

        staffBook.addPerson(bensonWithSlot);
        staffBook.addPerson(danielBase);
        staffBook.addPerson(georgeBase);

        Model staffModel = new ModelManager(staffBook, new UserPrefs());
        Model expectedStaffModel = new ModelManager(staffBook, new UserPrefs());

        String expectedMessage = String.format(TutorDashboardCommand.MESSAGE_SUCCESS, 3,
                "1. Benson Meier: Fri 09:00-11:00\n"
                + "2. Daniel Meier: (no slots set)\n"
                + "3. George Best: (no slots set)");
        assertCommandSuccess(new TutorDashboardCommand(), staffModel, expectedMessage, expectedStaffModel);
    }

    @Test
    public void equals() {
        TutorDashboardCommand a = new TutorDashboardCommand();
        TutorDashboardCommand b = new TutorDashboardCommand();

        // same object
        assertTrue(a.equals(a));
        // different instance, same type
        assertTrue(a.equals(b));
        // null
        assertFalse(a.equals(null));
        // different type
        assertFalse(a.equals(5));
    }

    @Test
    public void execute_returnsCorrectTutorCount() {
        CommandResult result = new TutorDashboardCommand().execute(model);
        // Message should mention 3 tutors (BENSON, DANIEL, GEORGE)
        assertTrue(result.getFeedbackToUser().contains("3 tutor(s)"));
    }

    @Test
    public void execute_staffNames_appearsInOutput() {
        CommandResult result = new TutorDashboardCommand().execute(model);
        String feedback = result.getFeedbackToUser();
        assertTrue(feedback.contains("Benson Meier"));
        assertTrue(feedback.contains("Daniel Meier"));
        assertTrue(feedback.contains("George Best"));
    }

    @Test
    public void execute_noSlotMessage_appearsForStaffWithoutSlots() {
        CommandResult result = new TutorDashboardCommand().execute(model);
        // All 3 staff have no slots in the typical address book
        assertEquals(3, result.getFeedbackToUser().split("no slots set", -1).length - 1);
    }
}
