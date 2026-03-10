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

        // valid usernames
        assertTrue(Username.isValidUsername("amy_bee"));
        assertTrue(Username.isValidUsername("-")); // one character
        assertTrue(Username.isValidUsername("john_doe_123")); // alphanumeric with underscores
    }

    @Test
    public void equals() {
        Username username = new Username("valid_username");

        // same values -> returns true
        assertTrue(username.equals(new Username("valid_username")));

        // same object -> returns true
        assertTrue(username.equals(username));

        // null -> returns false
        assertFalse(username.equals(null));

        // different types -> returns false
        assertFalse(username.equals(5.0f));

        // different values -> returns false
        assertFalse(username.equals(new Username("other_username")));
    }
}
