<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ include file="common/taglibs.jsp" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>SpringMVC-Web 帐号管理</title>
	<%@ include file="common/meta.jsp" %>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	
	<script src="${ctx}/js/jquery-min.js" type="text/javascript"></script>
	<script src="${ctx}/js/table.js" type="text/javascript"></script>
</head>

<body>
<div class="container">
	<%@ include file="common/header.jsp" %>
	<div id="content" class="span-24 last prepend-top">
		<div id="filter">
			<form:form id="mainForm" modelAttribute="page" action="list" method="post">
				<form:hidden id="pageNo" path="pageNo" />
				<form:hidden id="orderBy" path="orderBy" />
				<form:hidden id="orderDir" path="orderDir" />
				你好, <shiro:principal/>.&nbsp;&nbsp;
				登录名: <input type="text" name="filter_EQS_loginName" value="${param['filter_EQS_loginName']}" size="9" />
				姓名或Email: <input type="text" name="filter_LIKES_name_OR_email" value="${param['filter_LIKES_name_OR_email']}" size="9" />
				<input type="button" value="搜索" onclick="search();" />
			</form:form>
		</div>
		<div>
			<table id="contentTable" class="contentTable">
				<tr>
					<th><a href="javascript:sort('loginName','asc')">登录名</a></th>
					<th><a href="javascript:sort('name','asc')">姓名</a></th>
					<th><a href="javascript:sort('email','asc')">电邮</a></th>
					<th>权限组</th>
					<th class="last">操作</th>
				</tr>

				<c:forEach items="${page.result}" var="user">
					<tr>
						<td>${user.loginName}&nbsp;</td>
						<td>${user.name}&nbsp;</td>
						<td>${user.email}&nbsp;</td>
						<td>${user.groupNames}&nbsp;</td>
						<td>&nbsp;
							<shiro:hasPermission name="user:view">
								<shiro:lacksPermission name="user:edit">
									<a href="show/${user.id}">查看</a>&nbsp;
								</shiro:lacksPermission>
        					</shiro:hasPermission>

							<shiro:hasPermission name="user:edit">
								<a href="input/${user.id}">修改</a>&nbsp;
								<a href="delete/${user.id}">删除</a>
							</shiro:hasPermission>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<div>
			第${page.pageNo}页, 共${page.totalPages}页
			<a href="javascript:jumpPage(1)">首页</a>
			<c:if test="${page.hasPrePage}"><a href="javascript:jumpPage(${page.prePage})">上一页</a></c:if>
			<c:if test="${page.hasNextPage}"><a href="javascript:jumpPage(${page.nextPage})">下一页</a></c:if>
			<a href="javascript:jumpPage(${page.totalPages})">末页</a>

			<shiro:hasPermission name="user:edit">
				<a href="add">增加新用户</a>
			</shiro:hasPermission>
		</div>

</div>
<%@ include file="common/footer.jsp" %>
</div>
</body>
</html>
