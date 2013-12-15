package com.snowdays_enrollment.controller.priv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet("/login.html")
public class LoginController extends HttpServlet {
	
	static Logger log = Logger.getLogger(Index.class.getName());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("START");
		try {
			getServletConfig().getServletContext().getRequestDispatcher("/private/jsp/index.jsp").forward(request, response);
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
		log.debug("START");
		try {
			request.setAttribute("username", request.getAttribute("username"));
			getServletConfig().getServletContext().getRequestDispatcher("/private/index.html").forward(request, response);
			} 
		catch (Exception ex) {
				ex.printStackTrace();
			}
	}
}
