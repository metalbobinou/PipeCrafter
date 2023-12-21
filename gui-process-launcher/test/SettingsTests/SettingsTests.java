package SettingsTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import Base.TestFXBase;
import javafx.scene.text.Text;

public class SettingsTests extends TestFXBase {

    // #region Attributes

    private final String SETTINGS_BUTTON_ID = "#settingsButton";

    // #endregion

    // #region Tests

    @Test
    public void openSettings() {
        // open settings
        clickOn(SETTINGS_BUTTON_ID);

        Text settingsText = find("#settingsLabel");

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("Settings", settingsText.getText());
    }

    // #endregion
}
