module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires okhttp3;
    requires com.google.gson;
    requires ormlite.jdbc;
    requires com.h2database;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb;
    opens at.ac.fhcampuswien.fhmdb.database to ormlite.jdbc;
    exports at.ac.fhcampuswien.fhmdb;
}