<#import "parts/common.ftl" as common>
<@common.page "События">
<#include "parts/navadmin.ftl" >
<h3 class="my-3">События</h3>
<div class="row">
    <div class="col-sm-6">
        <form method="post" action="/admin/event_add">
            <div class="form-group">
            <label for="typeselect">Тип события:</label>
            <select class="custom-select mb-2" name="eventTypeId" id="typeselect">
            <#list event_types as eventtype>
                <option value="${eventtype.id}">${eventtype.name}</option>
            </#list>
            </select>
            </div>
            <div class="form-group">
                <label for="eventcost">Коэффициент:</label>
                <input type="text" name="newCost" value="1" class="form-control sm-2" id="eventcost">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Добавить событие</button>
        </form>
    </div>
</div>
<h3 class="my-3">Текущие события</h3>
<table class="table table-sm table-striped">
    <thead>
    <tr>
        <th scope="col">Дата</th>
        <th scope="col">Тип</th>
        <th scope="col">UUID</th>
        <th scope="col">Коэф</th>
        <th scope="col"></th>
        <th scope="col"></th>
        <th scope="col"></th>
    </tr>
    </thead>
    <#list events?sortBy("dateCreated") as event>
        <#if event.active>
        <tr>
            <td>${event.dateCreated?ifExists}</td>
            <td><#if event.eventType??>${event.eventType.name}<#else>ТИП УДАЛЁН</#if></td>
            <td>${event.UUID?ifExists}</td>
            <td>
                <form method="post" action="/admin/event_save/" class="form-inline">
                    <input type="hidden" name="event_id" value="${event.id}" size="2">
                    <input type="text" name="new_cost" value="${event.cost}" class="form-control form-control-sm" size="1"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-success ml-2 btn-sm">Сохранить</button>
                </form>
            </td>
            <td>
                <a href="/admin/events/${event.id}" class="btn btn-secondary btn-sm">
                    Просмотр
                </a>
            </td>
            <td>
                <form method="post" action="/admin/event_close/" class="form-inline">
                    <input type="hidden" name="event_id" value="${event.id}">
                    <input type="text" name="closeTime" placeholder="Минуты" class="form-control form-control-sm" size="4" required/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-warning ml-2 btn-sm">Завершить</button>
                </form>
            </td>
            <td>
                <a href="/admin/event_del/${event.id}" class="btn btn-danger btn-sm">Удалить</a>
            </td>
        </tr>
        </#if>
</#list>
</table>

<h3 class="my-3">Завершённые события</h3>
<table class="table table-sm table-striped">
    <thead>
    <tr>
        <th scope="col">Дата</th>
        <th scope="col">Тип</th>
        <th scope="col">Коэф</th>
        <th scope="col"></th>
        <th scope="col"></th>
    </tr>
    </thead>
    <#list events?sortBy("dateCreated") as event>
    <#if !event.active>
    <tr>
        <td>${event.dateCreated?ifExists}</td>
        <td><#if event.eventType??>${event.eventType.name}<#else>ТИП УДАЛЁН</#if></td>
        <td>${event.cost}</td>
        <td>${event.dateClosed?ifExists}</td>
        <td>
            <a href="/admin/events/${event.id}">
                <button type="submit" class="btn btn-secondary btn-sm">Просмотр</button>
            </a>
        </td>
    </tr>
</#if>
</#list>
</table>



</@common.page>