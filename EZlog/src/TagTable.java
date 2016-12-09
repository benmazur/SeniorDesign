
import java.sql.*;
import java.util.ArrayList;  


/**
 * A Java MySQL SELECT statement example.
 * Demonstrates the use of a SQL SELECT statement against a
 * MySQL database, called from a Java program.
 * 
 * Created by Alvin Alexander, http://devdaily.com
 */
public class TagTable{
	public ArrayList<Tag> tags;

	public TagTable(){
		tags=getTagTable();
	}

  public ArrayList<Tag> getTagTable(){
	ArrayList<Tag> tags = new ArrayList();
	try{  
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/virtualCart", "root", "root");
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
}