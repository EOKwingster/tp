package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.TeachingStaff;
import seedu.address.model.person.Username;
import seedu.address.model.tag.AbstractTag;
import seedu.address.model.tag.TagFactory;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Username("alexyeoh"),
                getTagSet("friends")),
            new TeachingStaff(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Username("berniceyu"), new Position("Professors"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Username("charlotteo"),
                getTagSet("neighbours")),
            new TeachingStaff(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Username("davidli"), new Position("Teaching Assistant"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Username("irfani"),
                getTagSet("classmates")),
            new TeachingStaff(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Username("royb"), new Position("Teaching Assistant"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<AbstractTag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(TagFactory::create)
                .collect(Collectors.toSet());
    }

}
