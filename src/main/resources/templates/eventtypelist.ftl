<#import "parts/common.ftl" as common>
<@common.page "Типы событий>">
<#include "parts/navadmin.ftl" >
<h3 class="my-3">Типы событий</h3>
<div class="mb-3">
<form method="post" action="/admin/event_type_add">
    <div class="form-group">
        <label for="typename">Наименование события:</label>
        <input type="text" class="form-control sm-2" name="eventTypeName" id="typename" value="" required>
    </div>
    <div class="form-group">
        <label for="typescore">Очки за событие:</label>
        <input type="text" class="form-control sm-2" name="eventTypeScore" id="typescore" value="0" required>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Добавить тип события</button>
</form>
</div>

<div class="alert alert-danger" role="alert">
    При удалении типа, очки за активные события не смогут быть начислены
</div>
<table class="table table-sm table-striped">
    <caption>Типы событий</caption>
    <thead>
    <tr>
        <th scope="col">Название</th>
        <th scope="col">Очки</th>
        <th scope="col">Изменить</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <#list eventTypes as eventType>
                <tr>
                    <td>${eventType.name}</td>
                    <td>${eventType.score}</td>
                    <td>
                        <form method="post" action="/admin/event_type_save/" class="form-inline">
                        <input type="hidden" name="eventTypeId" value="${eventType.id}">
                        <input type="text" name="score" class="form-control form-control-sm" size="3" required>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-success btn-sm ml-2">Сохранить</button>
                        </form>
                    </td>
                    <td>
                        <a href="/admin/event_type_del/${eventType.id}" class="btn btn-danger btn-sm">Удалить</a>
                    </td>
                </tr>
    </#list>
</table>






</@common.page>