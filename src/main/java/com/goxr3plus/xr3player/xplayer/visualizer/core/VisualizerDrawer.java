/**
 * 
 */
package com.goxr3plus.xr3player.xplayer.visualizer.core;

import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.JuliaSet;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Oscilloscope;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Polyspiral;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Sierpinski;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Sprites3D;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Sprites3D.Shape3D;
import com.goxr3plus.xr3player.utils.general.InfoTool;

/**
 * The Class VisualizerDrawer.
 *
 * @author GOXR3PLUS
 */
public class VisualizerDrawer extends VisualizerModel {

//	private final Oscilloscope oscilloscope = new Oscilloscope(this);
	private Polyspiral polySpiral = new Polyspiral(this);
	protected final Sierpinski sierpinski = new Sierpinski(this);
	private final JuliaSet juliaSet = new JuliaSet(this);
	private Sprites3D sprite3D = new Sprites3D(this, Shape3D.SPHERE);

	// -----------------------------Images---------------------------------

	// -----

	/** The DEFAULT_BACKGROUND_IMAGE */
	public static final Image DEFAULT_BACKGROUND_IMAGE = null;// =
																// InfoTool.getImageFromResourcesFolder("VisualizerBackground.jpg")
	/** The DEFAULT_FOREGROUND_IMAGE */
	public static final Image DEFAULT_FOREGROUND_IMAGE = InfoTool
			.getImageFromResourcesFolder("VisualizerForeground.png");

	// -----

	/** The foreground image */
	public Image foregroundImage;// = DEFAULT_FOREGROUND_IMAGE

	/** The background image. */
	public Image backgroundImage;// = DEFAULT_BACKGROUND_IMAGE

	// -----

	/**
	 * Draws the foreground image of the visualizer
	 *
	 */
	public void drawForegroundImage() {

		float[] array = returnBandsArray(getStereoMerge(), 1);

		// !null
		if (foregroundImage != null) {

			// Compute
			double imageW;// = foregroundImage.getWidth()
			double imageH;// = foregroundImage.getHeight()
			if (getCanvasWidth() < getCanvasHeight())
				imageW = imageH = getCanvasWidth() / 2.00;
			else
				imageW = imageH = getCanvasHeight() / 2.00;

			// Draw it
			gc.drawImage(foregroundImage, (getCanvasWidth() / 2.00 - imageW / 2) - imageW * array[0] / 2,
					(getCanvasHeight() / 2.00 - imageH / 2) - imageH * array[0] / 2, imageW + imageW * array[0],
					imageH + imageH * array[0]);

		}
	}

	/**
	 * Draws the background image of the visualizer
	 */
	public void drawBackgroundImage() {

		float[] array = returnBandsArray(getStereoMerge(), 1);

		// !null
		if (backgroundImage != null) {
			// gc.drawImage(backgroundImage, 0, 0, canvasWidth, canvasHeight);

			// Compute
			// double imageW = backgroundImage.getWidth();
			// double imageH = backgroundImage.getHeight();
			// = foregroundImage.getHeight()
			// if (canvasWidth < canvasHeight)
			// imageW = imageH = canvasWidth / 2.00;
			// else
			// imageW = imageH = canvasHeight / 2.00;

			// Draw it
			double front = Math.abs(array[0]) / 4;
			gc.drawImage(backgroundImage, -(front * getCanvasWidth()), -(front * getCanvasHeight()),
					getCanvasWidth() + (front * getCanvasWidth()) * 2, getCanvasHeight() + (front * getCanvasHeight()) * 2);
			// gc.drawImage(backgroundImage,canvasWidth - imageW) - imageW * array[0] / 2,
			// (canvasHeight - imageH) - imageH * array[0], imageW + imageW * array[0],
			// imageH + imageH * array[0])
		}
	}

