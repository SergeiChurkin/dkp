<div>
    <form action="/main/update_equip" method="post" enctype="multipart/form-data">
        <div class="form-row">
            <div class="col">
                <select class="custom-select mb-2" name="type_1">
                    <#assign type = 1>
                    <#assign count = 0>
                    <#assign userEquipItemId = 0>
                    <#list equips as equipItem>
                    <#if type==equipItem.equipType.id>
                        <#list user_equips as userEquipItem>
                            <#if equipItem.id==userEquipItem.id>
                            <option value="${equipItem.id}" selected>${equipItem.equipType.name} - ${equipItem.grade}</option>
                            <#assign userEquipItemId = equipItem.id>
                            </#if>
                        </#list>
                <#if equipItem.id!=userEquipItemId>
                <option value="${equipItem.id}">${equipItem.equipType.name} - ${equipItem.grade}</option>
                </#if>
                    <#else>
                </select>
                </div>
                <#assign count++>
                <#if count==2>
                </div>
                <div class="form-row">
                    <#assign count = 0>
                </#if>
                <div class="col">
                <select class="custom-select  mb-2" name="type_${equipItem.equipType.id}">
                    <#list user_equips as userEquipItem>
                        <#if equipItem.id==userEquipItem.id>
                        <option value="${equipItem.id}" selected>${equipItem.equipType.name} - ${equipItem.grade}</option>
                        <#assign userEquipItemId = equipItem.id>
                        </#if>
                    </#list>
                    <#if equipItem.id!=userEquipItemId>
                    <option value="${equipItem.id}">${equipItem.equipType.name} - ${equipItem.grade}</option>
                    </#if>
                        <#assign type = equipItem.equipType.id>
                    </#if>

                </#list>
                </select>
            </div>
        </div>


        <div class="form-row">
            <div class="col">
                <label>Уровень:</label>
                <select class="custom-select mb-3" name="level">
                    <#list levels as level>
                        <#if level.id==user_level.id>
                        <option value="${level.value}" selected>${level.value}</option>
                        <#else>
                        <option value="${level.value}">${level.value}</option>
                        </#if>
                </#list>
                </select>
            </div>
            <div class="col">
                <label>Профа:</label>
                <select class="custom-select mb-3" name="prof">
                    <#list profs as prof>
                        <#if prof.id==user_prof.id>
                        <option value="${prof.value}" selected>${prof.value}</option>
                        <#else>
                        <option value="${prof.value}">${prof.value}</option>
                        </#if>
                    </#list>
                </select>
            </div>
        </div>
        <div class="my-3">
            <input type="file" name="reg_screenshot" required accept="image/*">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Сохранить" class="btn btn-primary" />
    </form>

</div>