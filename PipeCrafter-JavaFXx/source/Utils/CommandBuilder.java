package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Models.Argument.Type;

/** Handle command building operations */
public class CommandBuilder {

    // #region Methods

    /**
     * Process the given command to be able to use it with the choosen shell
     * 
     * @param command        the command as a list of strings
     * @param returnAsString if set, return a list which first element is the
     *                       command as one string
     * @return the command as a list of strings
     */
    public static List<String> getCommand(Models.Command command, boolean returnAsString) {

        List<String> cmdList = new ArrayList<>();
        cmdList.add(command.getCmd());
        StringBuilder commandBuilder = new StringBuilder(command.getCmd());

        for (Models.Argument arg : command.getArgumentList()) {
            String argAsString = arg.toString();

            if (arg.getType() == Type.OUTPUT) {
                new File(argAsString).setExecutable(true);
            }

            commandBuilder.append(" ").append(argAsString);
            cmdList.add(argAsString);
        }

        if (returnAsString) {
            return Arrays.asList(commandBuilder.toString());
        }

        switch (Business.Settings.getUsedShell()) {
            case BASH:
                return Arrays.asList("bash", "-c", commandBuilder.toString());
            case CMD:
                return Arrays.asList("cmd", "/c", commandBuilder.toString());
            case POWERSHELL:
                return Arrays.asList("powershell", "-Command", commandBuilder.toString());
            case WSL:
                return Arrays.asList("wsl", commandBuilder.toString());
            default:
                return cmdList;
        }
    }

    // #endregion
}
