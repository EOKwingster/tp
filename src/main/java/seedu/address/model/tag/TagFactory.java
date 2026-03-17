package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.model.tag.restricted.RestrictedTag;
import seedu.address.model.tag.restricted.TagSchema;
import seedu.address.model.tag.restricted.TutorialTagSchema;

/**
 * A factory class responsible for creating instances of {@link AbstractTag}.
 * <p>
 * This class provides a centralized way to instantiate tags based on their
 * string representation. It supports both standard tags and restricted tags
 * (tags containing a delimiter).
 */
public class TagFactory {
    public static final String UNKNOWN_SCHEMA_MESSAGE = "Specified Restricted Tag of prefix %s is unknown";

    /**
     * Retrieves the associated {@link TagSchema} for a given tag prefix. If the
     * prefix does not match any known schema, it throws an exception.
     *
     * @param prefix The prefix extracted from the tag string.
     * @return The corresponding {@link TagSchema} instance.
     * @throws IllegalArgumentException if the prefix corresponds to an unknown
     *                                  schema.
     */
    private static TagSchema getAssociatedSchema(String prefix) {
        // CHECKSTYLE.OFF: Indentation
        return switch (prefix) {
            case TutorialTagSchema.VARIANT -> new TutorialTagSchema();
            default -> throw new IllegalArgumentException(String.format(UNKNOWN_SCHEMA_MESSAGE, prefix));
        };
        // CHECKSTYLE.ON: Indentation
    }

    /**
     * Creates a new {@link AbstractTag} instance from a raw string.
     *
     * @param tag The string representation of the tag to create.
     * @return A new instance of either {@link Tag} or {@link RestrictedTag}.
     * @throws IllegalArgumentException if the tag is null, contains invalid
     *                                  characters, has an unsupported prefix for
     *                                  restricted tags, or fails validation.
     */
    public static AbstractTag create(String tag) throws IllegalArgumentException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (RestrictedTag.isRestrictedTag(trimmedTag)) {
            var prefix = RestrictedTag.TagParts.parse(trimmedTag).get().prefix;
            var schema = getAssociatedSchema(prefix);
            return new RestrictedTag(schema, tag);
        }

        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalArgumentException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }
}
