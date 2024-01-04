package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.testfx.util.WaitForAsyncUtils;

import Models.Command.State;
import javafx.geometry.VerticalDirection;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;

/** Page class to interact with the main page */
public class MainPage extends SuperPage {
    // #region Constructor

    public MainPage(TestFXBase drive) {
        super(drive);
    }

    // #endregion

    // #region Methods

    public void addCmd() {
        drive.scroll(30, VerticalDirection.UP);
        drive.clickOn(Ids.ADD_CMD_BUTTON_ID);
    }

    /** Add a command and check it went well */
    public void add1CmdAndCheck() {

        int currentStepBeforeAdd = Business.App.getCurrentStep();
        int nbCmdsBeforeAdd = Business.Command.getCommands().size();

        addCmd();

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(Business.App.getCurrentStep(), currentStepBeforeAdd);
        assertEquals(Business.Command.getCommands().size(), nbCmdsBeforeAdd + 1);

        Models.Command addedCmd = Business.Command.getCommands().get(nbCmdsBeforeAdd);

        if (nbCmdsBeforeAdd == 0) {
            assertEquals(addedCmd.getState(), State.NEXT_TO_RUN);
        } else {
            assertEquals(addedCmd.getState(), State.TO_RUN);
        }
        assertEquals(addedCmd.getPosition(), Business.Command.getCommands().indexOf(addedCmd) + 1);
        assertEquals(addedCmd.getPosition(), nbCmdsBeforeAdd + 1);
    }

    /**
     * Add an argument of type type to the Nth command
     * 
     * @param cmdIndex the index of the command to add an argument to
     * @param type     the value of the argument to add
     * @param value
     */
    public void addArg(int cmdIndex, Models.Argument.Type type, Object value) {
        Models.Command targetedCmd = Business.Command.getCommands().get(cmdIndex);

        int nbArgBeforeAdd = targetedCmd.getArgumentList().size();
        // TODO scroll to command
        ImageView addArgButton = drive.lookup(Ids.CMD_ADD_ARG_BUTTON_ID).nth(cmdIndex).query();
        drive.clickOn(addArgButton);

        selectArgType(type, value);

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(targetedCmd.getArgumentList().size(), nbArgBeforeAdd + 1);

        Models.Argument addedArg = targetedCmd.getArgumentList().get(nbArgBeforeAdd);

        assertEquals(addedArg.getMotherCommand(), targetedCmd);
        assertEquals(addedArg.getType(), type);

        switch (type) {
            case FILE:
                assertEquals(((File) addedArg.getObjectValue()).getAbsolutePath(), value.toString());
                break;
            case TEXT:
                assertEquals(addedArg.getObjectValue().toString(), value.toString());
                break;
            case OUTPUT:
                OutputParameters op = addedArg.getOutputParameter();
                OutputParameters givenOp = (OutputParameters) value;

                assertEquals(op.getFormat(), givenOp.getFormat());
                assertEquals(op.getStream(), givenOp.getStream());
                assertEquals(op.getCmdToUse(), givenOp.getCmdToUse());
                break;

            default:
                throw new IllegalArgumentException();
        }

    }

    /**
     * Select the type for an argument
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
                        drive.clickOn("stdout");
                        break;
                    case OUT:
                        drive.clickOn("stderr");
                        break;
                }

                drive.clickOn(Ids.FORMAT_SELECTOR_BOX_ID);

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
