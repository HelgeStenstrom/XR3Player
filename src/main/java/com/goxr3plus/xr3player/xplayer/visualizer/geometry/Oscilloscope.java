/**
 * 
 */
package com.goxr3plus.xr3player.xplayer.visualizer.geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.goxr3plus.xr3player.xplayer.visualizer.core.VisualizerDrawer;

/**
 * The Class Oscilloscope.
 * 
 * -----------------------------------------------------------------------
 * 
 * -----------------------------------------------------------------------
 * 
 * 
 * Oscilloscope
 * 
 * -----------------------------------------------------------------------
 * 
 * -----------------------------------------------------------------------
 *
 * 
 * @author GOXR3PLUS
 */
public class Oscilloscope {

	// ---------------Oscilloscope-------------------

	/** The color size. */
	private final int colorSize = 360;

	/** The color index. */
	private int colorIndex = 0;

	/** The band width. */
	private float bandWidth;

	/** The x. */
	private int x = 0;

	/** The y. */
	private int y = 0;

	/** The x old. */
	private int xOld = 0;

	/** The y old. */
	@SuppressWarnings("unused")
	private int yOld = 0;

	/** VisualizerDrawer instance. */
	private VisualizerDrawer visualizerDrawer;

	// ---------------------------------------------------------------------------------------------------

	/**
	 * Constructor.
	 *
	 * @param visualizerDrawer the visualizer drawer
	 */
	public Oscilloscope(VisualizerDrawer visualizerDrawer) {
		this.visualizerDrawer = visualizerDrawer;
	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 			        Oscilloscope
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/**
	 * Draws an Oscilloscope.
	 *
	 * @param stereo The Oscilloscope with have 2 lines->stereo or 1 line->merge
	 *               left and right audio
	 */
	public void drawOscilloscope(boolean stereo) {
		float[] pSample1;

		// It will be stereo?
		if (stereo)
			pSample1 = visualizerDrawer.getpLeftChannel();
		else // not?Then merge the array
			pSample1 = visualizerDrawer.getStereoMerge();

		// Background
		visualizerDrawer.drawBackgroundImage();

		final GraphicsContext graphicsContext = visualizerDrawer.gc;

		graphicsContext.setStroke(visualizerDrawer.getScopeColor());
		// System.out.println(pSample.length)

		final int halfCanvasHeight = visualizerDrawer.getHalfCanvasHeight();
		int yLast1 = (int) (pSample1[0] * (float) halfCanvasHeight)
				+ halfCanvasHeight;
		int samIncrement1 = 1;
		final int canvasWidth = visualizerDrawer.getCanvasWidth();
		for (int a = samIncrement1, c = 0; c < canvasWidth; a += samIncrement1, c++) {
			int yNow = (int) (pSample1[a] * (float) halfCanvasHeight)
					+ halfCanvasHeight;
			graphicsContext.strokeLine(c, yLast1, c + 1.00, yNow);
			yLast1 = yNow;
		}

		// Oscilloscope will be stereo
		if (stereo) {
			colorIndex = (colorIndex == colorSize - 1) ? 0 : colorIndex + 1;
			graphicsContext.setStroke(Color.hsb(colorIndex, 1.0f, 1.0f));

			float[] pSample2 = visualizerDrawer.getpRightChannel();

			int yLast2 = (int) (pSample2[0] * (float) halfCanvasHeight)
					+ halfCanvasHeight;
			int samIncrement2 = 1;
			for (int a = samIncrement2, c = 0; c < canvasWidth; a += samIncrement2, c++) {
				int yNow = (int) (pSample2[a] * (float) halfCanvasHeight)
						+ halfCanvasHeight;
				graphicsContext.strokeLine(c, yLast2, c + 1.00, yNow);
				yLast2 = yNow;
			}

		}

	}

	/**
	 * Draws an Oscilloscope with up and down Lines.
	 */
	public void drawOscilloScopeLines() {

		// Background
		visualizerDrawer.drawBackgroundImage();

		// Use HSB color model
		colorIndex = (colorIndex == colorSize - 1) ? 0 : colorIndex + 1;
		visualizerDrawer.gc.setStroke(Color.hsb(colorIndex, 1.0f, 1.0f));
		// System.out.println(colorIndex / (float) colorSize)

		int newSampleCount = (int) (visualizerDrawer.getDataLine().getFormat().getFrameRate() * 0.023);
		bandWidth = (float) visualizerDrawer.getCanvasWidth() / (float) newSampleCount;
		int halfHeight = visualizerDrawer.getCanvasHeight() / 2;
		int quarterHeight = visualizerDrawer.getCanvasHeight() / 4;
		xOld = 0;
		yOld = 0;
		// System.out.println(bandWidth)

		// Sum the sample values from the left and right audio channels.
		// Draw a line between the x,y coordinates of each new audio sample and
		// those of the previous sample. x = sample number; y = sample value
		for (int i = 0; i < newSampleCount; i++) {
			x = (int) (i * bandWidth);
			y = halfHeight
					+ (int) (quarterHeight * (visualizerDrawer.getpLeftChannel()[i] + visualizerDrawer.getpRightChannel()[i]));

			x = Math.min(Math.max(0, x), visualizerDrawer.getCanvasWidth());
			y = Math.min(Math.max(0, y), visualizerDrawer.getCanvasHeight());

			visualizerDrawer.gc.strokeLine(xOld, halfHeight, x, y);
			xOld = x;
			yOld = y;
		}
	}

}
