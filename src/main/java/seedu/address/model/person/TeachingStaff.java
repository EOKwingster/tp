package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.AbstractTag;

/**
 * Represents a Teaching Staff member in the address book.
 * Extends Person with an additional position field.
 * Teaching staff have position "Teaching Assistant" by default.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public non-sealed class TeachingStaff extends Person {

    /** Default position for teaching staff when not specified. */
    public static final String DEFAULT_POSITION_VALUE = Position.TEACHING_ASSISTANT;

    /** Prefix for generated staff phone numbers (8xxxxxxx). */
    private static final String DEFAULT_PHONE_PREFIX = "8";
    /** Number of digits in the generated part of default phone (7 digits, so 10^7 range). */
    private static final int DEFAULT_PHONE_MODULO = 10_000_000;
    /** Default email domain for staff when only name is provided. */
    private static final String DEFAULT_EMAIL_DOMAIN = "staff.local";
    /** Fallback local part when name yields no valid email local part. */
    private static final String DEFAULT_EMAIL_LOCAL_FALLBACK = "staff";
    /** Prefix for generated staff username. */
    private static final String DEFAULT_USERNAME_PREFIX = "staff";

    protected Position position;

    /**
     * Constructs teaching staff with name only; phone, email, username and position use defaults.
     * Position is set to {@value #DEFAULT_POSITION_VALUE}.
     *
     * @param name A valid name (required).
     */
    public TeachingStaff(Name name) {
        this(name, Collections.emptySet());
    }

    /**
     * Constructs teaching staff with name and tags; other fields use defaults.
     *
     * @param name A valid name (required).
     * @param tags Tags for the staff (can be empty).
     */
    public <T extends AbstractTag> TeachingStaff(Name name, Set<T> tags) {
        super(
                name,
                defaultPhoneForName(name),
                defaultEmailForName(name),
                defaultUsernameForName(name),
                tags
        );
        this.position = new Position(DEFAULT_POSITION_VALUE);
    }

    /**
     * Every field must be present and not null.
     */
    public <T extends AbstractTag> TeachingStaff(Name name, Phone phone, Email email, Username username,
                         Position position, Set<T> tags) {
        super(name, phone, email, username, tags);
        requireAllNonNull(position);
        this.position = position;
    }

    private static Phone defaultPhoneForName(Name name) {
        int code = Math.abs(name.fullName.hashCode()) % DEFAULT_PHONE_MODULO;
        return new Phone(String.format(DEFAULT_PHONE_PREFIX + "%07d", code));
    }

    private static Email defaultEmailForName(Name name) {
        String local = name.fullName.replaceAll("\\s+", "").toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", "");
        if (local.isEmpty()) {
            local = DEFAULT_EMAIL_LOCAL_FALLBACK;
        }
        return new Email(local + "@" + DEFAULT_EMAIL_DOMAIN);
    }

    private static Username defaultUsernameForName(Name name) {
        String base = name.fullName.replaceAll("\\s+", "");
        if (!Username.isValidUsername(base)) {
            base = DEFAULT_EMAIL_LOCAL_FALLBACK;
        }
        return new Username(DEFAULT_USERNAME_PREFIX + base);
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TeachingStaff)) {
            return false;
        }

        TeachingStaff otherStaff = (TeachingStaff) other;
        return super.equals(otherStaff)
                && position.equals(otherStaff.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, username, position, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("username", username)
                .add("position", position)
                .add("tags", tags)
                .toString();
    }
}
