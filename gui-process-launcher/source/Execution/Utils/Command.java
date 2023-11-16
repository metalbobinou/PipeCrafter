package Execution.Utils;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import Business.Settings.Shell;

/** Handle command building operations */
public class Command {

    // region Nested types

    public enum OutputStream {
        OUT(".out"),
        ERR(".err");

        private final String associatedString;

        OutputStream(String associatedString) {
            this.associatedString = associatedString;
        }

        public String getExtension() {
            return associatedString;
        }
    }

    // endregion

    // region Methods

    /**
     * Build and return the path to the saved output of the given step from the
     * given stream as a string
     */
    public static String getPathForOutputOfStep(int step, OutputStream stream) {
        return Paths.get(Business.Settings.getOutputSavingDirectory().toString(), step + stream.getExtension())
                .toString();
    }

    /** Get the command as a list of strings */
    public static List<String> getCommand(List<String> command) {

        if (Business.Settings.getUsedShell() == Shell.NONE) {
            return command;
        }

        // - Wrap the command to use it with a shell
        StringBuilder commandBuilder = new StringBuilder();
        // Turn the list into a single string
        for (String string : command) {
            commandBuilder.append(" ").append(string);
        }
        commandBuilder.deleteCharAt(0);
        String commandString = commandBuilder.toString();

        switch (Business.Settings.getUsedShell()) {
            case BASH:
                return Arrays.asList("bash", "-c", commandString);
            case CMD:
                return Arrays.asList("cmd", "/c", commandString);
            case POWERSHELL:
                return Arrays.asList("powershell", "-Command", commandString);
            case WSL:
                return Arrays.asList("wsl", commandString);
            default:
                return command;
        }
    }

    // Add function to check used output files have exec rights (read/write is given
    // when generated)

    // endregion
}
