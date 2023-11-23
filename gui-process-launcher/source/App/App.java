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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));

        Parent mainNode = loader.load();
        Business.App.setMainController(loader.getController());

        stage.setTitle("Execution Pipeline Builder");

        stage.setScene(new Scene(mainNode));
        // stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getMinWidth()); // !!! set to "main" anchor pane min sizes
        stage.setMinHeight(stage.getMinHeight());
    }

    public static void main(String[] args) {

        // region temp until settings implemented then call settings setup
        Business.Settings.setExecutionDirectory(
                "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/source/test");
        Business.Settings.setOutputSavingDirectory(
                "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/source/test/output");

        // endregion

        Execution.ProcessManager.init();
        launch();
    }

    // endregion
}
