package gb.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AuthService {

    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:userTestDB.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static String getNickByLoginAndPass(String login, String pass) {

        String sql = String.format("select nickname FROM userTable where" +
                " login = '%s' and password = '%s'", login, pass);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static ArrayList getBlackListByLogin (String login) {
//        String sql = String.format("select nickname FROM userTable where" +
//                " login = '%s'", login);
//        ArrayList<String> blacklist = new ArrayList<>();
//        try {
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                blacklist.add(rs.getString("blacklist"));
//            }
//            return blacklist;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static String getAllMsg (){    // получение из БД всех сообщений
        String sql = String.format("select nickname, msg FROM message");
        try {
            ResultSet rs = stmt.executeQuery(sql);
            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                sb.append(rs.getString("nickname") +": "+ rs.getString("msg") +'\n');
            }
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStmt() {
        return stmt;
    }
}
