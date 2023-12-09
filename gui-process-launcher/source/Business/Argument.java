package Business;

import Models.Argument.Type;
import Models.Command.State;
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
        if (arg.getType() == Type.OUTPUT) {
            arg.getOutputParameter().getCmdToUse().getReferringArgumentList().remove(arg);
        }

        Models.Command cmd = arg.getMotherCommand();

        cmd.getArgumentList().remove(arg);
        cmd.getCmdView().deleteArg(node);
    }

    /**
     * Update all the arguments referring to the given command after it was
     * moved
     * 
     * @param cmd the moved command
     */
    public static void updateCmdRefsOnMove(Models.Command cmd) {
        boolean broke = false;

        // Check arguments referring to the moved command
        for (Models.Argument argReferringToCmd : cmd.getReferringArgumentList()) {

            // Currently is a valid reference to a command
            if (argReferringToCmd.getType() == Type.OUTPUT) {
                // Check if ref was broken by move
                if (argReferringToCmd.getMotherCommand().getPosition() <= cmd.getPosition()) {
                    broke = true;
                    argReferringToCmd.makeInvalid();
                }
                // Currently is an invalid reference to an existing command
            } else if (argReferringToCmd.getType() == Type.INVALID
                    && argReferringToCmd.getObjectValue() instanceof OutputParameters) {

                // Check if ref was fixed by move
                if (argReferringToCmd.getMotherCommand().getPosition() > cmd.getPosition()) {
                    argReferringToCmd.makeValidOutput();
                }
            }
            argReferringToCmd.getArgumentView().refresh();
        }

        // Check arguments the moved command refers to
        for (Models.Argument arg : cmd.getArgumentList()) {

            // Currently is a valid reference to a command
            if (arg.getType() == Type.OUTPUT) {
                // Check if ref was broken by move
                if (arg.getOutputParameter().getCmdToUse().getPosition() >= cmd.getPosition()) {
                    broke = true;
                    arg.makeInvalid();
                }

                // Currently is an invalid reference to an existing command
            } else if (arg.getType() == Type.INVALID
                    && arg.getObjectValue() instanceof OutputParameters) {

                // Check if ref was fixed by move
                if (arg.getOutputParameter().getCmdToUse().getPosition() < cmd.getPosition()) {
                    arg.makeValidOutput();
                }
            }
            arg.getArgumentView().refresh();
        }

        if (broke) {
            Utils.Alerts.getInvalidCommandRefAlert().show();
        }
    }

    /**
     * Check the arguments of a command and say if any is invalid or skipped
     * 
     * @param cmd the command which arguments should be checked
     * @return true if any argument is invalid/skipped, false if none is
     */
    public static boolean cmdHasInvalidArg(Models.Command cmd) {
        for (Models.Argument arg : cmd.getArgumentList()) {
            if (arg.getType() == Type.INVALID
                    || (arg.getType() == Type.OUTPUT
                            && arg.getOutputParameter().getCmdToUse().getState() == State.SKIPPED)) {
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
