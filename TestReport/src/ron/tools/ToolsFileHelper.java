package ron.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ToolsFileHelper {
	
	//  读取文件
	public static String readFile(String path) throws IOException{
		
		File file =new File(path);
		if(!file.exists())
			file.createNewFile();
		
		BufferedReader br=new BufferedReader(new FileReader(path));
		StringBuilder builder =new StringBuilder();
		String aline;
		while((aline=br.readLine())!=null){
			builder.append(aline);
		}
		br.close();
		return builder.toString();
	}
	
	// 写文件
	public static void writeFile(String path,String msg) throws Exception{

		File file =new File(path);
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		
		BufferedWriter bw= new BufferedWriter(new FileWriter(file,true));
		bw.write(msg);
		bw.close();
		
	}
}
