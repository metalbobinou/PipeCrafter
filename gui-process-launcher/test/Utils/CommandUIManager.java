package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testfx.util.WaitForAsyncUtils;

import Models.Command.State;
import javafx.geometry.VerticalDirection;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

/** Sub MainPage class to handle interactions with commands */
public class CommandUIManager extends MainPage {
    // #region Constructor

    public CommandUIManager(TestFXBase drive) {
        super(drive);
    }

    // #endregion

    // #region Methods

    /**
     * Check that an argument has the correct values
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
        assertEquals(cmd.getName(), name);
        assertEquals(cmd.getCmd(), cmdText);
    }

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
            TextField nameField = drive.find(Ids.CMD_NAME_TEXTFIELD_ID, cmdIndex);
            drive.clickOn(nameField);
            drive.write(name);
        }
        if (cmd != null) {
            TextField cmdField = drive.find(Ids.CMD_TEXTFIELD_ID, cmdIndex);
            drive.clickOn(cmdField);
            drive.write(cmd);
        }

    }

    /**
     * Move one command to another's location
     * 
     * Note: waiting for the TODO implementation, can move one visible command to
     * another that is also on screen
     * 
     * @param sourceIndex
     * @param destinationIndex
     */
    public void moveCmd(int sourceIndex, int destinationIndex) {

        // TODO scroll to source

        drive.drag((ImageView) drive.find(Ids.CMD_RUN_BUTTON_ID, sourceIndex), MouseButton.PRIMARY);

        // TODO scroll to destination

        drive.dropTo((ImageView) drive.find(Ids.CMD_RUN_BUTTON_ID, destinationIndex));
    }

    // #endregion
}
