package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    protected Name name;
    protected Phone phone;
    protected Email email;

    // Data fields
    private final Username username;
    private final Role role;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Username username, Role role, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, username, role, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.role = role;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Username getUsername() {
        return username;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && username.equals(otherPerson.username)
                && role.equals(otherPerson.role)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, username, role, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("username", username)
                .add("role", role)
                .add("tags", tags)
                .toString();
    }

    /**
     * Creates a temporarily mutable copy of this Person, allowing modification
     * through a delegate function. This is useful for operations that
     * require temporary mutability (e.g., editing) before finalizing the object.
     *
     * @param delegate A consumer that receives the mutable copy and can modify it
     * @return An immutable Person instance that has been modified by the delegate.
     * @throws NullPointerException if the delegate is null.
     */
    public Person cloneInto(Consumer<MutablePerson> delegate) {
        requireNonNull(delegate);
        var clonedPerson = new MutablePerson(name, phone, email, address, tags);
        delegate.accept(clonedPerson);
        clonedPerson.markComplete();
        return clonedPerson;
    }

}
