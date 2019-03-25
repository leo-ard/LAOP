package org.lrima.laop.ui.components;

import javafx.scene.control.Button;

import java.util.function.Consumer;

/**
 * The play button of the simulation drawer timeline
 * @author Léonard
 */
public class PlayButton extends Button {
    private boolean isPlaying;

    private Consumer<Boolean> whenToggled;

    /**
     * Initialize a pause button with a certain state
     * @param play false if pause, true otherwise
     */
    public PlayButton(Consumer<Boolean> whenToggled){
        this.isPlaying = false;
        this.whenToggled = whenToggled;

        this.setStatus();
        this.setOnAction(e ->
            setIsPlaying(!isPlaying)
        );
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
        if(this.isPlaying)
            this.setText("||");
        else
            this.setText(">");

        whenToggled.accept(this.isPlaying);
    }
}
