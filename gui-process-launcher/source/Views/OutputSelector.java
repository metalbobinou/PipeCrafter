package Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/** //TODO */
public class OutputSelector {

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Text cmdText;

    @FXML
    public ComboBox<?> commandBox;

    @FXML
    public CheckBox displayCheckbox;

    @FXML
    public Button doneButton;

    @FXML
    public ChoiceBox<?> streamBox;

    @FXML
    public void done(MouseEvent event) {

    }

}
