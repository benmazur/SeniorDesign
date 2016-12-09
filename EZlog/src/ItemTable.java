import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemTable{
	public ArrayList<Item> items;

	public ItemTable(){
		items=getItemTable();
	}

	public ArrayList<Item> getItemTable(){
		ArrayList<Item> items = new ArrayList();
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Inventory", "root", "root");

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
}