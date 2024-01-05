package EditTests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

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
public class ArgEditTests extends TestFXBase {

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

    @Test
    void moveArg() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.addCmd(null, null);
        cm.addCmd(null, null);

        int cmdIndex = 1;

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.addArg(cmdIndex, Type.TEXT, "1");
        am.addArg(cmdIndex, Type.TEXT, "2");
        am.addArg(cmdIndex, Type.TEXT, "0");

        am.moveArg(cmdIndex, 2, 0);

        Models.Command cmd = Business.Command.getCommands().get(cmdIndex);
        List<Models.Argument> argList = cmd.getArgumentList();

        for (int i = 0; i < argList.size(); i++) {
            am.checkArg(cmd, 3, argList.get(i), Type.TEXT, Integer.toString(i));
        }
    }

}
