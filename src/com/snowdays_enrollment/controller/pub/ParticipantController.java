package com.snowdays_enrollment.controller.pub;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.model.Group;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.tools.Email;


/**
 * Servlet implementation class ParticipantController(public)
 */
@WebServlet(urlPatterns = {
		"/public/enrollmentForm.html", 
		"/public/participantAdd"
		})
public class ParticipantController extends HttpServlet {
	
	// commons logging references
	static Logger log = Logger.getLogger(ParticipantController.class.getName());
	
	private static final long serialVersionUID = 1L;

	private static String form = "/public/jsp/participant.jsp";
	private static String showFeedback = "/public/participantFeedback.jsp";
	
	private static String feedback = "enrollmentForm.html?message=y";
	
    /**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private ParticipantDao dao;   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ParticipantController() {
        super();
        log.debug("###################################");
    	log.trace("START");
		dao = new ParticipantDao();
        log.debug("Dao object instantiated");
        log.trace("END");
    }

	/**
	 * doGet method - maps the normal pages
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param HttpServletRequest request, HttpServletResponse response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("START");
		log.debug("-- " + request.getParameter("message"));
		
		boolean showForm = true;
		if (request.getParameter("message") != null){
			showForm = false;
		}
		
		if (showForm) {
			log.debug("show the form to enrollment");
			log.debug("id_group: " + request.getParameter("id_group"));
	    	if (request.getParameter("id_group") != null){
	    		log.debug("doget - load the form");
	    		int id_group = Integer.parseInt(request.getParameter("id_group").toString());
	        	request.setAttribute("id_group", id_group);
	        	request.setAttribute("email", request.getParameter("email"));
	    		try {
	    			getServletConfig().getServletContext().getRequestDispatcher(form).forward(request, response);
	    			} 
	    		catch (Exception ex) {
	    				ex.printStackTrace();
	    			}
	    	}
		}
		else {
			log.debug("show a message");
			try {
    			getServletConfig().getServletContext().getRequestDispatcher(showFeedback).forward(request, response);
    			} 
    		catch (Exception ex) {
    				ex.printStackTrace();
    			}
		}

	}

	/**
	 * doPost method - maps the url that comes from a form
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * 
	 * @param (HttpServletRequest request, HttpServletResponse response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	log.trace("START");
    	Participant record = new Participant();
    	
	

    	int	id_group = Integer.parseInt(request.getParameter("id_group").toString());

    	log.debug("----------------> id_group: " + id_group);
    	log.debug("----------------> email: " + request.getParameter("email"));
    	
		record.setId_group(id_group);
    	record.setFname(request.getParameter("fname"));
    	record.setLname(request.getParameter("lname"));
    	record.setEmail(request.getParameter("email"));
    	record.setDate_of_birth(request.getParameter("date_of_birth"));
    	
    	String currentdate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    	log.debug("current date&time: " + currentdate);
    	record.setRegistrationTime(currentdate);;
    	record.setApproved(false);
    	
    	String id = request.getParameter("id");
        
    	log.debug("id: " + id);
    	log.debug("INSERT");
    	
    	//Check if the group has still place
    	GroupDao gdao = new GroupDao();
    	Group g = gdao.getRecordById(id_group);
    	int alreadyEnrolled = gdao.getNrEnrolledParticipant(id_group);
    	
    	request.setAttribute("group", g);
    	request.setAttribute("nrEnrolledParticipant", alreadyEnrolled);
    	
    	log.debug("g.getMax_group_number(): " + g.getGroupMaxNumber());
    	log.debug("alreadyEnrolled: " + alreadyEnrolled);
    	
    	if(alreadyEnrolled < g.getGroupMaxNumber()){
    		log.debug("places are available");
	        dao.addRecord(id_group, record);
	        request.setAttribute("record", record);
	        
	        try {
				sendEmail(record, request.getParameter("email").toString());
				feedback = "enrollmentForm.html?message=y&sentEmail=y";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else {
    		log.debug("places are NOT available");
			feedback = "enrollmentForm.html?message=y&sentEmail=n";
    		
    	}
    	log.debug(feedback);
        response.sendRedirect(feedback);
    	
    	log.trace("END");
	}   
	
	
	/**
	 * sendMail send an emaiil
	 * 
	 * 
	 * @param p A Participant
	 * @param to An email address
	 */
    private boolean sendEmail(Participant p, String to) throws ParseException{ 
    	log.debug("address: " + to);

	
        log.debug("TO: " + to);
        String subject = "Subscription registered";
        
        String message = "";
        message += "Hi " + p.getFname() + "\n";
        message += "\nYour subscription has been registered by the system\n";
        message += "\n\nThe staff";
		
		Email e = new Email();
		return e.sendEmail(to, subject, message);
    }
	
}
