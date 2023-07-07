<!doctype html>
<html lang="ru">
<meta charset="utf-8">

<head>
    <title>Chipped Pets Helper</title>
</head>

<body>
    <h2>Личный кабинет пользователя</h2>
    <ul>
        <li><a href="/pet/add-new-pet"><h3>Добавить питомца</h3></a></li>
        <li><a href="/pet/pets-list"><h3>Посмотреть моих питомцев</h3></a></li>
    </ul>
    <br><br><br>
    <hr>
    <h3>Выйти из аккаунта</h3>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Выход">
    </form>
</body>
