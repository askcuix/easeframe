<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>SpringMVC-Web 上传文件</title>
	<%@ include file="common/meta.jsp" %>
	<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/css/blueprint/screen.css" type="text/css" rel="stylesheet" media="screen, projection"/>
	<link href="${ctx}/css/blueprint/print.css" type="text/css" rel="stylesheet" media="print"/>
	<!--[if lt IE 8]><link href="${ctx}/css/blueprint/blueprint/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
</head>

<body>
<div class="container">
<%@ include file="common/header.jsp" %>
<div id="content">
	<div class="span-24 last prepend-top">
	<h3>上传文件</h3>
	<form id="fileuploadForm" action="fileupload" method="post" enctype="multipart/form-data" >
		<c:if test="${not empty message}">
			<div id="message">${message}</div>	  		
		</c:if>
		<table class="noborder">
			<tr>
				<td>文件:</td>
				<td><input type="file" name="file" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input class="button" type="submit" value="上传"/>
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
<%@ include file="common/footer.jsp" %>
</div>
</body>
</html>
