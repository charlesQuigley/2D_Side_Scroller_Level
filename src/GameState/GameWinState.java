/* *
 * GameWinState.java
 *
 * Manages the game's win state, such as the drawing of assets and the
 * level itself.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package GameState;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import Audio.AudioPlayer;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GameWinState extends GameState{

    private JButton endGame; //Not implemented yet. We can use this if we want a button on this screen.
    GameStateManager gsm; // This is used to bring us back to the MENU state.


    int xCoord, yCoord; //Used for animating the player.

    BufferedImage[] sprite; //Used to hold one row of player sprites.
    BufferedImage[] bossSprite;

    int walkCounter; //Is used for animating the player.


    ArrayList<BufferedImage[]> playerSprites; //Gets all of the player sprites.
    private final int[] numFrames = {2, 8, 1, 2, 4, 2, 5};


    Image endScreenBackground;
    
  //Bring in the Music
  	private AudioPlayer bgMusic;


    public GameWinState(GameStateManager gsm)
    {
        this.gsm = gsm;


        //THE FOLLOWING CODE IS COPIED FROM THE PLAYER CLASS VERBATIM...I HAD TO GET PLAYER SPRITES FOR THIS SCREEN....
        int width = 32;
        int height = 35;


        try {
        	BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/Tiki Sprite.gif"));
			playerSprites = new ArrayList<BufferedImage[]>();
			
			BufferedImage[] idle_bufIm = new BufferedImage[numFrames[0]];
			BufferedImage[] walk_bufIm = new BufferedImage[numFrames[1]];
			BufferedImage[] jump_bufIm = new BufferedImage[numFrames[2]];
			BufferedImage[] fall_bufIm = new BufferedImage[numFrames[3]];
			BufferedImage[] glide_bufIm = new BufferedImage[numFrames[4]];
			BufferedImage[] fire_bufIm = new BufferedImage[numFrames[5]];
			BufferedImage[] scratch_bufIm = new BufferedImage[numFrames[6]];
			
			for(int i = 0; i < 7; i++) {
				for(int j = 0; j < numFrames[i]; j++) {
				
					switch(i) {
						case 0:
							idle_bufIm[j] = spritesheet.getSubimage(j*width, i*height, width, height);
							break;
						case 1:
							if(j == 0) {walk_bufIm[j] = spritesheet.getSubimage(j*width, 2*height, width, height);}
							if(j == 1) {walk_bufIm[j] = spritesheet.getSubimage(j*width, 2*height, width, height);}
							if(j == 2) {walk_bufIm[j] = spritesheet.getSubimage(j*width, 2*height, width, height);}
							if(j == 3) {walk_bufIm[j] = spritesheet.getSubimage(j*width, 2*height, width, height);}
							if(j == 4) {walk_bufIm[j] = spritesheet.getSubimage(j*width, 2*height, width, height);}
							if(j == 5) {walk_bufIm[j] = spritesheet.getSubimage(j*width, 2*height, width, height);}
							break;
						case 2:
							jump_bufIm[j] = spritesheet.getSubimage(3*width, 0*height, width, height);
							break;
						case 3:
							fall_bufIm[j] = spritesheet.getSubimage(4*width, 0*height, width, height);
							break;
						case 4:
							glide_bufIm[j] = spritesheet.getSubimage(5*width, 0*height, width, height);
							break;
						case 5:
							if(j == 0) {fire_bufIm[j] = spritesheet.getSubimage(3*width, 0*height, width, height);}
							if(j == 1) {fire_bufIm[j] = spritesheet.getSubimage(4*width, 0*height, width, height);}
							break;
						case 6:
							if(j == 0) {scratch_bufIm[j] = spritesheet.getSubimage(0, 3*height, width, height);}
							if(j == 1) {scratch_bufIm[j] = spritesheet.getSubimage(j*width, 3*height, width, height);}
							if(j == 2) {scratch_bufIm[j] = spritesheet.getSubimage(j*width, 3*height, width, height);}
							if(j == 3) {scratch_bufIm[j] = spritesheet.getSubimage(j*width, 3*height, width, height);}
							if(j == 4) {scratch_bufIm[j] = spritesheet.getSubimage(j*width, 3*height, width, height);}
							if(j == 5) {scratch_bufIm[j] = spritesheet.getSubimage(j*width, 3*height, width, height);}
							break;
					}
				
				}
			}
			playerSprites.add(idle_bufIm);
			playerSprites.add(walk_bufIm);
			playerSprites.add(jump_bufIm);
			playerSprites.add(fall_bufIm);
			playerSprites.add(glide_bufIm);
			playerSprites.add(fire_bufIm);
			playerSprites.add(scratch_bufIm);

            BufferedImage spritesheetBoss = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/bossCharlesEndGame.png"));
            bossSprite = new BufferedImage[2];
            bossSprite[0] = spritesheetBoss.getSubimage(0, 0, 90, 90);
            bossSprite[1] = spritesheetBoss.getSubimage(90, 0, 90, 90);


            endScreenBackground = ImageIO.read(new File("Resources/Backgrounds/sunrise.png"));
			
        }catch(IOException e)
        {
            e.printStackTrace();
        }

        //END OF GETTING PLAYER SPRITES FOR THIS SCREEN

        xCoord = 0;
        yCoord = 100;
        walkCounter = 0;

        sprite = playerSprites.get(1); //We put the first row of player sprites into this variable.


      //Make Some Noise
      bgMusic = new AudioPlayer("/Music/EndMusic.mp3");
      bgMusic.play();
    }

    @Override
    public void init() {


    }

    @Override
    public void update() {

    }

    public void draw(java.awt.Graphics2D g)
    {


        g.drawImage(endScreenBackground, 0, 0, null);



        //draw title in yellow
        g.setColor( Color.YELLOW);
        g.setFont(new Font("Century Gothic", Font.BOLD, 18));
        g.drawString("YOU WIN!", 120, 40);


        //draw "Thanks for Playing"
        g.setColor( Color.YELLOW);
        g.setFont(new Font("Thanks", Font.ITALIC, 24));
        g.drawString("Thanks For Playing!!!", 45, 180);


        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("To Play Again, Press ENTER", 115, 200);




        if(xCoord < 180)
        {


            //Every other loop around, we're going to change the sprite so it looks like the character is walking.
            if(walkCounter % 2 == 0) {
                g.drawImage(sprite[1], xCoord, yCoord, null);

                //Sleep is just used to make the walking animation look better
                try {
                    Thread.sleep(50);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }

                g.drawImage(sprite[2], xCoord, yCoord, null);
                g.drawImage(bossSprite[0], 70, 60, null);


            }
            else {

                g.drawImage(sprite[3], xCoord, yCoord, null);
                //Sleep is just used to make walking animation look better.
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                g.drawImage(sprite[4], xCoord, yCoord, null);
                g.drawImage(bossSprite[0], 70, 60, null);

            }


            //Change the xCoordinate of the sprite by 5 pixels every loop
            xCoord = xCoord + 5;
        }
        else
        {

            //Once the character has walked to the middle of the screen, he just dances in place.
            if(walkCounter % 2 == 0)
            {

                sprite = playerSprites.get(3);
                g.drawImage(sprite[0], xCoord, yCoord, null);
                g.drawImage(bossSprite[1], 70, 60, null);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                sprite = playerSprites.get(0);
                g.drawImage(sprite[0], xCoord, yCoord, null);
                g.drawImage(bossSprite[0], 70, 60, null);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    walkCounter = walkCounter + 1;

       // endGame.repaint();

    }

    @Override
    public void keyPressed(int k) {

        if(k == KeyEvent.VK_ENTER) {

        	bgMusic.stop();
            gsm.setState(GameStateManager.stateMenu); //Pressing Enter takes up back to the MENU state.

        }
    }

    @Override
    public void keyReleased(int k) {

    }


}
