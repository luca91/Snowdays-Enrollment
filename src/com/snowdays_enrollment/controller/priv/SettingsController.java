package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
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
		
		UserDao uDao = new UserDao();
		User systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		
		HttpSession session = request.getSession();
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser", systemUser);
		
		SettingsDao sDao = new SettingsDao();
		ArrayList<Country> countries = (ArrayList<Country>) sDao.getAllCountries();
		for(int i = 0; i < countries.size(); i++){
			System.out.println(countries.get(i).getName());
		}
		
		request.setAttribute("countries", countries);
		request.setAttribute("badges", new String[] {"Participant", "Party/Host", "Staff"});
		request.setAttribute("maxPerGroup", sDao.getSetting("maxpergroup"));
		request.setAttribute("maxInternals", sDao.getSetting("maxinternals"));
		System.out.println(sDao.getSetting("maxinternals"));
		request.setAttribute("enrollmentStartExt", sDao.getSetting("enrollmentstartext"));
		
		
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
		SettingsDao sDao = new SettingsDao();
		Settings s = new Settings();
		
		UserDao ud = new UserDao();
		User  systemUser = ud.getUserByUsername(request.getUserPrincipal().getName());
		
		HttpSession session = request.getSession(true);
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser",systemUser);	
		
		s.setMaxParticipantsPerGroup(Integer.parseInt(request.getParameter("maxpergroup")));
		s.setMaxInternals(Integer.parseInt(request.getParameter("maxinternals")));
		s.setStartExternalsEnrollment(request.getParameter("enrollmentstartext"));
		
		ArrayList<Country> countries = (ArrayList<Country>) sDao.getAllCountries();
		Map<String, Integer> list = new HashMap<String, Integer>();
		for(int i = 0; i < countries.size(); i++){
			list.put(countries.get(i).getName(), Integer.parseInt((String) request.getParameter(countries.get(i).getName())));
		}
		sDao.setPeoplePerCountry(list);
		sDao.addSettings("maxpergroup", request.getParameter("maxpergroup"));
		sDao.addSettings("maxinternals", request.getParameter("maxinternals"));
		System.out.println(request.getParameter("maxinternals")+ "doPost");
		sDao.addSettings("enrollmentstartext", request.getParameter("enrollmentstartext"));
		response.sendRedirect("/snowdays-enrollment/private/index.html");
	}

}
