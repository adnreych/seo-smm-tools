
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetMailServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter writer = response.getWriter();
		String queries = request.getParameter("queries");
		ArrayList<String> ar = new ArrayList<String>(Arrays.asList(queries.split("\n")));

		ArrayList<String> ar2 = ar.stream().map(x -> x.substring(0, x.length() - 1))
				.collect(Collectors.toCollection(ArrayList::new));
		GetMail gm = new GetMail(ar2);
		writer.println("<html> <head> <title>Результат проверки</title></head>" + "<body>" + gm.getMail() + "queries="
				+ ar2 + "</body>" + "</html>");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
