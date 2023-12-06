package Business;

import java.util.ArrayList;
import java.util.List;

import Execution.ProcessManager;
import Models.Command.State;
import Utils.Alerts;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;

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

            if (Alerts.getExecutionOnGoingAlert().showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {

                ProcessManager.kill();
            }
        }

        switch (initialState) {
            case ALREADY_RUN:
                // TODO restart prompt (ask if needed) then call run
                break;
            case NEXT_TO_RUN:
                run(command);
                break;
            case RUNNING:
                // Process was just stopped
                break;
            case TO_RUN:
                // TODO check if dependancies okay
                break;
        }
    }

    /**
     * Launch the execution of a command
     * 
     * @param command the command to launch execution for
     */
    private static void run(Models.Command command) {
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

    /** Reset all commands to their default state and update the UI */
    public static void resetAll() {
        if (commands.size() < 1) {
            return;
        }
        commands.get(0).updateState(State.NEXT_TO_RUN);

        for (int i = 1; i < commands.size(); i++) {
            commands.get(i).updateState(State.TO_RUN);
        }
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
