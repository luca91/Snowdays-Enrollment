package com.snowdays_enrollment.controller.priv;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.User;

/**
 * Servlet implementation class Index
 */

@WebServlet("/private/index.html")
public class Index extends HttpServlet {
	
	// commons logging references
	static Logger log = Logger.getLogger(Index.class.getName());
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
    	log.trace("START");
        // TODO Auto-generated constructor stub
    }

	/**
	 * doGet method - maps the normal pages
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param HttpServletRequest request, HttpServletResponse response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		log.trace("START");
		
		User u = new User();
		UserDao ud = new UserDao();
		u = ud.getUserByEmail(request.getUserPrincipal().getName());
		
		HttpSession session = request.getSession(true);
		session.setAttribute("systemUser", u)*/;
		 
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
		// TODO Auto-generated method stub
	}

}
