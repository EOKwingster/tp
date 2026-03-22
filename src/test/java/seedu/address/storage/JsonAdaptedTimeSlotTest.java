package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.TimeSlot;

public class JsonAdaptedTimeSlotTest {

    @Test
    public void constructor_fromTimeSlot_serializesToString() {
        TimeSlot model = new TimeSlot("mon-10-12");
        JsonAdaptedTimeSlot adapted = new JsonAdaptedTimeSlot(model);
        assertEquals(model.toString(), adapted.getSlot());
    }

    @Test
    public void constructor_fromString_storesSlot() {
        JsonAdaptedTimeSlot adapted = new JsonAdaptedTimeSlot("mon-10-12");
        assertEquals("mon-10-12", adapted.getSlot());
    }

    @Test
    public void toModelType_validSlot_returnsTimeSlot() throws Exception {
        JsonAdaptedTimeSlot adapted = new JsonAdaptedTimeSlot("wed-10-12");
        TimeSlot expected = new TimeSlot("wed-10-12");
        assertEquals(expected, adapted.toModelType());
    }

    @Test
    public void toModelType_invalidSlot_throwsIllegalValueException() {
        JsonAdaptedTimeSlot adapted = new JsonAdaptedTimeSlot("not-a-slot");
        assertThrows(IllegalValueException.class, TimeSlot.MESSAGE_CONSTRAINTS, adapted::toModelType);
    }
}
