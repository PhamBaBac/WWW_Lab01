<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Account List</title>
    <!-- Thêm thẻ link để kết nối với CSS của Bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1>Account List</h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Account ID</th>
            <th>Full Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="account" items="${ACCOUNT}">
            <tr>
                <td>${account.accountId}</td>
                <td>${account.fullName}</td>
                <td>${account.email}</td>
                <td>${account.phone}</td>
                <td>${account.status}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <br>
    <a href="logout.jsp" class="btn btn-primary">Logout</a> <!-- Đường dẫn đến trang logout.jsp để đăng xuất -->
</div>

<!-- Thêm thẻ script để kết nối với JavaScript của Bootstrap -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
