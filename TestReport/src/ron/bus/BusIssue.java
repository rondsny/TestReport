package ron.bus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ron.bean.BeanIssue;
import ron.tools.ToolsConfig;
import ron.tools.ToolsHttp;

public class BusIssue {

	// 从远程获取问题的详细信息
	public BeanIssue getBeanById(String cookie,String id) throws Exception{
		BeanIssue bean =new BeanIssue();
		String html=(new ToolsHttp()).httpGet(ToolsConfig.get("PT_URL")+"/issues/"+id, cookie);
		String regEx = "<tr><th class=\"status\">状态:</th><td class=\"status\">(.*?)</td>.*?<th class=\"priority\">优先级:</th><td class=\"priority\">(.*?)</td>.*?<th class=\"category\">类别:</th><td class=\"category\">(.*?)</td>.*?";
		
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(html);
		while(mat.find()){
			bean.setId(id);
			bean.setStatus(mat.group(1));
			bean.setLevel(getLevelByString(mat.group(2)));
			bean.setCategory(mat.group(3));
		}
		return bean;
	}
	

	// 从远程获取问题的详细信息
	public BeanIssue getBeanByBean(String cookie,BeanIssue bean) throws Exception{
		String html=(new ToolsHttp()).httpGet(ToolsConfig.get("PT_URL")+"/issues/"+bean.getId(), cookie);
		String regEx = "<tr><th class=\"status\">状态:</th><td class=\"status\">(.*?)</td>.*?<th class=\"priority\">优先级:</th><td class=\"priority\">(.*?)</td>.*?<th class=\"category\">类别:</th><td class=\"category\">(.*?)</td>.*?";
		
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(html);
		while(mat.find()){
			bean.setStatus(mat.group(1));
			bean.setLevel(getLevelByString(mat.group(2)));
			bean.setCategory(mat.group(3));
		}
		return bean;
	}
	
	
	/*
	 * 	<option value="1">低</option>
	 *	<option value="2">普通</option>
	 *	<option value="3">高</option>
	 *	<option value="4">紧急</option>
	 *	<option value="5">立刻</option>
	 * */
	private int getLevelByString(String msg){
		int level=0;
		if(msg.equals("低")){
			level=1;
		}else if(msg.equals("普通")){
			level=2;
		}else if(msg.equals("高")){
			level=3;
		}else if(msg.equals("紧急")){
			level=4;
		}else if(msg.equals("立刻")){
			level=5;
		}
		return level;
	}

}
