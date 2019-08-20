/**
 * Created by liao on 2019/8/20.
 * 获取自定义属性document.getElementById("errormessage").getAttribute("errormessage")
 */
var tipObjList = new Array();
function checkForm() {
    var result = notEmptyCheck() && needIntCheck() && notZeroCheck() && towScaleCheck() && needCheckedCheck();
    showErrorTipList();
    return result;
}
function notEmptyCheck() {
    var notEmptyList = document.getElementsByClassName("notEmpty");
    if (notEmptyList == null || notEmptyList.length == 0) {
        return true;
    }
    var result = true;
    for (var i = 0; i < notEmptyList.length; i++) {
        if (notEmptyList[i].value == null || notEmptyList[i].value.trim().length == 0) {
            formatError(notEmptyList[i]);
            result = false;
        }
    }
    return result;
}
function needCheckedCheck() {
    var needCheckedList = document.getElementsByClassName("needChecked");
    if (needCheckedList == null || needCheckedList.length == 0) {
        return true;
    }
    var result = true;
    for (var i = 0; i < needCheckedList.length; i++) {
        var checkedList = document.getElementsByName(needCheckedList[i].name);
        for (var j = 0; j < checkedList.length; i++) {
            if (checkedList[j].checked == true) {
                break;
            }
            if (j == checkedList.length - 1) {
                formatError(needCheckedList[i]);
                result = false;
            }
        }
    }
    return result;
}
function needIntCheck() {
    var needIntList = document.getElementsByClassName("needChecked");
    if (needIntList == null || needIntList.length == 0) {
        return true;
    }
    var result = true;
    for (var i = 0; i < needIntList.length; i++) {
        if (needIntList[i].value != null && !validateInt(needIntList[i].value)) {
            formatError(needIntList[i]);
            result = false;
        }
    }
    return result;
}
function notZeroCheck() {
    var notZeroList = document.getElementsByClassName("notZero");
    if (notZeroList == null || notZeroList.length == 0) {
        return true;
    }
    var result = true;
    for (var i = 0; i < notZeroList.length; i++) {
        if (notZeroList[i].value != null && !validateNoZeroInt(notZeroList[i].value)) {
            formatError(notZeroList[i]);
            result = false;
        }
    }
    return result;
}

function towScaleCheck() {
    var towScaleList = document.getElementsByClassName("needChecked");
    if (towScaleList == null || towScaleList.length == 0) {
        return true;
    }
    var result = true;
    for (var i = 0; i < towScaleList.length; i++) {
        if (towScaleList[i].value != null && !validateTowScale(towScaleList[i].value)) {
            result = false;
        }
    }
    return result;
}
function formatError(obj) {
    var isContain = false;
    for (var i = 0; i < tipObjList.length; i++) {
        if (obj === tipObjList[i]) {
            isContain = true;
        }
    }
    if (!isContain) {
        tipObjList.push(obj);
    }
}
function showErrorTipList() {
    for (var i = 0; i < tipObjList.length; i++) {
        showErrorTip(tipObjList[i]);
    }
    tipObjList = new Array();
}
function showErrorTip(obj) {
    var errorMessage = obj.getAttribute("errorMessage");
    alert(errorMessage);
}

/**
 * 验证整数
 *
 * @param value        输入的数值
 * @returns
 */
function validateInt(value) {
    if (value === undefined || value === null) {
        return false;
    }
    var reg = new RegExp("^-?[0-9]+$");
    return reg.test(value);
}

/**
 * 验证正整数
 *
 * @param value        输入的数值
 * @returns
 */
function validateNoZeroInt(value) {
    if (value === undefined || value === null) {
        return false;
    }
    var reg = new RegExp("^?[1-9]+$");
    return reg.test(value);
}

/**
 * 金额验证
 *
 * @param value        输入的数值
 * @returns
 */
function validateTowScale(value) {
    if (value === undefined || value === null) {
        return false;
    }
    var reg = new RegExp("^?[0-9]+\.?[0-9]{0,2}")
    return reg.test(value);
}
