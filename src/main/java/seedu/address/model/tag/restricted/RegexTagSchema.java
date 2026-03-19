package seedu.address.model.tag.restricted;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Pattern;

/**
 * Represents a tag schema that uses a regex pattern for validation.
 */
public abstract class RegexTagSchema extends TagSchema {
    protected final Pattern pattern;

    /**
     * Constructs a {@code RegexTagSchema}.
     *
     * @param variant The variant identifier for the schema.
     * @param pattern The regular expression pattern to enforce.
     * @throws IllegalArgumentException if the pattern is null.
     */
    public RegexTagSchema(String variant, String pattern) {
        super(variant);
        requireNonNull(pattern);
        checkArgument(pattern.length() > 0);
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean isTagValid(String tag) {
        if (tag == null) {
            return false;
        }
        return pattern.matcher(tag).matches();
    }
}
