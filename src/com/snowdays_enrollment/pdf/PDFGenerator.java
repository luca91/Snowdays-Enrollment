package com.snowdays_enrollment.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;

/**
 * This class create a badge for a participant.
 * @author Luca Bellettati
 *
 */
public class PDFGenerator {
	 
	static Logger log = Logger.getLogger(PDFGenerator.class.getName());
	
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="surname"
	 */
	private String surname;
	/**
	 * @uml.property  name="id"
	 */
	private String group;
	private String profile;
	private Document aDocument;
	private ArrayList<Participant> participants;
	private String badgeType;
	private String groupFolder;
	private String fileName;
	private ArrayList<Group> groups;
	private String output;
	private Connection c;
	
	
	/**
	 * Empty constructor.
	 */
	public PDFGenerator(){ }
	
	/**
	 * It constructs an instance of the object with all the info.
	 * @param fname String
	 * @param lname String 
	 * @param groupId int
	 * @param eventName String
	 * @param path String 
	 * @throws MalformedURLException
	 * @throws DocumentException
	 * @throws IOException
	 */
	public PDFGenerator(String fname, String lname, String group, String profile, String badge) throws MalformedURLException, DocumentException, IOException{
		name = fname;
		surname = lname;
		this.group = group;
		this.profile = profile;
		badgeType = badge;
		setDocument();
		fileName = fname + "_" + lname + "_" + group + ".pdf";
	}
	
	public PDFGenerator(String group, ArrayList<Participant> groupParticipants, String badge, String groupFolder) throws FileNotFoundException, DocumentException{
		this.group = group;
		participants = groupParticipants;
		badgeType = badge;
		this.groupFolder = groupFolder;
	}
	
	public PDFGenerator(ArrayList<Group> groups, String outputFolder, Connection c, String badge){
		this.groups = groups;
		output = outputFolder;
		this.c = c;
		badgeType = badge;
		fileName = "participants_badges.pdf";
	}
	
	public void createGroupBadges() throws MalformedURLException, IOException, DocumentException{
		Image badgeFront = Image.getInstance(chooseBadgeFront());
		for (int i=0; i<participants.size();i++){
			System.out.println(participants.size()-i);

			aDocument.newPage();

			Paragraph p1 = new Paragraph(capitalizeString(participants.get(i).getFname()), new Font(FontFamily.HELVETICA, 10));
			p1.setAlignment(Element.ALIGN_CENTER); aDocument.add(p1);

			Paragraph p2 = new Paragraph(capitalizeString(participants.get(i).getLname()), new Font(FontFamily.HELVETICA, 10));
			p2.setAlignment(Element.ALIGN_CENTER); p2.setSpacingBefore(0); aDocument.add(p2);

			Paragraph p3 = new Paragraph(participants.get(i).getId()+" - "+group, new Font(FontFamily.HELVETICA, 10));
			p3.setAlignment(Element.ALIGN_CENTER); p3.setSpacingBefore(-1); aDocument.add(p3);

			badgeFront.setAbsolutePosition(0,0);
			badgeFront.scaleAbsolute(281, 367);
			aDocument.add(badgeFront);

			Image photo = Image.getInstance(groupFolder+ File.separator + "profile" + File.separator + participants.get(i).getPhoto());
			photo.setAlignment(Element.ALIGN_CENTER);
			photo.scaleAbsolute(96,120);
			photo.setAbsolutePosition(92,145);
			aDocument.add(photo);
		}
		
		aDocument.close();
		System.out.println("FINISH!!!");
	}
	
