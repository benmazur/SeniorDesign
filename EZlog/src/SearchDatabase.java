import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Servlet that interacts with a search bar that narrows down a list of existing node names based on the characters already in the search bar
@WebServlet("/SearchDatabase")
public class SearchDatabase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public SearchDatabase() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Retrieves the value of the input characters from the request url
		String q = request.getParameter("q");
		
		
		response.setContentType("text/html");

	}
}
