import java.sql.*;

public class tableUpdater {
	
	//get incoming tag info
	//compare incoming tag info to what is already listed in table of database
	//if (tag info is not in table){
	//	insert tag_info in table
	//}
	//else if(tag info is in table){
	//	do nothing?
	//}

	public void updateTable(){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
		}catch(Exception e){

		}
	}
}
