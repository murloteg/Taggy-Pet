<!doctype html>

<head>
    <title>Chipped Pets Helper</title>
</head>
    <body>
    <h2>Список всех питомцев в базе данных</h2>
    <hr>
    <#list pets as pet>
        <div>
            <b>Чип:</b> ${pet.chipId}<br>
            <b>Вид животного:</b> ${pet.type}<br>
            <b>Порода:</b> ${pet.breed}<br>
            <b>Пол:</b> ${pet.sex}<br>
            <b>Кличка:</b> ${pet.name}<br>
            <br>
            <h3>Информация о хозяине питомца "${pet.name}"</h3>
            <b>Имя:</b> ${pet.user.firstName}<br>
            <b>email:</b> ${pet.user.email}<br>
            <b>Телефон:</b> ${pet.user.phoneNumber}<br>
            <hr>
        </div>
    <#else>
        <h2>Не добавлено ни одного питомца</h2>
    </#list>
</body>