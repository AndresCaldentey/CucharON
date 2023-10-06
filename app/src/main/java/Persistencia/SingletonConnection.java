package Persistencia;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

public class SingletonConnection {

    private static JdbcConnectionSource connectionSource;
    public SingletonConnection() {
        try {
            String url = "jdbc:mysql://cucharoon.mysql.database.azure.com:3306/cucharoon";
            String username = "LosCucharones";
            String password = "Cucharon123";
            connectionSource = new JdbcConnectionSource(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static JdbcConnectionSource getSingletonInstance() {
        if(connectionSource == null) {
            new SingletonConnection();
        }
        return  connectionSource;
    }
}
