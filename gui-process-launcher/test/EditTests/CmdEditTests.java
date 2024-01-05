package EditTests;

import org.junit.jupiter.api.Test;

import Models.Command.State;
import Utils.CommandUIManager;
import Utils.TestFXBase;

/** Command edit related tests class */
public class CmdEditTests extends TestFXBase {

    @Test
    public void add1Cmd() {
        CommandUIManager cm = new CommandUIManager(this);

        int nbCmdsBeforeAdd = Business.Command.getCommands().size();

        String name = "A command";
        String cmd = "echo";

        cm.addCmd(name, cmd);

        cm.checkCmd(Business.Command.getCommands().get(0), State.NEXT_TO_RUN, 1, nbCmdsBeforeAdd + 1, name, cmd);
    }

    // @Test
    // public void add100Cmd() {
    // CommandUIManager page = new CommandUIManager(this);

    // int nbCmdsToAdd = 20;

    // for (int i = 0; i < nbCmdsToAdd; i++) {
    // page.addCmd(null, null);
    // }

    // State expectedState = State.NEXT_TO_RUN;

    // for (int i = 0; i < nbCmdsToAdd; i++) {
    // page.checkCmd(Business.Command.getCommands().get(i), expectedState, i + 1,
    // nbCmdsToAdd, "", "");
    // expectedState = State.TO_RUN;
    // }
    // }

    @Test
    void moveCmdMiddle() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.addCmd("1", null);
        cm.addCmd("3", null);
        cm.addCmd("4", null);
        cm.addCmd("2", null);

        cm.moveCmd(3, 1);

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < Business.Command.getCommands().size(); i++) {
            cm.checkCmd(Business.Command.getCommands().get(i), expectedState, i + 1, 4, String.valueOf(i + 1), "");
            expectedState = State.TO_RUN;
        }
    }

    @Test
    void moveCmdFirstAndLast() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.addCmd("3", null);
        cm.addCmd("1", null);
        cm.addCmd("2", null);

        cm.moveCmd(0, 2);

        State expectedState = State.NEXT_TO_RUN;

        for (int i = 0; i < Business.Command.getCommands().size(); i++) {
            cm.checkCmd(Business.Command.getCommands().get(i), expectedState, i + 1, 3, String.valueOf(i + 1), "");
            expectedState = State.TO_RUN;
        }
    }

}
