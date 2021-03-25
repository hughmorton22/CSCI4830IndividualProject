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

@WebServlet("/ByChampionNameSearch")
public class ByChampionNameMorton extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public ByChampionNameMorton() {
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
        	 
            String selectSQL = "SELECT * FROM LolBuildsTableMorton";
            
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
        	 
        	 
            String selectSQL = "SELECT * FROM LolBuildsTableMorton WHERE champion LIKE ?";
            String theChampion = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theChampion);
         }
         
         
         ResultSet rs = preparedStatement.executeQuery();
         
         
         while (rs.next()) {
        	 
            int id = rs.getInt("id");
            String name = rs.getString("name").trim();
            String champion = rs.getString("champion").trim();
            String mythic = rs.getString("mythic_item").trim();
            String legendary1 = rs.getString("legendary_item1").trim();
            String legendary2 = rs.getString("legendary_item2").trim();
            String legendary3 = rs.getString("legendary_item3").trim();
            String legendary4 = rs.getString("legendary_item4").trim();
            String boots = rs.getString("boots").trim();

            if (keyword.isEmpty() || champion.contains(keyword)) {
               out.println("<b>Build Name</b>: " + name + "<br>");
               out.println("<b>Champion</b>: " + champion + "<br>");
               out.println("<b>Mythic Item</b>: " + mythic + "<br>");
               out.println("<b>Legendary Item 1</b>: " + legendary1 + "<br>");
               out.println("<b>Legendary Item 2</b>: " + legendary2 + "<br>");
               out.println("<b>Legendary Item 3</b>: " + legendary3 + "<br>");
               out.println("<b>Legendary Item 4</b>: " + legendary4 + "<br>");
               out.println("<b>Boots</b>: " + boots + "<br><br>");
            }
         }
         out.println("<a href=/webproject-lol-lookup-morton/search-morton.html>Search Data</a> <br>");
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
