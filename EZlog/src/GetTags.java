


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
 


@WebServlet("/GetTags")
public class GetTags extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetTags() {
		super();
	}
	
	
	public ArrayList<Tag> getTaggedItems(String table){
		ArrayList<Tag> tags = new ArrayList<Tag>();
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+table, "root", "root");
			Statement st = con.createStatement();
			String sql = ("SELECT * FROM tags;");
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
					String id = rs.getString(1);
					Tag tag = new Tag(id);
					tags.add(tag);
					//System.out.println(id);
			}
			con.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	    return tags;
	  }
	
	public ArrayList<Item> getItemTable(String table){
		ArrayList<Item> items = new ArrayList<Item>();
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ table, "root", "root");

			Statement st = con.createStatement();
			String sql = ("SELECT * FROM items;");
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				String id = rs.getString(1);
				String title = rs.getString(2);
				double price = rs.getDouble(3);
				Item item = new Item(id, title, price);
				items.add(item);
				//System.out.println("Item id: "+item.id);
			}
			con.close();
		}
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return items;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get tags
		ArrayList<Tag> tags = getTaggedItems("virtualCart");
		//get inventory items
		ArrayList<Item> items = getItemTable("Inventory");

		String output="";
		output+="<col width='12%'><col width='44%'><col width='44%'><tr><th>Price</th><th>Item</th><th>Tag</th></tr>";
		
		ArrayList<Item> itemsScanned = new ArrayList<Item>();

		//boolean itemNotInInventory=false;
		String unfoundID = " ";
		//Add tags that are in both Inventory and Virtual Cart to itemsScanned
		//						         
		//Also set tags that are in both db inInventory field to true
		for (int i=0; i<tags.size(); i++){
			for (int j=0; j<items.size(); j++){
				if (tags.get(i).id.equals(items.get(j).id)){
					itemsScanned.add(items.get(j));
					tags.get(i).inInventory=true;
				}	
			}
		}
		
		//Loop through tags in virtual Cart again and check
		//that they are all in inventory
		for (int m=0; m<tags.size(); m++){
			if (tags.get(m).inInventory == false){
				//itemNotInInventory=true;
				unfoundID= tags.get(m).id;
				break;
			}
		}
		
		double totalCost=0;
		for (int i=0; i<itemsScanned.size(); i++){
			if (i==tags.size()-1){
				output+="<tr style='border-bottom:solid 1px #ddd'><td>$"+itemsScanned.get(i).price+"</td>"
						+ "<td>"+itemsScanned.get(i).title+"</td>"
						+ "<td>"+itemsScanned.get(i).id+"</td></tr>";
				totalCost+=itemsScanned.get(i).price;
				
			}
			else{
				output+="<tr><td>$"+itemsScanned.get(i).price+"</td>"
						+ "<td>"+itemsScanned.get(i).title+"</td>"
						+ "<td>"+itemsScanned.get(i).id+"</td></tr>";
				totalCost+=itemsScanned.get(i).price;
			}
		}
		//if (itemNotInInventory){
			output+="~"+unfoundID;
		//}
		output+="~";
		//Cost Section
		if(totalCost>0){
			output+="<h2><a id='totalCost' class='label label-default'> $ "
					+ totalCost+ " total </a><h2>";
			output+="~<button style='float: right; margin-top: 10px' type='button'"
					+"class='btn btn-primary btn-lg' data-toggle='modal'"
					+"data-target='#paymentModal'>Pay</button>";
		}


		
		response.setContentType("text/html");
		response.getWriter().write(output);
		
		//System.out.println(output);
	}



	/*	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}*/

}
