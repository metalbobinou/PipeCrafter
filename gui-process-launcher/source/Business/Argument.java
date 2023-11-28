package Business;

import java.util.UUID;

import javafx.scene.Node;

/** Business class for arguments */
public class Argument {

    // region Attributes

    /** Used to save the argument being added, null when un/reset */
    private static Models.Argument addedArg = null;

    // endregion

    // region Methods

    /**
     * Retreive addedArg variable and reset it to null
     * 
     * @return addedArg
     */
    public static Models.Argument popAddedArg() {
        Models.Argument res = addedArg;
        addedArg = null;
        return res;
    }

    /**
     * Check if addedArg is set
     * 
     * @return true if set, false otherwise
     */
    public static boolean isAddedArgSet() {
        return addedArg != null;
    }

    public static void addArgument(UUID commandID, Views.Argument controller, Node node) {
        Models.Argument model = Business.Argument.popAddedArg();

        Business.Command.getCommand(commandID).ifPresentOrElse(
                (commandModel) -> {
                    commandModel.getArgumentList().add(model);
                },
                () -> {
                    System.err.println("Error finding model for command with id: " + commandID.toString());
                });

        controller.setUp(node, model);
    }

    // endregion

    // region Getters and Setters

    public static void setAddedArg(Models.Argument addedArg) {
        Argument.addedArg = addedArg;
    }

    // endregion

}
