package Execution;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Execution.Utils.CommandBuilder;
import Utils.OutputStream;
import Utils.Utils;

/** Handle a unique process object for all executions */
public class ProcessManager {

    // region Attributes

    /** The unique ProcessBuilder reference */
    private static ProcessBuilder processBuilder = null;

    // endregion

    // region Methods

    /** Initialize the unique ProcessBuilder reference and its directory */
    public static void init() {
        if (processBuilder != null) {
            throw new IllegalArgumentException("The ProcessBuilder is already initialized.");
        }
        processBuilder = new ProcessBuilder();
        setExecution();
    }

    /** Check and set the execution directory for the ProcessBuilder */
    public static void setExecution() {
        if (Business.Settings.getExecutionDirectory() == null) {
            throw new IllegalArgumentException(
                    "Tried to init a process builder with an unspecified execution directory");
        }
        processBuilder.directory(Business.Settings.getExecutionDirectory());
    }

    /**
     * Check and set the files used to save the streams
     * 
     * @param step the number of the step to execute determining the files' name
     */
    private static void setRedirection(int step) {

        File stdOutsDestination = new File(Business.Settings.getOutputSavingDirectory(), step + ".out");
        File stdErrsDestination = new File(Business.Settings.getOutputSavingDirectory(), step + ".err");

        // Settings setters guarantee that the directories exist and can be written to
        // Check that if files already exist, they can still be written to
        if ((stdOutsDestination.exists() && !stdOutsDestination.canWrite())
                | (stdErrsDestination.exists() && !stdErrsDestination.canWrite())) {
            throw new IllegalArgumentException("The file(s) used to save the output cannot be accessed.");
        }

        processBuilder.redirectOutput(stdOutsDestination);
        processBuilder.redirectError(stdErrsDestination);
    }

    /**
     * Set the ProcessBuilder for the specified command and execute it
     * 
     * @param command command to execute
     * @return the returned code or null if an execution error occured
     */
    public static void execute(Models.Command command) {

        List<String> cmdList = CommandBuilder.getCommand(command);
        setRedirection(command.getPosition());
        processBuilder.command(cmdList);
        try {
            Process process = processBuilder.start();
            command.setExitCode(process.waitFor());

        } catch (IOException | InterruptedException e) {
            try (FileWriter fileWriter = new FileWriter(
                    Utils.getPathForOutputOfStep(command.getPosition(), OutputStream.ERR), true)) {
                fileWriter.append(e.getMessage());
            } catch (IOException e1) {
                System.err.println("Error writing error in stderr output file for command " + command.getPosition()
                        + ": " + e.getMessage());
            }
        }

    }

    // endregion

    // region Getters

    public static ProcessBuilder getProcessBuilder() {
        return processBuilder;
    }

    // endregion
}
