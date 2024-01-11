package Views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Models.Argument.Type;
import Utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** Controller class for the argument type selector */
public class ArgumentTypeSelector implements Initializable {

    // #region Attributes

    /** URL for the fxml file representing a the output selector window */
    private static URL outputSelectorURL = null;

    /** The anchor pane, used to retreive the Stage */
    @FXML
    public AnchorPane anchorPane;

    /** Group containing elements to choose an output */
    @FXML
    public HBox outputGroup;

    // #endregion

    // #region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (outputSelectorURL == null) {
            outputSelectorURL = getClass().getResource("/fxml/outputSelector.fxml");
        }

        if (Business.Command.getCommandReiceivingArgument().getPosition() <= 1) {
            outputGroup.setVisible(false);
            outputGroup.setMouseTransparent(true);
        }
    }

    /**
     * Prompt the user for the file to use
     * 
     * @param event
     */
    @FXML
    public void file(MouseEvent event) {
        File file = null;
        // if the argument is being modified and not added, set defautl value
        if (Business.Argument.getAddedArgType() == Type.FILE) {
            Object value = Business.Argument.getAddedArgValue();
            if (value != null) {
                file = Utils.getFc(((File) value).getParentFile()).showOpenDialog(new Stage());
            }
        } else {
            file = Utils.getFc(null).showOpenDialog(new Stage());
        }

        if (file != null) {
            // If already set, the argument is being modified, otherwise it is
            // being added
            if (Business.Argument.isAddedArgSet()) {
                Business.Argument.popAddedArg().setArgument(Type.FILE, file);

            } else {
                Business.Argument.setAddedArg(new Models.Argument(Type.FILE, file));
            }
        }

        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    /**
     * Prompt the user to enter the desired text value
     * 
     * @param event
     */
    @FXML
    public void text(MouseEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text value");
        dialog.setHeaderText("Please enter your value:");
        dialog.setGraphic(null);

        // if the argument is being modified and not added, set default value to current
        if (Business.Argument.getAddedArgType() == Type.TEXT) {
            Object value = Business.Argument.getAddedArgValue();
            if (value != null) {
                dialog.getEditor().setText((String) value);
            }
        }

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(text -> {
            if (Business.Argument.isAddedArgSet()) {
                Business.Argument.popAddedArg().setArgument(Type.TEXT, text);
            } else {
                Business.Argument.setAddedArg(new Models.Argument(Type.TEXT, text));
            }
        });

        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    /**
     * Prompt the user to choose the desired output
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    public void output(MouseEvent event) throws IOException {
        // Show output selector window
        Parent root = FXMLLoader.load(outputSelectorURL);
        Stage popupStage = new Stage();
        popupStage.setTitle("Choose output");
        // freeze main window
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait();
        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    // #endregion

}
