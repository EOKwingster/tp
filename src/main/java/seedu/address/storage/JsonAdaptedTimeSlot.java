package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.TimeSlot;

/**
 * Jackson-friendly version of {@link TimeSlot}.
 */
class JsonAdaptedTimeSlot {

    private final String slot;

    /**
     * Constructs a {@code JsonAdaptedTimeSlot} with the given slot string.
     */
    @JsonCreator
    public JsonAdaptedTimeSlot(String slot) {
        this.slot = slot;
    }

    /**
     * Converts a given {@code TimeSlot} into this class for Jackson use.
     */
    public JsonAdaptedTimeSlot(TimeSlot source) {
        slot = source.toString();
    }

    @JsonValue
    public String getSlot() {
        return slot;
    }

    /**
     * Converts this Jackson-friendly adapted time slot object into the model's {@code TimeSlot} object.
     *
     * @throws IllegalValueException if the slot string is invalid.
     */
    public TimeSlot toModelType() throws IllegalValueException {
        if (!TimeSlot.isValidTimeSlot(slot)) {
            throw new IllegalValueException(TimeSlot.MESSAGE_CONSTRAINTS);
        }
        return new TimeSlot(slot);
    }
}
