package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.TeachingStaff;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.Username;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes_containsDuplicatePrefixes() {
        String msg = Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_PHONE);
        assertTrue(msg.contains(Messages.MESSAGE_DUPLICATE_FIELDS.trim()));
        assertTrue(msg.contains("n/"));
        assertTrue(msg.contains("p/"));
    }

    @Test
    public void format_student_includesCoreFields() {
        Person person = new PersonBuilder().withName("Amy").build();
        String formatted = Messages.format(person);
        assertTrue(formatted.contains("Amy"));
        assertTrue(formatted.contains("Phone:"));
        assertTrue(formatted.contains("Email:"));
        assertTrue(formatted.contains("Username:"));
        assertTrue(formatted.contains("Tags:"));
    }

    @Test
    public void format_teachingStaffWithoutAvailability_includesPosition() {
        Person person = new PersonBuilder().withName("Bob").withPosition("Professors").build();
        TeachingStaff staff = (TeachingStaff) person;
        String formatted = Messages.format(staff);
        assertTrue(formatted.contains("Position:"));
        assertTrue(formatted.contains("Professors"));
    }

    @Test
    public void format_teachingStaffWithAvailability_includesSortedSlots() {
        Name name = new Name("Carol");
        Phone phone = new Phone("91234567");
        Email email = new Email("carol@example.com");
        Username username = new Username("carol");
        Position position = new Position("Teaching Assistant");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friends"));
        Set<TimeSlot> slots = new HashSet<>();
        slots.add(new TimeSlot("tue-10-12"));
        slots.add(new TimeSlot("mon-9-11"));
        TeachingStaff staff = new TeachingStaff(name, phone, email, username, position, tags, slots);
        String formatted = Messages.format(staff);
        assertTrue(formatted.contains("Availability:"));
        assertTrue(formatted.contains("Mon"));
        assertTrue(formatted.contains("Tue"));
    }
}
