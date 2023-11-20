package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
            URL url = new File("source/Views/Source/main_screen.fxml").toURI().toURL();
            root = FXMLLoader.load(url);
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
