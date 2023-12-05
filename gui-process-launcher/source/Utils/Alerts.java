package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** Class handling alerts displayed */
public class Alerts {

    // region Attributes

    /** Alert for when the maximum number of characeters has been reached */
    private static Alert maxCharAlert;

    /** Alert used when the output file could not be read */
    private static Alert failedReadingOutputFileAlert;

    /** Alert used when the entered path is invalid */
    private static Alert invalidPathAlert;

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
    }

    // endregion

    // region Getters and Setters

    public static Alert getMaxCharAlert() {
        return maxCharAlert;
    }

    public static void setMaxCharAlert(Alert maxCharAlert) {
        Alerts.maxCharAlert = maxCharAlert;
    }

    public static Alert getFailedReadingOutputFileAlert() {
        return failedReadingOutputFileAlert;
    }

    public static void setFailedReadingOutputFileAlert(Alert failedReadingFileAlert) {
        Alerts.failedReadingOutputFileAlert = failedReadingFileAlert;
    }

    public static Alert getInvalidPathAlert() {
        return invalidPathAlert;
    }

    public static void setInvalidPathAlert(Alert invalidPathAlert) {
        Alerts.invalidPathAlert = invalidPathAlert;
    }

    // endregion

}
