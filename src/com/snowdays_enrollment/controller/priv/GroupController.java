package com.snowdays_enrollment.controller.priv;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.SettingsDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Settings;
import com.snowdays_enrollment.model.User;


/**
 * Servlet implementation class GroupController
 */
@WebServlet(urlPatterns = {
		"/private/groupList.html", 
		"/private/group.jsp", 
		"/private/groupAdd",
		"/private/groupDelete",
		"/private/groupStatus"
		})
public class GroupController extends HttpServlet {
	
	// commons logging references
	static Logger log = Logger.getLogger(GroupController.class.getName());
	
	private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/group.jsp";
    private static String LIST_USER = "/groupList.jsp";
    private static String UNAUTHORIZED_PAGE = "/private/jsp/errors/unauthorized.jsp";
    private Connection c;
    private HttpSession session;

    /**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private GroupDao dao;   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GroupController() {
        super();
        log.debug("###################################");
    	log.trace("START");
		dao = new GroupDao();
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
    	session  = request.getSession(true);
    	c = (Connection) session.getAttribute("DBConnection");
		UserDao ud = new UserDao(c);
		User systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		SettingsDao sDao = new SettingsDao(c);
		
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		log.debug("user role: "+systemUser.getRole());
		session.setMaxInactiveInterval(1200);
		
		String status = sDao.getSetting("all_blocked");
		log.debug("status: "+status);
		request.setAttribute("status", status);
    	
    	String forward="";
        String action = request.getParameter("action");
        if (action == null){
        	log.debug("action is NULL");
        	action="";
        }

// #########################################################################################        
        if (action.equalsIgnoreCase("delete")){
            log.debug("action: DELETE - " + action);
            int id = Integer.parseInt(request.getParameter("id"));
            File groupFolder = new File(getServletConfig().getServletContext().getRealPath("/") 
            		+ File.separator + dao.getRecordById(id).getName());
            groupFolder.delete();
            dao.deleteRecord(id);
            forward = LIST_USER; 
        }
// #########################################################################################        
        else if (action.equalsIgnoreCase("edit")){
            log.debug("action: EDIT - " + action);
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Group record = dao.getRecordById(id);
            request.setAttribute("record", record);
            List<User> listOfGroup_mng = ud.getAllRecordWithRole("group_manager");
            request.setAttribute("countries", dao.getCountries());
            listOfGroup_mng.add(ud.getUserById(record.getGroupReferentID()));
            request.setAttribute("listOfGroup_mng", listOfGroup_mng);
            request.setAttribute("satProgs", new String[] {"none", "snowvolley", "dodgeball"});
        }
// #########################################################################################        
        else if (action.equalsIgnoreCase("insert")){
            log.debug("action: INSERT - " + action);
            request.removeAttribute("record");
            forward = INSERT_OR_EDIT;
            List<User> listOfGroup_mng = ud.getAllRecordWithRole("group_manager");
            request.setAttribute("listOfGroup_mng", listOfGroup_mng);
            request.setAttribute("countries", dao.getCountries());
            request.setAttribute("satProgs", new String[] {"none", "snowvolley", "dodgeball"});
        }
// ########################################################################################
        else if (action.equalsIgnoreCase("approve")) {
        	log.debug("action: APPROVE - " + action);
        	request.removeAttribute("record");
        	int groupID = Integer.parseInt(request.getParameter("id_group"));
        	dao.approve(groupID, true);
        	Group g = dao.getRecordById(groupID);
        	g.setIsApproved(true);
        	request.setAttribute("record", g);
        }
// ########################################################################################
        else if (action.equalsIgnoreCase("disapprove")) {
        	log.debug("action: DISAPPROVE - " + action);
        	request.removeAttribute("record");
        	int groupID = Integer.parseInt(request.getParameter("id_group"));
        	dao.approve(groupID, false);
        	Group g = dao.getRecordById(groupID);
        	g.setIsApproved(false);
        	request.setAttribute("record", g);
        }
// #########################################################################################
        else if(action.equals("block")){
        	if(systemUser.getRole().equals("admin")){
        		dao.blockAll(true);
        		sDao.addSettings("all_blocked", "blocked");
        		request.setAttribute("status", "blocked");
        	}
        }
// #########################################################################################
        else if(action.equals("unblock")){
        	if(systemUser.getRole().equals("admin")){
        		dao.blockAll(false);
        		sDao.addSettings("all_blocked", "unblocked");
        		request.setAttribute("status", "unblocked");
        	}
        }
// #########################################################################################        
        else if (action.equalsIgnoreCase("listRecord")){
            log.debug("action: " + action);
            if (systemUser.getRole().equals("admin")){
                log.debug("admin");
                forward = LIST_USER;
            }
            else if (systemUser.getRole().equals("group_manager")){
                forward = LIST_USER;
            }
        } else {
            log.debug("action: ELSE - " + action);
            if (systemUser.getRole().equals("admin")){
                log.debug("admin");
                forward = LIST_USER;
                request.setAttribute("records", dao.getAllRecords());
            }
            else if (systemUser.getRole().equals("group_manager")){
                forward = LIST_USER;
            }
        }
    	
        log.debug("forward: " + forward);
        log.debug("action: " + action);
        
        log.debug("######################");
        log.debug("systemUser.getRole(): " + systemUser.getRole().toString());
        
        if(action.equals("delete") || action.equals("approve") || action.equals("disapprove") 
        		|| action.equals("unblock") || action.equals("block")){
        	response.sendRedirect("groupList.html");
        }
        else{
			if (systemUser.getRole().equals("admin") 
					|| systemUser.getRole().equals("group_manager")){
		        log.debug("systemUser is an admin");
			    forward = "/private/jsp" + forward;
			}
			else {
				forward = UNAUTHORIZED_PAGE;
			}
	        
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
    	
    	Group record = new Group();
		UserDao ud = new UserDao(c);
		User systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		session.setMaxInactiveInterval(1200);
		String forward;

		
		System.out.println(request.getParameter("max_group_number"));
    	record.setGroupReferentID(Integer.parseInt(request.getParameter("id_group_referent")));
    	record.setName(request.getParameter("name"));
    	record.setFirstParticipantRegisteredID(-1);
    	record.setCountry(request.getParameter("country"));
    	record.setBadgeType(request.getParameter("badge"));
    	record.setActualParticipantNumber(0);
    	record.setGroupMaxNmber(Integer.parseInt(new SettingsDao().getSetting("maxpergroup")));
    	record.setSnowvolley(request.getParameter("saturday"));
    	System.out.println(record.getSnowvolley());
    	record.setIsBlocked(Boolean.parseBoolean(request.getParameter("blocked")));
    	record.setFirstParticipantRegisteredID(-1);
    	
//    	if(request.getParameter("approved").equals("NO"))
//    		record.setIsApproved(false);
//    	else
//    		record.setIsApproved(true);
//    	if(request.getParameter("blocked").equals("NO"))
//    		record.setIsBlocked(false);
//    	else
//    		record.setIsBlocked(true);
    	
    	String id = request.getParameter("id");
        
    	log.debug("id: " + id);
    	
        if(id == null || id.isEmpty()) {
        	log.debug("INSERT");
            dao.addRecord(record);
        }
        else
        {
        	log.debug("UPDATE");
        	record.setId(Integer.parseInt(id));
            dao.updateRecord(record);
        }
        
		//Load event list available for user
        if (systemUser.getRole().equals("admin")){
            log.debug("admin");
            request.setAttribute("records", dao.getAllRecords());
        }
        else if (systemUser.getRole().equals("group_manager")){
            request.setAttribute("records", dao.getRecordsByGroupReferentID(systemUser.getId()));
        }
        
        String realPath = getServletConfig().getServletContext().getRealPath("/");
        File groupFolder = new File(realPath+"/"+record.getName());
        System.out.println(groupFolder.getPath());
        groupFolder.mkdir();
        File photosFolder = new File(realPath+"/"+record.getName()+"/profile");
        photosFolder.mkdir();
        File idsFolder = new File(realPath+"/"+record.getName()+"/studentids");
        idsFolder.mkdir();
        File badgeFilder = new File(realPath+"/"+record.getName()+"/badges");
        badgeFilder.mkdir();
        
        forward =  "groupList.html";
        log.debug("forward: " + forward);

    	log.trace("END");
		response.sendRedirect("/snowdays-enrollment/private/"+forward);
	}
	
	public boolean checkForNames(String name){
		ArrayList<String> namesList = getGroupsNamesList();
		if(namesList.contains(name.toLowerCase()))
			return true;
		return false;		
	}
	
	public ArrayList<String> getGroupsNamesList(){
		List<Group> l = dao.getAllRecords();
		ArrayList<String> names = new ArrayList<String>(l.size());
		for(int i = 0; i < l.size(); i++){
			Group g = l.get(i);
			names.add(g.getName().toLowerCase());
		}
		return names;
	}
}