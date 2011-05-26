<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="common/taglibs.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>SpringMVC-Web 角色管理</title>
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
			$("#name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
<div class="container">
<%@ include file="common/header.jsp" %>
<div id="content">
	<div class="span-24 last">
	<h3><c:choose><c:when test="${group.id == null}">创建</c:when><c:otherwise>修改</c:otherwise></c:choose>角色</h3>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/group/save" method="post">
		<form:hidden path="id" />
		<table class="noborder">
			<tr>
				<td>角色名:</td>
				<td><form:input id="name" path="name" size="40" cssClass="required"/></td>
			</tr>
			<tr>
				<td>授权:</td>
				<td>
					<c:if test="${allAuthorityList != null }">
						<c:set var="itemCount" value="0" />
						<c:set var="columnCount" value="3" />
						<table class="checkboxTable" style="margin-bottom:0em;">
							<c:forEach items="${allAuthorityList}" var="authority">
								<c:if test="${(itemCount%columnCount) == 0}">
									<tr>
								</c:if>
								<td class="checkboxTd">
									<input type="checkbox" name="checkedPermissionIds" value="${authority.id}" id="checkedPermissionIds-${authority.id}" 
									<c:forEach items="${role.authIds}" var="checkedAid">
									<c:if test="${authority.id == checkedAid}">
										checked="checked"
									</c:if>
									</c:forEach>
									/>
									<label for="checkedPermissionIds-${authority.id}" class="checkboxLabel">${authority.name}</label>
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
					<shiro:hasPermission name="group:edit">
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
