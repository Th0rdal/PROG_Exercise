package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final JFXButton detailsButton = new JFXButton("show details");
    private final JFXButton addToWatchlistButton = new JFXButton("Watchlist");
    private final HBox inCellButtons = new HBox(title, detailsButton, addToWatchlistButton);
    private final HBox inCellLayout = new HBox(title, inCellButtons);
    private final VBox layout = new VBox(inCellLayout, detail, genre);

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.getStyleClass().add("movie-cell");
            title.setText(movie.getTitle());
            detail.setText(
                    movie.getDescription() != null
                            ? movie.getDescription()
                            : "No description available"
            );
            genre.setText(
                    movie.getGenres() != null
                            ? movie.getGenres().toString()
                            : "No genres available"
            );

            // color scheme
            title.getStyleClass().add("text-yellow");
            detail.getStyleClass().add("text-white");
            genre.getStyleClass().add("text-white");
            detailsButton.setBackground(new Background(new BackgroundFill(Color.web("#f5c518"), new CornerRadii(3.5), null)));
            addToWatchlistButton.setBackground(new Background(new BackgroundFill(Color.web("#f5c518"), new CornerRadii(3.5), null)));
            layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

            // layout
            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            detail.setWrapText(true);
            genre.setMaxWidth((this.getScene().getWidth() - 30));
            genre.setWrapText(true);
            layout.setPadding(new Insets(10));
            layout.spacingProperty().set(10);
            HBox.setHgrow(inCellButtons, Priority.ALWAYS);
            inCellButtons.alignmentProperty().set(javafx.geometry.Pos.CENTER_RIGHT);
            inCellLayout.setAlignment(Pos.CENTER_LEFT);
            layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
            inCellLayout.spacingProperty().set(10);
            inCellButtons.spacingProperty().set(10);
            //addToWatchlistButton.paddingProperty().set(new Insets(0, 0, 0, 0));
            inCellButtons.setPadding(new Insets(0, 10, 0, 0));
            setGraphic(layout);
        }
    }
}

