/*
 * 
 */
package com.goxr3plus.xr3player.xplayer.visualizer.presenter;

import com.goxr3plus.xr3player.application.MainLoader;
import com.goxr3plus.xr3player.controllers.xplayer.XPlayerController;
import com.goxr3plus.xr3player.xplayer.visualizer.core.VisualizerDrawer;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Orientation;
import javafx.scene.paint.Color;

/**
 * The Class Visualizer.
 *
 * @author GOXR3PLUS
 */
abstract class Visualizer extends VisualizerDrawer {

	/** The animation service. */
	private final PaintService animationService = new PaintService();

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
	/**
	 * This Service is updating the visualizer.
	 *
	 * @author GOXR3PLUS
	 */
	public class PaintService extends AnimationTimer {

		/** The next second. */
		long nextSecond;
		long next50Milliseconds;

		/** The Constant ONE_SECOND_NANOS. */
		private static final long ONE_SECOND_NANOS = 1_000_000_000L;
		// 50 Milliseconds in nanoseconds
		private static final long MILLISECONDS_50_NANOS = 1_000_000L * 50;

		/**
		 * When this property is <b>true</b> the AnimationTimer is running
		 */
		private volatile SimpleBooleanProperty running = new SimpleBooleanProperty(false);

		/**
		 * XPlayerController reference
		 */
		XPlayerController xPlayerController;

		/**
		 * The animationService can draw
		 */
		private boolean draw = true;

		// FPS
		private long previousTime = 0;
		private float secondsElapsedSinceLastFpsUpdate = 0f;
		private int framesSinceLastFpsUpdate = 0;

		@Override
		public void start() {
			// Values must be >0
			if (getCanvasWidth() <= 0 || getCanvasHeight() <= 0) {
				setCanvasHeight(1);
				setCanvasWidth(1);
			}

			next50Milliseconds = nextSecond = 0L;
			super.start();
			running.set(true);
		}

		@Override
		public void stop() {
			super.stop();
			running.set(false);
		}

		/**
		 * @return True if AnimationTimer is running
		 */
		public boolean isRunning() {
			return running.get();
		}

		/**
		 * @return Running Property
		 */
		public SimpleBooleanProperty runningProperty() {
			return running;
		}

		/**
		 * This method is used by XPlayerController to pass a reference to the
		 * AnimationTimer
		 *
		 * @param xPlayerController The XPlayerController Reference
		 */
		public void passXPlayer(XPlayerController xPlayerController) {
			this.xPlayerController = xPlayerController;
		}

		// ===============EXPERIMENTAL===========================
		// int maximumFramesPerSecond = 60;
		// int maximumFramesPer50Milliseconds = (int) Math.round(maximumFramesPerSecond
		// / 20.00);
		// int framesPer50Milliseconds = 0;
		// ===============END OF EXPERIMENTAL===========================

		@Override
		public void handle(long nanos) {

			// CHECK IF VISUALIZERS ARE ENABLED
			if (MainLoader.getSettingsWindow() != null
					&& !MainLoader.getSettingsWindow().getGeneralSettingsController().getHighGraphicsToggle().isSelected())
				return;

			if (previousTime == 0) {
				previousTime = nanos;
				return;
			}

//			float secondsElapsed = ( nanos - previousTime ) / 1e9f;
//			previousTime = nanos;
//			
//			secondsElapsedSinceLastFpsUpdate += secondsElapsed;
//			framesSinceLastFpsUpdate++;
//			if (secondsElapsedSinceLastFpsUpdate >= 0.5f) {
//				int fps = Math.round(framesSinceLastFpsUpdate / secondsElapsedSinceLastFpsUpdate);
//				//System.out.println(fps)
//				secondsElapsedSinceLastFpsUpdate = 0;
//				framesSinceLastFpsUpdate = 0;
//			}

			// Avoid null pointer and also check if we have permission to draw the
			// visualizer
			if (xPlayerController != null && !xPlayerController.visualizerStackController.isVisible()) {
				clear();
				draw = false;
				return;
			}

			// No need to draw the visualizer if player is on microMode
			if (!xPlayerController.getModesStackPane().isVisible()) {
				clear();
				return;
			}

			// ===============EXPERIMENTAL===========================
			// //We want every 50 milliseconds only the allowed frames to be drawn
			// if (framesPer50Milliseconds >= maximumFramesPer50Milliseconds)
			// draw = false;
			// else {
			// draw = true;
			// ++framesPer50Milliseconds;
			// }
			//
			// //Check every 50 milliseconds how many frames have been drawn and prepare for
			// the next 50 milliseconds
			// if (nanos >= next50Milliseconds) {
			// framesPerSecond += framesPer50Milliseconds;
			// framesPer50Milliseconds = 0;
			// next50Milliseconds = nanos + MILLISECONDS_50_NANOS;
			// }
			// ===============END OF EXPERIMENTAL===========================

			// =====Remove it if you want to add the experimental
			draw = true;
			++framesPerSecond;

			// Check for 1 second passed
			if (nanos >= nextSecond) {
				setFps(framesPerSecond);
				framesPerSecond = 0;
				nextSecond = nanos + ONE_SECOND_NANOS;
			}

			// System.out.println("Animation Timer is Running");

			// Can draw?
			if (draw) {
				clear();
				switch (displayMode.get()) {

				case 0:
					drawOscilloscope(false);
					break;
				case 1:
					drawOscilloscope(true);
					break;
				case 2:
					drawOscilloScopeLines();
					break;
				case 3:
					drawSpectrumBars();
					break;
				case 4:
					drawVUMeter(Orientation.HORIZONTAL);
					break;
				case 5:
					drawPolySpiral();
					break;
				case 6:
					drawCircleWithLines();
					break;
				case 7:
					drawSierpinski();
					break;
				case 8:
					drawSprite3D();
					break;
				case 9:
					drawVUMeter(Orientation.VERTICAL);

					break;
				case 10:
					drawJuliaSet();
					break;
				default:
					break;
				}

				// -- Show FPS if necessary.
				if (isShowFPS()) {
					gc.setFill(Color.BLACK);
					gc.fillRect(0, getCanvasHeight() - 15.00, 50, 28);
					gc.setStroke(Color.WHITE);
					gc.strokeText("FPS: " + getFps(), 0, getCanvasHeight() - 3.00); // + " (FRRH: " + frameRateRatioHint + ")"
				}

			} // END: if draw == TRUE

			// --------------------------------------------------------------------------------------RUBBISH
			// CODE
			/*
			 * if (System.currentTimeMillis() >= lfu + 1000) { lfu =
			 * System.currentTimeMillis(); fps = framesPerSecond; framesPerSecond = 0; }
			 */

			// System.out.println("Canvas Width is:" + canvasWidth + " , Canvas
			// Height is:" + canvasHeight)

			// System.out.println("Running..")
		}

	}

}
