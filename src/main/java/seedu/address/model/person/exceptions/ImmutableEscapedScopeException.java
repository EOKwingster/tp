package seedu.address.model.person.exceptions;

/**
 * Signals that a temporarily mutable object has been modified after it's
 * (mutable) lifetime should have expired
 */
public class ImmutableEscapedScopeException extends RuntimeException {
    public ImmutableEscapedScopeException() {
        super("Mutable operation performed outside of valid scope");
    }
}