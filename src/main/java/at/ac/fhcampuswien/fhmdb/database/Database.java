package at.ac.fhcampuswien.fhmdb.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

public class Database {

    private final String DB_URL = "jdbc:h2:file: ./db/moviesDB";
    private final String username = "user";
    private final String password = "passwort";
    private ConnectionSource connectionSource;
    private Dao<WatchlistEntity, Long> dao;

    private static Database instance;

    private Database() {
        try {
            this.createConnectionSource();
            this.dao = DaoManager.createDao(this.connectionSource, WatchlistEntity.class);
            this.createTables();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    public void createConnectionSource() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(this.DB_URL, this.username, this.password);
    }

    private void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(this.connectionSource, WatchlistEntity.class);
    }

    public Dao<WatchlistEntity, Long> getDao() {
        return this.dao;
    }
}