	public void createSingleBadge() throws MalformedURLException, IOException, DocumentException{
		Image badgeFront = Image.getInstance(chooseBadgeFront());
		Paragraph p1 = new Paragraph(capitalizeString(name), new Font(FontFamily.HELVETICA, 10));
		p1.setAlignment(Element.ALIGN_CENTER); aDocument.add(p1);

		Paragraph p2 = new Paragraph(capitalizeString(surname), new Font(FontFamily.HELVETICA, 10));
		p2.setAlignment(Element.ALIGN_CENTER); p2.setSpacingBefore(0); aDocument.add(p2);

		Paragraph p3 = new Paragraph(group, new Font(FontFamily.HELVETICA, 10));
		p3.setAlignment(Element.ALIGN_CENTER); p3.setSpacingBefore(-1); aDocument.add(p3);

		badgeFront.setAbsolutePosition(0,0);
		badgeFront.scaleAbsolute(281, 367);
		aDocument.add(badgeFront);

		Image photo = Image.getInstance(groupFolder+ File.separator + "profile" + File.separator + profile);
		photo.setAlignment(Element.ALIGN_CENTER);
		photo.scaleAbsolute(96,120);
		photo.setAbsolutePosition(92,145);
		
		aDocument.add(photo);
		aDocument.close();
	}
	
	public void createBadgeByType(String type) throws MalformedURLException, IOException, DocumentException{
		Image badgeFront = Image.getInstance(chooseBadgeFront());
		ParticipantDao pDao = new ParticipantDao(c);
		for(int j = 0; j < groups.size(); j++){
			participants = new ArrayList<Participant>();
			Group g = groups.get(j);
			group = g.getName();
			participants = (ArrayList<Participant>) pDao.getAllRecordsById_group(g.getId());
			groupFolder = output + File.separator + g.getName();
			for (int i=0; i < participants.size(); i++){
				System.out.println(participants.size()-i);
	
				aDocument.newPage();
	
				Paragraph p1 = new Paragraph(capitalizeString(participants.get(i).getFname()), new Font(FontFamily.HELVETICA, 10));
				p1.setAlignment(Element.ALIGN_CENTER); aDocument.add(p1);
	
				Paragraph p2 = new Paragraph(capitalizeString(participants.get(i).getLname()), new Font(FontFamily.HELVETICA, 10));
				p2.setAlignment(Element.ALIGN_CENTER); p2.setSpacingBefore(0); aDocument.add(p2);
	
				Paragraph p3 = new Paragraph(participants.get(i).getId()+" - "+group, new Font(FontFamily.HELVETICA, 10));
				p3.setAlignment(Element.ALIGN_CENTER); p3.setSpacingBefore(-1); aDocument.add(p3);
	
				badgeFront.setAbsolutePosition(0,0);
				badgeFront.scaleAbsolute(281, 367);
				aDocument.add(badgeFront);
	
				Image photo = Image.getInstance(groupFolder+ File.separator + "profile" + File.separator + participants.get(i).getPhoto());
				photo.setAlignment(Element.ALIGN_CENTER);
				photo.scaleAbsolute(96,120);
				photo.setAbsolutePosition(92,145);
				aDocument.add(photo);
			}
		}
		
		aDocument.close();
		System.out.println("FINISH!!!");
	}
	
	public String chooseBadgeFront(){
		String result = null;
		String folder = output + "/template" + File.separator;
		switch(badgeType){
		case "PARTICIPANT":
			result = folder + "participant_front.jpg";
			break;
		case "STAFF":
			result = folder + "staff_front.jpg";
			break;
		case "PARTY/HOST":
			result = folder + "host_front.jpg";
			break;
		case "SPONSOR":
			result = folder + "sponsor_front.jpg";
			break;
		}
		return result;
	}
	
	private static String capitalizeString(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
				found = false;
			}
		}
		return String.valueOf(chars);
	}
	
	public void setDocument() throws FileNotFoundException, DocumentException{
		Rectangle page = new Rectangle(280f, 360f);
		aDocument = new Document(page, 0f, 0f, 221f, 0f);
		PdfWriter.getInstance(aDocument, new FileOutputStream(getAbsoluteFilePath()));
	}

	public String getAbsoluteFilePath(){
		return groupFolder + File.separator + "badges" + fileName;
	}
	
	public void setFileName(String name){
		fileName = name;
	}
	
	public String getPathBadgeByType(){
		return output + fileName;
	}
}
