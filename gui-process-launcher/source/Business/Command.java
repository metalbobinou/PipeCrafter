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

    // region Attributes

    /** List of all commands */
    private static List<Models.Command> commands = new ArrayList<>();

    /**
     * Inform on the position of the command to which the user wants to add an
     * argument
     */
    private static Models.Command commandReiceivingArgument = null;

    // endregion

    // region Methods

    /**
     * Add a command to the list and set up its controller
     * 
     * @param controller controller for which to create a command model
     * @param node       FX object representing the command
     */
    public static void addCommand(Views.Command controller, Node node) {

        Models.Command model = new Models.Command(commands.size() + 1,
                commands.size() == 0 ? State.NEXT_TO_RUN : State.TO_RUN, controller);
        commands.add(model);
        controller.setUp(node, model);
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

            case ALREADY_RUN:
                if (Alerts.getConfirmRestartFromAlert().showAndWait().orElse(null) != ButtonType.YES) {
                    return;
                }
                resetAll(command.getPosition() - 1, App.getCurrentStep());
                App.restartFrom(command.getPosition());
                break;

            case TO_RUN:
                // TODO check if dependancies okay
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
     * Rotate a command, update the UI accordingly and check for incoherences
     * 
     * @param parent      Pane where the drag and drop is happening
     * @param sourceIndex index of the object being dragged
     * @param targetIndex index where the object is being dropped
     */
    public static void rotate(Pane parent, int sourceIndex, int targetIndex) {
        Models.Command sourceCmd = commands.get(sourceIndex);
        Models.Command targetCmd = commands.get(targetIndex);

        // Prevent change if target or source command is running or already run
        if (sourceCmd.getState() == State.ALREADY_RUN || targetCmd.getState() == State.ALREADY_RUN
                || sourceCmd.getState() == State.RUNNING || targetCmd.getState() == State.RUNNING) {
            Utils.Alerts.getRestrictedEditAlert().showAndWait();
            return;
        }

        if (sourceCmd.getState() == State.NEXT_TO_RUN) {
            targetCmd.updateState(State.NEXT_TO_RUN);
            sourceCmd.updateState(State.TO_RUN);
        } else if (targetCmd.getState() == State.NEXT_TO_RUN) {
            sourceCmd.updateState(State.NEXT_TO_RUN);
            targetCmd.updateState(State.TO_RUN);
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

        for (int i = start; i <= end; i++) {
            Models.Command cmd = commands.get(i);
            cmd.updatePosition(i + 1);
        }

        Argument.checkOutputsOrder();

        parent.getChildren().clear();
        parent.getChildren().addAll(nodes);
    }

    // endregion

    // region Getters and Setters

    public static List<Models.Command> getCommands() {
        return commands;
    }

    public static void setCommands(List<Models.Command> commands) {
        Command.commands = commands;
    }

    public static Models.Command getCommandReiceivingArgument() {
        return commandReiceivingArgument;
    }

    public static void setCommandReiceivingArgument(Models.Command command) {
        Command.commandReiceivingArgument = command;
    }

    // endregion
}
