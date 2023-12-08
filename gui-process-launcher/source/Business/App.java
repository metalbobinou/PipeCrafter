package Business;

import javafx.scene.layout.StackPane;

/** Business class for the app */
public class App {

    // region Attributes

    /** The main screen controller */
    private static Views.Main mainController = null;

    private static StackPane mainNode = null;

    /** State if edits are allowed or not */
    private static boolean editModeOn = true;

    /** The step being executed or the step to execute next */
    private static int currentStep = 1;

    /**
     * State if a step is being executed or if execution is paused/edit mode
     * is on
     */
    private static boolean isExecuting = false;

    /** Time at which execution began */
    private static long execStartTime;

    /** State whether the execution was interupted */
    private static boolean execWasInterrupted = false;

    /** Message displayed when the execution is paused */
    private static final String pauseExecMessage = "Execution paused at step ";

    // endregion

    // region Methods

    /** Set the edit mode */
    public static void setEditMode() {
        currentStep = 1;
        editModeOn = true;
        isExecuting = false;
        mainController.setEditMode();
    }

    /**
     * Update app business variables when executing a command
     * 
     * @param command command to be executed
     */
    public static void setRun(Models.Command command) {
        currentStep = command.getPosition();
        editModeOn = false;
        execStartTime = System.currentTimeMillis();
        isExecuting = true;
        mainController.setExecMode();
    }

    /**
     * Update app business variables after executing a command
     * 
     * @param isOver true if all commands have been executed
     */
    public static void endRun(boolean isOver, boolean goToNext) {
        if (isOver) {
            currentStep = 1;
        } else if (goToNext) {
            currentStep++;
        }
        isExecuting = false;
        mainController
                .setPausedExecMode(isOver ? "Done, all commands executed" : pauseExecMessage + currentStep);
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

    // endregion

    // region Getters and Setters

    public static Views.Main getMainController() {
        return mainController;
    }

    public static void setMainController(Views.Main mainController) {
        if (App.mainController == null) {
            App.mainController = mainController;
        } else {
            System.err.println("Main controller reference not set as it has already been.");
        }
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

    public static long getExecStartTime() {
        return execStartTime;
    }

    public static void setExecStartTime(long execStartTime) {
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

    // endregion
}
