package SettingsTests;

import org.junit.jupiter.api.Test;
import static org.testfx.api.FxAssert.verifyThat;

import Utils.Ids;
import Utils.SettingsPage;
import Utils.TestFXBase;
import javafx.scene.control.TextField;

/** Settings related tests class */
public class SettingsTests extends TestFXBase {

    // #region Tests

    @Test
    public void checkOutputDirectory() {
        new SettingsPage(this);
        verifyThat(Ids.OUTPUT_FOLDER_FIELD, (TextField tf) -> {
            return tf.getText().equals(Business.Settings.getOutputSavingDirectory().getAbsolutePath());
        });
    }

    @Test
    public void checkExecutionDirectory() {
        new SettingsPage(this);
        verifyThat(Ids.EXEC_FOLDER_FIELD, (TextField tf) -> {
            return tf.getText().equals(Business.Settings.getExecutionDirectory().getAbsolutePath());
        });
    }

    // #endregion

    // #region Providers

    // #endregion
}
