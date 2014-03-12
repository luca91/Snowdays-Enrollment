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
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
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
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser", systemUser);
		GroupDao gDao = new GroupDao(c);
		PDFGenerator badges = null;
		
		String type = request.getParameter("type");
		String output = "";
//		if(request.getParameter("group") != null){
//			int group = Integer.parseInt(request.getParameter("group"));
//			Group g = gDao.getRecordById(group);
//			try {
//				badges = new PDFGenerator(g.getName(), 
//						(ArrayList<Participant>) pDao.getAllRecordsById_group(group), g.getBadgeType(), 
//						getServletConfig().getServletContext().getRealPath("/") + g.getName());
//				badges.setFileName(g.getName() + "_badges.pdf");
//				badges.setDocument();
//			} catch (DocumentException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			 output = badges.getAbsoluteFilePath();
//		}
		if(type != null){
			ArrayList<Group> groups = new ArrayList<Group>();
			switch(type){
			case "participant":
				groups = (ArrayList<Group>) gDao.getGroupsByBadgeType("PARTICIPANT");
				System.out.println("Size: " + groups.size());
				badges = new PDFGenerator(groups, getServletConfig().getServletContext().getRealPath("/"), c, "PARTICIPANT");
				try {
					badges.setDocument();
					badges.createBadgeByType("PARTICIPANT");
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				output = badges.getPathBadgeByType();
				break;
			case "staff":
				groups = (ArrayList<Group>) gDao.getGroupsByBadgeType("STAFF");
				System.out.println("Size: " + groups.size());
				badges = new PDFGenerator(groups, getServletConfig().getServletContext().getRealPath("/"), c, "STAFF");
				try {
					badges.setDocument();
					badges.createBadgeByType("STAFF");
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				output = badges.getPathBadgeByType();
				break;
			case "host":
				groups = (ArrayList<Group>) gDao.getGroupsByBadgeType("PARTY/HOST");
				System.out.println("Size: " + groups.size());
				badges = new PDFGenerator(groups, getServletConfig().getServletContext().getRealPath("/"), c, "PARTY/HOST");
				try {
					badges.setDocument();
					badges.createBadgeByType("PARTY/HOST");
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				output = badges.getPathBadgeByType();
				break;
			case "sponsor":
				groups = (ArrayList<Group>) gDao.getGroupsByBadgeType("Sponsor");
				System.out.println("Size: " + groups.size());
				badges = new PDFGenerator(groups, getServletConfig().getServletContext().getRealPath("/"), c, "Sponsor");
				try {
					badges.setDocument();
					badges.createBadgeByType("Sponsor");
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				badges.getPathBadgeByType();
				break;
        	
			}
		}
			getServletConfig().getServletContext().getRequestDispatcher(output).forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public void performDownload(String fileToDownload){
		
	}

}
