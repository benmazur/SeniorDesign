import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Servlet that interacts with a search bar that narrows down a list of existing node names based on the characters already in the search bar
@WebServlet("/SearchDatabase")
public class SearchDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SearchDatabase() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Retrieves the value of the input characters from the request url	
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("user");
		String value = request.getParameter("val");
		String output = generateJSON(user, value);
		//System.out.println(output);
		response.setContentType("text/json");
		response.getWriter().write(output);

	}
	
	public String generateJSON(String user, String searchValue){
		String JSONdata = "[";
		ArrayList <String> ColumnNames = getColumns(user);
		int columnCount = ColumnNames.size(); 
		try{  
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserDatabases", "root", "root");
			Statement st = con.createStatement();
			String sql = ("SELECT * FROM " + user+"_inventory where ");
			for (int j=0; j<columnCount; j++){
				if (j!=columnCount-1){
					sql+= ColumnNames.get(j) + " like '" + searchValue + "%' or ";
				}else{
					sql+= ColumnNames.get(j) + " like '" + searchValue + "%';";
				}
				
			}
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
