package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.text.Text;

/** Page class for settings related operations */
public class SettingsPage extends SuperPage {

    // #region Constructor

    public SettingsPage(TestFXBase drive) {
        super(drive);

        // Open the settings page
        drive.clickOn(Ids.SETTINGS_BUTTON_ID);

        WaitForAsyncUtils.waitForFxEvents();

        // Verify that it was indeed opened
        Text settingsText = drive.find(Ids.SETTINGS_TITLE_LABEL);
        assertEquals("Settings", settingsText.getText());
    }

    // #endregion

    // #region Methods

    // #endregion

}
