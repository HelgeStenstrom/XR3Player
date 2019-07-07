/*
 * 
 */
package com.goxr3plus.xr3player.xplayer.visualizer.presenter;

import com.goxr3plus.xr3player.xplayer.visualizer.core.VisualizerDrawer;

/**
 * The Class Visualizer.
 *
 * @author GOXR3PLUS
 */
abstract class Visualizer extends VisualizerDrawer {

	/** The animation service. */
	private final PaintService animationService = new PaintService(this);

	/**
	 * Constructor
	 * 
	 */
	public Visualizer() {
		// System.out.println("Visualizer Constructor called...{" + text + "}");

		// if i didn't add the draw to the @Override resize(double width, double
		// height) then it must be into the below listeners

		// Make the magic happen when the width or height changes
		// ----------
		widthProperty().addListener((observable, oldValue, newValue) -> {
			// System.out.println("New Visualizer Width is:" + newValue)

			// Canvas Width
			setCanvasWidth((int) widthProperty().get());

			// Compute the Color Scale
			computeColorScale();

		});
		// -------------
		heightProperty().addListener((observable, oldValue, newValue) -> {
			// System.out.println("New Visualizer Height is:" + newValue)

			// Canvas Height
			setCanvasHeight((int) heightProperty().get());
			setHalfCanvasHeight(getCanvasHeight() >> 1);

			// Sierpinski
			sierpinski.sierpinskiRootHeight = getCanvasHeight();

			// Compute the Color Scale
			computeColorScale();
		});

	}

	/**
	 * Stars the visualizer.
	 */
	public void startVisualizer() {
		animationService.start();
	}

	/**
	 * Stops the visualizer.
	 */
	public void stopVisualizer() {
		animationService.stop();
		clear();
	}

	/**
	 * @return True if AnimationTimer of Visualizer is Running
	 */
	public boolean isRunning() {
		return animationService.isRunning();
	}

	/**
	 * @return the animationService
	 */
	public PaintService getAnimationService() {
		return animationService;
	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 							      Paint Service
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

}
