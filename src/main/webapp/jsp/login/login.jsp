<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
    <jsp:include page="/jsp/header.jsp"/>

    <h1>Login</h1>
    <form action="j_security_check" method="post">
        <table>
            <tr>
                <td>Username:</td>
                <td><input type="text" name="j_username" placeholder="enter username.."/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="j_password" placeholder="enter password.."/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"/></td>
            </tr>
        </table>
    </form>

    <jsp:include page="/jsp/footer.jsp"/>
</body>
</html>