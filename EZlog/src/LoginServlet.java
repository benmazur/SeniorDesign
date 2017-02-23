import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");

		if(valid(user, pwd)){
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("user", user);
			response.addCookie(userName);
			response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
			// Set standard HTTP/1.0 no-cache header.
			response.setHeader("Pragma", "no-cache");
			//Get the encoded URL string
			String encodedURL = response.encodeRedirectURL("LoginSuccess.jsp");
			response.sendRedirect(encodedURL);
		}else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
			PrintWriter out= response.getWriter();
			out.println("<font color=red>Either user name or password is wrong.</font>");
			rd.include(request, response);
		}

	}
	
	public boolean valid(String username, String password){
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EZlogUsers", "root", "root");
			Statement st = con.createStatement();
			String sql = ("SELECT * FROM users;");
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				String user = rs.getString(1);
				if (username.equals(user)){
					String pwd = rs.getString(2);
					if (pwd.equals(password)){
						return true;
					}
					else{
						return false;
					}
				}
			}
			con.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());	
		}
		return false;
	}

}