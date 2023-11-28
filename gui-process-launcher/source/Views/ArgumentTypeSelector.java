package Views;

import java.io.File;
import java.util.Optional;

import Models.Argument.Type;
import Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** Controller class for the argument type selector */
public class ArgumentTypeSelector {

    // region Attributes

    /** The anchor pane, used to retreive the Stage */
    @FXML
    public AnchorPane anchorPane;

    // endregion

    // region Methods

    @FXML
    public void file(MouseEvent event) {
        File file = Utils.getFc().showOpenDialog(new Stage());

        if (file != null) {
            Business.Argument.setAddedArg(new Models.Argument(Type.FILE, file));
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
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(text -> {
            Business.Argument.setAddedArg(new Models.Argument(Type.TEXT, text));
        });

        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    @FXML
    public void output(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    // endregion

}
