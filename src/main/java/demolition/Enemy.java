package demolition;

import processing.core.PImage;

import java.util.ArrayList;

public abstract class Enemy extends Man {

    /**
     * Second between the enemy moves from the current position to the next
     */
    public static final double SECONDS_BETWEEN_MOVING = 1;

    /**
     * Helpe change the enemy's positon,
     * work with SECONDS_BETWEEN_MOVING
     */
    protected int movementCounter;

    /**
     * Used to decide the type of the enemy (red or yellow) when loading
     */
    protected String loadString;

    /**
     * The player the user controls
     */
    protected Player player;

    /**
     * Enemy constructor
     * default lives for enemy: 1
     * default movementCounter: 1
     * load the enemy
     * @param x, coordinate x of the enemy
     * @param y, coordinate y of the enemy
     * @param spriteList, all sprites the enemy needs
     * @param map, map object to interact with
     * @param player, player object to interact wth
     */
    public Enemy(int x, int y, ArrayList<PImage> spriteList, Map map, Player player) {
        super(x, y, spriteList, map);

        this.lives = 1;

        this.movementCounter = 0;

        this.player = player;

        loadEnemy();
    }

    /**
     * Use to decide the type fo the enemy to load
     * @param loadString
     */
    protected void setLoadString(String loadString){
        this.loadString = loadString;
    }

    /** 
     * Load enemy based on the map list
     * which reads the map from text files
     */
    protected void loadEnemy() {
        for (int i = 0; i < this.map.getMap().size(); i++) {
            for (int j = 0; j < this.map.getMap().get(i).size(); j++) {
                if (this.map.getMap().get(i).get(j).equals(this.loadString)) {
                    setX(j * IMAGE_SIZE);
                    setY(i * IMAGE_SIZE + IMAGE_SIZE * 3 / 2);
                    this.map.getMap().get(i).set(j, " "); // set the position taken to empty
                    return;
                }
            }
        }
    }

    /**
     * Control interval between each sprite
     * Try kill player
     * Make the enemy move once every second
     */
    public void tick() {
        this.movementCounter++;
        if (this.movementCounter > SECONDS_BETWEEN_MOVING * App.FPS) {
            this.movementCounter = 0;
        }
        this.timer++;
        if (this.timer > SECONDS_BETWEEN_MOVES * App.FPS) {
            this.i++;
            this.timer = 0;
        }
        if (this.i > 3) {
            this.i = 0;
        }

        tickHelper();

        tryKillPlayer();

        if (this.movementCounter == 1) {
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
     * Child class like redEnemy and yellowEnemy must implement this,
     * which decides the algorithm they follow when blocked by a wall
     */
    protected abstract void tickHelper();

    /**
     * Kill the player if it's in the same position as the enemy
     */
    private void tryKillPlayer() {
        if (this.x == this.player.getX() && this.y == this.player.getY()) {
            this.player.decreaseLives();
        }
    }
}
