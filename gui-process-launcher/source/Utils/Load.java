package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/** Class handling loading of data from JSON */
public class Load {

    // region Methods

    /** Load a save file */
    public static void load(File file) {

        Business.App.resetAll();

        try {
            loadPipeline(file);
        } catch (Exception e) {
            Alerts.getErrorLoadingPipelineAlert().show();
            e.printStackTrace();
            return;
        }

        if (Alerts.getLoadStatePromptAlert().showAndWait().orElse(null) != ButtonType.YES) {
            return;
        }

        file = Utils.getFcWithFilter().showOpenDialog(new Stage());
        if (file == null) {
            return;
        }

        try {
            loadState(file);
        } catch (Exception e) {
            Alerts.getErrorLoadingStateAlert().show();
            e.printStackTrace();
        }
    }

    /**
     * Load a pipeline file
     * 
     * @param file file to load
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void loadPipeline(File file) throws FileNotFoundException, IOException {
        try (FileReader fileReader = new FileReader(file)) {
            Save save = Utils.getGson().fromJson(fileReader, Save.class);
            Business.Settings.setExecutionDirectory(save.executionDirectoryPath);
            Business.Settings.setOutputSavingDirectory(save.outputSavingDirectoryPath);
            Business.Settings.setUsedShell(save.usedShell);
        }
    }

    /**
     * Load a state file
     * 
     * @param file file to load
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void loadState(File file) throws FileNotFoundException, IOException {
        try (FileReader fileReader = new FileReader(file)) {
            Save.State state = Utils.getGson().fromJson(fileReader, Save.State.class);

            Business.App.setState(state);

            for (int i = 0; i < state.states.size(); i++) {
                Tuple<Models.Command.State, Integer> tuple = state.states.get(i);
                Models.Command cmd = Business.Command.getCommands().get(i);
                cmd.setExitCode(tuple.second);
                cmd.updateState(tuple.first);
            }
        }
    }

    // endregion
}
