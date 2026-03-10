package seedu.address.model.person;

import java.util.Collections;
import java.util.Set;

import seedu.address.model.person.exceptions.ImmutableEscapedScopeException;
import seedu.address.model.tag.Tag;

/**
 * A temporarily mutable version of Person, allowing modification of its fields.
 * Allows clean editing of an object without violating the functional
 * immutability requirements of Person
 *
 * <p>
 * Care must be taken to avoid leaking mutability during internal API
 * implementation using this object. Always markComplete() once mutablitity is
 * no longer explicitly required.
 * </p>
 *
 * @see Person
 * @see ImmutableEscapedScopeException
 * @see Tag
 */
public class MutablePerson extends Person {
    // mutable object rendered immutable with runtime check, ensures that this
    // object cannot be modified in an outer scope
    private boolean isEditable = true;

    protected MutablePerson(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags);
    }

    public void setName(Name name) {
        checkEditable();
        this.name = name;
    }

    public void setPhone(Phone phone) {
        checkEditable();
        this.phone = phone;
    }

    public void setEmail(Email email) {
        checkEditable();
        this.email = email;
    }

    public void setAddress(Address address) {
        checkEditable();
        this.address = address;
    }

    public void setTags(Set<Tag> tags) {
        checkEditable();
        this.tags = Collections.unmodifiableSet(tags);
    }

    private void checkEditable() {
        if (!isEditable) {
            throw new ImmutableEscapedScopeException();
        }
    }

    public void markComplete() {
        isEditable = false;
    }
}
