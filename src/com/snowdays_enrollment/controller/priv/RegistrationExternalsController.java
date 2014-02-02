package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.RegistrationExternalsDao;
import com.snowdays_enrollment.dao.SettingsDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Country;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.RegistrationExternal;
import com.snowdays_enrollment.model.Settings;
import com.snowdays_enrollment.model.User;

/**
 * Servlet implementation class RegistrationController
 */
@WebServlet("/private/externalsRegistrations.html")
public class RegistrationExternalsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static Logger log = Logger.getLogger(RegistrationExternalsController.class.getName());
	
	private RegistrationExternalsDao dao;
	private String forward="";
	private User systemUser;
	HttpSession session;
	Settings s;
	SettingsDao sDao;
	RegistrationExternalsDao reDao; 
	int actualTotal;
	private Connection c;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationExternalsController() {
        super();
        log.debug("###################################");
    	log.trace("START");
		dao = new RegistrationExternalsDao(c);
		sDao = new SettingsDao(c);
		s = new Settings();
        log.debug("Dao object instantiated");
        log.trace("END");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");
		session = request.getSession(true);
		c = (Connection) session.getAttribute("DBConnection");
		UserDao uDao = new UserDao(c);
		systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser", systemUser);
		
		dao = new RegistrationExternalsDao(c);
		sDao = new SettingsDao(c);
		reDao = new RegistrationExternalsDao(c);
		s = sDao.getAllSettings();
		s.setCountries(sDao.getAllCountries());
		actualTotal = 0;
		sDao.setDefaultPeopleCountry();
		
		s = new Settings();
		s = sDao.getAllSettings();
		s.setCountries(sDao.getAllCountries());
		
		request.setAttribute("totalParticipants", sDao.getSetting("maxexternals"));
		request.setAttribute("totalRegistered", reDao.getRegistrationsCount());
		
		
		if(systemUser.getRole().equals("admin")){
			log.debug("role: " + "admin");
			RegistrationExternalsDao reDao = new RegistrationExternalsDao(c);
			request.setAttribute("records", getRegistrationFinalList(reDao.getAllRegistration()));
			request.setAttribute("actualParticipants", actualTotal);
		}
		
		forward = "/private/jsp/registrationsExternals.jsp";
		log.debug("forward: " + forward);
		
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
		// TODO Auto-generated method stub
	}
	
	public List<Group> getRegistrationFinalList(List<RegistrationExternal> list){
		List<Group> result = new ArrayList<Group>();
		List<String> added = new ArrayList<String>();
		GroupDao gd = new GroupDao(c);
		List<String> names = gd.getAllGroupsNames();
		int p = 1;
		while(!list.isEmpty()){
			if(!added.contains(list.get(0).getGroupName()) 
					&& names.contains(list.get(0).getGroupName()) 
					&& checkGroupPeoplePerCountry(gd.getGroupByName(list.get(0).getGroupName()).getCountry(),
							gd.getGroupByName(list.get(0).getGroupName()).getActualParticipantNumber())
							&& checkTotalPeople(gd.getGroupByName(list.get(0).getGroupName()).getActualParticipantNumber())){
				Group g = gd.getGroupByName(list.get(0).getGroupName());
				g.setTimeFirstRegistration(new RegistrationExternalsDao(c).getRegistrationByParticipantID(list.get(0).getParticipantID()).getTime());
				g.setPosition(p);
				actualTotal += g.getActualParticipantNumber();
				
				gd.updateRecord(g);
				result.add(g);
				added.add(list.get(0).getGroupName());
				p++;
			}
			list.remove(0);
		}
		return result;
	}
	
	public boolean checkGroupPeoplePerCountry(String country, int groupActualNr){
		int[] countryNR = new int[2];
		Country c = s.getCountry(country);
		if(c == null)
			log.debug("country: null");
		countryNR[0] = c.getMaxPeople();
		countryNR[1] = c.getActualPeople();
		int placesLeft = countryNR[0] - countryNR[1];
		log.debug("max people: "+countryNR[0]);
		log.debug("actual people: "+countryNR[1]);
		log.debug("places left: "+placesLeft);
		if(groupActualNr <= placesLeft && groupActualNr >= 5)
			return true;
		return false;
	}
	
	public boolean checkTotalPeople(int groupActualNr){
		int totalMaxPeople = Integer.parseInt(sDao.getSetting("maxexternals"));
		int actualPeople = reDao.getRegistrationsCount();
		int actualUpdate = actualPeople+groupActualNr;
		int placesLeft = totalMaxPeople-actualPeople;
		int maxExceed = placesLeft*2;
		if(actualUpdate <= totalMaxPeople)
			return true;
		else if (maxExceed <= 10)
			if(actualUpdate <= (totalMaxPeople+maxExceed))
				return true;
		return false;
	}
}
