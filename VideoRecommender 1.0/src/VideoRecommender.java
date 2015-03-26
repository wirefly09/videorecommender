
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedWriter;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
	
	public ArrayList<Long> ItemSimilarity_run (String userId){
		ArrayList<Long> videoList = new ArrayList();
			try {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movies24.csv");
				DataModel dm = new FileDataModel(stream2file(inputStream)); 
				
				
				//UserSimilarity sim = new LogLikelihoodSimilarity(dm);
				//TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
				//UserSimilarity sim = new PearsonCorrelationSimilarity(dm);
				
				ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
				
				//UserNeighborhood neigh1 = new NearestNUserNeighborhood(20, sim, dm);
				//Recommender recommenderGeneric = new GenericUserBasedRecommender(dm, neigh1, sim);
				//Recommender recommenderSlope = new SlopeOneRecommender(dm);
				
				GenericItemBasedRecommender recommenderGeneric = new GenericItemBasedRecommender(dm, sim);
						
				
				int x=0;
					
				for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
					long itemId = items.nextLong();
					List<RecommendedItem>recommendations = recommenderGeneric.recommend(itemId, 5);
					
					for(RecommendedItem recommendation : recommendations) {
						//System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
						//bw.write(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue() + "\n");
						if(userId.equals(Objects.toString(itemId))){
						videoList.add(recommendation.getItemID());
						}
						//videoList.add(itemId);
					}
					x++;
					if(x>5) break; 
				}
				
				recommenderGeneric.refresh(null);
				
			} 
			catch (IOException e) {
				System.out.println("There was an error.");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("There was a Taste Exception");
				e.printStackTrace();
			}
			return videoList;
			
		} 
		/*
		 * Takes a userId input from the webservice request and returns an array list of recommended movies based on TanimotoCoefficientSimilarity.
		 */
		public ArrayList<Long> TanimotoCoefficientSimilarity_run (String userId){
			ArrayList<Long> videoList = new ArrayList();
			try {
				InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movies24.csv");
				DataModel dm = new FileDataModel(stream2file(inputStream)); 
				
				
				TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
				UserNeighborhood neigh1 = new NearestNUserNeighborhood(20, sim, dm);
				Recommender recommenderGeneric = new GenericUserBasedRecommender(dm, neigh1, sim);

				
				int x=0;
				for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();) {
					long itemId = items.nextLong();
					List<RecommendedItem>recommendations = recommenderGeneric.recommend(itemId, 5);
					
					for(RecommendedItem recommendation : recommendations) {
						//System.out.println(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue());
						//bw.write(itemId + "," + recommendation.getItemID() + "," + recommendation.getValue() + "\n");
						if(userId.equals(Objects.toString(itemId))){
							videoList.add(recommendation.getItemID());
						}
					}
					x++;
					if(x>10) break; 
				}
				
				recommenderGeneric.refresh(null);
				
			} catch (IOException e) {
				System.out.println("There was an error.");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("There was a Taste Exception");
				e.printStackTrace();
			}
			
			return videoList;
		}
		
		/*
		 * Takes a clusterId as input and returns a list of videos that the provided clusterId would like
		 */
		public ArrayList<Long> KMeansCluster_run(long clusterId) throws Exception{
			ArrayList<Long> videoList = new ArrayList();
			
			SimpleKMeans kmeans = new SimpleKMeans(); 
			kmeans.setSeed(10);
	 
			//important parameter to set: preserver order, number of cluster.
			kmeans.setPreserveInstancesOrder(true);
			kmeans.setNumClusters(5);
	 
			
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test1.arff");
			//DataModel dm = new FileDataModel(stream2file(inputStream)); 
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			ArffReader arff = new ArffReader(reader);
			Instances data = arff.getData();
			//data.setClassIndex(data.numAttributes() - 1);
			
			//BufferedReader datafile = readDataFile("test1.arff"); 
			//Instances data = new Instances(datafile);
	 
	 
			kmeans.buildClusterer(data);
	 
			// This array returns the cluster number (starting with 0) for each instance
			// The array has as many elements as the number of instances
			int[] assignments = kmeans.getAssignments();

			
			//BufferedWriter bw = new BufferedWriter(new FileWriter(filepath + "/data/outputs/KMeansCluster_output.csv"));		
			
			long i=0;
			for(long clusterNum : assignments) {
			    /*
				if(clusterNum == 1){
					
					bw.write(clusterNum + "," + i + "\n");
			    }
			    */
				//System.out.printf("%d,%d \n", clusterNum, i);
				if(clusterId == clusterNum){
					videoList.add(i);
				}
			
				i++;			    
			}
			
			//bw.close();
			return videoList;
			
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
		
	
	public static final String PREFIX = "movies24";
    public static final String SUFFIX = ".csv";
    
	 public static File stream2file (InputStream in) throws IOException {
	        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
	        tempFile.deleteOnExit();
	        try (FileOutputStream out = new FileOutputStream(tempFile)) {
	            IOUtils.copy(in, out);
	        }
	        return tempFile;
	    }

}
