/**
 * Created by ao on 2017/10/31.
 */
function addRow(button){
    var table = button.parentNode.parentNode.parentNode;
    var buttonRow = button.parentNode.parentNode;
    var row = table.insertRow();
    for(var i = 0;i < buttonRow.cells.length;i++){
        var cell = row.insertCell(i);
        cell.class =  buttonRow.cells[i].class;
        cell.align = buttonRow.cells[i].align;
        cell.bgColor = buttonRow.cells[i].bgColor;
        cell.innerHTML = buttonRow.cells[i].innerHTML;
    }
}
function deleteRow(button){
    var index = getRowIndex(button);
    var table = getTable(button);
    if(table.rows.length == 1){
        return;
    }
    table.deleteRow(index-1);
}
function deleteAllRow(table){
    for(var i = 0;i < table.rows.length;i++){
        table.deleteRow(0);
    }
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
function updateOrderNum(tableIndex){
    var orderNums = document.getElementsByName("ordinalNumber");
    var startIndex = getRowCount(tableIndex-1);
    var endIndex = getRowCount(tableIndex);
    for(var  i = startIndex;i < endIndex;i++){
        orderNums[i].value = i - startIndex+1;
    }
}
function getRowCount(){
    var tableList = document.getElementsByName("table");
    var count = 0;
    for(var i = 0;i <= tableIndex;i++){
        count += tableList[i].rows.length;
    }
    return count;
}