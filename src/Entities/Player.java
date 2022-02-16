package Entities;

import TileMap.*;
import Audio.AudioPlayer;

import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends Entity{
	
	//player stats
	private int health;
	private int fullHealth;
	private int star;
	private int fullStar;
	private boolean dead;
	private boolean iFrame;
	private long invincibilityTimer;

	private AudioPlayer explosion_sound;

	private int bossNoiseTimer;
	
	//fireball
	private boolean shoot;
	private int starCost;
	private int starAtk;
	private ArrayList<Star> starsArray;
	
	//scratch
	private boolean staff;
	private int staffAtk;
	private int rangeOfStaff;
	
	//gliding
	private boolean hover;
	
	//animations
	private ArrayList<BufferedImage[]> playerSprites;
	private final int[] numFrames = {1, 6, 1, 1, 1, 2, 6};
	
	//animation actions
	private static final int Stagnant = 0;
	private static final int Walk = 1;
	private static final int Jump = 2;
	private static final int Fall = 3;
	private static final int Hover = 4;
	private static final int StarShoot = 5;
	private static final int StaffSwing = 6;
	
	//Audio
	private HashMap<String, AudioPlayer> sounds;
	
	public Player(TileMap tm) {
		super(tm);
		
		spriteBoxWidth = 32;
		spriteBoxHeight = 35;
		spriteWidth = 20;
		spriteHeight = 20;
		
		speed = 0.3;
		maxSpeed = 1.6;
		noSpeed = 0.4;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jump = -4.8;
		stopJump = 0.3;
		
		face = "Right";
		
		fullHealth = 5;
		health = fullHealth;
		star = fullStar = 400; // 2500 //maximum Fire starts at 400. Everytime you defeat an enemy, you get 100 more max fire.
		
		starCost = 200; // 200 //it costs 400 to shoot a fireball
		starAtk = 5;
		starsArray = new ArrayList<Star>();
		
		staffAtk = 8;
		rangeOfStaff = 40;
		
		//load sprites

			BufferedImage playerSheet = SpriteSheetLoader.SpriteSheetLoad(new File("Resources/Sprites/Player/Tiki Sprite.gif"));
			playerSprites = new ArrayList<BufferedImage[]>();
			
			BufferedImage[] stagnant = new BufferedImage[numFrames[0]];
			BufferedImage[] walk = new BufferedImage[numFrames[1]];
			BufferedImage[] jump = new BufferedImage[numFrames[2]];
			BufferedImage[] fall = new BufferedImage[numFrames[3]];
			BufferedImage[] hover = new BufferedImage[numFrames[4]];
			BufferedImage[] star = new BufferedImage[numFrames[5]];
			BufferedImage[] staff = new BufferedImage[numFrames[6]];
			
			for(int i = 0; i < 7; i++) {
				for(int j = 0; j < numFrames[i]; j++) {
				
					switch(i) {
						case 0:
							stagnant[j] = playerSheet.getSubimage(j*spriteBoxWidth, i*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);
							break;
						case 1:
							if(j == 0) {walk[j] = playerSheet.getSubimage(j*spriteBoxWidth, 2*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 1) {walk[j] = playerSheet.getSubimage(j*spriteBoxWidth, 2*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 2) {walk[j] = playerSheet.getSubimage(j*spriteBoxWidth, 2*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 3) {walk[j] = playerSheet.getSubimage(j*spriteBoxWidth, 2*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 4) {walk[j] = playerSheet.getSubimage(j*spriteBoxWidth, 2*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 5) {walk[j] = playerSheet.getSubimage(j*spriteBoxWidth, 2*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							break;
						case 2:
							jump[j] = playerSheet.getSubimage(3*spriteBoxWidth, 0*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);
							break;
						case 3:
							fall[j] = playerSheet.getSubimage(4*spriteBoxWidth, 0*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);
							break;
						case 4:
							hover[j] = playerSheet.getSubimage(5*spriteBoxWidth, 0*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);
							break;
						case 5:
							if(j == 0) {star[j] = playerSheet.getSubimage(3*spriteBoxWidth, 0*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 1) {star[j] = playerSheet.getSubimage(4*spriteBoxWidth, 0*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							break;
						case 6:
							if(j == 0) {staff[j] = playerSheet.getSubimage(0, 3*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 1) {staff[j] = playerSheet.getSubimage(j*spriteBoxWidth, 3*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 2) {staff[j] = playerSheet.getSubimage(j*spriteBoxWidth, 3*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 3) {staff[j] = playerSheet.getSubimage(j*spriteBoxWidth, 3*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 4) {staff[j] = playerSheet.getSubimage(j*spriteBoxWidth, 3*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							if(j == 5) {staff[j] = playerSheet.getSubimage(j*spriteBoxWidth, 3*spriteBoxHeight, spriteBoxWidth, spriteBoxHeight);}
							break;
					}
				
				}
			}
			playerSprites.add(stagnant);
			playerSprites.add(walk);
			playerSprites.add(jump);
			playerSprites.add(fall);
			playerSprites.add(hover);
			playerSprites.add(star);
			playerSprites.add(staff);

		
		animation = new Animation();
		currentAction = Stagnant;
		animation.setFrames(playerSprites.get(Stagnant));
		animation.setDelay(400);
		
		sounds = new HashMap<String, AudioPlayer>();
		sounds.put("jump", new AudioPlayer("/SFX/jump.mp3"));
		sounds.put("scratch", new AudioPlayer("/SFX/scratch.mp3"));
		sounds.put("fireball_sound", new AudioPlayer("/SFX/fireball_effect.mp3"));
		sounds.put("Ouch", new AudioPlayer("/SFX/Ouch.mp3"));


		bossNoiseTimer = 0;
		
	}
	
	public int getHealth() { return health; }
	public int getFullHealth() { return fullHealth; }
	public int getStar() { return star; }
	public int getfullStar() { return fullStar; }

	public void setfullStar()
	{
		fullStar = fullStar + 100;
	}

	public void fullStarBecome16()
	{
		fullStar = 1600;
	}

	public void maxFireBecome16()
	{
		fullStar = 1600;
	}

	public void setHealthToMax()
	{
		health = fullHealth;
	}

	public void shootStar() {
		shoot = true;
	}
	public void setStaffSwing()
	{
		staff = true;
	}
	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public int getFullStar()
	{
		return fullStar;
	}

	
	public void checkAttack(ArrayList<Enemy> enemies) {
		
		//Enemy array loop
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			
			//check Scratch attack hit
			if(staff) {
				if(face.equals("Right")) {
					if(e.getx() > x && e.getx() < x + rangeOfStaff && e.gety() > y - spriteBoxHeight/2 && e.gety() < y + spriteBoxHeight/2) {
						e.attack(staffAtk);
					}
				}
				else {
					if(e.getx() < x && e.getx() > x - rangeOfStaff && e.gety() > y - spriteBoxHeight/2 && e.gety() < y + spriteBoxHeight/2) {
						e.attack(staffAtk);
					}
				}
			}
			
			//check Fireball attack hit
			for(int j = 0; j < starsArray.size(); j++) {
				if(starsArray.get(j).intersects(e)) {
					e.attack(starAtk);
					starsArray.get(j).setLandedProjectile();

					//if we're dealing with an attack on the boss
					if(e == enemies.get(enemies.size() -1) && bossNoiseTimer >= 50)
					{
						bossNoiseTimer = 0;
						explosion_sound = new AudioPlayer("/SFX/BossHurtNoise.mp3");
						explosion_sound.play();
						explosion_sound.stop();
					}
					break;
				}
			}
			
			//check Enemy Intersection Collision
			if(intersects(e)) {
				hit(e.getAttackPoints());
			}
			
		}
		
	}
	
	public void hit(int damage) {
		if(iFrame) return;
		sounds.get("Ouch").play();
		health -= damage;
		if(health <= 0) dead = true;
		iFrame = true;
		invincibilityTimer = System.nanoTime();
	}
	
	private void getNextPosition() {
		//movement
		if(left) {
			edgeX -= speed;
			if(edgeX < -maxSpeed) {
				edgeX = -maxSpeed;
			}
		}
		else if(right) {
			edgeX += speed;
			if(edgeX > maxSpeed) {
				edgeX = maxSpeed;
			}
		}
		else {
			if(edgeX > 0) {
				edgeX -= speed;
				if(edgeX < 0) { edgeX = 0;}
			}
			else if(edgeX < 0) {
				edgeX += speed;
				if(edgeX > 0) { edgeX = 0; }
			}
		}
		
		//no moving attacks
		if((currentAction == StaffSwing || currentAction == StarShoot) && !(jumping || falling)) { edgeX = 0; }
		
		//jumping
		if(jumping && !falling) {
			sounds.get("jump").play();
			sounds.get("jump").stop();
			edgeY = jump;
			falling = true;
		}
		
		//falling
		if(falling) {
			if(edgeY > 0 && hover) edgeY += fallSpeed * 0.1;
			else edgeY += fallSpeed;
			
			if(edgeY > 0) jumping = false;
			if(edgeY < 0 && !jumping) edgeY += stopJump;
			
			if(edgeY > maxFallSpeed) edgeY = maxFallSpeed;
		}
	}
	
	public void update() {
		//update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(nextX, nextY);

		bossNoiseTimer++;
		
		//If we Fall
		if(y > 220) dead = true;
		
		//Stop the attack
		if(currentAction == StaffSwing) {
			if(animation.hasPlayedOnce()) staff = false;
		}
		if(currentAction == StarShoot) {
			if(animation.hasPlayedOnce()) shoot = false;
		}
		
		//fireball attack
		star += 2;
		if(star > fullStar) star = fullStar;
		if(shoot && currentAction != StarShoot) {
			if(star > starCost) {
				star -= starCost;

				if(face.equals("Right")) {
					Star fb = new Star(tileMap, true);
					fb.setPosition(x, y);
					starsArray.add(fb);
				}
				else if(face.equals("Left"))
				{
					Star fb = new Star(tileMap, false);
					fb.setPosition(x, y);
					starsArray.add(fb);
				}

			}
		}
		
		//Fireball update
		for(int i = 0; i < starsArray.size(); i++) {
			starsArray.get(i).update();
			if(starsArray.get(i).deleteStar()) {
				starsArray.remove(i);
				i--;
			}
		}
		
		//Flinch Checker
		if(iFrame) {
			long time = (System.nanoTime() - invincibilityTimer) / 1000000;
			if(time > 1000) { iFrame = false; }
		}
		
		//set animation
		if(staff) {
			if(currentAction != StaffSwing) {
				sounds.get("scratch").play();
				currentAction = StaffSwing;
				animation.setFrames(playerSprites.get(StaffSwing));
				animation.setDelay(50);
				spriteBoxWidth = 60;
			}
		}
		else if(shoot) {
			if(currentAction != StarShoot) {
				sounds.get("fireball_sound").play();
				currentAction = StarShoot;
				animation.setFrames(playerSprites.get(StarShoot));
				animation.setDelay(100);
				spriteBoxWidth = 30;
			}
		}
		else if(edgeY > 0) {
			if(hover) {
				if(currentAction != Hover) {
					currentAction = Hover;
					animation.setFrames(playerSprites.get(Hover));
					animation.setDelay(100);
					spriteBoxWidth = 30;
				}
			}
			else if(currentAction != Fall) {
				currentAction = Fall;
				animation.setFrames(playerSprites.get(Fall));
				animation.setDelay(100);
				spriteBoxWidth = 30;
			}
		}
		else if(edgeY < 0) {
			if(currentAction != Jump) {
				currentAction = Jump;
				animation.setFrames(playerSprites.get(Jump));
				animation.setDelay(-1);
				spriteBoxWidth = 30;
			}
		}
		else if(left || right) {
			if(currentAction != Walk) {
				currentAction = Walk;
				animation.setFrames(playerSprites.get(Walk));
				animation.setDelay(40);
				spriteBoxWidth = 30;
			}
		}
		else {
			if(currentAction != Stagnant) {
				currentAction = Stagnant;
				animation.setFrames(playerSprites.get(Stagnant));
				animation.setDelay(400);
				spriteBoxWidth = 30;
			}
		}
		
		animation.update();
		
		//set direction
		if(currentAction != StaffSwing && currentAction != StarShoot) {
			if(right) face = "Right";
			if(left) face = "Left";
		}
		
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		
		//draw fireballs
		for(int i = 0; i < starsArray.size(); i++) {
			starsArray.get(i).draw(g);
		}
		
		//draw player
		if(iFrame) {
			long time = (System.nanoTime() - invincibilityTimer) / 1000000;
			if(time / 100 % 2 == 0) { return; }
		}
		
		super.draw(g);
	}
	
	public boolean maybeLost() {
		return dead;
	}
	
	
	
}
