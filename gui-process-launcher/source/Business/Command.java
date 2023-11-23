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
                commands.size() == 0 ? State.NEXT_TO_RUN : State.TO_RUN);
        controller.setUp(node, model);
        commands.add(model);
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
        Execution.ProcessManager.execute(command);
    }

    // endregion
}
