package com.snowdays_enrollment.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;

import com.snowdays_enrollment.controller.priv.ParticipantController;

public class FileUpload {
	
	static Logger log = Logger.getLogger(ParticipantController.class.getName());
	
	public void saveFile(HttpServletRequest request, String path){
	log.trace("START");
	List<FileItem> items;
	try {
		PrintWriter pw = new PrintWriter(path);
		items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        for (FileItem item : items) {
        	item.getName();
            if (!item.isFormField()) {
            	// Process form file field (input type="file").
                String fieldname = item.getFieldName();
                String filename = FilenameUtils.getName(item.getName());
                InputStream filecontent = item.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(filecontent));
                String line = null;
                while((line = br.readLine()) != null){
                	pw.write(line);
                }
                System.out.println("File written");
            } 
        }
    } catch (org.apache.commons.fileupload.FileUploadException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    log.trace("END");
	}

}
