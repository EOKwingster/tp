package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TagTypeTest {
    @Test
    public void testTagTypeValueOf() {
        // Assert that valueOf() works correctly
        TagType tagType = TagType.valueOf("TAG");
        assertEquals(TagType.TAG, tagType);
    }

    @Test
    public void testTagTypeGetUiPriority() {
        assertEquals(999, TagType.TAG.getUiPriority());
        assertEquals(0, TagType.COURSE.getUiPriority());
        assertEquals(1, TagType.TUTORIAL.getUiPriority());
        assertEquals(2, TagType.LAB.getUiPriority());
    }
}

