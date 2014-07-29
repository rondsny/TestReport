package ron.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class ToolsHttp {
	
	private static String _ptCookie="";
	private static HttpClient client = HttpClientBuilder.create().build();
	
	
	// 获取token和设置cookie
	private static String GetTokenAndSetCookie() throws Exception{
		
		String token="";


		String targetHost=ToolsConfig.get("PT_URL");
		String url=targetHost+"/login";
		
		HttpGet get = new HttpGet(url);
		if(_ptCookie!=null&&_ptCookie!="")
			get.addHeader("Cookie", _ptCookie);

		HttpResponse response = client.execute(get);
		HttpEntity result = response.getEntity();
		String content = EntityUtils.toString(result);
		
		// 获取token
		String regEx = "authenticity_token\" type=\"hidden\" value=\"(.*?)\" /></div>"; //使用正则取出
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(content);
		boolean rs = mat.find(); 
		if(rs)
			token=mat.group(1);
		
		// 设置cookie
		Header[] headers = response.getAllHeaders();
        for (int i=0; i < headers.length; i++) {
            Header h = headers[i];
            if(h.getName().equals("Set-Cookie")){
            	_ptCookie=h.getValue();
            }
        }
		return token;
	}

	// 平台登录
	public static String httpPtLogin(String username,String password) throws Exception{
		
		String token=GetTokenAndSetCookie();

		String targetHost=ToolsConfig.get("PT_URL");
		String url=targetHost+"/login";
		HttpPost post = new HttpPost(url);
		post.addHeader("Cookie", _ptCookie);
		
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();
		formParams.add(new BasicNameValuePair("utf8", "✓"));
		formParams.add(new BasicNameValuePair("authenticity_token", token));
		formParams.add(new BasicNameValuePair("username", username));
		formParams.add(new BasicNameValuePair("password", password));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, "UTF-8");
		post.setEntity(entity);
		
		HttpResponse response = client.execute(post);	//提交POST请求
		
		HttpEntity result = response.getEntity();		//拿到返回的HttpResponse的"实体"
		String content = EntityUtils.toString(result);;//用httpcore.jar提供的工具类将"实体"转化为字符串打印到控制台
		
		Header[] headers = response.getAllHeaders();
		
        for (int i=0; i < headers.length; i++) {
            Header h = headers[i];
            if(h.getName().equals("Set-Cookie")){
            	_ptCookie=h.getValue();
            }
        }
        
        if(!content.contains("redirected"))
        	_ptCookie="FALSE";
		return _ptCookie;
	}

	// 通用Get请求
	public String httpGet(String url,String myCookie) throws Exception{
		HttpGet get = new HttpGet(url);
		if(myCookie!=null&&myCookie!="")
			get.addHeader("Cookie", myCookie);

		HttpResponse response = client.execute(get);
		HttpEntity result = response.getEntity();
		String content = EntityUtils.toString(result);
		return content;
	}
}
