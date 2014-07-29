<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,
			java.util.*,
			org.apache.http.conn.*,
			ron.tools.*" %>
<%
	String version="";
	version=ToolsConfig.get("VERSION");
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<title>TestReport</title>
<link href="css/core.css" rel="stylesheet" type="text/css">
<script>
</script>
</head>

<body>
<div style="margin:10px 20px">
	<h3>关于</h3>
	
	<br/>
	<div>
		<p>
			TestReport 用于爬取redmine系统的bug缺陷并进行测试报告生成！<br/>
			有任何疑问或建议，请联系管理员。<br/>
		</p>
	</div>
	<br/>
	<h6>© <a href="about.jsp">TestTools</a> 2014.06 - 2014.08 <a href="about.jsp"><%=version %></a></h6>
	<h6>Powered by Veiyn</h6>
	
</div>
</body>