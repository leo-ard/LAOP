package org.lrima.laop.graphics.panels.simulation.timeline;

import org.lrima.laop.controller.SimulationDrawer;

import javafx.scene.control.Button;

/**
 * The play button of the simulation drawer timeline
 * @author Léonard
 */
public class PlayButton extends Button {
    private boolean isPlaying;
    private SimulationDrawer simulationDrawer;

    /**
     * Initialize a pause button with a certain state
     * @param play false if pause, true otherwise
     */
    public PlayButton(boolean play, SimulationDrawer simulationDrawer){
        this.isPlaying = play;
        this.simulationDrawer = simulationDrawer;
        this.setStatus();
        this.setOnAction(e ->
            setIsPlaying(!isPlaying)
        );
    }

    /**
     * Initialize a pause button with the state play to true
     */
    public PlayButton(SimulationDrawer simulationDrawer){
        this(false, simulationDrawer);
    }

    /**
     * Get the status of this button
     * @return
     */
    public boolean getIsPlaying(){
        return this.isPlaying;
    }

    /**
     * Set the status of this button
     * @param play true if playing, false otherwise
     */
    public void setIsPlaying(boolean play){
        this.isPlaying = play;
        this.setStatus();
    }

    /**
     * Set the text of the button depending on its state
     */
    private void setStatus(){
        if(this.isPlaying){
            this.setText("||");
            this.simulationDrawer.startAutodraw(100);
        }
        else{
            this.setText(">");
            this.simulationDrawer.stopAutoDraw();
        }
    }
}
