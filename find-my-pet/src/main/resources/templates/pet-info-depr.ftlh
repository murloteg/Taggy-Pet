<!doctype html>

<head>
    <title>Chipped Pets Helper</title>
</head>
<body>
    <h1>Chipped Pets Helper</h1>
    <h4>Подробная информация о питомце</h4>
    <#if pet??>
        <b>Чип:</b> ${pet.chipId}<br>
        <b>Вид животного:</b> ${pet.type}<br>
        <b>Порода:</b> ${pet.breed}<br>
        <b>Пол:</b> ${pet.sex}<br>
        <b>Кличка:</b> ${pet.name}<br>
        <hr>
        <h3>Информация о хозяине</h3>
        <#list pet.users as user>
            <b>Имя:</b> ${user.getFirstName()}<br>
            <b>email:</b> ${user.getEmail()}<br>
            <b>Телефон:</b> ${user.getPhoneNumber()}<br>
        </#list>
    <#else>
        <h4>К сожалению, в нашей базе нет питомца с указанным чипом</h4>
    </#if>
</body>
