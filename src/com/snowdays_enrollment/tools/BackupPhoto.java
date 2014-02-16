package com.snowdays_enrollment.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class BackupPhoto {
	
	private List<String> groups;
	private String serverPath;
	private String backUpFolderPath;
	private File file;
	private String subfolder;
	
	public BackupPhoto(String group, String server, File file, String subfolder){
		groups = new ArrayList<String>(1);
		groups.add(group);
		serverPath = server;
		this.file = file;
		this.subfolder = subfolder;
	}
	
	public BackupPhoto(String server, String...strings){
		serverPath = server;
		for(String s: strings)
			groups.add(s);
	}
	
	public void createBackUpFolder(){
		File backUpFolder = new File(serverPath + File.separator + "../../backup_photo");
		if(!backUpFolder.exists())
			backUpFolder.mkdir();
		backUpFolderPath = backUpFolder.getPath();
	}
	
	public void createGroupBackUpFolders(){
		File groupBackUpFolders = new File(backUpFolderPath + File.separator + groups.get(0));
		if(!groupBackUpFolders.exists()){
			groupBackUpFolders.mkdir();
			File destProfile = new File(groupBackUpFolders + File.separator + "profile");
			File destBadges = new File(groupBackUpFolders + File.separator + "badges");
			File desIds = new File(groupBackUpFolders + File.separator + "studentids");
			destProfile.mkdir();
			destBadges.mkdir();
			desIds.mkdir();
		}
		
	}
	
	public void backupSinglePhoto() throws IOException{
		File destination = new File(backUpFolderPath + File.separator + groups.get(0) + File.separator + subfolder + File.separator + file.getName());
		System.out.println("Source: " + file.getAbsolutePath());
		System.out.println("Destination: " + destination);
		FileUtils.copyFile(file.getAbsoluteFile(), destination);
	}

}
