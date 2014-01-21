package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout.html")
public class LogoutController extends HttpServlet {
	
	static Logger log = Logger.getLogger(LogoutController.class.getName());
	private static final long serialVersionUID = 1L;
	private Connection c;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");
		HttpSession session;
    	session = request.getSession();
    	c = (Connection) session.getAttribute("DBConnection");
    	try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        session.invalidate();
        log.debug("session invalidated");
        
        try {
			response.sendRedirect("http://scub.unibz.it:8080/snowdays-enrollment/");
			} 
		catch (Exception ex) {
				ex.printStackTrace();
			}
    	log.trace("END");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
