/* *
 * GameWinState.java
 *
 * Manages the game's first level state, such as the drawing of assets and the
 * level itself. Also assists in music playing and enemy behavior.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package GameState;


import Main.GamePanel;
import Entities.*;
import Entities.Enemies.*;
import Audio.AudioPlayer;
import TileMap.*;
import Entity.HUD;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	//Player Variable
	private Player player;
	
	//Enemy Array List
	private ArrayList<Enemy> enemies;

	Bossihan b;
	
	//Explosion Array List
	private ArrayList<Explosion> explosions;
	
	//HUD call creator
	private HUD hud;
	
	//Bring in the Music
	private AudioPlayer bgMusic;
	private AudioPlayer explosion_sound;

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/cave2.png");
		tileMap.loadMap("/Maps/level.map");
		tileMap.setPosition(0, 0);
		
		//set Background
		bg = new Background("/Backgrounds/night_bg.gif", 0.1);
		
		//set Player
		player = new Player(tileMap);
		player.setPosition(100, 100); //3000 is good if you want to go directly to boss.
		
		//setEnemies
		populateEnemies();
		
		//set HUD
		hud = new HUD(player);
		
		//set explosions
		explosions = new ArrayList<Explosion>();
		
		//Make Some Noise
		bgMusic = new AudioPlayer("/Music/calm_music_level1.mp3");
		//bgMusic = new AudioPlayer("/SFX/BossHurtNoise.mp3");
		explosion_sound = new AudioPlayer("/SFX/enemy_explosion.mp3");
		bgMusic.play();


		
	}
	
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		Spider s;
		Point[] points = new Point[] {new Point(200,100), new Point(653, 160), new Point(614, 160), new Point(534, 160), new Point(850, 140),new Point(932, 140), new Point(1612,200),
									  new Point(1710,200), new Point(1885,200), new Point(1924,200), new Point(2144,50), new Point(2220,50)};
		
		for(int i = 0; i < points.length; i++) {
			s = new Spider(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
		b = new Bossihan(tileMap);
		//b.setPosition(3055, 135);
		b.setPosition(3055, 125);
		enemies.add(b);
		
	}
	
	public void update() {

		if(!bgMusic.clip.isRunning()) {
			bgMusic.stop();
			bgMusic.play();
			//System.out.println("Music Restarted");
		}
		//update player
		player.update();

		//System.out.printf("X: %d\nY: %d\n" ,player.getx(), player.gety());
		if(player.maybeLost()) {
			bgMusic.stop();
			gsm.setState(0);
		}
		
		tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());

		
		//set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//attack enemies
		player.checkAttack(enemies);


		//Since the boss object was put in the enemies array (holds objects of class Enemy)
		//we need to now cast the boss object back from Enemy class to Bossihan class.
		//This is so we can access the boss method checkAttack(player), which checks if the boss'
		//fireballs have hit the player, damaging said player.
		b = (Bossihan) enemies.get(enemies.size() - 1);

		b.getPlayerYCoord(player.gety());
		b.getPlayerXCoord(player.getx());

		b.checkAttack(player);


		//update enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			
			if(e.getDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
				explosion_sound.play();
				

				player.setfullStar();
				
				/*if(i == enemies.size()-1) {

					bgMusic.stop();
					gsm.setState(GameStateManager.GameWinState);
				}*/
			}


		}
		
		//update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}

	}
	
	public void draw(Graphics2D g) {
		//draw background
		bg.draw(g);
		
		//draw tilemap
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
		
		//draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		//draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		//draw hud
		hud.draw(g);
	}
	
	public void keyPressed(int k) {

		if(b.getDead()) {
			bgMusic.stop();
			gsm.setState(GameStateManager.GameWinState);
		}


		if (k == KeyEvent.VK_A) player.setLeft(true);
		if (k == KeyEvent.VK_D) player.setRight(true);
		if (k == KeyEvent.VK_W) player.setUp(true);
		if (k == KeyEvent.VK_S) player.setDown(true);
		if (k == KeyEvent.VK_W) player.setJumping(true);
		if (k == KeyEvent.VK_P) player.setHover(true);
		if (k == KeyEvent.VK_O) player.setStaffSwing();
		if (k == KeyEvent.VK_I) player.shootStar();


		//To go directly to the boss with full health and max fire = 16.
		if(k == KeyEvent.VK_PERIOD)
		{
			player.maxFireBecome16();
			player.setHealthToMax();
			player.setPosition(2800, 100);
		}


	}
	public void keyReleased(int k) {

		if(b.getDead()) {
			bgMusic.stop();
			gsm.setState(GameStateManager.GameWinState);
		}

		if (k == KeyEvent.VK_A) player.setLeft(false);
		if (k == KeyEvent.VK_D) player.setRight(false);
		if (k == KeyEvent.VK_W) player.setUp(false);
		if (k == KeyEvent.VK_S) player.setDown(false);
		if (k == KeyEvent.VK_W) player.setJumping(false);
		if (k == KeyEvent.VK_P) player.setHover(false);

	}


}
