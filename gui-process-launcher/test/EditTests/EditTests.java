package EditTests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;

import Models.Argument.Type;
import Models.Command.State;
import Utils.ArgumentUIManager;
import Utils.CommandUIManager;
import Utils.OutputParameters;
import Utils.TestFXBase;
import Utils.OutputParameters.Format;
import Utils.OutputParameters.OutputStream;

/** Edit related tests class */
public class EditTests extends TestFXBase {

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
    public void addTextArg() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.addCmd("A", "echo");

        int cmdIndex = 0;
        Type type = Type.TEXT;
        Object value = "hey";

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);
        int nbArgBeforeAdd = targetedCmd.getArgumentList().size();

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.addArg(cmdIndex, type,
                value);

        am.checkArg(targetedCmd, nbArgBeforeAdd + 1, targetedCmd.getArgumentList().get(nbArgBeforeAdd), type, value);
    }

    @Test
    public void addOutputArg() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.addCmd("A", "echo");
        cm.addCmd("B", "echo");

        int cmdIndex = 1;
        Type type = Type.OUTPUT;
        Object value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);
        int nbArgBeforeAdd = targetedCmd.getArgumentList().size();

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.addArg(cmdIndex, type,
                value);

        am.checkArg(targetedCmd, nbArgBeforeAdd + 1, targetedCmd.getArgumentList().get(nbArgBeforeAdd), type, value);
    }

    @Test
    public void addOutputArgToFirstCmd() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.addCmd("A", "echo");
        cm.addCmd("B", "echo");

        int cmdIndex = 0;
        Type type = Type.OUTPUT;
        Object value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        ArgumentUIManager am = new ArgumentUIManager(this);
        assertThrows(FxRobotException.class, () -> {
            am.addArg(cmdIndex, type,
                    value);
        });

    }

    @Test
    public void editArg() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.addCmd("A", "echo");
        cm.addCmd("B", "echo");

        int cmdIndex = 1;
        Type type = Type.TEXT;
        Object value = "hey";

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.addArg(cmdIndex, type,
                value);
        am.addArg(cmdIndex, type,
                value);

        int argIndex = 1;
        int nbArgBeforeEdit = targetedCmd.getArgumentList().size();
        type = Type.OUTPUT;
        value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        am.editArg(cmdIndex, argIndex, type, value);

        am.checkArg(targetedCmd, nbArgBeforeEdit, targetedCmd.getArgumentList().get(argIndex), type, value);
    }

}
