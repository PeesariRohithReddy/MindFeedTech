package com.mail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Email
 */
@WebServlet("/Email")
public class Email extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Email() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		response.setContentType("text/html");		
		String name=request.getParameter("name");
		String email=request.getParameter("emailid");
		String mail=request.getParameter("message");
		
		
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String to = "rvangeti@mindfeedtech.com";
        String subject = "User Messsage From MindFeedTech.com";
        String message="Hi Raghunath,\n\n Below are the details of an aspirant \n\n name:"+name+"\n\n"+"email:"+email+"\n\n"+"message:"+mail;
        String user = "hr@mindfeedtech.com";
        String pass = "admin@123";
        MailSend.send(to,subject, message, user, pass);
        RequestDispatcher rd=request.getRequestDispatcher("/thankyou.html");
        rd.include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);	
		
	}

}
