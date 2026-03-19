package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person.MutablePerson;
import seedu.address.model.person.exceptions.ImmutableEscapedScopeException;
import seedu.address.model.tag.Tag;

public class MutablePersonTest {
    private MutablePerson mutablePerson;

    @BeforeEach
    public void setup() {
        mutablePerson = new MutablePerson(new Name(VALID_NAME_AMY), new Phone(VALID_PHONE_AMY),
                new Email(VALID_EMAIL_AMY), new Username(VALID_USERNAME_AMY),
                new HashSet<>());
    }

    @Test
    public void testSetAllFields() {
        mutablePerson.setName(new Name(VALID_NAME_BOB));
        mutablePerson.setPhone(new Phone(VALID_PHONE_BOB));
        mutablePerson.setEmail(new Email(VALID_EMAIL_BOB));
        mutablePerson.setUsername(new Username(VALID_USERNAME_BOB));
        mutablePerson.setTags(new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND))));

        assertEquals(VALID_NAME_BOB, mutablePerson.getName().toString());
        assertEquals(VALID_PHONE_BOB, mutablePerson.getPhone().toString());
        assertEquals(VALID_EMAIL_BOB, mutablePerson.getEmail().toString());
        assertEquals(VALID_USERNAME_BOB, mutablePerson.getUsername().toString());
        assertTrue(mutablePerson.getTags().contains(new Tag(VALID_TAG_FRIEND)));
    }

    @Test
    public void notNull() {
        assertThrows(NullPointerException.class, () -> {
            mutablePerson.setName(null);
        });

        assertThrows(NullPointerException.class, () -> {
            mutablePerson.setPhone(null);
        });

        assertThrows(NullPointerException.class, () -> {
            mutablePerson.setEmail(null);
        });

        assertThrows(NullPointerException.class, () -> {
            mutablePerson.setUsername(null);
        });

        assertThrows(NullPointerException.class, () -> {
            mutablePerson.setTags(null);
        });
    }

    @Test
    public void testSetFieldsAfterMarkComplete() {
        mutablePerson.markComplete();

        assertThrows(ImmutableEscapedScopeException.class, () -> {
            mutablePerson.setName(new Name(VALID_NAME_BOB));
        });

        assertThrows(ImmutableEscapedScopeException.class, () -> {
            mutablePerson.setPhone(new Phone(VALID_PHONE_BOB));
        });

        assertThrows(ImmutableEscapedScopeException.class, () -> {
            mutablePerson.setEmail(new Email(VALID_EMAIL_BOB));
        });

        assertThrows(ImmutableEscapedScopeException.class, () -> {
            mutablePerson.setUsername(new Username(VALID_USERNAME_BOB));
        });

        assertThrows(ImmutableEscapedScopeException.class, () -> {
            mutablePerson.setTags(new HashSet<>());
        });
    }
}
