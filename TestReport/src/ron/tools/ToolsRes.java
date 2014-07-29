package ron.tools;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;

public class ToolsRes {
	
	// 返回 http://127.0.0.1:8080/TestReport/
	public static String getBaseUrl(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	}
}
