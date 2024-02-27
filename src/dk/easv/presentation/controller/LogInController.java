package dk.easv.presentation.controller;

import dk.easv.entities.User;
import dk.easv.presentation.model.AppModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;


import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML private PasswordField passwordField;
    @FXML private TextField userId;
    @FXML private Button logInButton;

    private AppModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new AppModel();
    }

    public void logIn(ActionEvent actionEvent) {
        model.loadUsers();
        model.loginUserFromUsername(userId.getText());
        if (model.getObsLoggedInUser() != null) {
            // Use the transition method to switch scenes after successful login
            switchSceneWithFade("/MainPage.fxml", "MovieToons");


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
            alert.showAndWait();
        }
    }



    private void switchSceneWithFade(String fxmlPath, String title) {
        Stage stage = (Stage) logInButton.getScene().getWindow();
        Parent currentRoot = logInButton.getScene().getRoot();

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), currentRoot);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                Scene scene = new Scene(root);
                scene.setFill(javafx.scene.paint.Color.valueOf("#131414"));
                FileWriter myWriter = new FileWriter("Resources\\user.txt");
                myWriter.write(userId.getText());
                myWriter.close();

                AppController controller = loader.getController();

                controller.setModel(model);

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
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load the page: " + title);
                alert.showAndWait();
            }
        });

        fadeOut.play();
    }


    public void signUp(ActionEvent actionEvent) {
        System.out.println("Sign-Up");
    }

}
