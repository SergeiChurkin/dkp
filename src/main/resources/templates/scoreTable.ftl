<#import "parts/common.ftl" as common>
<@common.page "Таблица">

<h3 class="my-3">Таблица</h3>
<table class="table table-sm table-striped shadow">
    <thead>
    <tr>
        <th scope="col">username</th>
        <th scope="col">Очки</th>
        <th scope="col">Коэф</th>
        <#list eventTypes as eventType>
        <th scope="col">${eventType.name?ifExists}</th>
        </#list>
    </tr>
    </thead>
    <#list users as user>
        <#if user.active==true>
            <tr>
                <td scope="col">${user.username?ifExists} </td>
                <td scope="col">${user.points?ifExists}</td>
                <td scope="col">${user.coefficient?ifExists}</td>
                <#list eventTypes as eventType>
                    <#assign count = 0>
                    <td scope="col">
                        <#list user.approvedEvents as event>
                            <#if event.eventType??>
                                <#if event.eventType.id == eventType.id>
                                <#assign count++>
                                </#if>
                            </#if>
                        </#list>
                    ${count}
                    </td>
                </#list>
            </tr>
        </#if>
</#list>
</table>


</@common.page>