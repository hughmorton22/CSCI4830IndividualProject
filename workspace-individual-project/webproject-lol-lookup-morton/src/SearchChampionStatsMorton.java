import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchChampionStats")
public class SearchChampionStatsMorton extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchChampionStatsMorton() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {

         DBConnectionMorton.getDBConnection();
         connection = DBConnectionMorton.connection;

         if (keyword.isEmpty()) {
        	 
            String selectSQL = "SELECT * FROM LolChampionsTableMorton";
            
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
        	 
        	 
            String selectSQL = "SELECT * FROM LolChampionsTableMorton WHERE name LIKE ?";
            String theName = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theName);
         }
         
         
         ResultSet rs = preparedStatement.executeQuery();
         
         
         while (rs.next()) {
        	 
            int id = rs.getInt("id");
            String name = rs.getString("name").trim();
            String hp = rs.getString("hp").trim();
            String mp = rs.getString("mp").trim();
            String ad = rs.getString("ad").trim();
            String as = rs.getString("as").trim();
            String ar = rs.getString("ar").trim();
            String mr = rs.getString("mr").trim();
            String ms = rs.getString("ms").trim();
            String rng = rs.getString("rng").trim();

            if (keyword.isEmpty() || name.contains(keyword)) {
               out.println("<b>Champion Name</b>: " + name + "<br>");
               out.println("<b>Base Health Points</b>: " + hp + "<br>");
               out.println("<b>Base Mana Points</b>: " + mp + "<br>");
               out.println("<b>Base Attack Damage</b>: " + ad + "<br>");
               out.println("<b>Base Attack Speed</b>: " + as + "<br>");
               out.println("<b>Base Armor</b>: " + ar + "<br>");
               out.println("<b>Base Magic Resistance</b>: " + mr + "<br>");
               out.println("<b>Base Movement Speed</b>: " + ms + "<br><br>");
               out.println("<b>Base Attack Range</b>: " + rng + "<br><br>");
            }
         }
         out.println("<a href=/webproject-lol-lookup-morton/search-build-morton.html>Search Champion Stats</a> <br>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
