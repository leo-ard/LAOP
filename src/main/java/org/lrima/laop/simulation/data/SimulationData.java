package org.lrima.laop.simulation.data;

import java.io.BufferedWriter;
import java.io.IOException;
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
	
	public void toCsv(BufferedWriter writer) throws IOException {
		for(GenerationData genData : this.generationData) {
			String line = genData.getCsvLine();
			writer.write(line);
			writer.newLine();
		}
		
	}
}
