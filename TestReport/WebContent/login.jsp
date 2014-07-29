<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,
			java.util.*,
			org.apache.http.conn.*,
			ron.tools.*" %>
<%
	String msg="";
	String userName="";
	String password="";
	

	if(request.getParameter("msg")!=null){
		msg=request.getParameter("msg");
		msg=new String(msg.getBytes("ISO-8859-1"),"utf-8");
	}
	
	if(request.getParameter("username")!=null&&request.getParameter("password")!=null){
		userName=request.getParameter("username").trim();
		password=request.getParameter("password").trim();
		
		if(userName.equals(""))
			msg+="!用户名不能为空<br/>";
		
		if(password.equals(""))
			msg+="!密码不能为空<br/>";
		
		if(!userName.equals("")&&!password.equals("")){
			try{
				msg=ToolsHttp.httpPtLogin(userName,password);
				if(!msg.equals("FALSE")){
					session.setAttribute("ptcookie",msg);
					session.setAttribute("username",userName);
					msg="！登录成功"+msg;
					response.sendRedirect("project.jsp"); 
				}
				else
					msg="！用户名或密码不正确";
				
			}catch(HttpHostConnectException e){
				msg="！无法连接redmine（redmine可能挂了或者目标地址不正确）";
			}
		}
		
	}
			
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<title>TestReport</title>
<link href="css/core.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="editor/themes/default/default.css" />
<script charset="utf-8" src="editor/kindeditor.js"></script>
<script charset="utf-8" src="editor/lang/zh_CN.js"></script>
<script>
</script>
</head>

<body>
<div style="margin:10px 20px">
	<h3>登录</h3>
	
	<h6 style="color:red"><%=msg %></h6>
	
	<form action="/TestReport/login.jsp" method="POST">
		<h4>用户名：</h4>
		<input type="text" name="username" value="<%=userName%>"/>
		<h4>密码：</h4>
		<input type="password" name="password" />
		
		<div style="margin:15px 0px">
			<input type="submit" value="登录">
		</div>
	</form>
	<br/>
	<h6 style="color:red">登录可能需要2次</h6>
	
</div>
</body>