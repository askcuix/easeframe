<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="common/taglibs.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>SpringMVC-Web 帐号管理</title>
	<%@ include file="common/meta.jsp" %>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/js/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	
	<script src="${ctx}/js/jquery-min.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/jquery.validate.js" type="text/javascript"></script>
	<script src="${ctx}/js/validate/messages_cn.js" type="text/javascript"></script>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
		});
	</script>
</head>

<body>
<div class="container">
<%@ include file="common/header.jsp" %>
<div id="content">
	<div class="span-24 last prepend-top">
	<h3><c:choose><c:when test="${user.id == null}">创建</c:when><c:otherwise>修改</c:otherwise></c:choose>用户</h3>
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/user/save" method="post">
		<form:hidden path="id" />
		<table>
			<tr>
				<td><label for="loginName">登录名:</label></td>
				<td><form:input id="loginName" path="loginName" size="40" /></td>
			</tr>
			<tr>
				<td><label for="name">用户名:</label></td>
				<td><form:input id="name" path="name" size="40" /></td>
			</tr>
			<tr>
				<td><label for="password">密码:</label></td>
				<td><form:password id="password" path="password" showPassword="true" size="40" /></td>
			</tr>
			<tr>
				<td><label for="passwordConfirm">确认密码:</label></td>
				<td><input type="password" id="passwordConfirm" name="passwordConfirm" size="40" value="${user.password}"/>
				</td>
			</tr>
			<tr>
				<td><label for="email">邮箱:</label></td>
				<td><form:input id="email" path="email" size="40" /></td>
			</tr>
			<tr>
				<td><label>权限组:</label></td>
				<td>
					<c:if test="${allRoleList != null }">
						<c:set var="itemCount" value="0" />
						<c:set var="columnCount" value="3" />
						<table style="margin-bottom:0em;" class="span-6">
							<c:forEach items="${allRoleList}" var="role">
								<c:if test="${(itemCount%columnCount) == 0}">
									<tr>
								</c:if>
								<td>
									<input type="checkbox" name="checkedGroupIds" value="${role.id}" id="checkedGroupIds-${role.id}" 
										<c:forEach items="${user.roleIds}" var="checkedRid">
											<c:if test="${role.id == checkedRid}">
												checked="checked"
											</c:if>
										</c:forEach>
									/>
									<label for="checkedGroupIds-${role.id}" class="checkboxLabel">${role.name}</label>
								</td>
								<c:set var="itemCount" value="${itemCount + 1}" />
								<c:if test="${(itemCount%columnCount) == 0}">
									</tr>
								</c:if>
							</c:forEach>
							<c:if test="${(itemCount%columnCount) != 0}">
									</tr>
							</c:if>
						</table>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<shiro:hasPermission name="user:edit">
						<input class="button" type="submit" value="提交"/>&nbsp;
					</shiro:hasPermission>
					<input class="button" type="button" value="返回" onclick="history.back()"/>
				</td>
			</tr>
		</table>
	</form:form>
	</div>
</div>
<%@ include file="common/footer.jsp" %>
</div>
</body>
</html>
