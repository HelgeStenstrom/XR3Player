package com.goxr3plus.xr3player.services.xplayer;

import java.io.File;
import java.util.logging.Level;

import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import com.goxr3plus.xr3player.application.Main;
import com.goxr3plus.xr3player.controllers.xplayer.XPlayerController;
import com.goxr3plus.xr3player.enums.NotificationType;
import com.goxr3plus.xr3player.utils.general.InfoTool;
import com.goxr3plus.xr3player.utils.general.TimeTool;
import com.goxr3plus.xr3player.utils.javafx.AlertTool;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.util.Duration;

/**
 * This Service is used to skip the Audio of XPlayer to different time.
 *
 * @author GOXR3PLUS
 */
public class XPlayerSeekService extends Service<Boolean> {

	/** The bytes to be skipped */
	long bytesToSkip;

	/**
	 * I am using this variables when i want to stop the player and go to a specific
	 * time for example at 1 m and 32 seconds :)
	 */
	boolean stopPlayer;

	/**
	 * Determines if the Service is locked , if yes it can't be used .
	 */
	private volatile boolean locked;

	private final XPlayerController xPlayerController;

	/**
	 * Constructor.
	 */
	public XPlayerSeekService(final XPlayerController xPlayerController) {
		this.xPlayerController = xPlayerController;
		setOnSucceeded(s -> done());
		setOnFailed(f -> done());
		setOnCancelled(f -> done());

	}

	/**
	 * Start the Service.
	 *
	 * @param bytesToSkip Bytes to skip
	 * @param stopPlayer If true will stop player
	 */
	public void startSeekService(final long bytesToSkip, final boolean stopPlayer) {
		final String absoluteFilePath = xPlayerController.xPlayerModel.songPathProperty().get();

		// First security check
		if (locked || isRunning() || absoluteFilePath == null) {
			return;
		}

		// Check if the file exists
		if (!new File(absoluteFilePath).exists()) {
			AlertTool.showNotification("Media doesn't exist", "Current Media File doesn't exist anymore...",
				Duration.seconds(2), NotificationType.ERROR);
			done();

			// Fix the labels
			xPlayerController.fixPlayerStop();

			// Set Indicator Progress
			xPlayerController.getProgressIndicator().progressProperty().set(-1.0);

			return;
		}

		// Check if the bytesTheUser wants to skip are more than the total bytes of the
		// audio
		if (bytesToSkip > xPlayerController.xPlayer.getTotalBytes())
			return;

		// System.out.println(bytes)

		// StopPlayer
		this.stopPlayer = stopPlayer;

		// Bytes to Skip
		this.bytesToSkip = bytesToSkip;

		// Create Binding
		if (!xPlayerController.speedIncreaseWorking) {
			xPlayerController.getFxLabel().textProperty().bind(messageProperty());
			xPlayerController.getRegionStackPane().visibleProperty().bind(runningProperty());
		}

		// lock the Service
		locked = true;

		// Restart
		reset();
		start();
	}

	/**
	 * When the Service is done.
	 */
	private void done() {

		// Remove the unidirectional binding
		xPlayerController.getFxLabel().textProperty().unbind();
		xPlayerController.getRegionStackPane().visibleProperty().unbind();
		xPlayerController.getRegionStackPane().setVisible(false);

		// Stop disc dragging!
		xPlayerController.discIsDragging = false;

		// Speed Control
		xPlayerController.speedIncreaseWorking = false;

		// Put the appropriate Cursor
		xPlayerController.disc.getCanvas().setCursor(Cursor.OPEN_HAND);

		// Recalculate Angle and paint again Disc
		xPlayerController.disc.calculateAngleByValue(xPlayerController.xPlayerModel.getCurrentTime(), xPlayerController.xPlayerModel.getDuration(), true);
		xPlayerController.disc.repaint();

		// unlock the Service
		locked = false;

	}

	@Override
	protected Task<Boolean> createTask() {
		return new Task<>() {
			@Override
			protected Boolean call() throws Exception {
				boolean succeded = true;

				// ----------------------- Seek the Media
				updateMessage(!xPlayerController.speedIncreaseWorking ? "Going to : [ "
					+ TimeTool.getTimeEdited(xPlayerController.xPlayerModel.getCurrentAngleTime()) + " ]"
					: "Speed Level : x" + InfoTool
					.getMinString(String.valueOf(xPlayerController.xPlayer.getSpeedFactor()), 4, ""));

				// Stop?
				if (stopPlayer)
					xPlayerController.xPlayer.stop();

				// GO
				// if (bytes != 0) { // and xPlayer.isPausedOrPlaying())
				Main.logger.info("Seek Service Started..");

				// CurrentTime
				xPlayerController.xPlayerModel
					.setCurrentTime(xPlayerController.xPlayerModel.getCurrentAngleTime());

				try {
					xPlayerController.xPlayer.seekBytes(bytesToSkip);
				} catch (final StreamPlayerException ex) {
					xPlayerController.logger.log(Level.WARNING, "", ex);
					succeded = false;
				}
				// }

				// ----------------------- Play Audio
				if (!xPlayerController.xPlayer.isPausedOrPlaying()) {
					xPlayerController.xPlayer.play();
					// xPlayer.pause();
				}

				// ----------------------- Configuration
				updateMessage("Applying Settings ...");

				// Configure Media Settings
				if (xPlayerController.xPlayer.isPausedOrPlaying())
					xPlayerController.configureMediaSettings(true);

				return succeded;
			}

		};
	}

}
