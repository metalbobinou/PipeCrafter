package EditTests;

import org.junit.jupiter.api.Test;

import Models.Command.State;
import Utils.CommandUIManager;
import Utils.TestFXBase;

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

    // @Test
    // public void addMany() {
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
    void deleteSimple() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("0", null);
        cm.add("1", null);
        cm.add("1", null);
    }

}
