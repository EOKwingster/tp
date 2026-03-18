package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.TeachingStaff;
import seedu.address.model.person.Username;
import seedu.address.model.tag.AbstractTag;

/**
 * Jackson-friendly version of {@link Person}.
 * Serialises as either student or staff (teaching staff) with a type field.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    /** JSON type value for teaching staff. */
    private static final String TYPE_STAFF = "staff";
    /** JSON type value for student. */
    private static final String TYPE_STUDENT = "student";

    private final String name;
    private final String phone;
    private final String email;
    private final String username;
    private final String type;
    private final String position;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("username") String username,
            @JsonProperty("type") String type, @JsonProperty("position") String position,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.type = type;
        this.position = position;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        username = source.getUsername().value;
        if (source instanceof TeachingStaff staff) {
            type = TYPE_STAFF;
            position = staff.getPosition().value;
        } else {
            type = TYPE_STUDENT;
            position = null;
        }
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @return A {@link Person} or {@link TeachingStaff} with the adapted data.
     * @throws IllegalValueException If any data constraints are violated.
     */
    public Person toModelType() throws IllegalValueException {
        final List<AbstractTag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        final Set<AbstractTag> modelTags = new HashSet<>(personTags);

        boolean isStaff = TYPE_STAFF.equals(type);
        if (isStaff) {
            if (position == null) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Position.class.getSimpleName()));
            }
            if (!Position.isValidPosition(position)) {
                throw new IllegalValueException(Position.MESSAGE_CONSTRAINTS);
            }
        }

        if (isStaff) {
            final Position modelPosition = new Position(position);
            return new TeachingStaff(modelName, modelPhone, modelEmail, modelUsername, modelPosition, modelTags);
        }
        return new Person(modelName, modelPhone, modelEmail, modelUsername, modelTags);
    }

}
