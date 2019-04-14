package org.lrima.laop.simulation.data;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultData {
	private HashMap<String, BatchData> batchData;

	public ResultData(ArrayList<String> scopes) {
		this.batchData = new HashMap<>();
		
		for(String scope : scopes) {
			batchData.put(scope, new BatchData());
		}
	}
	
	public BatchData getBatchData(String scope) {
		return this.batchData.get(scope);
	}
}
