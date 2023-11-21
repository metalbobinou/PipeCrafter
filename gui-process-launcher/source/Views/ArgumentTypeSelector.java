package Views;

import java.util.Optional;

import Business.Argument;
import Models.Argument.Type;
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
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @FXML
    public void text(MouseEvent event) {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text value");
        dialog.setHeaderText("Please enter your value:");
        dialog.setContentText("");
        Optional<String> result = dialog.showAndWait();

        Business.Argument.setAddedArg(new Models.Argument(Type.TEXT, result.orElse("")));
        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    @FXML
    public void output(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    // endregion

}
