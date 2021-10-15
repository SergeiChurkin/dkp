<#import "parts/common.ftl" as common>
<#include "parts/security.ftl">
<@common.page "События">

<h3 class="my-3">События</h3>
<#if isConfirm&&isValidate>
<div>
    <#if eventsWithoutScreenshot?? && eventsWithoutScreenshot?size gt 0>
        <#list eventsWithoutScreenshot as event>
            <#if event.eventType??>
            <div class="card mb-3  shadow rounded">
                <div class="card-body">
                    <h3 class="card-title">${event.eventType.name?if_exists}</h3>
                    <p class="card-text">Необходимо подтвердить участие до <b>${event.dateClosed?if_exists}</b></p>
                    <form method="post" action="/event/approve" enctype="multipart/form-data">
                        <div class="my-3">
                            <input type="file" name="screenshot" required accept="image/*">
                        </div>
                        <input type="hidden" name="eventId" value="${event.id}"/>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-primary">Подтвердить участие</button>
                    </form>
                </div>
            </div>
            </#if>
        </#list>
    </#if>

    <#if eventsNotClosed?? && eventsNotClosed?size gt 0>
        <#list eventsNotClosed as event>
            <div class="card mb-3 shadow rounded">
                <div class="card-body">
                    <h3 class="card-title">${event.eventType.name?if_exists}</h3>
                    <p class="card-text"><b>${event.dateCreated?if_exists}</b></p>
                    <p class="card-text">Ожидайте завершения события</p>
                </div>
            </div>
        </#list>
    </#if>

    <#if eventsWithScreenshot?? && eventsWithScreenshot?size gt 0>
        <#list eventsWithScreenshot as event>
            <#if event.eventType??>
                <div class="card mb-3 shadow rounded">
                    <div class="card-body">
                        <h3 class="card-title">${event.eventType.name?if_exists}</h3>
                        <p class="card-text"><b>Спасибо за участие</b></p>
                        <p class="card-text">Скриншот отправлен</p>
                    </div>
                </div>
            </#if>
        </#list>
    </#if>

    <#if activeEvents?? && activeEvents?size gt 0>
        <#list activeEvents as event>
            <#if event.eventType??>
            <div class="card mb-3 shadow rounded">
                <div class="card-body">
                    <h3 class="card-title">${event.eventType.name?if_exists}</h3>
                    <p class="card-text"><b>${event.dateCreated?if_exists}</b></p>
                    <form method="post" action="/event_take">
                        <input type="hidden" name="eventId" value="${event.id}"/>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-primary">Участвовать</button>
                    </form>
                </div>
            </div>
            </#if>
        </#list>
    </#if>


</div>

</#if>


<#if !isValidate && isRequested>
<div>
    Профиль ожидает подтверждения
</div>
</#if>

<#if !isRequested && !isValidate>
<div>
    <a href="/main" >Заполните профиль</a>
</div>
</#if>

</@common.page>