# PipeCrafter
GUI for buidling and launching steps of a pipeline of programs


# Dependencies: (see last section for the download links)
- Java _(linux: OpenJDK)_
- JavaFX _(linux: OpenJFX)_
- gson _(linux: libgoogle-gson-java)_


# How to launch:

## (method 1)

1) Launch this command in a shell:
```
java --module-path "/usr/share/openjfx/lib:/usr/share/java/"  \
     --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,\
javafx.media,javafx.swing,javafx.web,com.google.gson          \
     -jar PipeCrafter-JavaFX.jar
```

## (method 2)

1) Create a "libs" folder with all of the required JARs files:
- gson.jar
- javafx-base.jar
- javafx-controls.jar
- javafx-fxml.jar
- javafx-graphics.jar
- javafx-media.jar
- javafx-swing.jar
- javafx-web.jar

2) Launch this command in a shell:
```
java --module-path "./libs" \
     --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,\
javafx.media,javafx.swing,javafx.web,com.google.gson  \
     -jar PipeCrafter-JavaFX.jar
```

OR

Launch this script:
```
sh launch.sh
```


# Depencies JAR manual download

## JavaFX :
- [OpenJFX.io](https://openjfx.io/)
- [GluonHQ JavaFX](https://gluonhq.com/products/javafx/)

## gson :
- [GitHub gson](https://github.com/google/gson)
- [Maven gson 2.10.1 JAR](https://search.maven.org/artifact/com.google.code.gson/gson/2.10.1/jar?eh=)


# Contributors:
- BOISSIER Fabrice (Project Leader & Architecture) [2023-...]
- AMOVIN Ivance (Architecture & Design & Developper) [2023-2024] - V0 & V1
