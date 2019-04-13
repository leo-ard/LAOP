package org.lrima.laop.ui.stage;

import com.jfoenix.controls.JFXButton;
import org.lrima.laop.simulation.GenerationBasedEnvironnement;
import org.lrima.laop.ui.SimulationView;
import org.lrima.laop.ui.components.ChartPanel;

public class GeneticStage extends SimulationView<GenerationBasedEnvironnement> {

    private ChartPanel chartPanel;

    public GeneticStage(GenerationBasedEnvironnement generationBasedEnvironnement){
        super(generationBasedEnvironnement);

        this.chartPanel = new ChartPanel();
        generationBasedEnvironnement.setOnGenerationFinish(() -> this.chartPanel.updateChartData(generationBasedEnvironnement.getGenerationData()));
        generationBasedEnvironnement.setOnSimulationFinish(() -> this.chartPanel.resetChart());
    }


    @Override
    public void setup(MainSimulationStage mainSimulationStage) {
        JFXButton btnGenFinish = new JFXButton("Next generation");
        btnGenFinish.getStyleClass().add("btn-light");
//        btnGenFinish.setOnAction( e-> {
//            this.getEngironnement().nextGen();
//        });
        mainSimulationStage.getTimeline().getChildren().add(btnGenFinish);

        mainSimulationStage.getTimeline().getRealTimeAttribute().addListener((observer, oldVal, newVal) -> {
        	this.getEngironnement().setAutoRun(newVal);
        });

        mainSimulationStage.getBotomBar().getChildren().add(chartPanel);

        mainSimulationStage.getMenuBar().addShowCharts(this.chartPanel);
        mainSimulationStage.getMenuBar().addRealTime(this.getEngironnement().getPhysicEngine());
    }
}
