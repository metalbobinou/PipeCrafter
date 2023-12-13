package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/** Class handling alerts displayed */
public class Alerts {

    // region Attributes

    /** Alert for when the maximum number of characeters has been reached */
    private static Alert maxCharAlert;

    /** Alert used when the output file could not be read */
    private static Alert failedReadingOutputFileAlert;

    /** Alert used when the entered path is invalid */
    private static Alert invalidPathAlert;

    /** Alert used to confirm cancellation of the execution pipeline */
    private static Alert confirmCancelAllAlert;

    /** Alert used to confirm cancellation of the current execution */
    private static Alert executionOnGoingAlert;

    /** Alert used to warn about a reference to a command that has become invalid */
    private static Alert invalidCommandRefAlert;

    /** Alert used to warn the user that an edit is impossible in this state */
    private static Alert restrictedEditAlert;

    /** Alert for when running a command is impossible due to invalid references */
    private static Alert forbiddenRunAlert;

    /**
     * Alert for the user to confirm they want to restart execution from a
     * previous step
     */
    private static Alert confirmRestartFromAlert;

    /** Alert for when skipping a command is impossible as it is referenced later */
    private static Alert forbiddenSkipAlert;

    /**
     * Alert for the user to confirm they want to delete a command that is
     * referenced by others
     */
    private static Alert confirmDeleteReferencedCmdAlert;

    /** Alert the user that there was an error saving the pipeline */
    private static Alert errorSavingPipelineAlert;

    /** Alert the user that there was an error saving the state */
    private static Alert errorSavingStateAlert;

    /** Alert the user that there was an error loading a pipeline file */
    private static Alert errorLoadingPipelineAlert;

    /** Alert the user that there was an error loading a state file */
    private static Alert errorLoadingStateAlert;

    /**
     * Alert to let the user confirm they want to load and overwrite the
     * existing pipeline
     */
    private static Alert confirmLoadingAlert;

    // endregion

    // region Methods

    /** Initialize all used alerts */
    public static void init() {
        maxCharAlert = new Alert(AlertType.WARNING);
        maxCharAlert.setTitle("Warning");
        maxCharAlert.setContentText("Character limit reached");

        failedReadingOutputFileAlert = new Alert(AlertType.ERROR);
        failedReadingOutputFileAlert.setTitle("Error");
        failedReadingOutputFileAlert.setContentText("Error reading output file");

        invalidPathAlert = new Alert(AlertType.ERROR);
        invalidPathAlert.setTitle("Error");
        invalidPathAlert
                .setContentText("The requested directory does not exist, or does not have the right permissions");

        confirmCancelAllAlert = new Alert(AlertType.CONFIRMATION);
        confirmCancelAllAlert.setTitle("Warning");
        confirmCancelAllAlert
                .setContentText("Are you sure you want to cancel all operations and go back to free edit mode?");
        confirmCancelAllAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

        executionOnGoingAlert = new Alert(AlertType.CONFIRMATION);
        executionOnGoingAlert.setTitle("Warning");
        executionOnGoingAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

        invalidCommandRefAlert = new Alert(AlertType.WARNING);
        invalidCommandRefAlert.setTitle("Warning");
        invalidCommandRefAlert.setContentText("Ohoh, your action broke some references to commands...");

        restrictedEditAlert = new Alert(AlertType.WARNING);
        restrictedEditAlert.setTitle("Warning");
        restrictedEditAlert.setContentText(
                "You can't modify a command that is being or has already been run. Please stop all execution or reset the pipeline and try again.");

        forbiddenRunAlert = new Alert(AlertType.ERROR);
        forbiddenRunAlert.setTitle("Error");
        forbiddenRunAlert.setContentText(
                "This command cannot be executed because it contains invalid references or references to command that were skipped. Delete, replace, move or execute them to continue.");

        confirmRestartFromAlert = new Alert(AlertType.CONFIRMATION);
        confirmRestartFromAlert.setTitle("Warning");
        confirmRestartFromAlert
                .setContentText(
                        "Are you sure you want to restart execution from this command? All outputs generated from this point will be lost.");
        confirmRestartFromAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

        forbiddenSkipAlert = new Alert(AlertType.CONFIRMATION);
        forbiddenSkipAlert.setTitle("Warning");
        forbiddenSkipAlert.setContentText(
                "Jumping to this command will result in unusable commands down the line as they reference commands that would be skipped. Do you want to continue?");
        forbiddenSkipAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

        confirmDeleteReferencedCmdAlert = new Alert(AlertType.CONFIRMATION);
        confirmDeleteReferencedCmdAlert.setTitle("Warning");
        confirmDeleteReferencedCmdAlert
                .setContentText(
                        "This command is referenced by others, deleting it will break these. Do you want to continue?");
        confirmDeleteReferencedCmdAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);

