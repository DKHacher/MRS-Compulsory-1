package dk.easv.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;


import java.io.IOException;

public class AccountController {
    @FXML
    private void backButton(ActionEvent actionEvent) {
        switchSceneWithFade(actionEvent, "/MainPage.fxml", "Main Page");
    }

    private void switchSceneWithFade(ActionEvent actionEvent, String fxmlPath, String title) {
        // Use the actionEvent to get the stage
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        Parent currentRoot = stage.getScene().getRoot();

        // Prepare fade out transition for current scene
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), currentRoot);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        // Set the action to perform when fade out is completed
        fadeOut.setOnFinished(event -> {
            try {
                // Load the new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));

                // Prepare fade in transition for new scene
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), root);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.play();

                stage.setScene(scene);
                stage.setTitle(title);
                stage.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page.");
                alert.showAndWait();
            }
        });

        // Start the fade out transition
        fadeOut.play();
    }

}
