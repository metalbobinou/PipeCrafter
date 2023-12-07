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
                "This command cannot be executed because it contains invalid references. Delete or replace them before continuing.");
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

    // endregion

}
