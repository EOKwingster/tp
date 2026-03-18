package seedu.address.model.tag.restricted;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TagSchemaTest {
    public static class TagSchemaStub extends TagSchema {
        public TagSchemaStub(String prefix) {
            super(prefix);
        }

        @Override
        public boolean isTagValid(String value) {
            return !value.isEmpty() && !value.contains("invalid");
        }

        @Override
        public String getConstraintViolationMessage() {
            return "Mock constraint violation";
        }
    }

    public static final String VALID_PREFIX = "yum";

    @Test
    public void null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new TagSchemaStub(null));
    }

    @Test
    public void correctVariant() {
        TagSchemaStub schema = new TagSchemaStub(VALID_PREFIX);
        assertEquals(schema.getVariant(), VALID_PREFIX);
    }
}
