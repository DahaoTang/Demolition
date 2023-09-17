package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;

import processing.data.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class App extends PApplet {

    /**
     * Width of the window of the app
     */
    public static final int WIDTH = 480;
    /**
     * Height of the window of the app
     */
    public static final int HEIGHT = 480;

    /**
     * Frame per second
     */
    public static final int FPS = 60;

    /**
     * Size of the each image,
     * both height and width
     */
    public static final int IMAGE_SIZE = 32;

    /**
     * Used for creating own font
     */
    private PFont myFont;

    /**
     * Used for read from a json configration file
     */
    private JSONObject json;
    private String jsonPath;

    /**
     * Store sprite for solid wall on the map
     */
    private PImage solidWall;
    /**
     * Store sprite for broken wall on the map
     */
    private PImage brokenWall;
    /**
     * Store sprite for empty block on the map
     */
    private PImage empty;
    /**
     * Store sprite for goal on the map
     */
    private PImage goal;

    /**
     * Store sprite for the clock icon on the map,
     * which will show the remaining time of the current game
     */
    private PImage clockImage;
    /**
     * Store sprite for player icon on the map,
     * which will show the lives the player has
     */
    private PImage playerIconImage;

    /**
     * Store sprites for bomb before explosion
     */
    private ArrayList<PImage> bombImages;
    /**
     * Store sprites for bomb explosion
     */
    private ArrayList<PImage> explosionImages;

    /**
     * Store sprites for the player
     */
    private ArrayList<PImage> playerImages;
    /**
     * Store sprites for the red enemy
     */
    private ArrayList<PImage> redEnemyImages;
    /**
     * Store sprites for the yellow
     */
    private ArrayList<PImage> yellowEnemyImages; 

    /**
     * The map object that will be used for
     * drawing static objects like walls, empty block etc
     */
    private Map map;

    /**
     * Draw clock icon in the window
     */
    private Icon clock;
    /** Draw player icon in the window */
    private Icon playerIcon;

    /**
     * Draw bomb on the map
     */
    private Bomb bomb;
    /**
     * Store all bombs the player sets
     */
    private ArrayList<Bomb> bombs;

    /**
     * Draw the player on the map 
     * and enable the user to control it
     */
    private Player player;
    /**
     * Store all enemies on one map within one game
     */
    private ArrayList<Enemy> enemies;

    /**
     * App constructor
     * Initailize:
     * json,
     * bombImages, explosionImages,
     * playerImages, enemy images,
     * bomb list and enemy list
     */
    public App() {
        this.json = new JSONObject();
        this.jsonPath = "config.json";

        this.bombImages = new ArrayList<PImage>();
        this.explosionImages = new ArrayList<PImage>();

        this.playerImages = new ArrayList<PImage>();
        this.redEnemyImages = new ArrayList<PImage>();
        this.yellowEnemyImages = new ArrayList<PImage>();

        this.bombs = new ArrayList<Bomb>();

        this.enemies = new ArrayList<Enemy>();
    }

    /**
     * Set the size of the window of the app
     * @return 
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Have all images loaded.
     * Create and use the customized font.
     * Set frame rate.
     * Create and load map for the current game.
     * Create the player;
     * Create the clock icon and the player icon.
     */
    public void setup() {
        // Set up font
        this.myFont = createFont("PressStart2P-Regular.ttf", IMAGE_SIZE / 2);
        textFont(this.myFont);
        fill(0, 0, 0); // font color

        // Set up frame rate
        frameRate(FPS);

        // Load json config file
        this.json = loadJSONObject(jsonPath);

        // Load map object images
        this.solidWall = this.loadImage("src/main/resources/wall/solid.png");
        this.brokenWall = this.loadImage("src/main/resources/broken/broken.png");
        this.empty = this.loadImage("src/main/resources/empty/empty.png");
        this.goal = this.loadImage("src/main/resources/goal/goal.png");

        // Init map
        this.map = new Map(this.json, this.solidWall, this.brokenWall, this.empty, this.goal);

        try {
            this.map.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Load player images
        playerImages.add(this.loadImage("src/main/resources/player/player1.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player2.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player3.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player4.png"));

        playerImages.add(this.loadImage("src/main/resources/player/player_up1.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_up2.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_up3.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_up4.png"));

        playerImages.add(this.loadImage("src/main/resources/player/player_left1.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_left2.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_left3.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_left4.png"));

        playerImages.add(this.loadImage("src/main/resources/player/player_right1.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_right2.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_right3.png"));
        playerImages.add(this.loadImage("src/main/resources/player/player_right4.png"));

        // Init player
        this.player = new Player(this.map.getPlayerX(), this.map.getPlayerY(), this.playerImages, this.map); // (32, 80) is the initial position for this.player

        // Load red enemy images
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_down1.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_down2.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_down3.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_down4.png"));

        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_up1.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_up2.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_up3.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_up4.png"));

        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_left1.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_left2.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_left3.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_left4.png"));

        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_right1.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_right2.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_right3.png"));
        redEnemyImages.add(this.loadImage("src/main/resources/red_enemy/red_right4.png"));

        // Load yellow enemy images
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_down1.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_down2.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_down3.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_down4.png"));

        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_up1.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_up2.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_up3.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_up4.png"));

        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_left1.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_left2.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_left3.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_left4.png"));

        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_right1.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_right2.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_right3.png"));
        yellowEnemyImages.add(this.loadImage("src/main/resources/yellow_enemy/yellow_right4.png"));

        // Init enemies
        appLoadEnemy();

        // Load bomb images
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb.png"));
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb1.png"));
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb2.png"));
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb3.png"));
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb4.png"));
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb5.png"));
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb6.png"));
        bombImages.add(this.loadImage("src/main/resources/bomb/bomb7.png"));

        // Load xxplosion images
        explosionImages.add(this.loadImage("src/main/resources/explosion/centre.png"));
        explosionImages.add(this.loadImage("src/main/resources/explosion/horizontal.png"));
        explosionImages.add(this.loadImage("src/main/resources/explosion/vertical.png"));
        explosionImages.add(this.loadImage("src/main/resources/explosion/end_bottom.png"));
        explosionImages.add(this.loadImage("src/main/resources/explosion/end_left.png"));
        explosionImages.add(this.loadImage("src/main/resources/explosion/end_right.png"));
        explosionImages.add(this.loadImage("src/main/resources/explosion/end_top.png"));

        // Load other icon images
        this.clockImage = this.loadImage("src/main/resources/icons/clock.png");
        this.playerIconImage = this.loadImage("src/main/resources/icons/player.png");

        // Init other icons
        this.clock = new Icon(IMAGE_SIZE * 8, IMAGE_SIZE / 2, this.clockImage);
        this.playerIcon = new Icon(IMAGE_SIZE * 4, IMAGE_SIZE / 2, this.playerIconImage);
    }

    /**
     * This method will be called every frame.
     * It resets the entire screen and draws the new screen based on the current state of all objects.
     * 
     * It sets the background to orange, then check the state of the game (playing, win or fail).
     * If win, show the winning page and game stops;
     * if fail, shoe the fail page and game stops;
     * if still playing, draw all elements on the map: the map, the enemies, the player, the bombs and the icons.
     */
    public void draw() {
        this.rect(-1, -1, WIDTH + 2, HEIGHT + 2); // reset the screen
        background(239, 129, 0); // background colour

        // If not win and not die
        if (!this.map.checkWin() && !this.map.outOfTime() && this.player.checkAlive()) {
            // ### DRAW MAP ###
            try {
                this.map.tick();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // Enter new game level, reset enemies
            if (this.map.gameChanged()) {
                this.enemies = new ArrayList<Enemy>();
                appLoadEnemy();
            }
            this.map.draw(this);
    
            // ### DRAW ENEMIES ###
            for (Enemy e: this.enemies) {
                if (e.checkAlive()) {
                    e.tick();
                    e.draw(this);
                }
            }
            
            // ### DRAW PLAYER ###
            this.player.tick();
            this.player.draw(this);
            
            // ### DRAW BOMB ###
            for (int i = 0; i < bombs.size(); i++) {
                if (!bombs.get(i).getFinished()){
                    bombs.get(i).tick();
                    bombs.get(i).draw(this);
                } 
            }
    
            // ### DRAW OTHER ICONS ###
            this.playerIcon.draw(this);
            this.clock.draw(this);
            // Text for other icons
            text(this.player.getLives(), IMAGE_SIZE * 5 + IMAGE_SIZE * 3 / 10, IMAGE_SIZE + IMAGE_SIZE * 3 / 10); // the position is based on good-looking only
            text(this.map.getTime(), IMAGE_SIZE * 9 + IMAGE_SIZE * 3 / 10, IMAGE_SIZE + IMAGE_SIZE * 3 / 10);

        // If win
        } else if (this.map.checkWin()) {
            text("YOU WIN", IMAGE_SIZE * 55 / 10, IMAGE_SIZE * 7);
        // If player dies or time runs out
        } else {
            text("GAME OVER", IMAGE_SIZE * 52 / 10, IMAGE_SIZE * 7);
        }
    }

    /**
     * The user will use the keyboard to control the player to move.
     * Use arrow keys to navigate and the space key to set a bomb at a time.
     */
    public void keyPressed() {
        // Left: 37
        // Up: 38
        // Right: 39
        // Down: 40
        // Space: 32
        if (this.keyCode == 37) {
            this.player.changeMovable(Direction.LEFT);
            if (this.player.getMovable()) {
                this.player.direction = Direction.LEFT;
                this.player.changeMoved();
            }
        } else if (this.keyCode == 38) {
            this.player.changeMovable(Direction.UP);
            if (this.player.getMovable()) {
                this.player.direction = Direction.UP;
                this.player.changeMoved();
            }
        } else if (this.keyCode == 39) {
            this.player.changeMovable(Direction.RIGHT);
            if (this.player.getMovable()) {
                this.player.direction = Direction.RIGHT;
                this.player.changeMoved();
            }
        } else if (this.keyCode == 40) {
            this.player.changeMovable(Direction.DOWN);
            if (this.player.getMovable()) {
                this.player.direction = Direction.DOWN;
                this.player.changeMoved();
            }
        } else if (this.keyCode == 32) {
            // Add new bomb to bomb list
            bomb = new Bomb(bombImages, explosionImages, this.player.getX(), this.player.getY(), this.map, this.player, this.enemies);
            bombs.add(bomb);
        }
    }

    /**
     * Helper method used to help load enemies in draw(), when the player enters a new level
     */
    private void appLoadEnemy() {
        // Get enemy number
        int redEnemyNumber = this.map.getEnemyNumber("R");
        int yellowEnemyNumber = this.map.getEnemyNumber("Y");
        
        // Add red enemies based on its number
        for (int i = 0; i < redEnemyNumber; i++) {
            this.enemies.add(new redEnemy(0, 0, redEnemyImages, this.map, this.player));
        }
        // Add yellow enemies bases on its number
        for (int i = 0; i < yellowEnemyNumber; i ++) {
            this.enemies.add(new yellowEnemy(0, 0, yellowEnemyImages, this.map, this.player));
        }

        // Load red enemy position
        for (int i = 0; i < redEnemyNumber; i++) {
            this.enemies.get(i).setLoadString("R");
            this.enemies.get(i).loadEnemy();
        }
        // Load yellow enemy position
        for (int i = redEnemyNumber; i < this.enemies.size(); i++) {
            this.enemies.get(i).setLoadString("Y");
            this.enemies.get(i).loadEnemy();
        }
    }

    /**
     * Get myFont
     * @return myFont
     */
    public PFont getMyFont() {
        return this.myFont;
    }

    /**
     * Get json
     * @return json
     */
    public JSONObject getJson() {
        return this.json;
    }

    /**
     * Get solid wall sprite
     * @return solidWall
     */
    public PImage getSolidWall() {
        return this.solidWall;
    }

    /**
     * Get broken wall sprite
     * @return brokenWall
     */
    public PImage getBrokenWall() {
        return this.brokenWall;
    }

    /**
     * Get empty bolck sprite
     * @return empty
     */
    public PImage getEmpty() {
        return this.empty;
    }

    /**
     * Get goal sprite
     * @return goal
     */
    public PImage getGoal() {
        return this.goal;
    }

    /**
     * Get clock icon sprite
     * @return clockImage
     */
    public PImage getClockImage() {
        return this.clockImage;
    }

    /**
     * Get player icon sprite
     * @return playerIconSprite
     */
    public PImage getPlayerIconImage() {
        return this.playerIconImage;
    }

    /**
     * Get Bomb object bomb
     * @return bomb
     */
    public Bomb getBomb() {
        return this.bomb;
    }

    /**
     * Get bomb list
     * @return bombs
     */
    public ArrayList<Bomb> getBombs() {
        return this.bombs;
    }

    /**
     * Get Player object player
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get enemy list
     * @return enemies
     */
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    /**
     * Set config file for testing
     * @param string config.json path
     */
    public void setConfig(String string) {
        this.jsonPath = string;
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
