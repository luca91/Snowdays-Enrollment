package com.snowdays_enrollment.controller.priv;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/logout.html")
public class LogoutController extends HttpServlet {
	
	static Logger log = Logger.getLogger(LogoutController.class.getName());
	private static final long serialVersionUID = 1L;
       
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
    	String forward="";
    	session = request.getSession();
        session.invalidate();
        log.debug("session invalidated");
//        forward = "/public/index.html";
        
        try {
			getServletConfig().getServletContext().getRequestDispatcher("/public/jsp/index.jsp").forward(request, response);
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
