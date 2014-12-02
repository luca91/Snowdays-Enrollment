package com.snowdays_enrollment.controller.priv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
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

import org.apache.catalina.ha.session.BackupManager;
import org.apache.commons.io.FileUtils;
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
import com.snowdays_enrollment.dao.RegistrationUniBzDao;
import com.snowdays_enrollment.dao.SettingsDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.model.RegistrationExternal;
import com.snowdays_enrollment.model.RegistrationUniBz;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.BackupPhoto;
import com.snowdays_enrollment.tools.DBConnection;
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
    	try {
			if(c.isClosed())
				c = new DBConnection().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	gDao = new GroupDao(c);
    	sDao = new SettingsDao(c);
    	dao = new ParticipantDao(c);
		UserDao ud = new UserDao(c);
		systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);

    	log.debug("id_group: " + request.getParameter("id_group"));
    	
    	request.setAttribute("groups", gDao.getAllRecords());
    	Group g = null;
    	
    	int page = -1;
    	if(request.getParameter("page") != null)
    		page = Integer.parseInt(request.getParameter("page"));
    	
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
//        	request.setAttribute("groupMaxNumber", sDao.getSetting("maxpergroup"));
        	listParticipantsByGroup(request, response);
        }
        //list records without an id_group
        else {
            if (systemUser.getRole().equals("admin")){
            	request.setAttribute("groupMaxNumber", sDao.getSetting("maxpergroup"));
	        	ArrayList<Participant> all = (ArrayList<Participant>) dao.getAllRecords();
	        	System.out.println("size: " + all.size());
	            int pages1 = ((all.size())/25) + 1;
	            ArrayList<Integer> pages2 = new ArrayList<Integer>();
	            for(int i = 1; i <= pages1; i++)
	            	 pages2.add(i);
	            System.out.println(pages2.toString());
	            request.setAttribute("pages", pages2);
                log.debug("admin");
                forward = LIST_USER;
                if(page != -1)
                	request.setAttribute("records", setRecordsByPage(page, all));
                request.setAttribute("groups", gDao.getAllRecords());
            }
            else if (systemUser.getRole().equals("group_manager")){
                forward = LIST_USER;
                id_group = gDao.getGroupByIDGroupReferent(systemUser.getId());
                System.out.println(id_group);
                request.setAttribute("id_group", id_group);
                g = gDao.getRecordById(id_group);
                System.out.println(g.getGroupMaxNumber());
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
			response.sendRedirect("participantList.html?page=1");
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
    	try {
			if(c.isClosed())
				c = new DBConnection().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Participant record = new Participant();
    	ParticipantDao pDao = new ParticipantDao(c);
    	
		UserDao ud = new UserDao(c);
		gDao = new GroupDao(c);
    	sDao = new SettingsDao(c);
    	dao = new ParticipantDao(c);
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		String forward = "";
		boolean image = false;
		
		session.removeAttribute("image");
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);	
		request.setAttribute("record", session.getAttribute("record"));
		session.removeAttribute("record");
		
	  
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
    	
    	File groupFolder = new File(getServletConfig().getServletContext().getRealPath("/") + gDao.getRecordById(id_group).getName());
    	if(!groupFolder.exists())
    		groupFolder.mkdirs();
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
	    	String id = "";
	    	
	    	String intolerances = request.getParameter("intolerances");
	    	if(gDao.getRecordById(id_group).getName().equals("Host") || gDao.getRecordById(id_group).getName().equals("Sponsor") || gDao.getRecordById(id_group).getName().equals("STAFF")){
	    		record.setId_group(id_group);
		    	record.setFname(request.getParameter("fname"));
		    	record.setLname(request.getParameter("lname"));
		    	record.setAddress(request.getParameter("address"));
		    	record.setZip(request.getParameter("zip"));
		    	record.setDocument(request.getParameter("photo"));
		    	record.setCity(request.getParameter("city"));
		    	record.setCountry(request.getParameter("country"));
		    	record.setEmail(request.getParameter("email"));
		    	record.setGender(request.getParameter("gender"));
		    	record.setTShirtSize("none");
		    	record.setFridayProgram(3);
		    	record.setRentalOption(5);
		    	id = request.getParameter("id");
	    	}
	    	else{
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
		    	record.setAddress(request.getParameter("address"));
		    	record.setBirthPlace(request.getParameter("birthplace"));
		    	record.setCity(request.getParameter("city"));
		    	record.setCountry(request.getParameter("country"));
		    	record.setBirthCountry(request.getParameter("birthcountry"));
		    	record.setZip(request.getParameter("zip"));
		    	record.setDocument(request.getParameter("idphoto"));
		    	if(gDao.getRecordById(id_group).getName().equals("UNIBZ"));
		    		record.setPhone(request.getParameter("phone"));
		    	id = request.getParameter("id");
		    	log.debug("id: "+id);
	    	}
	    	
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
			            			record.setPhoto(record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName); 
			            		}
			            		//###########################################################################################
			            		else{
			            			//no file name stored in DB --> ADDING
			            			log.debug("case: file, no DB (photo)");
			            			record.setPhoto(record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
			            		}
		            		}
		            		else{
		            			record.setPhoto(record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" +fileName);
		            			finalFile = new File(savePath + File.separator + subfolder + File.separator + record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName); 
		            		}
			            	part.write(finalFile.getAbsolutePath());
//			            	BackupPhoto bu = new BackupPhoto(gDao.getRecordById(id_group).getName(), getServletConfig().getServletContext().getRealPath("/"), finalFile, "profile");
//			            	bu.createBackUpFolder();
//			            	bu.createGroupBackUpFolders();
//			            	bu.backupSinglePhoto();
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
			            			record.setDocument(record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName); 
			            		}
			            		//###########################################################################################
			            		else{
			            			//no file name stored in DB --> ADDING
			            			log.debug("case: file, no DB (studentid)");
			            			record.setDocument(record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
			            			finalFile = new File(savePath + File.separator + subfolder + File.separator + record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
			            		}
		            		}
		            		else{
		            			record.setDocument(record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
		            			finalFile = new File(savePath + File.separator + subfolder + File.separator + record.getFname().toUpperCase() + "_" + record.getLname().toUpperCase() + "_" + fileName);
		            		}
			            	part.write(finalFile.getAbsolutePath());
//			            	BackupPhoto bu = new BackupPhoto(gDao.getRecordById(id_group).getName(), getServletConfig().getServletContext().getRealPath("/"), finalFile, "studentids");
//			            	bu.createBackUpFolder();
//			            	bu.createGroupBackUpFolders();
//			            	bu.backupSinglePhoto();
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
	    	System.out.println("Image: "+ image);
	    	if(!image){
	    		session.removeAttribute("image");
	    		session.setAttribute("image", false);
	    		request.setAttribute("image", false);
		        if(id == null || id.isEmpty()) {
		        	log.debug("INSERT");
		            dao.addRecord(id_group, record);
		            request.setAttribute("id_group", id_group);
		            if(!((id == null || id.isEmpty()) 
		            		&& (gDao.getRecordById(id_group).getName().equals("UNIBZ") 
		            		|| gDao.getRecordById(id_group).getName().equals("Host") 
		            		|| gDao.getRecordById(id_group).getName().equals("Alumni") 
		            		|| gDao.getRecordById(id_group).getName().equals("STAFF")
		            		|| gDao.getRecordById(id_group).getName().equals("Sponsor")))){
			            RegistrationExternal r = new RegistrationExternal();
			            int idPar = dao.getIDByParticipant(record.getFname(), record.getLname(), id_group);
			            r.setParticipantID(idPar);
			            r.setGroupName(gDao.getRecordById(id_group).getName());
			            r.setTime(dao.getRecordById(idPar).getRegistrationTime());
			            Group g = gDao.getRecordById(record.getId_group()); 
			            if(g.getActualParticipantNumber() == 0){
			            	g.setFirstParticipantRegisteredID(idPar);
			            	gDao.updateRecord(g);
			            }			            	
			            RegistrationExternalsDao reDao = new RegistrationExternalsDao(c);
			            reDao.addRegistration(r);
		            }
		            else if(gDao.getRecordById(id_group).getName().equals("STAFF") || gDao.getRecordById(id_group).getName().equals("Sponsor")){
			        	RegistrationUniBzDao ruDao = new RegistrationUniBzDao();
			        	RegistrationUniBz ru = new RegistrationUniBz();
			        	if(ruDao.getRegistrationByEmail(record.getEmail()) == null){
				        	ru.setName(record.getFname());
				        	ru.setSurname(record.getLname());
				        	ru.setEmail(record.getEmail());
				        	ru.setStatus("submit");
				        	ru.setGroup(gDao.getRecordById(id_group).getName());
				        	ruDao.addRecord(ru);
			        	}
			        	else
			        		ruDao.submit(record.getEmail());
			        	
			        }
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
	    		if(id == null || id.isEmpty()){
		    		record.setPhoto(null);
		    		record.setDocument(null);
	    		}
	        	session.setAttribute("record", record);
	        	System.out.println("ID foto: " + record.getId());
	        	session.setAttribute("image", image);
	    	}
	        log.debug("forward: " + forward);
	        response.sendRedirect(forward);
	    }
	}
    
    public void insertParticipant(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException{
    	request.removeAttribute("record");
    	Participant record = (Participant) session.getAttribute("record");
    	if(gDao.getRecordById(id_group).getName().equals("Host") || gDao.getRecordById(id_group).getName().equals("STAFF") || gDao.getRecordById(id_group).getName().equals("Sponsor")) {
    		if(record != null){
    			request.setAttribute("record", session.getAttribute("record"));
	    		request.setAttribute("selGen", record.getGender());
//	    		request.setAttribute("id", record.getId());
    		}
    		request.setAttribute("genders", new String[] {"", "Female", "Male"});
    		forward = "/host.jsp";
    	}
    	else{
	    	if(record != null){
	    		request.setAttribute("record", session.getAttribute("record"));
	    		request.setAttribute("selGen", record.getGender());
	    	    request.setAttribute("selPro", dao.getProgramByID(record.getFridayProgram()));
	    	    request.setAttribute("selTS", record.getTShirtSize());
	    	    request.setAttribute("selRen", dao.getRentalByID(record.getRentalOption()));
	    	    request.setAttribute("genders", new String[] {"", "Female", "Male"});
//	    	    request.setAttribute("id", record.getId());
	    	}
	    	  if(id_group != 0 && (gDao.getRecordById(id_group).getName().equals("UNIBZ") || gDao.getRecordById(id_group).getName().equals("Alumni")))
	          	forward = "/participantIntPrivate.jsp";
	          else
	        	  forward = INSERT_OR_EDIT;
	        request.setAttribute("programs", new String[] {"", "Ski Race", "Snowboard Race", "Snowshoe Hike", "Relax"});
	        request.setAttribute("tshirts", new String[] {"", "Small", "Medium", "Large", "Extra Large"});
	        request.setAttribute("rentals", new String[] {"none", "Only skis", "Only snowboard", "Skis and boots", "Snowboard and boots"});
	        request.setAttribute("genders", new String[] {"", "Female", "Male"});
	        request.setAttribute("group", new GroupDao(c).getRecordById(Integer.parseInt(request.getParameter("id_group"))).getName());
        }
        session.removeAttribute("record");
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
        			"studentids" + File.separator + dao.getRecordById(id).getDocument());
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
        	  if(gDao.getRecordById(id_group).getName().equals("Host") || gDao.getRecordById(id_group).getName().equals("Sponsor") || gDao.getRecordById(id_group).getName().equals("STAFF")){
      			request.setAttribute("record", record);
  	    		request.setAttribute("selGen", record.getGender());
  	    		request.setAttribute("id", record.getId());
  	    		request.setAttribute("genders", new String[] {"", "Female", "Male"});
  	    		forward = "/host.jsp";
        	  }
        	  else{
	              request.setAttribute("record", record);   
	              request.setAttribute("programs", new String[] {"", "Ski Race", "Snowboard Race", "Snowshoe Hike", "Relax"});
	              request.setAttribute("tshirts", new String[] {"", "Small", "Medium", "Large", "Extra Large"});
	              request.setAttribute("rentals", new String[] {"none", "Only skis", "Only snowboard", "Skis and boots", "Snowboard and boots"});
	              request.setAttribute("id", record.getId());
	              request.setAttribute("genders", new String[] {"", "Female", "Male"});
	              request.setAttribute("selGen", record.getGender());
	              request.setAttribute("selPro", dao.getProgramByID(record.getFridayProgram()));
	              request.setAttribute("selTS", record.getTShirtSize());
	              request.setAttribute("selRen", dao.getRentalByID(record.getRentalOption()));
	              if(id_group != 0 && (gDao.getRecordById(id_group).getName().equals("UNIBZ") || gDao.getRecordById(id_group).getName().equals("Alumni") || gDao.getRecordById(id_group).getName().equals("Host")))
	            	  forward = "/participantIntPrivate.jsp";
	              else
	            	  forward = INSERT_OR_EDIT;
        	  }
          }
          else {
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
              request.setAttribute("groupMaxNumber", gDao.getRecordById(id_group).getGroupMaxNumber());
              request.setAttribute("nrEnrolledParticipant", gDao.getRecordById(id_group).getActualParticipantNumber());
          }
          else if (systemUser.getRole().equals("group_manager")){
              forward = LIST_USER;
              GroupDao gDao = new GroupDao(c);
              id_group = gDao.getGroupByIDGroupReferent(systemUser.getId());
              System.out.println(id_group);
              request.setAttribute("id_group", id_group);
              Group g = gDao.getRecordById(id_group);
              System.out.println(g.getGroupMaxNumber());
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
    
    public ArrayList<Participant> setRecordsByPage(int page, ArrayList<Participant> all){
    	ArrayList<Participant> result = new ArrayList<Participant>();
    	System.out.println("All: " + all.toString());
    	int begin = 25*(page-1);
    	int end = (25*page)-1;
    	System.out.println("begin: " + begin);
    	System.out.println("end: " + end);
    	System.out.println("Size: " + all.size());
    	for(int i = begin; (i <= end && i < all.size());i++)
    		if(all.get(i) != null)
    		result.add(all.get(i));
    	return result;
    }
}

