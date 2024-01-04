package EditTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import Models.Argument.Type;
import Utils.MainPage;
import Utils.OutputParameters;
import Utils.TestFXBase;

/** Edit related tests class */
public class EditTests extends TestFXBase {

    @Test
    public void add1Cmd() {
        MainPage page = new MainPage(this);
        page.add1CmdAndCheck();
    }

    // @Test
    // public void add100Cmd() {
    // MainPage page = new MainPage(this);

    // for (int i = 0; i < 20; i++) {
    // page.add1CmdAndCheck();
    // }
    // }

    @Test
    public void addTextArg() {
        MainPage page = new MainPage(this);
        page.addCmd();
        page.addArg(0, Type.TEXT, "hey");
    }

}
