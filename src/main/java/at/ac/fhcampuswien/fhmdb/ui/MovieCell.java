package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.database.WatchlistEntity;
import at.ac.fhcampuswien.fhmdb.database.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class MovieCell extends ListCell<Movie> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final Label genre = new Label();
    private final JFXButton detailsButton = new JFXButton("Show details");
    private final JFXButton addToWatchlistButton = new JFXButton("add to Watchlist");
    private final HBox inCellButtons = new HBox(title, detailsButton, addToWatchlistButton);
    private final HBox inCellLayout = new HBox(title, inCellButtons);
    private final VBox layout = new VBox(inCellLayout, detail, genre);
    private boolean showDetails = true;

    public MovieCell() {
        super();

        this.detailsButton.setOnAction(actionEvent -> {
            if (this.showDetails) {
                layout.getChildren().add(this.getDetails());
                this.showDetails = false;
                detailsButton.setText("Hide Details");
            }else {
                layout.getChildren().remove(3);
                this.showDetails = true;
                detailsButton.setText("Show Details");
            }

        });

        this.addToWatchlistButton.setOnAction(actionEvent -> {
            WatchlistRepository watchlistRepository = new WatchlistRepository();
            try {
                watchlistRepository.addToWatchlist(WatchlistEntity.convertMovieToWatchlistEntity(getItem()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // color scheme
        title.getStyleClass().add("text-yellow");
        detail.getStyleClass().add("text-white");
        genre.getStyleClass().add("text-white");
        detailsButton.setBackground(new Background(new BackgroundFill(Color.web("#f5c518"), new CornerRadii(3.5), null)));
        addToWatchlistButton.setBackground(new Background(new BackgroundFill(Color.web("#f5c518"), new CornerRadii(3.5), null)));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#454545"), null, null)));

        // layout
        detail.setWrapText(true);
        genre.setWrapText(true);
        detail.setPrefWidth(50);
        detail.setMaxWidth(50);
        layout.setPadding(new Insets(10));
        layout.spacingProperty().set(10);
        HBox.setHgrow(inCellButtons, Priority.ALWAYS);
        inCellButtons.alignmentProperty().set(javafx.geometry.Pos.CENTER_RIGHT);
        inCellLayout.setAlignment(Pos.CENTER_LEFT);
        layout.alignmentProperty().set(javafx.geometry.Pos.CENTER_LEFT);
        inCellLayout.spacingProperty().set(10);
        inCellButtons.spacingProperty().set(10);
        inCellButtons.setPadding(new Insets(0, 10, 0, 0));
    }

    private VBox getDetails() {
        VBox details = new VBox();

        Label releaseYear = new Label("Release Year: " + getItem().getReleaseYear());
        Label length = new Label("Length: " + getItem().getLengthInMinutes());
        Label rating = new Label("Rating: " + getItem().getRating()+"/10");

        Label director = new Label("Directors: " + String.join(", ", getItem().getDirectors()));
        Label writer = new Label("Directors: " + String.join(", ", getItem().getWriters()));
        Label mainCast = new Label("Directors: " + String.join(", ", getItem().getMainCast()));

        releaseYear.getStyleClass().add("text-white");
        length.getStyleClass().add("text-white");
        rating.getStyleClass().add("text-white");
        director.getStyleClass().add("text-white");
        writer.getStyleClass().add("text-white");
        mainCast.getStyleClass().add("text-white");

        details.getChildren().add(releaseYear);
        details.getChildren().add(length);
        details.getChildren().add(rating);
        details.getChildren().add(director);
        details.getChildren().add(writer);
        details.getChildren().add(mainCast);
        return details;

    }

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

            title.fontProperty().set(title.getFont().font(20));
            detail.setMaxWidth(this.getScene().getWidth() - 30);
            genre.setMaxWidth((this.getScene().getWidth() - 30));
            setGraphic(layout);
        }
    }
}

