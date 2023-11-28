package Views;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/** Controller class for an argument */
public class Argument implements Initializable {

    // region Attributes

    /** Image representing the file icon */
    private Image file_iconImage;

    /** Image representing the output icon */
    private Image output_iconImage;

    /** Image representing the text icon */
    private Image textImage;

    /** Image representing the red output icon */
    private Image red_output_iconImage;

    /** The FX object associated with this controller */
    private Node argumentNode;

    /** Command model object associated with this view */
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
    public Text text;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        file_iconImage = new Image(getClass().getResource("/images/file_icon.png").toString());
        output_iconImage = new Image(getClass().getResource("/images/output_icon.png").toString());
        textImage = new Image(getClass().getResource("/images/text_input_icon.png").toString());
        red_output_iconImage = new Image(getClass().getResource("/images/red_output_icon.png").toString());
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
        text.setFill(Color.WHITE);
        switch (argumentModel.getType()) {
            case FILE:
                // TODO
                // text.setText(argumentModel.getObjectValue());
                break;
            case OUTPUT:
                // TODO
                // text.setText(argumentModel.getObjectValue());
                break;
            case TEXT:
                text.setText((String) argumentModel.getObjectValue());
                break;
            default:
                text.setFill(Color.RED);
                text.setText("Invalid ref");
                break;

        }
    }

    @FXML
    public void delete(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @FXML
    public void edit(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    // endregion

}