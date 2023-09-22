package vn.edu.iuh.fit.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.iuh.fit.models.Account;
import vn.edu.iuh.fit.models.GrantAccess;
import vn.edu.iuh.fit.models.Role;
import vn.edu.iuh.fit.repositories.AccountRepositories;
import vn.edu.iuh.fit.repositories.GrantAccessRepository;
import vn.edu.iuh.fit.repositories.LogRepository;
import vn.edu.iuh.fit.repositories.RoleRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/login"})
public class ControllerServlet extends HttpServlet {
    private AccountRepositories AccRepo = new AccountRepositories();
    private GrantAccessRepository grantAccessRepository;
    private RoleRepository roleRepository;
    private LogRepository logRepository;



    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null) {
            resp.getWriter().println("Action is missing!");
            return;
        }

        HttpSession session = req.getSession(false);

        if (session != null || "login".equals(action)) {
            switch (action) {
                case "login":
                    login(req, resp);
                    break;
                case "Create":
                    CRUD(req, resp, "Create");
                    break;
                case "Delete":
                    CRUD(req, resp, "Delete");
                    break;
                case "Update":
                    CRUD(req, resp, "Update");
                    break;
                default:
                    resp.getWriter().println("Invalid action!");
            }
        } else {
            resp.getWriter().println("Please login!");
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String account_id = req.getParameter("account_id");
        String password = req.getParameter("password");

        // Xác thực người dùng bằng cách gọi hàm authenticateUser từ AccountRepository
        AccountRepositories accountRepository = new AccountRepositories(); // Khởi tạo AccountRepository

        Account authenticatedAccount = accountRepository.authenticateUser(account_id, password);

        if (authenticatedAccount != null) {
            // Đăng nhập thành công
            String accountId = authenticatedAccount.getAccountId();

            GrantAccessRepository grantAccessRepository = new GrantAccessRepository();
            List<GrantAccess> grantAccessList = grantAccessRepository.findByAccount(accountId);

            List<String> roleIds = getRoleIdsFromGrantAccess(grantAccessList);

            HttpSession session = req.getSession(true); // Tạo phiên làm việc nếu chưa có

            // Lưu thông tin đăng nhập và vai trò của người dùng trong phiên làm việc
            session.setAttribute("account_id", authenticatedAccount.getAccountId());
            session.setAttribute("role_ids", roleIds);

            // Chuyển hướng đến trang tương ứng dựa trên vai trò
            if (roleIds.contains("admin")) {
                resp.sendRedirect("admin.jsp");
            } else {
                // Gửi danh sách tài khoản đến trang JSP để hiển thị
                List<Account> allAccounts = accountRepository.getAllAccount();
                req.setAttribute("ACCOUNT", allAccounts);

                // Sử dụng RequestDispatcher để chuyển tiếp yêu cầu đến trang JSP
                RequestDispatcher dispatcher = req.getRequestDispatcher("accountList.jsp");
                dispatcher.forward(req, resp);
            }
        } else {
            // Đăng nhập thất bại
            resp.getWriter().println("Login failed. Please check your credentials.");
        }
    }


    private void CRUD(HttpServletRequest req, HttpServletResponse resp, String action) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            switch (action) {
                case "Create":
                    String accountID = req.getParameter("account_id");
                    String fullName = req.getParameter("full_name");
                    String password = req.getParameter("password");
                    String email = req.getParameter("email");
                    String phone = req.getParameter("phone");
                    int status = Integer.parseInt(req.getParameter("status"));

                    // Tạo tài khoản mới
                    Account acc_new = new Account(accountID, fullName, password, email, phone, status);

                    // Thử thêm tài khoản
                    Boolean result_add_account = AccRepo.addAccount(acc_new);

                    if (result_add_account) {
                        resp.getWriter().println("Create success !!!");
                    } else {
                        resp.getWriter().println("Create failed !!!");
                    }
                    break;

                case "Delete":
                    String uidToDelete = req.getParameter("account_id_delete");
                    Boolean resultDeleteAccount = AccRepo.deleteAccount(uidToDelete);
                    if (resultDeleteAccount) {
                        resp.getWriter().println("Deletion success !!!");
                    } else {
                        resp.getWriter().println("Deletion failed !!!");
                    }
                    break;

                case "Update":
                    String accountIdToUpdate = req.getParameter("account_id");
                    String newFullName = req.getParameter("full_name");
                    String newPassword = req.getParameter("password");
                    String newEmail = req.getParameter("email");
                    String newPhone = req.getParameter("phone");
                    int newStatus = Integer.parseInt(req.getParameter("status"));

                    Account updatedAccount = new Account(accountIdToUpdate, newFullName, newPassword, newEmail, newPhone, newStatus);


                    Boolean resultUpdateAccount = AccRepo.updateAccount(updatedAccount, accountIdToUpdate);

                    if (resultUpdateAccount) {
                        resp.getWriter().println("Update success !!!");
                    } else {
                        resp.getWriter().println("Update failed !!!");
                    }
                    break;
                default:
            }

        } else {
            resp.getWriter().println("Please login !!!");
        }
    }
    private List<String> getRoleIdsFromGrantAccess(List<GrantAccess> grantAccessList) {
        List<String> roleIds = new ArrayList<>();

        // Duyệt qua danh sách các đối tượng GrantAccess
        for (GrantAccess grantAccess : grantAccessList) {
            // Kiểm tra quy tắc để xác định roleId, ví dụ: nếu isGrant là 1, coi đó là vai trò admin
            if (grantAccess.getIsGrant() == 1) {
                roleIds.add(grantAccess.getRoleId());
            }
        }
        // Trả về danh sách roleId
        return roleIds;
    }
}
