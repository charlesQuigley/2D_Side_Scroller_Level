package Entities;

import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;

import Main.GamePanel;

public abstract class Entity {
	
	//tile stuff
	protected TileMap tileMap;
	protected int tileSize;
	protected double xmap;
	protected double ymap;
	
	//position and vector
	protected double x;
	protected double y;
	protected double edgeX;
	protected double edgeY;

	//How big each sprite's sprite box is
	protected int spriteBoxWidth;
	protected int spriteBoxHeight;

	//Actual sprite images.
	protected int spriteWidth;
	protected int spriteHeight;

	//collision
	protected int currRow;
	protected int currCol;
	protected double xdest;
	protected double ydest;
	protected double nextX;
	protected double nextY;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;

	//animation
	protected Animation animation;
	protected int currentAction;
	protected int previousAction;
	protected String face;

	//movement
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;

	//movement attributes
	protected double speed;
	protected double maxSpeed;
	protected double noSpeed;
	protected double fallSpeed;
	protected double maxFallSpeed;
	protected double jump;
	protected double stopJump;

	//constructor
	public Entity(TileMap level) {
		tileMap = level;
		tileSize = level.getTileSize();
	}

	public boolean intersects(Entity o) {
		Rectangle r1 = getRectangle();
		Rectangle r2 = o.getRectangle();
		return r1.intersects(r2);
	}

	public Rectangle getRectangle() {
		return new Rectangle((int)x - spriteWidth, (int)y - spriteHeight, 
								spriteWidth, spriteHeight);
	}

	public void calculateCorners(double x, double y) {
		int leftTile = (int)(x - spriteWidth / 2) / tileSize;
		int rightTile = (int)(x + spriteWidth / 2 - 1) / tileSize;
		int topTile = (int)(y - spriteHeight / 2) / tileSize;
		int bottomTile = (int)(y + spriteHeight / 2 - 1) / tileSize;

		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);


		topLeft = tl == Tile.BLOCKED;
		topRight = tr == Tile.BLOCKED;
		bottomLeft = bl == Tile.BLOCKED;
		bottomRight = br == Tile.BLOCKED;
	}

	public void checkTileMapCollision() {
		currCol = (int)x / tileSize;
		currRow = (int)y / tileSize;

		xdest = x + edgeX;
		ydest = y + edgeY;

		nextX = x;
		nextY = y;

		calculateCorners(x, ydest);

		if(edgeY < 0) {
			if(topLeft || topRight) {
				edgeY = 0;
				nextY = currRow * tileSize + spriteHeight / 2;
			}
			else {
				nextY += edgeY;
			}
		}
		if(edgeY > 0) {
			if(bottomLeft || bottomRight) {
				edgeY = 0;
				falling = false;
				nextY = (currRow + 1) * tileSize - spriteHeight / 2;
			}
			else {
				nextY += edgeY;
			}
		}

		calculateCorners(xdest, y);

		if(edgeX < 0) {
			if(topLeft || bottomLeft) {
				edgeX = 0;
				nextX = currCol * tileSize + spriteWidth / 2;
			}
			else {
				nextX += edgeX;
			}
		}
		if(edgeX > 0) {
			if(topRight || bottomRight) {
				edgeX = 0;
				nextX = (currCol + 1) * tileSize - spriteWidth / 2;
			}
			else {
				nextX += edgeX;
			}
		}

		if(!falling) {
			calculateCorners(x, ydest + 1);
			if(!bottomLeft && !bottomRight) {
				falling = true;
			}
		}
	}

	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getspriteBoxWidth() { return spriteBoxWidth; }
	public int getspriteBoxHeight() { return spriteBoxHeight; }
	public int getspriteWidth() { return spriteWidth; }
	public int getspriteHeight() { return spriteHeight; }

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setVector(double edgeX, double edgeY) {
		this.edgeX = edgeX;
		this.edgeY = edgeY;
	}
	
	public void setMapPosition() {
		xmap = tileMap.getx();
		ymap = tileMap.gety();
	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	public void setJumping(boolean b) { jumping = b; }
	
	public boolean notOnScreen() { 
		return x + xmap + spriteBoxWidth < 0 ||
				x + xmap - spriteBoxWidth > GamePanel.WIDTH ||
				y + ymap + spriteBoxHeight < 0 ||
				y + ymap - spriteBoxHeight > GamePanel.HEIGHT;
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(face.equals("Right")) {
			g.drawImage(animation.getImage(), (int)(x+xmap-spriteBoxWidth/2), (int)(y+ymap-spriteBoxHeight/2), null);
		}
		else {
			g.drawImage(animation.getImage(), (int)(x+xmap-spriteBoxWidth/2+spriteBoxWidth),
					(int)(y+ymap-spriteBoxHeight/2), -spriteBoxWidth, spriteBoxHeight, null);
		}
	}
	
	
	
}
