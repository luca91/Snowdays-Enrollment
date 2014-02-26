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

import com.itextpdf.text.DocumentException;
import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.pdf.PDFGenerator;

/**
 * Servlet implementation class BadgeController
 */
@WebServlet(urlPatterns={
		"/private/downloadAllBadges",
		"/private/badges.html"
})
public class BadgeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private Connection c;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BadgeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		c = (Connection) session.getAttribute("DBConnection");
		UserDao ud = new UserDao(c);
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		GroupDao gDao = new GroupDao(c);
		ParticipantDao pDao = new ParticipantDao(c);
		PDFGenerator badges = null;
		
		if(request.getParameter("group") != null){
			int group = Integer.parseInt(request.getParameter("group"));
			Group g = gDao.getRecordById(group);
			try {
				badges = new PDFGenerator(g.getName(), 
						(ArrayList<Participant>) pDao.getAllRecordsById_group(group), g.getBadgeType(), 
						getServletConfig().getServletContext().getRealPath("/") + "../../" + g.getName());
				badges.setFileName(g.getName() + "_badges.pdf");
				badges.setDocument();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File output = null;
			try {
				 output = badges.createGroupBadges();
				 
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
