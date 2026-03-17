package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.restricted.RestrictedTag;
import seedu.address.model.tag.restricted.TutorialTagSchema;
import seedu.address.model.tag.restricted.TutorialTagSchemaTest;

/**
 * Integration tests for {@link TagFactory}.
 */
public class TagFactoryTest {
    @Test
    public void nullTag_throws() {
        assertThrows(NullPointerException.class, () -> TagFactory.create(null));
    }

    @Test
    public void validTag_passes() {
        AbstractTag tag = TagFactory.create(AbstractTagTest.VALID_TAG_NAME_1);
        assertTrue(tag instanceof Tag);
        assertEquals(AbstractTagTest.VALID_TAG_NAME_1, tag.tagName);
    }

    @Test
    public void invalidTag_fails() {
        assertThrows(IllegalArgumentException.class, () -> TagFactory.create(AbstractTagTest.INVALID_TAG_NAME_EMPTY));
        assertThrows(IllegalArgumentException.class, () -> TagFactory.create(AbstractTagTest.INVALID_TAG_NAME_SPACES));
    }

    @Test
    public void invalidRestrictedTagVariant_fails() {
        var e = assertThrows(IllegalArgumentException.class, () -> TagFactory.create("thisdoesnotexist:1234"));
        assertEquals(String.format(TagFactory.UNKNOWN_SCHEMA_MESSAGE, "thisdoesnotexist"), e.getMessage());
    }

    @Test
    public void validRestrictedTagTutorial_passes() {
        var s = TutorialTagSchema.VARIANT + RestrictedTag.DELIMITER + TutorialTagSchemaTest.VALID_TUTORIAL_TAG;
        AbstractTag tag = TagFactory.create(s);
        assertTrue(tag instanceof RestrictedTag);
        assertEquals(s, tag.tagName);
    }

    @Test
    public void invalidRestrictedTagTutorial_fails() {
        var s = TutorialTagSchema.VARIANT + RestrictedTag.DELIMITER + TutorialTagSchemaTest.INVALID_TUTORIAL_TAG;
        assertThrows(IllegalArgumentException.class, () -> TagFactory.create(s));
    }
}
