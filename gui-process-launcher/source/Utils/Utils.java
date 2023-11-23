package Utils;

import java.nio.file.Paths;

/** Class providing utils methods generally */
public class Utils {

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

    // endregion
}
