package Utils;

import javafx.scene.control.ButtonType;

/** Class handling loading of data from JSON */
public class Load {

    // region Methods

    /** TODO */
    public static void load() {

        if (Alerts.getConfirmLoadingAlert().showAndWait().orElse(null) != ButtonType.YES) {
            return;
        }
        // TODO
        // reset all states to default before loading state
        // if load state too set "stateSaveFile" to it and if not to null
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    // endregion
}
