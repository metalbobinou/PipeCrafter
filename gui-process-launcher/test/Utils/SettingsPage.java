package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
        Text settingsText = drive.find(Ids.SETTINGS_TITLE_LABEL_ID);
        assertEquals("Settings", settingsText.getText());
    }

    // #endregion

    // #region Methods

    /**
     * Replace the text in the execution folder text field
     * 
     * @param textToWrite text to set for the field
     */
    public void setExecFolderField(String textToWrite) {
        replaceTextIn(Ids.EXEC_FOLDER_FIELD_ID, textToWrite);
    }

    /**
     * Replace the text in the output folder text field
     * 
     * @param textToWrite text to set for the field
     */
    public void setOutputFolderField(String textToWrite) {
        replaceTextIn(Ids.OUTPUT_FOLDER_FIELD_ID, textToWrite);
    }

    /**
     * Select the chosen shell
     * 
     * @param shell shell to select
     */
    public void selectShell(Business.Settings.Shell shell) {
        drive.clickOn(Ids.SHELL_CHOICE_BOX_ID);
        drive.clickOn(shell.toString());
    }

    /** Return to the main menu */
    public void returnToMenu() {
        drive.clickOn(Ids.SETTINGS_BACK_BUTTON_ID);
        drive.find(Ids.SETTINGS_BUTTON_ID);
    }

    /**
     * Verify that the execution folder field is set properly
     * 
     * @param textToCompare if not null, text that the field should contain
     */
    public void verifyExecFolderField(String textToCompare) {
        drive.verifyThat(Ids.EXEC_FOLDER_FIELD_ID, (TextField tf) -> {
            boolean settingsUpdated = tf.getText()
                    .equals(Business.Settings.getExecutionDirectory().getAbsolutePath());
            boolean textSet = textToCompare == null ? true : tf.getText().equals(textToCompare);
            return settingsUpdated && textSet;
        });
    }

    /**
     * Verify that the output folder field is set properly
     * 
     * @param textToCompare if not null, text that the field should contain
     */
    public void verifyOutputFolderField(String textToCompare) {
        drive.verifyThat(Ids.OUTPUT_FOLDER_FIELD_ID, (TextField tf) -> {
            boolean settingsUpdated = tf.getText()
                    .equals(Business.Settings.getOutputSavingDirectory().getAbsolutePath());
            boolean textSet = textToCompare == null ? true : tf.getText().equals(textToCompare);
            return settingsUpdated && textSet;
        });
    }

    /**
     * Verify that the shell choice box is set properly
     * 
     * @param shellToUse if not null, shell that the box should display
     */
    public void verifyUsedShellBox(Business.Settings.Shell shellToUse) {
        drive.verifyThat(Ids.SHELL_CHOICE_BOX_ID,
                node -> ((ChoiceBox) node).getValue().equals(Business.Settings.getUsedShell().toString())
                        && shellToUse == null ? true : ((ChoiceBox) node).getValue().equals(shellToUse.toString()));
    }

    // #endregion

}
