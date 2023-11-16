package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Parent;

/** Entry point for the application */
public class App extends Application {

    // region Methods

    /** Open and set the main window/view */
    @Override
    public void start(Stage stage) throws IOException {

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/Views/Source/main_screen.fxml"));
        } catch (Exception e) {
            throw new RuntimeException("FXML file not found");
        }
        stage.setTitle("Execution Pipeline Builder");
        stage.setScene(new Scene(root));
        // stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getMinWidth()); // !!! set to "main" anchor pane min sizes
        stage.setMinHeight(stage.getMinHeight());
    }

    public static void main(String[] args) {
        launch();
    }

    // endregion
}
