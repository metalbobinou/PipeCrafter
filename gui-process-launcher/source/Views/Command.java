package Views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

/** Controller class for a command */
public class Command implements Initializable {

    // region Attributes

    /** The FX object associated with this controller */
    private Node commandNode;

    /** Command model object associated with this view */
    private Models.Command commandModel;

    /** A key used to set a "type" for dragged elements */
    private static final String TAB_DRAG_KEY = "command";

    /** Object used to handle drag and drop */
    private ObjectProperty<Node> draggingTab = new SimpleObjectProperty<Node>();

    /** String representing the CSS styling of the scroll bar */
    private String scrollBarStyleCSS;

    /**
     * URL for the fxml file representing a the argument type selector
     * window
     */
    private URL argumentTypeSelectorURL;

    /** URL for the fxml file representing an argument */
    private URL argumentURL;

    /** Image representing the restart icon */
    private Image restart_icon;

    /** Image representing the start icon */
    private Image start_icon;

    /** Image representing the stop icon */
    private Image stop_icon;

    /** Image representing the start from icon */
    private Image start_from_icon;

    /** The scroll pane used for the arguments */
    @FXML
    public ScrollPane arguments_scrollPane;

    /** The Hbox containing the arguments */
    @FXML
    public HBox argumentsHbox;

    /** Text used to inform about exit code */
    @FXML
    public Text exitCode_label;

    /** Borders of the box */
    @FXML
    public Rectangle boxBorders;

    /** Image representing the (re)run button */
    @FXML
    public ImageView run_button;

    /** Text used to state the step of this command */
    @FXML
    public Text step_label;

    /** Text field used by the user to enter their command */
    @FXML
    public TextField textField;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollBarStyleCSS = getClass().getResource("/css/scrollBarStyle.css").toExternalForm();

        argumentTypeSelectorURL = getClass().getResource("/fxml/argumentTypeSelector.fxml");

        argumentURL = getClass().getResource("/fxml/argument.fxml");

        restart_icon = new Image(getClass().getResource("/images/restart_icon.png").toString());

        start_icon = new Image(getClass().getResource("/images/start_icon.png").toString());

        stop_icon = new Image(getClass().getResource("/images/stop_icon.png").toString());

        start_from_icon = new Image(getClass().getResource("/images/start_from_icon.png").toString());
    }

    /**
     * Associate the controller with its node and model, and set FX objects
     * with model's information and for
     * drag and drop
     * 
     * @param node  the controller's node
     * @param model the controller's model
     */
    public void setUp(Node node, Models.Command model) {
        this.commandNode = node;
        this.commandModel = model;
        step_label.setText(String.valueOf(model.getPosition()));

        updateState();

        arguments_scrollPane.getStylesheets()
                .add(scrollBarStyleCSS);

        // region drag and drop implementation
        node.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                final Dragboard dragboard = event.getDragboard();
                if (dragboard.hasString()
                        && TAB_DRAG_KEY.equals(dragboard.getString())
                        && draggingTab.get() != null) {
                    event.acceptTransferModes(TransferMode.MOVE);
                    event.consume();
                }
            }
        });

        node.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(final DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    Pane parent = (Pane) node.getParent();
                    Object source = event.getGestureSource();
                    int sourceIndex = parent.getChildren().indexOf(source);
                    int targetIndex = parent.getChildren().indexOf(node);
                    List<Node> nodes = new ArrayList<Node>(parent.getChildren());
                    if (sourceIndex < targetIndex) {
                        Collections.rotate(
                                nodes.subList(sourceIndex, targetIndex + 1), -1);
                    } else {
                        Collections.rotate(
                                nodes.subList(targetIndex, sourceIndex + 1), 1);
                    }
                    parent.getChildren().clear();
                    parent.getChildren().addAll(nodes);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        node.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard dragboard = node.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(TAB_DRAG_KEY);
                dragboard.setContent(clipboardContent);
                draggingTab.set(node);
                event.consume();
            }
        });
        // endregion
    }

    /**
     * Add an argument to the command
     * 
     * @param event triggering mouse event
     * @throws IOException
     */
    @FXML
    public void add_argument(MouseEvent event) throws IOException {

        // Show type selector window
        Parent root = FXMLLoader.load(argumentTypeSelectorURL);
        Stage popupStage = new Stage();
        popupStage.setTitle("Choose argument type");
        // freeze main window
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(root));
        popupStage.showAndWait();

        if (!Business.Argument.isAddedArgSet()) {
            // Operation aborted
            return;
        }

        FXMLLoader loader = new FXMLLoader(argumentURL);

        Parent argumentNode = loader.load();
        Argument argumentController = loader.getController();

        Business.Argument.addArgument(commandModel, argumentController, argumentNode);
        argumentsHbox.getChildren().add(argumentNode);
    }

    /** Update all UI elements of the command based on its model's state */
    public void updateState() {
        switch (commandModel.getState()) {
            case ALREADY_RUN:
                run_button.setImage(restart_icon);
                boxBorders.setStroke(Color.GRAY);
                exitCode_label.setText("Exit code: " + commandModel.getExitCode());
                break;
            case NEXT_TO_RUN:
                run_button.setImage(start_icon);
                boxBorders.setStroke(Color.BLUE);
                break;
            case RUNNING:
                run_button.setImage(stop_icon);
                boxBorders.setStroke(Color.VIOLET);
                break;
            case TO_RUN:
                run_button.setImage(start_from_icon);
                boxBorders.setStroke(Color.WHITE);
                break;
        }
    }

    @FXML
    public void delete(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    /**
     * Trigger the execution of the selected command
     * 
     * @param event
     */
    @FXML
    public void run(MouseEvent event) {
        commandModel.setCmd(textField.getText());
        Business.Command.decideExec(commandModel);
        updateState();
    }

    // endregion

    // region Getters and Setters

    public Node getCommandNode() {
        return commandNode;
    }

    public void setCommandNode(Node node) {
        this.commandNode = node;
    }

    // endregion
}
