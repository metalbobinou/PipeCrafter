package EditTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import Utils.MainPage;
import Utils.TestFXBase;

/** Edit related tests class */
public class EditTests extends TestFXBase {

    @Test
    public void addCmd() {
        MainPage page = new MainPage(this);
        page.addCmd();

    }
}
