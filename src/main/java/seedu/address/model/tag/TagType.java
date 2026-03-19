package seedu.address.model.tag;

/**
 * Represents types of Tag in the address book.
 */
public enum TagType {
    TAG(999),
    COURSE(0),
    TUTORIAL(1),
    LAB(2);

    /**
     * The priority of the tag type for UI display. The lower the value, the higher the priority.
     */
    private final int uiPriority;

    TagType(int uiPriority) {
        this.uiPriority = uiPriority;
    }

    public int getUiPriority() {
        return uiPriority;
    }
}
