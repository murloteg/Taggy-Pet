<!doctype html>
<head>
    <title>Chipped Pets Helper</title>
</head>

<body>
    <h2>Здесь вы можете добавить нового питомца. Для этого заполните форму:</h2>
    <form action="/pet/create" method="post">
        Чип: <input type="text" name="chipId"/> <br><br>
        Вид животного: <input type="text" name="type"/> <br><br>
        Порода: <input type="text" name="breed"/> <br><br>
        Пол: <input type="text" name="sex"/> <br><br>
        Кличка: <input type="text" name="name"/> <br><br>
        Дата рождения: <input type="text" name="dateOfBirth"/> <br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Добавить питомца"/> <br><br>
    </form>
</body>
