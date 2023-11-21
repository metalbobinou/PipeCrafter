package Models;

import java.io.File;

/** Internal representation of argument related data */
public class Argument {

    // region Nested Types

    public enum Type {
        FILE,
        OUTPUT,
        TEXT,
        INVALID;
    }

    // endregion

    // region Attributes

    /** Type of the argument */
    private Type type;

    /**
     * Abstract Object holding the value of the argument to be converted
     * dependeing on the specified Type
     */
    private Object objectValue;

    // endregion

    // region Constructors

    public Argument(Type type, Object objectValue) {
        setArgument(type, objectValue);
    }

    // endregion

    // region Methods

    private boolean checkTypeValueMatch(Type type, Object value) {
        switch (type) {
            case FILE:
                return value instanceof File;
            case OUTPUT:
                return value instanceof Object[];
            case TEXT:
                return value instanceof String;
            default: // Invalid or unset
                return true;
        }
    }

    public void setArgument(Type type, Object objectValue) {
        if (!checkTypeValueMatch(type, objectValue)) {
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.objectValue = objectValue;
    }

    // endregion

    // region Getters and Setters

    public Type getType() {
        return type;
    }

    public Object getObjectValue() {
        return objectValue;
    }

    // endregion
}
