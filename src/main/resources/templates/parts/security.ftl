<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>

    <#assign
        user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        isAdmin = user.isAdmin()
        isRL = user.isRL()
        isLogged = true
        isConfirm = user.isConfirm()
        isValidate = user.isValidate()
        isRequested = user.isRequested()
    >
<#else>
    <#assign
        name = ""
        isRL = false
        isAdmin = false
        isLogged = false
        isConfirm = false
        isValidate = false
        isRequested = false
>
</#if>