package SettingsTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import Business.Settings.Shell;
import Utils.SettingsPage;
import Utils.TestFXBase;

/** Settings related tests class */
public class SettingsTests extends TestFXBase {

    // #region Test UI shows current settings

    @Test
    public void checkOutputDirectory() {
        new SettingsPage(this).verifyOutputFolderField(null);
    }

    @Test
    public void checkExecutionDirectory() {
        new SettingsPage(this).verifyExecFolderField(null);

    }

    @Test
    public void checkUsedShell() {
        new SettingsPage(this).verifyUsedShellBox(null);
    }

    // #endregion

    // #region Test UI update current settings and show update

    @Test
    public void writeOutputDir() {
        SettingsPage page = new SettingsPage(this);

        String newPath = baseURL + "test";

        page.setOutputFolderField(newPath);

        page.verifyOutputFolderField(newPath);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\\", "qwerty", "nwendfo3928y9{}{{@#(*())}}" })
    public void writeGarbageOutputDir(String text) {
        SettingsPage page = new SettingsPage(this);

        String currentSetPath = Business.Settings.getOutputSavingDirectory().getAbsolutePath();

        page.setOutputFolderField(text);

        page.verifyErrorAlertDisplayed();

        // check that the path was not changed
        page.verifyOutputFolderField(currentSetPath);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "\\", "qwerty", "nwendfo3928y9{}{{@#(*())}}" })
    public void writeGarbageExecDir(String text) {
        SettingsPage page = new SettingsPage(this);

        String currentSetPath = Business.Settings.getExecutionDirectory().getAbsolutePath();

        page.setExecFolderField(text);

        page.verifyErrorAlertDisplayed();

        // check that the path was not changed
        page.verifyExecFolderField(currentSetPath);
    }

    @Test
    public void writeExecDir() {
        SettingsPage page = new SettingsPage(this);

        String newPath = baseURL + "test";

        page.setExecFolderField(newPath);

        page.verifyExecFolderField(newPath);
    }

    @ParameterizedTest
    @EnumSource(Shell.class)
    public void setShell(Shell shellToUse) {
        SettingsPage page = new SettingsPage(this);

        page.selectShell(shellToUse);

        page.verifyUsedShellBox(shellToUse);
    }

    @Test
    public void goBackToMenu() {
        new SettingsPage(this).returnToMenu();
    }

    // #endregion
}
