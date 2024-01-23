package Views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Utils.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/** Controller class for the main view/screen */
public class Main implements Initializable {

    // #region Attributes

    /** URL for the fxml file representing a command */
    private URL cmdBoxURL;

    /** URL for the fxml file representing the command page */
    private URL settingsURL;

    /** The Vboc containing the commands */
    @FXML
    public VBox commandVBox;

    /** The text object used to display the exit status */
    @FXML
    public Text status_text;

    /** Button to cancel all execution */
    @FXML
    public ImageView stopAllButton;

    // #endregion

    // #region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Alerts.init();
        cmdBoxURL = getClass().getResource("/fxml/command_box.fxml");
        settingsURL = Settings.class.getResource("/fxml/settings.fxml");
    }

    /** Switch to edit mode */
    public void setEditMode() {
        status_text.setText("Editing");

        Node addButton = commandVBox.getChildren().get(commandVBox.getChildren().size() - 1);
        addButton.setVisible(true);
        addButton.setDisable(false);

        stopAllButton.setVisible(false);
        stopAllButton.setDisable(true);

        Business.Command.resetAll(0, Business.Command.getCommands().size());
    }

    /** Switch to execution mode */
    public void setExecMode() {
        Node addButton = commandVBox.getChildren().get(commandVBox.getChildren().size() - 1);
        addButton.setVisible(false);
        addButton.setDisable(true);

        stopAllButton.setVisible(false);
        stopAllButton.setDisable(true);
    }

    /** Switch to paused execution mode */
    public void setPausedExecMode(String statusMessage) {
        status_text.setText(statusMessage);

        stopAllButton.setVisible(true);
        stopAllButton.setDisable(false);
    }

    /**
     * Delte an element from the command's Vbox
     * 
     * @param cmdNode node of the command to delete
     */
    public void deleteCmd(Node cmdNode) {
        commandVBox.getChildren().remove(cmdNode);
    }

    /**
     * Generate a new command view
     * 
     * @return the generated command view
     * @throws IOException
     */
    public Views.Command newCommandView() throws IOException {
        FXMLLoader loader = new FXMLLoader(cmdBoxURL);

        Parent commandNode = loader.load();
        Views.Command commandController = loader.getController();

        commandController.setCommandNode(commandNode);

        commandVBox.getChildren().add(commandVBox.getChildren().size() - 1, commandNode);

        return commandController;
    }

    /**
     * Add a new command box to the command list
     * 
     * @param event triggering mouse event
     * @throws IOException
     */
    @FXML
    public void add_command(MouseEvent event) throws IOException {

        Business.Command.addCommand(newCommandView(), null);
    }

    /**
     * Show the settings page
     * 
     * @param event
     * @throws IOException
     */
    @FXML
    public void open_settings(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(settingsURL);
        Parent node = loader.load();

        Business.App.getMainNode().getChildren().add(node);
    }

    /**
     * Cancel all execution
     * 
     * @param event
     */
    @FXML
    public void stopAll(MouseEvent event) {

        if (Alerts.getConfirmCancelAllAlert().showAndWait().orElse(ButtonType.CANCEL) == ButtonType.YES) {
            Business.App.setEditMode();
        }
    }

    /**
     * 
     * @param event
     */
    @FXML
    public void load(MouseEvent event) {
        if (Alerts.getConfirmLoadingAlert().showAndWait().orElse(null) != ButtonType.YES) {
            return;
        }

        File file = Utils.Utils.getFcWithFilter().showOpenDialog(new Stage());
        if (file == null) {
            return;
        }
        Utils.Load.load(file);
    }

    /**
     * 
     * @param event
     */
    @FXML
    public void save(MouseEvent event) {
        File file = Utils.Utils.getFcWithFilter().showSaveDialog(new Stage());
        if (file == null) {
            return;
        }
        Utils.Save.save(file);
    }

    // #endregion
}
