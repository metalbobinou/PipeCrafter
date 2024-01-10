package EditTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import Models.Argument.Type;
import Models.Command.State;
import Utils.ArgumentUIManager;
import Utils.CommandUIManager;
import Utils.OutputParameters;
import Utils.TestFXBase;
import Utils.OutputParameters.Format;
import Utils.OutputParameters.OutputStream;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

@Timeout(20)
/** Command edit related tests class */
public class CmdEditTests extends TestFXBase {

    @Test
    public void add1() {
        CommandUIManager cm = new CommandUIManager(this);

        int nbCmdsBeforeAdd = Business.Command.getCommands().size();

        String name = "A command";
        String cmd = "echo";

        cm.add(name, cmd);

        cm.check(Business.Command.getCommands().get(0), State.NEXT_TO_RUN, 1, nbCmdsBeforeAdd + 1, name, cmd);
    }

    @Test
    @Timeout(100)
    public void addMany() {
        CommandUIManager cm = new CommandUIManager(this);

        int nbCmdsToAdd = 40;

        for (int i = 0; i < nbCmdsToAdd; i++) {
            cm.add(null, null);
        }

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < nbCmdsToAdd; i++) {
            cm.check(Business.Command.getCommands().get(i), expectedState, i + 1,
                    nbCmdsToAdd, "", "");
            expectedState = State.TO_RUN;
        }
    }

    @Test
    void moveMiddle() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("1", null);
        cm.add("3", null);
        cm.add("4", null);
        cm.add("2", null);

        cm.move(3, 1);

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < Business.Command.getCommands().size(); i++) {
            cm.check(Business.Command.getCommands().get(i), expectedState, i + 1, 4, String.valueOf(i + 1), "");
            expectedState = State.TO_RUN;
        }
    }

    @Test
    void moveFirstAndLast() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("3", null);
        cm.add("1", null);
        cm.add("2", null);

        cm.move(0, 2);

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < Business.Command.getCommands().size(); i++) {
            cm.check(Business.Command.getCommands().get(i), expectedState, i + 1, 3, String.valueOf(i + 1), "");
            expectedState = State.TO_RUN;
        }
    }

    @Test
    void deleteMiddle() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("0", null);
        cm.add("1", null);
        cm.add("1", null);

        cm.delete(1);

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < Business.Command.getCommands().size(); i++) {
            cm.check(Business.Command.getCommands().get(i), expectedState, i + 1, 2, String.valueOf(i), "");
            expectedState = State.TO_RUN;
        }
    }

    @Test
    void deleteReferenced() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("0", null);
        cm.add("0", null);

        int referencedCmdIndex = 0;

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.add(1, Type.OUTPUT, new OutputParameters(Business.Command.getCommands().get(referencedCmdIndex),
                OutputStream.ERR, Format.CONTENT));

        cm.delete(referencedCmdIndex);
        cm.handleAlertPopup(AlertType.CONFIRMATION, ButtonType.YES);

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < Business.Command.getCommands().size(); i++) {
            cm.check(Business.Command.getCommands().get(i), expectedState, i + 1, 1, String.valueOf(i), "");
            expectedState = State.TO_RUN;
        }
    }

    // TODO test breaking references by move or delete
}
