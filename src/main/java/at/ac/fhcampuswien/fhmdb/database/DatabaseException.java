package at.ac.fhcampuswien.fhmdb.database;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.h2.store.Data;

import java.sql.SQLException;

public class DatabaseException extends SQLException {
    public DatabaseException(String message) {
        Alert a = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        a.show();
    }
}
