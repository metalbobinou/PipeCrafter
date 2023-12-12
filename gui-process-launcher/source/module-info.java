module fr.epita.gpl.guiprocesslauncher {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports App;
    exports Business;
    exports Execution;
    exports Models;
    exports Utils;
    exports Views;
}