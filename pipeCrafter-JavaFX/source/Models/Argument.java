package Models;

import java.io.File;
import Utils.OutputParameters;

/** Internal representation of argument related data */
public class Argument {

    // #region Nested Types

    /** The types of arguments */
    public enum Type {
        FILE, // represented by a File
        OUTPUT, // represenetd by an OutputParameters object
        TEXT, // represented by a String
        INVALID;
    }
    // #endregion

    // #region Attributes

    /** Type of the argument */
    private Type type = Type.INVALID;

    /**
     * Abstract Object holding the value of the argument to be converted
     * dependeing on the specified Type
     */
    private Object objectValue;

    /** View object associated with this model */
    private Views.Argument argumentView;

    /** Command to which the argument belongs to */
    private Command motherCommand;

    // #endregion

    // #region Constructors

    public Argument(Type type, Object objectValue) {
        setArgument(type, objectValue);
    }

    // #endregion

    // #region Methods

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
                return value instanceof OutputParameters;
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
        // If argument was already set as a reference to a command, update the
        // reference to the arg in the command's reffering list
        if (this.type == Type.OUTPUT) {
            getOutputParameter().getCmdToUse().getReferringArgumentList().remove(this);
        }
        this.type = type;
        this.objectValue = objectValue;
    }

    /** Make an argument invalid */
    public void makeInvalid() {
        type = Type.INVALID;
    }

    /** Make an argument valid */
    public void makeValidOutput() {
        type = Type.OUTPUT;
    }

    /**
     * Return the argument in a usable String format depending on its type and
     * format
     */
    @Override
    public String toString() {
        switch (type) {
            case FILE:
                return ((File) objectValue).getAbsolutePath();
            case OUTPUT:
                return getOutputParameter().toString();
            case TEXT:
                return (String) objectValue;
            case INVALID:
                if (objectValue instanceof OutputParameters) {
                    return getOutputParameter().toString();
                }
            default:
                return null;
        }
    }

    // #endregion

    // #region Getters and Setters

    public Type getType() {
        return type;
    }

    public Object getObjectValue() {
        return objectValue;
    }

    public Command getMotherCommand() {
        return motherCommand;
    }

    public void setMotherCommand(Command motherCommand) {
        this.motherCommand = motherCommand;
    }

    public Views.Argument getArgumentView() {
        return argumentView;
    }

    public void setArgumentView(Views.Argument argumentView) {
        this.argumentView = argumentView;
    }

    public OutputParameters getOutputParameter() {
        if (!(objectValue instanceof OutputParameters)) {
            throw new IllegalArgumentException("Wrong type of argument");
        }
        return (OutputParameters) objectValue;
    }

    // #endregion
}
