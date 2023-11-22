package Business;

import java.util.ArrayList;
import java.util.List;

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
        Models.Command model = new Models.Command(commands.size() + 1);
        controller.setUp(node, model);
        commands.add(model);
    }

    // endregion
}
