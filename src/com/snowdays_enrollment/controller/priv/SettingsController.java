package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.SettingsDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Country;
import com.snowdays_enrollment.model.Settings;
import com.snowdays_enrollment.model.User;

/**
 * Servlet implementation class SettingsController
 */
@WebServlet(urlPatterns={
		"/private/settings.html",
		"/private/settingsEdit"
})
public class SettingsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(SettingsController.class.getName());
	private Connection c;
	private HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SettingsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");
		session = request.getSession();
		c = (Connection) session.getAttribute("DBConnection");
		
		UserDao uDao = new UserDao();
		User systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser", systemUser);
		session.setMaxInactiveInterval(1200);
		
		SettingsDao sDao = new SettingsDao(c);
		ArrayList<Country> countries = (ArrayList<Country>) sDao.getAllCountries();
		for(int i = 0; i < countries.size(); i++){
			System.out.println(countries.get(i).getName());
		}
		
		request.setAttribute("countries", countries);
		request.setAttribute("badges", new String[] {"Participant", "Party/Host", "Staff"});
		request.setAttribute("maxPerGroup", sDao.getSetting("maxpergroup"));
		request.setAttribute("maxInternals", sDao.getSetting("maxinternals"));
		System.out.println(sDao.getSetting("maxinternals"));
		request.setAttribute("maxExternals", sDao.getSetting("maxexternals"));
		
		
		String forward = "/private/jsp/settings.jsp";
		
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
		log.trace("START");
		session = request.getSession(true);
		c = (Connection) session.getAttribute("DBConnection");
		SettingsDao sDao = new SettingsDao(c);
		Settings s = new Settings();
		GroupDao gDao = new GroupDao(c);
		
		UserDao ud = new UserDao(c);
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);	
		session.setMaxInactiveInterval(1200);
		
		s.setMaxParticipantsPerGroup(Integer.parseInt(request.getParameter("maxpergroup")));
		s.setMaxInternals(Integer.parseInt(request.getParameter("maxinternals")));
		s.setMaxExternals(Integer.parseInt(request.getParameter("maxexternals")));
		
		ArrayList<Country> countries = (ArrayList<Country>) sDao.getAllCountries();
		Map<String, Integer> list = new HashMap<String, Integer>();
		for(int i = 0; i < countries.size(); i++){
			list.put(countries.get(i).getName(), Integer.parseInt((String) request.getParameter(countries.get(i).getName())));
		}
		sDao.setPeoplePerCountry(list);
		sDao.addSettings("maxpergroup", request.getParameter("maxpergroup"));
		gDao.updateMaxParticipants(Integer.parseInt(request.getParameter("maxpergroup")));
		sDao.addSettings("maxinternals", request.getParameter("maxinternals"));
		System.out.println(request.getParameter("maxinternals")+ "doPost");
		sDao.addSettings("maxexternals", request.getParameter("maxexternals"));
		response.sendRedirect("/snowdays-enrollment/private/index.html");
	}

}
