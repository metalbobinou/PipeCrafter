package Utils;

import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/** Class defining the saving model and handling saving of data to JSON */
public class Save {

    // region Nested Types

    /** The model used to save state related data */
    public static class State {
        public int currentCmdIndex;
        public boolean isOver;
        public List<Models.Command.State> states;

        public State() {
            this.currentCmdIndex = Business.App.getCurrentCommandIndex();

            this.isOver = Business.App.isOver();

            this.states = new ArrayList<>();
            for (Models.Command cmd : Business.Command.getCommands()) {
                states.add(cmd.getState());
            }
        }
    }

    // endregion

    // region Attributes

    private static File stateSaveFile = null;

    /** Path to the execution directory */
    public String executionDirectoryPath;

    /** Path to the directory used to save outputs */
    public String outputSavingDirectoryPath;

    /** The setting about which shell to use */
    public Business.Settings.Shell usedShell;

    /** The list of commands built */
    public List<Models.Command> commands;

    // endregion

    // region Constructors

    public Save() {
        this.executionDirectoryPath = Business.Settings.getExecutionDirectory().getAbsolutePath();
        this.outputSavingDirectoryPath = Business.Settings.getOutputSavingDirectory().getAbsolutePath();
        this.usedShell = Business.Settings.getUsedShell();
        this.commands = Business.Command.getCommands();
    }

    // endregion

    // region Methods

    /**
     * Save the current configuration, pipeline and its state in the selected
     * file
     */
    public static void save() {
        File file = Utils.getFcWithFilter().showSaveDialog(new Stage());

        if (file == null) {
            return;
        }
        try {
            try (FileWriter fileWriter = new FileWriter(file)) {
                Utils.getGson().toJson(new Save(), fileWriter);
            }

            stateSaveFile = new File(Utils.getFilePathNoExtension(file) + ".state.json");
            try (FileWriter fileWriter = new FileWriter(stateSaveFile)) {
                Utils.getGson().toJson(new State(), fileWriter);
            }

        } catch (Exception e) {
            Alerts.getErrorSavingPipelineAlert().show();
            e.printStackTrace();
        }

    }

    // endregion

    // region Getters And Setters

    public static File getStateSaveFile() {
        return stateSaveFile;
    }

    public static void setStateSaveFile(File stateSaveFile) {
        Save.stateSaveFile = stateSaveFile;
    }

    // endregion

}
