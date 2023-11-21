package Execution.Utils;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import Business.Settings.Shell;
import Utils.OutputStream;

/** Handle command building operations */
public class CommandBuilder {

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

    /**
     * Process the given command to be able to use it with the choosen shell
     * 
     * @param command the command as a list of strings
     * @return the (wrapped) command as a list of strings
     */
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
