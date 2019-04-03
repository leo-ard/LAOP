package org.lrima.laop.utils.lasp;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import org.lrima.laop.utils.lasp.beans.AlgorithmBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This is a gateway class between the LASP API and the algorithm beans
 * @author Clement Bisaillon
 */
public class AlgorithmsApiGateway extends ApiCaller {
	private final static Type collectionType = new TypeToken<Collection<AlgorithmBean>>(){}.getType();
	private final static String ALGORITHM_LIST_ENDPOINT = "http://localhost:8000/api/posts";
	
	/**
	 * Retrieve all the algorithms from the LASP database
	 * @return a collection of AlgorithmBean for each algorithm
	 */
	public static Collection<AlgorithmBean> getAllAlgorithms(){
		try {
			//Get the data from the url
			String jsonResponse = getContentFromURL(ALGORITHM_LIST_ENDPOINT, "GET");
			
			//Process json
			Gson gson = getJsonBuilder().create();
		    return(gson.fromJson(jsonResponse, collectionType));
		    
		}catch(Exception e) {
			System.err.println("Error while getting data from LASP !");
			return new ArrayList<>();
		}
	}
	
	/**
	 * Get a new GsonBuilder to process the json data
	 * @return a new GsonBuilder object
	 */
	private static GsonBuilder getJsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    return builder;
	}
}
