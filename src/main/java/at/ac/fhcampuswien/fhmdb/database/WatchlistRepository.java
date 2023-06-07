package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

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
        }
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        dao.delete(dao.queryForEq("id", movie.getApiId()));
    }

    public List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }
}
