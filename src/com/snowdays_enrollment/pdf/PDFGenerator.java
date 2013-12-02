package com.snowdays_enrollment.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import net.glxn.qrgen.image.ImageType;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;

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
	/**
	 * @uml.property  name="eventName"
	 */
	private String eventName;
	/**
	 * @uml.property  name="path"
	 */
	private String path;
	/**
	 * @uml.property  name="aDocument"
	 * @uml.associationEnd  
	 */
	private int id;
	private String imagePath;
	private Document aDocument;
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
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
	public PDFGenerator(String fname, String lname, int id, String group, String eventName, String path) throws MalformedURLException, DocumentException, IOException{
		log.debug("###################################");
	    log.trace("START");
		name = fname;
		surname = lname;
		this.id = id;
		this.group = group;
		this.eventName = eventName;
		this.path = path;
		log.debug("PDF generator istantiated");
	    log.trace("END");
	}
	
	/**
	 * It creates the final pdf and stores it in the given path.
	 * @return boolean
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public boolean createDocument() throws DocumentException, MalformedURLException, IOException{
		log.trace("START");
		setDocument();
		boolean check = false;
	    aDocument.open();
	    addData();
	    addQR();
	    addImage();
	    log.debug("Output set");
	    aDocument.close();
	    return check;
	}
	
	/**
	 * It adds the text part to the badge.
	 * @return boolean
	 * @throws DocumentException
	 */
	public boolean addData() throws DocumentException{
		log.trace("START");
		Paragraph container = new Paragraph();
		Paragraph event = new Paragraph(eventName, subFont);
		Paragraph data = new Paragraph(name+" "+surname+" (#"+id+")", catFont);
		Paragraph group = new Paragraph(this.group, smallBold);
		container.add(event);
		container.add(data);
		container.add(group);
		container.setAlignment(Paragraph.ALIGN_RIGHT);
		boolean added = aDocument.add(container);
		log.debug("Container set");
		log.trace("END");
		return added;
	}
	
	/**
	 * It adds the qr image to the badge. 
	 * @return boolean
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public boolean addQR() throws MalformedURLException, IOException, DocumentException{
		log.trace("START");
		 QRGenerator aQR = QRGenerator.from(name+" "+surname+" "+this.group);
		 aQR.to(ImageType.PNG);
//		 File qrFile = aQR.file();
		 log.debug("QR location: "+aQR.file().getPath());
		 Image qrImage = Image.getInstance(aQR.file().getPath());
		 qrImage.scaleAbsolute(80f, 80f);
		 qrImage.setAbsolutePosition(200f, 280f);
		 boolean added = aDocument.add(qrImage);
		 log.debug("QR added");
		 log.trace("END");
		 return added;
	}
	
	/**
	 * It returns the absoliute path of the final file.
	 * @return String
	 */
	public String getAbsoluteFilePath(){
		return path+"/"+name+surname+this.group+".pdf";
	}
	
	/**
	 * It returns the path of the file in the server.
	 * @return String
	 */
	public String getFilePath(){
		return "/private/pdf/"+name+surname+this.group+".pdf";
	}
	
	/**
	 * It sets the image/logo for the event.
	 * @return boolean
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public boolean addImage() throws MalformedURLException, IOException, DocumentException{
		log.trace("START");
		Image anImage = Image.getInstance(imagePath);
		anImage.scaleAbsolute(100f, 70f);
		anImage.setAbsolutePosition(20f, 270f);
		boolean added = aDocument.add(anImage);
		log.debug("Image added");
		log.trace("END");
		return added;
	}
	
	/**
	 * It returns this document.
	 * @return Document
	 */
	public Document getDocument(){
		return this.aDocument;
	}
	
	/**
	 * It set the path of the image.
	 * @param path - String
	 */
	public void setImagePath(String path){
		this.imagePath = path;
	}
	
	/**
	 * It gets the filepath of the image.
	 * @return String
	 */
	public String getImagePath(){
		return this.imagePath;
	}
	
	/**
	 * It sets the size and the output for the document.
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void setDocument() throws FileNotFoundException, DocumentException{
		Rectangle page = new Rectangle(280f, 360f);
		aDocument = new Document(page, 0f, 0f, 221f, 0f);
		PdfWriter.getInstance(aDocument, new FileOutputStream(getAbsoluteFilePath()));
	}

}
