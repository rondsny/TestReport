package ron.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import ron.tools.ToolsConfig;
import ron.tools.ToolsHttp;

public class Test {
	
	private static String _ptCookie="";

	public static void main(String[] args) throws Exception {
		
		//System.out.println(ToolsConfig.processLogin("PT_URL"));

//		System.out.println(">>>>");
//		System.out.println("=>>"+new ToolsHttp().httpPtLogin("weiyg","uc&weiyg"));
//		System.out.println("<<<<");
		
		
		//httpGet("http://db.freeala.com:9401/login");
		//httpPost("","","");
		

		Map<String,Integer> catMap = new HashMap<String,Integer>();
		System.out.println(catMap.get("abc"));
		System.out.println(catMap.get("abcd"));

	}

	
	public static String httpGet(String url) throws Exception{
		String msg="";
		String token="";
		
		HttpClient client = new DefaultHttpClient(); 	//构建一个Client
		
		HttpGet get = new HttpGet(url);//构建一个GET请求
		
		if(_ptCookie!=null&&_ptCookie!="")
			get.addHeader("Cookie", _ptCookie);

		HttpResponse response = client.execute(get);	//提交POST请求
		
		HttpEntity result = response.getEntity();		//拿到返回的HttpResponse的"实体"
		String content = EntityUtils.toString(result);	//用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
		if(_ptCookie==""){
			String regEx = "authenticity_token\" type=\"hidden\" value=\"(.*?)\" /></div>"; //使用正则取出
			Pattern pat = Pattern.compile(regEx);
			Matcher mat = pat.matcher(content);
			boolean rs = mat.find(); 
	
			if(rs){
				token=mat.group(1);
				System.out.println(token);
			}
			
			Header[] headers = response.getAllHeaders();
			
	        for (int i=0; i < headers.length; i++) {
	            Header h = headers[i];
	            if(h.getName().equals("Set-Cookie")){
	            	_ptCookie=h.getValue();
	            	System.out.println( h.getName()+" : "+_ptCookie);
	            }
	        }
			
	        httpPtLogin("weiyg","uc&weiyg",token);
		}else{
			System.out.println(content);
		}
		return msg;
	}

	// 平台登录
	public static String httpPtLogin(String username,String password,String token) throws Exception{
		String msg="";

		HttpClient client = new DefaultHttpClient(); //构建一个Client
		
		HttpPost post = new HttpPost("http://db.freeala.com:9401/login");//构建一个POST请求
		
		post.addHeader("Cookie", _ptCookie);
		
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("utf8", "✓"));
		formParams.add(new BasicNameValuePair("authenticity_token", token));
		formParams.add(new BasicNameValuePair("username", username));
		formParams.add(new BasicNameValuePair("password", password));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");//将表单参数转化为“实体”
		post.setEntity(entity);									//将“实体“设置到POST请求里
		
		HttpResponse response = client.execute(post);//提交POST请求
		
		HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
		String content = EntityUtils.toString(result);;//用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
		
		Header[] headers = response.getAllHeaders();
		
        for (int i=0; i < headers.length; i++) {
            Header h = headers[i];
            if(h.getName().equals("Set-Cookie")){
            	_ptCookie=h.getValue();
            	System.out.println( h.getName()+" : "+_ptCookie);
            }
        }
		
		System.out.println("asd:::"+content);

		httpGet("http://db.freeala.com:9401/projects/fairy/issues/report/version");
		
		return msg;
	}
	
	public static String httpPost(String url,String cookie,String params,String token) throws Exception{
		String msg="";

		HttpClient client = new DefaultHttpClient(); //构建一个Client
		
		HttpPost post = new HttpPost("http://uctc.ucweb.local/login");//构建一个POST请求
		//构建表单参数
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("username", "weiyg"));
		formParams.add(new BasicNameValuePair("password", "ron&weiyg"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");//将表单参数转化为“实体”
		post.setEntity(entity);									//将“实体“设置到POST请求里
		
		HttpResponse response = client.execute(post);//提交POST请求
		
		HttpEntity result = response.getEntity();//拿到返回的HttpResponse的"实体"
		String content = EntityUtils.toString(result);;//用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
		System.out.println(content);
		
		return msg;
	}
	
}
