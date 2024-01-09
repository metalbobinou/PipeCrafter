package Business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Execution.ProcessManager;
import Models.Command.State;
import Utils.Alerts;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

/** Business class for commands */
public class Command {

    // #region Attributes

    /** List of all commands */
    private static final List<Models.Command> commands = new ArrayList<>();

    /**
     * Inform on the position of the command to which the user wants to add an
     * argument
     */
    private static Models.Command commandReiceivingArgument = null;

    // #endregion

    // #region Methods

    /**
     * Add a command to the list and set up its controller
     * 
     * @param controller controller for which to create a command model
     * @param cmdModel   the command to add, null if it must be created
     */
    public static void addCommand(Views.Command controller, Models.Command cmdModel) {

        if (cmdModel == null) {
            cmdModel = new Models.Command();
        }
        cmdModel.setPosition(commands.size() + 1);
        cmdModel.setCmdView(controller);
        cmdModel.setState(commands.size() == 0 ? State.NEXT_TO_RUN : State.TO_RUN);

        commands.add(cmdModel);
        controller.setUp(cmdModel);
    }

    /**
     * Interprate the the appropriate action to trigger when the "run" button
     * of a command is pressed based on the app and command's context
     * 
     * @param command command from which the "run" button was clicked
     */
    public static void decideExec(Models.Command command) {
        Models.Command.State initialState = command.getState();

        if (Business.App.isExecuting()) {

            if (Alerts.getExecutionOnGoingAlert().showAndWait().orElse(null) == ButtonType.YES) {

                ProcessManager.kill();
            } else {
                return;
            }
        }

        switch (initialState) {
            case RUNNING:
                // Process was just stopped
                return;
            case NEXT_TO_RUN:
                // Nothing to do prior to run
                break;

            case SKIPPED:
            case ALREADY_RUN:
                if (Alerts.getConfirmRestartFromAlert().showAndWait().orElse(null) != ButtonType.YES) {
                    return;
                }
                resetAll(command.getIndex(), App.getCurrentStep());
                App.restartFrom(command.getPosition());
                break;

            case TO_RUN:
                // Check if any command at or after the one to jump to
                // references a command that is going to be skipped
                for (int i = App.getCurrentCommandIndex(); i < command.getIndex(); i++) {

                    for (Models.Argument arg : commands.get(i).getReferringArgumentList()) {

                        if (arg.getMotherCommand().getPosition() >= command.getPosition()) {

                            if (Alerts.getForbiddenSkipAlert().showAndWait().orElse(null) == ButtonType.YES) {

                                i = command.getIndex(); // force 1st loop exit
                                break;
                            } else {
                                return;
                            }
                        }
                    }
                }
                skipAll(App.getCurrentCommandIndex(), command.getIndex());
                App.restartFrom(command.getPosition());
                break;
        }

        run(command);
    }

    /**
     * Launch the execution of a command
     * 
     * @param command the command to launch execution for
     */
    private static void run(Models.Command command) {
        if (Argument.cmdHasInvalidArg(command)) {
            Utils.Alerts.getForbiddenRunAlert().showAndWait();
            return;
        }
        command.updateState(State.RUNNING);
        Business.App.setRun(command);
        Thread thread = new Thread(() -> {
            Execution.ProcessManager.execute(command);

            // Post execution updates:
            if (Business.App.getAndResetExecWasInterrupted()) {
                command.updateState(State.NEXT_TO_RUN);
                Business.App.endRun(false, false);

            } else {
                // For executed command
                command.updateState(State.ALREADY_RUN);

                // For next command in line
                boolean isOver = commands.size() == Business.App.getCurrentStep();
                if (!isOver) {
                    commands.get(Business.App.getCurrentCommandIndex() + 1).updateState(State.NEXT_TO_RUN);
                }

                Business.App.endRun(isOver, true);
            }
        });
        thread.start();
    }

    /**
     * Reset all commands in the given range to their default state
     * and update the UI
     * 
     * @param from index at which the reset must begin
     * @param to   index at which the reset must end
     */
    public static void resetAll(int from, int to) {
        if (commands.size() < 1) {
            return;
        }
        commands.get(from).updateState(State.NEXT_TO_RUN);

        for (int i = ++from; i < to; i++) {
            commands.get(i).updateState(State.TO_RUN);
        }
    }

