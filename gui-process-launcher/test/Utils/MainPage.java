package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.testfx.util.WaitForAsyncUtils;

import Models.Command.State;
import javafx.geometry.VerticalDirection;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/** Page class to interact with the main page */
public class MainPage extends SuperPage {
    // #region Constructor

    public MainPage(TestFXBase drive) {
        super(drive);
    }

    // #endregion

    // #region Methods

    /**
     * Add a command
     * 
     * @param name optional, name to give the command
     * @param cmd  optional, command to assign
     */
    public void addCmd(String name, String cmd) {
        drive.scroll(30, VerticalDirection.UP);
        drive.clickOn(Ids.ADD_CMD_BUTTON_ID);
        setCmdDetails(Business.Command.getCommands().size() - 1, name, cmd);
    }

    /**
     * Fill in details for the specified command
     * 
     * @param cmdIndex the index of the targeted command
     * @param name     optional, name to give the command
     * @param cmd      optional, command to assign
     */
    public void setCmdDetails(int cmdIndex, String name, String cmd) {
        if (name != null) {
            TextField nameField = drive.lookup(Ids.CMD_NAME_TEXTFIELD_ID).nth(cmdIndex).query();
            drive.clickOn(nameField);
            drive.write(name);
        }
        if (cmd != null) {
            TextField cmdField = drive.lookup(Ids.CMD_TEXTFIELD_ID).nth(cmdIndex).query();
            drive.clickOn(cmdField);
            drive.write(cmd);
        }

    }

    /**
     * Check that an argument has the correct values and UI
     * TODO add UI tests
     * 
     * @param cmd                 the command to check
     * @param expectedState       the expected state
     * @param expectedPosition    the expected position
     * @param expectedCmdListSize the expected command list size
     * @param name                the expected name
     * @param cmdText             the expected given command
     */
    public void checkCmd(Models.Command cmd, State expectedState, int expectedPosition,
            int expectedCmdListSize, String name, String cmdText) {
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(Business.Command.getCommands().size(), expectedCmdListSize);

        assertEquals(cmd.getState(), expectedState);
        assertEquals(cmd.getPosition(), expectedPosition);
        assertEquals(cmd.getPosition(), Business.Command.getCommands().indexOf(cmd) + 1);
        assertTrue(cmd.getName().equals(name));
        assertTrue(cmd.getCmd().equals(cmdText));
    }

    /**
     * Start adding an argument to the Nth command without selecting a type
     * 
     * Note: waiting for the TODO implementation, can only add an argument to
     * the commands visible on screen when the function is called
     * 
     * @param cmdIndex the index of the command to add an argument to
     */
    public void addArg(int cmdIndex) {
        // TODO scroll to command
        ImageView addArgButton = drive.lookup(Ids.CMD_ADD_ARG_BUTTON_ID).nth(cmdIndex).query();
        drive.clickOn(addArgButton);
    }

    /**
     * Add an argument of type type to the Nth command
     * 
     * Note: waiting for the TODO implementation, can only add an argument to
     * the commands visible on screen when the function is called
     * 
     * @param cmdIndex the index of the command to add an argument to
     * @param type     the type of the argument to add
     * @param value    the value of the argument to add
     */
    public void addArg(int cmdIndex, Models.Argument.Type type, Object value) {
        // TODO scroll to command
        ImageView addArgButton = drive.lookup(Ids.CMD_ADD_ARG_BUTTON_ID).nth(cmdIndex).query();
        drive.clickOn(addArgButton);

        selectArgType(type, value);
    }

    /**
     * Check that an argument has the correct values and UI
     * 
     * TODO add UI checks
     * 
     * @param cmd                 command to which the argument belongs
     * @param expectedArgListSize the command's arguments list's expected size
     * @param arg                 the argument to check
     * @param type                the expected type for the argument
     * @param value               the expected value for the argument
     */
    public void checkArg(Models.Command cmd, int expectedArgListSize, Models.Argument arg, Models.Argument.Type type,
            Object value) {

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(cmd.getArgumentList().size(), expectedArgListSize);

        assertEquals(arg.getMotherCommand(), cmd);
        assertEquals(arg.getType(), type);

        switch (type) {
            case FILE:
                assertTrue(((File) arg.getObjectValue()).getAbsolutePath().equals(value.toString()));
                break;
            case TEXT:
                assertTrue(arg.getObjectValue().toString().equals(value.toString()));
                break;
            case OUTPUT:
                OutputParameters op = arg.getOutputParameter();
                OutputParameters givenOp = (OutputParameters) value;

                assertEquals(op.getFormat(), givenOp.getFormat());
                assertEquals(op.getStream(), givenOp.getStream());
                assertEquals(op.getCmdToUse(), givenOp.getCmdToUse());

                assertTrue(op.getCmdToUse().getReferringArgumentList().contains(arg));
                break;

            default:
                throw new IllegalArgumentException();
        }

    }

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

    /**
     * Edit the targeted argument and set its new value
     * 
     * @param cmdIndex the command's index to which the argument belongs to
     * @param argIndex the argument's index within its command's arguments
     * @param type     the new type for the argument
     * @param value    the new value for the argument
     */
    public void editArg(int cmdIndex, int argIndex, Models.Argument.Type type, Object value) {

        ScrollPane argCmdScrollPane = drive.lookup(Ids.CMD_ARG_SCROLLPANE_ID).nth(cmdIndex).query();

        Label argLabel = drive.from(argCmdScrollPane).lookup(Ids.ARGUMENT_VALUE_ID).nth(argIndex).query();
        drive.clickOn(argLabel);

        selectArgType(type, value);
    }

    // #endregion
}
