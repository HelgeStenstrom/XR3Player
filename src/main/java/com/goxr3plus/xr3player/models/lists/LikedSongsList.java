package com.goxr3plus.xr3player.models.lists;

/**
 * This class represents a List of Songs that User Likes
 * 
 * @author GOXR3PLUS
 *
 */
public class LikedSongsList extends DatabaseList {

	/**
	 * The name of the database table
	 */
	private static final String dataBaseTableName = "LikedMediaListOriginal";

	/**
	 * Constructor
	 *
	 */
	public LikedSongsList() {
		super(dataBaseTableName);
	}

}
