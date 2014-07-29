<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,
			java.util.*,
			ron.tools.*,
			ron.bean.*,
			ron.bus.*,
			java.text.*,
			java.util.*,
			java.util.regex.*" %>
			
<%
	response.setContentType("text/html;charset=utf-8");
	String targetHost=ToolsConfig.get("PT_URL");	
	String path=application.getRealPath("/");

	String baseUrl =ToolsRes.getBaseUrl(request);
	String project="other";
	String from="";
	String tos="";
	String[] tosArray=new String[100];
	String title="";
	String htmlContent="";
	String msg="";
	if(session.getAttribute("project")!=null)
		project=session.getAttribute("project").toString();
	
	String dataUrl=path+"data/project_"+project+".json";
	
	if(session.getAttribute("htmlContent")!=null){
		htmlContent=session.getAttribute("htmlContent").toString();
	}
	if(session.getAttribute("title")!=null){
		title=session.getAttribute("title").toString();
	}

	if(request.getParameter("txtfrom")!=null){
		from=request.getParameter("txtfrom").trim();
		from=new String(from.getBytes("ISO-8859-1"),"utf-8");
	}
	if(request.getParameter("txttos")!=null){
		tos=request.getParameter("txttos").trim();
		tos=new String(tos.getBytes("ISO-8859-1"),"utf-8");
		tosArray=tos.split("\\|");
	}

	DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	String filepath= "html/report_"+project+"_"+format.format(new Date())+"_t.html";
	
	if(ToolsMail.sendMail(tosArray,from,title, htmlContent)){
		BeanReport rBean=new BeanReport(ToolsCom.getRandomByLength(32),"version",from,tos,title,filepath,0,false);
		BusReport bus=new BusReport();
		bus.addReportToJson(dataUrl,rBean);
		
		filepath=path+filepath;
		ToolsFileHelper.writeFile(filepath, htmlContent);
		msg="邮件发送成功！！！<br/>";
	}
	else
		msg="邮件发送失败！！！<br/>";
	

%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<title>TestReport</title>
<link href="css/core.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div style="margin:10px 20px">
	<%=msg %>
	</div>
</body>