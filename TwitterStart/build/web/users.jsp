<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
        
    </head>
    <body>
        
        <h2>Users</h2>
        <table>
            <tr>
                <th>Id</th>
                <th>Username</th>
                <th>Password Hash</th>
            </tr>
            <c:forEach var ="user" items="${users}">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.username}" /></td>
                    <td><c:out value="${user.password}" /></td>
                </tr>
            </c:forEach>
        </table><br>
        <h2>Login</h2>
        <form action="Twitter" method="post">
            <label>User Name</label>
            <input type="text" name="username" /> <br>
            <label>Password</label>
            <input type="password" name="password" /> <br>
            <input type="hidden" name="action" value="Login"/>
            <input type="submit" name="Login" />
        </form>
        <h2>Add User</h2>
        <form action="Twitter" method="post">
            <label>User Name</label>
            <input type="text" name="username" /> <br>
            <label>Password</label>
            <input type="password" name="password" /> <br>
            <input type="hidden" name="action" value="createUser"/>
            <input type="submit" name="Add User" />
        </form>
        <h2>Update User</h2>
        <form action="Twitter" method="post">
            <label>ID</label>
            <input type="text" name="id" /> <br>
            <label>User Name</label>
            <input type="text" name="username" /> <br>
            <label>Password</label>
            <input type="password" name="password" /> <br>
            <input type="hidden" name="action" value="updateUser"/>
            <input type="submit" name="Login" />
        </form>
    </body>
</html>
