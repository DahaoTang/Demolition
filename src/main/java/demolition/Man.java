package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public abstract class Man {

     /**
     * Width of the window of the app
     */
    public static final int WIDTH = 480;
    /**
     * Height of the window of the app
     */
    public static final int HEIGHT = 480;

    /**
     * Size of the each image,
     * both height and width
     */
    public static final int IMAGE_SIZE = 32;

    /**
     * Second between each sprite
     */
    public static final double SECONDS_BETWEEN_MOVES = 0.2;

    protected int i; // loop through image list
    protected int timer; // count during interval between sprites

    /**
     * Man coordinate: x
     */
    protected int x;
    /**
     * Man coordinate: y
     */
    protected int y;

    /**
     * Check if the man is movable,
     * true when no wall in front of it,
     * false otherwise
     */
    protected boolean movable;
    /**
     * Check if the man is moved,
     * true if it is,
     * false if not
     */
    protected boolean moved;

    /**
     * The current direction of the man
     */
    protected Direction direction;
    
    /** 
     * Current / remaining lives of the man
     */
    protected int lives;
    /**
     * Current game level
     */
    protected int gameLevel;

    /**
     * Store all sprites need for the man
     */
    protected ArrayList<PImage> spriteList;
    /**
     * Store all sprites need for the man
     * when the current direction is down
     */
    protected ArrayList<PImage> spriteList_DOWN;
    /**
     * Store all sprites need for the man
     * when the current direction is up
     */
    protected ArrayList<PImage> spriteList_UP;
    /**
     * Store all sprites need for the man
     * when the current direction is left
     */
    protected ArrayList<PImage> spriteList_LEFT;
    /**
     * Store all sprites need for the man
     * when the current direction is right
     */
    protected ArrayList<PImage> spriteList_RIGHT;

    /**
     * The map of the current game
     */
    protected Map map;

    /**
     * Man constructor
     * default i: 0
     * default timer:0
     * default movable: true
     * default moved: false
     * default direction: down
     * default game level: 0
     * @param x, coordinate x of the man
     * @param y, coordinate y of the man
     * @param spriteList, sprite list storing all the sprites needed by the man facing different directions
     * @param map, map object to interact with
     */
    public Man(int x, int y, ArrayList<PImage> spriteList, Map map) {

        this.i = 0;
        this.timer = 0;

        this.x = x;
        this.y = y;

        this.movable = true;
        this.moved = false;

        this.direction = Direction.DOWN;

        this.gameLevel = 0;

        // Load sprites in different directions
        this.spriteList = spriteList;

        this.spriteList_DOWN = new ArrayList<PImage>();
        this.spriteList_DOWN.add(spriteList.get(0));
        this.spriteList_DOWN.add(spriteList.get(1));
        this.spriteList_DOWN.add(spriteList.get(2));
        this.spriteList_DOWN.add(spriteList.get(3));

        this.spriteList_UP = new ArrayList<PImage>();
        this.spriteList_UP.add(spriteList.get(4));
        this.spriteList_UP.add(spriteList.get(5));
        this.spriteList_UP.add(spriteList.get(6));
        this.spriteList_UP.add(spriteList.get(7));

        this.spriteList_LEFT = new ArrayList<PImage>();
        this.spriteList_LEFT.add(spriteList.get(8));
        this.spriteList_LEFT.add(spriteList.get(9));
        this.spriteList_LEFT.add(spriteList.get(10));
        this.spriteList_LEFT.add(spriteList.get(11));

        this.spriteList_RIGHT = new ArrayList<PImage>();
        this.spriteList_RIGHT.add(spriteList.get(12));
        this.spriteList_RIGHT.add(spriteList.get(13));
        this.spriteList_RIGHT.add(spriteList.get(14));
        this.spriteList_RIGHT.add(spriteList.get(15));

        this.map = map;
    }

    /**
     * Control interval between each sprite
     */
    public abstract void tick(); // {

    /**
     * Draw all the sprite based on the condition in tick()
     * @param app, the man itself
     */
    public void draw(PApplet app) {
        if (this.direction == Direction.DOWN) {
            app.image(spriteList_DOWN.get(this.i), this.x, this.y);
        } else if (this.direction == Direction.UP) {
            app.image(spriteList_UP.get(this.i), this.x, this.y);
        } else if (this.direction == Direction.LEFT) {
            app.image(spriteList_LEFT.get(this.i), this.x, this.y);
        } else if (this.direction == Direction.RIGHT) {
            app.image(spriteList_RIGHT.get(this.i), this.x, this.y);
        }
    }

    /**
     * Change the boolean value of movable to its opposite
     */
    public void changeMoved() {
        this.moved = !this.moved;
    }

    /**
     * Set a new value to x, the current coordinate x of the man
     * @param x, coordinate x of the man
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set a new value to y, the current coordinate y of the man
     * @param y, coordinate y of the man 
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Change movable state based on the position and the next direction of the man,
     * if the next block in the direction of the man's next direction is a wall, then set false to movable,
     * otherwise set true to movable
     * @param nextDirection, the direction the man moves towards
     */
    public void changeMovable(Direction nextDirection) {

        int map_i = (this.y + IMAGE_SIZE / 2) / IMAGE_SIZE - 2;
        int map_j = this.x / IMAGE_SIZE;

        if (nextDirection == Direction.DOWN) {
            if (this.map.getMap().get(map_i + 1).get(map_j).equals("W") || this.map.getMap().get(map_i + 1).get(map_j).equals("B")) {
                this.movable = false;
                return;
            } 
        } else if (nextDirection == Direction.UP) {
            if (this.map.getMap().get(map_i - 1).get(map_j).equals("W") || this.map.getMap().get(map_i - 1).get(map_j).equals("B")) {
                this.movable = false;
                return;
            }
        } else if (nextDirection == Direction.LEFT) {
            if (this.map.getMap().get(map_i).get(map_j - 1).equals("W") || this.map.getMap().get(map_i).get(map_j - 1).equals("B")) {
                this.movable = false;
                return;
            }
        } else if (nextDirection == Direction.RIGHT) {
            if (this.map.getMap().get(map_i).get(map_j + 1).equals("W") || this.map.getMap().get(map_i).get(map_j + 1).equals("B")) {
                this.movable = false;
                return;
            }
        }
        this.movable = true;
        return;
    }

    /**
     * Check if the man is still alive,
     * if it has at least one live, return true,
     * otherwise return false
     * @return true or false
     */
    public boolean checkAlive() {
        if (this.lives > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Decrease the lives of the man by one
     */
    public void decreaseLives() {
        this.lives--;
    }

    /**
     * Get current coordinate x of the man
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get current coordinate y of the man
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get the movable state of the man in the current position
     * @return movable or not movable
     */
    public boolean getMovable() {
        return this.movable;
    }

    /**
     * Get the remaining live(s) of the man
     * @return the live(s) of the man
     */
    public int getLives() {
        return this.lives;
    }
}