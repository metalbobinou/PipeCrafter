package Business;

import java.time.Instant;

import Utils.Save;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import java.time.Duration;

/** Business class for the app */
public class App {

    // #region Attributes

    /** The main screen controller */
    private static Views.Main mainController = null;

    private static StackPane mainNode = null;

    /** State if edits are allowed or not */
    private static boolean editModeOn = true;

    /** The step being executed or the step to execute next */
    private static int currentStep = 1;

    /** State if the execution was completed or not */
    private static boolean isOver = false;

    /**
     * State if a step is being executed or if execution is paused/edit mode
     * is on
     */
    private static boolean isExecuting = false;

    /** Time at which execution began */
    private static Instant execStartTime;

    /** Timeline object to update timer */
    private static Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), e -> {
        updateTimer();
    }));

    /** State whether the execution was interupted */
    private static boolean execWasInterrupted = false;

    /** Message displayed when the execution is paused */
    private static final String pauseExecMessage = "Execution paused at step ";

    // #endregion

    // #region Methods

    /** Set the edit mode */
    public static void setEditMode() {
        currentStep = 1;
        editModeOn = true;
        isExecuting = false;
        mainController.setEditMode();
        isOver = false;
    }

    /**
     * Update app business variables when executing a command
     * 
     * @param command command to be executed
     */
    public static void setRun(Models.Command command) {
        currentStep = command.getPosition();
        editModeOn = false;
        execStartTime = Instant.now();
        isExecuting = true;
        isOver = false;
        mainController.setExecMode();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        updateTimer();
    }

    /**
     * Update app business variables after executing a command
     * 
     * @param isOver true if all commands have been executed
     */
    public static void endRun(boolean isOver, boolean goToNext) {
        timeline.stop();
        App.isOver = isOver;
        if (goToNext && !isOver) {
            currentStep++;
        }
        isExecuting = false;
        mainController
                .setPausedExecMode(isOver ? "Done, all commands executed" : pauseExecMessage + currentStep);
        Utils.Save.updateState();
    }

    /**
     * Update app variales when restarting from a given command
     * 
     * @param position position of the command that is restarted
     */
    public static void restartFrom(int position) {
        currentStep = position;
        mainController.setPausedExecMode(pauseExecMessage + currentStep);
    }

    /**
     * Reset the app to its default state: delete all commands, set edit mode
     * and reset the state save file
     */
    public static void resetAll() {
        Save.setStateSaveFile(null);
        Business.Command.deleteAllCmd();
        setEditMode();
    }

    /**
     * Set the app's state and global settings based on the given state
     * 
     * @param state the loaded state to use
     */
    public static void setState(Utils.Save.State state) {
        currentStep = state.currentCmdIndex + 1;
        isOver = state.isOver;
        editModeOn = state.editModeOn;
        if (!editModeOn) {
            mainController.setExecMode();
            mainController
                    .setPausedExecMode(isOver ? "Done, all commands executed" : pauseExecMessage + currentStep);

        }
    }

    /**
     * Return the index in the Business.Command.commands list of the command
     * being executed or the next to be
     * 
     * @return the index as an int
     */
    public static int getCurrentCommandIndex() {
        return currentStep - 1;
    }

    /**
     * Get execWasInterrupted and reset it to false
     * 
     * @return execWasInterrupted
     */
    public static boolean getAndResetExecWasInterrupted() {
        boolean res = execWasInterrupted;
        execWasInterrupted = false;
        return res;
    }

    /** Method to update the text displaying the timer on run */
    public static void updateTimer() {
        if (isExecuting) {
            Duration duration = Duration.between(execStartTime, Instant.now());
            mainController.status_text
                    .setText(new StringBuilder("Executing step ").append(currentStep).append(" for ")
                            .append(duration.toDays()).append(" d ").append(duration.toHoursPart()).append(" h ")
                            .append(duration.toMinutesPart()).append(" m ").append(duration.toSecondsPart())
                            .append(" s")
                            .toString());
        }
    }

    // #endregion

    // #region Getters and Setters

    public static Views.Main getMainController() {
        return mainController;
    }

    public static void setMainController(Views.Main mainController) {
        App.mainController = mainController;
    }

    public static boolean isEditModeOn() {
        return editModeOn;
    }

    public static void setEditModeOn(boolean editModeOn) {
        App.editModeOn = editModeOn;
    }

    public static int getCurrentStep() {
        return currentStep;
    }

    public static void setCurrentStep(int currentStep) {
        App.currentStep = currentStep;
    }

    public static boolean isExecuting() {
        return isExecuting;
    }

    public static void setExecuting(boolean isExecuting) {
        App.isExecuting = isExecuting;
    }

    public static Instant getExecStartTime() {
        return execStartTime;
    }

    public static void setExecStartTime(Instant execStartTime) {
        App.execStartTime = execStartTime;
    }

    public static StackPane getMainNode() {
        return mainNode;
    }

    public static void setMainNode(StackPane mainNode) {
        App.mainNode = mainNode;
    }

    public static void setExecWasInterrupted(boolean execWasInterrupted) {
        App.execWasInterrupted = execWasInterrupted;
    }

    public static Timeline getTimeline() {
        return timeline;
    }

    public static boolean isOver() {
        return isOver;
    }

    // #endregion
}
