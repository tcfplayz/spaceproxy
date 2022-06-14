package velocity.tcf.spaceproxy.util;

import java.sql.*;

public class MySQL {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/spacemc?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
    static final String USER = "tcfplayz";
    static final String PASS = "mcro123123";

    private static Connection startConnection()  {
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addPlayer(String uuid, String name, String ip) {
        try {
            Connection c = startConnection();
            assert c != null;
            Statement stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO plrdata (uuid, coins, goldcrate, voidcrate, diamondcrate, playtime, name, ip, level, exp) VALUES ('" + uuid + "', '" + 0 + "', '" + 0 + "', '" + 0 + "', '" + 0 + "', '" + 0 + "', '" + name + "', '" + ip + "', '" + 1 + "', '" + 0 + "')");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update values
    public static void updateCoins(String uuid, int coins) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET coins = '" + coins + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlaytime(String uuid, long playtime) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET playtime = '" + playtime + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateName(String uuid, String name) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET name = '" + name + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCrates(String uuid, String voidcrate, String diamondcrate, String goldcrate) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET voidcrate = '" + voidcrate + "' WHERE uuid = '" + uuid + "'");
            stmt.executeUpdate("UPDATE plrdata SET diamondcrate = '" + diamondcrate + "' WHERE uuid = '" + uuid + "'");
            stmt.executeUpdate("UPDATE plrdata SET goldcrate = '" + goldcrate + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateExp(String uuid, int exp) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET exp = '" + exp + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLevel(String uuid, int lvl) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET level = '" + (lvl + 1) + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateIP(String uuid, String ip) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET ip = '" + ip + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateLastEntered(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            stmt.executeUpdate("UPDATE plrdata SET lastonline = '" + System.currentTimeMillis() + "' WHERE uuid = '" + uuid + "'");
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // get values
    public static int getCoins(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT coins FROM plrdata WHERE uuid = '" + uuid + "'");
            int coins = 0;
            if(rs.next()) coins = rs.getInt("coins");
            stmt.close();
            c.close();
            return coins;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int[] getExpLvl(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT exp, level FROM plrdata WHERE uuid = '" + uuid + "'");
            int[] explvl = {0, 0};
            if (rs.next()) explvl = new int[]{rs.getInt("exp"), rs.getInt("level")};
            stmt.close();
            c.close();
            return explvl;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{0, 0};
    }

    public static int getPlaytime(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT playtime FROM plrdata WHERE uuid = '" + uuid + "'");
            int playtime = 0;
            if(rs.next()) playtime = rs.getInt("playtime");
            stmt.close();
            c.close();
            return playtime;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int[] getCrates(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT voidcrate, diamondcrate, goldcrate FROM plrdata WHERE uuid = '" + uuid + "'");
            int[] crates = {0, 0, 0};
            if (rs.next()) crates = new int[]{rs.getInt("voidcrate"), rs.getInt("diamondcrate"), rs.getInt("goldcrate")};
            stmt.close();
            c.close();
            return crates;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new int[]{0, 0, 0};
    }

    public static String getName(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM plrdata WHERE uuid = '" + uuid + "'");
            String name = "";
            if (rs.next()) name = rs.getString("name");
            stmt.close();
            c.close();
            return name;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getIP(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ip FROM plrdata WHERE uuid = '" + uuid + "'");
            String ip = "0.0.0.0";
            if (rs.next()) ip = rs.getString("ip");
            stmt.close();
            c.close();
            return ip;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // check if player exists
    public static boolean playerExists(String uuid) {
        try {
            Connection c = startConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM plrdata WHERE uuid = '" + uuid + "'");
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

    public static String formatTime(long time) {

        int seconds = (int) (time / 1000) % 60 ;
        int minutes = (int) ((time / (1000*60)) % 60);
        int hours   = (int) ((time / (1000*60*60)) % 24);


        String s = "", m = "", h = "";

        if (seconds < 10) s = "0" + seconds;
        else if (minutes >= 60) minutes += seconds / 60;
        else s = String.valueOf(seconds);
        if (minutes < 10) m = "0" + minutes;
        else if (minutes >= 60) hours += minutes / 60;
        else m = String.valueOf(minutes);
        if (hours < 10) h = "0" + hours;
        else h = String.valueOf(hours);

        return h + ":" + m + ":" + s;
    }

}
