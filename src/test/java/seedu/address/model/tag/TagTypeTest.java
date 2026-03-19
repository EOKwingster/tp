package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TagTypeTest {

    @Test
    public void testTagTypeToString() {
        // Assert that toString() returns the correct description
        assertEquals("Tag", TagType.TAG.toString());
    }

    @Test
    public void testTagTypeValueOf() {
        // Assert that valueOf() works correctly
        TagType tagType = TagType.valueOf("TAG");
        assertEquals(TagType.TAG, tagType);
    }

    @Test
    public void testTagTypeDescription() {
        // Assert that the Tag enum has the correct description
        String expectedDescription = "Tag";
        assertEquals(expectedDescription, TagType.TAG.toString());
    }

}

