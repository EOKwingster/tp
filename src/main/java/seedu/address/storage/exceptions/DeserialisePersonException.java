package seedu.address.storage.exceptions;

/**
 * Represents an error which occurs during deserialisation of csv string representation of Person object.
 */
public class DeserialisePersonException extends Exception {
    public DeserialisePersonException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DeserialisePersonException} with the specified detail {@code message} and {@code cause}.
     */
    public DeserialisePersonException(String message, Throwable cause) {
        super(message, cause);
    }
}
