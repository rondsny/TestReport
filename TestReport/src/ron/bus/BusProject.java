package ron.bus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ron.bean.BeanProject;
import ron.bean.BeanReport;
import ron.tools.ToolsFileHelper;

import com.google.gson.Gson;

public class BusProject {

	public BeanProject[] getBeans(String path) throws Exception{
		String json =ToolsFileHelper.readFile(path);
		if(json.isEmpty())
			json="[]";
		BeanProject[] monkeyBeans =new Gson().fromJson(json, BeanProject[].class);
		return monkeyBeans;	
	}

	
	public String getJsonFromBean(BeanProject bean){
		return new Gson().toJson(bean);
	}
	
	
	// 增
	public String addFileToJson(String path,BeanProject bean) throws Exception{
		
		String json =ToolsFileHelper.readFile(path);
		
		BeanProject[] beans =new Gson().fromJson(json, BeanProject[].class);
		List<BeanProject> beanList=new ArrayList<BeanProject>();
		
		beanList.add(bean);
		
		if(beans!=null && beans.length>0){
			for(BeanProject abean : beans){
				beanList.add(abean);
			}
		}
		
		json = new Gson().toJson((BeanProject[])beanList.toArray(new BeanProject[beanList.size()]));
		ToolsFileHelper.writeFile(path, json);
		return json;
	}
	
	// 增加一个BeanReport
	public String addReportToJson(String path,String project,BeanReport rBean) throws Exception{

		List<BeanProject> beanList=new ArrayList<BeanProject>();
		BeanProject[] beans=this.getBeans(path);
		boolean isExit=false;
		
		List<BeanReport> reportList=new ArrayList<BeanReport>();
		reportList.add(rBean);
		
		for(BeanProject bean:beans){
			if(bean.getProject().equals(project)){	// 如果存在
				for(BeanReport r2Bean:bean.getReports()){
					reportList.add(r2Bean);
				}
				bean.setReports(reportList);
				isExit=true;
			}
			beanList.add(bean);
		}
		if(!isExit){	// 如果不存在
			beanList.add(new BeanProject("",project,project,reportList,false));
		}
		String json = new Gson().toJson((BeanProject[])beanList.toArray(new BeanProject[beanList.size()]));
		ToolsFileHelper.writeFile(path, json);
		return json;
	}

	// 根据md5获得Bean
	public BeanProject getBeanByAndMd5(String path, String md5) throws IOException{
		String json =ToolsFileHelper.readFile(path);
		BeanProject[] beans =new Gson().fromJson(json, BeanProject[].class);
		if(beans!=null && beans.length>0){
			for(BeanProject bean : beans){
				if(bean.getMd5().equals(md5)){
					return bean;
				}
					
			}
		}
		return null;
	}
	
	// 删
	public String deleteBeanInJson(String path,String md5) throws Exception{

		String json =ToolsFileHelper.readFile(path);
		BeanProject[] beans =new Gson().fromJson(json, BeanProject[].class);
		List<BeanProject> beanList=new ArrayList<BeanProject>();
		if(beans!=null && beans.length>0){
			for(BeanProject bean : beans){
				if(!bean.getMd5().equals(md5))
					beanList.add(bean);
			}
		}
		json = new Gson().toJson((BeanProject[])beanList.toArray(new BeanProject[beanList.size()]));
		ToolsFileHelper.writeFile(path, json);
		return json;
	}

}