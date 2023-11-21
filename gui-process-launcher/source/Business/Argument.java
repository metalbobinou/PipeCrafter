package Business;

/** Business class for arguments */
public class Argument {

    // region Attributes

    /** Used to save the argument being added, null when un/reset */
    private static Models.Argument addedArg = null;

    // endregion

    // region Methods

    // endregion

    // region Getters and Setters

    public static Models.Argument getAddedArg() {
        Models.Argument res = addedArg;
        addedArg = null;
        return res;
    }

    public static void setAddedArg(Models.Argument addedArg) {
        Argument.addedArg = addedArg;
    }

    // endregion

}
