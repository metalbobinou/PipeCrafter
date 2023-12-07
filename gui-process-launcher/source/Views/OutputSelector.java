package Views;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Models.Argument.Type;
import Utils.CommandBuilder;
import Utils.OutputParameters;
import Utils.OutputParameters.Format;
import Utils.OutputParameters.OutputStream;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.ListView;

/** Controller class for the output selector */
public class OutputSelector implements Initializable {

    // region Attributes

    /** Value used to choose the stream as stdout */
    private final String str4stdout = "stdout";

    /** Value used to choose the stream as stderr */
    private final String str4sterr = "stderr";

    /** Value used to choose the format as path */
    private final String str4path = "Path to file";

    /** Value used to choose the format as path */
    private final String str4content = "File's content";

    /** The anchor pane used to retreive the Stage */
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

    /** ChoiceBox to choose the desired format for the chosen command's output */
    @FXML
    public ChoiceBox<String> formatBox;

    /** CheckBox to display the full selected command */
    @FXML
    public CheckBox displayCheckbox;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // region Set up for commandBox
        List<Models.Command> cmdList = Business.Command.getCommands();
        for (int i = 0; i < Business.Command.getCommandReiceivingArgument().getPosition() - 1; i++) {
            Models.Command cmd = cmdList.get(i);

            commandBox.getItems().add(new StringBuilder(String.valueOf(cmd.getPosition())).append(" - ")
                    .append(cmd.getName()).toString());
        }

        // Add tooltip
        commandBox.setTooltip(null);
        commandBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setTooltip(null);
                        } else {
                            if (item.length() > 40) {
                                setText(item.substring(0, 40) + "...");
                            } else {
                                setText(item);
                            }
                            setTooltip(new Tooltip(item));
                        }
                    }
                };
            }
        });

        // endregion

        // Set default value to the previous step
        commandBox
                .setValue(commandBox.getItems().get(Business.Command.getCommandReiceivingArgument().getPosition() - 2));
        commandBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                dispCmd();
            }
        });

        streamBox.getItems().addAll(str4stdout, str4sterr);
        streamBox.setValue(str4stdout);

        formatBox.getItems().addAll(str4path, str4content);
        formatBox.setValue(str4path);
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
                cmdText.setText("<error>");
            } else {
                String command = CommandBuilder.getCommand(Business.Command.getCommands().get(indexOfSelected), true)
                        .get(0);
                if (command.length() >= 60) {
                    command = command.substring(0, 60) + "...";
                }
                cmdText.setText(command);
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

        OutputParameters op = new OutputParameters(
                Business.Command.getCommands().get(Integer.valueOf(cmdBoxItems.indexOf(commandBox.getValue()))),
                streamBox.getValue() == str4stdout ? OutputStream.OUT : OutputStream.ERR,
                formatBox.getValue() == str4path ? Format.PATH : Format.CONTENT);

        if (Business.Argument.isAddedArgSet()) {
            Business.Argument.popAddedArg().setArgument(Type.OUTPUT, op);
        } else {
            Business.Argument.setAddedArg(new Models.Argument(Type.OUTPUT, op));
        }

        ((Stage) anchorPane.getScene().getWindow()).close();
    }

    // endregion

}
