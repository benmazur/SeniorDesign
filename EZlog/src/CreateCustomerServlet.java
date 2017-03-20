
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet("/CreateCustomerServlet")
public class CreateCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateCustomerServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String username = request.getParameter("username-register");
		String password = request.getParameter("password-register");
		String confirmPassword = request.getParameter("confirm-password-register");
		String email = request.getParameter("email");

		if(password.equals(confirmPassword)){
			try{  
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EZlogUsers", "root", "root");
				Statement st = con.createStatement();
				String sql = ("insert into users values('"
						+ username + "', '"
						+ password + "', '"
						+ email + "', '0');");
				st.executeUpdate(sql);
				con.close();
				Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserDatabases", "root", "root");
				String sql1 = ("create table " + username +"_inventory(ID varchar(30), "
						+ "Name varchar(30), Description varchar(255));");
				Statement st1 = con1.createStatement();
				st1.executeUpdate(sql1);
				con1.close();
				
			}
			catch (Exception e)
			{
				System.err.println("Got an exception! ");
				System.err.println(e.getMessage());
			}
			String encodedURL = response.encodeRedirectURL("RegisterSuccess.html");
			response.sendRedirect(encodedURL);
		}
		else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			PrintWriter out= response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Passwords do not match');");
			out.println("location='index.html';");
			out.println("</script>");
			rd.include(request, response);
		}
	}
}

