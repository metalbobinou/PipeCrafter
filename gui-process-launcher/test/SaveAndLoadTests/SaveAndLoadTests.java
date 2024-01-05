package SaveAndLoadTests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import Models.Argument.Type;
import Models.Command.State;
import Utils.ArgumentUIManager;
import Utils.CommandUIManager;
import Utils.OutputParameters;
import Utils.OutputParameters.Format;
import Utils.OutputParameters.OutputStream;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import Utils.TestFXBase;

public class SaveAndLoadTests extends TestFXBase {

    @Test
    void saveAndLoad() {
        CommandUIManager cm = new CommandUIManager(this);
        ArgumentUIManager am = new ArgumentUIManager(this);

        int nbOfCmds = 10;
        int nbOfArgs = 3;
        int indexOfReferedCmd = 0;

        for (int i = 0; i < nbOfCmds; i++) {
            cm.add(String.valueOf(i), "echo");
            for (int j = 0; j < nbOfArgs; j++) {
                am.add(i, Type.TEXT, String.valueOf(i) + "-" + String.valueOf(j));
            }
        }

        OutputParameters op = new OutputParameters(Business.Command.getCommands().get(indexOfReferedCmd),
                OutputStream.OUT,
                Format.PATH);
        am.add(nbOfCmds - 1, Type.OUTPUT, op);

        cm.setDetails(nbOfCmds - 1, String.valueOf(nbOfCmds), "");

        File save = new File(savedDirectory, "saveAndLoad");

        Utils.Save.save(save);

        cm.add("should be overwritten when load", "echo");

        Utils.Load.load(save);
        cm.handleAlertPopup(AlertType.CONFIRMATION, ButtonType.NO);

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < Business.Command.getCommands().size() - 1; i++) {
            Models.Command cmd = Business.Command.getCommands().get(i);

            cm.check(cmd, expectedState, i + 1, nbOfCmds, String.valueOf(i), "echo");

            for (int j = 0; j < nbOfArgs; j++) {
                am.check(cmd, nbOfArgs, cmd.getArgumentList().get(j), Type.TEXT,
                        String.valueOf(i) + "-" + String.valueOf(j));
            }
            expectedState = State.TO_RUN;
        }

        Models.Command cmd = Business.Command.getCommands().get(nbOfCmds - 1);
        cm.check(cmd, expectedState, nbOfCmds, nbOfCmds, String.valueOf(nbOfCmds), "");
        for (int j = 0; j < nbOfArgs; j++) {
            am.check(cmd, nbOfArgs + 1, cmd.getArgumentList().get(j), Type.TEXT,
                    String.valueOf(nbOfCmds) + "-" + String.valueOf(j));
        }

        Models.Argument referingArg = cmd.getArgumentList().get(nbOfArgs);
        am.check(cmd, nbOfArgs + 1, referingArg, Type.OUTPUT, op);

        Models.Command referedCmd = Business.Command.getCommands().get(indexOfReferedCmd);
        assertTrue(referedCmd.getReferringArgumentList().size() == 1
                && referedCmd.getReferringArgumentList().contains(referingArg));
    }

    // TODO load after exec to load state

}
