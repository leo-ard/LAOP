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
        System.out.println(chartPanel);
    }


    @Override
    public void setup(MainSimulationStage mainSimulationStage) {
        JFXButton btnGenFinish = new JFXButton("Next generation");
        btnGenFinish.getStyleClass().add("btn-light");
        btnGenFinish.setOnAction( e-> {
            btnGenFinish.setDisable(true);
//            this.simulationEngine.nextGen();
        });
        mainSimulationStage.getTimeline().getChildren().add(btnGenFinish);

        mainSimulationStage.getBotomBar().getChildren().add(chartPanel);

        mainSimulationStage.getMenuBar().addShowCharts(this.chartPanel);

        System.out.println("haha");
    }
}
