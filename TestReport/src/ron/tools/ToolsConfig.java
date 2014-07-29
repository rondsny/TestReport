package ron.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ToolsConfig {

	private static final Properties prop = new Properties();

	public static String get(String KEY) throws Exception {
		String msg="";
		try {
			
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

			msg = prop.getProperty(KEY);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(msg==null)
			throw new Exception("Config Null");
		
		return msg;
	}


	public static String[] getArray(String KEY) throws Exception {
		String msg="";
		String[] result=new String[100];
		try {
			
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

			msg = prop.getProperty(KEY);
			result=msg.split("\\|");
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(msg==null)
			throw new Exception("Config Null");
		
		return null;
	}
}
