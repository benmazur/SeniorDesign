import java.sql.*;  
class MySqlCon{
	
	
	public static void main(String args[]){  
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

			Statement st = con.createStatement();
			String sql = ("SELECT * FROM tags;");
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				//if (rs.next()){
					System.out.println(rs.getInt(1));  
				//}
			}
			con.close();
		}catch(Exception e){ System.out.println(e);}  
	}  
} 
