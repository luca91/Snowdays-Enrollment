package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.DBConnection;

/**
 * Servlet implementation class Registrations
 */
@WebServlet("/private/registrations.html")
public class Registrations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(Registrations.class.getName());
	private Connection c;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registrations() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");
		HttpSession session = request.getSession(true);
		c = (Connection) session.getAttribute("DBConnection");
		try {
			if(c.isClosed())
				c = new DBConnection().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserDao uDao = new UserDao(c);
		User systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		
		String forward = null;
		String action = request.getParameter("action");
		if(action != null){
			log.debug("action: " + action);
			if(action.equalsIgnoreCase("unibz"))
				forward = "/snowdays-enrollment/private/unibzRegistrations.html";
			else
				forward = "/snowdays-enrollment/private/externalsRegistrations.html";
			
			try {
				response.sendRedirect(forward);
				} 
			catch (Exception ex) {
					ex.printStackTrace();
				}
		}
		
		else{
			forward = "/private/jsp/registrations.jsp";
			  
			try {
				getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
				} 
			catch (Exception ex) {
					ex.printStackTrace();
				}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