        errorSavingPipelineAlert = new Alert(AlertType.ERROR);
        errorSavingPipelineAlert.setTitle("Error");
        errorSavingPipelineAlert.setContentText(
                "Error saving the pipeline to the given file.");

        errorSavingStateAlert = new Alert(AlertType.ERROR);
        errorSavingStateAlert.setTitle("Error");
        errorSavingStateAlert.setContentText(
                "Error saving the state to the given file.");

        errorLoadingPipelineAlert = new Alert(AlertType.ERROR);
        errorLoadingPipelineAlert.setTitle("Error");
        errorLoadingPipelineAlert.setContentText(
                "Error loading the pipeline from the chosen file.");

        errorLoadingStateAlert = new Alert(AlertType.ERROR);
        errorLoadingStateAlert.setTitle("Error");
        errorLoadingStateAlert.setContentText(
                "Error loading the state from the chosen file.");

        confirmLoadingAlert = new Alert(AlertType.CONFIRMATION);
        confirmLoadingAlert.setTitle("Warning");
        confirmLoadingAlert.setContentText(
                "Loading another pipeline will overwrite the existing one. Do you want to continue?");
        confirmLoadingAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
    }

    // endregion

    // region Getters and Setters

    public static Alert getMaxCharAlert() {
        return maxCharAlert;
    }

    public static Alert getFailedReadingOutputFileAlert() {
        return failedReadingOutputFileAlert;
    }

    public static Alert getInvalidPathAlert() {
        return invalidPathAlert;
    }

    public static Alert getConfirmCancelAllAlert() {
        return confirmCancelAllAlert;
    }

    public static Alert getExecutionOnGoingAlert() {
        executionOnGoingAlert
                .setContentText("Step " + Business.App.getCurrentStep()
                        + " is being executed. Proceeding will abort it. Do you want to continue?");
        return executionOnGoingAlert;
    }

    public static Alert getInvalidCommandRefAlert() {
        return invalidCommandRefAlert;
    }

    public static Alert getRestrictedEditAlert() {
        return restrictedEditAlert;
    }

    public static Alert getForbiddenRunAlert() {
        return forbiddenRunAlert;
    }

    public static Alert getConfirmRestartFromAlert() {
        return confirmRestartFromAlert;
    }

    public static Alert getForbiddenSkipAlert() {
        return forbiddenSkipAlert;
    }

    public static Alert getConfirmDeleteReferencedCmdAlert() {
        return confirmDeleteReferencedCmdAlert;
    }

    public static Alert getErrorSavingPipelineAlert() {
        return errorSavingPipelineAlert;
    }

    public static Alert getErrorSavingStateAlert() {
        return errorSavingStateAlert;
    }

    public static Alert getErrorLoadingPipelineAlert() {
        return errorLoadingPipelineAlert;
    }

    public static Alert getErrorLoadingStateAlert() {
        return errorLoadingStateAlert;
    }

    public static Alert getConfirmLoadingAlert() {
        return confirmLoadingAlert;
    }

    // endregion

}
