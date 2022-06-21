import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/CorreosSiractBean")
public class ServletInsercion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/CorreosSiractBean")
	private DataSource fuente;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sql = "insert into cursos  values (2,'java') ";

		try (Connection conn = fuente.getConnection(); Statement stmt = conn.createStatement();) {

			stmt.executeUpdate(sql);
			
		} catch (Exception se) {

			throw new RuntimeException("error SQL", se);
		}
	}

}