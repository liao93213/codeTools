/**
 * Created by liao on 2019/8/20.
 * 获取自定义属性document.getElementById("errormessage").getAttribute("errormessage")
 */
function Check() {
    this.init();
}

Check.prototype = {
    constructor: Check,
    init: function () {
        this.tipObjList = new Array();
    },
    checkForm: function () {
        /**
         * 避免
         */
        var result = this.notEmptyCheck();
        result = this.needIntCheck() && result;
        result = this.notZeroCheck() && result;
        result = this.towScaleCheck() && result;
        result = this.needCheckedCheck() && result;
        this.showErrorTipList();
        return result;
    },
    notEmptyCheck: function () {
        var notEmptyList = document.getElementsByClassName("notEmpty");
        if (notEmptyList == null || notEmptyList.length == 0) {
            return true;
        }
        var result = true;
        for (var i = 0; i < notEmptyList.length; i++) {
            if (notEmptyList[i].value == null || notEmptyList[i].value.trim().length == 0) {
                this.formatError(notEmptyList[i]);
                result = false;
            }
        }
        return result;
    },
    needCheckedCheck: function () {
        var needCheckedList = document.getElementsByClassName("needChecked");
        if (needCheckedList == null || needCheckedList.length == 0) {
            return true;
        }
        var result = true;
        for (var i = 0; i < needCheckedList.length; i++) {
            var checkedList = document.getElementsByName(needCheckedList[i].name);
            for (var j = 0; j < checkedList.length; j++) {
                if (checkedList[j].checked == true) {
                    break;
                }
                if (j == checkedList.length - 1) {
                    this.formatError(needCheckedList[i]);
                    result = false;
                }
            }
        }
        return result;
    },
    needIntCheck: function () {
        var needIntList = document.getElementsByClassName("needNoZeroInt");
        if (needIntList == null || needIntList.length == 0) {
            return true;
        }
        var result = true;
        for (var i = 0; i < needIntList.length; i++) {
            if (needIntList[i].value != null && !this.validateInt(needIntList[i].value)) {
                this.formatError(needIntList[i]);
                result = false;
            }
        }
        return result;
    },
    notZeroCheck: function () {
        var notZeroList = document.getElementsByClassName("notZero");
        if (notZeroList == null || notZeroList.length == 0) {
            return true;
        }
        var result = true;
        for (var i = 0; i < notZeroList.length; i++) {
            if (notZeroList[i].value != null && !this.validateNoZeroInt(notZeroList[i].value)) {
                this.formatError(notZeroList[i]);
                result = false;
            }
        }
        return result;
    },

    towScaleCheck: function () {
        var towScaleList = document.getElementsByClassName("needChecked");
        if (towScaleList == null || towScaleList.length == 0) {
            return true;
        }
        var result = true;
        for (var i = 0; i < towScaleList.length; i++) {
            if (towScaleList[i].value != null && !this.validateTowScale(towScaleList[i].value)) {
                result = false;
            }
        }
        return result;
    },
    formatError: function (obj) {
        var isContain = false;
        for (var i = 0; i < this.tipObjList.length; i++) {
            if (obj === this.tipObjList[i]) {
                isContain = true;
            }
        }
        if (!isContain) {
            this.tipObjList.push(obj);
        }
    },
    showErrorTipList: function () {
        for (var i = 0; i < this.tipObjList.length; i++) {
            this.showErrorTip(this.tipObjList[i]);
        }
        this.tipObjList = new Array();
    },
    showErrorTip: function (obj) {
        var errorMessage = obj.getAttribute("errorMessage");
        alert(errorMessage);
    },

    /**
     * 验证整数
     *
     * @param value        输入的数值
     * @returns
     */
    validateInt: function (value) {
        if (value === undefined || value === null) {
            return false;
        }
        var reg = new RegExp("^-?[0-9]+$");
        return reg.test(value);
    },

    /**
     * 验证正整数
     *
     * @param value        输入的数值
     * @returns
     */
    validateNoZeroInt: function (value) {
        if (value === undefined || value === null) {
            return false;
        }
        var reg = new RegExp("^[1-9]+$");
        return reg.test(value);
    },

    /**
     * 金额验证
     *
     * @param value        输入的数值
     * @returns
     */
    validateTowScale: function (value) {
        if (value === undefined || value === null) {
            return false;
        }
        var reg = new RegExp("^[0-9]+\.?[0-9]{0,2}")
        return reg.test(value);
    }
}


