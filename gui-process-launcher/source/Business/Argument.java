package Business;

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

    /**
     * Add an argument to a command
     * 
     * @param commandModel model of the command receiving the argument
     * @param controller   View relating to the command being added
     * @param node         node of the argument being added
     */
    public static void addArgument(Models.Command commandModel, Views.Argument controller, Node node) {
        Models.Argument model = popAddedArg();

        model.setMotherCommand(commandModel);
        commandModel.getArgumentList().add(model);
        controller.setUp(node, model);
    }

    /**
     * Delete an argument from a command
     * 
     * @param arg  argument model to delete
     * @param node node of the argument to delete
     */
    public static void deleteArgument(Models.Argument arg, Node node) {
        Models.Command cmd = arg.getMotherCommand();
        cmd.getArgumentList().remove(arg);
        cmd.getCmdView().deleteArg(node);
    }

    // endregion

    // region Getters and Setters

    public static void setAddedArg(Models.Argument addedArg) {
        Argument.addedArg = addedArg;
    }

    // endregion

}
