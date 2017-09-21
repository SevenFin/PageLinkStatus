<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: SevenFin
  Date: 2015/9/17
  Time: 9:33
  --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script language="javascript">

        function changeF0() {
            document.getElementById('txt0').value = document.getElementById('sel0').options[document.getElementById('sel0').selectedIndex].value;
        }
        function changeF1() {
            document.getElementById('txt1').value = document.getElementById('sel1').options[document.getElementById('sel1').selectedIndex].value;
        }
        function changeF2() {
            document.getElementById('txt2').value = document.getElementById('sel2').options[document.getElementById('sel2').selectedIndex].value;
        }
        function changeU1() {
            document.getElementById('stateTo').value = document.getElementById('stateTo2').options[document.getElementById('stateTo2').selectedIndex].value;
        }

        function load(){

            document.getElementById("resultFrame").style.display = "none";
            document.getElementById("loadFrame").style.display = "block";

            document.getElementById("resultForm").action = "/journal/queryByYear.im";
            document.getElementById("resultForm").submit();

            document.getElementById("queryButton").disabled = "disabled";
            document.getElementById("updateButton").disabled = "disabled";
        }

        function update(){

            // 更新状态提交前准备确认
            var vendor = document.getElementById("vendor").value;
            var type = document.getElementById("txt0").value;
            var condition = document.getElementById("condition").checked;
            var conditionValue = document.getElementById("condition").value;
            var beginYear = document.getElementById("txt1").value;
            var endYear = document.getElementById("txt2").value;
            var stateFrom = document.getElementById("stateFrom").value;
            var stateTo = document.getElementById("stateTo").value;
            var flag = true;
            if ("" != stateFrom) {
                var stateArr = stateFrom.split(",");
                for (var i = 0; i < stateArr.length; i++) {

                    var num = stateArr[i];
                    if (!new RegExp(/^[0-9]{1}$/).test(num) && num != "") {
                        alert("重置链接状态格式错误！");
                        flag = false;
                    }
                }
            }
            if (!new RegExp(/^[0-9]{1}$/).test(stateTo) && stateTo != "") {
                alert("重置链接状态格式错误！");
                flag = false;
            }
//            if (type != 10) {
//                alert("暂不支持重置jinfo之外类型的数据！");
//                flag = false;
//            }
            if (flag) {
                var confirmMsg = "提供商：" + vendor + "\n数据类型：" + type + "\n条件："
                        + (condition?conditionValue:"") ;
                if(type != 10){
                    confirmMsg += "\n开始年：" + beginYear + "\n结束年：" + endYear;
                }
                confirmMsg += "\n重置链接状态：" + (stateFrom == "" ? "所有状态" : stateFrom)
                        + "\n是否确定重置上述条件链接状态为 "+stateTo+" ？";
                if (confirm(confirmMsg)) {
                    // 更新状态提交前准备确认

                    document.getElementById("queryButton").disabled = "disabled";
                    document.getElementById("updateButton").disabled = "disabled";
                    document.getElementById("resultFrame").style.display = "none";
                    document.getElementById("loadFrame").style.display = "block";
                    //提交请求
                    var form = document.getElementById("resultForm");
                    form.action = "/journal/updateTargetState.im";
                    form.submit();
                    //提交请求
                }
            }

        }

        function time(){
            var date = new Date();
            var week = (date.getDay()==1?"一":"")+(date.getDay()==2?"二":"")+
                    (date.getDay()==3?"三":"")+(date.getDay()==4?"四":"")+
                    (date.getDay()==5?"五":"")+(date.getDay()==6?"六":"")+
                    (date.getDay()==7?"日":"");
            var month = (date.getMonth()+1)<10 ? "0"+(date.getMonth()+1) : (date.getMonth()+1);
            var day = date.getDate()<10 ? "0"+date.getDate() : date.getDate();
            var hour = date.getHours()<10 ? "0"+date.getHours() : date.getHours();
            var minute = date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
            var second = date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
            var t = date.getFullYear()+ "年"+ month + "月"
                    + day + "日 "+"星期"+week+" "
                    + hour + ":"+ minute + ":" + second;
            document.getElementById("time").innerHTML = t;

            // 初始化给select赋值
            document.getElementById("txt1").value = document.getElementById("last").value;
            document.getElementById("txt2").value = document.getElementById("first").value;
            document.getElementById("stateTo").value = 0;

            // 刘和旭专用代码段
            var username = document.getElementById("username").value;
            if(username == "liuhexu"){
                document.getElementById("vendor_WF").selected = true;
                document.getElementById("condition").checked = true;
                document.getElementById("txt1").value = document.getElementById("first").value;
            }
            // 刘和旭专用代码段

        }
    </script>

</head>
<body onload="time()">
<table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
        <td width="50%">
            <h3>当前登录 ： ${user.username}</h3>
            <input id="username" type="hidden" value="${user.username}"/>
        </td>
        <td>
            <h5>登录时间：<span id="time"></span></h5>
        </td>
        <td>
            <h5>登录IP：<span id="ip">${ip}</span></h5>
        </td>
    </tr>
