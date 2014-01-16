package com.snowdays_enrollment.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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
	
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
	
	public void uploadFile(String directory) throws IOException{
	}

}
