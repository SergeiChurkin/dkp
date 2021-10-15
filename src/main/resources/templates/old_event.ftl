<#import "parts/common.ftl" as common>
<#include "parts/security.ftl">
<@common.page "События">

<h3 class="my-3" xmlns="http://www.w3.org/1999/html">События</h3>
<#if isConfirm&&isValidate>
<div>

    <#list events as event>
            <#if event.active>
                <#if current_user.events?size <= 0>
                        <div class="card mb-3">
                        <div class="card-body">
                            <h3 class="card-title">${event.UUID?ifExists}</h3>
                            <h4 class="card-title">${event.eventType.name?ifExists}</h4>
                            <p class="card-text">${event.dateCreated?ifExists}</p>
                            <form method="post" action="/event_take" enctype="multipart/form-data">
                                <div class="custom-file  mb-3">
                                    <input type="file" class="custom-file-input" id="customFile" name="screenshot" required>
                                    <label class="custom-file-label" for="customFile">Выберите изображение</label>
                                </div>
                                <input type="hidden" name="eventId" value="${event.id}"/>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-primary">Учавствовать</button>
                            </form>

                        </div>
                    </div>
                <#else>
                    <#list current_user.events as userEvent>
                        <#if userEvent.id == event.id>
                            <div class="card mb-3">
                                <div class="card-body">
                                    <h3 class="card-title">${event.UUID?ifExists}</h3>
                                    <h4 class="card-title">${event.eventType.name?ifExists}</h4>
                                    <p class="card-text">${event.dateCreated?ifExists}</p>
                                    <form method="post" action="/event_take" enctype="multipart/form-data">
                                        <div class="custom-file  mb-3">
                                            <input type="file" class="custom-file-input" id="customFile1" name="screenshot" required>
                                            <label class="custom-file-label" for="customFile1">Выберите изображение</label>
                                        </div>
                                        <input type="hidden" name="eventId" value="${event.id}"/>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <button type="submit" class="btn btn-primary">Учавствовать</button>
                                    </form>

                                </div>
                            </div>
                        </#if>
                    </#list>
                </#if>
            </#if>
    </#list>
</div>

</#if>




<#elseIf  event.active && userEvent.id != event.id >
<div class="card mb-3">
    <div class="card-body">
        <h3 class="card-title">${event.eventType.name?ifExists}</h3>
        <p class="card-text">${event.dateCreated?ifExists}</p>
        <form method="post" action="/event_take">
            <input type="hidden" name="eventId" value="${event.id}"/>
            <input type="hidden" name="userEventid" value="${userEvent.id}"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Участвовать</button>
        </form>
    </div>
</div>




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