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
	
	String targetHost=ToolsConfig.get("PT_URL");	
	String path=application.getRealPath("/");
	String htmlContent=ToolsFileHelper.readFile(path+"data/module.html");
	String baseUrl =ToolsRes.getBaseUrl(request);
	
	String msg="";
	String title="";
	String project="other";
	String versionId="";

	String cb_result="";
	String editor_risk="";
	String editor_point="";
	String editor_resource="";

	String image_bugPicUrl="";
	String image_catPicUrl="";

	String from=ToolsConfig.get("MAIL_FROM");
	String tos=ToolsConfig.get("MAIL_TOS");
	List<BeanVersion> beanVersionList2= new ArrayList<BeanVersion>();
	
	response.setContentType("text/html;charset=utf-8");
	
	//所有版本数据
	List<String> allList= new ArrayList<String>();
	// version
	List<String> oneVersionList= new ArrayList<String>();

	if(session.getAttribute("project")!=null&&session.getAttribute("project")!=""){
		project=session.getAttribute("project").toString();
		versionId=session.getAttribute("versionId").toString();
		image_catPicUrl=session.getAttribute("catPicUrl").toString();
		
		int tmp_sum=0;
		if(session.getAttribute("beanVersionList")!=null){
			List<BeanVersion> beanVersionList= (List<BeanVersion>)session.getAttribute("beanVersionList");
			for(BeanVersion bean :beanVersionList){
				if(request.getParameter("cbStatis_"+bean.getVersion())!=null){
					tmp_sum+=bean.getOpen();
					bean.setSumCount(tmp_sum);
					beanVersionList2.add(bean);
				}
			}
		}
		
		image_bugPicUrl=new BusPic().getUrlByVersionList(session, beanVersionList2,path);

		String png16_bugPicUrl =ToolsImg.getBase64Png(path+image_bugPicUrl);
		String png16_catPicUrl =ToolsImg.getBase64Png(path+image_catPicUrl);

		image_bugPicUrl="<image src=\"data:image/png;base64,"+png16_bugPicUrl+"\" />";
		if(request.getParameter("cbCat")!=null){
			image_catPicUrl="<image src=\"data:image/png;base64,"+png16_catPicUrl+"\" />";
		}else{
			image_catPicUrl="无数据！";
		}
	}
	String dataUrl=path+"data/project_"+project+".json";
	
	if(session.getAttribute("username")!=null&&session.getAttribute("username")!=""){
		
		// 获取版本数据
		msg=(new ToolsHttp()).httpGet(targetHost+"/projects/"+project+"/issues/report/version", session.getAttribute("ptcookie").toString());
		String regEx = "<td.*><a href=\".*\">(.*?)</a></td>|<td.*>(.*?)</td>"; //使用正则取出
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(msg);
		while(mat.find()){
			allList.add(mat.group(1));
		}
	}

	if(request.getParameter("txt_title")!=null){
		title=request.getParameter("txt_title").trim();
		title=new String(title.getBytes("ISO-8859-1"),"utf-8");
	}

	if(request.getParameter("editor_risk")!=null){
		editor_risk=request.getParameter("editor_risk").trim();
		editor_point=request.getParameter("editor_point").trim();
		editor_resource=request.getParameter("editor_resource").trim();
		
		editor_risk=new String(editor_risk.getBytes("ISO-8859-1"),"utf-8");
		editor_point=new String(editor_point.getBytes("ISO-8859-1"),"utf-8");
		editor_resource=new String(editor_resource.getBytes("ISO-8859-1"),"utf-8");
	}
	if(request.getParameter("cb_result")!=null){
		cb_result=request.getParameter("cb_result").trim();
	}
	if(cb_result.equals("true")){
		cb_result="<font style=\"color:green;font-weight:900\">通过</font>";
		title+="_通过";	
	}else{
		cb_result="<font style=\"color:red;font-weight:900\">不通过</font>";
		title+="_不通过";
	}

	//主要问题
	String main_bugs="";
	if(session.getAttribute("beanIssueList")!=null){
		List<BeanIssue> beanIssueList= (List<BeanIssue>)session.getAttribute("beanIssueList");
		for(BeanIssue bean :beanIssueList){
			if(request.getParameter("cbMain_"+bean.getId())!=null){
				String tmp="<a target=\"_blank\" href=\""+targetHost+"/issues/"+bean.getId()+"\">"+bean.getTitle();
				main_bugs+=tmp+"<br/>";
			}
		}
	}

	//表格数据
	String data_bugs="";
	int i=0;
	String preTd="<td style=\"text-align:center;width:100px\">";
	for(BeanVersion tmp : beanVersionList2){
		data_bugs += "<tr>";
		data_bugs += preTd + (++i) +"</td>";
		data_bugs += preTd + tmp.getVersion()+"</td>";
		data_bugs += preTd + tmp.getCurrentCount()+"</td>";
		data_bugs += preTd + tmp.getOpen()+"</td>";
		data_bugs += preTd + tmp.getSumCount()+"</td>";
		data_bugs+="</tr>";
	}
	
	DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	String filepath= "html/report_"+format.format(new Date())+"_t.html";

	// 获取历史版本
	BusReport bus=new BusReport();
	BeanReport[] beans=bus.getBeans(dataUrl);
	String other_tests="<ul>";
	int jj=1;
	for(BeanReport tmp_bean:beans){
		if(jj==1){
			from=tmp_bean.getFrom();
			tos=tmp_bean.getTos();
		}
		if(jj++>5)
			break;
		
		other_tests+="<li><a href='"+baseUrl+tmp_bean.getUrl()+"'>"+tmp_bean.getTitle()+"</a></li>";
	}
	other_tests+="</ul>";
	
	htmlContent=htmlContent.replaceFirst("##tittle##", title);
	htmlContent=htmlContent.replaceFirst("##cb_result##", cb_result);
	htmlContent=htmlContent.replaceFirst("##editor_risk##", editor_risk);
	htmlContent=htmlContent.replaceFirst("##editor_point##", editor_point);
	htmlContent=htmlContent.replaceFirst("##main_bugs##", main_bugs);
	htmlContent=htmlContent.replaceFirst("##data_bugs##", data_bugs);
	htmlContent=htmlContent.replaceFirst("##image_bugPicUrl##", image_bugPicUrl);
	htmlContent=htmlContent.replaceFirst("##image_catPicUrl##", image_catPicUrl);
	htmlContent=htmlContent.replaceFirst("##editor_resource##", editor_resource);
	htmlContent=htmlContent.replaceFirst("##other_tests##", other_tests);
	htmlContent=htmlContent.replaceFirst("##right_info##", "<h6>© <a href='"+baseUrl+"about.jsp'>TestReport</a> 2014.06 - now <a href='"+baseUrl+"about.jsp'>"+ToolsConfig.get("VERSION")+"</a></h6><h6>Powered by Veiyn</h6>");

	
	session.setAttribute("title", title);
	session.setAttribute("htmlContent", htmlContent);

%>
<div style="margin:20px;font-family:Microsoft JhengHei">
	<form action="/TestReport/send.jsp" method="post">
		<h1>预览</h1>
		<h6 style="color:red">请输入发件人和收件人后，点击发送</h6>
		<br/>
		<h6>发件人：</h6>
		<input type="text" name="txtfrom" value="<%=from %>" style="width:300px;height:50px"/>
		<h6>收件人：</h6>
		<textarea name="txttos" style="width:300px;height:100px;font-family:Microsoft JhengHei"><%=tos %></textarea><br/>
		使用[|]分割收件人<br/>
		<input type="submit" value="发送" /><br/>
	</form>
</div>

<hr/>
<%=htmlContent %>
