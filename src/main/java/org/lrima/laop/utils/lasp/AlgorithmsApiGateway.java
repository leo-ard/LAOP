package org.lrima.laop.utils.lasp;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;

import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmBean;
import org.lrima.laop.utils.lasp.beans.algorithm.AlgorithmResponseBean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This is a gateway class between the LASP API and the algorithm beans
 * @author Clement Bisaillon
 */
public class AlgorithmsApiGateway extends ApiCaller {
	private final Type COLLECTION_TYPE = new TypeToken<Collection<AlgorithmBean>>(){}.getType();
	private final String ALGORITHM_LIST_ENDPOINT = "https://lasp.lrima.cmaisonneuve.qc.ca/api/posts";
	private HashMap<Integer, AlgorithmResponseBean> cache;
	
	public AlgorithmsApiGateway() {
		this.cache = new HashMap<Integer, AlgorithmResponseBean>();
	}
	
	/**
	 * Retrieve all the algorithms from the LASP database
	 * @return a collection of AlgorithmBean for each algorithm
	 */
	public AlgorithmResponseBean getAllAlgorithms(int page){
		//Check if it is in the cache
		if(this.cache.containsKey(page)) {
			//Return the cached result instead of calling the api again
			return this.cache.get(page);
		}
		
		try {
			//Get the data from the url
			HashMap<String, String> parameters = new HashMap<>();
			parameters.put("page", "" + page);
			String jsonResponse = getContentFromURL(ALGORITHM_LIST_ENDPOINT, "GET", parameters);
			
			//Process json
			Gson gson = getJsonBuilder().create();
			AlgorithmResponseBean response = gson.fromJson(jsonResponse, AlgorithmResponseBean.class);
			
			//Add the response to the cache
			this.cache.put(page, response);
			
		    return(response);
		    
		}catch(Exception e) {
			System.err.println("Error while getting data from LASP !");
			return null;
		}
	}
	
	/**
	 * Get a new GsonBuilder to process the json data
	 * @return a new GsonBuilder object
	 */
	private GsonBuilder getJsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    return builder;
	}
}
