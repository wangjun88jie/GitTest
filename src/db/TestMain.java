package db;

import java.sql.Connection;
import java.sql.SQLException;

public class TestMain {
    public static void main(String[] args) throws SQLException {
        DBPoolConnection dbPoolConnection = DBPoolConnection.getInstance();
        Connection conn = dbPoolConnection.getConnection();
    }
}
