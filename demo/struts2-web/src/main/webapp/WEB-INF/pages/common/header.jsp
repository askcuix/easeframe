<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<div id="header" class="span-24 last">
	<div id="title">
		<h2>Struts2-Web示例</h2>
		<h3>--CRUD管理界面演示</h3>
	</div>
	<div id="menu">
		<ul>
			<li><a href="${ctx}/account/user.action">帐号列表</a></li>
			<li><a href="${ctx}/account/group.action">权限组列表</a></li>
			<li><a href="${ctx}/login!logout.action">退出登录</a></li>
		</ul>
	</div>
</div>