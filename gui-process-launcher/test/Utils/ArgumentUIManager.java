package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

/** Sub MainPage class to handle interactions with arguments */
public class ArgumentUIManager extends CommandUIManager {
    // #region Constructor

    public ArgumentUIManager(TestFXBase drive) {
        super(drive);
    }

    // #endregion

    // #region Methods

    /**
     * Check that an argument has the correct values
     * 
     * @param cmd                 command to which the argument belongs
     * @param expectedArgListSize the command's arguments list's expected size
     * @param arg                 the argument to check
     * @param type                the expected type for the argument
     * @param value               the expected value for the argument
     */
    public void check(Models.Command cmd, int expectedArgListSize, Models.Argument arg, Models.Argument.Type type,
            Object value) {

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(cmd.getArgumentList().size(), expectedArgListSize);

        assertEquals(arg.getMotherCommand(), cmd);
        assertEquals(arg.getType(), type);

        switch (type) {
            case FILE:
                assertEquals(((File) arg.getObjectValue()).getAbsolutePath(), value.toString());
                break;
            case TEXT:
                assertEquals(arg.getObjectValue().toString(), value.toString());
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
     * Start adding an argument to the Nth command without selecting a type
     * 
     * Note: waiting for the TODO implementation, can only add an argument to
     * the commands visible on screen when the function is called
     * 
     * @param cmdIndex the index of the command to add an argument to
     */
    public void add(int cmdIndex) {
        // TODO scroll to command
        drive.clickOn((ImageView) drive.find(Ids.CMD_ADD_ARG_BUTTON_ID, cmdIndex));
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
    public void add(int cmdIndex, Models.Argument.Type type, Object value) {
        // TODO scroll to command
        drive.clickOn((ImageView) drive.find(Ids.CMD_ADD_ARG_BUTTON_ID, cmdIndex));

        new ArgumentSelectorPage(drive).selectArgType(type, value);
    }

    /**
     * Edit the targeted argument and set its new value
     * 
     * @param cmdIndex the command's index to which the argument belongs to
     * @param argIndex the argument's index within its command's arguments
     * @param type     the new type for the argument
     * @param value    the new value for the argument
     */
    public void edit(int cmdIndex, int argIndex, Models.Argument.Type type, Object value) {

        ScrollPane cmdArgScrollPane = drive.find(Ids.CMD_ARG_SCROLLPANE_ID, cmdIndex);

        drive.clickOn((Label) drive.from(cmdArgScrollPane).lookup(Ids.ARGUMENT_VALUE_ID).nth(argIndex).query());

        new ArgumentSelectorPage(drive).selectArgType(type, value);
    }

    /**
     * Move a command's argument
     * 
     * Note: waiting for the TODO implementation, can move 1 visible argument to
     * another that is also on screen
     * 
     * @param cmdIndex
     * @param sourceIndex      index where to drag
     * @param destinationIndex index where to drop
     */
    public void move(int cmdIndex, int sourceIndex, int destinationIndex) {

        // TODO scroll to source

        ScrollPane cmdArgScrollPane = drive.find(Ids.CMD_ARG_SCROLLPANE_ID, cmdIndex);

        drive.drag((Label) drive.from(cmdArgScrollPane).lookup(Ids.ARGUMENT_VALUE_ID).nth(sourceIndex).query(),
                MouseButton.PRIMARY);

        // TODO scroll to destination

        drive.dropTo((Label) drive.from(cmdArgScrollPane).lookup(Ids.ARGUMENT_VALUE_ID).nth(destinationIndex).query());
    }

    /**
     * Delete the specified argument
     * 
     * Note: waiting for the TODO implementation, can only delete on screen args
     * 
     * @param cmdIndex index of the argument's command
     * @param argIndex index of the targeted argument
     */
    public void delete(int cmdIndex, int argIndex) {
        // TODO scroll to argument

        ScrollPane cmdArgScrollPane = drive.find(Ids.CMD_ARG_SCROLLPANE_ID, cmdIndex);

        drive.clickOn(
                (ImageView) drive.from(cmdArgScrollPane).lookup(Ids.DELETE_ARGUMENT_BUTTON_ID).nth(argIndex).query());

    }

    // #endregion
}
