
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h2>${message}</h2>
       <h2>Login</h2>
        <form action="Login" method="post">
            <label>User Name</label>
            <input type="text" name="username" /> <br>
            <label>Password</label>
            <input type="password" name="password" /> <br>
            <input type="hidden" name="action" value="login"/>
            <input type="submit" name="Login" />
        </form>
       <h2>Register New Account</h2>
        <form action="Login" method="post">
            <label>User Name</label>
            <input type="text" name="username" /> <br>
            <label>Password</label>
            <input type="password" name="password" /> <br>
            <input type="hidden" name="action" value="register"/>
            <input type="submit" name="Register" />
        </form>
    </body>
</html>