	// ---------------------------------------------------------------------

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

//	/**
//	 * Draws an Oscilloscope
//	 *
//	 * @param stereo The Oscilloscope with have 2 lines->stereo or 1 line->merge
//	 *               left and right audio
//	 */
//	public void drawOscilloscope(boolean stereo) {
//		oscilloscope.drawOscilloscope(stereo);
//	}
//
//	/**
//	 * Draws an Oscilloscope with up and down Lines
//	 */
//	public void drawOscilloScopeLines() {
//		oscilloscope.drawOscilloScopeLines();
//	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 				Rosette and Polyspiral
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/**
	 * Draws a Rosette and a Polyspiral.
	 */
	public void drawPolySpiral() {
		polySpiral.drawPolySpiral();

	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 				Sprite3D
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/**
	 * Draws a 3D Sprite
	 */
	public void drawSprite3D() {
		sprite3D.draw();
	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 			Circle With Lines
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/** The color size. */
	private final int colorSize = 360;

	/** The color index. */
	private int colorIndex = 0;

	/**
	 * Draws the Round Circle with Lines on it's circumference
	 */
	public void drawCircleWithLines() {
		gc.setLineWidth(2);

		// Background
		drawBackgroundImage();

		// Calculate the radius
		int radius;
		if (getCanvasHeight() > getCanvasWidth())
			radius = getCanvasWidth() / 2;
		else
			radius = getCanvasHeight() / 2;
		radius = (int) (radius / 1.5);

		gc.setLineWidth(2);

		// int previousX1 = -1
		// int previousY1 = -1
		int previousEndX = -1;
		int previousEndY = -1;

		double centerX = getCanvasWidth() / 2.00;
		double centerY = getCanvasHeight() / 2.00;

		// for loop
		for (float angle = 0; angle <= 360; angle++) {
			// Use HSB color model
			colorIndex = (colorIndex == colorSize - 1) ? 0 : colorIndex + 1;
			gc.setStroke(Color.hsb(colorIndex, 1.0f, 1.0f));
			// gc.setFill(Color.hsb(colorIndex, 1.0f, 1.0f))
			// System.out.println("Calculating")
			// gc.setStroke(Color.rgb(random.nextInt(255), random.nextInt(255),
			// random.nextInt(255)))

			// Code before
			// int px1 = (int) (canvasWidth / 2 + Math.sin(Math.toRadians(angle)) * radius)
			// int py1 = (int) (canvasHeight / 2 + Math.cos(Math.toRadians(angle)) * radius)

			// int px2 = (int) (canvasWidth / 2 + Math.sin(Math.toRadians(angle)) * (radius
			// + Math.abs(pSample[(int) angle]) * 100))
			// int py2 = (int) (canvasHeight / 2 + Math.cos(Math.toRadians(angle)) * (radius
			// + Math.abs(pSample[(int) angle]) * 100))

			// Code after [ Runs faster ]
			double angleRadians = Math.toRadians(angle);
			double mathSin = Math.sin(angleRadians);
			double mathCos = Math.cos(angleRadians);

			int startX = (int) (centerX + mathSin * radius); // startX
			int startY = (int) (centerY + mathCos * radius); // startY

			double add = Math.abs(getStereoMerge()[(int) angle]) * (radius);

			int endX = (int) (centerX + mathSin * (radius + add)); // endX
			int endY = (int) (centerY + mathCos * (radius + add)); // endY

			// Join with the previous line
			if (previousEndX != -1) {
				gc.strokeLine(previousEndX, previousEndY, endX, endY); // connect with the previous line

				// gc.fillPolygon(new double[] { previousX1,previousX2, px2, px1 }, new double[]
				// { previousY1,previousY2, py2, py1 }, 4)
			}

			// previousX1 = px1
			// previousY1 = py1
			previousEndX = endX;
			previousEndY = endY;

			gc.strokeLine(startX, startY, endX, endY); // draw the line

			int endX2 = (int) (centerX + mathSin * (radius - add)); // endX
			int endY2 = (int) (centerY + mathCos * (radius - add)); // endY
			gc.strokeLine(startX, startY, endX2, endY2); // draw the line
		}

		// Foreground
		drawForegroundImage();

		// Reset it so it doesn't affect the others
		gc.setLineWidth(1);

	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 			Spectrum Analyzer
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/**
	 * Draws a spectrum analyzer using rectangles.
	 */
	public void drawSpectrumBars() {

		float barWidth = (float) getCanvasWidth() / (float) saBands;
		float[] array = returnBandsArray(getStereoMerge(), saBands);
		float c = 0;

		// Background
		this.drawBackgroundImage();

		for (int band = 0; band < saBands; band++) {
			drawSpectrumBar((int) c, getCanvasHeight(), (int) barWidth - 1, (int) (array[band] * getCanvasHeight()), band);
			c += barWidth;
		}
	}

	/**
	 * Draw spectrum analyser bar.
	 *
	 * @param pX      the p X
	 * @param pY      the p Y
	 * @param pWidth  the width
	 * @param pHeight the height
	 * @param band    the band
	 */
	private void drawSpectrumBar(int pX, int pY, int pWidth, int pHeight, int band) {
		float c = 0;

		// Draw the main Shape
		for (int a = pY; a >= pY - pHeight; a -= barOffset) {
			c += saColorScale;
			if (c < spectrumAnalyserColors.length)
				gc.setFill(spectrumAnalyserColors[(int) c]);

			gc.fillRect(pX, a, pWidth, 1);
		}

		// Draw The peaks
		// peakColor = (Color) gc.getFill();
		if (peakColor != null && peaksEnabled) {

			gc.setStroke(peakColor);
			if (pHeight > peaks[band]) {
				peaks[band] = pHeight;
				peaksDelay[band] = peakDelay;
			} else {
				peaksDelay[band] -= 2;
				if (peaksDelay[band] < 0)
					peaks[band] -= 2;
				if (peaks[band] < 0)
					peaks[band] = 0;
			}

			gc.strokeRect(pX, pY - peaks[band], pWidth, 1);
		}

	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 							VUMeter
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/**
	 * Draws a VUMeter.
	 */
	public void drawVUMeter(Orientation orientation) {

		// Background
		drawBackgroundImage();

		float wLeft = 0.0f;
		float wRight = 0.0f;
		float wSadfrr = vuDecay * frameRateRatioHint;

		for (int a = 0; a < getpLeftChannel().length; a++) {
			wLeft += Math.abs(getpLeftChannel()[a]);
			wRight += Math.abs(getpRightChannel()[a]);
		}

		wLeft = (wLeft = (wLeft * 2.0f) / getpLeftChannel().length) > 1.0f ? 1.0f : wLeft;
		wRight = (wRight = (wRight * 2.0f) / getpRightChannel().length) > 1.0f ? 1.0f : wRight;

		/*
		 * vuAverage += ( ( wLeft + wRight ) / 2.0f ); vuSamples++; if ( vuSamples > 128
		 * ) { vuSamples /= 2.0f; vuAverage /= 2.0f }
		 */

		if (wLeft >= (oldLeft - wSadfrr))
			oldLeft = wLeft;
		else {
			oldLeft -= wSadfrr;
			if (oldLeft < 0)
				oldLeft = 0;

		}

		if (wRight >= (oldRight - wSadfrr))
			oldRight = wRight;
		else {
			oldRight -= wSadfrr;
			if (oldRight < 0)
				oldRight = 0;

		}

		// Horizontal
		if (orientation == Orientation.HORIZONTAL) {
			int wHeight = (getCanvasHeight() >> 1) - 5;
			drawHorizontalVolumeMeterBar(2, 2, (int) (oldLeft * (float) (getCanvasWidth() - 2)), wHeight);
			drawHorizontalVolumeMeterBar(2, wHeight + 32, (int) (oldRight * (float) (getCanvasWidth() - 2)), wHeight);
			// Vertical
		} else {
			int wWidth = (getCanvasWidth() >> 1) - 5;
			drawVerticalVolumeMeterBar(4, getCanvasHeight(), wWidth - 1, (int) (oldLeft * (float) (getCanvasHeight())));
			drawVerticalVolumeMeterBar(wWidth + 8, getCanvasHeight(), wWidth - 1, (int) (oldRight * (float) (getCanvasHeight())));
		}

	}

	/**
	 * Draw volume meter bar.
	 *
	 * @param x       the x
	 * @param y       the y
	 * @param pWidth  the width
	 * @param pHeight the height
	 */
	private void drawHorizontalVolumeMeterBar(int x, int y, int pWidth, int pHeight) {

		// Draw the rectangles
		float c = 0;
		int max = x + pWidth;
		for (int a = x; a <= max; a += 2) {
			c += vuColorScale;
			if (c < 256.0f)
				gc.setStroke(spectrumAnalyserColors[(int) c]);

			gc.strokeRect(a, y, 2, pHeight);
		}

		// Drag the rectangles inside rectangles
		gc.setStroke(Color.BLACK);
		for (int a = x; a <= max; a += 15) {
			gc.strokeRect(a, y, 1, pHeight);
		}
	}

	/**
	 * Draw volume meter bar.
	 *
	 * @param x       the x
	 * @param y       the y
	 * @param pWidth  the width
	 * @param pHeight the height
	 */
	private void drawVerticalVolumeMeterBar(int x, int y, int pWidth, int pHeight) {

		// Draw the rectangles
		gc.setStroke(super.getScopeColor());

		// float c = 0;
		int min = y - pHeight;
		for (int a = getCanvasHeight(); a >= min; a -= 2) {
			// c += vuVColorScale;
			// if (c < 256.0f)
			// gc.setStroke(spectrumAnalyserColors[(int) c]);

			gc.strokeRect(x, a, pWidth, 2);
		}

		// Drag the rectangles inside rectangles
		gc.setStroke(Color.BLACK);
		for (int a = getCanvasHeight(); a >= min; a -= 15) {
			gc.strokeRect(x, a, pWidth, 1);
		}
	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 						    Sierpinski
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/**
	 * Draws the Sierpinski Triangles.
	 */
	public void drawSierpinski() {
		sierpinski.drawSierpinski();
	}

	/*-----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * 
	 * 						    Julia Fractals
	 * 
	 * -----------------------------------------------------------------------
	 * 
	 * -----------------------------------------------------------------------
	 */

	/**
	 * Draws the Julia Set
	 */
	public void drawJuliaSet() {
		juliaSet.drawJuliaSet();
	}

}
