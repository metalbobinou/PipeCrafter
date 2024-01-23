# PipeCrafter
GUI for buidling and launching steps of a pipeline of programs

# Dependencies:
- Java (linux: OpenJDK)
- JavaFX (linux: OpenJFX)
- gson (linux: libgoogle-gson-java)

# How to launch:

# (method 1)
java --module-path "/usr/share/openjfx/lib:/usr/share/java/"  \
     --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,\
javafx.media,javafx.swing,javafx.web,com.google.gson          \
     -jar PipeCrafter-JavaFX.jar

# (method 2) : create a "libs" folder with all of the required JARs files
java --module-path "./libs" \
     --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,\
javafx.media,javafx.swing,javafx.web,com.google.gson  \
     -jar PipeCrafter-JavaFX.jar

# Contributors:
- BOISSIER Fabrice (Project Leader & Architecture) [2023-...]
- AMOVIN Ivance (Architecture & Design & Developper) [2023-2024] - V0 & V1
