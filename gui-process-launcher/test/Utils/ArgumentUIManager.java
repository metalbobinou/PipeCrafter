package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.testfx.util.WaitForAsyncUtils;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

/** Sub MainPage class to handle interactions with arguments */
public class ArgumentUIManager extends CommandUIManager {
    // #region Constructor

    public ArgumentUIManager(TestFXBase drive) {
        super(drive);
    }

    // #endregion

    // #region Methods

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

        new ArgumentSelectorPage(drive).selectArgType(type, value);
    }

    /**
     * Check that an argument has the correct values
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

        new ArgumentSelectorPage(drive).selectArgType(type, value);
    }

    // #endregion
}
