package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FhmdbApplication extends Application {
    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 840, 620);
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
            stage.setTitle("FHMDb");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Cant load fxml file", ButtonType.OK).show();
        }

        Database db = Database.getDatabase();
    }

    public static void main(String[] args) {
        launch();
    }


}