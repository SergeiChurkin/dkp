<#import "parts/common.ftl" as common>
<@common.page "Параметры экипировки">
<#include "parts/navadmin.ftl" >
<h3 class="my-3">Параметры экипировки</h3>

<h4 class="mt-3">Экипировка</h4>

<div class="row mb-3">
    <div class="col-sm-6">
        <form method="post" action="/admin/equip_add">
            <div class="form-group">
                <label for="typeEquip">Тип экипировки:</label>
                <select class="custom-select mb-2" name="equipTypeId" id="typeEquip">
                    <#list equip_types as equip_type>
                    <option value="${equip_type.id}">${equip_type.name}</option>
                </#list>
                </select>
            </div>
            <div class="form-group">
                <label for="equipGrade">Grade:</label>
                <input type="text" name="grade" class="form-control sm-2" id="equipGrade" placeholder="Grade">
            </div>
            <div class="form-group">
                <label for="equipCost">Коэффициент:</label>
                <input type="text" name="cost" class="form-control sm-2" id="equipCost" placeholder="Коэффициент">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Добавить экипировку</button>
        </form>
    </div>
    <div class="col-sm-6">
        <form method="post" action="/admin/equip_type_add">
            <div class="form-group">
                <label for="typename">Наименование экипировки:</label>
                <input type="text" class="form-control sm-2" name="equipTypeName" id="typename" placeholder="Наименование экипировки">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Добавить тип экипировки</button>
        </form>

    </div>
</div>



<table class="table table-sm table-striped">
    <caption>Экипировка</caption>
    <thead>
    <tr>
        <th scope="col">Тип</th>
        <th scope="col">Grade</th>
        <th scope="col">Коэф</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <#list equips?sort_by(['equipType', 'id']) as equip>
                <tr>
                    <td>${equip.equipType.name}</td>
                    <td>${equip.grade}</td>
                    <td>${equip.cost}</td>
                    <td>
                        <form method="post" action="/admin/equip_save/" class="form-inline">
                        <input type="hidden" name="equip_id" value="${equip.id}">
                        <input type="text" name="new_cost" value="${equip.cost}" class="form-control form-control-sm" size="3" required>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-success btn-sm ml-2">Сохранить</button>
                        </form>

                    </td>

                </tr>
    </#list>
</table>





<h4 class="mt-3">Уровни</h4>
<table class="table table-sm table-striped">
    <caption>Уровни</caption>
    <thead>
    <tr>
        <th scope="col">Значение</th>
        <th scope="col">Коэф</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <#list levels as level>
    <tr>
        <td>${level.value}</td>
        <td>${level.cost}</td>
        <td>
            <form method="post" action="/admin/level_save/" class="form-inline">
                <input type="hidden" name="level_id" value="${level.id}">
                <input type="text" name="new_cost" value="${level.cost}" class="form-control form-control-sm" size="3" required>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-success btn-sm ml-2">Сохранить</button>
            </form>
        </td>
    </tr>
    </#list>
</table>
<form method="post" action="/admin/level_add">
    <div class="form-group">
        <label for="levelValue">Уровень:</label>
        <input type="text" class="form-control sm-2" name="levelValue" id="levelValue" placeholder="Уровень">
    </div>
    <div class="form-group">
        <label for="levelCost">Коэф:</label>
        <input type="text" class="form-control sm-2" name="new_cost" id="levelCost" placeholder="Коэф">
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Добавить уровень</button>
</form>

<h4 class="mt-3">Профы</h4>
<table class="table table-sm table-striped">
    <caption>Профы</caption>
    <thead>
    <tr>
        <th scope="col">Значение</th>
        <th scope="col">Коэф</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <#list profs as prof>
    <tr>
        <td>${prof.value}</td>
        <td>${prof.cost}</td>
        <td>
            <form method="post" action="/admin/prof_save/" class="form-inline">
                <input type="hidden" name="prof_id" value="${prof.id}">
                <input type="text" name="new_cost" value="${prof.cost}" class="form-control form-control-sm" size="3" required>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button type="submit" class="btn btn-success btn-sm ml-2">Сохранить</button>
            </form>
        </td>
    </tr>
</#list>
</table>
<form method="post" action="/admin/prof_add">
    <div class="form-group">
        <label for="profValue">Профа:</label>
        <input type="text" class="form-control sm-2" name="profValue" id="profValue" placeholder="Профа">
    </div>
    <div class="form-group">
        <label for="profCost">Коэф:</label>
        <input type="text" class="form-control sm-2" name="new_cost" id="profCost" placeholder="Коэф">
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary">Добавить профу</button>
</form>

</@common.page>