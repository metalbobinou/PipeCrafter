package Views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Models.Argument.Type;
import Utils.CommandBuilder;
import Utils.OutputStream;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/** Controller class for the output selector */
public class OutputSelector implements Initializable {

    // region Attributes

    /** The anchor pane, used to retreive the Stage */
    @FXML
    public AnchorPane anchorPane;

    /** Text used to display the full selected command */
    @FXML
    public Text cmdText;

    /** ComboBox to pick a command */
    @FXML
    public ComboBox<String> commandBox;

    /** ChoiceBox to choose the desired stream for the chosen command's output */
    @FXML
    public ChoiceBox<String> streamBox;

    /** CheckBox to display the full selected command */
    @FXML
    public CheckBox displayCheckbox;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Models.Command> cmdList = Business.Command.getCommands();
        for (int i = 0; i < Business.Command.getCommandReiceivingArgument() - 1; i++) {
            Models.Command cmd = cmdList.get(i);

            StringBuilder strbuidler = new StringBuilder(String.valueOf(cmd.getPosition())).append(" - ");
            if (cmd.getName().length() > 20) {
                strbuidler.append(cmd.getName().substring(0, 20));
            } else {
                strbuidler.append(cmd.getName());
            }
            commandBox.getItems().add(strbuidler.toString());
        }

        // Set default value to the previous step
        commandBox.setValue(commandBox.getItems().get(Business.Command.getCommandReiceivingArgument() - 2));
        commandBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                commandBox.setValue(oldValue);
            }
            if (newValue != oldValue) {
                dispCmd();
            }
        });

        streamBox.getItems().addAll("stdout", "stderr");
        streamBox.setValue("stdout");
        streamBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                streamBox.setValue(oldValue);
            }
        });
    }

    /**
     * If the display option checked, display the built command of the
     * selected step
     */
    private void dispCmd() {
        ObservableList<String> cmdBoxItems = commandBox.getItems();

        if (displayCheckbox.isSelected()) {
            int indexOfSelected = cmdBoxItems.indexOf(commandBox.getValue());
            if (indexOfSelected == -1) {
                cmdText.setText("error");
            } else {
                cmdText.setText(
                        CommandBuilder.getCommand(Business.Command.getCommands().get(indexOfSelected), true).get(0));
            }
        } else {
            cmdText.setText("");
        }
    }

    /**
     * Even handler for displayCheckbox
     * 
     * @param event
     */
    @FXML
    public void dispCmd(ActionEvent event) {
        dispCmd();
    }

    /**
     * Build the selected output as an argument
     * 
     * @param event
     */
    @FXML
    public void done(MouseEvent event) {
        ObservableList<String> cmdBoxItems = commandBox.getItems();

        Business.Argument.setAddedArg(new Models.Argument(Type.OUTPUT,
                new Object[] {
                        Integer.valueOf(cmdBoxItems.indexOf(commandBox.getValue()) + 1),
                        streamBox.getValue() == "stdout" ? OutputStream.OUT : OutputStream.ERR
                }));

        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    // endregion

}
