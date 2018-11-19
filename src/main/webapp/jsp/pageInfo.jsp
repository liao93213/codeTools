<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="basePath" scope="page"/>
<c:forEach items="${pageInfo.pageTableList}" var="table">
    <table width="100%" class="table-1" border="0" cellpadding="3"
           cellspacing="1">
        <tr>
            <td>表格名称</td>
            <td><input type="text" value="${table.tableName}" name="tableName"></td>
            <td>是否列表</td>
            <td>
                <select name="isList">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}">${whether.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select name="isList">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}">${whether.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>每行列数</td>
            <td><input type="text" value="${table.tdNumRow}" name="tdNumRow"></td>
            <td><input type="button" value="按照表名排序"  style="color: blue" name="orderButton" onclick="orderByTableName(this)"></td>
            <td><input type="button" value="清空表格" style="color: blue" name="deleteAll" onclick="emptyTable(this)"></td>
        </tr>
    </table>
        <table width="100%" class="table-7">
        <thead>
        <th ></th>
        <th >正确顺序</th>
        <th>是否表格标题</th>
        <th >页面字段名</th>
        <th >来源表名</th>
        <th>数据库字段名</th>
        <th>数据库字段说明</th>
        <th >来源bean名称</th>
        <th>页面输入类型</th>
        <th>值类型限制</th>
        <th>必填</th>
        <th >长度限制</th>
        </thead>
        <tbody name="table">
        <c:forEach items="${table.elementList}" var="element" varStatus="ele">
        <tr>
            <td >
                <input type="button"  style="color: blue" value="删除" name="delete"  onclick="deleteRow(this)"/>
                <input type="button" style="color: blue"  value="新增" name="add"  onclick="copyRow(this)"/>
            </td>
            <td ><input type="text" value="${ele.index + 1}" name="ordinalNumber" size="3" onchange="changeOrdinalNumber(this)"></td>
            <td >
                <select style="width: 50px" name="isTableTitle" onchange="changeTableTag(this)">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}"
                                <c:if test="${whether.value eq  element.isTableTitle}">selected</c:if>>${whether.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td ><input type="text" value="${element.eleName}" name="eleName" size="14" oninput="changEleName(this)" onclick="changEleName(this)" onfocus="changEleName(this)" onblur="displayNoneByObj(this)" onkeyup="moveSelect()" onkeydown="enterConfirmValue(this)"></td>
            <td>
                <input type="text" value="${element.dbTable}" name="dbTable" size="20">
                <div name="dbTableDiv"></div>
            </td>
            <td>
                <input type="text" value="${element.dbColName}" name="dbColName" size="14" oninput="changDBColName(this)" onclick="changDBColName(this)" onfocus="changDBColName(this)" onblur="displayNoneByObj(this)" onkeyup="moveSelect()" onkeydown="enterConfirmValue(this)">
                <div name="dbColNameDiv"></div>
            </td>
            <td  >
                <input type="text" value="${element.dbComment}" name="dbComment" size="14"  oninput="changEleName(this)" onclick="changEleName(this)" onfocus="changEleName(this)" onblur="displayNoneByObj(this)" onkeyup="moveSelect()" onkeydown="enterConfirmValue(this)">
                <div name="dbCommentDiv" style="z-index:1"></div>
            </td>
            <td >
                <input type="text" value="${element.beanName}" name="beanName" size="14">
            </td>
            <td>
                <select name="inputType">
                    <c:forEach items="${inputTypeList }" var="inputType">
                        <option value="${inputType.value}"
                                <c:if test="${inputType.value eq  element.inputType}">selected</c:if>>${inputType.desc}</option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select style="width: 80px" name="typeLimit">
                    <c:forEach items="${valueTypeList }" var="valueType">
                        <option  value="${valueType.value}"
                                 <c:if test="${valueType.value eq  element.typeLimit}">selected</c:if>>${valueType.desc}</option>
                    </c:forEach>
                </select>
            </td >
            <td >
                <select style="width: 50px" name="isNullable">
                    <c:forEach items="${whetherList }" var="whether">
                        <option value="${whether.value}"
                                <c:if test="${whether.value eq  element.isNullable}">selected</c:if>>${whether.desc}</option>
                    </c:forEach>
                </select>
            </td >

            <td ><input size="5" type="text" value="${element.lengthLimit}" name="lengthLimit"></td>
        </tr>
    </c:forEach>
    </tbody>
    </table>
</c:forEach>
<%--<script>
    $("input[name='dbColName']").autoSearchText({ width: 300, itemHeight: 150,minChar:1, datafn: getColList, fn: NONE });
    $("input[name='dbTable']").autoSearchText({ width: 300, itemHeight: 150,minChar:1, datafn: getTableList, fn: NONE });
    function NONE(){

    }
</script>--%>

