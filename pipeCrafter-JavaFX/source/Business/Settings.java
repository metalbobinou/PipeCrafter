package Business;

import java.io.File;

import Execution.ProcessManager;

/** Business class for app settings */
public class Settings {

    // #region Nested types

    public enum Shell {
        NONE,
        BASH,
        CMD,
        POWERSHELL,
        WSL,
    }
    // #endregion

    // #region Attributes

    /** Represents the directory where commands should be executed */
    private static File executionDirectory = new File(System.getProperty("user.dir"));

    /** Represents the directory where output files should be saved */
    private static File outputSavingDirectory = new File(System.getProperty("user.dir"));

    /** Shell to use during execution */
    private static Shell usedShell = Shell.NONE;

    // #endregion

    // #region Getters and Setters

    public static File getExecutionDirectory() {
        return executionDirectory;
    }

    public static void setExecutionDirectory(File executionDirectory) {
        if (executionDirectory == null | !executionDirectory.isDirectory() | !executionDirectory.canWrite()) {
            throw new IllegalArgumentException(
                    "The specified execution directory is not specified, is not a directory or is not writable.");
        }
        Settings.executionDirectory = executionDirectory;
        ProcessManager.setExecution();
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

    // #endregion
}
