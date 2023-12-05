package Views;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import Utils.Alerts;
import Utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** Controller class for the settings page */
public class Settings implements Initializable {

    // region Attributes

    /** The textfield used to specify the execution directory */
    @FXML
    public TextField execFolderField;

    /** The textfield used to specify the output directory */
    @FXML
    public TextField outputFolderField;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        execFolderField.setText(Business.Settings.getExecutionDirectory().getAbsolutePath());
        outputFolderField.setText(Business.Settings.getOutputSavingDirectory().getAbsolutePath());
    }

    /**
     * Go back to the main screen
     * 
     * @param event
     */
    @FXML
    public void backToMain(MouseEvent event) {
        // The last element that was added is the node for the settings page
        Business.App.getMainNode().getChildren().remove(Business.App.getMainNode().getChildren().size() - 1);
    }

    /**
     * Check the entered path for the execution directory, set if correct,
     * alert if not
     * 
     * @param event
     */
    @FXML
    public void fillExecFolder(ActionEvent event) {
        try {
            Business.Settings.setExecutionDirectory(execFolderField.getText());
        } catch (Exception e) {
            Alerts.getInvalidPathAlert().showAndWait();
            execFolderField.setText(Business.Settings.getExecutionDirectory().getAbsolutePath());
        }
    }

    /**
     * Check the entered path for the output directory, set if correct,
     * alert if not
     * 
     * @param event
     */
    @FXML
    public void fillOutputFolder(ActionEvent event) {
        try {
            Business.Settings.setOutputSavingDirectory(outputFolderField.getText());
        } catch (Exception e) {
            Alerts.getInvalidPathAlert().showAndWait();
            outputFolderField.setText(Business.Settings.getOutputSavingDirectory().getAbsolutePath());
        }
    }

    /**
     * Get, check and set the execution directory via directory chooser
     * 
     * @param event
     */
    @FXML
    public void getPathExecFolder(MouseEvent event) {
        File directory = Utils.getDc().showDialog(new Stage());

        if (directory != null) {
            try {
                Business.Settings.setExecutionDirectory(directory);
                execFolderField.setText(Business.Settings.getExecutionDirectory().getAbsolutePath());
            } catch (Exception e) {
                Alerts.getInvalidPathAlert().showAndWait();
            }
        }
    }

    /**
     * Get, check and set the output directory via directory chooser
     * 
     * @param event
     */
    @FXML
    public void getPathOutputFolder(MouseEvent event) {
        File directory = Utils.getDc().showDialog(new Stage());

        if (directory != null) {
            try {
                Business.Settings.setOutputSavingDirectory(directory);
                outputFolderField.setText(Business.Settings.getOutputSavingDirectory().getAbsolutePath());
            } catch (Exception e) {
                Alerts.getInvalidPathAlert().showAndWait();
            }
        }
    }

    // endregion
}
