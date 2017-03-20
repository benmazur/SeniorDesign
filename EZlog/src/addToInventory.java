


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



@WebServlet("/addToInventory")
public class addToInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public addToInventory() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("user");
		ArrayList <String> ColumnNames = getColumns(user);
		String output = generateJSON(ColumnNames);
		response.setContentType("text/json");
		response.getWriter().write(output);
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("user");
		ArrayList<String> params = new ArrayList<String>();
		String counter = request.getParameter("counter");
		int count = Integer.parseInt(counter);
		for (int i=0; i<count; i++){
			String param = request.getParameter("input_"+i);
			params.add(param);
		}
		insertToTable(user, params);
		
	}
	public void insertToTable(String user, ArrayList<String> params){
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserDatabases", "root", "root");
			Statement st = con.createStatement();
			String sql = ("insert into " + user + "_inventory values(");
			for (int i = 0; i<params.size(); i++){
				if (i!=params.size()-1){
					sql+="'"+ params.get(i) + "',";
				}else{
					sql+="'"+ params.get(i) + "'";
				}
			}
			sql+=");";
			//System.out.println(sql);
			st.executeUpdate(sql);
			con.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
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
	
	public String generateJSON(ArrayList <String> ColumnNames){
		String JSONdata="[";
		for (int i=0; i<ColumnNames.size(); i++){
			JSONdata+="{";
			if (i!=ColumnNames.size()-1){
				JSONdata+= "\"column\":\""+ ColumnNames.get(i)+"\"},";
			}
			else{
				JSONdata+= "\"column\":\""+ ColumnNames.get(i)+"\"}]";
			}
		}
		//System.out.println(JSONdata);
		return JSONdata;
	}
	
}
