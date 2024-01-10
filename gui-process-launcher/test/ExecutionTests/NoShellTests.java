package ExecutionTests;

import java.io.File;

import org.junit.jupiter.api.Test;

import Models.Command.State;
import Utils.CommandUIManager;
import Utils.TestFXBase;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoShellTests extends TestFXBase {

        @Test
        void mixed() {
                loadPipeline(new File(savesToLoadDirectory, "noShell_outputArgs.json"));

                CommandUIManager cm = new CommandUIManager(this);

                Models.Command cm1 = Business.Command.getCommands().get(0);
                Models.Command cm2 = Business.Command.getCommands().get(1);
                Models.Command cm3 = Business.Command.getCommands().get(2);

                cm.pressRun(0);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm1, State.ALREADY_RUN, 0, "toto\n", ""));
                assertEquals(cm2.getState(), State.NEXT_TO_RUN);
                assertEquals(cm3.getState(), State.TO_RUN);

                cm.pressRun(1);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm2, State.ALREADY_RUN, 0, "toto\n", ""));
                assertEquals(cm1.getState(), State.ALREADY_RUN);
                assertEquals(cm3.getState(), State.NEXT_TO_RUN);

                cm.pressRun(2);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm3, State.ALREADY_RUN, 0, "the answer is toto\n\n", ""));
                assertEquals(cm2.getState(), State.ALREADY_RUN);
                assertEquals(cm1.getState(), State.ALREADY_RUN);
        }

}
