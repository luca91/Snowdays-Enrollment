package com.snowdays_enrollment.controller.priv;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.BadgeDao;
import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Badge;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.pdf.PDFGenerator;
import com.itextpdf.text.DocumentException;

/**
 * Servlet implementation class DownloadBadgeController
 */
@WebServlet(urlPatterns = {
		"/private/badge.jsp",
		"/private/badge.html",
		"/private/downloadBadge",
		"/private/badge",
		"/private/badgeList.html",
		"/private/badgeList.jsp"
})
/**
 * This servlet take care of the badge creation and listing on the webpage.
 * @author Luca Bellettati
 *
 */
public class BadgeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(BadgeController.class.getName());
	
	private static String OUTPUT = "/output";
	private static String DOWNLOAD_LIST = "/badgeList.jsp";
	private static String UNAUTHORIZED_PAGE = "/private/jsp/errors/unauthorized.jsp";
	
	/**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private BadgeDao dao;
	
	private int id_group;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BadgeController() {
        super();
        log.debug("###################################");
        log.trace("START");
        dao = new BadgeDao();
        log.debug("Dao object instantiated");
        log.trace("END");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");

		UserDao ud = new UserDao();
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		ParticipantDao pDao = new ParticipantDao();
		
		HttpSession session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		session.setMaxInactiveInterval(1200);
		
		GroupDao gdao = new GroupDao();
    	request.setAttribute("groups", gdao.getAllRecords());
    	System.out.println(gdao.getAllRecords().size());
		

    	log.debug("id_group (first): " + request.getParameter("id_group"));	

    	if (request.getParameter("id_group") != null){
    		log.debug("id_group is not null!");
    		id_group = Integer.parseInt(request.getParameter("id_group").toString());
    		log.debug("id_group: "+id_group);
        	GroupDao gd = new GroupDao();
        	request.setAttribute("group_name", gd.getRecordById(id_group).getName());
    	}
    	log.debug("id_group (second): "+request.getParameter("id_group"));
    	request.setAttribute("id_group", request.getParameter("id_group"));
    	
    	
    	String forward="";
        String action = request.getParameter("action");
        if (action == null){
        	log.debug("action is NULL");
        	action="";
        }         
        
// #########################################################################################
        //list record using a group_id
        if (action.equalsIgnoreCase("listRecord")){
            log.debug("action: listRecord - " + action);
            if (systemUser.getRole().equals("admin")){
                log.debug("admin");
                forward = DOWNLOAD_LIST;
                request.setAttribute("records", pDao.getAllRecordsById_group(id_group));
                GroupDao gd = new GroupDao();
                request.setAttribute("groups", gd.getAllRecords());
            }
        }
        
        //generate the badge and open the file just created
        else if(action.equalsIgnoreCase("download")) {
        	log.debug(systemUser.getRole());
        	int id = Integer.parseInt(request.getParameter("id"));
        	String id_event = request.getParameter("event_id");
        	Participant aPar = pDao.getRecordById(id);
        	PDFGenerator badge = null;
        	try {
        		File outputFolder = new File(getServletContext().getRealPath("/")+"/private/pdf/");
        		outputFolder.mkdir();
        		GroupDao gd = new GroupDao();
				badge = new PDFGenerator(aPar.getFname(), aPar.getLname(), aPar.getId(), gd.getRecordById(aPar.getId_group()).getName(), id_event, outputFolder.getAbsolutePath());
				badge.setImagePath(getServletContext().getRealPath("/private/images/logo_unibz.jpg"));
				badge.createDocument();
				log.debug("badge folder: "+badge.getFilePath());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	log.debug("file path: "+badge.getFilePath());
        	
        	forward = badge.getFilePath();
        	
        }
        
        else {
            if (systemUser.getRole().equals("admin")){
                log.debug("admin");
                forward = DOWNLOAD_LIST;
                request.setAttribute("records", pDao.getAllRecords());
                GroupDao gd = new GroupDao();
                request.setAttribute("groups", gd.getAllRecords());
            }
        }
// #########################################################################################     	
        log.debug("forward: " + forward);
        log.debug("action: " + action);
    	        
        if(!action.equalsIgnoreCase("download"))
        		forward = "/private/jsp" + forward;

        
		try {
			getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
			} 
		catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");
		Badge record = new Badge();
		
		UserDao ud = new UserDao();
		User  systemUser = ud.getUserByEmail(request.getUserPrincipal().getName());
		
		HttpSession session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		
		if (systemUser.getRole().equals("admin")){
            GroupDao gd = new GroupDao();
            request.setAttribute("groups", gd.getAllRecords());
        }
        else if (systemUser.getRole().equals("group_mng")){
            GroupDao gd = new GroupDao();
            request.setAttribute("groups", gd.getRecordsByGroupReferentID(systemUser.getId()));
        }
		
		int id_group = 0;
			
		log.debug("id_group: " + request.getParameter("id_group"));
    	log.debug("action: " + request.getParameter("action"));
		
    	if (request.getParameter("id_group") != null){
    		log.debug("id_group is not null!");
    		id_group = Integer.parseInt(request.getParameter("id_group").toString());
    	}
    	
    	if (request.getParameter("action") != null && request.getParameter("action").equals("listRecord")) {
    		//form for the download
    		
	    	
	    	log.debug("----------------> id_group: " + request.getParameter("id_group"));
	    	
	    	record.setFirstName(request.getParameter("fname"));
	    	record.setLastName(request.getParameter("lname"));
	    	record.setGroup(id_group);	
    	}
	} 
}
