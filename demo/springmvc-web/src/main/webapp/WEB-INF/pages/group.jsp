<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ include file="common/taglibs.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>SpringMVC-Web 角色管理</title>
	<%@ include file="common/meta.jsp" %>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
</head>

<body>
<div class="container">
	<%@ include file="common/header.jsp" %>
	<div id="content" class="span-24 last prepend-top">
		<div>你好, <shiro:principal/>.</div>
		<div>
		<table id="contentTable" class="contentTable">
			<tr>
				<th>名称</th>
				<th>授权</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${groupList}" var="role">
				<tr>
					<td>${group.name}</td>
					<td>${group.permissionNames}</td>
					<td>&nbsp;
						<shiro:hasPermission name="group:view">
							<shiro:lacksPermission name="group:edit">
								<a href="show/${role.id}">查看</a>&nbsp;
						    </shiro:lacksPermission>
        				</shiro:hasPermission>

						<shiro:hasPermission name="group:edit">
							<a href="input/${role.id}" id="editLink-${name}">修改</a>&nbsp;
							<a href="delete/${role.id}" id="deleteLink-${name}">删除</a>
						</shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>

	<div>
		<shiro:hasPermission name="group:edit">
			<a href="add">增加新权限组</a>
		</shiro:hasPermission>
	</div>
</div>
<%@ include file="common/footer.jsp" %>
</div>
</body>
</html>
