<!doctype html>

<head>
    <title>Chipped Pets Helper</title>
</head>
<body>
    <h3>Авторизация</h3>
    <form action="/login" method="post">
        Email: <input type="email" name="username"> <br><br>
        Пароль: <input type="password" name="password"> <br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Вход">
    </form>
    <br>
    <hr>
    <h3>Нет аккаунта? Зарегистрируйтесь прямо сейчас!</h3>
    <a href="/registration"><h2>Зарегистрироваться</h2></a>
</body>