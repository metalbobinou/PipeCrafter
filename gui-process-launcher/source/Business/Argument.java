package Business;

import Models.Argument.Type;
import Utils.OutputParameters;
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
        model.setArgumentView(controller);
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

    /**
     * Verify that all commands within the provided range do not reference
     * commands that will be executed after them.
     * Display a warning if needed.
     */
    public static void checkOutputsOrder() {
        boolean broke = false;
        for (Models.Command cmd : Command.getCommands()) {
            for (Models.Argument arg : cmd.getArgumentList()) {

                if (arg.getType() == Type.OUTPUT) {
                    int referencedPosition = ((OutputParameters) arg.getObjectValue()).getCmdToUse().getPosition();
                    if (referencedPosition >= cmd.getPosition()) {
                        broke = true;
                        arg.setArgument(Type.INVALID, arg.getObjectValue());
                    }
                    arg.getArgumentView().refresh();
                }
            }
        }
        if (broke) {
            Utils.Alerts.getInvalidCommandRefAlert().show();
        }
    }

    /**
     * Check the arguments of a command and say if any is invalid
     * 
     * @param cmd the command which arguments should be checked
     * @return true if any argument is invalid, false if none is
     */
    public static boolean cmdHasInvalidArg(Models.Command cmd) {
        for (Models.Argument arg : cmd.getArgumentList()) {
            if (arg.getType() == Type.INVALID) {
                return true;
            }
        }
        return false;
    }

    // endregion

    // region Getters and Setters

    public static void setAddedArg(Models.Argument addedArg) {
        Argument.addedArg = addedArg;
    }

    // endregion

}
