<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>SpringMVC-Web 帐号管理</title>
	<%@ include file="common/meta.jsp" %>
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
</head>

<body>
<div class="container">
<%@ include file="common/header.jsp" %>
<div id="content">
	<div class="span-24 last prepend-top">
	<h3>查看用户</h3>
	
	<table class="span-10">
		<tr>
			<td>登录名:</td>
			<td>${user.loginName}</td>
		</tr>
		<tr>
			<td>用户名:</td>
			<td>${user.name}</td>
		</tr>
		<tr>
			<td>邮箱:</td>
			<td>${user.email}</td>
		</tr>
		<tr>
			<td>角色:</td>
			<td>${user.roleNames}</td>
		</tr>
		<tr>
			<td colspan="2">
				<input class="button" type="button" value="返回" onclick="location.replace('${ctx}/user/list')" />
			</td>
		</tr>
	</table>

	</div>
</div>
<%@ include file="common/footer.jsp" %>
</div>
</body>
</html>
