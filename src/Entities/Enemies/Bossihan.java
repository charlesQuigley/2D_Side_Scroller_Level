package Entities.Enemies;

import Entities.*;
import TileMap.TileMap;
import TileMap.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * The boss class!
 * An object of this class represents the final boss of the game, who is at the end of the level.
 * Once this boss is defeated, the player wins the game and we go to the game's Win State.
 * This boss moves right to left and shoots Star projectiles that follow the player.
 * These star projectiles are unique to the boss and so are called BossStarPower class objects.
 */
public class Bossihan extends Enemy{

    private ArrayList<BossStarPower> stars; //We use this to have the star "keep traveling" even after its animation cycle
    //has ended. Really we're creating new star objects and changing their positions
    //with each new star object.
    private int starDMG; //how much attack power the star has.
    int starCounter; //This is used to time the boss' star projectiles without using System.nanoTime(), because
    //that would pause the whole game besides the music and keyListeners, as
    // the whole game is basically running on 1 thread.


    //Holds the boss's sprite images.
    private BufferedImage[] bossSprites;


    int playerXCoord, playerYCoord; //We want the boss' projectiles to follow the player.


    /**
     *
     * @param tm - is used to determine where to put the Boss at certain coordinates within the level.
     */
    public Bossihan(TileMap tm){
        super(tm); //Call the Enemy constructor


        spriteBoxWidth = 90; //boss sprite box is 90 pixels wide
        spriteBoxHeight = 90; //boss spirte box is 90 pixels tall
        spriteWidth = 40; //boss's actual sprite within the sprite box is about 40 wide.
        spriteHeight = 90; //boss's actual sprite within the sprite box is about 90 tall/

        speed = 1;
        maxSpeed = 0.5;

        starDMG = 1; //The boss' stars do 1 damage to the player.
        atk = 2; //The boss does 2 damage if the player collides into him.
        fullHealth = 60; //Boss is defeated after 12 of the player's stars.

        health = fullHealth;


        //BossSpriteSheet holds the entire image file for the Boss's sprites.
        //We then use .getSubimage() to parse through each individual sprite image within the sprite sheet.
        //It's like using a cookie cutter; you get a piece of the whole.
        //bossSprites[] then gets each individual sprite image and puts that sprite image in a different index.

        ///BufferedImage bossSpriteSheet = SpriteSheetLoader.SpriteSheetLoad(new File("Resources/Sprites/Enemies/boss.gif"));
        BufferedImage bossSpriteSheet = SpriteSheetLoader.SpriteSheetLoad(new File("Resources/Sprites/Enemies/bossCharles.png"));
        bossSprites = new BufferedImage[2]; //For now, we'll just have one boss image.

        assert bossSpriteSheet != null;
        bossSprites[0] = bossSpriteSheet.getSubimage(0, 0, spriteBoxWidth, spriteBoxHeight);
        bossSprites[1] = bossSpriteSheet.getSubimage(spriteBoxWidth * 1, 0, spriteBoxWidth, spriteBoxHeight);



        animation = new Animation();
        animation.setFrames(bossSprites);  //we use this to control how quickly each sprite image for the boss
                                            //gets shown on the screen for. It controls timing of the sprite animations.
        animation.setDelay(300);

        left = true; //Sets the movement for the boss to be starting in the left direction.
        face = "Left";


        stars = new ArrayList<BossStarPower>(); //Will hold the BossStar projectiles.

        starCounter = 0; //Is used to time when the boss shoots stars.
    }

    /**
     * This method handles the projected movement of the Boss on the screen and helps determine
     * whether or not the boss will collide with something.
     */
    private void getNextPosition() {
        if(right) {
            edgeX = edgeX + speed;
            if(edgeX > maxSpeed)  //correcting speed of boss movement issues when boss starts moving right.
            {
                edgeX = maxSpeed;
            }
        }
        else if(left) {
            edgeX = edgeX - speed; //correcting speed of boss movement issues when boss starts moving left.
            if(edgeX < -maxSpeed) {
                edgeX = -maxSpeed;
            }
        }

    }

