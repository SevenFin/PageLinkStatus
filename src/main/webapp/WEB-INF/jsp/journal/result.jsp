<%--
  User: SevenFin
  Date: 2015/9/17
  Time: 9:33
  --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script language="javascript">
        function onLoad() {
            window.parent.document.getElementById("queryButton").disabled = false;
            window.parent.document.getElementById("updateButton").disabled = false;

            window.parent.document.getElementById("loadFrame").style.display = "none";
            window.parent.document.getElementById("resultFrame").style.display = "block";
        }
        function submitForm() {
            var form = document.getElementById("stateForm");

            var vendor = document.getElementById("vendor").value;
            var type = document.getElementById("type").value;
            var condition = document.getElementById("condition").value;
            var beginYear = document.getElementById("beginYear").value;
            var endYear = document.getElementById("endYear").value;
            var stateFrom = document.getElementById("stateFrom").value;
            var flag = true;
            if ("" != stateFrom) {
                var stateArr = stateFrom.split(",");
                for (var i = 0; i < stateArr.length; i++) {

                    var num = stateArr[i];
                    if (!new RegExp(/^[1-9]{1}$/).test(num) && num != "") {
                        alert("重置链接状态格式错误！");
                        flag = false;
                    }
                }
            }
//            if (type != 10) {
//                alert("暂不支持重置jinfo之外类型的数据！");
//                flag = false;
//            }
            if (flag) {
                var confirmMsg = "提供商：" + vendor + "\n数据类型：" + type + "\n条件："
                        + condition ;
                if(type != 10){
                    confirmMsg += "\n开始年：" + beginYear + "\n结束年：" + endYear;
                }
                confirmMsg += "\n重置链接状态：" + (stateFrom == "" ? "所有状态" : stateFrom)
                        + "\n是否确定重置上述条件链接状态为0？";
                if (confirm(confirmMsg)) {
                    form.submit();
                }
            }
        }
    </script>

</head>
<body onload="onLoad()" style="margin: 0px;">
<c:if test="${error==1}"><h2>请刷新页面重新登录</h2></c:if>
<c:choose>
    <c:when test="${msg==null}">
        <table width="100%" cellpadding="0" cellspacing="0" border="1px solid black">
            <tr align="center" style="height: 35px;">
                <th width="10%">年份(I_Code)</th>
                <th width="10%">链接总量(total)</th>
                <th width="10%">初始状态0</th>
                <th width="10%">正在下载1</th>
                <th width="10%">下载错误2</th>
                <th width="10%">完成下载3</th>
                <th width="10%">解析错误6</th>
                <th width="10%">无效链接7</th>
                <th width="10%">解析完成8</th>
                <th width="10%">数据入库9</th>
            </tr>
            <c:forEach items="${result}" var="statusVo">
                <tr align="center" style="height: 30px;">
                    <th>${statusVo.year}</th>
                    <td>${statusVo.count}</td>
                    <td>${statusVo.countState0}</td>
                    <td>${statusVo.countState1}</td>
                    <td>${statusVo.countState2}</td>
                    <td>${statusVo.countState3}</td>
                    <td>${statusVo.countState6}</td>
                    <td>${statusVo.countState7}</td>
                    <td>${statusVo.countState8}</td>
                    <td>${statusVo.countState9}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <h2>
                ${msg}
        </h2>
    </c:otherwise>
</c:choose>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
        <td width="40%">
            <h3>
            <c:if test="${updateMsg!=''}">
                ${updateMsg}
            </c:if>
            &nbsp;用时：${timeSpan}秒
            </h3>
        </td>
        <td>
            <%--<c:if test="${sessionScope.role==1}">--%>
                <%--<form action="/journal/updateState.im" method="post" id="stateForm" >--%>
                    <%--<input type="hidden" value="${queryObj.vendor}" name="vendor" id="vendor"/>--%>
                    <%--<input type="hidden" value="${queryObj.type}" name="type" id="type"/>--%>
                    <%--<input type="hidden" value="${queryObj.condition}" name="condition" id="condition"/>--%>
                    <%--<input type="hidden" value="${queryObj.beginYear}" name="beginYear" id="beginYear"/>--%>
                    <%--<input type="hidden" value="${queryObj.endYear}" name="endYear" id="endYear"/>--%>
                    <%--重置的状态（不填为所有，多种以‘,’分隔）：--%>
                    <%--<input type="text" name="stateFrom" value="" id="stateFrom"/>--%>
                    <%--<input type="button" value="重置状态" onclick="submitForm()"/>--%>
                <%--</form>--%>
            <%--</c:if>--%>
        </td>
    </tr>
</table>

</body>
</html>