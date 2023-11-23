package Models;

import java.io.File;

import Utils.OutputStream;

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

    /**
     * Check that the given value corresponds to the given type
     * 
     * @param type  type to check
     * @param value value to check
     * @return true if match and false otherwise
     */
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

    /**
     * Change the argument's type and value only if they match, raise an
     * exception otherwise
     * 
     * @param type        new type
     * @param objectValue new value
     */
    public void setArgument(Type type, Object objectValue) {
        if (!checkTypeValueMatch(type, objectValue)) {
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.objectValue = objectValue;
    }

    @Override
    public String toString() {
        switch (type) {
            case FILE:
                return ((File) objectValue).getAbsolutePath();
            case OUTPUT:
                Object[] objArr = (Object[]) objectValue;
                return Utils.Utils.getPathForOutputOfStep((int) objArr[0],
                        (OutputStream) objArr[1]);
            case TEXT:
                return (String) objectValue;
            default:
                return null;
        }
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
