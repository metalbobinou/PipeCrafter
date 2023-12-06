package Views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import Utils.OutputParameters;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/** Controller class for an argument */
public class Argument implements Initializable {

    // region Attributes

    /** Image representing the file icon */
    private static Image file_iconImage = null;

    /** Image representing the output icon */
    private static Image output_iconImage = null;

    /** Image representing the text icon */
    private static Image textImage = null;

    /** Image representing the red output icon */
    private static Image red_output_iconImage = null;

    /** The FX object associated with this controller */
    private Node argumentNode;

    /** Argument model object associated with this view */
    private Models.Argument argumentModel;

    /** A key used to set a "type" for dragged elements */
    private static final String TAB_DRAG_KEY = "argument";

    /** Object used to handle drag and drop */
    private ObjectProperty<Node> draggingTab = new SimpleObjectProperty<Node>();

    /** Icon for the argument type */
    @FXML
    public ImageView icon;

    /** Text displayed for the argument */
    @FXML
    public Label text;

    @FXML
    public Tooltip tooltip;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (file_iconImage == null) {
            file_iconImage = new Image(getClass().getResource("/images/file_icon.png").toString());
            output_iconImage = new Image(getClass().getResource("/images/output_icon.png").toString());
            textImage = new Image(getClass().getResource("/images/text_input_icon.png").toString());
            red_output_iconImage = new Image(getClass().getResource("/images/red_output_icon.png").toString());
        }
    }

    /**
     * Associate the controller with its node and model, and set FX objects for
     * drag and drop
     * 
     * @param node  the controller's node
     * @param model the controller's model
     */
    public void setUp(Node node, Models.Argument model) {
        this.argumentNode = node;
        this.argumentModel = model;
        setIcon();
        setText();

        // region Drag and Drop Imple

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

    /** Set the argument's icon according to its type */
    public void setIcon() {
        switch (argumentModel.getType()) {
            case FILE:
                icon.setImage(file_iconImage);
                break;
            case OUTPUT:
                icon.setImage(output_iconImage);
                break;
            case TEXT:
                icon.setImage(textImage);
                break;
            default:
                icon.setImage(red_output_iconImage);
                break;

        }
    }

    /** Set the argument's text according to its type and value */
    public void setText() {
        text.setTextFill(Color.WHITE);
        switch (argumentModel.getType()) {
            case FILE:
                text.setText(((File) argumentModel.getObjectValue()).getName());
                break;
            case OUTPUT:
                OutputParameters params = (OutputParameters) argumentModel.getObjectValue();

                text.setText(String.valueOf(params.getStep()) + params.getStream().getExtension()
                        + params.getFormat().getString());
                break;
            case TEXT:
                text.setText(argumentModel.toString());
                break;
            default:
                text.setTextFill(Color.RED);
                text.setText("Invalid ref");
                break;
        }
        tooltip.setText(text.getText());
    }

    @FXML
    public void delete(MouseEvent event) {
        Business.Argument.deleteArgument(argumentModel, argumentNode);
    }

    @FXML
    public void edit(MouseEvent event) throws IOException {
        Business.Command.setCommandReiceivingArgument(argumentModel.getMotherCommand());

        Business.Argument.setAddedArg(argumentModel);

        Views.Command.showArgumentSelector();

        setIcon();
        setText();
    }

    // endregion

}