<#include "security.ftl">
<#macro registration>
<#if !isLogged>
<form action="/registration" method="post">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Имя пользователя:</label>
        <div class="col-sm-6">
            <input type="text" name="username" placeholder="Логин"
                   value="<#if user??>${user.username}</#if>"
                   class="form-control ${(usernameError??)?string('is-invalid','')}" required/>
            <#if usernameError??>
            <div class="invalid-feedback">
                ${usernameError}
            </div>
        </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Пароль:</label>
        <div class="col-sm-6">
            <input type="password" name="password" placeholder="Пароль"
                   class="form-control ${(passwordError??)?string('is-invalid','')}" required/>
            <#if passwordError??>
            <div class="invalid-feedback">
                ${passwordError}
            </div>
        </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Подтвердите пароль:</label>
        <div class="col-sm-6">
            <input type="password" name="passwordConfirm" placeholder="Подтвердите пароль"
                   class="form-control ${(password2Error??)?string('is-invalid','')}" required/>
            <#if password2Error??>
                <div class="invalid-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">E-mail:</label>
        <div class="col-sm-6">
            <input type="email" name="email" placeholder="E-mail"
                   value="<#if user??>${user.email}</#if>"
                   class="form-control ${(emailError??)?string('is-invalid','')}" />
            <#if emailError??>
            <div class="invalid-feedback">
                ${emailError}
            </div>
        </#if>
    </div>
    </div>
    <div class="form-group row">
        <div class="g-recaptcha" data-sitekey="6Lcm8L4ZAAAAADudt2ZcT7lxOXLuAdK-hSKpCoN2"></div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="col-sm-5"><input type="submit" value="Зарегистрироваться" class="btn btn-primary"/></div>
</form>
</#if>
</#macro>