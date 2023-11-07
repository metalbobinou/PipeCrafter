package fr.epita.gpl;

import java.io.File;

/** Handle all app settings */
public class Settings {

    public enum Shell {
        NONE,
        BASH,
        CMD,
        POWERSHELL,
        WSL,
    }

    /** Represents the directory where commands should be executed */
    private static File executionDirectory = null;

    /** Represents the directory where output files should be saved */
    private static File outputSavingDirectory = null;

    /** Shell to use during execution */
    private static Shell usedShell = Shell.NONE;

    // -- Getters and setters
    public static File getExecutionDirectory() {
        return executionDirectory;
    }

    public static void setExecutionDirectory(File executionDirectory) {
        if (executionDirectory == null | !executionDirectory.isDirectory() | !executionDirectory.canWrite()) {
            throw new IllegalArgumentException(
                    "The specified execution directory is not specified, is not a directory or is not writable.");
        }
        Settings.executionDirectory = executionDirectory;
    }

    public static void setExecutionDirectory(String executionDirectoryPath) {
        setExecutionDirectory(new File(executionDirectoryPath));
    }

    public static File getOutputSavingDirectory() {
        return outputSavingDirectory;
    }

    public static void setOutputSavingDirectory(File outputSavingDirectory) {
        if (outputSavingDirectory == null | !outputSavingDirectory.isDirectory() | !outputSavingDirectory.canWrite()) {
            throw new IllegalArgumentException(
                    "The specified saving directory is not specified, is not a directory or is not writable.");
        }
        Settings.outputSavingDirectory = outputSavingDirectory;
    }

    public static void setOutputSavingDirectory(String outputSavingDirectoryPath) {
        setOutputSavingDirectory(new File(outputSavingDirectoryPath));
    }

    public static Shell getUsedShell() {
        return usedShell;
    }

    public static void setUsedShell(Shell usedShell) {
        Settings.usedShell = usedShell;
    }
}
