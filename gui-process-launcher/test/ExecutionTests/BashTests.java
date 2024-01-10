package ExecutionTests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import Business.Settings.Shell;
import Models.Command.State;
import Utils.CommandUIManager;
import Utils.TestFXBase;

@Timeout(12)
public class BashTests extends TestFXBase {

    @Test
    void bashTest() {
        loadPipeline(new File(savesToLoadDirectory, "bash_outputArgs.json"));

        CommandUIManager cm = new CommandUIManager(this);

        Models.Command cm1 = Business.Command.getCommands().get(0);
        Models.Command cm2 = Business.Command.getCommands().get(1);

        assertEquals(Shell.BASH, Business.Settings.getUsedShell());

        cm.pressRun(0);
        assertDoesNotThrow(
                () -> cm.checkExec(cm1, State.ALREADY_RUN, 0, "", "redirect me !\n"));

        cm.pressRun(1);
        assertDoesNotThrow(
                () -> cm.checkExec(cm2, State.ALREADY_RUN, 0, "redirect me !\n", ""));
    }
}
