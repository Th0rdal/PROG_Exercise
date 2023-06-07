package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.observe.Observable;
import at.ac.fhcampuswien.fhmdb.observe.ObservableMessages;
import at.ac.fhcampuswien.fhmdb.observe.Observer;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository implements at.ac.fhcampuswien.fhmdb.observe.Observable {

    private Dao<WatchlistEntity, Long> dao;
    private static WatchlistRepository instance = null;

    private WatchlistRepository() {
        this.dao = Database.getDatabase().getDao();
    }

    public static WatchlistRepository getWatchlistRepository() {
        if (WatchlistRepository.instance == null) {
            WatchlistRepository.instance = new WatchlistRepository();
        }
        return WatchlistRepository.instance;
    }

    public void addToWatchlist(WatchlistEntity movie) throws SQLException {
        if (dao.queryForEq("title", movie.getTitle()).isEmpty()) {
            dao.create(movie);
            this.updateObservers(ObservableMessages.ADDED);
            return;
        }
        this.updateObservers(ObservableMessages.ALREADY_EXISTS);

    }

    public void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        dao.delete(dao.queryForEq("APIID", movie.getApiId()));
    }

    public List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void updateObservers(ObservableMessages message) {
        for (Observer observer : this.observers) {
            observer.update(message);
        }
    }
}
