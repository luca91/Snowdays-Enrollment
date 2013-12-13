package com.snowdays_enrollment.controller.pub;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Maps servlet public/jsp/index.jsp
@WebServlet("/index.html")
public class Index extends HttpServlet {
	
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
//		log.debug("START");
		try {
			//request.setAttribute ("servletName", "MyServlet");
			getServletConfig().getServletContext().getRequestDispatcher("/public/jsp/index.jsp").forward(request, response);
			//response.sendRedirect("/snowdays-enrollment/public/jsp/index.jsp");
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
