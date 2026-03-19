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
}

