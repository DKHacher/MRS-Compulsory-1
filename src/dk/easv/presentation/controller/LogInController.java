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

import javax.swing.*;
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
            try {
                // Load the main page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainPage.fxml"));
                Parent root = loader.load();

                // Get the current stage (window) from the action event
                Stage stage = (Stage) logInButton.getScene().getWindow();

                // Set the new scene on the current stage
                stage.setScene(new Scene(root));
                stage.setTitle("MovieToons");
                stage.centerOnScreen();

                //Pass data to main page controller
                AppController controller = loader.getController();
                controller.setModel(model);

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Could not load MainPage.fxml");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
            alert.showAndWait();
        }
    }


    public void signUp(ActionEvent actionEvent) {
        System.out.println("Sign-Up");
    }

}
