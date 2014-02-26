package com.snowdays_enrollment.controller.priv;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.snowdays_enrollment.dao.DocumentDao;
import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Badge;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.pdf.PDFGenerator;
import com.itextpdf.text.DocumentException;

/**
 * Servlet implementation class DownloadBadgeController
 */
@WebServlet(urlPatterns = {
		"/private/downloadDocs",
		"/private/documents.html",
})
/**
 * This servlet take care of the badge creation and listing on the webpage.
 * @author Luca Bellettati
 *
 */
public class DocumentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String UNAUTHORIZED_PAGE = "/private/jsp/errors/unauthorized.jsp";
	private HttpSession session;
	private Connection c;
	
	/**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private DocumentDao dao;
	
	private int id_group;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DocumentsController() {
        super();
  
        dao = new DocumentDao();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		c = (Connection) session.getAttribute("DBConnection");
		UserDao ud = new UserDao(c);
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		ParticipantDao pDao = new ParticipantDao(c);
		GroupDao gDao = new GroupDao(c);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
    	
    	String forward="";
        String action = request.getParameter("action");
        if (action == null){
        	action="";
        }         
        
        //download all docs
        if (action.equalsIgnoreCase("all")){
            if (systemUser.getRole().equals("admin")){
                // to do
            }
        }
        else if(request.getParameter("id_group") != null){
        	//to do, only for a group
        }
        else if(request.getParameter("id") != null){
           //to do, only for one participant
        }
        
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
		// TO DO
	} 
}
