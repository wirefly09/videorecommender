package com.personal.personaltest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;


/**
 * Servlet implementation class FirstServlet
 */
@WebServlet(description = "My First Servlet", urlPatterns = { "/FirstServlet" , "/FirstServlet.do"}, initParams = {@WebInitParam(name="id",value="1"),@WebInitParam(name="name",value="pankaj")})
public class Main extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public static final String HTML_START="<html><body>";
    public static final String HTML_END="</body></html>";
        
    	
	  public static void main(String[] args) throws Exception {
		    Server server = new Server(Integer.valueOf(System.getenv("PORT")));
		    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		    context.setContextPath("/");
		    server.setHandler(context);
		    context.addServlet(new ServletHolder(new Main()),"/*");
		    server.start();
		    server.join();
	  }
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }
 
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("userName");
    	String email = request.getParameter("email");
    	String ip = request.getRemoteAddr();
    	
    	
    	VideoRecommender vid1 = new VideoRecommender();
    	vid1.ItemSimilarity_run();
    	
    	PrintWriter out = response.getWriter();
    	out.println(HTML_START + "<h2>Hi There!</h2><br/><h3>Name:"+ name +"</h3></br>" + "Email:" + email + "</br>" + "</br>" + HTML_END);   
    	
        if (request.getRequestURI().endsWith("/db")) {
            showDatabase(request,response);
          } else {
            showHome(request,response);
          }
    }
 
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
    
	private void showHome(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	    RelativisticModel.select();
	    Amount<Mass> m = Amount.valueOf("12 GeV").to(KILOGRAM);
	
	    String energy = System.getenv().get("ENERGY");
	    resp.getWriter().print("E=mc^2:" + energy + " = " + m);
	  }
	
	  private void showDatabase(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {
	    Connection connection = null;
	    try {
	      connection = getConnection();
	
	      Statement stmt = connection.createStatement();
	      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
	      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
	      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
	
	      String out = "Hello!\n";
	      while (rs.next()) {
	          out += "Read from DB: " + rs.getTimestamp("tick") + "\n";
	      }
	
	      resp.getWriter().print(out);
	    } catch (Exception e) {
	      resp.getWriter().print("There was an error: " + e.getMessage());
	    } finally {
	      if (connection != null) try{connection.close();} catch(SQLException e){}
	    }
	  }
	
	  /**
	   * Connect to the postgresdatabase in heroku 
	   * @return
	   * @throws URISyntaxException
	   * @throws SQLException
	   */
	  private Connection getConnection() throws URISyntaxException, SQLException {
	    URI dbUri = new URI(System.getenv("DATABASE_URL"));
	
	    String username = dbUri.getUserInfo().split(":")[0];
	    String password = dbUri.getUserInfo().split(":")[1];
	    int port = dbUri.getPort();
	
	    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + port + dbUri.getPath();
	
	    return DriverManager.getConnection(dbUrl, username, password);
	  }
	  
	  

}