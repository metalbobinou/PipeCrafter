package Utils;

import java.nio.file.Paths;

import javafx.stage.FileChooser;

/** Class providing utils methods generally */
public class Utils {

    // region Attributes

    private static FileChooser fc = null;

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

    public static void initFC() {
        if (fc == null) {
            fc = new FileChooser();
            fc.setInitialDirectory(Business.Settings.getExecutionDirectory());
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

    // endregion
}
