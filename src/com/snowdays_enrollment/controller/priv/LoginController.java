package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;

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

@WebServlet(urlPatterns={
		"/login.html",
		"/private/loginCheck"})
public class LoginController extends HttpServlet {
	
	static Logger log = Logger.getLogger(Index.class.getName());
	private Connection c;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("START");
		HttpSession session = request.getSession();
		session.setAttribute("DBConnection", new DBConnection().getConnection());
		UserDao uDao = new UserDao((Connection) session.getAttribute("DBConnection")); 
		if(session.getAttribute("systemUser") != null)
			session.removeAttribute("systemUser");
		session.setAttribute("systemUser", uDao.getUserByUsername(request.getUserPrincipal().getName()));
		session.setMaxInactiveInterval(600);
		try {
//			getServletConfig().getServletContext().getRequestDispatcher("/private/index.html").forward(request, response);
			response.sendRedirect("/snowdays-enrollment/private/index.html");
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
	}
}
