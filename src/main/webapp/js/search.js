/**
 * Created by ao on 2017/11/10.
 */
var eleInfoList = null;
var olaValue = null;
function  changDBColName(obj) {
    var index = getTableIndex(obj,obj.name);
    var colName = ifBlankReturnNull(obj.value);
    if(colName == null || colName.trim().length==0){
        return ;
    }
    $.ajax({
        type: "post",
        url: basePath+"/conf/changeDBColName.do",
        data: {title:"",tableNames: document.getElementById("tableName").value,dbColName:colName},
        dataType: "json",
        async: false,
        error: function () {
            alert("查询失败");
        },
        success: function (data) {
            eleInfoList = data;
            setOldValue(obj);
            display(obj,data,"dbColNameDiv","dbColName");
            display(obj,data,"dbCommentDiv","dbComment");
            display(obj,data,"dbTableDiv","dbTable");
        }
    });
}
function  changEleName(obj) {
    var index = getTableIndex(obj,obj.name);
    var eleName = obj.value;
    if(eleName == null || eleName.trim().length==0){
        return ;
    }
    $.ajax({
        type: "post",
        url: basePath+"/conf/changeEleName.do",
        data: {title:"",tableNames: document.getElementById("tableName").value,"eleName":eleName},
        dataType: "json",
        async: false,
        error: function () {
            alert("查询失败");
        },
        success: function (data) {
            eleInfoList = data;
            setOldValue(obj);
            //setValue(0,getObjIndex(obj,obj.name));
            display(obj,data,"dbColNameDiv","dbColName");
            display(obj,data,"dbCommentDiv","dbComment");
            display(obj,data,"dbTableDiv","dbTable");
        }
    });
}
function setOldValue(obj){
    var eleIndex = getObjIndex(obj,obj.name);
    var data = new Object();
    data.dbTable =  document.getElementsByName("dbTable")[eleIndex].value;
    data.dbColName =document.getElementsByName("dbColName")[eleIndex].value  ;
    data.dbComment = document.getElementsByName("dbComment")[eleIndex].value  ;
    data.beanName = document.getElementsByName("beanName")[eleIndex].value  ;
    data.inputType = document.getElementsByName("inputType")[eleIndex].value  ;
    data.isNullable = document.getElementsByName("isNullable")[eleIndex].value  ;
    data.lengthLimit = document.getElementsByName("lengthLimit")[eleIndex].value  ;
    data.typeLimit = document.getElementsByName("typeLimit")[eleIndex].value ;
    olaValue =  data;
}
function display(obj,dataList,divName,eleName){
    var html = "<ul>";
    for(var i = 0;i < dataList.length;i++){
        html += createOneRow(divName,obj,dataList[i],eleName);
    }
    html +="</ul>";
    var index = getObjIndex(obj,obj.name);
    document.getElementsByName(divName)[index].innerHTML="";
    document.getElementsByName(divName)[index].innerHTML=html;
    document.getElementsByName(divName)[index].style.display="";
}
function createOneRow(divName,obj,data,eleName){
    var index = getObjIndex(obj,obj.name);
    return '<li '
        + 'name="'+eleName+'Li"'
        + 'onclick="confirmValue('+index+',this)" '
        + ' onmouseover="selected('+index+',this)" '
        + ' onmouseout="noSelect('+index+',this)"'
        + '>'
        + data[eleName]
        + '</li>';
}
function enterConfirmValue(obj){
    var keyCode = event.keyCode;
    var selectedIndex = getSelectLiIndex();
    if(selectedIndex == -1){
        return;
    }
    if(keyCode == 13){
        confirmValue(getObjIndex(obj,obj.name),document.getElementsByName("dbTableLi")[selectedIndex]);
    }
}
function confirmValue(eleIndex,obj){
    var index = getObjIndex(obj,obj.attributes.name.value);
    setValue(index,eleIndex);
    var div = obj.parentNode.parentNode;
    var divIndex = getObjIndex(div,div.attributes.name.value);
    displayNone(divIndex);
}
function displayNoneByObj(obj){
    displayNone(getObjIndex(obj,obj.name));
}
function displayNone(divIndex){
    document.getElementsByName("dbTableDiv")[divIndex].innerHTML = "";
    document.getElementsByName("dbColNameDiv")[divIndex].innerHTML = "";
    document.getElementsByName("dbCommentDiv")[divIndex].innerHTML = "";
    document.getElementsByName("dbTableDiv")[divIndex].style.display = "none";
    document.getElementsByName("dbColNameDiv")[divIndex].style.display = "none";
    document.getElementsByName("dbCommentDiv")[divIndex].style.display = "none";
    olaValue = null;
    eleInfoList = null;
}
function setValue(liIndex,eleIndex){
    var data = eleInfoList[liIndex];
   setValueByData(data,eleIndex);
}
function setValueByData(data,eleIndex){
    document.getElementsByName("dbTable")[eleIndex].value = data.dbTable;
    document.getElementsByName("dbColName")[eleIndex].value = data.dbColName;
    document.getElementsByName("dbComment")[eleIndex].value = data.dbComment;
    document.getElementsByName("beanName")[eleIndex].value = data.beanName;
    document.getElementsByName("inputType")[eleIndex].value = data.inputType;
    document.getElementsByName("isNullable")[eleIndex].value = data.isNullable;
    document.getElementsByName("lengthLimit")[eleIndex].value = data.lengthLimit;
    document.getElementsByName("typeLimit")[eleIndex].value = data.typeLimit;
}
function setDefault(liIndex,eleIndex){
    var data = eleInfoList[liIndex];
    document.getElementsByName("dbTable")[eleIndex].value = data.dbTable;
    document.getElementsByName("dbColName")[eleIndex].value = data.dbColName;
    document.getElementsByName("dbComment")[eleIndex].value = data.dbComment;
    document.getElementsByName("beanName")[eleIndex].value = data.beanName;
    document.getElementsByName("inputType")[eleIndex].value = data.inputType;
    document.getElementsByName("isNullable")[eleIndex].value = data.isNullable;
    document.getElementsByName("lengthLimit")[eleIndex].value = data.lengthLimit;
    document.getElementsByName("typeLimit")[eleIndex].value = data.typeLimit;
}
function selected(eleIndex,obj){
    var index = getObjIndex(obj,obj.attributes.name.value);
    var data = eleInfoList[index];
    document.getElementsByName("dbTableLi")[index].style.backgroundColor = "gray";
    document.getElementsByName("dbColNameLi")[index].style.backgroundColor = "gray";
    document.getElementsByName("dbCommentLi")[index].style.backgroundColor = "gray";
    setValue(index,eleIndex);
}
function noSelect(eleIndex,obj){
    var index = getObjIndex(obj,obj.attributes.name.value);
    document.getElementsByName("dbTableLi")[index].style.backgroundColor = "";
    document.getElementsByName("dbColNameLi")[index].style.backgroundColor = "";
    document.getElementsByName("dbCommentLi")[index].style.backgroundColor = "";
    setValueByData(olaValue,eleIndex);
    //setValue(0,eleIndex);
}
function moveInput(obj){
    var index = getObjIndex(obj);
    document.getElementsByName("divName")[index].style.display="none";
}

function moveSelect(){
    var keyCode = event.keyCode;
    var selectIndex = getSelectLiIndex();
    var li = document.getElementsByName("dbTableLi");
    var div = li[0].parentNode.parentNode;
    var divIndex = getObjIndex(div,div.attributes.name.value);
    if(li.length == 0){
        return;
    }
    if(keyCode == 38){//向上键
        if(selectIndex != -1){
            noSelect(divIndex,li[selectIndex]);
        }
        if(selectIndex > 0){
            selected(divIndex,li[selectIndex-1]);
        }
    }else if(keyCode == 40){//向下键
        if(selectIndex != -1){//有选中
            noSelect(divIndex,li[selectIndex]);
        }
        if(selectIndex < li.length-1){//不是最后一个
            selected(divIndex,li[selectIndex+1]);
        }
    }
}

function getSelectLiIndex(){
    var tableList = document.getElementsByName("dbTableLi");
    for(var i = 0;i < tableList.length;i++){
        if(tableList[i].style.backgroundColor == "gray"){
            return i;
        }
    }
    return -1;
}