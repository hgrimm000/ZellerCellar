package edu.ycp.cs320.project.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.project.controller.SignupController;

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Signup Servlet: doGet");
		
		req.getRequestDispatcher("/_view/signup.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
	        throws ServletException, IOException {

	    System.out.println("Signup Servlet: doPost");

	    //get input parameters
	    String errorMessage = "";
	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    //create new controller
	    SignupController controller = new SignupController();

	    boolean validSignup = controller.validateSignup(username, password);
	    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
	        // display error message
	        //req.setAttribute("errorMessage", "Username or password cannot be empty.");
	        req.getRequestDispatcher("/_view/signup.jsp").forward(req, resp);
	    } else if (validSignup) {
	        //redirect to login page
	    	req.getSession().setAttribute("user", username);

			//go to start game page
			resp.sendRedirect(req.getContextPath() + "/start_game");
	    } else {
	        //display error message
	    	errorMessage = "Username/Password contains restricted symbols: |;^\' ";
	    	req.setAttribute("errorMessage", errorMessage);
	        req.getRequestDispatcher("/_view/signup.jsp").forward(req, resp);
	    }

	}
}