package seedu.address.model.tag.restricted;

/**
 * A concrete implementation of {@link RegexTagSchema} specifically for tutorial
 * tags.
 * <p>
 * Valid tutorial tags typically follow the format of an optional uppercase
 * letter followed by up to two digits (e.g., "D24", "8").
 */
public class TutorialTagSchema extends RegexTagSchema {
    public static final String VARIANT = "tut";
    public static final String TAG_PATTERN = "[A-Z]?\\d{1,2}";
    public static final String MESSAGE_CONSTRAINTS = "Tutorial tag expects format of an optional uppercase letter "
            + "followed by maximum 2 numbers. Valid: 'D24' and '8'";

    /**
     * Constructs a {@code TutorialTagSchema}.
     */
    public TutorialTagSchema() {
        super(VARIANT, TAG_PATTERN);
    }

    @Override
    public String getConstraintViolationMessage() {
        return MESSAGE_CONSTRAINTS;
    }
}
