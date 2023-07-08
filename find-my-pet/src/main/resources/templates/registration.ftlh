<!doctype html>

<head>
    <title>Chipped Pets Helper</title>
</head>
<body>
    <h1>Chipped Pets Helper</h1>
    <h4>Регистрация</h4>
    <form action="/registration" method="post">
        Имя пользователя: <input type="text" name="username"> <br><br>
        Email: <input type="email" name="email"> <br><br>
        Номер телефона: <input type="text" name="phoneNumber"> <br><br>
        Пароль: <input type="password" name="password"> <br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Зарегистрироваться">
    </form>
</body>