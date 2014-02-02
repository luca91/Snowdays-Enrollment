package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasypt.util.text.BasicTextEncryptor;

import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.Email;

/**
 * Servlet implementation class UserController
 */
@WebServlet(urlPatterns = {
		"/private/userList.html", 
		"/private/user.jsp", 
		"/private/userAdd",
		"/private/userDelete"
		})
public class UserController extends HttpServlet {
	
	// commons logging references
	static Logger log = Logger.getLogger(UserController.class.getName());
	private Connection c;
	
	private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_USER = "/userList.jsp";
    private static String UNAUTHORIZED_PAGE = "/private/jsp/errors/unauthorized.jsp";
    private String forward;
    private HttpSession session;
    
    /**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private UserDao dao;   
	
    /**
     * Constructor
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        log.debug("UserController ###################################");
    	log.trace("START");
		dao = new UserDao(c);
        log.debug("Dao object instantiated");
        log.trace("END");
    }

    
	/**
	 * doGet method - maps the normal pages
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param HttpServletRequest request, HttpServletResponse response
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.trace("START");
    	session = request.getSession(true);
    	c = (Connection) session.getAttribute("DBConnection");
        String action = request.getParameter("action");
        dao = new UserDao(c);
		User  systemUser = dao.getUserByUsername(request.getUserPrincipal().getName());
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);        
        
        if (action == null){
        	log.debug("action is NULL");
        	action="";
        }
        
        //Delete user
        if (action.equalsIgnoreCase("delete")){
            log.debug("action: DELETE - " + action);
            deleteUser(request, systemUser);
        } 
        
        //Edit user
        else if (action.equalsIgnoreCase("edit")){
        	log.debug("action: EDIT - " + action);
        	editUser(request, systemUser);
        }
        
        //Insert user
        else if (action.equalsIgnoreCase("insert")){
            log.debug("action: INSERT - " + action);
            insertUser(request, systemUser);
        } 
        
        //List users
        else if (action.equalsIgnoreCase("listUser")){
            log.debug("action: listUser - " + action);
            listUsers(request);
           
        } else {
            log.debug("action: ELSE - " + action);
            listUsers(request);
        }
    	
        log.debug("forward: " + forward);
        log.debug("action: " + action);
    	
        log.debug("######################");
        log.debug("systemUser.getRole(): " + systemUser.getRole().toString());
        
		if (systemUser.getRole().equals("admin")){
	        log.debug("systemUser is an admin");
		    forward = "/private/jsp" + forward;
		}
		else {
	        log.debug("systemUser is NOT an admin");
			forward = UNAUTHORIZED_PAGE;
		}
		        
		log.debug("forward: " + forward);
        
		try {
			getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
			} 
		catch (Exception ex) {
				ex.printStackTrace();
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
    	User user = new User();
    	dao = new UserDao(c);
    	
    	User systemUser = dao.getUserByUsername(request.getUserPrincipal().getName());
    	System.out.println(systemUser.getPassword());
    	
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		String forward;
		
        user.setFname(request.getParameter("fname"));
        user.setLname(request.getParameter("lname"));
        user.setDate_of_birth(request.getParameter("date_of_birth"));
        user.setPassword(decryptPW(request.getParameter("password")));
        log.debug("password: "+ user.getPassword());
        user.setEmail(request.getParameter("email"));
        user.setRole(request.getParameter("role"));
        user.setUsername(request.getParameter("username"));
        String id = request.getParameter("id");
        

    	log.debug("id: " + id);
    	
        if(id == null || id.isEmpty()) {
        	//new user
        	
            String password = Long.toHexString(Double.doubleToLongBits(Math.random()));
            log.debug("passowrd: " + password);
            user.setPassword(password);

        	log.debug("INSERT");
            dao.addUser(user);
        }
        else
        {
        	log.debug("UPDATE");
            user.setId(Integer.parseInt(id));
            dao.updateUser(user);
        }
        
        forward = "/private/jsp" + LIST_USER;
        log.debug("forward: " + forward);
        request.setAttribute("users", dao.getAllUsers());
		try {
			getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
			} 
		catch (Exception ex) {
				ex.printStackTrace();
			}
    	log.trace("END");
	}
	
	public void deleteUser(HttpServletRequest request, User systemUser){
		log.trace("START");
		 int id = Integer.parseInt(request.getParameter("id"));
         if (systemUser.getRole().equals("admin")){
         	dao.deleteUser(id);
         	forward = LIST_USER;
         	request.setAttribute("users", dao.getAllUsers());
         }   
         log.trace("END");
	}
	
	public void insertUser(HttpServletRequest request, User systemUser){
		log.trace("START");
		  request.removeAttribute("user");
          if (systemUser.getRole().equals("admin")){
        	  forward = INSERT_OR_EDIT;
          }
          log.trace("END");
	}
	
	public void listUsers(HttpServletRequest request){
		log.trace("START");
		forward = LIST_USER;
        request.setAttribute("users", dao.getAllUsers());
        log.trace("END");
	}
	
	public void editUser(HttpServletRequest request, User systemUser){
		log.trace("START");
		forward = INSERT_OR_EDIT;
        int id = Integer.parseInt(request.getParameter("id"));
        if (systemUser.getRole().equals("admin")){
        	User user = dao.getUserById(id);
         	request.setAttribute("user", user);
         }
        log.trace("END");
	}
	
	public String decryptPW(String pw){
		BasicTextEncryptor textEncr = new BasicTextEncryptor();
		return textEncr.decrypt(pw);
	}

}
