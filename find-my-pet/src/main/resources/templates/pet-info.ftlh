<!doctype html>

<head>
    <title>Chipped Pets Helper</title>
</head>
<body>
    <h1>Chipped Pets Helper</h1>
    <h4>Подробная информация о питомце</h4>

    <b>Чип:</b> ${pet.chipId}<br>
    <b>Вид животного:</b> ${pet.type}<br>
    <b>Порода:</b> ${pet.breed}<br>
    <b>Пол:</b> ${pet.sex}<br>
    <b>Кличка:</b> ${pet.name}<br>
    <b>Дата рождения:</b> ${pet.dateOfBirth}<br><br>
    <hr>
    <h3>Информация о хозяине</h3>
    <b>Имя:</b> ${pet.user.name}<br>
    <b>email:</b> ${pet.user.email}<br>
    <b>Телефон:</b> ${pet.user.phoneNumber}<br>
</body>