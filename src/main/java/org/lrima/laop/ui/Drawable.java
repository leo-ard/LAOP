package org.lrima.laop.ui;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface that makes lines drawable
 * @author Clement Bisaillon
 */
public interface Drawable {
	/**
	 * Defines how the object can be drawn on the screen
	 * @param gc the graphical context to draw on
	 */
	void draw(GraphicsContext gc);
}
