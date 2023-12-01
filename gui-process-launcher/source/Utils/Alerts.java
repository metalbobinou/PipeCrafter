package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** Class handling alerts displayed */
public class Alerts {

    // region Attributes

    /** Alert for when the maximum number of characeters has been reached */
    private static Alert maxCharAlert;

    private static Alert failedReadingOutputFileAlert;

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

    // endregion

}
