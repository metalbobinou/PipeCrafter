package Business;

import java.util.ArrayList;
import java.util.List;
import Models.Command.State;
import javafx.scene.Node;

/** Business class for commands */
public class Command {

    // region Attributes

    /** List of all commands */
    private static List<Models.Command> commands = new ArrayList<>();

    /**
     * Inform on the position of the command to which the user wants to add an
     * argument
     */
    private static int commandReiceivingArgument = -1;

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
        if (Business.App.isExecuting()) {
            // TODO send prompt "Step X is being executed. Proceeding will abort it. Do you
            // want to continue?". If command.getState() == RUNNING then return (stop
            // called)
        }

        // If continue:
        switch (command.getState()) {
            case ALREADY_RUN:
                // TODO restart prompt (ask if needed) then call run
                break;
            case NEXT_TO_RUN:
                run(command);
                break;
            case RUNNING:
                // error
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
        command.setState(State.RUNNING);
        Business.App.setRun(command);
        Thread thread = new Thread(() -> {
            Execution.ProcessManager.execute(command);

            // Post execution updates:
            // For executed command
            command.setState(State.ALREADY_RUN);
            command.getCmdView().updateState();

            // For next command in line
            boolean isOver = commands.size() == Business.App.getCurrentStep();
            if (!isOver) {
                Models.Command nextCmd = commands.get(Business.App.getCurrentStep());
                nextCmd.setState(State.NEXT_TO_RUN);
                nextCmd.getCmdView().updateState();
            }

            // App variables
            Business.App.endRun(isOver);
        });
        thread.start();
    }

    /** Reset all commands to their default state and update the UI */
    public static void resetAll() {
        if (commands.size() < 1) {
            return;
        }
        commands.get(0).setState(State.NEXT_TO_RUN);
        commands.get(0).getCmdView().updateState();

        for (int i = 1; i < commands.size(); i++) {
            commands.get(i).setState(State.TO_RUN);
            commands.get(i).getCmdView().updateState();
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

    public static int getCommandReiceivingArgument() {
        return commandReiceivingArgument;
    }

    public static void setCommandReiceivingArgument(int commandReiceivingArgument) {
        Command.commandReiceivingArgument = commandReiceivingArgument;
    }

    // endregion
}
