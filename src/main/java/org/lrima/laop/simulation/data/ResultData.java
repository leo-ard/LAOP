package org.lrima.laop.simulation.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.lrima.laop.core.LAOP;
import org.lrima.laop.settings.Scope;

public class ResultData {
	private HashMap<String, BatchData> batchData;
	private Date simulationStartDate;
	private LinkedHashMap<String, Scope> scopes;

	public ResultData(LinkedHashMap<String, Scope> scopes) {
		this.scopes = scopes;
		this.batchData = new HashMap<>();
		this.simulationStartDate = new Date();
		
		for(String scopeString : scopes.keySet()) {
			Scope scope = scopes.get(scopeString);
			batchData.put(scopeString, new BatchData(scope));
		}
	}
	
	public BatchData getBatchData(String scope) {
		return this.batchData.get(scope);
	}
	
	public void addData(String scope, BatchData data) {
		this.batchData.put(scope, data);
	}
	
	public void toCsv() {
		
		//Create a directory with the time of the simulation start in /data
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/d/HH-mm-ss");
		for(String scope : this.batchData.keySet()) {
			BatchData batchData = this.batchData.get(scope);
			String directory = "data/" + dateFormat.format(this.simulationStartDate);
			File batchFile = new File(directory, scope + ".csv");
			batchFile.getParentFile().mkdirs();
			batchData.toCsv(batchFile);
			
			//Create a info file containing information about the simulated algorithm
//			File infoFile = new File(directory, scope + "_info.txt");
//			try {
//				FileOutputStream fos = new FileOutputStream(infoFile);
//				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//				
//				bw.write("Network class name: " + batchData.getNetworkName());
//				bw.newLine();
//				bw.write("Learning class name: " + batchData.getLearningName());
//				
//				bw.close();
//				fos.close();
//			}catch(Exception e) {
//				e.printStackTrace();
//				System.err.println("Error writing information file: " + e.getMessage());
//			}
		}
		
		
	}
}
