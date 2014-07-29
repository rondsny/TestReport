<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,
			java.util.*,
			org.apache.http.conn.*,
			ron.tools.*,
			java.net.*,
			java.util.regex.*" %>
<%

	String targetHost=ToolsConfig.get("PT_URL");	
	String projectMsg="";
	String oneVersion="";

	Map<String,String> projectMap= new HashMap<String,String>();
	Map<String,Map<Integer,String>> versionMap= new HashMap<String,Map<Integer,String>>();	//key与projctMap的key相对应
	
	List<String> oneVersionList= new ArrayList<String>();
	
	if(session.getAttribute("username")!=null&&session.getAttribute("username")!=""){

		projectMsg=(new ToolsHttp()).httpGet(targetHost+"/projects", session.getAttribute("ptcookie").toString());
		String regEx = "<div class='root'><a href=\"/projects/(.*?)</a><div class=\"wiki description\">";
		
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(projectMsg);
		String tmp="";
		while(mat.find()){
			tmp=mat.group(1);
			projectMap.put(tmp.split("\"")[0], tmp.split(">")[1]);
		}
	}else{
		String tmpMsg=URLEncoder.encode("！请先登录","utf-8");
		response.sendRedirect("login.jsp?msg="+tmpMsg);
	}
	
	// 遍历获取各个项目的版本号
	Iterator iter = projectMap.entrySet().iterator(); 
	while (iter.hasNext()) { 
	    Map.Entry entry = (Map.Entry) iter.next(); 
	    Object key = entry.getKey(); 
	    Object val = entry.getValue();
	    
		String sourceHtml=(new ToolsHttp()).httpGet(targetHost+"/projects/"+key+"/issues/report/version", session.getAttribute("ptcookie").toString());
	
		String regEx = "<td class=\"name\"><a href=\".*fixed_version_id=(.*?)&amp;set_filter.*\">(.*?)</a></td>"; //使用正则取出

		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(sourceHtml);
		Map<Integer,String> tmpMap=new TreeMap<Integer,String>();
		while(mat.find()){
			tmpMap.put(Integer.parseInt(mat.group(1)),mat.group(2));
			//System.out.println(mat.group(1)+">>>>"+mat.group(2));
		}
		versionMap.put(key.toString(), tmpMap);
		
	}
			
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
<h3>各个项目版本</h3>

<div style="margin:10px 20px">
<%
Iterator iter_i = projectMap.entrySet().iterator(); 
while (iter_i.hasNext()) { 
    Map.Entry entry = (Map.Entry) iter_i.next(); 
    Object key = entry.getKey();
    Object val = entry.getValue();
%>
<h4><%=val %></h4>
<ul>
<%

	Map tmpMap=versionMap.get(key.toString());
	Iterator iter_j = tmpMap.entrySet().iterator(); 
	while (iter_j.hasNext()) { 
	    Map.Entry entry_j = (Map.Entry) iter_j.next(); 
	    Object key_j = entry_j.getKey();
	    Object val_j = entry_j.getValue();
%>
<li><a href="edit.jsp?project=<%=key %>&projectname=<%=val %>&versionid=<%=key_j %>&versionname=<%=val_j %>" ><%=val_j %></a></li>
<%
	}
%>
</ul>
<%
} %>
</div>
</body>
</html>