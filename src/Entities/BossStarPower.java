package Entities;

import TileMap.TileMap;

/**
 * Boss's Star Projectile!
 * This class is only used for the Boss object's projectiles.
 * This class extends the Star class, so it has all of the regular projectile methods.
 * Thus, objects of this class behave like normal projectiles, except instead of just moving straight,
 * these class objects follow the player's X and Y coordinates on the screen.
 * So, even if the boss is moving to the right, if the player is on the left, these projectiles
 * will move towards the left. These projectiles will also follow where the player is vertically on the screen,
 * but only a little bit, as the Y coordinates of objects of this class increase/decrease slowly.
 *
 */

public class BossStarPower extends Star {

    private boolean starHasLaunched;
    private String starDirectionX;


    public BossStarPower(TileMap level, boolean right) {
        super(level, right); //calls the constructor for the Star class

        starHasLaunched = false; //Used to set up the Boss star's movement.
        starDirectionX = ""; //Used to determine the Boss Star's movement.

    }

    /**
     * This class keeps constant track of where the BossStarPower object is on the screen and
     * changes the x and y coordinates of the BossStarPower object based on its current positioning.
     * @param playerXcoord - used to determine where the player is so the star can follow the player.
     * @param playerYCoord - used to determine where the player is sos the star can follow the player.
     */
    public void update(int playerXcoord, int playerYCoord) {

        //The boss shoots stars...but we do not want the stars to travel in a straight line like they
        //normally do in the Star class. So, we have to change the way stars are positioned. We are
        //only doing this for the boss class. We want the boss's stars to follow the player.

        // We only do this on the first update.
        if (!starHasLaunched) {
            checkTileMapCollision(); //The boss goes through one of the tiles,
                                    //probably because his Y Coordinate does not reflect how big his image is on the screen.
                                    //So, the tile the boss goes through is above his Y Coordinate, making his image
                                    //going through this tile not considered a collision. The star he shoots, however,
                                    //WILL collide with this tile. This will freeze the boss in place. So, we only check
                                    //collisions with tiles the regular way once.



            if (x > playerXcoord) {
                //If the player is on the left and the boss is moving right,
                //and we want the boss's star to follow the player, we set
                //the x coordinate of the star to - 3 pixels of what it should be for regular entities.
                x = x - 20;
                starDirectionX = "left";

            } else if (x < playerXcoord) {
                x = x + 20; //^^ same logic, but the directions are now swapped.
                starDirectionX = "right";
            }
        }

        starHasLaunched = true; //To make sure we only do the above code on the first update.


        //We only want the star to exactly follow the player's x coordinate once. After the first call to
        //this update() class, we have the star just travel in a straight line.
        //If the star follows the player throughout the entire time,
        //the game gets wayyy to difficult, and possibly unbeatable.
        if (starDirectionX.equals("left")) {
            x = x - 2;
        } else if (starDirectionX.equals("right")) {
            x = x + 2;
        }

        //star will follow the player's y coordinate if the player's y coordinate
        //is larger than the y-coordinate of where the star was initially shot.
        //In java, y-coordinate gets higher the lower on the screen you go.
        //So, when the player is above the star, the star will shoot diagonally upwards.
        if (y > playerYCoord) {
            y = y - 0.5;
        }
        else if (y < playerYCoord)  //Same logic, but vice-vera. Make star go down a little slower
        {                               //so it's not nearly impossible to defeat the boss.
            y = y + 0.3;
        }


        //Since we only call checkTileMapCollision once, we need a new way for the Boss's stars to disappear
        //once they've hit a tile. So, we have this class. It's pretty basic and only works for
        //the current level design
        checkWallCollision();

        setPosition(x, y); //set the (x,y) coordinates for the star.

        if (edgeX == 0 && !landed) {
            setLandedProjectile(); //Sets up the animation of the star disappearing as it collides into a Entity.
        }

        animation.update(); //change the particular Boss Star's image.

        if (landed && animation.hasPlayedOnce()) {
            //If the particular Boss's star has collided with an Entity, and star disappearing animation
            //cycle has ended, we remove the Boss Star from the Stars array list.
            delete = true;
        }
    }


    //Only for boss's stars. Only works because of the position of the walls
    //at the end of the map. Would have to change if the walls positions changed.
    public void checkWallCollision()
    {
        //This will only work with our current map layout.
        //It will not work if we begin changing the x positions of where the
        //walls for the boss are at.
        //If we don't have this collision detection, the boss's stars will be seen halfway through the map.
        if(x < 2950 )
        {
            setLandedProjectile(); //If we hit the end of the boss' area on the left,
                                //Set up the animation of the star disappearing.

        }
        else if(x > 3170)
        {
            setLandedProjectile();  //If we hit the end of the boss' area on the left,
            //Set up the animation of the star disappearing.

        }

    }
}
