package Base;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

/** Base abstract test class */
public abstract class TestFXBase extends ApplicationTest {

    // #region Attributes

    /** The base URL to access locations */
    protected static String baseURL = "/Users/ivance/Documents/Pro/Metalab/GUI-Pipeline-Launcher/gui-process-launcher/";

    /** The directory used to load stored saves */
    protected final static File savesToLoadDirectory = new File(
            baseURL + "test/ressources/savesToLoad");

    /** The directory used to store saves */
    protected final static File savedDirectory = new File(
            baseURL + "test/ressources/saved");

    // #endregion

    // #region Methods

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    /**
     * Launch the app before each test
     * 
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        ApplicationTest.launch(App.App.class);
        Business.Settings.setExecutionDirectory(baseURL + "test/ressources");
        Business.Settings.setOutputSavingDirectory(baseURL + "test/ressources/output");

    }

    /**
     * Reset the stage and the inputs after each test
     * 
     * @throws Exception
     */
    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[] {});
        release(new MouseButton[] {});

        // clean generated outputs/saves
        for (File file : Business.Settings.getOutputSavingDirectory().listFiles())
            if (!file.isDirectory())
                file.delete();
        for (File file : savedDirectory.listFiles())
            if (!file.isDirectory())
                file.delete();
    }

    /**
     * Helper method to retrieve JavaFX components,
     * equivalent to former "queryFirst()"
     * 
     * @param <T>   type of the component to retrieve
     * @param query the string query associated with the wanted component
     * @return the requested component
     */
    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }

    // #endregion
}
