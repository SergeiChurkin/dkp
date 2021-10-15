<#include "security.ftl">
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <a class="navbar-brand" href="/">ClanPage</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item mx-2">
                <a  href="/lk" class="btn btn-light">Личный кабинет</a>
            </li>
            <#if isLogged>
            <li class="nav-item mx-2">

                    <#if isConfirm && isValidate>
                <a href="/main/edit" class="btn btn-light">
                    Редактировать профиль</a>
                    <#else>
                    <a href="/main" class="btn btn-light">
                    Заполнить профиль</a>
                    </#if>

            </li>
            </#if>
            <#if isLogged>
            <li class="nav-item mx-2">
                <a href="/event" class="btn btn-light">События</a>
            </li>
            <li class="nav-item mx-2">
                <a href="/table/score" class="btn btn-light">Сводная таблица</a>
            </li>
            </#if>
            <#if !isLogged>
                <li class="nav-item mx-2">
                    <a href="/registration" class="btn btn-light">Регистрация</a>
                </li>
            </#if>
            <#if isAdmin>
            <li class="nav-item mx-2">
                <a href="/admin" class="btn btn-light">Admin Panel</a>
            </li>
        </#if>
        </ul>
    </div>
    <#if name!=""&&isLogged>
    <div class="navbar-text mr-3"><b>${name}</b></div>
        <div class="mr-3">
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" value="Выйти" class="btn btn-light"/>
            </form>
        </div>

    </#if>
</nav>