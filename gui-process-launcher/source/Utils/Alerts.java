package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** Class handling alerts displayed */
public class Alerts {

    // region Attributes

    private static Alert maxCharAlert;

    // endregion

    // region Methods

    public static void init() {
        maxCharAlert = new Alert(AlertType.WARNING);
        maxCharAlert.setTitle("Warning");
        maxCharAlert.setContentText("Character limit reached");
    }

    // endregion

    // region Getters and Setters

    public static Alert getMaxCharAlert() {
        return maxCharAlert;
    }

    public static void setMaxCharAlert(Alert maxCharAlert) {
        Alerts.maxCharAlert = maxCharAlert;
    }

    // endregion

}