    /**
     * Adapt state and position of commands following a deletion
     * 
     * @param from index of the deleted command
     */
    public static void resetForDelete(int from) {
        if (commands.size() <= from) {
            return;
        }
        Models.Command cmd = commands.get(from);
        if (App.getCurrentCommandIndex() == from) {
            cmd.updateState(State.NEXT_TO_RUN);
        }
        cmd.updatePosition(from + 1);

        for (int i = ++from; i < commands.size(); i++) {
            cmd = commands.get(i);
            cmd.updateState(State.TO_RUN);
            cmd.updatePosition(i + 1);
        }
    }

    /**
     * Skip all commands within the given range
     * 
     * @param from index at which the skip must begin
     * @param to   index at which the skip must end (excluded)
     */
    public static void skipAll(int from, int to) {

        for (int i = from; i < to; i++) {
            commands.get(i).updateState(State.SKIPPED);
        }

        commands.get(to).updateState(State.NEXT_TO_RUN);
    }

    /**
     * Rotate a command, update the UI accordingly and check for incoherences
     * 
     * @param parent      Pane where the drag and drop is happening
     * @param sourceIndex index of the object being dragged
     * @param targetIndex index where the object is being dropped
     */
    public static void rotate(Pane parent, int sourceIndex, int targetIndex) {
        Models.Command sourceCmd = commands.get(sourceIndex);
        Models.Command targetCmd = commands.get(targetIndex);

        // Prevent change if target or source command is not (next) to run
        if (sourceCmd.getState() != State.TO_RUN && targetCmd.getState() != State.NEXT_TO_RUN
                && sourceCmd.getState() != State.NEXT_TO_RUN && targetCmd.getState() != State.TO_RUN) {
            Utils.Alerts.getRestrictedEditAlert().showAndWait();
            return;
        }

        List<Node> nodes = new ArrayList<Node>(parent.getChildren());

        int start, end;

        if (sourceIndex < targetIndex) {
            Collections.rotate(
                    nodes.subList(sourceIndex, targetIndex + 1), -1);
            Collections.rotate(
                    commands.subList(sourceIndex, targetIndex + 1), -1);

            start = sourceIndex;
            end = targetIndex;
        } else {
            Collections.rotate(
                    nodes.subList(targetIndex, sourceIndex + 1), 1);
            Collections.rotate(
                    commands.subList(targetIndex, sourceIndex + 1), 1);
            start = targetIndex;
            end = sourceIndex;
        }

        if (sourceCmd.getState() == State.NEXT_TO_RUN) {
            commands.get(App.getCurrentCommandIndex()).updateState(State.NEXT_TO_RUN);
            sourceCmd.updateState(State.TO_RUN);
        } else if (targetCmd.getState() == State.NEXT_TO_RUN) {
            sourceCmd.updateState(State.NEXT_TO_RUN);
            targetCmd.updateState(State.TO_RUN);
        }

        for (int i = start; i <= end; i++) {
            commands.get(i).updatePosition(i + 1);
        }

        Argument.updateCmdRefsOnMove(sourceCmd);

        parent.getChildren().clear();
        parent.getChildren().addAll(nodes);
    }

    /**
     * Delete a command
     * 
     * @param cmd  command model to delete
     * @param node node of the command to delete
     */
    public static void deleteCommand(Models.Command cmd, Node node) {

        cmd.makeReferringArgsInvalid();
        for (int i = 0; i < cmd.getArgumentList().size();) {
            Argument.deleteArgument(cmd.getArgumentList().get(i));
        }
        commands.remove(cmd);
        App.getMainController().deleteCmd(node);
        resetForDelete(cmd.getIndex());
    }

    /** Delete all commands */
    public static void deleteAllCmd() {
        for (int i = 0; i < commands.size();) {
            Models.Command command = commands.get(i);
            deleteCommand(command, command.getCmdView().getCommandNode());
        }
    }

    // #endregion

    // #region Getters and Setters

    public static List<Models.Command> getCommands() {
        return commands;
    }

    public static Models.Command getCommandReiceivingArgument() {
        return commandReiceivingArgument;
    }

    public static void setCommandReiceivingArgument(Models.Command command) {
        Command.commandReiceivingArgument = command;
    }

    // #endregion
}
