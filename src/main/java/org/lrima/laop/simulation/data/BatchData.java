package org.lrima.laop.simulation.data;

import java.util.ArrayList;

public class BatchData {

	private ArrayList<SimulationData> simulationData;
	
	public BatchData() {
		this.simulationData = new ArrayList<>();
	}
	
	public void addData(SimulationData simData) {
		this.simulationData.add(simData);
		System.out.println("AA: " + simulationData.size());
	}
	
	public void addAll(ArrayList<SimulationData> simData) {
		this.simulationData.addAll(simData);
	}
	
	public ArrayList<SimulationData> getSimulationData() {
		return this.simulationData;
	}
}
