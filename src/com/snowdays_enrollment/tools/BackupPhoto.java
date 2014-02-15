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
	
	public BackupPhoto(String group, String server){
		groups = new ArrayList<String>(1);
		groups.add(group);
		serverPath = server;
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
	
	public void backupSingleGroup() throws IOException{
		File groupFolder = new File(serverPath + groups.get(0));
		File profile = new File(groupFolder + File.separator + "profile");
		File badges = new File(groupFolder + File.separator + "badges");
		File ids = new File(groupFolder + File.separator + "studentids");
		File destination = new File(backUpFolderPath + File.separator + groups.get(0));
		File destProfile = new File(destination + File.separator + "profile");
		File destBadges = new File(destination + File.separator + "badges");
		File desIds = new File(destination + File.separator + "studentids");
		FileUtils.copyDirectory(profile, destProfile);
		FileUtils.copyDirectory(badges, destBadges);
		FileUtils.copyDirectory(ids, desIds);
	}

}
