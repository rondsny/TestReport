package ron.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class ToolsImg {

	/*
	 * Convert PNG to Base64
	 * path the PNG path
	 * */
    public static String getBase64Png(String path) throws Exception {
	    String msg="";
	    try{ 
	        BufferedImage image = ImageIO.read(new File(path));
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(image, "png", baos);
	        msg = Base64.encode(baos.toByteArray());

	    }catch(IOException e){
	    	e.printStackTrace();
        } 
	    return msg;
    }
}