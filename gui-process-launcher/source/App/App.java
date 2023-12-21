package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;

import Utils.Utils;
import javafx.stage.Stage;

/** Entry point for the application */
public class App extends Application {

    // region Methods

    /** Open and set the main window/view */
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_screen.fxml"));
        StackPane mainNode = loader.load();

        Business.App.setMainNode(mainNode);
        Business.App.setMainController(loader.getController());

        Business.App.setEditMode();

        stage.setTitle("Execution Pipeline Builder");

        stage.setScene(new Scene(mainNode));
        // stage.sizeToScene();
        stage.show();
        stage.setMinWidth(stage.getMinWidth()); // !!! TODO set to "main" anchor pane min sizes
        stage.setMinHeight(stage.getMinHeight());
    }

    public static void main(String[] args) {

        // TODO setup properly when loading?
        // Region temp until settings implemented then call settings setup
        // Default values tho ??
        Business.Settings.setExecutionDirectory(
                "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/source/test");
        Business.Settings.setOutputSavingDirectory(
                "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/source/test/output");

        // endregion

        Utils.initFcAndDc();
        Execution.ProcessManager.init();
        launch();
    }

    // endregion
}

class Launcher {
    public static void main(String[] args) {
        App.main(args);
    }
}