    /**
     * This method sets the Boss's new position within the level, to make it look like he's moving.
     * This method also times when Boss's stars should fire and handles the star's movement and sprite changes.
     */
    public void update() {

        //Update our Star buffer
        for(int i = 0; i < stars.size(); i++)
        {
            stars.get(i).update(playerXCoord, playerYCoord);
            if (stars.get(i).deleteStar()) //If a star has collided with something and its disappearing sprites have
            {                               //already shown on screen.
                stars.remove(i); //Discard this star, it's time on screen should end.
                i--;

            }
        }

        starCounter = starCounter + 1;

        //We want the stars to be shot in a timed manner, not randomly.
        //Thus we don't use new Random().nextInt(). We, however, do NOT want
        //1 star to be shot every time the update() class is called,
        //that results in waaaaay too many stars because update() is called constantly.
        //So, every 100th update() call, we shoot a star. That spaces the stars out more.
        if(starCounter == 65)
        {

            BossStarPower star = new BossStarPower(tileMap, right);
            star.setPosition(x , y + 15); //where the star should go on the map.




            stars.add(star); //Star gets loaded into what is essentially our Star buffer.

            starCounter = 0; //after a star is shot, restart timer.

            animation.setFram(1); //Boss' image shown on screen.

        }
        else if(starCounter == 20)
        {
            animation.setFram(0);
        }


        getNextPosition(); //sets the edgeX variable
        checkTileMapCollision(); //uses edgeX in conjunction with Boss's x and y coordinates to
                                //determine if a collision will occur.
        setPosition(nextX, nextY); //Sets the Boss' position on the screen.


        //invincibility frames
        super.update();




        //direction change
        if(right && edgeX == 0)
        {
            right = false;
            left = true;
            face = "Left";
        }
        else if(left && edgeX == 0) {
            right = true;
            left = false;
            face = "Right";
        }

    }

    public void getHealth()
    {
        System.out.println("Health: " + health);
    }

    public void checkAttack(Player player)
    {
        //check star attack hit
        for(int j = 0; j < stars.size(); j++) {
            if(stars.get(j).intersects(player)) {
                player.hit(starDMG);
                stars.get(j).setLandedProjectile();
                break;
            }
        }
    }


    public void getPlayerYCoord(int playerYCoord)
    {
        this.playerYCoord = playerYCoord;
    }

    public void getPlayerXCoord(int playerXCoord)
    {
        this.playerXCoord = playerXCoord;
    }


    //Bossihan's hit box is different from regular enemies. His sprite asset sqaure is 90x90.
    //His actual head is roughly 60 x 60 pixels.
    //if regular characters are 30x30, we must add a few more pixels for his hitbox.
    //So, if spriteWidth is 30, we must add 30 to that.
    //y corresponds to the top of his sprite asset square. So, if you want to be able to jump
    //over his head, which starts 30 pixels into that square, you have to make y-coord 30 below what it was.
    //(+ numbers means down for y). This is the top of his head.
    //now, the box only goes 30
    //Now, if we didn't subtract 10 from spriteHeight at the end, the hit box would be all the way down to the ground.
    //By subtracting 10 pixels, the length of the hit box is short enough to where stars don't hit
    //him from the ground. Buuuut, the hit box is still long enough to where you cannot walk under him.
    @Override
    public Rectangle getRectangle() {
        return new Rectangle((int)x - spriteWidth, (int)(y +30) - spriteHeight,
                        spriteWidth + 30, spriteHeight - 10);
    }

    public void draw(Graphics2D g) {
        setMapPosition();
        super.draw(g);

        //draw dem stars
        for(int i = 0; i < stars.size(); i++) {
            stars.get(i).draw(g);
        }
    }
}
