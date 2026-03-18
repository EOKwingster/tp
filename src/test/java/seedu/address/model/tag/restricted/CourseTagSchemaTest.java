package seedu.address.model.tag.restricted;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CourseTagSchemaTest {
    public static final String VALID_COURSE_TAG = "CS2103T";
    public static final String INVALID_COURSE_TAG = "cs2103";

    private CourseTagSchema schema = new CourseTagSchema();

    @Test
    public void testValidTags() {
        assertTrue(schema.isTagValid(VALID_COURSE_TAG));
        assertTrue(schema.isTagValid("CS2103"));
        assertTrue(schema.isTagValid("AB1234Z"));
        assertTrue(schema.isTagValid("ABC1234Z"));
        assertTrue(schema.isTagValid("ABCD1234Z"));
        assertTrue(schema.isTagValid("ABCD1234"));
    }

    @Test
    public void testInvalidTags() {
        assertFalse(schema.isTagValid(INVALID_COURSE_TAG));
        assertFalse(schema.isTagValid("CS2103TTTTT"));
        assertFalse(schema.isTagValid("CS2103t"));
        assertFalse(schema.isTagValid("CSSSS2103"));
        assertFalse(schema.isTagValid("AbCD1234"));
        assertFalse(schema.isTagValid("AB12"));
        assertFalse(schema.isTagValid("AB1233333333333"));
        assertFalse(schema.isTagValid("A1234"));
    }

    @Test
    public void testGetConstraintViolationMessage() {
        assertEquals(CourseTagSchema.MESSAGE_CONSTRAINTS, schema.getConstraintViolationMessage());
    }

    @Test
    public void getVariant() {
        assertEquals(CourseTagSchema.VARIANT, schema.getVariant());
    }
}
