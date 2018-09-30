package main.java.com.goxr3plus.xr3player.smartcontroller.tags.mp3;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.ID3v24FieldKey;
import org.jaudiotagger.tag.id3.ID3v24Tag;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import main.java.com.goxr3plus.xr3player.application.tools.general.InfoTool;

/**
 * This class is capable of fully modifying mp3 ID3V1 tags
 * 
 * @author GOXR3PLUS
 *
 */
public class ID3v2 extends StackPane {
	
	//--------------------------------------------------------------
	
	@FXML
	private TextField artistField;
	
	@FXML
	private TextField albumField;
	
	@FXML
	private TextField commentField;
	
	@FXML
	private TextField genreField;
	
	@FXML
	private TextField yearField;
	
	// -------------------------------------------------------------
	
	/** The logger. */
	private final Logger logger = Logger.getLogger(getClass().getName());
	
	private final ID3v24Service service = new ID3v24Service();
	
	/**
	 * Constructor.
	 */
	public ID3v2() {
		
		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource(InfoTool.TAGS_FXMLS + "ID3V2.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "", ex);
		}
		
	}
	
	/**
	 * Called as soon as .FXML is loaded from FXML Loader
	 */
	@FXML
	private void initialize() {
		
	}
	
	/**
	 * Starts the Service that populates the ID3v1 Fields
	 * 
	 * @param fileAbsolutePath
	 */
	public void populateTagFields(String fileAbsolutePath) {
		service.startService(fileAbsolutePath);
	}
	
	/**
	 * Using this Service as an external Thread which updates the Information based on the selected Media
	 * 
	 * @author GOXR3PLUS
	 *
	 */
	public class ID3v24Service extends Service<Void> {
		
		//Fields
		private String artist = "";
		private String album = "";
		private String comment = "";
		private String genre = "";
		private String year = "";
		
		/**
		 * The absolute path of the given file
		 */
		private String fileAbsolutePath;
		
		public void startService(String fileAbsolutePath) {
			this.fileAbsolutePath = fileAbsolutePath;
			
			//Restart the Service
			this.restart();
			
		}
		
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				
				@Override
				protected Void call() throws Exception {
					
					//Fields
					artist = "...";
					album = "...";
					comment = "...";
					genre = "...";
					year = "...";
					
					try {
						//MP3File
						MP3File mp3File = new MP3File(fileAbsolutePath);
						
						//Check if it has ID3v1Tag
						if (mp3File.hasID3v2Tag()) {
							
							ID3v24Tag tag = mp3File.getID3v2TagAsv24();
							
							//-- Artist
							artist = tag.getFirst(ID3v24FieldKey.ARTIST);
							
							//-- Album
							album = tag.getFirst(ID3v24FieldKey.ALBUM);
							
							//-- Comment
							comment = tag.getFirst(ID3v24FieldKey.COMMENT);
							
							//-- Genre
							genre = tag.getFirst(ID3v24FieldKey.GENRE);
							
							//-- Year
							year = tag.getFirst(ID3v24FieldKey.YEAR);
							
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
					//Populate the fields
					Platform.runLater(() -> {
						
						artistField.setText(artist);
						
						albumField.setText(album);
						
						commentField.setText(comment);
						
						genreField.setText(genre);
						
						yearField.setText(year);
						
					});
					return null;
				}
			};
		}
		
		public String getFileAbsolutePath() {
			return fileAbsolutePath;
		}
		
	}
	
}
