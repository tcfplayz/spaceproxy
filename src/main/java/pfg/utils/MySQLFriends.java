package pfg.utils;

import java.sql.*;

public class MySQLFriends {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/spacemc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    static final String USER = "tcfplayz";
    static final String PASS = "**********";

    private static Connection startConnection()  {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addPlayer(String uuid) {
        try {
            Connection c = startConnection();
            assert c != null;
            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO friendlimit (uuid, limit, current) VALUES ('" + uuid + "', '" + 0 + "', '" + 0 + "')");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void acceptFriend(String uuid, String uuid2) {
        try {
            Connection c = startConnection();
            assert c != null;
            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO friend (friend1, friend2) VALUES ('" + uuid + "', '" + uuid2 + "')");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
        public static void removeFriend(String uuid, String uuid2, int id) {
        try {
        Connection c = startConnection();
        Statement stmt = c.createStatement();
        stmt.executeUpdate("DELETE FROM friend WHERE id = '" + id + "'");
        stmt.close();
        c.close();
         } catch (SQLException e) {
        e.printStackTrace();
         }
        }
        */

    public static boolean playerExists(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM friendlimit WHERE uuid = '" + uuid + "'");
            if (rs.next()) {
                stmt.close();
                c.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
