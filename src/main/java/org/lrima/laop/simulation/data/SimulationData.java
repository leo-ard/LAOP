package org.lrima.laop.simulation.data;

import java.util.ArrayList;

public class SimulationData {
	
	private ArrayList<GenerationData> generationData;
	
	public SimulationData() {
		this.generationData = new ArrayList<>();
	}
	
	public void addData(GenerationData data) {
		this.generationData.add(data);
	}
	
	public ArrayList<GenerationData> getGenerationData(){
		return this.generationData;
	}
}
