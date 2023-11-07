package fr.epita.gpl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Executioner {

    public static void exe() {
        try {

            // Create ProcessBuilder instance
            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.directory(new File(
                    "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/src/test"));

            String command = "cat testFile.txt && echo \"Your error message\" >&2";
            processBuilder.command(Arrays.asList("bash", "-c", command));

            processBuilder.redirectOutput(new File(
                    "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/src/test/output/1.out"));
            processBuilder.redirectError(new File(
                    "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/src/test/output/1.err"));

            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            System.out.println("Command executed with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
