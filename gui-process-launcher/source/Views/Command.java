package Views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

/** Controller class for a command */
public class Command {

    // region Attributes

    /** The FX object associated with this controller */
    private Node commandNode;

    /** Command model object associated with this view */
    private Models.Command commandModel;

    /** A key used to set a "type" for dragged elements */
    private static final String TAB_DRAG_KEY = "command";

    /** Object used to handle drag and drop */
    private ObjectProperty<Node> draggingTab = new SimpleObjectProperty<Node>();

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

    /**
     * Associate the controller with its node and model, and set FX objects for
     * drag and drop
     * 
     * @param node  the controller's node
     * @param model the controller's model
     */
    public void setUp(Node node, Models.Command model) {
        this.commandNode = node;
        this.commandModel = model;

        arguments_scrollPane.getStylesheets()
                .add(getClass().getResource("/css/scrollBarStyle.css").toExternalForm());

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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/argumentTypeSelector.fxml"));
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/argument.fxml"));

        Parent argumentNode = loader.load();
        Argument argumentController = loader.getController();

        Business.Argument.addArgument(commandModel, argumentController, argumentNode);
        argumentsHbox.getChildren().add(argumentNode);
    }

    public void setState() {
        switch (commandModel.getState()) {
            case ALREADY_RUN:

                break;
            case NEXT_TO_RUN:
                break;
            case TO_RUN:
                break;
            default:
                break;
        }
    }

    @FXML
    public void delete(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @FXML
    public void run(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    // endregion

    // region Getters and Setters

    public Node getCommandNode() {
        return commandNode;
    }

    public void setCommandNode(Node node) {
        this.commandNode = node;
    }

    public Models.Command getCommandModel() {
        return commandModel;
    }

    // endregion
}
