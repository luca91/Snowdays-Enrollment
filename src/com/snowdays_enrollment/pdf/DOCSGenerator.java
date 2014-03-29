package com.snowdays_enrollment.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	private Path agreementBodyTextFirst;
	private Path agreementBodyTextSecond;
	private Path agreementBodyTextThird;
	private String busBodyText;
	private Document document;
	private Path footerText;
	private static Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
	private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 20,Font.BOLD);
	private static Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
	private static Font headerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
	private static Font signFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC);
	private static Font formFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC);
	private static Font footerFont = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
	private static String fileLocation;
	/**
	 * @uml.property  name="record"
	 */
//	private Participant record;
	/**
	 * @uml.property  name="path"
	 */
	private String path;

	private Paragraph head;

	private Paragraph bodyFirst;
	
	private Paragraph bodySecond;
	
	private Paragraph bodyThird;
	
	private Paragraph bus;
	
	private String groupid;
	
	private ArrayList<Participant> p;
	
	private Paragraph foot;
	
	//FOR TESTING
	

//	public static void main(String [ ] args) throws MalformedURLException, DocumentException, IOException
//	{
//		String fname = "Ettore";
//		String lname = "El Burrito";
//		String email = "fakeEmail@morefake.com";
//		String date_of_birth = "03/11/1993";	
//		
//		Participant record = new Participant();
//        record.setId(105);
//        record.setId_group(56);
//        record.setGroupName("Unibz");
//        record.setFname(fname);
//        record.setLname(lname);
//        record.setEmail(email);
//        record.setDate_of_birth(date_of_birth);
//        record.setApproved(false);
//        record.setGender("M");
//        record.setAddress("via Sotto il Monte 34");
//        record.setBirthCountry("Spain");
//        record.setBirthPlace("Madrid");
//        record.setCity("Lana");
//        record.setCountry("Italy");
//        record.setZip("39011");
//        record.setPhone("3386228095");
//        
//	String outputDir = "/home/luca/Documents/";
//	DOCSGenerator g = new DOCSGenerator	("Unibz", record, outputDir );
//	g.setImagePath("/home/luca/git/snowdays-enrollment/WebContent/private/images/Logo_orizzontale_2014.png");
//	g.setHeaderText("/home/luca/git/snowdays-enrollment/WebContent/private/docsblueprints/header");
//	g.setAgreementBodyTextFirst("/home/luca/git/snowdays-enrollment/WebContent/private/docsblueprints/agreement_body");
//	g.setAgreementBodyTextSecond("/home/luca/git/snowdays-enrollment/WebContent/private/docsblueprints/agreement_body_2");
//	g.setAgreementBodyTextThird("/home/luca/git/snowdays-enrollment/WebContent/private/docsblueprints/agreement_body_3");
//	g.setFooterText("/home/luca/git/snowdays-enrollment/WebContent/private/docsblueprints/footer");
//	g.setDocument();
//	g.writeDocument();
//	g.closePdf();
//	}
	
	
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
	    p = new ArrayList<Participant>();
	    p.add(record);
		this.path = path;
		this.groupid = groupName;
		log.debug("Constructor instantiated");
	}
	
	public DOCSGenerator(String groupName, ArrayList<Participant> p, String path){
		groupid = groupName;
		this.path = path;
		this.p = p;
	}
	
	/**
	 * Construct the document for one participant 
	 * @throws DocumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void writeDocument() throws DocumentException, MalformedURLException, IOException{ 
	try {
		if(p.size() == 1){
	        document.add(head);
	        addCentTitle("LETTER OF AGREEMENT"); 
	        Paragraph form = createCompiledForm(p.get(0));
	        document.add(form);
	        document.add(bodyFirst); 
	        document.add(foot);
	        document.newPage();
	        document.add(head);
	        document.add(bodySecond);
	        document.add(foot);
//	        document.newPage();
//	        document.add(head);
//	        document.add(bodyThird);
//	        document.add(foot);
	        document.newPage();
	        document.add(head);
	        addCentTitle("RESPONSABILITIES FOR BUS DAMAGES");
	        document.add(form);
	        document.add(bus);
	        document.add(foot);
	        document.newPage();
	        log.debug(p.get(0).getFname()+" written");
        }
		else{
			for(Participant par: this.p.toArray(new Participant[] {})){
				if(document.getPageNumber() >= 2){
					System.out.println("Page number :" + document.getPageNumber());
					document.newPage();
				}
				document.add(head);
			    addCentTitle("LETTER OF AGREEMENT"); 
			    Paragraph form = createCompiledForm(par);
			    document.add(form);
			    document.add(bodyFirst); 
		        document.add(foot);
		        document.newPage();
		        document.add(head);
		        document.add(bodySecond);
		        document.add(foot);
//			    document.newPage();
//			    document.add(head);
//			    document.add(bodyThird);
//		        document.add(foot);
		        document.newPage();
		        document.add(head);
			    addCentTitle("RESPONSABILITIES FOR BUS DAMAGES");
			    document.add(form);
			    document.add(bus);
			    document.add(foot);
			    log.debug(p.get(0).getFname()+" written");
			}
		}
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
		    header.setSpacingAfter(12);
		    scan.close();
		    return header;
	  }
	
	/**
	 * Create the text body part for the agreement document, parsed from the blueprint agreement_body
	 * @return
	 * @throws IOException 
	 */
	public Paragraph createAgreementBodyFirst() throws IOException {
		Paragraph body = new Paragraph();
		body.setAlignment(Paragraph.ALIGN_LEFT);
		Scanner scan = new Scanner(agreementBodyTextFirst);
		String line;
		String rex2 = "^([0-9]?[.]? )?[A-Z\\sa-z]+[:]?$";
		while (scan.hasNext()){
			line = scan.nextLine();
			if (line.matches(rex2)) {
				Paragraph subtitle =	new Paragraph(line, subtitleFont);
				subtitle.setSpacingAfter(0);	
				subtitle.setSpacingBefore(2);
				body.add(subtitle);			  
			}	
			else {
				Paragraph p = new Paragraph(line, bodyFont);
				p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				body.add(p);
			}
		}		
//		body.add(createSignature());	 
		scan.close();
		return body;

	}
	
	public Paragraph createAgreementBodySecond() throws IOException {
		Paragraph body = new Paragraph();
		body.setAlignment(Paragraph.ALIGN_LEFT);
		Scanner scan = new Scanner(agreementBodyTextSecond);
		String line;
		String rex2 = "^([0-9]?[.]? )?[A-Z\\sa-z]+[:]?$";
		while (scan.hasNext()){
			line = scan.nextLine();
				if (line.matches(rex2)) {
					Paragraph subtitle =	new Paragraph(line, subtitleFont);
					subtitle.setSpacingAfter(0);	
					subtitle.setSpacingBefore(2);
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
	
	public Paragraph createAgreementBodyThird() throws IOException {
		Paragraph body = new Paragraph();
		body.setAlignment(Paragraph.ALIGN_LEFT);
		Scanner scan = new Scanner(agreementBodyTextThird);
		String line;
		String rex2 = "^([0-9]?[.]? )?[A-Z\\sa-z]+[:]?$";
		while (scan.hasNext()){
			line = scan.nextLine();
				if (line.matches(rex2)) {
					Paragraph subtitle =	new Paragraph(line, subtitleFont);
					subtitle.setSpacingAfter(4);	
					subtitle.setSpacingBefore(4);
					body.add(subtitle);			  
				}	
			else {
				Paragraph p = new Paragraph(line, bodyFont);
				p.setAlignment(Paragraph.ALIGN_JUSTIFIED);
				body.add(p);
			}
		}		
		body.add(createSignature());
		for(int i = 0; i < 26; i++)
			body.add(new Paragraph("\n", signFont));
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
		for(int i = 0; i < 19; i++)
			body.add(new Paragraph("\n", signFont));
		return body;
	}
	
	/**
	 * Creates the compiled paragraph with the participant data
	 * @return Paragraph
	 */
	public Paragraph createCompiledForm(Participant record){
		Paragraph form = new Paragraph();
		
		if (record != null) {
		
			System.out.println(record.getBirthPlace());
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		Paragraph loc = new Paragraph("Bolzano, " + dateFormat.format(new Date()), bodyFont);
		loc.setAlignment(Paragraph.ALIGN_RIGHT);
		loc.setSpacingAfter(10);
			
		Paragraph name = new Paragraph ();
		Chunk name2 = new Chunk ("The undersigned    ", bodyFont );
		name.add(name2);
		if (record.getFname() != null) {
		Chunk name3 = new Chunk (record.getFname()+" "+record.getLname(), formFont );
		  name.add(name3);
		}
		else{
			name.add("___________________________  _______________________");
		}
		
		
		Paragraph born = new Paragraph ();
		Chunk born2 = new Chunk ("born in    ", bodyFont );
		Chunk born4 = new Chunk ("   on the    ", bodyFont );
		born.add(born2);

		if (!record.getBirthPlace().equals("n") || !record.getBirthCountry().equals("n")) {
			Chunk born3 = new Chunk (record.getBirthPlace()+ ", "+record.getBirthCountry(), formFont );
			born.add(born3);
			born.add(born4);
			Chunk born5 = new Chunk (record.getDate_of_birth(), formFont );			
			born.add(born5);
		}
		else{
			Chunk born3 = new Chunk ("______________________________", formFont );
			born.add(born3);
			Chunk born5 = new Chunk ("______________________________", formFont );
			born.add(born4);
			born.add(born5);
		}
			
		Paragraph resident = new Paragraph();
		if(!record.getAddress().equals("n")){
			Chunk res1 = new Chunk ("resident in    ", bodyFont );
			Chunk res2 = new Chunk (record.getAddress() + ", "+ record.getCity() +" \n" +record.getZip()+"   "+record.getCountry() , formFont);
			resident.add(res1);
			resident.add(res2);
		}
		else{
			Chunk res1 = new Chunk ("resident in ___________________________________________________________", bodyFont );
			resident.add(res1);
		}

//		else
//		{
//			born.add("___________________________________ on the ______________________");
//		}
//	
//		
//		Paragraph resident = new Paragraph();
//		Chunk res1 = new Chunk ("resident in    ", bodyFont );	
//		resident.add(res1);
//		if (record.getAddress() != null && record.getCity() != null){
//			Chunk res2 = new Chunk (record.getAddress() + ", "+ record.getCity() + "," , formFont);	
//			resident.add(res2);
//		}
//		else
//		{
//			resident.add("___________________________________, ______________________");
//		}
//		
//		if (record.getZip() != null && record.getCountry() != null){ 
//		Chunk res3 = new Chunk (" \n" +record.getZip()+"   "+record.getCountry()  , formFont);
//		resident.add(res3);
//		}
//		else {
//			resident.add("__________________________________________");
//		}
//		
		Paragraph uni = new Paragraph ("University of    " , bodyFont );
		if (groupid != null){ 
		Chunk uni2 = new Chunk (groupid, formFont);
		uni.add(uni2);
		}
		else {
			uni.add("__________________________________________");
		}
		
//		Paragraph spec = new Paragraph (" *if you find this form incorrect, please report to snowdays@unibz.it", signFont);
//		spec.setAlignment(Paragraph.ALIGN_RIGHT);
		
		form.add(loc);
		form.add(name);
		form.add(born); 
		form.add(resident);
		form.setSpacingAfter(10);
		form.add(uni);
//		form.add(spec);
		}		
			
		return form;
		
		
	}
	
	public Paragraph createFooter() throws IOException{
		Paragraph footer = new Paragraph();
		  
	    Scanner scan = new Scanner(footerText);
	    String line;
	    Paragraph p = new Paragraph("\n", footerFont);
	    footer.add(p);
	    footer.add(p);
	     while (scan.hasNext()){
	    	  line = scan.nextLine();
	    	  Paragraph ft = new Paragraph(line, footerFont);
	    	  ft.setAlignment(Paragraph.ALIGN_CENTER);
	    	  footer.add(ft);
	     }
	     scan.close();
	     return footer;
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

	public Path getAgreementBodyTextFirst() {
		return agreementBodyTextFirst;
	}

	public void setAgreementBodyTextFirst(String agreementBodyText) {
		this.agreementBodyTextFirst = Paths.get(agreementBodyText);
	}
	
	public Path getAgreementBodyTextSecond() {
		return agreementBodyTextFirst;
	}

	public void setAgreementBodyTextSecond(String agreementBodyText) {
		this.agreementBodyTextSecond = Paths.get(agreementBodyText);
	}
	
	public Path getAgreementBodyTextThird() {
		return agreementBodyTextThird;
	}

	public void setAgreementBodyTextThird(String agreementBodyText) {
		this.agreementBodyTextThird = Paths.get(agreementBodyText);
	}
	
	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getBusBodyText() {
		return busBodyText;
	}

	public void setBusBodyText(String busBodyText) {
		this.busBodyText = busBodyText;
	}

	public void closePdf(){
		document.close(); 
		log.debug("Pdf closed in "+ getFilePath() );
	}
	
	public void openPdf() {
		document.open();
	}
	
	public void setFooterText(String footer){
		this.footerText = Paths.get(footer);
	}
	
	public Path getFooterText(){
		return footerText;
	}
	
	public String getFileLocation(){
		if(p.size() == 1)
			return "/private/pdf/Agreement_Bus_" + p.get(0).getFname() + "_" + p.get(0).getLname() + ".pdf";
		else
			return "/private/pdf/Agreement_Bus_" + groupid + ".pdf";
	}
	
	

	/**
	 * Create the actual file in in the correct folder and instantiate the necessary paragraphs
	 * @throws DocumentException
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public void setDocument() throws DocumentException, MalformedURLException, IOException{
		document = new Document(PageSize.A4);
		if(checkFile(getFilePath())){
			File delete = new File(getFilePath());
			delete.delete();
		}
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getFilePath()));
		writer.setPageEmpty(false);
		openPdf();
	    addMetaData();
	    head = createHeader();
		bodyFirst = createAgreementBodyFirst();
		bodySecond = createAgreementBodySecond();
//		bodyThird = createAgreementBodyThird();
		bus = createBusParagraph();
		foot = createFooter();
		foot.setAlignment(Paragraph.ALIGN_BOTTOM);
	}
	
	
	/**
	 * Add Metadata info to file
	 */
	 private void addMetaData() {
		 if(p.size() > 1){
		    document.addTitle("License Agreement - Bus Guidelines " +groupid);
		    document.addSubject("License Agreement and Bus guidelines for group: "+groupid);
		    document.addAuthor("Snowdays 2014");
		    document.addCreator("Snowdays 2014");
		    document.addCreationDate();
		 }
		 else{
			 document.addTitle("License Agreement - Bus Guidelines " + p.get(0).getFname() + " " + p.get(0).getLname());
			 document.addSubject("License Agreement and Bus guidelines for participant: " + p.get(0).getFname() + " " + p.get(0).getLname());
			 document.addAuthor("Snowdays 2014");
			 document.addCreator("Snowdays 2014");
			 document.addCreationDate();
		 }
			 
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
//	public String getAbsoluteFilePath(){
//		return path+"/"+"Agreement"+record.getId_group()+".pdf";
//	}
//	
//	public Participant getRecord() {
//		return record;
//	}
//
//	public void setRecord(Participant record) {
//		this.record = record;
//	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilePath(){
		if(p.size() == 1)
			return path +"/Agreement_Bus_" + p.get(0).getFname() + "_" + p.get(0).getLname() + ".pdf";
		else
			return path +"/Agreement_Bus_" + groupid + ".pdf";
	}
	
	public boolean checkFile(String file){
		File toCheck = new File(file);
		if(toCheck.exists())
			return true;
		return false;
	}
}