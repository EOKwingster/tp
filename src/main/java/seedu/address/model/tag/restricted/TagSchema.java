package seedu.address.model.tag.restricted;

import static java.util.Objects.requireNonNull;

/**
 * Abstract base class for defining tag validation schemas.
 * <p>
 * Subclasses must implement logic to determine if a tag name is valid and what
 * error message to return.
 */
public abstract class TagSchema {
    protected final String variant;

    /**
     * Constructs a new {@code TagSchema}.
     *
     * @param variant A unique identifier for the schema variant.
     */
    public TagSchema(String variant) {
        requireNonNull(variant);
        this.variant = variant;
    }

    /**
     * Returns the variant identifier associated with this schema.
     *
     * @return The variant string.
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Checks whether a given tag name matches the expected prefix according to the
     * current schema variant.
     *
     * @param prefix The tag name to validate against the schema variant.
     * @return if the prefix matches the variant identifier
     */
    public boolean doesPrefixMatch(String prefix) {
        return variant.equals(prefix);
    }

    /**
     * Validates whether the given tag is valid according to this schema's rules.
     *
     * @param tag The tag string to validate.
     * @return true if the tag is valid; false otherwise.
     */
    public abstract boolean isTagValid(String tag);

    /**
     * Returns the error message to display if validation fails.
     *
     * @return The constraint violation message.
     */
    public abstract String getConstraintViolationMessage();
}
