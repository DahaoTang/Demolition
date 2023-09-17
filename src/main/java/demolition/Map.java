package demolition;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {

    /**
     * Size of the each image,
     * both height and width
     */
    public static final int IMAGE_SIZE = 32;
    /**
     * Second between the enemy moves from the current position to the next
     */
    public static final double TIME_INTERVAL = 1;

    private int timer;

    private JSONObject json;

    private JSONArray levels; // for looping through all levels
    private JSONObject level; // current level
    private int lives; // lives the player has

    private File mapFile; // map file for one game level
    private ArrayList<String> mapPaths; // paths of all map txt files
    private ArrayList<ArrayList<String>> map; // map for one game level
    private ArrayList<Integer> times; // list of time for all game levels

    private int time; // time counter within one game level

    private int gameLevel; // current game level
    private int oldGameLevel; // for detecting whether to enter next level
    private boolean reachGoal;
    private boolean win;

    private PImage solidWall;
    private PImage brokenWall;
    private PImage empty;
    private PImage goal;

    /**
     * Map costructor
     * @param json, for reading form the config.json file
     * @param solidWall, sprite for the solid wall
     * @param brokenWall, sprite for the broken wall
     * @param empty, sprite for the empty block
     * @param goal, sprite for the goal block
     */
    public Map(JSONObject json, PImage solidWall, PImage brokenWall, PImage empty, PImage goal) {

        this.json = json;
        this.levels = json.getJSONArray("levels");
        this.lives = this.json.getInt("lives");

        this.mapPaths = new ArrayList<String>();
        this.map = new ArrayList<ArrayList<String>>();
        this.times = new ArrayList<Integer>();

        this.timer = 0;

        gameLevel = 0;
        oldGameLevel = 0;
        reachGoal = false;
        win = false;

        this.solidWall = solidWall;
        this.brokenWall = brokenWall;
        this.empty = empty;
        this.goal = goal;

        // Loop through all game levels
        for (int i = 0; i < levels.size(); i++) {
            this.level = this.levels.getJSONObject(i);
            this.mapPaths.add(this.level.getString("path"));
            this.times.add(this.level.getInt("time"));
        }
    }

    /**
     * Load the map when entering a new game including the first game
     * @throws IOException, read from a text file which contains the map of the game of the current level
     */
    public void load() throws IOException {
        this.time = this.times.get(this.gameLevel);
        this.mapFile = new File(this.mapPaths.get(gameLevel));

        // Load map for the current game level
        Scanner sc = new Scanner(this.mapFile);
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] dataList = data.split("");
            this.map.add(new ArrayList<String>(Arrays.asList(dataList)));
        }
        sc.close();
    }

    /**
     * Control interval between each sprite
     * Reset and reload the map when entering a new game
     * @throws IOException, read from a text file which contains the map of the game of the current level
     */
    public void tick() throws IOException {
        this.timer++;
        if (this.timer > TIME_INTERVAL* App.FPS) {
            this.time--;
            this.timer = 0;
        }

        if (reachGoal) {
            this.oldGameLevel = this.gameLevel;
            this.gameLevel++;
            
            // Win when finish the last game
            if (this.gameLevel == this.levels.size()) {
                this.win = true;
                return;
            }

            // Reset map when if reach the goal of the current game
            this.map = new ArrayList<ArrayList<String>>();
            this.load();
            this.changeReachGoal();
        }
    }

    /**
     * Draw all the sprite based on the condition in tick()
     * @param app, the map itself
     */
    public void draw(PApplet app){
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                // Draw solid wall
                if (this.map.get(i).get(j).equals("W")) {
                    app.image(this.solidWall, j * IMAGE_SIZE, i * IMAGE_SIZE + 2 * IMAGE_SIZE);
                // Draw broken wall
                } else if (this.map.get(i).get(j).equals("B")) {
                    app.image(this.brokenWall, j * IMAGE_SIZE, i * IMAGE_SIZE + 2 * IMAGE_SIZE);
                // Drwa goal 
                } else if (this.map.get(i).get(j).equals("G")) {
                    app.image(this.goal, j * IMAGE_SIZE, i * IMAGE_SIZE + 2 * IMAGE_SIZE);
                } else {
                    app.image(this.empty, j * IMAGE_SIZE, i * IMAGE_SIZE + 2 * IMAGE_SIZE);
                } 
            }
        }
    }

    /**
     * Check if the player wins
     * @return the current state of the private variable win
     */
    public boolean checkWin() {
        return this.win;
    }

    /**
     * Get the initialization coordinate x of the player based on the map of the current game
     * @return the initialization coordinate x of the player
     */
    public int getPlayerX() {
        for (int i =0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.get(i).size(); j++) {
                if (this.map.get(i).get(j).equals("P")) {
                    return j * IMAGE_SIZE;
                }
            }
        }
        return 32; // default x
    }

    /**
     * Get the initialization coordinate xy of the player based on the map of the current game
     * @return the initialization coordinate y of the player
     */
    public int getPlayerY() {
        for (int i =0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.get(i).size(); j++) {
                if (this.map.get(i).get(j).equals("P")) {
                    return i * IMAGE_SIZE + IMAGE_SIZE * 3 / 2;
                }
            }
        }
        return 80; // default y
    }

    /**
     * Get the number of the specific tyoe of enemy depending on the loadstring of the enemy
     * @param loadString, indicate the type of the enemy to get the number of ("R" for red enemy and "Y" for yellow enemy)
     * @return the number of the specific tyoe of enemy
     */
    public int getEnemyNumber(String loadString) {
        int num = 0;
        for (int i = 0; i < this.map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (this.map.get(i).get(j).equals(loadString)) {
                    num++;
                }
            }
        }
        return num;
    }

    /**
     * Check if the player has run out of time during the current game
     * @return true if have run out of time, false otherwise
     */
    public boolean outOfTime() {
        if (this.time < 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the boolean value of the variable reachGoal to its opposite
     */
    public void changeReachGoal() {
        if (this.reachGoal == false) {
            this.reachGoal = true;
        } else {
            this.reachGoal = false;
        }
    }

    /**
     * Check if game has changed, which indicates the player has entered the next level
     * if the player has, set the oldGameLevel same as the current game level
     * @return true or false
     */
    public boolean gameChanged() {
        if (this.oldGameLevel != this.gameLevel) {
            this.oldGameLevel = this.gameLevel;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the lives of the player
     * which is provided by the config.json file
     * @return the lives of the player
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Get the map of the current game in the current state
     * @return the map object 
     */
    public ArrayList<ArrayList<String>> getMap() {
        return this.map;
    }

    /**
     * Get the remaining time of the current game
     * @return the remaining time of the current game
     */
    public int getTime() {
        return this.time;
    }

    /**
     * Get the current game level
     * @return the current game level
     */
    public int getGameLevel() {
        return this.gameLevel;
    }
}
