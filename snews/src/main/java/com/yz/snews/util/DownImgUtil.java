package com.yz.snews.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DownImgUtil {
	private static Logger logger = LoggerFactory.getLogger(DownImgUtil.class);
	
    /**
     * @param urlPath 图片路径
     * @throws Exception 
     */
    public static byte [] getImage(String urlPath) {
    	for (int i = 1; i <= 3; i++) {
    		try {
            	URL url = new URL(urlPath);//：获取的路径
                //:http协议连接对象
                HttpURLConnection conn = null;
        		conn = (HttpURLConnection) url.openConnection();
        		conn.setRequestMethod("GET");
        		conn.setReadTimeout(6 * 10000);
                if (conn.getResponseCode() <10000){
                    InputStream inputStream = conn.getInputStream();
                    byte[] data = readStream(inputStream);
                    return data;
                }
    		} catch (Exception e) {
    			// TODO: handle exception
    			logger.debug("Fetch img url failed: "+e);
    		}
		}
    	return null;
    }
    
    public static void saveImg(byte [] data,String path) throws Exception {
    	FileOutputStream outputStream = new FileOutputStream(path);
        outputStream.write(data);
        outputStream.close();
	}
    
    public static void saveImg(byte [] data,String savePath,int width,int height) throws Exception {
    	BufferedImage img = Scalr.resize(byte2img(data), Scalr.Method.SPEED , Scalr.Mode.FIT_TO_WIDTH,
    			width, height, Scalr.OP_ANTIALIAS);
    	FileOutputStream outputStream = new FileOutputStream(savePath);
        outputStream.write(img2byte(img));
        outputStream.close();
	}
    
    /**
     * 读取url中数据，并以字节的形式返回
     * @param inputStream
     * @return
     * @throws Exception
     */
    private static byte[] readStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len = inputStream.read(buffer)) !=-1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }
    
    private static BufferedImage byte2img(byte [] data) {
    	ByteArrayInputStream in = new ByteArrayInputStream(data);    //将b作为输入流；
    	BufferedImage img = null;
    	try {
    		img = ImageIO.read(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return img;
	}
    
    private static byte [] img2byte(BufferedImage img) {
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	try {
			ImageIO.write(img,"jpg",stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return stream.toByteArray();
	}
}
