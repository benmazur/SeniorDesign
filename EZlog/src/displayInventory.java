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
@WebServlet("/displayInventory")
public class displayInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("user");
		String output = generateJSON(user);
		response.setContentType("text/json");
		response.getWriter().write(output);

	}

	public String generateJSON(String user){
		String JSONdata = "[";
		ArrayList <String> ColumnNames = getColumns(user);
		int columnCount = ColumnNames.size(); 
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserDatabases", "root", "root");
			Statement st = con.createStatement();
			String sql = ("SELECT * FROM " + user+"_inventory;");
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				JSONdata += "{";
				for (int i = 1; i <= columnCount; i++) {
					if (i!=columnCount){
						JSONdata += "\""+ ColumnNames.get(i-1) + "\":\"" + rs.getString(i) + "\", "; 
					}
					else{
						JSONdata += "\""+ ColumnNames.get(i-1) + "\":\"" + rs.getString(i) + "\"},";
					}
				}
			}
			JSONdata=JSONdata.substring(0,JSONdata.length()-1);
			con.close();
		}catch (Exception e){
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		JSONdata+="]";
		//System.out.println(JSONdata);
		return JSONdata;
	}





	public ArrayList <String> getColumns(String user){
		ArrayList <String> ColumnNames = new ArrayList<String>();
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserDatabases", "root", "root");
			Statement st = con.createStatement();
			String sql = ("describe " + user +"_inventory;");
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				ColumnNames.add(rs.getString(1));
			}
			con.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return ColumnNames;
	}
}