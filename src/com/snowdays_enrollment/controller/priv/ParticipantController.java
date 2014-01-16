package com.snowdays_enrollment.controller.priv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
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
    
    /**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private ParticipantDao dao;   
	
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
    	log.trace("START");

		UserDao ud = new UserDao();
		systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		SettingsDao sDao = new SettingsDao();
		
		session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		session.setMaxInactiveInterval(1200);

    	log.debug("id_group: " + request.getParameter("id_group"));
    	
    	GroupDao gdao = new GroupDao();
    	request.setAttribute("groups", gdao.getAllRecords());
    		
    	
    	if (request.getParameter("id_group") != null){
    		log.debug("id_group is not null!");
    		id_group = Integer.parseInt(request.getParameter("id_group"));
    		log.debug("id_group: "+id_group);
        	Group g = gdao.getRecordById(id_group);
        	request.setAttribute("group", g);
        	request.setAttribute("nrEnrolledParticipant", gdao.getNrEnrolledParticipant(id_group));
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
        	updateParticipant(request, response);
        }

        else if (action.equalsIgnoreCase("insert")){
            log.debug("action: INSERT - " + action);
            insertParticipant(request, response);
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
                GroupDao gd = new GroupDao();
                request.setAttribute("groups", gd.getAllRecords());
            }
            else if (systemUser.getRole().equals("group_manager")){
                forward = LIST_USER;
                GroupDao gDao = new GroupDao();
                id_group = gDao.getGroupByIDGroupReferent(systemUser.getId());
                System.out.println(id_group);
                request.setAttribute("id_group", id_group);
                Group g = gDao.getRecordById(id_group);
                request.setAttribute("groupMaxNumber", g.getGroupMaxNumber());
                request.setAttribute("nrEnrolledParticipant", g.getActualParticipantNumber());
                request.setAttribute("records", dao.getAllRecordsById_group(id_group));
                request.setAttribute("blocked", gDao.getRecordById(id_group).isBlocked());
            }
        }
// #########################################################################################     	
        log.debug("forward: " + forward);
        log.debug("action: " + action);
    	        
		forward = "/private/jsp" + forward;

		if(action.equals("delete"))
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
    	Participant record = new Participant();
    	ParticipantDao pDao = new ParticipantDao();
    	
		UserDao ud = new UserDao();
		GroupDao gDao = new GroupDao();
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		
		HttpSession session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);	
	  
        if (systemUser.getRole().equals("admin")){
            GroupDao gd = new GroupDao();
            request.setAttribute("groups", gd.getAllRecords());
        }
        else if (systemUser.getRole().equals("group_manager")){
            GroupDao gd = new GroupDao();
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
	    		record.setIntolerances(parseIntolerances(intolerances));
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
	    	String directory = getServletConfig().getServletContext().getRealPath("/") + gDao.getRecordById(id_group).getName() + "/profile/";
	    	String photo = uploadFile(request, directory);
	    	record.setPhotoURL(photo);
	    	log.debug("filename: "+ record.getPhotoURL());
	        
	    	log.debug("id: " + id);
	    	
	        if(id == null || id.isEmpty()) {
	        	log.debug("INSERT");
	            dao.addRecord(id_group, record);
	            request.setAttribute("id_group", id_group);
	            RegistrationExternal r = new RegistrationExternal();
	            int idPar = dao.getIDByParticipant(record.getFname(), record.getLname(), id_group);
	            r.setParticipantID(idPar);
	            r.setGroupName(new GroupDao().getRecordById(id_group).getName());
	            r.setTime(dao.getRecordById(idPar).getRegistrationTime());
	            RegistrationExternalsDao reDao = new RegistrationExternalsDao();
	            reDao.addRegistration(r);
	        }
	        else
	        {
	        	log.debug("UPDATE");
	        	record.setId(Integer.parseInt(id));
	            dao.updateRecord(record);
	            request.setAttribute("id_group", id_group);
	            
	        }
	        
	        String forward = "participantList.html?action=listRecord&id_group=" + id_group;
	        log.debug("forward: " + forward);

	        response.sendRedirect(forward);
			
    	}
    	else if (request.getParameter("action").equals("invite") ){
    		//invite participant
    		log.debug("Invite Participant");
    		
    		String[] result = request.getParameter("listTo").toString().split(";", -1);
    		int count = 0;
    		
    		GroupDao gd = new GroupDao();
    		Group g = gd.getRecordById(id_group);
    		
    		for (int i = 0; i < result.length; i++){
    			try {
					if (sendEmail(result[i], g, request.getServerName())){
						count++;
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			log.debug(result[i]);
    		}

	        String forward = "participantList.html?action=listRecord&id_group=" + id_group + "&count=" + count + "&showCount=y";
	        log.debug("forward: " + forward);
	        
	        response.sendRedirect(forward);
    	}
    	else if (request.getParameter("action").equals("approve") ){
    		//approve enrollment
    		log.debug("APPROVE");
    		log.debug("action: " + request.getParameter("action"));
	        
	        ParticipantDao pd = new ParticipantDao();
	        List<Participant>  list = pd.getAllRecordsById_group(id_group);
	        Participant p = new Participant();
	        
	        for (int i = 0; i < list.size(); i++){
	        	p = list.get(i);
	        	if (!p.isApproved()){
	        		pd.approve(p.getId(), true); 
	        	}
	        }
	        
	        String forward = "participantList.html?action=listRecord&id_group=" + id_group;
	        log.debug("forward: " + forward);
        
	        response.sendRedirect(forward);
    		
    	}
    	else if (request.getParameter("action").equals("disapprove") ){
    		//approve enrollment
    		log.debug("DISAPPROVE");
    		log.debug("action: " + request.getParameter("action"));
	        
	        ParticipantDao pd = new ParticipantDao();
	        List<Participant>  list = pd.getAllRecordsById_group(id_group);
	        Participant p = new Participant();
	        
	        for (int i = 0; i < list.size(); i++){
	        	p = list.get(i);
	        	if (p.isApproved()){
	        		pd.approve(p.getId(), false); 
	        	}
	        }
	        
	        String forward = "participantList.html?action=listRecord&id_group=" + id_group;
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
        forward = INSERT_OR_EDIT;
        request.setAttribute("programs", new String[] {"", "Ski Race", "Snowboard Race", "Snowshoe Hike", "Relax"});
        request.setAttribute("tshirts", new String[] {"", "Small", "Medium", "Large", "Extra Large"});
        request.setAttribute("rentals", new String[] {"none", "Only skis", "Only snowboard", "Skis and boots", "Snowboard and boots"});
        request.setAttribute("genders", new String[] {"", "Female", "Male"});
        request.setAttribute("group", new GroupDao().getRecordById(Integer.parseInt(request.getParameter("id_group"))).getName());
    }
    
    public void deleteParticipant(HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, ServletException{
    	 int id = Integer.parseInt(request.getParameter("id"));
         
         List<Integer> listOfAuthorizedId = dao.canBeChangedBy(id);
         log.debug("before if");
         if (listOfAuthorizedId.contains(systemUser.getId())){
        	log.debug("inside if");
        	log.debug("id: "+id);
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
              GroupDao gDao = new GroupDao();
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
    
    public String uploadFile(HttpServletRequest request, String directory) throws IOException, ServletException{
		log.trace("START");
		System.out.println("uploadFile");
		 DiskFileItemFactory factory = new DiskFileItemFactory();
		 String result = "";

	        // Sets the size threshold beyond which files are written directly to
	        // disk.
//	        factory.setSizeThreshold(MAX_MEMORY_SIZE);

	        // Sets the directory used to temporarily store files that are larger
	        // than the configured size threshold. We use temporary directory for
	        // java
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	        // constructs the folder where uploaded file will be stored
	        String uploadFolder = getServletContext().getRealPath("/")
	                + File.separator + directory;

	        // Create a new file upload handler
	        ServletFileUpload upload = new ServletFileUpload(factory);

	        // Set overall request size constraint
	        upload.setSizeMax(MAX_REQUEST_SIZE);
	        
		try {
	            // Parse the request
			List<FileItem> items = upload.parseRequest(request);
           Iterator<FileItem> iter = items.iterator();
           System.out.println(iter.hasNext());
           while (iter.hasNext()) {
           	FileItem item = (FileItem) iter.next();
           	System.out.println("item name: " +item.getName());

               if (!item.isFormField()) {
                   String fileName = new File(item.getName()).getName();
                   String filePath = uploadFolder + File.separator + fileName;
                   File uploadedFile = new File(filePath);
                   System.out.println("file: "+filePath);
                   // saves the file to upload directory
                   item.write(uploadedFile);
                   result = fileName;
	      }
	            }
		  } catch (FileUploadException ex) {
	            throw new ServletException(ex);
	      } catch (Exception ex) {
	            throw new ServletException(ex);
	      }
		log.trace("END");
		return result;
    }
}
    
//    public String getInputFile(HttpServletRequest request, String name) throws ServletException, FileUploadException, org.apache.commons.fileupload.FileUploadException{
//    	log.trace("START");
//    	System.out.println(name);
//    	List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
//		for(FileItem item: items){
//			log.debug(item.getName());
//			if(!item.isFormField() && (item.getName() == name)){
//				return FilenameUtils.getName(item.getName());
//			}
//		}
//		log.trace("END");
//		return null;
//    }
//    	
//    private static String getFileName(Part part){
//    	for(String cd: part.getHeader("content-position").split(";")){
//    		if(cd.trim().startsWith("filename")){
//    			String filename = cd.substring(cd.indexOf('=')+1).trim().replace("\"","");
//    			return filename.substring(filename.lastIndexOf('/')+1).substring(filename.lastIndexOf('\\')+1);
//    		}
//    	}
//    	return null;
//    }
//    
//    private static String getValue(Part part, HttpServletRequest request) throws IOException{
//    	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//    	    // You can get your file from request
//    	    CommonsMultipartFile multipartFile =  null; // multipart file class depends on which class you use assuming you are using org.springframework.web.multipart.commons.CommonsMultipartFile
//
//    	    Iterator<String> iterator = multipartRequest.getFileNames();
//    	    String result = "";
//
//    	    while (iterator.hasNext()) {
//    	         String key = (String) iterator.next();
//    	        // create multipartFile array if you upload multiple files
//    	        multipartFile = (CommonsMultipartFile) multipartRequest.getFile(key);
//    	    }
//    	    result = multipartFile.getOriginalFilename();
//    	    
//    	    return result;
//    	
////    	 BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
////    	    StringBuilder value = new StringBuilder();
////    	    char[] buffer = new char[1024];
////    	    for (int length = 0; (length = reader.read(buffer)) > 0;) {
////    	        value.append(buffer, 0, length);
////    	    }
////    	    return value.toString();
//    }
// }

