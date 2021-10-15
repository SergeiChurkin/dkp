<#import "parts/common.ftl" as common>
<#include "parts/security.ftl">
<@common.page "Редактировать профиль">

<h3 class="my-3">Редактировать профиль</h3>


<#if isValidate && isConfirm>
    <#include "parts/usereditform.ftl">
</#if>


</@common.page>