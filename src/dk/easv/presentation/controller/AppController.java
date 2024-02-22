package dk.easv.presentation.controller;

import dk.easv.entities.*;
import dk.easv.presentation.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.animation.FadeTransition;
import javafx.util.Duration;


import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController implements Initializable {
    @FXML
    private ListView<User> lvUsers;
    @FXML
    private ListView<Movie> lvTopForUser;
    @FXML
    private ListView<Movie> lvTopAvgNotSeen;
    @FXML
    private ListView<UserSimilarity> lvTopSimilarUsers;
    @FXML
    private ListView<TopMovie> lvTopFromSimilar;
    @FXML
    private StackPane sideMenuPane;
    @FXML
    private Button hamburgerButton, logOutBtn;

    private AppModel model;
    private long timerStartMillis = 0;
    private String timerMsg = "";
    @FXML
    private HBox crowdHBox;
    @FXML
    private ScrollPane crowdScroll;
    @FXML
    private ScrollPane similarScroll;
    @FXML
    private HBox similarHBox;

    private void startTimer(String message){
        timerStartMillis = System.currentTimeMillis();
        timerMsg = message;
    }

    private void stopTimer(){
        System.out.println(timerMsg + " took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setModel(AppModel model) {

        List<TopMovie> movieListSimilar = new ArrayList<>();
        List<Movie> movieListCrowd = new ArrayList<>();
        model.loadUsers();
        model.loadData(model.getObsLoggedInUser());


        movieListSimilar = model.getObsTopMoviesSimilarUsers();
        movieListCrowd = model.getObsTopMovieNotSeen();

        /*
        the loops are seperated to test the data
        in a final product we would make it loop
        based on the actual lists that contain the movies


        the movie images are also random, because we couldn't get
        an image for every movie in the list, if this were a end of
        semester exam project, we would endeavor to get as many movie
        pictures for the project as possible, and link them together wherever necessary

        missing adding function on click for image views, minor detail
        */

        //this is the loop for Similar movies
        List<ImageView> imgViewListSimilar = imageLoop(similarHBox);
        addEventToImageView(imgViewListSimilar, movieListSimilar, 0);

        //the same loop for Crowd Favourites
        List<ImageView> imgViewListCrowd = imageLoop(crowdHBox);
        addEventToImageView(imgViewListCrowd, movieListCrowd);

    }

    private void addEventToImageView(List<ImageView> imgList, List<TopMovie> mvlist, int id) {
        for (ImageView iv: imgList){
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                switchSceneWithFade("/MovieDetails.fxml", "Movie Details");

                event.consume();
            });
        }
    }
    private void addEventToImageView(List<ImageView> imgList, List<Movie> mvlist) {
        for (ImageView iv: imgList){
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                switchSceneWithFade("/MovieDetails.fxml", "Movie Details");

                event.consume();
            });
        }
    }

    private List<ImageView> imageLoop(HBox crowdHBox) {
        List<ImageView> imageViewList = new ArrayList<>();
        for (int i = 0; i< 20; i++){
            Image image;
            ImageView pic;
            Random rng = new Random();
            int rnd = rng.nextInt(1,11);
            image = new Image("Movies/movie_"+rnd+".jpg");
            pic = new ImageView(image);
            pic.setFitWidth(115);
            pic.setFitHeight(150);
            crowdHBox.getChildren().add(pic);
            imageViewList.add(pic);

        }
        return imageViewList;
    }


    @FXML
    private void handleHamburgerButtonAction(ActionEvent actionEvent) {
        sideMenuPane.setVisible(!sideMenuPane.isVisible());
    }

    @FXML
    private void logOut(ActionEvent event) {
        switchSceneWithFade("/LoginPage.fxml", "Login");
    }

    @FXML
    private void accountDetails(ActionEvent actionEvent) {
        switchSceneWithFade("/AccountPage.fxml", "Account");
    }


    private void switchSceneWithFade(String fxmlPath, String title) {
        Stage stage = (Stage) sideMenuPane.getScene().getWindow();
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
