package com.snowdays_enrollment.controller.priv;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.snowdays_enrollment.dao.GroupDao;
import com.snowdays_enrollment.dao.ParticipantDao;
import com.snowdays_enrollment.dao.UserDao;
import com.snowdays_enrollment.model.Participant;
import com.snowdays_enrollment.model.User;
import com.snowdays_enrollment.pdf.DOCSGenerator;
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
	
	private HttpSession session;
	private Connection c;
	
	/**
	 * @uml.property  name="dao"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	
	private int id_group;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DocumentsController() {
        super();
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
        	id_group = Integer.parseInt(request.getParameter("id_group")); 
        	List<Participant> records  = pDao.getAllRecordsById_group(id_group);
        	System.out.println("Records size: " + records.size());
            request.setAttribute("records", pDao.getAllRecordsById_group(id_group));
            GroupDao gd = new GroupDao(c);
            request.setAttribute("groups", gd.getAllRecords());
     
        	DOCSGenerator docg = null;
        	try {
        		File outputFolder = new File(getServletConfig().getServletContext().getRealPath("/")+"/pdf/");
        		outputFolder.mkdir();
        		docg = new DOCSGenerator (gd.getRecordById(id_group).getName(), (ArrayList<Participant>) records, outputFolder.getAbsolutePath());
        		docg.setImagePath(getServletContext().getRealPath("/private/images/Logo_orizzontale_2014.png"));
        		docg.setHeaderText(getServletContext().getRealPath("/private/docsblueprints/header"));
        		docg.setFooterText(getServletContext().getRealPath("/private/docsblueprints/footer"));
        		docg.setAgreementBodyTextFirst(getServletContext().getRealPath("/private/docsblueprints/agreement_body"));
        		docg.setAgreementBodyTextSecond(getServletContext().getRealPath("/private/docsblueprints/agreement_body_2"));
        		docg.setFooterText(getServletContext().getRealPath("/private/docsblueprints/footer"));
        		docg.setDocument();
        		docg.writeDocument();
        		docg.closePdf();
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}       	
        	
        	forward = docg.getFileLocation();
        }
        else if(request.getParameter("id") != null){
        	GroupDao gd = new GroupDao(c);
        	DOCSGenerator docg = null;
        	File outputFolder = new File(getServletConfig().getServletContext().getRealPath("/")+"/pdf/");
//    		outputFolder.mkdir();
    		Participant p = pDao.getRecordById((Integer.parseInt(request.getParameter("id"))));
        	docg = new DOCSGenerator (gd.getRecordById(id_group).getName(), p, outputFolder.getAbsolutePath());
    		docg.setImagePath(getServletContext().getRealPath("/private/images/Logo_orizzontale_2014.png"));
    		docg.setHeaderText(getServletContext().getRealPath("/private/docsblueprints/header"));
    		docg.setAgreementBodyTextFirst(getServletContext().getRealPath("/private/docsblueprints/agreement_body"));
    		try {
				docg.setDocument();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
    		try {
				docg.writeDocument();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
    		docg.closePdf();
			
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
