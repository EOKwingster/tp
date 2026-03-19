package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AbstractTagTest {

    public static final String VALID_TAG_NAME_1 = "friend";
    public static final String VALID_TAG_NAME_2 = "colleague";
    public static final String VALID_TAG_NAME_WITH_NUMBERS = "tag123";
    public static final String INVALID_TAG_NAME_EMPTY = "";
    public static final String INVALID_TAG_NAME_SPACES = "tag with spaces";
    public static final String INVALID_TAG_NAME_SPECIAL_CHARS = "tag@friend";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagNameEmpty_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag(INVALID_TAG_NAME_EMPTY));
    }

    @Test
    public void constructor_invalidTagNameWithSpaces_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag(INVALID_TAG_NAME_SPACES));
    }

    @Test
    public void constructor_invalidTagNameWithSpecialChars_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag(INVALID_TAG_NAME_SPECIAL_CHARS));
    }

    @Test
    public void constructor_validTagName_success() {
        AbstractTag tag = new Tag(VALID_TAG_NAME_1);
        assertEquals(VALID_TAG_NAME_1, tag.tagName);
    }

    @Test
    public void constructor_validTagNameWithNumbers_success() {
        AbstractTag tag = new Tag(VALID_TAG_NAME_WITH_NUMBERS);
        assertEquals(VALID_TAG_NAME_WITH_NUMBERS, tag.tagName);
    }

    @Test
    public void isValidTagName_validTagNames_returnsTrue() {
        assertTrue(Tag.isValidTagName(VALID_TAG_NAME_1));
        assertTrue(Tag.isValidTagName(VALID_TAG_NAME_2));
        assertTrue(Tag.isValidTagName(VALID_TAG_NAME_WITH_NUMBERS));
        assertTrue(Tag.isValidTagName("a")); // Single character
        assertTrue(Tag.isValidTagName("1")); // Single number
    }

    @Test
    public void isValidTagName_invalidTagNames_returnsFalse() {
        assertFalse(Tag.isValidTagName(INVALID_TAG_NAME_EMPTY));
        assertFalse(Tag.isValidTagName(INVALID_TAG_NAME_SPACES));
        assertFalse(Tag.isValidTagName(INVALID_TAG_NAME_SPECIAL_CHARS));
        assertFalse(Tag.isValidTagName("tag-name")); // With hyphen
        assertFalse(Tag.isValidTagName("tag_name")); // With underscore
        assertFalse(Tag.isValidTagName("tag.name")); // With period
    }

    @Test
    public void getTagType_returnsCorrectType() {
        AbstractTag tag = new Tag(VALID_TAG_NAME_1);
        assertEquals(TagType.TAG, tag.getTagType());
    }

    @Test
    public void equals_sameTagName_returnsTrue() {
        AbstractTag tag1 = new Tag(VALID_TAG_NAME_1);
        AbstractTag tag2 = new Tag(VALID_TAG_NAME_1);
        assertEquals(tag1, tag2);
    }

    @Test
    public void equals_differentTagName_returnsFalse() {
        AbstractTag tag1 = new Tag(VALID_TAG_NAME_1);
        AbstractTag tag2 = new Tag(VALID_TAG_NAME_2);
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        AbstractTag tag = new Tag(VALID_TAG_NAME_1);
        assertEquals(tag, tag);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        AbstractTag tag = new Tag(VALID_TAG_NAME_1);
        assertFalse(tag.equals(null));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        AbstractTag tag = new Tag(VALID_TAG_NAME_1);
        assertFalse(tag.equals(VALID_TAG_NAME_1));
    }

    @Test
    public void hashCode_sameTagName_sameHashCode() {
        AbstractTag tag1 = new Tag(VALID_TAG_NAME_1);
        AbstractTag tag2 = new Tag(VALID_TAG_NAME_1);
        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void hashCode_differentTagName_differentHashCode() {
        AbstractTag tag1 = new Tag(VALID_TAG_NAME_1);
        AbstractTag tag2 = new Tag(VALID_TAG_NAME_2);
        assertNotEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void toString_validTagName_correctFormat() {
        AbstractTag tag = new Tag(VALID_TAG_NAME_1);
        assertEquals("[" + VALID_TAG_NAME_1 + "]", tag.toString());
    }
}
