package Views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

/** Controller class for the main view/screen */
public class Main implements Initializable {

    // region Attributes

    @FXML
    public VBox processVBox;

    @FXML
    public Text status_text;

    // endregion

    // region Methods

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void add_process(MouseEvent event) throws IOException {
        // Rectangle rect = new Rectangle(100, 100);
        // rect.setFill(Color.RED);
        // processVBox.getChildren().add(0, rect);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Source/process_box.fxml"));

        // keep trace to remove from vbox?
        Parent process = loader.load();

        // object handled by the business
        Process processController = loader.getController();
        processController.SetUp(process);
        processVBox.getChildren().add(processVBox.getChildren().size() - 1, process);
        // throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @FXML
    public void load(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @FXML
    public void open_settings(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    @FXML
    public void save(MouseEvent event) {
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

    // endregion

    // private static final String TAB_DRAG_KEY = "titledpane";
    // private ObjectProperty<TitledPane> draggingTab;

    // @Override
    // public void start(Stage primaryStage) throws Exception {
    // draggingTab = new SimpleObjectProperty<TitledPane>();
    // VBox vbox = new VBox();
    // for (int i = 0; i < 4; i++) {
    // final TitledPane pane = new TitledPane();
    // pane.setText("pane" + (i + 1));
    // vbox.getChildren().add(pane);
    // pane.setOnDragOver(new EventHandler<DragEvent>() {
    // @Override
    // public void handle(DragEvent event) {
    // final Dragboard dragboard = event.getDragboard();
    // if (dragboard.hasString()
    // && TAB_DRAG_KEY.equals(dragboard.getString())
    // && draggingTab.get() != null) {
    // event.acceptTransferModes(TransferMode.MOVE);
    // event.consume();
    // }
    // }
    // });
    // pane.setOnDragDropped(new EventHandler<DragEvent>() {
    // public void handle(final DragEvent event) {
    // Dragboard db = event.getDragboard();
    // boolean success = false;
    // if (db.hasString()) {
    // Pane parent = (Pane) pane.getParent();
    // Object source = event.getGestureSource();
    // int sourceIndex = parent.getChildren().indexOf(source);
    // int targetIndex = parent.getChildren().indexOf(pane);
    // List<Node> nodes = new ArrayList<Node>(parent.getChildren());
    // if (sourceIndex < targetIndex) {
    // Collections.rotate(
    // nodes.subList(sourceIndex, targetIndex + 1), -1);
    // } else {
    // Collections.rotate(
    // nodes.subList(targetIndex, sourceIndex + 1), 1);
    // }
    // parent.getChildren().clear();
    // parent.getChildren().addAll(nodes);
    // success = true;
    // }
    // event.setDropCompleted(success);
    // event.consume();
    // }
    // });
    // pane.setOnDragDetected(new EventHandler<MouseEvent>() {
    // @Override
    // public void handle(MouseEvent event) {
    // Dragboard dragboard = pane.startDragAndDrop(TransferMode.MOVE);
    // ClipboardContent clipboardContent = new ClipboardContent();
    // clipboardContent.putString(TAB_DRAG_KEY);
    // dragboard.setContent(clipboardContent);
    // draggingTab.set(pane);
    // event.consume();
    // }
    // });
    // }
    // TitledPane pane = new TitledPane("MAIN", vbox);
    // primaryStage.setScene(new Scene(pane, 890, 570));
    // primaryStage.show();
    // }
}
