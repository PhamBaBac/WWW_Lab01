package vn.edu.iuh.fit.repositories;

import vn.edu.iuh.fit.connectDB.ConnectDB;
import vn.edu.iuh.fit.models.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositories {
    Connection con;

    public AccountRepositories() {
        con = ConnectDB.getInstance().getConnection();
    }

    public List<Account> getAllAccount() {
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM account";
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getString("account_id"));
                acc.setFullName(rs.getString("full_name"));
                acc.setPassword(rs.getString("password"));
                acc.setEmail(rs.getString("email"));
                acc.setPhone(rs.getString("phone"));
                acc.setStatus(rs.getInt("status"));
                accounts.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }


    public Account authenticateUser(String account_id, String password) {
        try {
            String sql = "SELECT * FROM account WHERE account_id = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, account_id);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getString("account_id"));
                account.setPassword(rs.getString("password"));
                // Thêm các trường khác nếu cần
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean addAccount(Account acc) {
        try {
            String sql = "INSERT INTO account (account_id, full_name, password, email, phone, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, acc.getAccountId());
            stm.setString(2, acc.getFullName());
            stm.setString(3, acc.getPassword());
            stm.setString(4, acc.getEmail());
            stm.setString(5, acc.getPhone());
            stm.setInt(6, acc.getStatus());

            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccount(String accountId) {
        try {
            String sql = "DELETE FROM account WHERE account_id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, accountId); // Sử dụng accountId để xác định tài khoản cần xóa

            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateAccount(Account acc, String accountId) {
        try {
            String sql = "UPDATE account SET full_name=?, password=?, email=?, phone=?, status=? WHERE account_id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, acc.getFullName());
            stm.setString(2, acc.getPassword());
            stm.setString(3, acc.getEmail());
            stm.setString(4, acc.getPhone());
            stm.setInt(5, acc.getStatus());
            stm.setString(6, accountId); // Sử dụng accountId để xác định tài khoản cần cập nhật

            if (stm.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Account getAccountById(String accountId) {
        Account account = null;
        try {
            String sql = "SELECT * FROM account WHERE account_id=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, accountId);

            ResultSet resultSet = stm.executeQuery();

            if (resultSet.next()) {
                account = mapResultSetToAccount(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }


    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        String accountId = resultSet.getString("account_id");
        String fullName = resultSet.getString("full_name");
        String password = resultSet.getString("password");
        String email = resultSet.getString("email");
        String phone = resultSet.getString("phone");
        Integer status = resultSet.getInt("status");
        return new Account(accountId, fullName, password, email, phone, status);
    }
}
