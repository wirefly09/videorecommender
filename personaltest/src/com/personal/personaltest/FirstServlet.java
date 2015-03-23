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


/**
 * Servlet implementation class FirstServlet
 */
@WebServlet(description = "My First Servlet", urlPatterns = { "/FirstServlet" , "/FirstServlet.do"}, initParams = {@WebInitParam(name="id",value="1"),@WebInitParam(name="name",value="pankaj")})
public class FirstServlet extends HttpServlet {
    /**
	private static final long serialVersionUID = 1L;
    public static final String HTML_START="<html><body>";
    public static final String HTML_END="</body></html>";
        
   
    public FirstServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("userName");
    	String email = request.getParameter("email");
    	String ip = request.getRemoteAddr();
    	
    	
    	VideoRecommender vid1 = new VideoRecommender();
    	vid1.ItemSimilarity_run();
    	
    	PrintWriter out = response.getWriter();
    	out.println(HTML_START + "<h2>Hi There!</h2><br/><h3>Name:"+ name +"</h3></br>" + "Email:" + email + "</br>" + "</br>" + HTML_END);   
    	
    	

    }
 

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
    
    
  
	*/
 
}