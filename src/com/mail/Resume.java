package com.mail;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Resume
 */
@WebServlet("/Resume")

@MultipartConfig(fileSizeThreshold   = 1024 * 1024 * 1,
maxFileSize         = 1024 * 1024 * 10,
maxRequestSize      = 1024 * 1024 * 15)
public class Resume extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String UPLOAD_DIR="resources";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Resume() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	try {
	   String name=request.getParameter("name");
	   String email=request.getParameter("emailid");
	   Part part=request.getPart("resume");
	   String filename=extractfilename(part);
	   
	   
	   
	   if(!filename.equals("")  || !filename.isEmpty()) 
	   {
		   
		   String applicationpath=getServletContext().getRealPath("");
		   String uploadPath=applicationpath + File.separator+UPLOAD_DIR;
		   System.out.println("application path:"+applicationpath);
		   File fileUploadDirectory=new File(uploadPath);
		   if(!fileUploadDirectory.exists()) {
			   fileUploadDirectory.mkdirs();
		   }
		   String savepath=uploadPath+File.separator+filename;
		   //System.out.println("SavePath:"+savepath);
		   part.write(savepath+File.separator);
		   final String user = "hr@mindfeedtech.com";
	       final String pass = "admin@123";
	       final String to="rvangeti@mindfeedtech.com";
	       Properties props = new Properties();
	       props.put("mail.smtp.host", "smtp.gmail.com");
	       //below mentioned mail.smtp.port is optional
	       props.put("mail.smtp.port", "587");		
	       props.put("mail.smtp.auth", "true");
	       props.put("mail.smtp.starttls.enable", "true");
	       
	       Session session = Session.getInstance(props,new javax.mail.Authenticator()
	       {
	     	  protected PasswordAuthentication getPasswordAuthentication() 
	     	  {
	     	 	 return new PasswordAuthentication(user,pass);
	     	  }
	      });
	     try
	     {
	    	 Message message=new MimeMessage(session);
	    	 message.setFrom(new InternetAddress(user));
	    	 message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
	    	 message.setSubject("Resume Submitted from MindFeedTech");
	    	 MimeBodyPart textpart=new MimeBodyPart();
	    	 MimeBodyPart messagebodypart=new MimeBodyPart();
	    	 Multipart multipart=new MimeMultipart();
	    	 DataSource source=new FileDataSource(savepath);
	    	 messagebodypart.setDataHandler(new DataHandler(source));
	    	 messagebodypart.setFileName(filename);
	    	 multipart.addBodyPart(messagebodypart);
	    	 String Text="Hi Raghunath,\n\n\n"+
	    	             "Below are the details of an aspirant\n"
	    	 		+ "name:"+name+"\nemail:"+email+"\nPFA";
	    	 textpart.setText(Text);
	    	 multipart.addBodyPart(textpart);
	    	 message.setContent(multipart);
	    	 Transport.send(message);
	    	 MimeMessage message_user = new MimeMessage(session);
	         message_user.setFrom(new InternetAddress(user));
	         message_user.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
	         message_user.setSubject("Message from MindFeedTechnologies");
	         message_user.setText("Hi "+name+",\n\n\nYour resume is successfully submitted to mindfeed technologies.\n We will get back to you soon.");
	         Transport.send(message_user);
	    	 RequestDispatcher rd=request.getRequestDispatcher("/thankyou.html");
	         rd.include(request, response);
	     }
	     catch (Exception e) {
	    	 System.out.println("Exception:"+e);
		}   
		   
	   }
	   else
	   {
		   System.out.println("No File Choosen");
	   }
	}
	catch (Exception e) {
		// TODO: handle exception
		System.out.println("Exception:"+e);
	}
	
	   
	}

	private String extractfilename(Part part) {
		// TODO Auto-generated method stub
		String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
		return "";
	}

}
