package vn.edu.iuh.fit.repositories;

import vn.edu.iuh.fit.connectDB.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogRepository {
    private final Connection connection;

    public LogRepository() {
        connection = ConnectDB.getInstance().getConnection();
    }

//    public void logLogin(Integer accountId) {
//        // Record log login to CSDL
//        String sql = "INSERT INTO log (account_id, login_time, note) VALUES (?, NOW(), 'LOGIN'";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, accountId);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void logLogout(Integer accountId) {
//        // Record log logout from CSDL
//        String sql = "UPDATE log SET logout_time = NOW(), note = 'Logout' WHERE account_id = ? AND logout_time IS NULL";
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, accountId);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

}
