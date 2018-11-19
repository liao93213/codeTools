<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
         contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="basePath" scope="page"/>
<html>
<head>
    <title>开始</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv ="X-UA-Compatible" content ="IE=edge">
    <link rel="stylesheet" type="text/css" href="${basePath}/css/common.css"/>
<%--
    <link rel="stylesheet" type="text/css" href="${basePath}/js/plugin/search/search.css"/>
--%>
    <script src="${basePath}/js/jquery/jquery-3.1.1.min.js"></script>
    <script src="${basePath}/js/common.js"></script>
    <script src="${basePath}/js/pageInfo.js"></script>
    <script src="${basePath}/js/search.js"></script>

<%--
    <script src="${basePath}/js/plugin/search/search.js"></script>
--%>

</head>
<body>
<table width="1100" class="table-6" bgcolor="#FFFFFF" border="0" cellpadding="3" cellspacing="1">
    <tr>
        <td width="20%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>表名：</label>
        </td>
        <td width="80%" align="left" bgcolor="#FFFFFF"><input type="text" id="tableName" value="t_repay_cbhb_balance,t_repay_cbhb_balance_detail,t_repay_cbhb_info,t_repay_cbhb_punitive_interest,t_repay_cbhb_relation_balancebill,t_repay_cbhb_repayschedule"maxlength="500" size="120">
        </td>
    </tr>
    <tr>
        <td width="20%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>原型地址：</label>
        </td>
        <td width="80%" align="left" bgcolor="#FFFFFF">
            <input type="text" id="htmlPath" maxlength="500" size="120" value="C:\Users\ao\Desktop\html.html">
        </td>
    </tr>
    <tr>
        <td width="20%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>名称前缀字符：</label>
        </td>
        <td width="80%" align="left" bgcolor="#FFFFFF">
            <input type="text" id="ingoreChar" maxlength="500" size="120">
        </td>
    </tr>
    <tr>
        <td width="20%" align="right" bgcolor="#FFFFFF">
            <label class="control-inline"><font color=red>*</font>是否使用缓存：</label>
        </td>
        <td width="80%" align="left" bgcolor="#FFFFFF">
            <select id="useCache">
                <option value="1">是</option>
                <option value="0">否</option>
            </select>
        </td>
    </tr>
</table>
<div align="center">
    <input type="button" class="button1" onclick="toPage()" value="确定">
</div>
<div id="pageConf">

</div>
</body>
<script>
    var basePath = '${basePath}';
    function toPage(){
        var tableName = document.getElementById("tableName").value;
        var htmlPath = document.getElementById("htmlPath").value;
        var useCache = document.getElementById("useCache").value;
        $.ajax({
            type: "post",
            url: "${basePath}/conf/getPageConf.do?tableNames="+tableName+"&htmlPath="+htmlPath+"&useCache="+useCache,
            data: JSON.stringify({}),
            dataType: "html",
            contentType: "application/json",
            async: false,
            error: function () {
                alert("查询失败");
            },
            success: function (data) {
                $("#pageConf").html("");
                $("#pageConf").html(data);
            }
        });
    }
    function ifBlankReturnNull(str) {
        return str == null || str.trim().length == 0 ? null : str.trim();
    }

</script>
</html>
