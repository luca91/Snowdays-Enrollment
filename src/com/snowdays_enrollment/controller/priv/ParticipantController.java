package com.snowdays_enrollment.controller.priv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sound.midi.SysexMessage;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import sun.util.locale.StringTokenIterator;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.RegistrationExternalsDao;
import com.snowdays_enrollment.dao.SettingsDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.model.RegistrationExternal;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.Email;
import com.snowdays_enrollment.tools.FileUpload;


/**
 * Servlet implementation class ParticipantController
 */
@WebServlet(urlPatterns = {
		"/private/participantList.html", 
		"/private/participant.jsp", 
		"/private/participantAdd",
		"/private/participantDelete",
		"/private/participantApprove"		
		})
@MultipartConfig
public class ParticipantController extends HttpServlet {
	
	// commons logging references
	static Logger log = Logger.getLogger(ParticipantController.class.getName());
	
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
	
	private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/participant.jsp";
    private static String LIST_USER = "/participantList.jsp";
    private static String UNAUTHORIZED_PAGE = "/private/jsp/errors/unauthorized.jsp";
    private String forward="";
    private User systemUser;
    private String action;
    private int id_group;
    HttpSession session;
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
		dao = new ParticipantDao(c);
		gDao = new GroupDao(c);
		sDao =  new SettingsDao(c);
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
    	log.trace("START");
    	session = request.getSession(true);
    	c = (Connection) session.getAttribute("DBConnection");
    	gDao = new GroupDao(c);
    	sDao = new SettingsDao(c);
    	dao = new ParticipantDao(c);
		UserDao ud = new UserDao(c);
		systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		session.setMaxInactiveInterval(1200);

    	log.debug("id_group: " + request.getParameter("id_group"));
    	
    	request.setAttribute("groups", gDao.getAllRecords());
    	Group g = null;
    		
    	
    	if (request.getParameter("id_group") != null){
    		log.debug("id_group is not null!");
    		id_group = Integer.parseInt(request.getParameter("id_group"));
    		log.debug("id_group: "+id_group);
        	g = gDao.getRecordById(id_group);
        	request.setAttribute("group", g);
        	request.setAttribute("groupName", gDao.getRecordById(id_group).getName());
        	request.setAttribute("nrEnrolledParticipant", gDao.getNrEnrolledParticipant(id_group));
    	}
    	
    	request.setAttribute("id_group", request.getParameter("id_group"));
    	
    	action = request.getParameter("action");
        if (action == null){
        	log.debug("action is NULL");
        	action="";
        }
        
        if (action.equalsIgnoreCase("delete")){
           log.debug("action: DELETE - " + action);
           deleteParticipant(request, response);
        }
        
        else if (action.equalsIgnoreCase("edit")){
        	if(request.getParameter("id") == null){
        		log.debug("action: INSERT - " + action);
            	insertParticipant(request, response);
        	}
        	else{
	        	log.debug("action: UPDATE - " + action);
	        	updateParticipant(request, response);
        	}
        }

        else if (action.equalsIgnoreCase("insert")){
            log.debug("action: INSERT - " + action);
            insertParticipant(request, response);
        }
        
        else if (action.equals("conclude")){
        	log.debug("action: CONCLUDE - " + action);
        	g.setIsBlocked(true);
        	gDao.updateRecord(g);
        }

        //list record using a group_id
        else if (action.equalsIgnoreCase("listRecord")){
        	request.setAttribute("groupMaxNumber", sDao.getSetting("maxpergroup"));
        	listParticipantsByGroup(request, response);
        }
        //list records without an id_group
        else {
        	request.setAttribute("groupMaxNumber", sDao.getSetting("maxpergroup"));
            if (systemUser.getRole().equals("admin")){
                log.debug("admin");
                forward = LIST_USER;
                request.setAttribute("records", dao.getAllRecords());
                request.setAttribute("groups", gDao.getAllRecords());
            }
            else if (systemUser.getRole().equals("group_manager")){
                forward = LIST_USER;
                GroupDao gDao = new GroupDao(c);
                id_group = gDao.getGroupByIDGroupReferent(systemUser.getId());
                System.out.println(id_group);
                request.setAttribute("id_group", id_group);
                g = gDao.getRecordById(id_group);
                request.setAttribute("groupMaxNumber", g.getGroupMaxNumber());
                request.setAttribute("nrEnrolledParticipant", g.getActualParticipantNumber());
                request.setAttribute("records", dao.getAllRecordsById_group(id_group));
                request.setAttribute("blocked", gDao.getRecordById(id_group).getIsBlocked());
                request.setAttribute("groupName", gDao.getRecordById(id_group).getName());
            }
        }
// #########################################################################################     	
        log.debug("forward: " + forward);
        log.debug("action: " + action);
    	        
