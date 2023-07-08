<!doctype html>

<head>
    <title>Chipped Pets Helper</title>
</head>
<body>
    <h3>Аккаунт с логином "${user.getUsername()}" был успешно удален!</h3>
    <h3>Вы можете снова использовать почту "${user.getEmail()}" для регистрации</h3>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Выход">
    </form>
</body>