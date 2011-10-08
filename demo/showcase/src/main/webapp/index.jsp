<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<link href="${ctx}/static/showcase.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/blueprint/1.0.1/screen.css" type="text/css" rel="stylesheet" media="screen, projection" />
	<link href="${ctx}/static/blueprint/1.0.1/print.css" type="text/css" rel="stylesheet" media="print" />
	<!--[if lt IE 8]><link href="${ctx}/static/blueprint/1.0.1/ie.css" type="text/css" rel="stylesheet" media="screen, projection"><![endif]-->
	<title>Showcase 示例</title>
</head>
<body>
<div class="container">
	<%@ include	file="/WEB-INF/layouts/header.jsp"%>
	<div id="content">
		<%@ include file="/WEB-INF/layouts/left.jsp"%>
		<div class="span-18 last prepend-top">
			<p>EaseFrame showcase</p>
		</div>
	</div>
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>
</div>
</body>
</html>