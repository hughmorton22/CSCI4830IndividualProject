
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class InsertMorton extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertMorton() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String name = request.getParameter("name");
      String champion = request.getParameter("champion");
      String mythic = request.getParameter("mythicItem");
      String legendary1 = request.getParameter("legendaryItem1");
      String legendary2 = request.getParameter("legendaryItem2");
      String legendary3 = request.getParameter("legendaryItem3");
      String legendary4 = request.getParameter("legendaryItem4");
      String boots = request.getParameter("boots");

      Connection connection = null;
      String insertSql = " INSERT INTO LolBuildsTableMorton (id, name, champion, mythic_item, legendary_item1, "
      		+ "legendary_item2, legendary_item3, legendary_item4, boots) values (default, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnectionMorton.getDBConnection();
         connection = DBConnectionMorton.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, name);
         preparedStmt.setString(2, champion);
         preparedStmt.setString(3, mythic);
         preparedStmt.setString(4, legendary1);
         preparedStmt.setString(5, legendary2);
         preparedStmt.setString(6, legendary3);
         preparedStmt.setString(7, legendary4);
         preparedStmt.setString(8, boots);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Data to DB table";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Build Name</b>: " + name + "\n" + //
            "  <li><b>Champion</b>: " + champion + "\n" + //
            "  <li><b>Mythic Item</b>: " + mythic + "\n" + //
            "  <li><b>Legendary Item 1</b>: " + legendary1 + "\n" + //
            "  <li><b>Legendary Item 2</b>: " + legendary2 + "\n" + //
            "  <li><b>Legendary Item 3</b>: " + legendary3 + "\n" + //
            "  <li><b>Legendary Item 4</b>: " + legendary4 + "\n" + //
            "  <li><b>Boots</b>: " + boots + "\n" + //

            "</ul>\n");

      out.println("<a href=/webproject-lol-lookup-morton/search-build-morton.html>Search Build Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
