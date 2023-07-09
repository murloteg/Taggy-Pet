<!doctype html>

<head>
    <title>Chipped Pets Helper</title>
</head>
<body>
    <h2>Вы действительно хотите удалить аккаунт?</h2>
    <form action="/user/delete" method="post" >
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <h3><input type="submit" value="Удалить мой аккаунт"></h3>
    </form>
</body>