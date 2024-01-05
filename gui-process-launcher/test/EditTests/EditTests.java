package EditTests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobotException;

import Models.Argument.Type;
import Models.Command.State;
import Utils.MainPage;
import Utils.OutputParameters;
import Utils.TestFXBase;
import Utils.OutputParameters.Format;
import Utils.OutputParameters.OutputStream;

/** Edit related tests class */
public class EditTests extends TestFXBase {

    @Test
    public void add1Cmd() {
        MainPage page = new MainPage(this);

        int nbCmdsBeforeAdd = Business.Command.getCommands().size();

        String name = "A command";
        String cmd = "echo";

        page.addCmd(name, cmd);

        page.checkCmd(Business.Command.getCommands().get(0), State.NEXT_TO_RUN, 1, nbCmdsBeforeAdd + 1, name, cmd);
    }

    // @Test
    // public void add100Cmd() {
    // MainPage page = new MainPage(this);

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
        MainPage page = new MainPage(this);
        page.addCmd("A", "echo");

        int cmdIndex = 0;
        Type type = Type.TEXT;
        Object value = "hey";

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);
        int nbArgBeforeAdd = targetedCmd.getArgumentList().size();

        page.addArg(cmdIndex, type,
                value);

        page.checkArg(targetedCmd, nbArgBeforeAdd + 1, targetedCmd.getArgumentList().get(nbArgBeforeAdd), type, value);
    }

    @Test
    public void addOutputArg() {
        MainPage page = new MainPage(this);
        page.addCmd("A", "echo");
        page.addCmd("B", "echo");

        int cmdIndex = 1;
        Type type = Type.OUTPUT;
        Object value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);
        int nbArgBeforeAdd = targetedCmd.getArgumentList().size();

        page.addArg(cmdIndex, type,
                value);

        page.checkArg(targetedCmd, nbArgBeforeAdd + 1, targetedCmd.getArgumentList().get(nbArgBeforeAdd), type, value);
    }

    @Test
    public void addOutputArgToFirstCmd() {
        MainPage page = new MainPage(this);
        page.addCmd("A", "echo");
        page.addCmd("B", "echo");

        int cmdIndex = 0;
        Type type = Type.OUTPUT;
        Object value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        assertThrows(FxRobotException.class, () -> {
            page.addArg(cmdIndex, type,
                    value);
        });

    }

    @Test
    public void editArg() {
        MainPage page = new MainPage(this);
        page.addCmd("A", "echo");
        page.addCmd("B", "echo");

        int cmdIndex = 1;
        Type type = Type.TEXT;
        Object value = "hey";

        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);

        page.addArg(cmdIndex, type,
                value);
        page.addArg(cmdIndex, type,
                value);

        int argIndex = 1;
        int nbArgBeforeEdit = targetedCmd.getArgumentList().size();
        type = Type.OUTPUT;
        value = new OutputParameters(Business.Command.getCommands().get(0), OutputStream.OUT, Format.CONTENT);

        page.editArg(cmdIndex, argIndex, type, value);

        page.checkArg(targetedCmd, nbArgBeforeEdit, targetedCmd.getArgumentList().get(argIndex), type, value);
    }

}
