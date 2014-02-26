package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.RegistrationUniBzDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.RegistrationUniBz;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.tools.DBConnection;
import com.snowdays_enrollment.tools.Email;

/**
 * Servlet implementation class RegistrationUniBzController
 */
@WebServlet(urlPatterns={
		"/private/unibzRegistrations.html",
		"/private/addLink",
		"/private/show"
})
public class RegistrationUniBzController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(RegistrationUniBzController.class.getName());
	private Connection c;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationUniBzController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");
		HttpSession session = request.getSession(true);
		c = (Connection) session.getAttribute("DBConnection");
		try {
			if(c.isClosed())
				c = new DBConnection().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserDao uDao = new UserDao(c);
		User systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		ParticipantDao pDao = new ParticipantDao(c);
		GroupDao gDao = new GroupDao(c);
		RegistrationUniBzDao ruDao = new RegistrationUniBzDao(c);
		String action = request.getParameter("action");
		String email = "";
		createInternalsGroups(gDao);
		setGroupID(request, gDao);
		request.setAttribute("selGroup", "");
		request.setAttribute("groups", new String []{"", "UNIBZ", "Alumni", "Host"});
		
		if(action != null && action.equals("delete")){
			email = request.getParameter("email");
			ruDao.deleteRecord(email);
			pDao.deleteRecordByEmail(email);
			response.sendRedirect("unibzRegistrations.html");
		}
		else if(action != null && action.equals("show")){
			RegistrationUniBz ru = ruDao.getRegistrationByEmail(request.getParameter("email"));
			
			request.setAttribute("record", ru);
			request.setAttribute("email", ru.getEmail());
			request.setAttribute("selGroup", ru.getGroup());
			request.setAttribute("records", ruDao.getAllRegistration());
			String forward = "/private/jsp/unibzRegistration.jsp";
			try {
				getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
				} 
			catch (Exception ex) {
					ex.printStackTrace();
			}
		}
		else{
			request.setAttribute("records", ruDao.getAllRegistration());
			
			String forward = "/private/jsp/unibzRegistration.jsp";
			try {
				getServletConfig().getServletContext().getRequestDispatcher(forward).forward(request, response);
				} 
			catch (Exception ex) {
					ex.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		c = (Connection) session.getAttribute("DBConnection");
		UserDao uDao = new UserDao(c);
		try {
			if(c.isClosed())
				c = new DBConnection().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		
		session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);
		
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		String groupSelected = request.getParameter("group");
		String link = request.getParameter("link");
		RegistrationUniBzDao ruDao = new RegistrationUniBzDao(c);
		RegistrationUniBz ru = new RegistrationUniBz();
		ru.setEmail(email);
		ru.setName(name);
		ru.setSurname(surname);
		ru.setStatus("none");
		ru.setGroup(groupSelected);
		ru.setLink(link);
		ruDao.addRecord(ru);
//		try {
//			sendEmail(email, name, gDao.getGroupByName(group).getId());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		response.sendRedirect("unibzRegistrations.html");
	}

	private boolean sendEmail(String email, String name, int groupID) throws ParseException{ 
		
        String subject = "Snowdays 2014 - Registration";
        
        String message = "";
        message += "Dear " + name + ",\n";
        message += "\nplease follow the link below to complete your registration to Snowdays 2014:\n";
        message += "\n" +
        			"http://scub.unibz.it:8080/snowdays-enrollment/public/participantInt.jsp?id_group=" + 
        			groupID + 
        			"&email=" + email
        			+ "\n";
        message += "\n\nThe Snowdays Team";
       
        Email em = new Email();
        return em.sendEmail(email, subject, message);
    }
	
	public String getGroupSelected(String group){
		System.out.println(group);
		switch(group){
			case "unibz":
				return "UNIBZ";
			case "alumni":
				return "Alumni";
			case "host":
				return "Host";
		}
		return null;
	}
	
	public void setGroupID(HttpServletRequest request, GroupDao g){
		request.setAttribute("unibz", g.getGroupByName("UNIBZ").getId());
		request.setAttribute("alumni", g.getGroupByName("Alumni").getId());
		request.setAttribute("host", g.getGroupByName("Host").getId());
	}
	
	public void createInternalsGroups(GroupDao gd){
		Group g = null;
		UserDao ud = new UserDao(c);
		if(gd.getGroupByName("UNIBZ") == null){
			g = new Group();
			g.setName("UNIBZ");
			g.setGroupReferentID(ud.getUserByUsername("murbani").getId());
			g.setGroupMaxNmber(200);
			g.setCountry("ITALY");
			g.setBadgeType("PARTICIPANT");
			g.setActualParticipantNumber(0);
			g.setIsBlocked(false);
			gd.addRecord(g);
		}
		if(gd.getGroupByName("Alumni") == null){
			g = new Group();
			g.setName("Alumni");
			g.setGroupReferentID(ud.getUserByUsername("murbani").getId());
			g.setGroupMaxNmber(10);
			g.setCountry("ITALY");
			g.setBadgeType("PARTICIPANT");
			g.setActualParticipantNumber(0);
			g.setIsBlocked(false);
			gd.addRecord(g);
		}
		if (gd.getGroupByName("Host") == null){
			g = new Group();
			g.setName("Host");
			g.setGroupReferentID(ud.getUserByUsername("murbani").getId());
			g.setGroupMaxNmber(20);
			g.setCountry("ITALY");
			g.setBadgeType("PARTICIPANT");
			g.setActualParticipantNumber(0);
			g.setIsBlocked(false);
			gd.addRecord(g);
		}
	}
	
}
