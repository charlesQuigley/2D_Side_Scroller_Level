/* *
 * Background.java
 *
 * Creates a level map from a tileset.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package TileMap;

import java.awt.*;
import java.awt.image.*;

import java.io.*;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {
	
	// Tile x Position and Bounds
	private double x;
	private int xmin;
	private int xmax;

	// Tile y Position and Bounds
	private double y;
	private int ymin;
	private int ymax;
	
	// Tile Map
	private int[][] map;
	private int tSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// Tile Drawing
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles; 		//will hold all of our level's tiles
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;

	// Constructor
	public TileMap(int tileSize) {

		this.tSize = 30;

		//How many tiles we draw ON SCREEN at a time.
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
	}

	// Tokenizes individual tiles from the tileset
	public void loadTiles(String s) {
		try {	// Read the tileset
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			numTilesAcross = tileset.getWidth() / tSize;
			tiles = new Tile[2][numTilesAcross];

			// Read tile subimages into the tiles array
			BufferedImage si;
			for(int col = 0; col < numTilesAcross; col++) {
				si = tileset.getSubimage(col * tSize, 184, tSize, tSize);
				tiles[0][col] = new Tile(si, Tile.DETAIL);
				si = tileset.getSubimage(col * tSize, 184 + tSize, tSize, tSize);
				tiles[1][col] = new Tile(si, Tile.BLOCKED);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	// Create the map
	public void loadMap(String s) {
		try {	// Read the map file
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in)); //Now we can read map file
			
			numCols = Integer.parseInt(br.readLine()); 	// Number of columns we'll store in our int[][] map variable
			numRows = Integer.parseInt(br.readLine()); 	// Number of rows we'll store in our int[][] map variable
			map = new int[numRows][numCols];			// Tile Map
			width = numCols * tSize;	// Total map width
			height = numRows * tSize;	// Total map height

			// Map x boundary
			xmin = GamePanel.WIDTH - width;
			xmax = 0;

			// Map y boundary
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;

			// Delimeter is white space
			String delim = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();

				// Split each number in the map file using
				// the whitespaces as delimiters
				String[] tokens = line.split(delim);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);	// Each number in the map file goes in here
																	// separated by whitespace.
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		for(int row = rowOffset; row < rowOffset + numRowsToDraw; row++) {
			if(row >= numRows)
			{
				break; //Once we've drawn all the rows, exit this loop.
			}
			
			for(int col = colOffset; col < colOffset + numColsToDraw; col++) {
				if(col >= numCols) break;

				if (map[row][col] == 0) // The first tile is a transparent tile
				{
					continue;
				}

				// Determine which tile is being drawn
				int rc = map[row][col];			// Tile number
				int r = rc / numTilesAcross;  	// Row number in tileset
				int c = rc % numTilesAcross; 	// Column number in tileset

				/////
				//IN THE MAP FILE:
				//--------------------------
				//13 is the edge of a vertical wall
				//14 is a black tile with a rock in it
				//15 is a competely black tile
				//9 is a ground tile.
				
				g.drawImage(tiles[r][c].getImage(), (int)x + col * tSize, (int)y + row * tSize, null);
			}
		}
	}

	// Focus the camera on the player
	public void setPosition(double x, double y) {

		// Move to new coords
		this.x += (x - this.x);
		this.y += (y - this.y);

		// Make sure x and y are within bounds
		fixBounds();

		colOffset = (int)-this.x / tSize;
		rowOffset = (int)-this.y / tSize;
	}

	private void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}

	// Getters

	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getTileSize() { return tSize; }

	// Returns the type of a tile at a specified position
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross; //
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}


}
