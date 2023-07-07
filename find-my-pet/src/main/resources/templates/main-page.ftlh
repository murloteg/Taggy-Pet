<!doctype html>
<html lang="ru">
<meta charset="utf-8">

<head>
    <title>Chipped Pets Helper</title>
</head>
<body>
    <h1>Chipped Pets Helper</h1>
    <#if user.email??>
        <h2><a href="/personal-cabinet">В личный кабинет</a></h2>
    <#else>
        <nav>
            <ul>
                <li><h2><a href="/login">Войти</a></h2></li>
                <li><h2><a href="/registration">Зарегистрироваться</a></h2></li>
            </ul>
        </nav>
    </#if>

    <h4>Все питомцы в базе данных</h4>
    <form action="/pet/find" method="get">
        Чип: <input type="text" name="chipId"/> <br><br>
        <input type="submit" value="Найти питомца по чипу"/> <br><br>
    </form>
    <hr>
</body>