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

import com.snowdays_enrollment.dao.RegistrationUniBzDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.User;

/**
 * Servlet implementation class RegistrationUniBzController
 */
@WebServlet("/private/unibzRegistrations.html")
public class RegistrationUniBzController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(RegistrationUniBzController.class.getName());
	private Connection c;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationUniBzController() {
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
		UserDao uDao = new UserDao(c);
		User systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		
		
		session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		session.setMaxInactiveInterval(1200);
		
		RegistrationUniBzDao ruDao = new RegistrationUniBzDao(c);
		
		String forward = "/private/jsp/unibzRegistration.jsp";
		
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
		// TODO Auto-generated method stub
	}

}
