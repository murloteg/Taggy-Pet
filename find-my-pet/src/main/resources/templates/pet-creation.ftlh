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
        Пол: <input required list="options" id=sex name="sex">
        <datalist id="options" type="text">
            <option selected value="Самец">Самец</option>
            <option value="Самка">Самка</option>
        </datalist>
        <br><br>
        Кличка: <input type="text" name="name"/> <br><br>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Добавить питомца"/> <br><br>
    </form>
</body>
