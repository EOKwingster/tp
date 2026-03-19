package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

/**
 * Represents a Teaching Staff's position in the address book.
 * Only "Teaching Assistant" and "Professors" are allowed.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class Position {

    /** Allowed position values. */
    public static final String TEACHING_ASSISTANT = "Teaching Assistant";
    public static final String PROFESSORS = "Professors";

    private static final Set<String> ALLOWED_VALUES = Set.of(TEACHING_ASSISTANT, PROFESSORS);

    public static final String MESSAGE_CONSTRAINTS =
            "Position must be one of: " + String.join(", ", ALLOWED_VALUES);

    public final String value;

    /**
     * Constructs a {@code Position}.
     *
     * @param position A valid position (Teaching Assistant or Professors).
     */
    public Position(String position) {
        requireNonNull(position);
        checkArgument(isValidPosition(position), MESSAGE_CONSTRAINTS);
        value = position.trim();
    }

    /**
     * Returns true if a given string is a valid position (Teaching Assistant or Professors, after trim).
     *
     * @param test The string to validate; may be null (returns false).
     * @return True if {@code test} is non-null and equals one of the allowed position values after trimming.
     */
    public static boolean isValidPosition(String test) {
        return test != null && ALLOWED_VALUES.contains(test.trim());
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Position)) {
            return false;
        }

        Position otherPosition = (Position) other;
        return value.equals(otherPosition.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
