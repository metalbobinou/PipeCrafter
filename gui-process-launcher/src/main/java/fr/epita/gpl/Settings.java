package fr.epita.gpl;

import java.io.File;

/**
 * Class responsible of all app settings.
 */
public class Settings {

    /**
     * File object representing the directory where commands should be executed.
     */
    private static File executionDirectory = null;

    /**
     * File object representing the directory where output files should be created
     * and stored.
     */
    private static File outputSavingDirectory = null;

    // -- Getters and setters
    public static File getExecutionDirectory() {
        return executionDirectory;
    }

    public static void setExecutionDirectory(File executionDirectory) {
        Settings.executionDirectory = executionDirectory;
    }

    public static void setExecutionDirectory(String executionDirectoryPath) {
        Settings.executionDirectory = new File(executionDirectoryPath);
    }

    public static File getOutputSavingDirectory() {
        return outputSavingDirectory;
    }

    public static void setOutputSavingDirectory(File outputSavingDirectory) {
        Settings.outputSavingDirectory = outputSavingDirectory;
    }

    public static void setOutputSavingDirectory(String outputSavingDirectoryPath) {
        Settings.outputSavingDirectory = new File(outputSavingDirectoryPath);
    }

}
