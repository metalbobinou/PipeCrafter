package Utils;

import java.io.File;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Business.Settings.Shell;
import Utils.OutputParameters.OutputStream;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/** Class providing utils methods generally */
public class Utils {

    // #region Attributes

    /** A single refrence to a file chooser */
    private static FileChooser fc = null;

    /** The extension filter used to filter json files */
    private static final ExtensionFilter ef = new ExtensionFilter("JSON", "*.json");

    /** A single refrence to a directory chooser */
    private static DirectoryChooser dc = null;

    /** The Gson object used to save and access data saved as JSON */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Save.class, new Adapters.SaveAdapter())
            .registerTypeAdapter(Models.Command.class, new Adapters.CommandAdapter())
            .registerTypeAdapter(Models.Argument.class, new Adapters.ArgumentAdapter())
            .registerTypeAdapter(OutputParameters.class, new Adapters.OutputParametersAdapter())
            .registerTypeAdapter(Tuple.class, new Adapters.TupleAdapter())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    // #endregion

    // #region Methods

    /**
     * Build and return the path to a saved output
     * 
     * @param step   step for which to retreive the output
     * @param stream type of the saved stream to gather
     * @return string representation of the path
     */
    public static String getPathForOutputOfStep(int step, OutputStream stream) {
        return Paths.get(Business.Settings.getOutputSavingDirectory().toString(), step + stream.getExtension())
                .toString();
    }

    /** Init the single reference to the file chooser if needed */
    public static void initFcAndDc() {
        fc = new FileChooser();
        fc.setInitialFileName("gui_config.json");

        dc = new DirectoryChooser();
        dc.setInitialDirectory(Business.Settings.getExecutionDirectory());
    }

    /**
     * Return the file's path without its extension
     * 
     * @param file file which name wants to be extracted
     * @return the file's path without its extension as a String
     */
    public static String getFilePathNoExtension(File file) {
        String filePath = file.getAbsolutePath();
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filePath.length()) {
            return filePath.substring(0, dotIndex);
        } else {
            return filePath;
        }
    }

    /**
     * Getter for the fileChooser
     * 
     * @param directoryToOpeOn directory to which set the initial directory,
     *                         null if it should be the executionDirectory
     * @return fc with the initial directory set
     */
    public static FileChooser getFc(File directoryToOpeOn) {
        if (directoryToOpeOn == null) {
            fc.setInitialDirectory(Business.Settings.getExecutionDirectory());
        } else {
            fc.setInitialDirectory(directoryToOpeOn);
        }

        fc.setSelectedExtensionFilter(null);
        return fc;
    }

    /**
     * Reset the app to its default state, resetting static fields
     */
    public static void resetApp() {
        Save.setLastUsedSaveFile(null);
        Save.setStateSaveFile(null);

        Business.App.setExecWasInterrupted(false);
        Business.App.setMainController(null);
        Business.App.setMainNode(null);

        Business.Argument.setAddedArg(null);

        Business.Command.setCommandReiceivingArgument(null);
        Business.Command.getCommands().clear();

        Business.Settings.setUsedShell(Shell.NONE);
        Business.Settings.setExecutionDirectory(new File(System.getProperty("user.dir")));
        Business.Settings.setOutputSavingDirectory(new File(System.getProperty("user.dir")));
    }

    // #endregion

    // #region Getters and Setters

    public static FileChooser getFcWithFilter() {
        File directory = Save.getLastUsedSaveFile();
        if (directory == null) {
            directory = Business.Settings.getExecutionDirectory();
        } else {
            directory = directory.getParentFile();
        }
        fc.setInitialDirectory(directory);
        fc.setSelectedExtensionFilter(ef);
        return fc;
    }

    public static void setFc(FileChooser fc) {
        Utils.fc = fc;
    }

    public static DirectoryChooser getDc() {
        return dc;
    }

    public static void setDc(DirectoryChooser dc) {
        Utils.dc = dc;
    }

    public static Gson getGson() {
        return gson;
    }

    // #endregion
}
