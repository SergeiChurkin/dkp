<#import "parts/common.ftl" as common>
<@common.page "User list">
<#include "parts/navadmin.ftl" >
<h3 class="my-3">User list</h3>

<table class="table table-sm table-striped">
    <thead>
    <tr>
        <th scope="col">Имя</th>
        <th scope="col">Коэф</th>
        <th scope="col">Списать</th>
        <th scope="col">Очки</th>
        <th scope="col">Добавить</th>
        <th scope="col">Статус</th>
        <th scope="col">Скриншот</th>
        <th scope="col">Заблокировать</th>
    </tr>
    </thead>
    <#assign count=1>
    <#list users as user>
        <#if user.active==true>
                <tr>
                    <td>
                    <a data-toggle="collapse" href="#collapse${count}" role="button" aria-expanded="false" aria-controls="collapse${count}">
                                    ${user.username}
                                  </a>
                    </td>
                    <td>${user.coefficient?ifExists}</td>
                    <td>
                        <form method="POST" action="/admin/minus_points" class="form-inline">
                            <input type="text" class="form-control form-control-sm" name="points" size="3" required>
                            <input type="hidden" name="user_id" value="${user.id}"/>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger btn-sm ml-2" >Списать</button>
                        </form>
                    </td>
                    <td>
                        ${user.points?ifExists}
                    </td>
                    <td>
                        <form method="POST" action="/admin/plus_points" class="form-inline">
                            <input type="text" class="form-control form-control-sm" name="points"  size="3" required>
                            <input type="hidden" name="user_id" value="${user.id}"/>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-success btn-sm ml-2">Добавить</button>
                        </form>

                    </td>
                    <td>
                        <#if user.requested && !user.validate>
                        <a href="/admin/approve_user/${user.id}" >подтвердить</a>
                        </#if>
                        <#if !user.requested  && user.confirm>
                        подтвержден
                        </#if>
                        <#if !user.requested  && !user.confirm && !user.validate>
                        профиль не заполнен
                        </#if>
                    </td>
                    <td><#if user.regScreenshot??><a href="/img/${user.id}/${user.regScreenshot}" target="_blank" >посмотреть</a></#if></td>
                    <td><a href="/admin/off_user/${user.id}" class="btn btn-danger btn-sm" >Заблокировать</a></td>
                </tr>
                <tr class="collapse" id="collapse${count}">
                <td colspan="8">
                <#list user.equip as equipItem>
                ${equipItem.equipType.name} - ${equipItem.grade}
                </#list>
                </td>
                </tr>
                <#assign count++>
        </#if>
    </#list>
</table>
<h4 class="my-3">Заблокированные пользователи</h4>
<table class="table table-sm table-striped">
    <thead>
    <tr>
        <th scope="col">Имя</th>
        <th scope="col">Коэф</th>
        <th scope="col">Очки</th>
        <th scope="col">Разблокировать</th>
        <th scope="col">Удалить</th>
    </tr>
    </thead>
    <#list users as user>
        <#if user.active==false>
        <tr>
            <td>
                ${user.username}
            </td>
            <td>
                ${user.coefficient?ifExists}
            </td>
            <td>
                ${user.points?ifExists}
            </td>
            <td>
                <a href="/admin/on_user/${user.id}" class="btn btn-success btn-sm" >Разблокировать</a>
            </td>
            <td>
                <a href="/admin/delete_user/${user.id}" class="btn btn-danger btn-sm" >Удалить</a>
            </td>
        </tr>
        </#if>
    </#list>
</@common.page>