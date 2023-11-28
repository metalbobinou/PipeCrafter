package Execution.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Business.Settings.Shell;
import Models.Argument.Type;

/** Handle command building operations */
public class CommandBuilder {

    // region Methods

    /**
     * Process the given command to be able to use it with the choosen shell
     * 
     * @param cmdList the command as a list of strings
     * @return the command as a list of strings
     */
    public static List<String> getCommand(Models.Command command) {

        List<String> cmdList = new ArrayList<>();

        if (Business.Settings.getUsedShell() == Shell.NONE) {

            cmdList.add(command.getCmd());
            for (Models.Argument arg : command.getArgumentList()) {
                String argAsString = arg.toString();

                if (arg.getType() == Type.OUTPUT) {
                    new File(argAsString).setExecutable(true);
                }
                cmdList.add(argAsString);
            }
            return cmdList;
        }

        // - Wrap the command to use it with a shell
        StringBuilder commandBuilder = new StringBuilder();
        // Turn the list into a single string
        for (Models.Argument arg : command.getArgumentList()) {
            String argAsString = arg.toString();

            if (arg.getType() == Type.OUTPUT) {
                new File(argAsString).setExecutable(true);
            }
            commandBuilder.append(" ").append(argAsString);
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
                return cmdList;
        }
    }
    // endregion
}
