<#import "parts/common.ftl" as common>
<@common.page "Событие ${event.UUID?ifExists}">
<div><a href="/admin/events">Назад </a> </div>
<h3 class="my-3">Событие ${event.UUID?ifExists}</h3>

<div>
    Имя: <#if event.eventType??>${event.eventType.name}<#else>ТИП УДАЛЁН</#if><br>
    Время создания: ${event.dateCreated}<br>
    Время завершения: ${event.dateClosed?ifExists}<br>
    Коэф: ${event.cost?ifExists}<br>
    Очки: <#if event.eventType??>${event.eventType.score}<#else>ТИП УДАЛЁН</#if><br>
    Состояние: ${event.active?string('активно','закрыто')}<br>
    UUID: ${event.UUID?ifExists}
</div>
<table class="table table-sm table-striped mx-3">
    <thead>
    <tr>
        <th scope="col">username</th>
        <th scope="col">Подтверждение</th>
        <th scope="col">Скриншот</th>
    </tr>
    </thead>
    <#if !event.active >
            <#list users_approve_event as user, screenshot>
            <tr>
                <td>${user.username?ifExists}</td>
                <td>
                    Подтвержден
                </td>
                <td> <a href="/img/${event.UUID}/${screenshot}" target=_blank>просмотр</a>

                </td>
            </tr>
        </#list>
        <#list users_confirm_event as user, screenshot>
        <tr>
            <td>${user.username?ifExists}</td>
            <td> <#if event.eventType??>
                <a href="/admin/approve_user_to_event/${event.id}/${user.id}">подтвердить</a>
                <#else>
                Тип события удалён.
                </#if>
            </td>
            <td> <a href="/img/${event.UUID}/${screenshot}" target=_blank>просмотр</a>

            </td>
        </tr>
        </#list>
        <#list users_not_confirm_event as user>
        <tr>
            <td>${user.username?ifExists}</td>
            <td>Скриншот не отправлен
            </td>
            <td></td>
        </tr>
        </#list>
    <#else>
        <#list event.users as user>
        <tr>
            <td>${user.username?ifExists}</td>
            <td>Завяка на участие подана</td>
            <td></td>
        </tr>
        </#list>
    </#if>
</table>

</@common.page>