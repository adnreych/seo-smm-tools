
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetMailServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter writer = response.getWriter();
		String queries = request.getParameter("queries");
		String[] queriesArray = queries.split("\n");
		GetMail gm = new GetMail(new ArrayList<String>(Arrays.asList(queriesArray)));

		writer.println("<html> <head> <title>Результат проверки</title></head>" + "<body>" + gm.getMail() + "</body>"
				+ "</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
