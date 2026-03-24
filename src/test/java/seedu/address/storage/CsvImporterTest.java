package seedu.address.storage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.TeachingStaff;
import seedu.address.model.person.TimeSlot;
import seedu.address.model.person.Username;
import seedu.address.model.tag.AbstractTag;
import seedu.address.model.tag.TagFactory;
import seedu.address.storage.exceptions.DeserialisePersonException;
import seedu.address.testutil.PersonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

public class CsvImporterTest {

    @TempDir
    public Path tempDir;

    @Test
    public void deserialisePerson_studentStrRepWithoutTags_returnsValidPerson() throws DeserialisePersonException {
        String studentStrRep = "Student,John Doe,91234567,johndoe,john@example.com,";
        Person student = assertDoesNotThrow(() ->
                        CsvImporter.deserialisePerson(studentStrRep),
                "Valid student csv string rep without tags should not throw DeserialisePersonException");

        Person expectedStudent = new PersonBuilder()
                .withName("John Doe")
                .withPhone("91234567")
                .withUsername("johndoe")
                .withEmail("john@example.com")
                .withTags()
                .build();
        assertEquals(expectedStudent, student);
    }

    @Test
    public void deserialisePerson_studentStrRepWithTags_returnsValidPerson() {
        String studentStrRep = "Student,Alice Smith,81234567,alicesmith,alice@example.com,cs2103;tutee";
        Person student = assertDoesNotThrow(() ->
                        CsvImporter.deserialisePerson(studentStrRep),
                "Valid student csv string rep with tags should not throw DeserialisePersonException");

        Person expectedStudent = new PersonBuilder()
                .withName("Alice Smith")
                .withPhone("81234567")
                .withEmail("alice@example.com")
                .withUsername("alicesmith")
                .withTags("cs2103", "tutee")
                .build();
        assertEquals(expectedStudent, student);
    }

    @Test
    public void deserialisePerson_teachingStaffNoAvailNoTags_returnsValidTeachingStaff() {
        String staffStrRep = "Teaching Assistant,Prof Benson,87654321,profbenson,prof@example.com,,";
        Person staff = assertDoesNotThrow(() ->
                        CsvImporter.deserialisePerson(staffStrRep),
                "Valid staff csv string rep with no avail, no tags should not throw DeserialisePersonException");
        TeachingStaff expectedStaff = (TeachingStaff) new PersonBuilder()
                .withName("Prof Benson")
                .withPhone("87654321")
                .withEmail("prof@example.com")
                .withUsername("profbenson")
                .withPosition("Teaching Assistant")
                .withTags()
                .build();
        assertEquals(expectedStaff, staff);
    }

    @Test
    public void deserialisePerson_teachingStaffNoAvailWithTags_returnsValidTeachingStaff() {
        String staffStrRep = "Teaching Assistant,Prof Benson,87654321,profbenson,prof@example.com,cs2103;tutee,";
        Person staff = assertDoesNotThrow(() ->
                        CsvImporter.deserialisePerson(staffStrRep),
                "Valid staff csv string rep with no avail, with tags should not throw DeserialisePersonException");
        TeachingStaff expectedStaff = (TeachingStaff) new PersonBuilder()
                .withName("Prof Benson")
                .withPhone("87654321")
                .withEmail("prof@example.com")
                .withUsername("profbenson")
                .withPosition("Teaching Assistant")
                .withTags("cs2103", "tutee")
                .build();
        assertEquals(expectedStaff, staff);
    }

    @Test
    public void deserialisePerson_teachingStaffWithAvailNoTags_returnsValidTeachingStaff() {
        TimeSlot slot1 = new TimeSlot("mon-10-12");
        TimeSlot slot2 = new TimeSlot("wed-14-16");
        TeachingStaff expectedStaff = new TeachingStaff(
                new Name("Prof Alice"),
                new Phone("91111111"),
                new Email("profalice@example.com"),
                new Username("profalice"),
                new Position("Professors"),
                Set.of(),
                Set.of(slot1, slot2));
        String staffStrRep = "Professors,Prof Alice,91111111,profalice,profalice@example.com,,mon-10-12;wed-14-16";

        Person staff = assertDoesNotThrow(() ->
                        CsvImporter.deserialisePerson(staffStrRep),
                "Valid staff csv string rep with avail, no tags should not throw DeserialisePersonException");
        assertEquals(expectedStaff, staff);
    }

    @Test
    public void deserialisePerson_teachingStaffWithAvailWithTags_returnsValidTeachingStaff() {
        TimeSlot slot1 = new TimeSlot("mon-10-12");
        TimeSlot slot2 = new TimeSlot("wed-14-16");
        TeachingStaff expectedStaff = new TeachingStaff(
                new Name("Prof Alice"),
                new Phone("91111111"),
                new Email("profalice@example.com"),
                new Username("profalice"),
                new Position("Professors"),
                Set.of(TagFactory.create("lecturer")),
                Set.of(slot1, slot2));
        String staffStrRep = "Professors,Prof Alice,91111111,profalice,profalice@example.com,lecturer,mon-10-12;wed-14-16";

        Person staff = assertDoesNotThrow(() ->
                        CsvImporter.deserialisePerson(staffStrRep),
                "Valid staff csv string rep with avail and tags should not throw DeserialisePersonException");
        assertEquals(expectedStaff, staff);
    }

    @Test
    public void importContacts_contactsAddedToModel_successful() throws IOException, DeserialisePersonException {
        Model expectedModel = new ModelManager();
        expectedModel.addPerson(ALICE);
        expectedModel.addPerson(BOB);

        Model model = new ModelManager();
        String aliceCsvRep = "Student,";
        aliceCsvRep += ALICE.getName().fullName + ",";
        aliceCsvRep += ALICE.getPhone().value + ",";
        aliceCsvRep += ALICE.getUsername().value + ",";
        aliceCsvRep += ALICE.getEmail().value + ",";
        aliceCsvRep += ALICE.getTags().stream().map(AbstractTag::getTagName).collect(Collectors.joining(";"));
        aliceCsvRep += "\n";

        TeachingStaff bob = (TeachingStaff) BOB;
        String bobCsvRep = bob.getPosition().value + ",";
        bobCsvRep += bob.getName().fullName + ",";
        bobCsvRep += bob.getPhone().value + ",";
        bobCsvRep += bob.getUsername().value + ",";
        bobCsvRep += bob.getEmail().value + ",";
        // extra trailing comma present because bob has no availability
        bobCsvRep += bob.getTags().stream().map(AbstractTag::getTagName).collect(Collectors.joining(";")) + ",";
        bobCsvRep += "\n";
        String csv = CsvExporter.HEADERS + aliceCsvRep + bobCsvRep;

        Path filePath = tempDir.resolve("contacts.csv");
        Files.writeString(filePath, csv);

        assertDoesNotThrow(() -> CsvImporter.importContacts(model, filePath.toString()));
        assertEquals(expectedModel, model);
    }
    // TODO: need test other incorrect csv formats, like missing fields, wrong format of fields, etc
    // TODO: need test parsing of command (in another file)

}
