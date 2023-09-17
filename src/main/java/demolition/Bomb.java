package demolition;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Bomb {

    /**
     * Size of the each image,
     * both height and width
     */
    public static final int IMAGE_SIZE = 32;
    /**
     * Second between each sprite when the bomb is still loading
     */
    public static final double SECONDS_BETWEEN_BOMBS = 0.25;
    /**
     * Second between each sprite when the bomb is loading
     */
    public static final double SECONDS_BETWEEN_EXPLOSIONS = 0.5;

    private int i; // loop through image list
    private int timer; // count during interval between sprite

    // Center coordinates
    private int x;
    private int y;
    
    private boolean loading; // loading
    private boolean finished; // explosion finishes

    private boolean brokenWallNextToCenter_DOWN;
    private boolean brokenWallNextToCenter_UP;
    private boolean brokenWallNextToCenter_LEFT;
    private boolean brokenWallNextToCenter_RIGHT;

    private ArrayList<PImage> bombSpriteList;
    private ArrayList<PImage> explosionSpriteList;

    private Map map;
    private Player player;
    private ArrayList<Enemy> enemies;

    /**
     * Bomb constructor
     * default loading state: true
     * default finished state: false
     * default brokenWallNextToCenter: false
     * @param bombSpriteList, sprites list for bomb when loading
     * @param explosionSpriteList, sprites list for bomb during explosion
     * @param x, coordinate x of bomb when loading
     * @param y, coordinate y of bomb when loading
     * @param map, map object to interact with
     * @param player, player object to interact with
     * @param enemies, enemy list storing all enemies
     */
    public Bomb(ArrayList<PImage> bombSpriteList, ArrayList<PImage> explosionSpriteList, int x, int y, Map map, Player player, ArrayList<Enemy> enemies) {
        
        this.i = 0;
        this.timer = 0;

        this.x = x;
        this.y = y + IMAGE_SIZE / 2;

        this.loading = true;
        this.finished = false;

        this.brokenWallNextToCenter_DOWN = false;
        this.brokenWallNextToCenter_UP = false;
        this.brokenWallNextToCenter_LEFT = false;
        this.brokenWallNextToCenter_RIGHT = false;
        
        this.bombSpriteList = bombSpriteList;
        this.explosionSpriteList = explosionSpriteList;

        this.map = map;
        this.player = player;
        this.enemies = enemies;
    }

    /**
     * Control interval between each sprite
     * and check and if the bomb is loading
     * and decide when and for how long the bomb should keep exploding
     */
    public void tick() {
        this.timer++;
        if (loading) {
            if (this.timer > SECONDS_BETWEEN_BOMBS * App.FPS) {
                this.timer = 0;
                this.i++;
                if (i > 7) {
                    this.i = 0;
                    this.loading = false;
                }
            }
        } else {
            if (this.timer > SECONDS_BETWEEN_EXPLOSIONS * App.FPS) {
                this.timer = 0;
                this.finished = true;
            }
        }
    }

    /**
     * Draw all the sprite based on the condition in tick()
     * Check if the enemies or player are captured by the explosion, 
     * if so, the player decreases its lives by one and resets to the starting point,
     * the enemy will die if captured
     * @param app, the bomb itself
     */
    public void draw(PApplet app) {

        // Bomb center coordinates in map list
        int map_i = (this.y + IMAGE_SIZE / 2) / IMAGE_SIZE - 2;
        int map_j = this.x / IMAGE_SIZE;

        // Bomb loading
        if (loading) {
            app.image(bombSpriteList.get(this.i), this.x, this.y);
            
            // Check wall next to center DOWN
            if (this.map.getMap().get(map_i + 1).get(map_j).equals("B")) {
                this.brokenWallNextToCenter_DOWN = true;
            }
            // Check wall next to center UP
            if (this.map.getMap().get(map_i - 1).get(map_j).equals("B")) {
                this.brokenWallNextToCenter_UP = true;
            }
            // Check wall next to center LEFT
            if (this.map.getMap().get(map_i).get(map_j - 1).equals("B")) {
                this.brokenWallNextToCenter_LEFT = true;
            }
            // Check wall next to center RIGHT
            if (this.map.getMap().get(map_i).get(map_j + 1).equals("B")) {
                this.brokenWallNextToCenter_RIGHT = true;
            }

        // Explosion starts
        } else {

            // ### CENTER ###
            app.image(explosionSpriteList.get(0), this.x, this.y);
            // Try kill enemy
            for (Enemy e: this.enemies) {
                if (e.getX() == this.x && e.getY() +IMAGE_SIZE / 2 == this.y) {
                    e.decreaseLives();
                    // System.out.println("bomb center");
                }
            }
            // Try kill player
            if (this.finished) {
                if (this.player.getX() == this.x && this.player.getY() + IMAGE_SIZE / 2 == this.y) {
                    this.player.decreaseLives();
                    this.player.resetPosition();
                    // System.out.println("bomb center");
                }
            }
            
            // ### DOWN ###
            if (!this.map.getMap().get(map_i + 1).get(map_j).equals("W")) {
                // DONE ONE
                app.image(explosionSpriteList.get(2), this.x, this.y + IMAGE_SIZE);
                // Try kill enemy
                for (Enemy e: this.enemies) {
                    if (e.getX() == this.x && e.getY() +IMAGE_SIZE / 2 == this.y + IMAGE_SIZE) {
                        e.decreaseLives();
                        // System.out.println("bomb down 1");
                    }
                }
                // Try kill player
                if (this.player.getX() == this.x && this.player.getY() + IMAGE_SIZE / 2 == this.y + IMAGE_SIZE) {
                    this.player.decreaseLives();
                    this.player.resetPosition();
                    // System.out.println("bomb down 1");
                }
                // Reset map list
                if (!this.map.getMap().get(map_i + 1).get(map_j).equals("G")) {
                    this.map.getMap().get(map_i + 1).set(map_j, " "); // empty down one
                }

                // DOWN TWO
                if (!this.brokenWallNextToCenter_DOWN) {
                    if (!this.map.getMap().get(map_i + 2).get(map_j).equals("W")) {
                        app.image(explosionSpriteList.get(3), this.x, this.y + IMAGE_SIZE * 2);
                        // Try kill enemy
                        for (Enemy e: this.enemies) {
                            if (e.getX() == this.x && e.getY() +IMAGE_SIZE / 2 == this.y + IMAGE_SIZE * 2) {
                                e.decreaseLives();
                                // System.out.println("bomb down 2");
                            }
                        }
                        // Try kill Player
                        if (this.player.getX() == this.x && this.player.getY() + IMAGE_SIZE / 2 == this.y + IMAGE_SIZE * 2) {
                            this.player.decreaseLives();
                            this.player.resetPosition();
                            // System.out.println("bomb down 2");
                        } 
                        // Reset map list
                        if (!this.map.getMap().get(map_i + 2).get(map_j).equals("G")) {
                            this.map.getMap().get(map_i + 2).set(map_j, " "); // empty down two
                        }
                    }
                }
            }

            // ### UP ###
            if (!this.map.getMap().get(map_i - 1).get(map_j).equals("W")) {
                // UP ONE
                app.image(explosionSpriteList.get(2), this.x, this.y - IMAGE_SIZE);
                // Try kill enemy
                for (Enemy e: this.enemies) {
                    if (e.getX() == this.x && e.getY() +IMAGE_SIZE / 2 == this.y - IMAGE_SIZE) {
                        e.decreaseLives();
                        // System.out.println("bomb up 1");
                    }
                }
                // Try kill player
                if (this.player.getX() == this.x && this.player.getY() + IMAGE_SIZE / 2 == this.y - IMAGE_SIZE) {
                    this.player.decreaseLives();
                    this.player.resetPosition();
                    // System.out.println("bomb up 1");
                }
                // Reset map list 
                if (!this.map.getMap().get(map_i - 1).get(map_j).equals("G")) {
                    this.map.getMap().get(map_i - 1).set(map_j, " "); // empty up one
                }

                // UP TWO
                if (!this.brokenWallNextToCenter_UP) {
                    if (!this.map.getMap().get(map_i - 2).get(map_j).equals("W")) {
                        app.image(explosionSpriteList.get(6), this.x, this.y - IMAGE_SIZE * 2);
                        // Try kill enemy
                        for (Enemy e: this.enemies) {
                            if (e.getX() == this.x && e.getY() +IMAGE_SIZE / 2 == this.y - IMAGE_SIZE * 2) {
                                e.decreaseLives();
                                // System.out.println("bomb up 2");
                            }
                        }
                        // Try kill player
                        if (this.player.getX() == this.x && this.player.getY() + IMAGE_SIZE / 2 == this.y - IMAGE_SIZE * 2) {
                            this.player.decreaseLives();
                            this.player.resetPosition();
                            // System.out.println("bomb up 2");
                        }
                        // Reset map list
                        if (!this.map.getMap().get(map_i - 2).get(map_j).equals("G")) {
                            this.map.getMap().get(map_i - 2).set(map_j, " "); // empty top two
                        }
                    }
                }
            }

            // ### LEFT ###
            if (!this.map.getMap().get(map_i).get(map_j - 1).equals("W")) {
                // LEFT ONE
                app.image(explosionSpriteList.get(1), this.x - IMAGE_SIZE, this.y);
                // Try kill enemy
                for (Enemy e: this.enemies) {
                    if (e.getX() == this.x - IMAGE_SIZE && e.getY() +IMAGE_SIZE / 2 == this.y) {
                        e.decreaseLives();
                        // System.out.println("bomb left 1");
                    }
                }
                // Try kill player
                if (this.player.getX() == this.x - IMAGE_SIZE && this.player.getY() + IMAGE_SIZE / 2 == this.y) {
                    this.player.decreaseLives();
                    this.player.resetPosition();
                    // System.out.println("bomb left 1");
                }
                // Reset map list
                if (!this.map.getMap().get(map_i).get(map_j - 1).equals("G")) {
                    this.map.getMap().get(map_i).set(map_j - 1, " "); // empty left one
                }

                // LEFT TWO
                if (!this.brokenWallNextToCenter_LEFT) {
                    if (!this.map.getMap().get(map_i).get(map_j - 2).equals("W")) {
                        app.image(explosionSpriteList.get(4), this.x - IMAGE_SIZE * 2, this.y);
                        // Try kill enemy
                        for (Enemy e: this.enemies) {
                            if (e.getX() == this.x - IMAGE_SIZE * 2 && e.getY() +IMAGE_SIZE / 2 == this.y) {
                                e.decreaseLives();
                                // System.out.println("bomb left 2");
                            }
                        }
                        // Try kill player
                        if (this.player.getX() == this.x - IMAGE_SIZE * 2 && this.player.getY() + IMAGE_SIZE / 2 == this.y) {
                            this.player.decreaseLives();
                            this.player.resetPosition();
                            // System.out.println("bomb left 2");
                        }
                        // Reset map list
                        if (!this.map.getMap().get(map_i).get(map_j - 2).equals("G")) {
                            this.map.getMap().get(map_i).set(map_j - 2, " "); // empty left two
                        }
                    }
                }
            }

            // ### RIGHT ###
            if (!this.map.getMap().get(map_i).get(map_j + 1).equals("W")) {
                // RIGHT ONE
                app.image(explosionSpriteList.get(1), this.x + IMAGE_SIZE, this.y);
                // Try kill enemy
                for (Enemy e: this.enemies) {
                    if (e.getX() == this.x + IMAGE_SIZE && e.getY() +IMAGE_SIZE / 2 == this.y) {
                        e.decreaseLives();
                        // System.out.println("bomb right 1");
                    }
                }
                // Try kill player
                if (this.player.getX() == this.x + IMAGE_SIZE && this.player.getY() + IMAGE_SIZE / 2 == this.y) {
                    this.player.decreaseLives();
                    this.player.resetPosition();
                    // System.out.println("bomb right 1");
                }
                // Reset map list
                if (!this.map.getMap().get(map_i).get(map_j + 1).equals("G")) {
                    this.map.getMap().get(map_i).set(map_j + 1, " "); // empty right one
                }

                // RIGHT TWO
                if (!this.brokenWallNextToCenter_RIGHT) {
                    if (!this.map.getMap().get(map_i).get(map_j + 2).equals("W")) {
                        app.image(explosionSpriteList.get(5), this.x + IMAGE_SIZE * 2, this.y);
                        // Try kill enemy
                        for (Enemy e: this.enemies) {
                            if (e.getX() == this.x + IMAGE_SIZE * 2 && e.getY() +IMAGE_SIZE / 2 == this.y) {
                                e.decreaseLives();
                                // System.out.println("bomb right 2");
                            }
                        }
                        // Try kill player
                        if (this.player.getX() == this.x + IMAGE_SIZE * 2 && this.player.getY() + IMAGE_SIZE / 2 == this.y) {
                            this.player.decreaseLives();
                            this.player.resetPosition();
                            // System.out.println("bomb right 2");
                        }
                        // Reset map list 
                        if (!this.map.getMap().get(map_i).get(map_j + 2).equals("G")) {
                            this.map.getMap().get(map_i).set(map_j + 2, " "); // empty right two
                        }
                    }
                }
            }
        }
    
        // Reset when explosion finishes
        if (finished) {
            this.brokenWallNextToCenter_DOWN = false;
            this.brokenWallNextToCenter_UP = false;
            this.brokenWallNextToCenter_LEFT = false;
            this.brokenWallNextToCenter_RIGHT = false;
        }
    }

    /**
     * Check if the bomb is finished,
     * used for stop explosion images and effects
     * @return true or false
     */
    public boolean getFinished() {
        return this.finished;
    }
}
