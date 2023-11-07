package fr.epita.gpl;

import java.util.Scanner;

import fr.epita.gpl.CommandManager.OutputStream;
import fr.epita.gpl.Settings.Shell;

public class V0 {

    // Execute only one command or chain 2 commands by making the first command
    // produce a script that will be executed with the 2nd command
    public static void demo() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("-- Config (enter nothing for default) --");
        System.out.print("Execution directory: ");
        String answer = scanner.nextLine();
        if (answer == null || answer == "") {
            answer = "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/src/test";
        }
        Settings.setExecutionDirectory(answer);

        System.out.print("Output directory: ");
        answer = scanner.nextLine();
        if (answer == null || answer == "") {
            answer = "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/src/test/output";
        }
        Settings.setOutputSavingDirectory(answer);

        System.out.print("Use bash? (y/n): ");
        answer = scanner.nextLine();
        if (answer == "y") {
            Settings.setUsedShell(Shell.BASH);
        }

        ProcessManager.init();

        System.out.print("What is the 1st command?: ");
        answer = scanner.nextLine();
        if (answer == null || answer == "") {
            answer = "cat script.sh";
        }
        Integer res = ProcessManager.execute(CommandManager.getCommand(answer), 1);
        System.out.println("Command 1 gave output code: " + res.toString());

        System.out.print(
                "Now you can use the previous output with a second command: [command] <output from command 1> . Do you want to use the last stdout (1) output or stderr (2) ?: ");
        answer = scanner.nextLine();
        CommandManager.OutputStream stream = OutputStream.OUT;
        if (answer == "2") {
            stream = OutputStream.ERR;
        }

        System.out.print(
                "What is the second command (the file of the output from command 1 will be appenned to whatever you type)?: ");
        answer = scanner.nextLine();
        res = ProcessManager
                .execute(CommandManager.getCommand(answer + CommandManager.getPathForOutputOfStep(1, stream)), 2);
        System.out.println("Command 2 gave output code: " + res.toString());

        scanner.close();
    }
}
