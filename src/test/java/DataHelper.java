import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;

import java.sql.*;
import java.util.Locale;

public class DataHelper {

    private static Faker faker;

    private static String dbURL = "jdbc:mysql://192.168.99.100:3306/app";
    private static String dbUserName = "app";
    private static String dbUserPassword = "pass";
    private static Connection connection;

    private static String userStatusRequest = "SELECT status FROM users WHERE id = ?";

    private static  String userIDRequest =
            "SELECT id " +
            "FROM users " +
            "WHERE login = ?;";

    private static String codeRequest =
            "SELECT code " +
            "FROM auth_codes " +
            "WHERE user_id = ? " +
            "and created = " +
            "(select max(created) " +
            "from auth_codes);";

    public static String getUserLogin(){
        faker = new Faker(Locale.ENGLISH);
        return faker.name().username();
    }

    public static String getUserPassword(){
        faker = new Faker(Locale.ENGLISH);
        String wrongPassword = faker.internet().password();
        return wrongPassword;
    }

    public static String getInvalidVerificationCode(){
        faker = new Faker(Locale.ENGLISH);
        return faker.number().digits(7);
    }

    public static void setMySQLConnection() throws SQLException {
        connection = DriverManager.getConnection(dbURL, dbUserName, dbUserPassword);
    }

    private static String getUserID(String userLogin) throws SQLException {
        setMySQLConnection();
        PreparedStatement userIdStat = connection.prepareStatement(userIDRequest);
        userIdStat.setString(1, userLogin);
        ResultSet userIdResult = userIdStat.executeQuery();
        String userID = "";
        Boolean idResult = userIdResult.next();
        if (idResult) {
            userID = userIdResult.getString("id");
        }
        return userID;
    }

    public static String getVerificationCode(String userLogin) throws SQLException {
        String userID = getUserID(userLogin);
        PreparedStatement codeStatement = connection.prepareStatement(codeRequest);
        codeStatement.setString(1, userID);
        ResultSet codeResultSet = codeStatement.executeQuery();
        String userCode = "";
        Boolean codeResult = codeResultSet.next();
        if (codeResult) {
            userCode = codeResultSet.getString("code");
        }
        return userCode;
    }

    public static String getUserStatus(String userLogin) throws SQLException {
        String userID = getUserID(userLogin);
        PreparedStatement userStatusStat = connection.prepareStatement(userStatusRequest);
        userStatusStat.setString(1, userID);
        ResultSet stateResultSet = userStatusStat.executeQuery();
        Boolean gotStatus = stateResultSet.next();
        String userStatus = "";
        if (gotStatus){
            userStatus = stateResultSet.getString("status");
        }
        return userStatus;
    }

    public static void userStatusShouldNotBeActive(String status){
        Assertions.assertFalse("active".equalsIgnoreCase(status));
    }

    public static void clearDB() throws SQLException {
        String deleteFromCardTransactions = "DELETE FROM card_transactions";
        String deleteFromAuthCodes = "DELETE FROM auth_codes";
        String deleteFromCards = "DELETE FROM cards";
        String deleteFromUsers = "DELETE FROM users";

        PreparedStatement cardTransactions = connection.prepareStatement(deleteFromCardTransactions);
        PreparedStatement authCodes = connection.prepareStatement(deleteFromAuthCodes);
        PreparedStatement cards = connection.prepareStatement(deleteFromCards);
        PreparedStatement users = connection.prepareStatement(deleteFromUsers);
        cardTransactions.executeUpdate();
        authCodes.executeUpdate();
        cards.executeUpdate();
        users.executeUpdate();
    }
}
