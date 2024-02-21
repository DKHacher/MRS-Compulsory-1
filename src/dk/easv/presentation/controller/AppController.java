package dk.easv.presentation.controller;

import dk.easv.entities.*;
import dk.easv.presentation.model.AppModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
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


    private AppModel model;
    private long timerStartMillis = 0;
    private String timerMsg = "";
    @FXML
    private ScrollPane CrowdScroll;
    @FXML
    private ScrollPane SimilarScroll;
    @FXML
    private HBox Hbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ;
    }



    private void startTimer(String message){
        timerStartMillis = System.currentTimeMillis();
        timerMsg = message;
    }

    private void stopTimer(){
        System.out.println(timerMsg + " took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
    }

    public void setModel(AppModel model) {
        List<TopMovie> movieListSimilar = new ArrayList<>();
        List<Movie> movieListCrowd = new ArrayList<>();
        model.loadUsers();
        model.loadData(model.getObsLoggedInUser());


        movieListSimilar = model.getObsTopMoviesSimilarUsers();
        movieListCrowd = model.getObsTopMovieNotSeen();

        for (int i = 0; i< 10; i++){
            Image image;
            ImageView pic;
            image = new Image("Movies/Movie_1.png");
            pic = new ImageView(image);
            pic.setFitWidth(150);
            pic.setFitHeight(140);
            Hbox.getChildren().add(pic);
        }


    }

    /*
    public void setModel(AppModel model) {
        this.model = model;
        lvUsers.setItems(model.getObsUsers());
        lvTopForUser.setItems(model.getObsTopMovieSeen());
        lvTopAvgNotSeen.setItems(model.getObsTopMovieNotSeen());
        lvTopSimilarUsers.setItems(model.getObsSimilarUsers());
        lvTopFromSimilar.setItems(model.getObsTopMoviesSimilarUsers());
        startTimer("Load users");
        model.loadUsers();
        stopTimer();

        lvUsers.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, oldUser, selectedUser) -> {
                    startTimer("Loading all data for user: " + selectedUser);
                    model.loadData(selectedUser);
                });

        Select the logged-in user in the listview, automagically trigger the listener above
        lvUsers.getSelectionModel().select(model.getObsLoggedInUser());

    }*/
}
