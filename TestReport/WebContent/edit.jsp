<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.*,
			java.util.*,
			ron.tools.*,
			ron.bean.*,
			ron.bus.*,
			java.net.*,
			java.util.regex.*" %>
<%
	String targetHost=ToolsConfig.get("PT_URL");
	String path=application.getRealPath("/");

	String msg="";
	String oneVersion="";
	String catPicUrl="";	// bug 分类图 饼状图
	
	String userName="";
	
	String project="";		// 项目名称（主键）
	String versionId="";	// 版本ID
	String title="";
	
	//所有版本数据
	List<String> allList= new ArrayList<String>();
	List<BeanVersion> beanVersionList= new ArrayList<BeanVersion>();
	List<BeanVersion> beanVersionList2= new ArrayList<BeanVersion>();
	
	// version
	List<BeanIssue> beanIssueList= new ArrayList<BeanIssue>();
	
	// bug分类
	Map<String,Integer> catMap = new HashMap<String,Integer>();
	
	if(request.getParameter("project")!=null&&request.getParameter("versionid")!=null){
		project=request.getParameter("project").trim();
		versionId=request.getParameter("versionid").trim();

		title+="[测试报告]";
		title+=new String(request.getParameter("projectname").getBytes("ISO-8859-1"),"utf-8");
		title+="_";
		title+=new String(request.getParameter("versionname").getBytes("ISO-8859-1"),"utf-8");
	}else{
		response.sendRedirect("login.jsp?msg="+URLEncoder.encode("！参数错误","utf-8"));
	}
	
	if(session.getAttribute("username")!=null&&session.getAttribute("username")!=""){
		userName=session.getAttribute("username").toString();
		String cookie =session.getAttribute("ptcookie").toString();
		// 获取版本数据
		msg=(new ToolsHttp()).httpGet(targetHost+"/projects/"+project+"/issues/report/version", session.getAttribute("ptcookie").toString());
	
		String regEx = "<td.*><a href=\".*\">(.*?)</a></td>|<td.*>(.*?)</td>"; //使用正则取出
		
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(msg);
		while(mat.find()){
			allList.add(mat.group(1));
		}
		
		int i=1;
		BeanVersion beanVersion=new BeanVersion();
		for(String tmp : allList){
			if(tmp==null||tmp.isEmpty())
				tmp="0";
			
			if(i%11==1)
				beanVersion.setVersion(tmp);

			if(i%11==9)
				beanVersion.setOpen(Integer.parseInt(tmp));
			
			if(i%11==0){
				
				if(!beanVersion.getVersion().contains("x")){
					beanVersion.setSumCount(0);	// 不保存了，使用当前打开的来计算就好了
					beanVersion.setCurrentCount(Integer.parseInt(tmp));
					beanVersionList.add(beanVersion);
				}
				
				beanVersion=null;
				beanVersion=new BeanVersion();
			}
			i++;
		}
		int jj=beanVersionList.size();
		int tmpSum=0;
		for(BeanVersion vBean:beanVersionList){
			if(jj<15){
				tmpSum+=vBean.getOpen();
				vBean.setSumCount(tmpSum);
				beanVersionList2.add(vBean);
			}
			jj--;
		}
		
		//获取某一版本数据
		oneVersion=(new ToolsHttp()).httpGet(targetHost+"/versions/"+versionId,cookie );
		regEx = "<td class=\"subject\"><a href=\"/issues/(.*?)\" class=\".*?\">(.*?)</td>"; //使用正则取出
		
		pat = Pattern.compile(regEx);
		mat = pat.matcher(oneVersion);
		while(mat.find()){
			BeanIssue bean=(new BusIssue()).getBeanById(cookie, mat.group(1));
			bean.setTitle(mat.group(2));
			beanIssueList.add(bean);
			
			if(catMap.get(bean.getCategory())==null)
				catMap.put(bean.getCategory(),1);
			else
				catMap.put(bean.getCategory(),catMap.get(bean.getCategory())+1);
			
		}
		catPicUrl=new BusPic().getUrlByCatMap(session, catMap, path);

		session.setAttribute("project",project);				//项目
		session.setAttribute("versionId",versionId);			//当前版本
		session.setAttribute("beanIssueList",beanIssueList);	//主要问题的数据
		session.setAttribute("beanVersionList",beanVersionList);			//所有版本统计
		session.setAttribute("catPicUrl",catPicUrl);			//bug分类图
		
	}else{
		response.sendRedirect("login.jsp?msg="+URLEncoder.encode("！请先登录","utf-8"));
	}
			
%>


<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8" />
<title>TestReport</title>
<link href="css/core.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="editor/themes/default/default.css" />

<script charset="utf-8" src="js/jquery-2.0.0.min.js"></script>
<script charset="utf-8" src="js/Chart.js"></script>
<script charset="utf-8" src="js/edit.js"></script>

