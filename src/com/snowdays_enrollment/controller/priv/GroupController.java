package com.snowdays_enrollment.controller.priv;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
import com.snowdays_enrollment.tools.DBConnection;


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
       
    	
		dao = new GroupDao();
        
        
    }

	/**
	 * doGet method - maps the normal pages
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param HttpServletRequest request, HttpServletResponse response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	session  = request.getSession(true);
    	c = (Connection) session.getAttribute("DBConnection");
    	try {
			if(c.isClosed())
				c = new DBConnection().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserDao ud = new UserDao(c);
		User systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		SettingsDao sDao = new SettingsDao(c);
		dao = new GroupDao(c);
		
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		
		
		String status = sDao.getSetting("all_blocked");
		
		request.setAttribute("status", status);
    	
    	String forward="";
        String action = request.getParameter("action");
        if (action == null){
        
        	action="";
        }

// #########################################################################################        
        if (action.equalsIgnoreCase("delete")){
            
            int id = Integer.parseInt(request.getParameter("id"));
            File groupFolder = new File(getServletConfig().getServletContext().getRealPath("/") 
            		+ File.separator + dao.getRecordById(id).getName());
            groupFolder.delete();
            dao.deleteRecord(id);
            forward = LIST_USER; 
        }
// #########################################################################################        
        else if (action.equalsIgnoreCase("edit")){
            
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
          
            request.removeAttribute("record");
            forward = INSERT_OR_EDIT;
            List<User> listOfGroup_mng = ud.getAllRecordWithRole("group_manager");
            request.setAttribute("listOfGroup_mng", listOfGroup_mng);
            request.setAttribute("countries", dao.getCountries());
            request.setAttribute("satProgs", new String[] {"none", "snowvolley", "dodgeball"});
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
          
            if (systemUser.getRole().equals("admin")){
                
                forward = LIST_USER;
            }
            else if (systemUser.getRole().equals("group_manager")){
                forward = LIST_USER;
            }
        } else {
            
            if (systemUser.getRole().equals("admin")){
                
                forward = LIST_USER;
                request.setAttribute("records", dao.getAllRecords());
            }
            else if (systemUser.getRole().equals("group_manager")){
                forward = LIST_USER;
            }
        }
    
        
        if(action.equals("delete") || action.equals("approve") || action.equals("disapprove") 
        		|| action.equals("unblock") || action.equals("block")){
        	response.sendRedirect("groupList.html");
        }
        else{
			if (systemUser.getRole().equals("admin") 
					|| systemUser.getRole().equals("group_manager")){
		       
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
    
    	session = request.getSession(true);
    	c = (Connection) session.getAttribute("DBConnection");
    	try {
			if(c.isClosed())
				c = new DBConnection().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Group record = new Group();
		UserDao ud = new UserDao(c);
		User systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		dao = new GroupDao(c);
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		String forward;
		
		System.out.println(request.getParameter("max_group_number"));
    	record.setGroupReferentID(Integer.parseInt(request.getParameter("id_group_referent")));
    	record.setName(request.getParameter("name"));
    	
    	record.setFirstParticipantRegisteredID(-1);
    	record.setCountry(request.getParameter("country"));
    	record.setBadgeType(request.getParameter("badge"));
    	record.setActualParticipantNumber(0);
    	if(record.getName().equals("UNIBZ")){
    		record.setGroupMaxNmber(Integer.parseInt(new SettingsDao(c).getSetting("maxinternals")));
    	}
    	else
    		record.setGroupMaxNmber(Integer.parseInt(new SettingsDao(c).getSetting("maxpergroup")));
    	record.setSnowvolley(request.getParameter("saturday"));
    	System.out.println(record.getSnowvolley());
    	record.setIsBlocked(Boolean.parseBoolean(request.getParameter("blocked")));
    	
    	record.setFirstParticipantRegisteredID(-1);
    	
    	String id = request.getParameter("id");
      
    	
        if(id == null || id.isEmpty()) {
        	
        	record.setIsBlocked(true);
            dao.addRecord(record);
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
        }
        else
        {
        	
        	record.setId(Integer.parseInt(id));
            dao.updateRecord(record);
        }
        
		//Load event list available for user
        if (systemUser.getRole().equals("admin")){
            
            request.setAttribute("records", dao.getAllRecords());
        }
        else if (systemUser.getRole().equals("group_manager")){
            request.setAttribute("records", dao.getRecordsByGroupReferentID(systemUser.getId()));
        }
        
        forward =  "groupList.html";
        
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