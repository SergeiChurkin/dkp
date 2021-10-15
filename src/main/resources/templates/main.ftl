<#import "parts/common.ftl" as common>
<#include "parts/security.ftl">
<@common.page "Заполните профиль">

<h3 class="my-3">Заполните профиль</h3>

<#if !isValidate && !isRequested>
    <#include "parts/userform.ftl">
</#if>

<#if !isValidate && isRequested>
    <div>
        Профиль ожидает подтверждения
    </div>
</#if>

<#if isValidate && isConfirm>
    <#include "parts/usereditform.ftl">
</#if>


</@common.page>