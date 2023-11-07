package fr.epita.gpl;

import java.util.Arrays;
import java.util.List;

import fr.epita.gpl.Settings.Shell;

/** Handle command building operations */
public class CommandManager {

    /** Get the command as a list of strings */
    public static List<String> getCommand(List<String> command) {

        if (Settings.getUsedShell() == Shell.NONE) {
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

        switch (Settings.getUsedShell()) {
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
}
