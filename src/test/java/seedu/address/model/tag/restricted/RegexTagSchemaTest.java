package seedu.address.model.tag.restricted;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RegexTagSchemaTest {
    private static final String WHITELIST_PREFIX = "hello";
    // matches hello/ANYTHING_HERE
    private static final String VALID_PATTERN = "^" + WHITELIST_PREFIX + "\\/.*$";
    private static final String VALID_TAG = WHITELIST_PREFIX + "/valid123";
    private static final String INVALID_TAG = "invalid-tag";
    private static final String INVALID_TAG_EMPTY_VALUE = WHITELIST_PREFIX;
    private static final String INVALID_TAG_NO_SLASH = WHITELIST_PREFIX + "valid123";

    public static class RegexTagSchemaStub extends RegexTagSchema {
        public RegexTagSchemaStub(String variant, String pattern) {
            super(variant, pattern);
        }

        @Override
        public String getConstraintViolationMessage() {
            return "Mock violation";
        }
    }

    @Test
    public void constructor_nullPattern_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RegexTagSchemaStub(WHITELIST_PREFIX, null));
    }

    @Test
    public void constructor_emptyPattern_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RegexTagSchemaStub(WHITELIST_PREFIX, ""));
    }

    public void constructor_invalidPattern_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RegexTagSchemaStub(WHITELIST_PREFIX, "("));
    }

    @Test
    public void constructor_validPattern_success() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        assertEquals(WHITELIST_PREFIX, schema.getVariant());
    }

    @Test
    public void validPrefix_returnsTrue() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        assertTrue(schema.doesPrefixMatch(WHITELIST_PREFIX));
    }

    @Test
    public void invalidPrefix_returnsFalse() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        assertFalse(schema.doesPrefixMatch("other"));
    }

    @Test
    public void isTagValid_validTag_returnsTrue() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        assertTrue(schema.isTagValid(VALID_TAG));
    }

    @Test
    public void isTagValid_invalidTag_returnsFalse() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        assertFalse(schema.isTagValid(INVALID_TAG));
    }

    @Test
    public void isTagValid_emptyValue_returnsFalse() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        assertFalse(schema.isTagValid(INVALID_TAG_EMPTY_VALUE));
    }

    @Test
    public void isTagValid_noSlash_returnsFalse() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        assertFalse(schema.isTagValid(INVALID_TAG_NO_SLASH));
    }

    @Test
    public void getConstraintViolationMessage_nonEmpty() {
        RegexTagSchemaStub schema = new RegexTagSchemaStub(WHITELIST_PREFIX, VALID_PATTERN);
        String message = schema.getConstraintViolationMessage();
        assertTrue(message != null);
    }
}
