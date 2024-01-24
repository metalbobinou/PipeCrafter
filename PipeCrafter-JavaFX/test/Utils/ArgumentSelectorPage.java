package Utils;

import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

/** Page class to interact with the argument selection page */
public class ArgumentSelectorPage extends ArgumentUIManager {
    // #region Constructor

    public ArgumentSelectorPage(TestFXBase drive) {
        super(drive);
    }

    // #endregion

    // #region Methods

    /**
     * Select the type for an argument
     * 
     * Note: waiting for the TODO implementation, can only add an output
     * argument referring to one of the first 10 commands
     * 
     * @param type  type to select
     * @param value value of the argument
     */
    public void selectArgType(Models.Argument.Type type, Object value) {
        switch (type) {
            case TEXT:
                drive.clickOn(Ids.TEXT_GROUP_ID);
                drive.write(value.toString());
                DialogPane dialogPane = drive.find(".dialog-pane");
                drive.clickOn(dialogPane.lookupButton(ButtonType.OK));
                return;
            case OUTPUT:
                drive.clickOn(Ids.OUTPUT_GROUP_ID);
                OutputParameters op = (OutputParameters) value;

                drive.clickOn(Ids.CMD_SELECTOR_BOX_ID);
                // TODO scroll to command
                drive.clickOn(String.valueOf(op.getPosition()) + " - " + op.getCmdToUse().getName());

                drive.clickOn(Ids.STREAM_SELECTOR_BOX_ID);
                switch (op.getStream()) {
                    case ERR:
                        drive.clickOn("stderr");
                        break;
                    case OUT:
                        drive.clickOn("stdout");
                        break;
                }

                drive.clickOn(Ids.FORMAT_SELECTOR_BOX_ID);

                WaitForAsyncUtils.waitForFxEvents();
                drive.sleep(10);

                switch (op.getFormat()) {
                    case CONTENT:
                        drive.clickOn("File's content");
                        break;
                    case PATH:
                        drive.clickOn("Path to file");
                        break;
                }

                drive.clickOn(Ids.DONE_BUTTON_ID);
                return;

            default:
                return;
        }
    }

    // #endregion
}
