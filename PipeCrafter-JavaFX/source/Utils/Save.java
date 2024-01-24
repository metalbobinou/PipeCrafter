package Utils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/** Class defining the saving model and handling saving of data to JSON */
public class Save {

    // #region Nested Types

    /** The model used to save state related data */
    public static class State {
        public int currentCmdIndex;
        public boolean isOver;
        public boolean editModeOn;
        public ArrayList<Tuple<Models.Command.State, Integer>> states;

        public State() {
            this.currentCmdIndex = Business.App.getCurrentCommandIndex();
            this.isOver = Business.App.isOver();
            this.editModeOn = Business.App.isEditModeOn();

            this.states = new ArrayList<>();
            for (Models.Command cmd : Business.Command.getCommands()) {
                Models.Command.State state = cmd.getState();
                if (state == Models.Command.State.RUNNING) {
                    state = Models.Command.State.NEXT_TO_RUN;
                }
                states.add(new Tuple<>(state, cmd.getExitCode()));
            }
        }
    }

    // #endregion

    // #region Attributes

    /** The file used to save states */
    private static File stateSaveFile = null;

    /** The last file used to save a configuration */
    private static File lastUsedSaveFile = null;

    /** Path to the execution directory */
    public String executionDirectoryPath;

    /** Path to the directory used to save outputs */
    public String outputSavingDirectoryPath;

    /** The setting about which shell to use */
    public Business.Settings.Shell usedShell;

    /** The list of commands built */
    public List<Models.Command> commands;

    // #endregion

    // #region Constructors

    public Save() {
        this.executionDirectoryPath = Utils.cwd.relativize(Business.Settings.getExecutionDirectory().toPath())
                .toString();
        this.outputSavingDirectoryPath = Utils.cwd.relativize(Business.Settings.getOutputSavingDirectory().toPath())
                .toString();
        this.usedShell = Business.Settings.getUsedShell();
        this.commands = Business.Command.getCommands();
    }

    public Save(Object smth) {
        // Constructor used when loading a save and setting all fields by hand
    }

    // #endregion

    // #region Methods

    /**
     * Save the current configuration, pipeline and its state in the selected
     * file
     * 
     * @param file the file to use as a save file, null if lastUsedSaveFile
     *             should be used
     */
    public static void save(File file) {
        if (file != null) {
            lastUsedSaveFile = file;
        }

        if (lastUsedSaveFile == null) {
            return;
        }
        try {
            try (FileWriter fileWriter = new FileWriter(lastUsedSaveFile)) {
                Utils.getGson().toJson(new Save(), fileWriter);
            }

            // If not null, stateSaveFile has to be changed to the match new
            // provided file
            if (file != null) {
                stateSaveFile = new File(Utils.getFilePathNoExtension(lastUsedSaveFile) + ".state.json");
            }
            try (FileWriter fileWriter = new FileWriter(stateSaveFile)) {
                Utils.getGson().toJson(new State(), fileWriter);
            }

        } catch (Exception e) {
            Alerts.getErrorSavingPipelineAlert().show();
            e.printStackTrace();
        }

    }

    /** Update the state save file */
    public static void updateState() {
        if (stateSaveFile == null) {
            return;
        }
        try {
            try (FileWriter fileWriter = new FileWriter(stateSaveFile)) {
                Utils.getGson().toJson(new State(), fileWriter);
            }
        } catch (Exception e) {
            Alerts.getErrorSavingStateAlert().show();
        }
    }

    // #endregion

    // #region Getters And Setters

    public static File getStateSaveFile() {
        return stateSaveFile;
    }

    public static void setStateSaveFile(File stateSaveFile) {
        Save.stateSaveFile = stateSaveFile;
    }

    public static File getLastUsedSaveFile() {
        return lastUsedSaveFile;
    }

    public static void setLastUsedSaveFile(File lastUsedSaveFile) {
        Save.lastUsedSaveFile = lastUsedSaveFile;
    }

    // #endregion

}
