package EditTests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.testfx.api.FxRobotException;

import Models.Argument.Type;
import Utils.ArgumentUIManager;
import Utils.CommandUIManager;
import Utils.OutputParameters;
import Utils.TestFXBase;
import Utils.OutputParameters.Format;
import Utils.OutputParameters.OutputStream;

@Timeout(20)
/** Edit related tests class */
public class ArgEditTests extends TestFXBase {

    @Test
    public void addTextArg() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("A", "echo");

        int cmdIndex = 0;
        Type type = Type.TEXT;
        Object value = "hey";

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);
        int nbArgBeforeAdd = targetedCmd.getArgumentList().size();

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.add(cmdIndex, type,
                value);

        am.check(targetedCmd, nbArgBeforeAdd + 1, targetedCmd.getArgumentList().get(nbArgBeforeAdd), type, value);
    }

    @Test
    public void addOutputArg() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("A", "echo");
        cm.add("B", "echo");

        int cmdIndex = 1;
        Models.Command cmdToUse = Business.Command.getCommands().get(0);
        Type type = Type.OUTPUT;
        Object value = new OutputParameters(cmdToUse, OutputStream.OUT, Format.CONTENT);

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);
        int nbArgBeforeAdd = targetedCmd.getArgumentList().size();

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.add(cmdIndex, type, value);

        Models.Argument addedArg = targetedCmd.getArgumentList().get(nbArgBeforeAdd);

        am.check(targetedCmd, nbArgBeforeAdd + 1, addedArg, type, value);

        assertTrue(cmdToUse.getReferringArgumentList().size() == 1
                && cmdToUse.getReferringArgumentList().contains(addedArg));
    }

    @Test
    public void addOutputArgToFirstCmd() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("A", "echo");
        cm.add("B", "echo");

        int cmdIndex = 0;
        Type type = Type.OUTPUT;
        Object value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        ArgumentUIManager am = new ArgumentUIManager(this);
        assertThrows(FxRobotException.class, () -> {
            am.add(cmdIndex, type,
                    value);
        });

    }

    @Test
    public void edit() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add("A", "echo");
        cm.add("B", "echo");

        int cmdIndex = 1;
        Type type = Type.TEXT;
        Object value = "hey";

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.add(cmdIndex, type,
                value);
        am.add(cmdIndex, type,
                value);

        int argIndex = 1;
        int nbArgBeforeEdit = targetedCmd.getArgumentList().size();
        type = Type.OUTPUT;
        value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        am.edit(cmdIndex, argIndex, type, value);

        am.check(targetedCmd, nbArgBeforeEdit, targetedCmd.getArgumentList().get(argIndex), type, value);
    }

    @Test
    void move() {
        CommandUIManager cm = new CommandUIManager(this);
        cm.add(null, null);
        cm.add(null, null);

        int cmdIndex = 1;

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.add(cmdIndex, Type.TEXT, "1");
        am.add(cmdIndex, Type.TEXT, "2");
        am.add(cmdIndex, Type.TEXT, "0");

        am.move(cmdIndex, 2, 0);

        Models.Command cmd = Business.Command.getCommands().get(cmdIndex);
        List<Models.Argument> argList = cmd.getArgumentList();

        for (int i = 0; i < argList.size(); i++) {
            am.check(cmd, 3, argList.get(i), Type.TEXT, Integer.toString(i));
        }
    }

    @Test
    void delete() {
        new CommandUIManager(this).add(null, null);

        ArgumentUIManager am = new ArgumentUIManager(this);
        am.add(0, Type.TEXT, "-1");
        am.add(0, Type.TEXT, "0");

        am.delete(0, 0);

        Models.Command cmd = Business.Command.getCommands().get(0);

        am.check(cmd, 1, cmd.getArgumentList().get(0), Type.TEXT, Integer.toString(0));

    }

}
