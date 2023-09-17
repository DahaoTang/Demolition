package demolition;

import processing.core.PImage;

import java.util.ArrayList;

public class Player extends Man {

    /**
     * Player constructor
     * Get the lives of the player based on the map parameter
     * @param x, coordinate x of the player
     * @param y, coordinate y of the player
     * @param spriteList, list storing all sprites needed by the player when faceing different directions
     * @param map, map object to interact with
     */
    public Player(int x, int y, ArrayList<PImage> spriteList, Map map) {
        super(x, y, spriteList, map);

        this.lives = this.map.getLives();
    }

    /**
     * Control interval between each sprite
     * If the player enters the next game, reset its position based on the new map
     */
    public void tick() {
        // Reset position when entering next game
        if (this.gameLevel != this.map.getGameLevel()) {
            this.resetPosition();
            this.gameLevel = this.map.getGameLevel();
        }

        this.checkReachGoal();

        this.timer++;
        if (this.timer > SECONDS_BETWEEN_MOVES * App.FPS) {
            this.i++;
            this.timer = 0;
        }
        if (this.i > 3) {
            this.i = 0;
        }

        // Change position if moved
        if (moved) {
            if (this.direction == Direction.RIGHT) {
                this.x += IMAGE_SIZE;
            } else if (this.direction == Direction.LEFT){
                this.x -= IMAGE_SIZE;
            } else if (this.direction == Direction.DOWN) {
                this.y += IMAGE_SIZE;
            } else if (this.direction == Direction.UP) {
                this.y -= IMAGE_SIZE;
            }
            changeMoved();
        }
    }

    /**
     * Set the player to its starting point of the current game
     */
    public void resetPosition() {
        this.x = this.map.getPlayerX();
        this.y = this.map.getPlayerY();
    }
    
    /**
     * Decrease the lives of the player by one
     * and set it to its starting point of the current game
     */
    public void decreaseLives() {
        this.resetPosition();
        this.lives--;
    }

    /**
     * Check if the player has reached the goal of the current game
     */
    private void checkReachGoal() {
        int map_i = (this.y + IMAGE_SIZE / 2) / IMAGE_SIZE - 2;
        int map_j = this.x / IMAGE_SIZE;
        if (this.map.getMap().get(map_i).get(map_j).equals("G")) {
            this.map.changeReachGoal();
        }
    }

}
