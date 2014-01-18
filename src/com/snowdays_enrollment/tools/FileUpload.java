package com.snowdays_enrollment.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;

import com.snowdays_enrollment.controller.priv.ParticipantController;

public class FileUpload {
	
	static Logger log = Logger.getLogger(ParticipantController.class.getName());
	
	public static boolean uploadFile(String directory, FileItem item, String subfolder) throws IOException{
		log.trace("START");
		try{
			File f = new File(directory+File.separator);
			if(!f.exists())
				f.mkdir();
			File savedFile = new File(f.getAbsolutePath()+File.separator+subfolder+File.separator+item.getName());
//			FileOutputStream fos = new FileOutputStream(savedFile);
//			InputStream is = item.openStream();
//			int x = 0;
//			byte[] b = new byte[1024];
//			while((x=is.read(b))!= -1){
//				fos.write(b, 0, x);
//			}
//			fos.flush();
//			fos.close();
			item.write(savedFile);
			log.trace("END");
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		log.trace("END");
		return false;
	}

}
