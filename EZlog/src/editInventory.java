import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



@WebServlet("/editInventory")
public class editInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public editInventory() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("user");
		ArrayList<String> params = new ArrayList<String>();
		String counter = request.getParameter("counter");
		int count = Integer.parseInt(counter);
		for (int i=0; i<count; i++){
			String param = request.getParameter("edit-input_"+i);
			params.add(param);
		}
		ArrayList <String> ColumnNames =  getColumns(user);
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserDatabases", "root", "root");
			Statement st = con.createStatement();
			String sql = createSQLStatement(user, params, ColumnNames);
			st.executeUpdate(sql);
			con.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}


	}
	public String createSQLStatement(String user, ArrayList<String> params, ArrayList<String> columns){
		String sql = "";
		sql += "update "+ user + "_inventory set ";
		for (int i=0; i<params.size(); i++){
			sql +=columns.get(i) + "='";
			sql += params.get(i) + "'";
			if (i!=params.size()-1){
				sql+=", ";
			}
		}
		sql+=" where ID='" + params.get(0) + "';";
//				+  + column1 + "='" +value1 + "'," + column2 + "='"
//						+ value2 + "' where " + idColumn + "='" + idNumber +"';";
//		
		
		return sql;
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

