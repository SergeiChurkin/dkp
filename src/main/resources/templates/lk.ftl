<#import "parts/common.ftl" as common>
<#include "parts/security.ftl">
<@common.page "Личный кабинет">

<h3 class="my-3">Личный кабинет</h3>
<#if !isValidate && isRequested>
<div>
    Профиль ожидает подтверждения
</div>
</#if>
<#if !isRequested && !isConfirm>
<div>
    <a href="/main" >Заполните профиль</a>
</div>
</#if>

<#if isConfirm && isValidate>
<div class="row">
    <div class="col-sm-6">
        <div class="card mb-3  shadow rounded">
            <div class="card-body">
                <h3 class="card-title">${user.coefficient?ifExists?string["0.##"]}</h3>
                <p class="card-text">Ваш коэффициент</p>
            </div>
        </div>
    </div>
    <div class="col-sm-6">
        <div class="card mb-3 shadow rounded">
            <div class="card-body">
                <h3 class="card-title">${user.points?ifExists?string["0.##"]}</h3>
                <p class="card-text">Ваши очки</p>
            </div>
        </div>
    </div>
</div>
<div class="card mb-3  shadow rounded">
    <div class="card-body">
        <h4 class="card-title">${user.username?ifExists}</h4>
        <p class="card-text">Уровень: <b>${level.value?ifExists}</b></p>
        <p class="card-text">Профа: <b>${prof.value?ifExists}</b></p>
        <p class="card-text">Email: <b>${user.email?ifExists}</b></p>
    <p class="card-text">Экипировка:</p>
        <ul class="list-group mb-3">
            <#list equips as equip>
            <li class="list-group-item">
                <span class="badge badge-primary badge-pill">${equip.grade}</span>
                ${equip.equipType.name}
            </li>
            </#list>
        </ul>
        <a href="/main/edit" class="btn btn-primary">Изменить</a>
    </div>
</div>


</#if>

</@common.page>