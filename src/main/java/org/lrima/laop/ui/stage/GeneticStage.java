package org.lrima.laop.ui.stage;

import com.jfoenix.controls.JFXButton;
import org.lrima.laop.simulation.GenerationBasedSimulation;
import org.lrima.laop.ui.MainSimulationStage;
import org.lrima.laop.ui.SimulationView;
import org.lrima.laop.ui.components.ChartPanel;

public class GeneticStage extends SimulationView<GenerationBasedSimulation> {

    private ChartPanel chartPanel;

    public GeneticStage(GenerationBasedSimulation generationBasedSimulation){
        super(generationBasedSimulation);

        this.chartPanel = new ChartPanel();
        this.getSimulation().setOnGenerationFinish(simulation -> this.chartPanel.updateChartData(simulation.getGenerationData()));
        this.getSimulation().setOnSimulationFinish(no -> this.chartPanel.resetChart());
    }


    @Override
    public void setup(MainSimulationStage mainSimulationStage) {
        JFXButton btnGenFinish = new JFXButton("Next generation");
        btnGenFinish.getStyleClass().add("btn-light");
        btnGenFinish.setOnAction( e-> {
            this.getSimulation().nextGen();
        });
        mainSimulationStage.getTimeline().getChildren().add(btnGenFinish);
        
        mainSimulationStage.getTimeline().getRealTimeAttribute().addListener((observer, oldVal, newVal) -> {
        	this.getSimulation().setAutoRun(newVal);
        });

        mainSimulationStage.getBotomBar().getChildren().add(chartPanel);

        mainSimulationStage.getMenuBar().addShowCharts(this.chartPanel);
        mainSimulationStage.getMenuBar().addRealTime(this.getSimulation());
    }
}