</table>
<hr style="border-color: #6a68ff;"/>
<br/>
<form action="/journal/updateTargetState.im" method="post" target="resultFrame" id="resultForm">
    <table cellspacing="0" cellpadding="0" border="1 black solid" style="height: 680px;width: 100%" align="center" >
        <tr>
            <td width="25%">
                &nbsp;&nbsp;提供商：
                <select name="vendor" id="vendor">
                    <c:forEach var="vendor" items="${vendorList}">
                        <option id="vendor_${vendor}" value="${vendor}">${vendor}</option>
                    </c:forEach>
                </select>
            </td>
            <td width="25%">
                &nbsp;&nbsp;数据类型：
                <span style="position:relative; width: 200px;height:20px;margin-bottom:10px;margin-left:0px;">
                <%--<select name="type">--%>
                    <%--<option value="10">jinfo</option>--%>
                    <%--<option value="11">hold</option>--%>
                    <%--<option value="14" selected>jart</option>--%>
                <%--</select>--%>
                    <select id="sel0" style="float: left; height: 22px;width: 100px; z-index:88; position:absolute; left:0px; top:0px;" onchange="changeF0();">
                        <option value="10" selected>jinfo (10)</option>
                        <option value="11">jhold (11)</option>
                        <option value="14">jart (14)</option>
                    </select>
                    <input type="text" id="txt0" name="type"  value="10"
                           style="position:absolute; width:78px; height:14px;
                               left:1px;top:1px;z-index:99; border:1px #FFF solid" />
                </span>
            </td>
            <td width="40%" colspan="2">
                &nbsp;&nbsp;开始年：
                <span style="position:relative; width: 200px;height:20px;margin-bottom:10px;margin-left:0px;">
                    <select id="sel1" style="float: left; height: 22px;width: 100px; z-index:88; position:absolute; left:0px; top:0px;" onchange="changeF1();">
                        <c:forEach items="${yearList}" var="i" varStatus="s">
                            <c:choose>
                                <c:when test="${s.first}">
                                    <option value="${i}" id="first">${i}</option>
                                </c:when>
                                <c:when test="${s.last}">
                                    <option value="${i}" id="last">${i}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${i}">${i}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <input type="text" id="txt1" name="beginYear"
                           style="position:absolute; width:78px; height:14px;
                           left:1px;top:1px;z-index:99; border:1px #FFF solid" />
                    <span style="position:absolute; width:100px; height:14px; left:125px;top:1px;z-index:99;">结束年：</span>
                    <select id="sel2"  style="float: right; height: 22px;width: 100px; z-index:88; position:absolute; left:200px; top:0px;" onchange="changeF2();">
                        <c:forEach items="${yearList}" var="i">
                            <option value="${i}">${i}</option>
                        </c:forEach>
                    </select>
                    <input type="text" id="txt2" name="endYear"
                           style="position:absolute; width:78px; height:14px;
                           left:201px;top:1px;z-index:99; border:1px #FFF solid" />
                </span>
            </td>
            <td  width="10%" align="center">
                <%--<input type="submit" value="查询" onclick="load()"/>--%>
                <input type="button" value="查询" onclick="load()" id="queryButton"/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;&nbsp;查询条件： &nbsp;&nbsp;
                <input type="checkbox" value="I_Prior=3000" name="condition" id="condition"/>
                <label for="condition">CSCD</label>
            </td>
            <c:if test="${sessionScope.role==1}">
                <td width="10%" colspan="1">
                    &nbsp;重置状态条件：
                </td>
                <td colspan="2">
                    &nbsp;
                    <span style="position:relative; width: 200px;height:20px;margin-bottom:10px;margin-left:0px;">
                        修改前状态: &nbsp;<input type="text" style="width: 80px;" name="stateFrom"
                                            id="stateFrom" value="0"/> &nbsp;
                        修改后状态:
                        <select id="stateTo2" style="float: right; height: 22px;width: 100px;
                                z-index:88; position:absolute; left:340px; top:0px;"
                                onchange="changeU1();">
                            <option value="0" id="stateSelect">初始状态0</option>
                            <option value="1">正在下载1</option>
                            <option value="2">下载错误2</option>
                            <option value="3">完成下载3</option>
                            <option value="6">解析错误6</option>
                            <option value="7">无效链接7</option>
                            <option value="8">解析完成8</option>
                            <option value="9">数据入库9</option>
                        </select>
                        <input type="text" id="stateTo" name="stateTo"
                               style="position:absolute; width:78px; height:14px;
                                   left:341px;top:1px;z-index:99; border:1px #FFF solid" />

                        <input type="button" value="重置状态" onclick="update()" id="updateButton"
                               style="position:absolute;left:451px;top:1px;"/>
                    </span>
                </td>
            </c:if>
            <c:if test="${sessionScope.role!=1}">
                <td colspan="3">
                </td>
            </c:if>
        </tr>

        <tr style="height:580px;">
            <td align="middle" colspan="5">
                <iframe name="resultFrame" width="100%" height="100%;" frameborder="0px;" id="resultFrame"
                        style="display: none;border: 0px;">
                </iframe>
                <iframe name="loadFrame" width="100%" height="100%;" frameborder="0px;" id="loadFrame"
                        src="/loading.im" style="display: none;border: 0px;">
                </iframe>
            </td>
        </tr>
    </table>
</form>
</body>
</html>