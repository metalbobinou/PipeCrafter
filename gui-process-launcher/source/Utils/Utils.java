package Utils;

import java.nio.file.Paths;

import Utils.OutputParameters.OutputStream;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/** Class providing utils methods generally */
public class Utils {

    // region Attributes

    /** A single refrence to a file chooser */
    private static FileChooser fc = null;

    /** A single refrence to a directory chooser */
    private static DirectoryChooser dc = null;

    // endregion

    // region Methods

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
        if (fc == null) {
            fc = new FileChooser();
            fc.setInitialDirectory(Business.Settings.getExecutionDirectory());
        }
        if (dc == null) {
            dc = new DirectoryChooser();
            dc.setInitialDirectory(Business.Settings.getExecutionDirectory());
        }
    }

    // endregion

    // region Getters and Setters

    public static FileChooser getFc() {
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

    // endregion
}