<script charset="utf-8" src="editor/kindeditor.js"></script>
<script charset="utf-8" src="editor/lang/zh_CN.js"></script>
<script>
        var editor;
        KindEditor.ready(function(K) {
                editor_risk = K.create('#editor_risk', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false,
					items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|','link']
                });
                editor_point = K.create('#editor_point', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false,
					items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|','link']
                });
                editor_resource =K.create('#editor_resource', {
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : false,
					items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|','link']
                });
        });
        
        // 同步数据，提交请求
        function submit(isSend){
        	editor_risk.sync();
        	editor_point.sync();
        	editor_resource.sync();
        	
        	if(isSend){
	        	//document.getElementById("btn_send").submit();
	        	alert(document.getElementById("editor_resource").html());
	        	return true;
        	}else{
	        	alert("Hello");
	        	return false;
        	}
        }

        function load_data()
        {
        	draw();
        }
        
        var isSelect=true;
        function chkall(this_id,div_id)
        {
        	isSelect=!isSelect;
        	var input = document.getElementById(div_id).getElementsByTagName("input");
            for (var i=0;i<input.length ;i++ )
            {
                if(input[i].type=="checkbox")
                    input[i].checked = this_id.checked;
            }
            if(div_id=="statis"){
            	load_data();
            }
        }
        
        $(document).ready(function(){
        	load_data();
        });
</script>

<style type="text/css">
.point_content{margin:5px 20px;padding:5px;background-color:#F9F9F9}
.point_content_edit{margin:5px 20px;padding:5px;}
</style>

</head>

<body>
<div style="margin:10px 20px">
	<h3>测试报告</h3>

	<form action="/TestReport/view.jsp" method="post" target="_blank">
	
		<h4>报告标题：<input type="text" name="txt_title" value="<%=title %>" style="width:300px;height:20px"/></h4>
		
		<h4>测试结果：<input type="checkbox" name="cb_result" value="true" />通过</h4>
		
		<h4>评价与风险：</h4>
		<div class="point_content_edit">
			<textarea id="editor_risk" name="editor_risk" style="width:700px;height:200px;visibility:hidden;"></textarea>
		</div>
	
		<h4>测试点说明：</h4>
		<div class="point_content_edit">
			<textarea id="editor_point" name="editor_point" style="width:700px;height:200px;visibility:hidden;"></textarea>
		</div>
		
		<h4>主要问题：</h4>	
		<div class="point_content">
			<div id="main_issue">
				<input type="checkbox" checked="checked" name="cbAllMain" onclick="chkall(this,'main_issue')" />选中显示（全选）<br/>
				<%
					for(BeanIssue bean :beanIssueList){
						String tmp="<a target=\"_blank\" href=\""+targetHost+"/issues/"+bean.getId()+"\">"+bean.getTitle();
				%>
				<input type="checkbox" checked="checked" name="cbMain_<%=bean.getId() %>" /> <%=tmp %><br/>
				<%
					}
				%>
			</div>
		</div>
		
		<h4>当前版本缺陷分类：</h4>
		<div class="point_content_edit" style="margin-left:50px">
			<input type="checkbox" name="cbCat" />是否显示<br/>
			<image src="<%=catPicUrl %>" />
		</div>
		
		<h4>缺陷统计：</h4>
		<div class="point_content">
			<div>
				<div style="float:left">
					<div>
						<h6>（选择需要显示的bug统计，默认只会显示最近的15个版本）</h6>
						<input type="checkbox" name="cbStatis_all" checked="checked" onclick="chkall(this,'statis')" />
						选中显示（全选）
					</div>
					<table border="1" style="border-color:#CCC;border-width:1px;border-style:none;">
					<thead><tr>
					<th style="text-align:left;width:100px">版本</th>
					<th style="text-align:center;width:100px"><strong>当前发现</strong></th>
					<th style="text-align:center;width:100px"><strong>当前未关闭</strong></th>
					<th style="text-align:center;width:100px"><strong>未关闭累计</strong></th>
					</tr></thead>
					<tbody id="statis">
						<%
							int i=beanVersionList2.size();
							for(BeanVersion tmpBean : beanVersionList2){
								String check="";
								if(i<8){
									check="checked=\"checked\"";
								}
								i--;
						%>
						<tr>
							<td style="text-align:left">
								<input type="checkbox" <%=check %> onclick="load_data()" name="cbStatis_<%=tmpBean.getVersion() %>" />
								<%=tmpBean.getVersion() %>
							</td>
							<td style="text-align:center"><%=tmpBean.getCurrentCount() %></td>
							<td style="text-align:center"><%=tmpBean.getOpen() %></td>
							<td style="text-align:center"><%=tmpBean.getSumCount() %></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
				<div style="float:left">
					<ul>
					<li style="color:black">黑色——当前发现</li>
					<li style="color:blue">蓝色——当前未关闭</li>
					<li style="color:red">红色——未关闭累计</li>
					</ul>
					<canvas width="800" height="400" id="canvas" ></canvas>
				</div>
			</div>
		</div>
		
		<br style="clear:both"/>
	
		<h4>测试资源：</h4>
		<div class="point_content_edit">
			<textarea id="editor_resource" name="editor_resource" style="width:700px;height:200px;visibility:hidden;">
				<table border="1" width="400" height="76">
					<tr>
						<td width="100">测试人员：</td><td><%=userName %></td>
					</tr>
					<tr>
						<td>部署时间：</td><td></td>
					</tr>
					<tr>
						<td>测试时间：</td><td></td>
					</tr>
					<tr>
						<td>测试环境：</td><td>PC版、BluckStack模拟器</td>
					</tr>
				</table>
			</textarea>
		</div>
		
		<h4>
			<input id="btn_send" type="submit" onclick="submit(false)" value="预览" />
			<h6>预览后可发送</h6>
		</h4>
	</form>
</div>
</body>