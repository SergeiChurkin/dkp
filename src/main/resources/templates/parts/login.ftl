<#macro login>
<div class="container">
<form  action="/login" method="post">
    <div class="form-group row">
        <div class="col-sm-5">
        <input type="text" class="form-control"  name="username" placeholder="Имя пользователя" required>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-5">
        <input type="password" class="form-control"  name="password" placeholder="Пароль" required>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Войти</button>

</form>
</div>
</#macro>

<#macro logout>
<div>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Выйти" class="btn btn-outline-secondary"/>
    </form>
</div>
</#macro>