<!doctype html>
<html>
<head>
    <title>FIND MY PET</title>
</head>
<body>
<h1>FIND MY PET</h1>
<h1> <a href="/login">Войти</a></h1>
<a href="/registration">Зарегистрироваться</a>
<h4>Все питомцы в базе данных</h4>

<form action="/pet/find" method="get">
    Чип: <input type="text" name="chipId"/> <br><br>
    <input type="submit" value="Найти питомца по чипу"/> <br><br>
</form>

<hr>

<h3>Добавить питомца</h3>
<form action="/pet/create" method="post">
    Чип: <input type="text" name="chipId"/> <br><br>
    Вид животного: <input type="text" name="type"/> <br><br>
    Порода: <input type="text" name="breed"/> <br><br>
    Пол: <input type="text" name="sex"/> <br><br>
    Кличка: <input type="text" name="name"/> <br><br>
    Дата рождения: <input type="text" name="dateOfBirth"/> <br><br>
    <input type="submit" value="Добавить питомца"/> <br><br>
</form>
</body>
</html>