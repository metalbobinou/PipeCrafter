package Execution;

import java.io.File;
import java.io.IOException;
import java.util.List;

/** Handle a unique process object for all executions */
public class ProcessManager {

    /** The unique ProcessBuilder reference */
    private static ProcessBuilder processBuilder = null;

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
        // "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/src/test"
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
     * Set the ProcessBuilder for the specified step and its arguments and
     * execute it
     * 
     * @param command list of the command and its arguments as strings to execute
     * @param step    number of the step to execute
     * @return the returned code or null if an execution error occured
     */
    public static Integer execute(List<String> command, int step) {

        setRedirection(step);
        processBuilder.command(command);
        try {
            Process process = processBuilder.start();
            return process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // -- Getters
    public static ProcessBuilder getProcessBuilder() {
        return processBuilder;
    }
}
