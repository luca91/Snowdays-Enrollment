package com.snowdays_enrollment.controller.pub;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//Maps servlet public/jsp/index.jsp
@WebServlet(urlPatterns={
		"/",
		"/public/index.html"			
})
public class Index extends HttpServlet {
	
	static Logger log = Logger.getLogger(Index.class.getName());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3593068122563498506L;

	/**
	 * doGet method - maps the normal pages
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param HttpServletRequest request, HttpServletResponse response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("START");
		try {
			getServletConfig().getServletContext().getRequestDispatcher("/public/jsp/index.jsp").forward(request, response);
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
		// TODO Auto-generated method stub
	}

}
