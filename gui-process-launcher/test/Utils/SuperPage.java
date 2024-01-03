package Utils;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.KeyCode;

/** Parent class for all page classes */
public abstract class SuperPage {

    // #region Attributes

    /** The drive to use to interact with the page */
    protected final TestFXBase drive;

    /** KeyCode to use to perform actions such as select all, copy and paste */
    protected final static KeyCode controlKey = System.getProperty("os.name").toLowerCase().contains("mac")
            ? KeyCode.COMMAND
            : KeyCode.CONTROL;

    // #endregion

    // #region Constructor

    public SuperPage(TestFXBase drive) {
        this.drive = drive;
    }

    // #endregion

    // #region Methods

    /**
     * Helper method to press and immediately release the given keys
     * 
     * @param keys to press and release
     */
    private void pressAndRelease(KeyCode... keys) {
        for (KeyCode key : keys) {
            drive.press(key);
        }
        for (KeyCode key : keys) {
            drive.release(key);
        }
    }

    /**
     * Replace the text in a writable element
     * 
     * @param elementId id of the element to set text for
     * @param text      text to write
     */
    public void replaceTextIn(String elementId, String text) {
        drive.clickOn(elementId);

        pressAndRelease(controlKey, KeyCode.A, KeyCode.BACK_SPACE);

        drive.write(text);

        pressAndRelease(KeyCode.ENTER);
    }

    /** Check that an alert is displayed and clear it */
    public void verifyErrorAlertDisplayed() {
        DialogPane dialogPane = drive.find(".dialog-pane");
        assumeTrue(dialogPane.getStyleClass().contains("error"));

        drive.clickOn(dialogPane.lookupButton(ButtonType.OK));
    }

    // #endregion
}
