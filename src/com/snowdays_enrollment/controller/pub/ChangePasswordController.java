package com.snowdays_enrollment.controller.pub;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.Email;

/**
 * Servlet implementation class UserController
 */
@WebServlet(urlPatterns = {
		"/public/changePassword.html", 
		"/public/changePasswordDo",
		"/public/changePasswordSend"
		})
public class ChangePasswordController extends HttpServlet {
	
	// commons logging references
	static Logger log = Logger.getLogger(ChangePasswordController.class.getName());
	
	private static final long serialVersionUID = 1L;
    
    /**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private UserDao dao;   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordController() {
        super();
        log.debug("UserController ###################################");
    	log.trace("START");
		dao = new UserDao();
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
    	String forward = "/WEB-INF/jsp/public/changePassword.jsp";   
		        
		log.debug("forward: " + forward);
        
		if (request.getParameter("action") == null) {
			log.debug(request.getParameter("action"));
		}
		
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
    	
    	String forward = "/WEB-INF/jsp/public/changePassword.jsp";
		String email = request.getParameter("email").toString();
		log.debug("email: " + email);
    	
    	String action = "";
    	if (request.getParameter("action") != null) {
        	log.trace("action is not null");
    		action = request.getParameter("action");

    	}
    	
    	
    	if (action.equals("")){
    		log.debug("form to send link");
    		if (sendEmail(email, request.getServerName())){
        		log.debug("sent mail");
        		request.setAttribute("message", 1);
        	}
    	}
    	else if (action.equals("change")){    		    		
    		String oldPassword = request.getParameter("oldPassword").toString();
    		String newPassword = request.getParameter("newPassword").toString();
    		String confirmedPassword = request.getParameter("confirmedPassword").toString();
    		log.debug("oldPassword: " + oldPassword);
    		log.debug("newPassword: " + newPassword);
    		log.debug("confirmedPassword: " + confirmedPassword);
    		
    		UserDao ud = new UserDao();
			log.debug("get user");
    		User u = ud.getUserByEmail(email);
    		log.debug("old password: " + u.getPassword());
    		Integer id = u.getId();

    		
	    	if (id != null){		
		    	log.debug("form to change password");

	    		if ( u.getPassword().equals(oldPassword)){
		    		if (!newPassword.equals("")){
		    			if (newPassword.equals(confirmedPassword)){
		    				log.debug("try to change password");
		    				if (ud.updatePassword(u.getId(), newPassword)){
		    					log.debug("password changed");
		    		    		request.setAttribute("message", 2);
		    				}
		    	    		
		    			}
		    			else {
		    				log.debug("confirmed password is no equal to new password");
		    	    		request.setAttribute("message", 3);
		    			}
		    		}
		    		else {
		    			log.debug("password  vuota");
		        		request.setAttribute("message", 4);
		    		}
	    		}
	    		else{
	    			log.debug("incorrect password");
	        		request.setAttribute("message", 5);
	    		}	    			
	    	}
	    	else {
	    		log.debug("email not recognized");
	    		request.setAttribute("message", 6);
	    	}
	    	
	    	
	    	request.setAttribute("email", email);
	    	request.setAttribute("oldPassword", oldPassword);
	    	request.setAttribute("newPassword", newPassword);
	    	request.setAttribute("confirmedPassword", confirmedPassword);
    		
    	}
    	
        log.debug("forward: " + forward);

		try {
			getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
			} 
		catch (Exception ex) {
				ex.printStackTrace();
			}
    	log.trace("END");
	}

    private boolean sendEmail(String to, String serverName){ 
    	log.debug("address: " + to);

        log.debug("TO: " + to);
        String subject = "Reset password for site EMS";
        
        String message = "";
        message += "You have asket to change the password for you user";
        message += "\nClick on the following link and change it:\n";
        message += "\n" +
        			"http://" + serverName + ":8080/ems/public/changePassword.html?email=" + to + "\n";
        message += "\n\nThe staff";
       
		Email e = new Email();
		return e.sendEmail(to, subject, message);		
    }	
}
