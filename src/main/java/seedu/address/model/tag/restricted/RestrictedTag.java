package seedu.address.model.tag.restricted;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Optional;

import seedu.address.model.tag.AbstractTag;
import seedu.address.model.tag.TagType;

/**
 * Represents a tag that must adhere to a specific delimiter format
 * ("prefix:value").
 *
 * <p>
 * This class extends enforces constraints defined by a {@link TagSchema}. If
 * validation fails during construction, an exception is thrown.
 * <p>
 */
public class RestrictedTag extends AbstractTag {
    public static final String MESSAGE_CONSTRAINTS = "Attempted to create RestrictedTag with missing delimiter ':'";
    public static final String DELIMITER = ":";

    private final TagSchema schema;

    /**
     * Constructs a {@code RestrictedTag} with the given schema and tag string.
     * <p>
     * The constructor validates that the tag contains the delimiter and matches the
     * schema's constraints.
     *
     * @param schema The schema defining the rules for this tag (e.g.,
     *               {@link TutorialTagSchema}).
     * @param tag    The tag string in the format "prefix:value".
     * @throws IllegalArgumentException if the tag does not contain the delimiter or
     *                                  fails schema validation.
     */
    public RestrictedTag(TagSchema schema, String tag) {
        super(tag);
        var p = TagParts.parse(tag);
        checkArgument(p.isPresent(), MESSAGE_CONSTRAINTS);

        var parts = p.get();
        checkArgument(schema.doesPrefixMatch(parts.prefix), schema.getConstraintViolationMessage());
        checkArgument(schema.isTagValid(parts.value), schema.getConstraintViolationMessage());
        this.schema = schema;
    }

    @Override
    public TagType getTagType() {
        return switch (getSchema().getVariant()) {
            case "course" -> TagType.COURSE;
            case "tut" -> TagType.TUTORIAL;
            case "lab" -> TagType.LAB;
            default -> TagType.TAG;
        };
    }

    public TagSchema getSchema() {
        return schema;
    }

    /**
     * Checks if a given string contains the restricted tag delimiter.
     *
     * @param test The string to check.
     * @return true if the string contains the delimiter; false otherwise.
     */
    public static boolean isRestrictedTag(String test) {
        return test.contains(DELIMITER);
    }

    /**
     * Represents the parsed components of a {@code RestrictedTag}. It encapsulates
     * the prefix and value parts of the tag string. (prefix:value)
     */
    public static class TagParts {
        public final String prefix;
        public final String value;

        private TagParts(String prefix, String value) {
            this.prefix = prefix;
            this.value = value;
        }

        /**
         * Parses a tag string into its constituent parts (prefix and value).
         *
         * @param tag The tag string to parse, in the format "prefix:value".
         * @return An {@code Optional} containing the parsed {@code TagParts} object if
         *         parsing is successful. An empty {@code Optional} is returned if
         *         parsing fails.
         */
        public static Optional<TagParts> parse(String tag) {
            requireNonNull(tag);
            var res = tag.split(DELIMITER, 2);
            if (res.length != 2) {
                return Optional.empty();
            }
            return Optional.of(new TagParts(res[0], res[1]));
        }
    }
}
