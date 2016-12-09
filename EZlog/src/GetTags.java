


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


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output="";
		output+="<col width='12%'><col width='44%'><col width='44%'><tr><th>Price</th><th>Item</th><th>Tag</th></tr>";
		ArrayList<Item> itemsScanned = new ArrayList();
		TagTable tagTable = new TagTable();
		ItemTable itemTable = new ItemTable();
		//boolean itemNotInInventory=false;
		String unfoundID = " ";
		

		//Add tags that are in both Inventory and Virtual Cart to itemsScanned
		//						   (itemTable)      (tagTable)
		//Also set tags that are in both db inInventory field to true
		for (int i=0; i<tagTable.tags.size(); i++){
			for (int j=0; j<itemTable.items.size(); j++){
				if (tagTable.tags.get(i).id.equals(itemTable.items.get(j).id)){
					itemsScanned.add(itemTable.items.get(j));
					tagTable.tags.get(i).inInventory=true;
				}	
			}
		}
		
		//Loop through tags in virtual Cart again and check
		//that they are all in inventory
		for (int m=0; m<tagTable.tags.size(); m++){
			if (tagTable.tags.get(m).inInventory == false){
				//itemNotInInventory=true;
				unfoundID= tagTable.tags.get(m).id;
				break;
			}
		}
		
		double totalCost=0;
		for (int i=0; i<itemsScanned.size(); i++){
			if (i==tagTable.tags.size()-1){
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
