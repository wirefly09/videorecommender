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

public class VideoRecommender {
	public String filepath = "/Users/mark.crawford/Documents/workspace/personaltest/data";
	public VideoRecommender(){
		
		
	} 
	
	public void ItemSimilarity_run (){
			try {
				DataModel dm = new FileDataModel(new File("/Users/mark.crawford/Documents/workspace/personaltest/data/Input/movies24.csv")); 
				
				//UserSimilarity sim = new LogLikelihoodSimilarity(dm);
				//TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
				//UserSimilarity sim = new PearsonCorrelationSimilarity(dm);
				ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
				
				//UserNeighborhood neigh1 = new NearestNUserNeighborhood(20, sim, dm);
				
				
				//Recommender recommenderGeneric = new GenericUserBasedRecommender(dm, neigh1, sim);
				//Recommender recommenderSlope = new SlopeOneRecommender(dm);
				GenericItemBasedRecommender recommenderGeneric = new GenericItemBasedRecommender(dm, sim);
				
				
				BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/mark.crawford/Documents/workspace/personaltest/data/Output/ItemSimilarity_output.csv"));		
				
				int x=0;
				for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
					long itemId = items.nextLong();
					List<RecommendedItem>recommendations = recommenderGeneric.recommend(itemId, 5);
					
					for(RecommendedItem recommendation : recommendations) {
						//System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
						bw.write(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue() + "\n");
					}
					x++;
					if(x>10) break; 
				}
				bw.close();	
				recommenderGeneric.refresh(null);
				
			} catch (IOException e) {
				System.out.println("There was an error.");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("There was a Taste Exception");
				e.printStackTrace();
			}
			
		}
		
		public void TanimotoCoefficientSimilarity_run (){
			try {
				DataModel dm = new FileDataModel(new File("data/input/movies24.csv")); 
				
				//UserSimilarity sim = new LogLikelihoodSimilarity(dm);
				TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
				//UserSimilarity sim = new PearsonCorrelationSimilarity(dm);
				
				
				UserNeighborhood neigh1 = new NearestNUserNeighborhood(20, sim, dm);
				
				
				Recommender recommenderGeneric = new GenericUserBasedRecommender(dm, neigh1, sim);
				//Recommender recommenderSlope = new SlopeOneRecommender(dm);
				//GenericItemBasedRecommender recommenderGeneric = new GenericItemBasedRecommender(dm, sim);
				
				
				BufferedWriter bw = new BufferedWriter(new FileWriter(filepath + "/Output/TanimotoCoefficientSimilarity_output.csv"));		
				
				int x=0;
				for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
					long itemId = items.nextLong();
					List<RecommendedItem>recommendations = recommenderGeneric.recommend(itemId, 5);
					
					for(RecommendedItem recommendation : recommendations) {
						//System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
						bw.write(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue() + "\n");
					}
					x++;
					if(x>10) break; 
				}
				bw.close();	
				recommenderGeneric.refresh(null);
				
			} catch (IOException e) {
				System.out.println("There was an error.");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("There was a Taste Exception");
				e.printStackTrace();
			}
			
		}
		
		public void KMeansCluster_run() throws Exception{
			SimpleKMeans kmeans = new SimpleKMeans();
			 
			kmeans.setSeed(10);
	 
			//important parameter to set: preserver order, number of cluster.
			kmeans.setPreserveInstancesOrder(true);
			kmeans.setNumClusters(5);
	 
			BufferedReader datafile = readDataFile(filepath + "input/test1.arff"); 
			Instances data = new Instances(datafile);
	 
	 
			kmeans.buildClusterer(data);
	 
			// This array returns the cluster number (starting with 0) for each instance
			// The array has as many elements as the number of instances
			int[] assignments = kmeans.getAssignments();

			
			BufferedWriter bw = new BufferedWriter(new FileWriter(filepath + "/data/outputs/KMeansCluster_output.csv"));		
			
			int i=0;
			for(int clusterNum : assignments) {
			    //if(clusterNum == 1){
				//System.out.printf("%d,%d \n", i, clusterNum);
				bw.write(clusterNum + "," + i + "\n");
			   // }
				i++;
			    
			}
			
			bw.close();
			
		}
		

		public BufferedReader readDataFile(String filename) {
			BufferedReader inputReader = null;
	 
			try {
				inputReader = new BufferedReader(new FileReader(filename));
			} catch (FileNotFoundException ex) {
				System.err.println("File not found: " + filename);
			}
	 
			return inputReader;
		}
		
	

}
