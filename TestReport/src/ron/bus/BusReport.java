package ron.bus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ron.bean.BeanReport;
import ron.tools.ToolsFileHelper;

import com.google.gson.Gson;

public class BusReport {

	public BeanReport[] getBeans(String path) throws Exception{
		String json =ToolsFileHelper.readFile(path);
		if(json.isEmpty())
			json="[]";
		BeanReport[] monkeyBeans =new Gson().fromJson(json, BeanReport[].class);
		return monkeyBeans;	
	}

	
	public String getJsonFromBean(BeanReport bean){
		return new Gson().toJson(bean);
	}
	
	// 增加一个BeanReport
	public String addReportToJson(String path,BeanReport rBean) throws Exception{

		List<BeanReport> beanList=new ArrayList<BeanReport>();
		beanList.add(rBean);
		
		BeanReport[] beans=this.getBeans(path);
		for(BeanReport bean:beans){
			beanList.add(bean);
		}
		String json = new Gson().toJson((BeanReport[])beanList.toArray(new BeanReport[beanList.size()]));
		ToolsFileHelper.writeFile(path, json);
		return json;
	}

	// 根据md5获得Bean
	public BeanReport getBeanByAndMd5(String path, String md5) throws IOException{
		String json =ToolsFileHelper.readFile(path);
		BeanReport[] beans =new Gson().fromJson(json, BeanReport[].class);
		if(beans!=null && beans.length>0){
			for(BeanReport bean : beans){
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
		BeanReport[] beans =new Gson().fromJson(json, BeanReport[].class);
		List<BeanReport> beanList=new ArrayList<BeanReport>();
		if(beans!=null && beans.length>0){
			for(BeanReport bean : beans){
				if(!bean.getMd5().equals(md5))
					beanList.add(bean);
			}
		}
		json = new Gson().toJson((BeanReport[])beanList.toArray(new BeanReport[beanList.size()]));
		ToolsFileHelper.writeFile(path, json);
		return json;
	}

}