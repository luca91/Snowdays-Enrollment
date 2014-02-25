package com.snowdays_enrollment.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;

/**
 * This class creates the pdf of the agreement and
 * bus damages documents, auto-compiled with the participant data.
 * Blueprints of the document are in WEBCONTENT/private/docsblueprints
 *
 * @author Ettore Ciprian
 *
 */


public class DOCSGenerator {
	
	
	static Logger log = Logger.getLogger(DOCSGenerator.class.getName());
	
	private Path headerText;
	private String imagePath;
	private Path agreementBodyText;
	private String busBodyText;
	private Document document;
	private static Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 22,Font.BOLD);
	private static Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	private static Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static Font signFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
	private static Font formFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC);
	
	/**
	 * @uml.property  name="record"
	 */
	private Participant record;
	/**
	 * @uml.property  name="path"
	 */
	private String path;

	private Paragraph head;

	private Paragraph body;
	
	private Paragraph bus;
	
	private String groupid;
	/**
	 * @uml.property  name="aDocument" 
	 * @uml.associationEnd  
	 */
	
	//FOR TESTING
	/*
	public static void main(String [ ] args) throws MalformedURLException, DocumentException, IOException
	{
		String fname = "Ettore";
		String lname = "El Burrito";
		String email = "fakeEmail@morefake.com";
		String date_of_birth = "03/11/1993";	
		
		Participant record = new Participant();
        record.setId(105);
        record.setId_group(56);
        record.setGroupName("Unibz");
        record.setFname(fname);
        record.setLname(lname);
        record.setEmail(email);
        record.setDate_of_birth(date_of_birth);
        record.setApproved(false);
        record.setGender("M");
        record.setAddress("via Sotto il Monte 34");
        record.setBirthCountry("Spain");
        record.setBirthPlace("Madrid");
        record.setCity("Lana");
        record.setCountry("Italy");
        record.setZip(39011);
        record.setPhone("3386228095");
        
	String outputDir = "/home/ettore/Documenti/";
	DOCSGenerator g = new DOCSGenerator	("Unibz", record, outputDir );
	g.setImagePath("/home/ettore/git/Snowdays-Enrollment/WebContent/private/images/Logo_orizzontale_2014.png");
	g.setHeaderText("/home/ettore/git/Snowdays-Enrollment/WebContent/private/docsblueprints/header");
	g.setAgreementBodyText("/home/ettore/git/Snowdays-Enrollment/WebContent/private/docsblueprints/agreement_body");
	g.setDocument();
	g.writeDocument();
	g.closePdf();

	}
	*/
	
	/**
	 * Empty constructor.
	 */
	public DOCSGenerator(){ }
	
	/**
	 * This constructor returns the final pdf filepath 
	 */
	public DOCSGenerator(String groupName, Participant record, String path) {
		super();
		log.debug("###################################");
	    log.trace("START");
	    this.record = record;
		this.path = path;
		this.groupid = groupName;
				log.debug("Constructor instantiated");
	}
	
	
	/**
	 * Construct the document for one participant 
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void writeDocument() throws DocumentException, MalformedURLException, IOException{ 
	try {
        document.add(head);
        addCentTitle("LETTER OF AGREEMENT"); 
        Paragraph form = createCompiledForm();
        document.add(form);
        document.add(body); 
        document.newPage();
        document.add(head);
        addCentTitle("RESPONSABILITIES FOR BUS DAMAGES");
        document.add(form);
        document.add(bus);
        log.debug("Output set");
	} catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	/**
	 * Create the header of the document
	 * @throws DocumentException
	 * @return Paragraph
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private Paragraph createHeader() throws DocumentException, MalformedURLException, IOException {
		    Paragraph header = new Paragraph();
		    PdfPTable table = new PdfPTable(3);
		    table.getDefaultCell().setBorder(Rectangle.BOTTOM);
		    float[] colWidths = { 350, 300, 350 };
		    table.setWidths(colWidths);
		    table.setWidthPercentage(100);		 
		    Paragraph l = new Paragraph();
		    Paragraph r = new Paragraph();
		    PdfPCell right = new PdfPCell();
		    PdfPCell left = new PdfPCell();
		    right.setBorder(Rectangle.BOTTOM);
		    left.setBorder(Rectangle.BOTTOM);
		    
		    Scanner scan = new Scanner(headerText);
		    String line;
		     while (scan.hasNext()){
		    	  line = scan.nextLine();
		    	  l.add(new Paragraph(line, headerFont));
		    	  line = scan.nextLine();
		    	  Paragraph rp = new Paragraph(line, headerFont);
		    	  rp.setAlignment(Element.ALIGN_RIGHT);
		    	  r.add(rp);
		     }
		     
		     right.addElement(r);
		     left.addElement(l);
		     table.addCell(left);
		     table.addCell(setImage());
		     table.addCell(right);
		     
		    header.add(table);
		    header.setSpacingAfter(16);
		    scan.close();
		    return header;
	  }
	
	/**
	 * Create the text body part for the agreement document, parsed from the blueprint agreement_body
	 * @return
	 * @throws IOException 
	 */
	public Paragraph createAgreementBody() throws IOException {
		Paragraph body = new Paragraph();
		body.setAlignment(Paragraph.ALIGN_LEFT);
		Scanner scan = new Scanner(agreementBodyText);
		String line;
		String rex2 = "^([0-9]?[.]? )?[A-Z\\sa-z]+[:]?$";
		while (scan.hasNext()){
			line = scan.nextLine();
				if (line.matches(rex2)) {
					Paragraph subtitle =	new Paragraph(line, subtitleFont);
					subtitle.setSpacingAfter(12);	
					subtitle.setSpacingBefore(8);
					body.add(subtitle);			  
				}	
			else {
				Paragraph p = new Paragraph(line, bodyFont);
				p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				body.add(p);
			}
		}		
		body.add(createSignature());	 
		scan.close();
		return body;

	}
	
	/**
	 * Parses and creates the bus guidelines body text from blueprint 
	 * @return
	 * @throws IOException
	 */
	public Paragraph createBusParagraph() throws IOException {
		busBodyText = "participant at the “Bolzano SNOWDAYS 2014” from 20th to 22th of March 2014 "
				+ "declares that if he/she causes any damage to the busses, "
				+ "he/she will pay a fee of 100 € as agreed with the bus company."
				+ "\nFor the accuracy of statement,";
		
		Paragraph body = new Paragraph(busBodyText, bodyFont); 
		body.add(createSignature());
		return body;
	}
	
	/**
	 * Creates the compiled paragraph with the participant data
	 * @return Paragraph
	 */
	public Paragraph createCompiledForm(){
		Paragraph form = new Paragraph();
		
		if (record != null) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Paragraph loc = new Paragraph("Bolzano, " + dateFormat.format(new Date()), bodyFont);
		loc.setAlignment(Paragraph.ALIGN_RIGHT);
		loc.setSpacingAfter(20);
			
		Paragraph name = new Paragraph ();
		Chunk name2 = new Chunk ("The undersigned    ", bodyFont );
		name.add(name2);
		if (record.getFname() != null) {
		Chunk name3 = new Chunk (record.getFname()+" "+record.getLname(), formFont );
		  name.add(name3);
		}
		
		
		Paragraph born = new Paragraph ();
		Chunk born2 = new Chunk ("born in    ", bodyFont );
		Chunk born4 = new Chunk ("   on the    ", bodyFont );
		born.add(born2);
		
		if (record.getBirthPlace() != null && record.getBirthCountry() != null) {
			Chunk born3 = new Chunk (record.getBirthPlace()+ ", "+record.getBirthCountry(), formFont );
			born.add(born3);
			Chunk born5 = new Chunk (record.getDate_of_birth(), formFont );
			born.add(born4);
			born.add(born5);
		}
	
		
		Paragraph resident = new Paragraph();
		Chunk res1 = new Chunk ("resident in    ", bodyFont );
		Chunk res2 = new Chunk (record.getAddress() + ", "+ record.getCity() +" \n" +record.getZip()+"   "+record.getCountry() , formFont);
		resident.add(res1);
		resident.add(res2);
		
		Paragraph uni = new Paragraph ("University of    " , bodyFont );
		Chunk uni2 = new Chunk (groupid, formFont);
		uni.add(uni2);
		
		Paragraph spec = new Paragraph (" *if you find this form incorrect, please report to snowdays@unibz.it", signFont);
		spec.setAlignment(Paragraph.ALIGN_RIGHT);
		
		form.add(loc);
		form.add(name);
		form.add(born); 
		form.add(resident);
		form.setSpacingAfter(20);
		form.add(uni);
		form.add(spec);
		}		
			
		return form;
		
		
	}
	

	/**
	 * Add a single title centered
	 * @param text
	 * @throws DocumentException
	 */
	public void addCentTitle(String text) throws DocumentException{
		Paragraph title = new Paragraph(text, titleFont); 
		title.setAlignment(Paragraph.ALIGN_CENTER);
		title.setSpacingAfter(40);
		document.add(title);
	}
	
	/**
	 * Create signature paragraph
	 * @return
	 */
	public Paragraph createSignature(){
		Paragraph sign = new Paragraph ("_________________________\n"
				+ "(Participant's signature)", signFont);
		sign.setAlignment(Paragraph.ALIGN_RIGHT);
		sign.setSpacingBefore(30);
		
		return sign;
		
	}
	
	/**
	 * It sets the image/logo Snowdays.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public Image setImage() throws MalformedURLException, IOException, DocumentException{
		log.trace("START");
		Image anImage = Image.getInstance(imagePath);
		anImage.scalePercent(100f);
		log.debug("Image added");
		log.trace("END");
     return anImage;
	}

	public Path getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {
		this.headerText = Paths.get(headerText);
	}

	public Path getAgreementBodyText() {
		return agreementBodyText;
	}

	public void setAgreementBodyText(String agreementBodyText) {
		this.agreementBodyText = Paths.get(agreementBodyText);
	}
	
	public String getBusBodyText() {
		return busBodyText;
	}

	public void setBusBodyText(String busBodyText) {
		this.busBodyText = busBodyText;
	}

	public void closePdf(){
		document.close(); 	
	}
	
	public void openPdf() {
		document.open();
	}

	/**
	 * Create the actual file in in the correct folder and instantiate the necessary paragraphs
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void setDocument() throws DocumentException, MalformedURLException, IOException{
		document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream(getAbsoluteFilePath()));
		openPdf();
	    addMetaData();
	    head = createHeader();
		body = createAgreementBody();
		bus = createBusParagraph();
	}
	
	
	/**
	 * Add Metadata info to file
	 */
	 private void addMetaData() {
		    document.addTitle("License Agreement - Bus guidelines " +record.getGroupName());
		    document.addSubject("License Agreement and Bus guidelines for group: "+record.getGroupName());
		    document.addAuthor("Snowdays 2014");
		    document.addCreator("Snowdays 2014");
		    document.addCreationDate();
		  }
	 
	 /**
	  * It sets the path of the image.
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
	 * It returns the absolute path of the final file.
	 * @return String
	 */
	public String getAbsoluteFilePath(){
		return path+"/Agreement"+record.getId_group()+".pdf";
	}
	
	public Participant getRecord() {
		return record;
	}

	public void setRecord(Participant record) {
		this.record = record;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * It returns the path of the file in the server.
	 * @return String
	 */
	public String getFilePath(){
		return "/private/pdf/Agreement"+record.getId_group()+".pdf";
	}
	

}
