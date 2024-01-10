package Utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.testfx.util.WaitForAsyncUtils;

import Models.Command.State;
import Utils.OutputParameters.OutputStream;
import javafx.geometry.VerticalDirection;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
    public void check(Models.Command cmd, State expectedState, int expectedPosition,
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
     * Check a command and its outputs after it has been executed
     * 
     * @param cmd                  command to check
     * @param expectedState        the expected state post execution
     * @param expectedExitCode     the expected exit code
     * @param expectedStdoutOutput optional, the expected content of the stdout
     *                             output file
     * @param expectedStderrOutput optional, the expected content of the stderr
     *                             output file
     * @throws IOException
     */
    public void checkExec(Models.Command cmd, State expectedState, Integer expectedExitCode,
            String expectedStdoutOutput, String expectedStderrOutput) throws IOException {

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(cmd.getState(), expectedState);
        assertEquals(cmd.getExitCode(), expectedExitCode);
        if (expectedStdoutOutput != null) {
            File outputFile = new File(Business.Settings.getOutputSavingDirectory(),
                    String.valueOf(cmd.getPosition()) + OutputStream.OUT.getExtension());

            assertEquals(expectedStdoutOutput, Files.readString(outputFile.toPath()));
        }
        if (expectedStderrOutput != null) {
            File outputFile = new File(Business.Settings.getOutputSavingDirectory(),
                    String.valueOf(cmd.getPosition()) + OutputStream.ERR.getExtension());

            assertEquals(expectedStderrOutput, Files.readString(outputFile.toPath()));
        }
    }

    /**
     * Add a command
     * 
     * @param name optional, name to give the command
     * @param cmd  optional, command to assign
     */
    public void add(String name, String cmd) {
        try {
            drive.clickOn(Ids.ADD_CMD_BUTTON_ID);
        } catch (Exception e) {
            drive.scroll(30, VerticalDirection.UP);
            drive.clickOn(Ids.ADD_CMD_BUTTON_ID);
        }
        setDetails(Business.Command.getCommands().size() - 1, name, cmd);
    }

    /**
     * Fill in details for the specified command
     * 
     * @param cmdIndex the index of the targeted command
     * @param name     optional, name to give the command
     * @param cmd      optional, command to assign
     */
    public void setDetails(int cmdIndex, String name, String cmd) {
        if (name != null) {
            drive.clickOn((TextField) drive.find(Ids.CMD_NAME_TEXTFIELD_ID, cmdIndex));
            pressAndRelease(controlKey, KeyCode.A, KeyCode.BACK_SPACE);
            drive.write(name);
        }
        if (cmd != null) {
            drive.clickOn((TextField) drive.find(Ids.CMD_TEXTFIELD_ID, cmdIndex));
            pressAndRelease(controlKey, KeyCode.A, KeyCode.BACK_SPACE);
            drive.write(cmd);
        }

    }

    /**
     * Move one command to another's location
     * 
     * Note: waiting for the TODO implementation, can move 1 visible command to
     * another that is also on screen
     * 
     * @param sourceIndex      index where to drag
     * @param destinationIndex index where to drop
     */
    public void move(int sourceIndex, int destinationIndex) {

        // TODO scroll to source

        drive.drag((ImageView) drive.find(Ids.CMD_RUN_BUTTON_ID, sourceIndex), MouseButton.PRIMARY);

        // TODO scroll to destination

        drive.dropTo((ImageView) drive.find(Ids.CMD_RUN_BUTTON_ID, destinationIndex));
    }

    /**
     * Delete a command
     * 
     * Note: waiting for the TODO implementation, can only delete on screen cmds
     * 
     * @param cmdIndex index of the command to delete
     */
    public void delete(int cmdIndex) {
        // TODO scroll to command

        drive.clickOn((ImageView) drive.find(Ids.CMD_DELETE_BUTTON_ID, cmdIndex));
    }

    /**
     * Press the run button of the specified command
     * 
     * Note: waiting for the TODO implementation, can only press the run button
     * of a command that is visible on screen
     * 
     * @param cmdIndex index of the command to interact with
     */
    public void pressRun(int cmdIndex) {

        // TODO scroll to source

        try {
            drive.clickOn((ImageView) drive.find(Ids.CMD_RUN_BUTTON_ID, cmdIndex), MouseButton.PRIMARY);
        } catch (Exception e) {
            drive.scroll(30, VerticalDirection.UP);
        }

    }

    // #endregion
}
