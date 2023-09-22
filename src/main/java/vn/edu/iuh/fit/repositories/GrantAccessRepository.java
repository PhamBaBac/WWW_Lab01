package vn.edu.iuh.fit.repositories;

import vn.edu.iuh.fit.connectDB.ConnectDB;
import vn.edu.iuh.fit.models.GrantAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GrantAccessRepository {
    private final Connection connection;

    public GrantAccessRepository() {
        connection = ConnectDB.getInstance().getConnection();
    }

    public void add(GrantAccess access) {
        // Add access to CSDL
        String sql = "INSERT INTO grant_access (account_id, role_id, is_grant, note) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, access.getAccountId());
            preparedStatement.setString(2, access.getRoleId());
            preparedStatement.setInt(3, access.getIsGrant());
            preparedStatement.setString(4, access.getNote());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<GrantAccess> findByAccount(String accountId) {
        // Find a list of an account's access rights by account ID
        List<GrantAccess> accesses = new ArrayList<>();
        String sql = "SELECT * FROM grant_access WHERE account_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                GrantAccess access = mapResultSetToAccess(resultSet);
                accesses.add(access);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accesses;
    }

    private GrantAccess mapResultSetToAccess(ResultSet resultSet) throws SQLException {
        String accountId = resultSet.getString("account_id");
        String roleId = resultSet.getString("role_id");
        Integer isGrant = resultSet.getInt("is_grant");
        String note = resultSet.getString("note");
        return new GrantAccess(accountId, roleId, isGrant, note);
    }

}
