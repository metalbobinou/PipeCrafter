#! /bin/sh

# Print informations
echo " "
echo " "

echo "################################################################"
echo "# Launching the PipeCrafter with all the .jar in ./libs folder #"
echo "################################################################"

echo " "
echo " "

# Command line with all the parameters
java --module-path "./libs" \
     --add-modules=javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web,com.google.gson \
     -jar PipeCrafter-JavaFX.jar
