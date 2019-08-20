/**
 * Created by cheng on 2019/8/18.
 */
function removeArrayElement(array,index){
    var newArray = new Array(array.length-1);
    var j = 0;
    for(var i = 0;i < array.length;i++){
        if(i != index) {
            newArray[j] = array[i];
            j++;
        }
    }
    return newArray;
}
function addArrayElement(array,ele,index) {
    var newArray = new Array(array.length+1);
    var j = 0;
    for(var i = 0;i < array.length;i++){
        if(i != index) {
            newArray[j] = array[i];
        }else{
            newArray[j] =  ele;
            j++;
            newArray[j] = array[i];
        }
        j++;
    }
    return newArray;
}

function addArray(array,ele) {
    return addArrayElement(array,ele,array.length);
}

function copyTbaleRow(button) {
    var table = button.parentNode.parentNode.parentNode;
    var buttonRow = button.parentNode.parentNode;
    var row = table.insertRow(buttonRow.rowIndex);
    for (var i = 0; i < buttonRow.cells.length; i++) {
        var cell = row.insertCell(i);
        cell.class = buttonRow.cells[i].class;
        cell.align = buttonRow.cells[i].align;
        cell.bgColor = buttonRow.cells[i].bgColor;
        cell.innerHTML = buttonRow.cells[i].innerHTML;
    }
    return row;
}

function copyTbaleRowWithDefaultValue(){
    var row = copyTbaleRow(button);
    clearValue(row)
}

function deleteRow(button){
    var index = getRowIndex(button);
    var table = getTable(button);
    if(table.rows.length == 1){
        return;
    }
    table.deleteRow(index-1);
}


function getRowIndex(button){
    return button.parentNode.parentNode.rowIndex;
}
function getRowNum(button){
    return button.parentNode.parentNode.parentNode.rows.length;
}

function getTable(obj){
    return obj.parentNode.parentNode.parentNode;
}

function getObjIndex(table,name){
    var tableList = document.getElementsByName(name);
    for(var i = 0;i < tableList.length;i++){
        if(tableList[i] === table){
            return i;
        }
    }
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var reg_rewrite = new RegExp("(^|/)" + name + "/([^/]*)(/|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    var q = window.location.pathname.substr(1).match(reg_rewrite);
    if(r != null){
        return unescape(r[2]);
    }else if(q != null){
        return unescape(q[2]);
    }else{
        return null;
    }
}

function ifBlankReturnNull(str) {
    return str == null || str.trim().length == 0 ? null : str.trim();
}

function clearValue(tag){
    $("#"+tag+" input[type='checkbox']").each(function(){this.checked=false;});
    $("#"+tag+" input[type='text']").each(function(){$(this).val("");;});
    $("#"+tag+" input[type='radio']").each(function(){$(this).val("");;});
    var selects = document.getElementById(tag).getElementsByTagName("select")
    if(selects == null){
        return;
    }
    for(var i = 0; i < selects.length; i++){
        selects[i].value = selects[i].options[0].value;
    }
}

function defaultSelectValue(selectId) {
    var select = document.getElementById(selectId);
    select.value = select.options[0].value;
}

function defaultSelectValue(selectId) {
    var select = document.getElementById(selectId);
    select.value = select.options[0].value;
}

function copyContent(){
    
}

function getCheckBoxValue(name){
    var checkBox = document.getElementsByName(name);
    var values = new Array();
    for(var i = 0; i < checkBox.length; i++) {
        if (checkBox[i].checked)
            values.push(checkBox[i].value);
    }
    return values;
}
function clearCheckBox(name) {
    var checkBox = document.getElementsByName(name);
    var values = new Array();
    for(var i = 0; i < checkBox.length; i++) {
        checkBox[i].checked = false;
    }
}

function checkAllCheckBox(name) {
    var checkBox = document.getElementsByName(name);
    for(var i = 0; i < checkBox.length; i++) {
        checkBox[i].checked = true;
    }
}
function getRadioValue(name){
    $("input[name="+name+"]:checked").val();
}

function getInputTextValueWithNoEmptyStr(id){
    return ifBlankReturnNull(document.getElementById(id));
}