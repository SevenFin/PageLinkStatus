<%--
  User: SevenFin
  Date: 2015/9/11
  Time: 10:45
  --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>title</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>

  <h2>登录</h2>
  <h3 style="color: red;">${message}</h3>
  <form action="/user/login.im" method="post">
    <table cellpadding="0" cellspacing="0">
      <tr>
        <td>用户名 ： </td>
        <td><input name="username" style="width: 120px;"/></td>
      </tr>
      <tr>
        <td>密  码 ： </td>
        <td><input name="password" type="password" style="width: 120px;"/></td>
      </tr>
      <tr>
        <td colspan="2"><input type="submit" value="登录"/></td>
      </tr>
    </table>
  </form>

</body>
</html>
