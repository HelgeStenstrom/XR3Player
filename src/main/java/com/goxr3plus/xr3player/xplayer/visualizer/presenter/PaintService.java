package com.goxr3plus.xr3player.xplayer.visualizer.presenter;

import com.goxr3plus.xr3player.application.Main;
import com.goxr3plus.xr3player.controllers.xplayer.GadgetOwner;
import com.goxr3plus.xr3player.controllers.xplayer.XPlayerController;
import com.goxr3plus.xr3player.xplayer.visualizer.geometry.Oscilloscope;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Orientation;
import javafx.scene.paint.Color;

/**
 * This Service is updating the visualizer.
 *
 * @author GOXR3PLUS
 */
public class PaintService extends AnimationTimer {

    private Visualizer visualizer;
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
    private final GadgetOwner gadgetOwner;

    public PaintService(Visualizer visualizer) {
        this.visualizer = visualizer;
        final Oscilloscope oscilloscope = new Oscilloscope(visualizer);
        gadgetOwner = new GadgetOwner(oscilloscope);
    }

    @Override
    public void start() {
        // Values must be >0
        if (visualizer.getCanvasWidth() <= 0 || visualizer.getCanvasHeight() <= 0) {
            visualizer.setCanvasHeight(1);
            visualizer.setCanvasWidth(1);
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
        if (Main.settingsWindow != null
                && !Main.settingsWindow.getGeneralSettingsController().getHighGraphicsToggle().isSelected())
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
            visualizer.clear();
            draw = false;
            return;
        }

        // No need to draw the visualizer if player is on microMode
        if (!xPlayerController.getModesStackPane().isVisible()) {
            visualizer.clear();
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
        ++visualizer.framesPerSecond;

        // Check for 1 second passed
        if (nanos >= nextSecond) {
            visualizer.setFps(visualizer.framesPerSecond);
            visualizer.framesPerSecond = 0;
            nextSecond = nanos + ONE_SECOND_NANOS;
        }

        // System.out.println("Animation Timer is Running");

        // Can draw?
        if (draw) {
            visualizer.clear();
            switch (visualizer.displayMode.get()) {

            case 0:
                gadgetOwner.drawOscilloscope(false);
                break;
            case 1:
                gadgetOwner.drawOscilloscope(true);
                break;
            case 2:
                gadgetOwner.drawOscilloScopeLines();
                break;
            case 3:
                visualizer.drawSpectrumBars();
                break;
            case 4:
                visualizer.drawVUMeter(Orientation.HORIZONTAL);
                break;
            case 5:
                visualizer.drawPolySpiral();
                break;
            case 6:
                visualizer.drawCircleWithLines();
                break;
            case 7:
                visualizer.drawSierpinski();
                break;
            case 8:
                visualizer.drawSprite3D();
                break;
            case 9:
                visualizer.drawVUMeter(Orientation.VERTICAL);

                break;
            case 10:
                visualizer.drawJuliaSet();
                break;
            default:
                break;
            }

            // -- Show FPS if necessary.
            if (visualizer.isShowFPS()) {
                visualizer.gc.setFill(Color.BLACK);
                visualizer.gc.fillRect(0, visualizer.getCanvasHeight() - 15.00, 50, 28);
                visualizer.gc.setStroke(Color.WHITE);
                visualizer.gc.strokeText("FPS: " + visualizer.getFps(), 0, visualizer.getCanvasHeight() - 3.00); // + " (FRRH: " + frameRateRatioHint + ")"
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
