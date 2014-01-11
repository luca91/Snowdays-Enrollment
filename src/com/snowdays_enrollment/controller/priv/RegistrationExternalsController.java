package com.snowdays_enrollment.controller.priv;

import java.io.IOException;
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
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.RegistrationExternal;
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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationExternalsController() {
        super();
        log.debug("###################################");
    	log.trace("START");
		dao = new RegistrationExternalsDao();
        log.debug("Dao object instantiated");
        log.trace("END");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.trace("START");
		UserDao uDao = new UserDao();
		systemUser = uDao.getUserByUsername(request.getUserPrincipal().getName());
		session = request.getSession();
		session.removeAttribute("systemUser");
		session.setAttribute("systemUser", systemUser);
		session.setMaxInactiveInterval(1200);
		
		if(systemUser.getRole().equals("admin")){
			log.debug("role: " + "admin");
			RegistrationExternalsDao reDao = new RegistrationExternalsDao();
			request.setAttribute("records", getRegistrationFinalList(reDao.getAllRegistration()));
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
		GroupDao gd = new GroupDao();
		List<String> names = gd.getAllGroupsNames();
		int p = 1;
		while(!list.isEmpty()){
			if(!added.contains(list.get(0).getGroupName()) && names.contains(list.get(0).getGroupName())){
				Group g = gd.getGroupByName(list.get(0).getGroupName());
				g.setTimeFirstRegistration(new RegistrationExternalsDao().getRegistrationByParticipantID(list.get(0).getParticipantID()).getTime());
				g.setPosition(p);
				result.add(g);
				added.add(list.get(0).getGroupName());
				p++;
			}
			list.remove(0);
		}
		return result;
	}

}
