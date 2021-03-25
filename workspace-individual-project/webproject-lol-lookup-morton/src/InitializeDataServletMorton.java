import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Comparator;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.UtilFile;

@WebServlet("/MyServletDB0204Morton")
public class InitializeDataServletMorton extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InitializeDataServletMorton() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) //
         throws ServletException, IOException {
      response.setContentType("text/html");
      String filename = "/WEB-INF/champions-stats.csv";
      List<String> contents = UtilFile.readFile(getServletContext(), filename);
      
      
      String name = null;
      String hp = null;
      String mp = null;
      String ad = null;
      String as = null;
      String ar = null;
      String mr = null;
      String ms = null;
      String rng = null;
      
      String[] currentData = null;
      
      Connection connection = null;
      String insertSql = " INSERT INTO LolChampionsTableMorton (id, name, hp, mp, ad, atks, ar, mr, ms, rng) values (default, ?, ?, ?, ?, ?, ?, ?, ?,?)";
      
      for (int i = 0; i < contents.size(); i++) {
    	 currentData = contents.get(i).split(",");
    	 name = currentData[0];
         hp = currentData[1];
         mp = currentData[2];
         ad = currentData[3];
         as = currentData[4];
         ar = currentData[5];
         mr = currentData[6];
         ms = currentData[7];
         rng = currentData[8];
         
         try {
             DBConnectionMorton.getDBConnection();
             connection = DBConnectionMorton.connection;
             PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
             preparedStmt.setString(1, name);
             preparedStmt.setString(2, hp);
             preparedStmt.setString(3, mp);
             preparedStmt.setString(4, ad);
             preparedStmt.setString(5, as);
             preparedStmt.setString(6, ar);
             preparedStmt.setString(7, mr);
             preparedStmt.setString(8, ms);
             preparedStmt.setString(9, rng);
             preparedStmt.execute();
             connection.close();
          } catch (Exception e) {
             e.printStackTrace();
          }
      }
      
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}