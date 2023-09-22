<!DOCTYPE html>
<html>
<head>
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        .navbar {
            overflow: hidden;
            background-color: #333;
        }

        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
        }

        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }

        form {
            display: none; /* Hide all forms by default */
        }

        #createUpdateForm {
            display: block;
        }
    </style>
    <script>
        function showForm(formId) {
            var forms = document.getElementsByTagName('form');
            for (var i = 0; i < forms.length; i++) {
                forms[i].style.display = 'none'; // Hide all forms
            }
            document.getElementById(formId).style.display = 'block'; // Show the clicked form
        }
    </script>
</head>
<body>

<div class="navbar">
    <a href="#" onclick="showForm('createUpdateForm')">Create/Update</a>
    <a href="#" onclick="showForm('deleteForm')">Delete</a>
</div>

<h2>Create/Update/Delete Account</h2>
<form id="createUpdateForm" method="post" action="admin">
    <label for="account_id">AccountID:</label>
    <input type="text" id="account_id" name="account_id" required><br><br>
    <label for="full_name">FullName:</label>
    <input type="text" id="full_name" name="full_name" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br><br>

    <label for="phone">Phone:</label>
    <input type="text" id="phone" name="phone" required><br><br>

    <label for="status">Status:</label>
    <input type="text" id="status" name="status"><br><br>

    <button type="submit" name="action" value="Create">Create</button>
    <button type="submit" name="action" value="Update">Update</button>
</form>

<form id="deleteForm" method="post" action="admin">
    <label for="account_id_delete">AccountId:</label>
    <input type="text" id="account_id_delete" name="account_id_delete" required><br><br>

    <button type="submit" name="action" value="Delete">Delete</button>
</form>

</body>
</html>
