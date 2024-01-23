package ExecutionTests;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import Models.Command.State;
import Utils.CommandUIManager;
import Utils.TestFXBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(12)
public class NoShellTests extends TestFXBase {

        @Test
        void mixedExec() {
                loadPipeline(new File(savesToLoadDirectory, "noShell_outputArgs.json"));

                CommandUIManager cm = new CommandUIManager(this);

                Models.Command cm1 = Business.Command.getCommands().get(0);
                Models.Command cm2 = Business.Command.getCommands().get(1);
                Models.Command cm3 = Business.Command.getCommands().get(2);

                cm.pressRun(0);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm1, State.ALREADY_RUN, 0, "toto\n", ""));
                assertEquals(State.NEXT_TO_RUN, cm2.getState());
                assertEquals(State.TO_RUN, cm3.getState());

                cm.pressRun(1);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm2, State.ALREADY_RUN, 0, "toto\n", ""));
                assertEquals(State.ALREADY_RUN, cm1.getState());
                assertEquals(State.NEXT_TO_RUN, cm3.getState());

                cm.pressRun(2);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm3, State.ALREADY_RUN, 0, "the answer is toto\n\n", ""));
                assertEquals(State.ALREADY_RUN, cm2.getState());
                assertEquals(State.ALREADY_RUN, cm1.getState());
        }

        @Test
        void execDelayScript() {
                CommandUIManager cm = new CommandUIManager(this);
                cm.add(null, "./delay_print_both_streams.sh");

                Models.Command cm1 = Business.Command.getCommands().get(0);

                // Start run
                cm.pressRun(0);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm1, State.RUNNING, null, "", ""));

                sleep(5000);

                assertDoesNotThrow(
                                () -> cm.checkExec(cm1, State.ALREADY_RUN, 0, "TestfileCMD1\n",
                                                "\"Your error message\"\n"));

        }

        @Test
        void interruptExec() {
                CommandUIManager cm = new CommandUIManager(this);
                cm.add(null, "./delay_print_both_streams.sh");

                Models.Command cm1 = Business.Command.getCommands().get(0);

                // Start run
                cm.pressRun(0);
                sleep(1000);
                // Interrupt Run
                cm.pressRun(0);
                cm.handleAlertPopup(AlertType.CONFIRMATION, ButtonType.YES);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm1, State.NEXT_TO_RUN, 143, "", ""));
        }

        @Test
        void skipReferencedCmd() {
                loadPipeline(new File(savesToLoadDirectory, "noShell_outputArgs.json"));

                CommandUIManager cm = new CommandUIManager(this);

                Models.Command cm1 = Business.Command.getCommands().get(0);
                Models.Command cm2 = Business.Command.getCommands().get(1);

                cm.pressRun(1);
                cm.handleAlertPopup(AlertType.CONFIRMATION, ButtonType.YES);
                cm.handleAlertPopup(AlertType.ERROR, ButtonType.OK);
                assertDoesNotThrow(
                                () -> cm.checkExec(cm1, State.SKIPPED, null, null, null));

                assertDoesNotThrow(
                                () -> cm.checkExec(cm2, State.NEXT_TO_RUN, null, null, null));
        }

        @Test
        void restartExec() {
                loadPipeline(new File(savesToLoadDirectory, "noShell_outputArgs.json"));

                CommandUIManager cm = new CommandUIManager(this);

                Models.Command cm1 = Business.Command.getCommands().get(0);
                Models.Command cm2 = Business.Command.getCommands().get(1);
                Models.Command cm3 = Business.Command.getCommands().get(2);

                cm.pressRun(0);
                cm.pressRun(1);
                cm.pressRun(2);

                cm.pressRun(0);
                cm.handleAlertPopup(AlertType.CONFIRMATION, ButtonType.YES);

                assertDoesNotThrow(
                                () -> cm.checkExec(cm1, State.ALREADY_RUN, 0, "toto\n", ""));
                assertEquals(State.NEXT_TO_RUN, cm2.getState());
                assertEquals(State.TO_RUN, cm3.getState());
        }
}
