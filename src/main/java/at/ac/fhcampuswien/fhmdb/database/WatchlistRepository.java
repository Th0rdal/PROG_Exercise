package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

    private Dao<WatchlistEntity, Long> dao;

    public WatchlistRepository() {
        this.dao = Database.getDatabase().getDao();
    }

    public void addToWatchlist(WatchlistEntity movie) throws SQLException {
        if (dao.queryForEq("title", movie.getTitle()).isEmpty()) {
            dao.create(movie);
        }
    }

    public void removeFromWatchlist(WatchlistEntity movie) throws SQLException {
        System.out.println("DAO");
        dao.delete(dao.queryForEq("title", movie.getTitle()));

    }

    public List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }
}
