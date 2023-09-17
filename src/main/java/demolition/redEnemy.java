package demolition;

import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

public class redEnemy extends Enemy {

    /**
     * redEnemy Constructor
     * @param x, coordinate x of the red enemy
     * @param y, coordinate y of the red enemy
     * @param spriteList. list storing all sprites needed by the red enemy when facing different directions
     * @param map, map object to interact with
     * @param player, player object to interact with
     */
    public redEnemy(int x, int y, ArrayList<PImage> spriteList, Map map, Player player) {
        super(x, y, spriteList, map, player);
    }

    /**
     * The algorithm to apply when the red enemy is block by a wall in front of it
     * It should turn to a rendom direction util it's movable again
     */
    public void tickHelper() {
        Random rand = new Random();

        // Check movable every second
        if (this.movementCounter == 1) {
            changeMovable(this.direction);
        }
        
        // Change to a random direction when not movable
        if (!getMovable()) {
            while (true) {
                if (rand.nextInt(4) == 0) {
                    this.direction = Direction.DOWN;
                } else if (rand.nextInt(4) == 1) {
                    this.direction = Direction.UP;
                } else if (rand.nextInt(4) == 2) {
                    this.direction = Direction.LEFT;
                } else if (rand.nextInt(4) == 3) {
                    this.direction = Direction.RIGHT;
                }

                changeMovable(this.direction);

                // Change direction until movable
                if (getMovable()) {
                    break;
                }
            }
        }
    }
}
