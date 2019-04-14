package org.lrima.laop.simulation.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.lrima.laop.core.LAOP;
import org.lrima.laop.settings.Scope;

public class BatchData {

	private ArrayList<SimulationData> simulationData;
	private String algorithmName;
	private Scope scope;
	
	public BatchData(Scope scope) {
		this.scope = scope;
		this.algorithmName = algorithmName;
		this.simulationData = new ArrayList<>();
	}
	
	public void addData(SimulationData simData) {
		this.simulationData.add(simData);
	}
	
	public void addAll(ArrayList<SimulationData> simData) {
		this.simulationData.addAll(simData);
	}
	
	public ArrayList<SimulationData> getSimulationData() {
		return this.simulationData;
	}
	
	public String getNetworkName() {
		return ((Class<?>) this.scope.get(LAOP.KEY_NETWORK_CLASS).getValue()).getSimpleName();
	}
	public String getLearningName() {
		return ((Class<?>) this.scope.get(LAOP.KEY_LEARNING_CLASS).getValue()).getSimpleName();
	}
	
	/**
	 * Writes the data the a specified csv file
	 * @param file
	 */
	public void toCsv(File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for(int i = 0 ; i < this.simulationData.size() ;i++) {
				SimulationData simData = this.simulationData.get(i);
				simData.toCsv(bw);
			}
			
			bw.close();
			fos.close();
		}catch(Exception e) {
			System.err.println("ERROR writing to file: " +e.getMessage());
		}
	}
}
