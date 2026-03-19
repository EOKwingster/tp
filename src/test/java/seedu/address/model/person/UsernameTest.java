package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid usernames
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("amy_bee")); // underscores not allowed
        assertFalse(Username.isValidUsername("-")); // hyphens not allowed
        assertFalse(Username.isValidUsername("john doe")); // spaces not allowed
        assertFalse(Username.isValidUsername("user@name")); // @ not allowed
        assertFalse(Username.isValidUsername("user.name")); // dots not allowed
        assertFalse(Username.isValidUsername("user#1")); // hash not allowed
        assertFalse(Username.isValidUsername("user!")); // exclamation not allowed
        assertFalse(Username.isValidUsername("user name")); // space in middle not allowed
        assertFalse(Username.isValidUsername("123 456")); // space between digits not allowed

        // valid usernames
        assertTrue(Username.isValidUsername("amybee")); // alphabetic lowercase
        assertTrue(Username.isValidUsername("AMYBEE")); // alphabetic uppercase
        assertTrue(Username.isValidUsername("AmyBee")); // mixed case
        assertTrue(Username.isValidUsername("a")); // single letter
        assertTrue(Username.isValidUsername("1")); // single digit
        assertTrue(Username.isValidUsername("johndoe123")); // alphanumeric
        assertTrue(Username.isValidUsername("123456")); // digits only
    }

    @Test
    public void equals() {
        Username username = new Username("validusername");

        // same values -> returns true
        assertTrue(username.equals(new Username("validusername")));

        // same object -> returns true
        assertTrue(username.equals(username));

        // null -> returns false
        assertFalse(username.equals(null));

        // different types -> returns false
        assertFalse(username.equals(5.0f));

        // different values -> returns false
        assertFalse(username.equals(new Username("otherusername")));
    }
}
