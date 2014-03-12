package com.snowdays_enrollment.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
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
	private PdfWriter writer;
	
	
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
		aDocument = new Document();
		name = fname;
		surname = lname;
		this.group = group;
		this.profile = profile;
		badgeType = badge;
		setDocument();
		fileName = fname + "_" + lname + "_" + group + ".pdf";
	}
	
	public PDFGenerator(String group, ArrayList<Participant> groupParticipants, String badge, String groupFolder) throws FileNotFoundException, DocumentException{
		aDocument = new Document();
		this.group = group;
		participants = groupParticipants;
		badgeType = badge;
		this.groupFolder = groupFolder;
	}
	
	public PDFGenerator(ArrayList<Group> groups, String outputFolder, Connection c, String badge){
		Rectangle pagesize = new Rectangle(252, 337);
		aDocument = new Document(pagesize, 0, 0, 221, 0);
		this.groups = groups;
		System.out.println("Size gen: " + groups.size());
		output = outputFolder;
		this.c = c;
		badgeType = badge;
		if(badge.equals("PARTY/HOST"))
			fileName = "host" + "_badges.pdf";
		else
			fileName = badge + "_badges.pdf";
	}
	
	public void createGroupBadges() throws MalformedURLException, IOException, DocumentException{
		aDocument.open();
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
			badgeFront.scaleAbsolute(252, 337);
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
	
//	public void createSingleBadge() throws MalformedURLException, IOException, DocumentException{
//		aDocument.open();
//		Image badgeFront = Image.getInstance(chooseBadgeFront());
//		Paragraph p1 = new Paragraph(capitalizeString(name), new Font(FontFamily.HELVETICA, 10));
//		p1.setAlignment(Element.ALIGN_CENTER); aDocument.add(p1);
//
//		Paragraph p2 = new Paragraph(capitalizeString(surname), new Font(FontFamily.HELVETICA, 10));
//		p2.setAlignment(Element.ALIGN_CENTER); p2.setSpacingBefore(0); aDocument.add(p2);
//
//		Paragraph p3 = new Paragraph(group, new Font(FontFamily.HELVETICA, 10));
//		p3.setAlignment(Element.ALIGN_CENTER); p3.setSpacingBefore(-1); aDocument.add(p3);
//
//		badgeFront.setAbsolutePosition(0,0);
//		badgeFront.scaleAbsolute(281, 367);
//		aDocument.add(badgeFront);
//
//		Image photo = Image.getInstance(groupFolder+ File.separator + "profile" + File.separator + profile);
//		photo.setAlignment(Element.ALIGN_CENTER);
//		photo.scaleAbsolute(96,120);
//		photo.setAbsolutePosition(92,145);
//		
//		aDocument.add(photo);
//		aDocument.close();
//	}
//	
	public void createBadgeByType(String type) throws MalformedURLException, IOException, DocumentException{
		aDocument.open();
		Image badgeFront = Image.getInstance(chooseBadgeFront());
		ParticipantDao pDao = new ParticipantDao(c);
		for(int j = 0; j < groups.size(); j++){
			participants = new ArrayList<Participant>();
			Group g = groups.get(j);
			if(g.getActualParticipantNumber() == 0)
				continue;
			else{
				group = g.getName();
				System.out.println("Group: " + group);
				participants = (ArrayList<Participant>) pDao.getAllRecordsById_group(g.getId());
				for (int i=0; i < participants.size(); i++){
					System.out.println("Participants: " + (participants.size()-i));
					Participant p = participants.get(i);
					System.out.println("Actual participant: " + p.getFname() + " " + p.getLname());
					System.out.println("Photo: " + p.getPhoto());
		
					aDocument.newPage();
					ColumnText ct1 = new ColumnText(writer.getDirectContent());
					ct1.setSimpleColumn(0, 0, 255, 141);
		
					Paragraph p1 = new Paragraph(capitalizeString(p.getFname()), new Font(FontFamily.HELVETICA, 10));
					p1.setAlignment(Element.ALIGN_CENTER);
					ct1.addElement(p1);
					ct1.go();
		
					ColumnText ct2 = new ColumnText(writer.getDirectContent());
					ct2.setSimpleColumn(0, 0, 255, 125);
					Paragraph p2 = new Paragraph(capitalizeString(p.getLname()), new Font(FontFamily.HELVETICA, 10));
					p2.setAlignment(Element.ALIGN_CENTER); 
					ct2.addElement(p2);
					ct2.go();
		
					ColumnText ct3 = new ColumnText(writer.getDirectContent());
					ct3.setSimpleColumn(0, 0, 255, 108);
					Paragraph p3 = new Paragraph(group, new Font(FontFamily.HELVETICA, 10));
					p3.setAlignment(Element.ALIGN_CENTER); 
					ct3.addElement(p3);
					ct3.go();
		
					badgeFront.setAbsolutePosition(0,0);
					badgeFront.scaleAbsolute(252, 337);
					aDocument.add(badgeFront);
					
					Image photo = null;
					
					if(participants.get(i).getPhoto() == null)
						photo = Image.getInstance(output + "/private/snowdays_logo_change_me.jpg");
					else
						photo = Image.getInstance(output + File.separator + g.getName() + File.separator + "profile" + File.separator + participants.get(i).getPhoto());
					photo.setAlignment(Element.ALIGN_CENTER);
					photo.scaleAbsolute(100,122);
					photo.setAbsolutePosition(78,140);
					photo.setSpacingAfter(20);
					aDocument.add(photo);
				}
			}
		}
		
		aDocument.close();
		System.out.println("FINISH!!!");
	}
	
	public String chooseBadgeFront(){
		String result = null;
		String folder = output + "/template/badges" + File.separator;
		switch(badgeType){
		case "PARTICIPANT":
			result = folder + "participant_front.jpg";
			break;
		case "STAFF":
			result = folder + "staff_front.jpg";
			break;
		case "PARTY/HOST":
			result = folder + "party_front.jpg";
			break;
		case "Sponsor":
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
		Rectangle page = new Rectangle(252f, 337f);
		aDocument = new Document(page, 0f, 0f, 221f, 0f);
		File file = new File(getPathBadgeByType());
		if(file.exists())
			file.delete();
		writer = PdfWriter.getInstance(aDocument, new FileOutputStream(getPathBadgeByType()));
	}

//	public String getAbsoluteFilePath(){
//		return groupFolder + File.separator + "badges" + fileName;
//	}
	
	public void setFileName(String name){
		fileName = name;
	}
	
	public String getPathBadgeByType(){
		File pdfFolder = new File(output + "/pdf/");
		if(!pdfFolder.exists())
			pdfFolder.mkdir();
		return pdfFolder.getAbsolutePath() + File.separator + fileName;
	}
}
