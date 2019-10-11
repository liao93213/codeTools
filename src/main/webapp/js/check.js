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
         * 确保都执行
         */
        var result = this.requiredCheck();
        result = this.intCheck() && result;
        result = this.naturalIntCheck() && result;
        result = this.positiveIntCheck() && result;
        result = this.checkedCheck() && result;
        result = this.towScaleCheck() && result;
        result = this.naturalTowScaleCheck() && result;
        result = this.positiveTowScaleCheck() && result;
        this.showErrorTipList();
        return result;
    },
    requiredCheck: function () {
        var notEmptyList = document.getElementsByClassName("required");
        return this.basicsValidate.call(this, notEmptyList, this.notEmpty);
    },
    checkedCheck: function () {
        var needCheckedList = document.getElementsByClassName("checked");
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
    intCheck: function () {
        var needIntList = document.getElementsByClassName("int");
        return this.basicsValidate.call(this, needIntList, this.validateInt);
    },
    naturalIntCheck: function () {
        var needIntList = document.getElementsByClassName("naturalInt");
        return this.basicsValidate.call(this, needIntList, this.validateNaturalInt);
    },
    positiveIntCheck: function () {
        var naturalIntList = document.getElementsByClassName("positiveInt");
        return this.basicsValidate.call(this, naturalIntList, this.validatePositiveInt);
    },

    towScaleCheck: function () {
        var towScaleList = document.getElementsByClassName("towScale");
        return this.basicsValidate.call(this, towScaleList, this.validateTowScale);
    },

    naturalTowScaleCheck: function () {
        var towScaleList = document.getElementsByClassName("naturalTowScale");
        return this.basicsValidate.call(this, towScaleList, this.validateNaturalTowScale);
    },

    positiveTowScaleCheck: function () {
        var positiveTowScaleList = document.getElementsByClassName("positiveTowScale");
        return this.basicsValidate.call(this, positiveTowScaleList, this.validatePositiveTowScale);
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

    basicsValidate: function (list, validateFun) {
        if (list == null || list.length == 0) {
            return true;
        }
        var result = true;
        for (var i = 0; i < list.length; i++) {
            if (list[i].value != null && !validateFun.call(this, list[i].value)) {
                this.formatError(list[i]);
                result = false;
            }
        }
        return result;
    },
    notEmpty: function (value) {
        return (value != null && value.trim().length != 0);
    },

    /**
     * 验证整数
     *
     * @param value        输入的数值
     * @returns
     */
    validateInt: function (value) {
        if (value == null || value.trim().length == 0) {
            return false;
        }
        var reg = new RegExp("^-?[0-9]+$");
        return reg.test(value);
    },

    /**
     * 验证自然数
     *
     * @param value        输入的数值
     * @returns
     */
    validateNaturalInt: function (value) {
        if (value == null || value.trim().length == 0) {
            return false;
        }
        var reg = new RegExp("^[0-9]+$");
        return reg.test(value);
    },

    /**
     * 验证正整数
     *
     * @param value        输入的数值
     * @returns
     */
    validatePositiveInt: function (value) {
        if (value == null || value.trim().length == 0) {
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
        if (value == null || value.trim().length == 0) {
            return false;
        }
        var reg = new RegExp("^-?[0-9]+\.?[0-9]{0,2}$")
        return reg.test(value);
    },

    /**
     * 自然金额验证
     *
     * @param value        输入的数值
     * @returns
     */
    validateNaturalTowScale: function (value) {
        if (value == null || value.trim().length == 0) {
            return false;
        }
        var reg = new RegExp("^[0-9]+\.?[0-9]{0,2}$")
        return reg.test(value);
    },
    /**
     * 正金额验证
     *
     * @param value        输入的数值
     * @returns
     */
    validatePositiveTowScale: function (value) {
        return this.validateNaturalTowScale.call(this, value) && parseFloat(value) > 0;
    }
}


