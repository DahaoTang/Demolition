package demolition;

import processing.core.PImage;

import java.util.ArrayList;

public class yellowEnemy extends Enemy {

    /**
     * redEnemy Constructor
     * @param x, coordinate x of the yellow enemy
     * @param y, coordinate y of the yellow enemy
     * @param spriteList. list storing all sprites needed by the yellow enemy when facing different directions
     * @param map, map object to interact with
     * @param player, player object to interact with
     */
    public yellowEnemy(int x, int y, ArrayList<PImage> spriteList, Map map, Player player) {
        super(x, y, spriteList, map, player);
    }

    /**
     * The algorithm to apply when the yellow enemy is block by a wall in front of it
     * It should move clock wise util it's movable again
     */
    public void tickHelper() {

        // Check movable every second
        if (this.movementCounter == 1) {
            changeMovable(this.direction);
        }
        
        // Turn clock wise when not movable
        if (!getMovable()) {
            while (true) {
                if (this.direction == Direction.RIGHT) {
                    this.direction = Direction.DOWN;
                } else if (this.direction == Direction.LEFT) {
                    this.direction = Direction.UP;
                } else if (this.direction == Direction.DOWN) {
                    this.direction = Direction.LEFT;
                } else if (this.direction == Direction.UP) {
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
