package Business;

import javafx.scene.layout.StackPane;

/** Business class for the app */
public class App {

    // region Attributes

    /** The main screen controller */
    private static Views.Main mainController = null;

    private static StackPane mainNode = null;

    /** States if edits are allowed or not */
    private static boolean editModeOn = true;

    /** The step being executed or the step to execute next */
    private static int currentStep = 1;

    /**
     * States if s step is being executed or if execution is paused/edit mode
     * is on
     */
    private static boolean isExecuting = false;

    /** Time at which execution began */
    private static long execStartTime;

    // endregion

    // region Methods

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
    public static void endRun(boolean isOver) {
        if (isOver) {
            currentStep = 0;
        }
        currentStep++;
        isExecuting = false;
        mainController
                .setPausedExecMode(isOver ? "Done, all commands executed" : "Execution paused at step " + currentStep);
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

    // endregion
}
