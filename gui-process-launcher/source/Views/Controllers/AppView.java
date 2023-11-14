package Views.Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class AppView extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        stage.setTitle("Execution Pipeline Builder");
        stage.setScene(new Scene(root));
        // stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getMinWidth()); // set to "main" anchor pane min sizes
        stage.setMinHeight(stage.getMinHeight());
    }

    public static void main(String[] args) {
        // V0.demo();
        launch();
    }
}