		forward = "/private/jsp" + forward;

		if(action.equals("delete") || action.equals("conclude"))
			response.sendRedirect("participantList.html");
		else{
			try {
				getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
				} 
			catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * doPost method - maps the url that comes from a form
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param (HttpServletRequest request, HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.trace("START");
    	session = request.getSession(true);
    	c = (Connection) session.getAttribute("DBConnection");
    	Participant record = new Participant();
    	ParticipantDao pDao = new ParticipantDao(c);
    	
		UserDao ud = new UserDao(c);
		gDao = new GroupDao(c);
    	sDao = new SettingsDao(c);
    	dao = new ParticipantDao(c);
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		String forward = "";
		boolean image = false;
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);	
		session.setMaxInactiveInterval(1200);
		request.setAttribute("record", session.getAttribute("record"));
		request.setAttribute("image", session.getAttribute("image"));
		session.removeAttribute("record");
		session.removeAttribute("image");
	  
        if (systemUser.getRole().equals("admin")){
            GroupDao gd = new GroupDao(c);
            request.setAttribute("groups", gd.getAllRecords());
        }
        else if (systemUser.getRole().equals("group_manager")){
            GroupDao gd = new GroupDao(c);
            request.setAttribute("groups", gd.getRecordsByGroupReferentID(systemUser.getId()));
        }
    	
    	int id_group = 0;
    	
    	log.debug("id_group: " + request.getParameter("id_group"));
    	log.debug("action: " + request.getParameter("action"));
    	
    	if (request.getParameter("id_group") != null){
    		id_group = Integer.parseInt(request.getParameter("id_group").toString());
    	}
    	
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
	    	record.setTShirtSize(request.getParameter("tshirt"));
	    	record.setRentalOption(pDao.getRentalOptionID(request.getParameter("rental")));
	    	String id = request.getParameter("id");
	    	log.debug("id: "+id);
	    	if(id != null)
	    		record.setId(Integer.parseInt(id));
	    	
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
			            		String url = dao.getRecordById(Integer.parseInt(id)).getStudentID();
			            		log.debug("url: (file, studentid)  "+url);
			            		if(url != null){
			            			//file name stored in DB --> EDITING
			            			log.debug("case: file, DB (studentid)");
			            			File old = new File(savePath + File.separator + subfolder+ File.separator 
				    	        			+ dao.getRecordById(Integer.parseInt(id)).getStudentID());
			            			old.delete();
			            			record.setStudentID(fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName); 
			            		}
			            		//###########################################################################################
			            		else{
			            			//no file name stored in DB --> ADDING
			            			log.debug("case: file, no DB (studentid)");
			            			record.setStudentID(fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + fileName);
			            		}
		            		}
		            		else{
		            			record.setStudentID(fileName);
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
		            			String url = dao.getRecordById(Integer.parseInt(id)).getStudentID();
		            			log.debug("url: (no file, studentid) "+url);
		            			subfolder = "studentids";
		            			//###########################################################################################
		            			if(url != null){
		            				//DB contains already a file name
		            				log.debug("case: no file, DB (studentid)");
		            				record.setStudentID(dao.getRecordById(Integer.parseInt(id)).getStudentID());
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
		            RegistrationExternal r = new RegistrationExternal();
		            int idPar = dao.getIDByParticipant(record.getFname(), record.getLname(), id_group);
		            r.setParticipantID(idPar);
		            r.setGroupName(new GroupDao().getRecordById(id_group).getName());
		            r.setTime(dao.getRecordById(idPar).getRegistrationTime());
		            RegistrationExternalsDao reDao = new RegistrationExternalsDao(c);
		            reDao.addRegistration(r);
		        }
		        else
		        {
		        	log.debug("UPDATE");
		        	record.setId(Integer.parseInt(id));
		            dao.updateRecord(record);
		            request.setAttribute("id_group", id_group);
		            
		        }
		        forward = "participantList.html?action=listRecord&id_group=" + id_group;
	        }
	    	else{
	    		if(request.getParameter("action").equals("edit"))
	    			forward = "participant.jsp?action=edit&id_group="+id_group;
	    		else
	    			forward = "participant.jsp?action=insert&id_group="+id_group;
	        	session.setAttribute("record", record);
	        	session.setAttribute("image", image);
	    	}
	        log.debug("forward: " + forward);
	        response.sendRedirect(forward);
	    }
    	
    	log.trace("END");
	}

    private boolean sendEmail(String to, Group g, String serverName) throws ParseException{ 
    	log.debug("address: " + to);

//		DateFormat df = new SimpleDateFormat("dd MMM yyyy");
//		Date fStart = new SimpleDateFormat("yyyy-MM-dd").parse(e.getStart());
//		log.debug("fStart: " + df.format(fStart));
//		Date fEnd = new SimpleDateFormat("yyyy-MM-dd").parse(e.getEnd());
//		log.debug("fEnd: " + df.format(fEnd));
//		Date fenrollment_start = new SimpleDateFormat("yyyy-MM-dd").parse(e.getEnrollment_start());
//		log.debug("fenrollment_start: " + df.format(fenrollment_start));
//		Date fenrollment_end = new SimpleDateFormat("yyyy-MM-dd").parse(e.getEnrollment_end());
//		log.debug("fenrollment_end: " + df.format(fenrollment_end)); 	
		
		
        log.debug("TO: " + to);
        String subject = "";
        
        String message = "";
//        message += "You are invited to the " + e.getName() + " event.\n";
//        message += "The event will take place from " + df.format(fStart) + " to " + df.format(fEnd) + "\n";
        message += "\nTo enroll to the event you have to click on the following link and fill up the registration form:\n";
        message += "\n" +
        			"http://" + serverName + ":8080/snowdays_enrollment/public/enrollmentForm.html?id_group=" + 
        			g.getId() + 
        			"&email=" + to 
        			+ "\n";
//        message += "\nDue date: " + df.format(fenrollment_start) +"\n";
//        message += "\nEnrollment until: " + df.format(fenrollment_end) + "\n";
        message += "\n\nThe staff";
       
        Email em = new Email();
        return em.sendEmail(to, subject, message);
    }
    
    public void insertParticipant(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException{
    	request.removeAttribute("record");
    	Participant record = (Participant) session.getAttribute("record");
    	if(record != null){
    		request.setAttribute("record", session.getAttribute("record"));
    		request.setAttribute("selGen", record.getGender());
    	    request.setAttribute("selPro", dao.getProgramByID(record.getFridayProgram()));
    	    request.setAttribute("selTS", record.getTShirtSize());
    	    request.setAttribute("selRen", dao.getRentalByID(record.getRentalOption()));
    	    request.setAttribute("id", record.getId());
    	}
   		forward = INSERT_OR_EDIT;
        request.setAttribute("programs", new String[] {"", "Ski Race", "Snowboard Race", "Snowshoe Hike", "Relax"});
        request.setAttribute("tshirts", new String[] {"", "Small", "Medium", "Large", "Extra Large"});
        request.setAttribute("rentals", new String[] {"none", "Only skis", "Only snowboard", "Skis and boots", "Snowboard and boots"});
        request.setAttribute("genders", new String[] {"", "Female", "Male"});
        request.setAttribute("group", new GroupDao(c).getRecordById(Integer.parseInt(request.getParameter("id_group"))).getName());
    }
    
    public void deleteParticipant(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException{
    	 int id = Integer.parseInt(request.getParameter("id"));
         
         List<Integer> listOfAuthorizedId = dao.canBeChangedBy(id);
         log.debug("before if");
         if (listOfAuthorizedId.contains(systemUser.getId())){
        	log.debug("inside if");
        	log.debug("id: "+id);
        	File profile = new File(getServletConfig().getServletContext().getRealPath("/") + 
        			"profile" + File.separator + dao.getRecordById(id).getPhoto());
        	log.debug(profile.getAbsoluteFile());
        	File studentID = new File(getServletConfig().getServletContext().getRealPath("/") + 
        			"studentids" + File.separator + dao.getRecordById(id).getStudentID());
        	log.debug(studentID.getAbsoluteFile());
        	profile.delete();
        	studentID.delete();
         	dao.deleteRecord(id);
         	forward = LIST_USER;
         	request.setAttribute("records", dao.getAllRecords());  
         	GroupDao ed = new GroupDao();
         	request.setAttribute("groups", ed.getAllRecords());
         }
         else {
         	forward = UNAUTHORIZED_PAGE;
         }
    }
    
    public void updateParticipant(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException{
    	  log.debug("action: EDIT - " + action);
          log.debug("systemUser: " + systemUser.getId());
          int id = Integer.parseInt(request.getParameter("id"));
          System.out.println(id);

          List<Integer> listOfAuthorizedId = dao.canBeChangedBy(id);
          if (listOfAuthorizedId.contains(systemUser.getId())){
          	log.debug("systemUser can modify the record");
              Participant record = dao.getRecordById(id);
              request.setAttribute("record", record);   
              request.setAttribute("programs", new String[] {"", "Ski Race", "Snowboard Race", "Snowshoe Hike", "Relax"});
              request.setAttribute("tshirts", new String[] {"", "Small", "Medium", "Large", "Extra Large"});
              request.setAttribute("rentals", new String[] {"none", "Only skis", "Only snowboard", "Skis and boots", "Snowboard and boots"});
              request.setAttribute("id", record.getId());
              request.setAttribute("genders", new String[] {"", "Female", "Male"});
              System.out.println(record.getGender());
              System.out.println(dao.getProgramByID(record.getFridayProgram()));
              System.out.println(record.getTShirtSize());
              System.out.println(dao.getRentalByID(record.getRentalOption()));
              request.setAttribute("selGen", record.getGender());
              request.setAttribute("selPro", dao.getProgramByID(record.getFridayProgram()));
              request.setAttribute("selTS", record.getTShirtSize());
              request.setAttribute("selRen", dao.getRentalByID(record.getRentalOption()));
              forward = INSERT_OR_EDIT;
          }
          else {
          	log.debug("systemUser can NOT modify the record");
          	forward = UNAUTHORIZED_PAGE;
          }
    }
    
    public void listParticipantsByGroup(HttpServletRequest request, HttpServletResponse response){
    	  log.debug("action: listRecord - " + action);
          if (systemUser.getRole().equals("admin")){
              log.debug("admin");
              forward = LIST_USER;
              request.setAttribute("records", dao.getAllRecordsById_group(id_group));
              GroupDao gd = new GroupDao();
              session.setAttribute("groups", gd.getAllRecords());
              request.setAttribute("id_group", id_group);
          }
          else if (systemUser.getRole().equals("group_manager")){
              forward = LIST_USER;
              GroupDao gDao = new GroupDao(c);
              id_group = gDao.getGroupByIDGroupReferent(systemUser.getId());
              System.out.println(id_group);
              request.setAttribute("id_group", id_group);
              Group g = gDao.getRecordById(id_group);
              request.setAttribute("groupMaxNumber", g.getGroupMaxNumber());
              request.setAttribute("nrEnrolledParticipant", g.getActualParticipantNumber());
              request.setAttribute("records", dao.getAllRecordsById_group(id_group));
          }   
    }
    
    public ArrayList<String> parseIntolerances(String intolerances){
    	StringTokenizer tk = new StringTokenizer(intolerances, ",");
    	ArrayList<String> result = new ArrayList<String>();
    	while(tk.hasMoreTokens()){
    		result.add(tk.nextToken());
    	}
    	
    	return result;
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

