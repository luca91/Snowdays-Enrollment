package com.snowdays_enrollment.controller.pub;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.RegistrationExternalsDao;
import com.snowdays_enrollment.dao.RegistrationUniBzDao;
import com.snowdays_enrollment.dao.SettingsDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.model.RegistrationExternal;
import com.snowdays_enrollment.model.RegistrationUniBz;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.DBConnection;
import com.snowdays_enrollment.tools.Email;


/**
 * Servlet implementation class ParticipantController(public)
 */
@WebServlet(urlPatterns = {
		"/public/participantInt.jsp",
		"/public/participantAdd"
		})
@MultipartConfig
public class ParticipantController extends HttpServlet {
	
	// commons logging references
	static Logger log = Logger.getLogger(ParticipantController.class.getName());
	
	private static final long serialVersionUID = 1L;
	
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;

	private static String form = "/public/jsp/participantInt.jsp";
	private Connection c;
	
    /**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private ParticipantDao dao;   
    private GroupDao gDao;
    private SettingsDao sDao;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParticipantController() {
        super();
        log.debug("###################################");
    	log.trace("START");
		dao = new ParticipantDao();
        log.debug("Dao object instantiated");
        log.trace("END");
    }

	/**
	 * doGet method - maps the normal pages
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param HttpServletRequest request, HttpServletResponse response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		c = new DBConnection().getConnection();
		String email = request.getParameter("email");
		request.setAttribute("email", email);
		int group = Integer.parseInt(request.getParameter("id_group"));
		System.out.println(group);
		RegistrationUniBzDao ruDao = new RegistrationUniBzDao();
		ParticipantDao pDao = new ParticipantDao();
		RegistrationUniBz ru = ruDao.getRegistrationByEmail(email);
		Participant p = new Participant();
		if (ru.getStatus().equals("none")) {
			p.setFname(ru.getName());
			p.setLname(ru.getSurname());
			p.setEmail(ru.getEmail());
		}
		else{
			p = pDao.getParticipantByEmail(email);
			request.setAttribute("record", p);
    		request.setAttribute("selGen", p.getGender());
    	    request.setAttribute("selPro", dao.getProgramByID(p.getFridayProgram()));
    	    request.setAttribute("selTS", p.getTShirtSize());
    	    request.setAttribute("selRen", dao.getRentalByID(p.getRentalOption()));
    	    request.setAttribute("id", p.getId());
		}
		if(p.getId() != 0)
			request.setAttribute("id", p.getId());
		request.setAttribute("record", p);
		request.setAttribute("id_group", group);
		request.setAttribute("programs", new String[] {"", "Ski Race", "Snowboard Race", "Snowshoe Hike", "Relax"});
	    request.setAttribute("tshirts", new String[] {"", "Small", "Medium", "Large", "Extra Large"});
	    request.setAttribute("rentals", new String[] {"none", "Only skis", "Only snowboard", "Skis and boots", "Snowboard and boots"});
	    request.setAttribute("genders", new String[] {"", "Female", "Male"});
	    request.setAttribute("group", new GroupDao(c).getRecordById(Integer.parseInt(request.getParameter("id_group"))).getName());
		try{
			getServletConfig().getServletContext().getRequestDispatcher("/public/jsp/participantInt.jsp").forward(request, response);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * doPost method - maps the url that comes from a form
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param (HttpServletRequest request, HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Participant record = new Participant();
    	ParticipantDao pDao = new ParticipantDao(c);
    	RegistrationUniBzDao ruDao = new RegistrationUniBzDao(c);
		gDao = new GroupDao(c);
    	sDao = new SettingsDao(c);
    	dao = new ParticipantDao(c);
		String forward = "";
		boolean image = false;
		
//		request.setAttribute("record", session.getAttribute("record"));
//		request.setAttribute("image", session.getAttribute("image"));
    	
    	int id_group = 0;
    	
    	log.debug("id_group: " + request.getParameter("id_group"));
    	log.debug("action: " + request.getParameter("action"));
    	
    	if (request.getParameter("id_group") != null){
    		id_group = Integer.parseInt(request.getParameter("id_group").toString());
    	}
    	
    	File groupFolder = new File(getServletConfig().getServletContext().getRealPath("/")+gDao.getRecordById(id_group).getName());
    	if(!groupFolder.exists())
    		groupFolder.mkdir();
    	File subfld = new File(groupFolder.getAbsoluteFile()+File.separator+"profile");
    	if(!subfld.exists())
    		subfld.mkdir();
    	subfld = new File(groupFolder.getAbsoluteFile()+File.separator+"badges");
    	if(!subfld.exists())
    		subfld.mkdir();
    	subfld = new File(groupFolder.getAbsoluteFile()+File.separator+"studentids");
    	if(!subfld.exists())
    		subfld.mkdir();
    	
    	
    	if (request.getParameter("action") == null || request.getParameter("action").equals("edit")) {
    		//form for INSERT or UPDATE
	    	
	    	log.debug("----------------> id_group: " + request.getParameter("id_group"));
	    	
	    	String intolerances = request.getParameter("intolerances");
	    	if(intolerances != null)
	    		record.setIntolerances(intolerances);
			record.setId_group(id_group);
	    	record.setFname(request.getParameter("fname"));
	    	record.setLname(request.getParameter("lname"));
	    	record.setEmail(request.getParameter("email"));
	    	record.setGender(request.getParameter("gender"));
	    	record.setFridayProgram(pDao.getProgramID(request.getParameter("friday")));
	    	record.setDate_of_birth(request.getParameter("date_of_birth"));
	    	record.setAddress(request.getParameter("address"));
	    	record.setBirthPlace(request.getParameter("birthplace"));
	    	record.setTShirtSize(request.getParameter("tshirt"));
	    	record.setRentalOption(pDao.getRentalOptionID(request.getParameter("rental")));
	    	record.setCity(request.getParameter("city"));
	    	record.setCountry(request.getParameter("country"));
	    	record.setBirthCountry(request.getParameter("birthcountry"));
	    	record.setZip(Integer.parseInt(request.getParameter("zip")));
	    	record.setPhone(request.getParameter("phone"));
	    	String id = request.getParameter("id");
	    	log.debug("id: "+id);
//	    	if(!id.equals(""))
//	    		record.setId(Integer.parseInt(id));
	    	
	    	 // gets absolute path of the web application
	        String savePath = getServletConfig().getServletContext().getRealPath("/") + gDao.getRecordById(id_group).getName();	        
	         
	        // creates the save directory if it does not exists
	        File fileSaveDir = new File(savePath);
	        if (!fileSaveDir.exists()) {
	            fileSaveDir.mkdir();
	        }
	         
	        for (Part part: request.getParts()) {
	        	if(part.getName().equals("photo") || part.getName().equals("idphoto")){
		            String fileName = extractFileName(part);
		            log.debug("file name: "+fileName);
		            File finalFile = null;
		            //###################################################################################################
		            if(!fileName.equals("") && part.getSize() <= MAX_REQUEST_SIZE){
		            	//the user select a (new) file
		            	String subfolder = "";
		            	//###############################################################################################
		            	if(part.getName().equals("photo")){
		            		//the user select a profile photo
		            		subfolder = "profile";
		            		if(!id.isEmpty()){
			            		String url = dao.getRecordById(Integer.parseInt(id)).getPhoto();
			            		log.debug("url: (file, photo) "+url);
			            		//###########################################################################################
			            		if(url != null){
			            			//file name stored in DB --> EDITING
			            			log.debug("case: file, DB (photo)");
			            			File old = new File(savePath + File.separator + subfolder+ File.separator 
				    	        			+ dao.getRecordById(Integer.parseInt(id)).getPhoto());
			            			old.delete();
			            			record.setPhoto(fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName); 
			            		}
			            		//###########################################################################################
			            		else{
			            			//no file name stored in DB --> ADDING
			            			log.debug("case: file, no DB (photo)");
			            			record.setPhoto(fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName);
			            		}
		            		}
		            		else{
		            			record.setPhoto(fileName);
		            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName); 
		            		}
			            	part.write(finalFile.getAbsolutePath());
		            	}
		            	//###############################################################################################
		            	else{
		            		subfolder = "studentids";
		            		//the user select the student ID photo
		            		if(!id.isEmpty()){
			            		String url = dao.getRecordById(Integer.parseInt(id)).getDocument();
			            		log.debug("url: (file, studentid)  "+url);
			            		if(url != null){
			            			//file name stored in DB --> EDITING
			            			log.debug("case: file, DB (studentid)");
			            			File old = new File(savePath + File.separator + subfolder+ File.separator 
				    	        			+ dao.getRecordById(Integer.parseInt(id)).getDocument());
			            			old.delete();
			            			record.setDocument(fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName); 
			            		}
			            		//###########################################################################################
			            		else{
			            			//no file name stored in DB --> ADDING
			            			log.debug("case: file, no DB (studentid)");
			            			record.setDocument(fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName);
			            		}
		            		}
		            		else{
		            			record.setDocument(fileName);
		            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName);
		            		}
			            	part.write(finalFile.getAbsolutePath());
		            	}
		            }
		            //###################################################################################################
	            	else{
	            		//the user didn't select any file
	            		String subfolder = "";
	            		if(!id.isEmpty()){
		            		//###############################################################################################
		            		if(part.getName().equals("photo")){
		            			//profile photo case
		            			String url = dao.getRecordById(Integer.parseInt(id)).getPhoto();
		            			log.debug("url: (no file, photo) "+url);
		            			subfolder = "profile";
		            			//###########################################################################################
		            			if(url != null){
		            				//DB contains already a file name
		            				log.debug("case: no file, DB (photo)");
		            				record.setPhoto(dao.getRecordById(Integer.parseInt(id)).getPhoto());
		            			}
		            		}
		            		//###############################################################################################
		            		else{
		            			//student ID photo case
		            			String url = dao.getRecordById(Integer.parseInt(id)).getDocument();
		            			log.debug("url: (no file, studentid) "+url);
		            			subfolder = "studentids";
		            			//###########################################################################################
		            			if(url != null){
		            				//DB contains already a file name
		            				log.debug("case: no file, DB (studentid)");
		            				record.setDocument(dao.getRecordById(Integer.parseInt(id)).getDocument());
		            			}
		            		}
	            		}
	            		//###############################################################################################
	            	}
	        	}
	        	//#######################################################################################################
	        	else
	        		continue;
	        	if(part.getSize() > MAX_REQUEST_SIZE)
	        		image = true;
	        	}	
	        //###########################################################################################################

	    	log.debug("id: " + id);
	    	if(!image){
		        if(id == null || id.isEmpty()) {
		        	log.debug("INSERT");
		            dao.addRecord(id_group, record);
		            request.setAttribute("id_group", id_group);
		            ruDao.submit(request.getParameter("email"));
		        }
		        else
		        {
		        	log.debug("UPDATE");
		        	record.setId(Integer.parseInt(id));
		            dao.updateRecord(record);
		            request.setAttribute("id_group", id_group);
		            
		        }
//	        	session.setAttribute("record", record);
//	        	session.setAttribute("image", image);
	    	}
	        log.debug("forward: " + forward);
	        response.sendRedirect("../");
	    }
	}   
	
	 private String extractFileName(Part part) {
	        String contentDisp = part.getHeader("content-disposition");
	        String[] items = contentDisp.split(";");
	        for (String s : items) {
	            if (s.trim().startsWith("filename")) {
	                return s.substring(s.indexOf("=") + 2, s.length()-1);
	            }
	        }
	        return "";
	    }
	
}
