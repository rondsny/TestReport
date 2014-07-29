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
	<h3>主页</h3>
	
	<br/>
	<h6><a href="index.jsp">主页</a></h6>
	<h6><a href="login.jsp">登录</a></h6>
	<h6><a href="project.jsp">项目</a></h6>
	<h6><a href="about.jsp">关于</a></h6>
	
	<br/>
	<h6><a href="ttt.jsp">调试</a></h6>
	
	<br/>
	<h6>© <a href="about.jsp">TestReport</a> 2014.06 - 2014.08 <a href="about.jsp"><%=version %></a></h6>
	<h6>Powered by Veiyn</h6>
	
</div>
</body>