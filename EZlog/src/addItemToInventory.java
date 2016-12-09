


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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



@WebServlet("/addItemToInventory")
public class addItemToInventory extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public addItemToInventory() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("val");
		String title = request.getParameter("title");
		String price = request.getParameter("price");
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localHost:3306/Inventory", "root", "root");
			
			Statement st = con.createStatement();
			String sql = ("insert into items values (‘" + id + "‘,‘"+title+"‘,"+price+");");
			int rs = st.executeUpdate(sql);
			con.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		
	}



	/*	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}*/

}
