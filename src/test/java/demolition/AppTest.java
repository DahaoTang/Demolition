package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import processing.core.PApplet;

public class AppTest {

    @Test
    public void setupTest() {
        // Create an instance of your application
        App app = new App();
        // Set the program to not loop automatically
        app.noLoop();
        // Set the path of the config file to use
        app.setConfig("src/test/resources/config.json");
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] {"App"}, app);
        // Call App.setup() to load in sprites
        // Set a 1 second delay to ensure all resources are loaded
        app.delay(1000);
        
        app.draw();

        assertNotNull(app.getBrokenWall());
        assertNotNull(app.getClockImage());
        assertNotNull(app.getEmpty());
        assertNotNull(app.getEnemies());
        assertNotNull(app.getGoal());
        assertNotNull(app.getJson());
        assertNotNull(app.getMyFont());
        assertNotNull(app.getPlayer());
        assertNotNull(app.getPlayerIconImage());
        assertNotNull(app.getSolidWall());

    }

    @Test
    public void PlayerMovementTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Test player movement
        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();
        assertNotNull(app.getPlayer());

        app.keyCode = 38; // up
        app.keyPressed();
        app.draw(); 
        assertNotNull(app.getPlayer());

        app.keyCode = 39; // right
        app.keyPressed();
        app.draw(); 
        assertNotNull(app.getPlayer());

        app.keyCode = 40; // down
        app.keyPressed();
        app.draw(); 
        assertNotNull(app.getPlayer());
    }

    @Test
    public void BombTest() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go down for 3 blocks
        for (int i = 0; i < 3; i++) {
            app.keyCode = 40; // down
            app.keyPressed();
            app.draw();
        }

        // Go right
        app.keyCode = 39; // right
        app.keyPressed();
        app.draw();

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go left
        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        // Go up
        app.keyCode = 38; // up
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());

        // Go down
        app.keyCode = 40; // down
        app.keyPressed();
        app.draw();

        // Go right
        app.keyCode = 39; // right
        app.keyPressed();
        app.draw();

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go left
        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        // Go up
        app.keyCode = 38; // up
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());

        // Go down
        app.keyCode = 40; // down
        app.keyPressed();
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Go up
        app.keyCode = 38; // up
        app.keyPressed();
        app.draw();

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go right for 3 blocks
        for (int i = 0; i < 3; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());

        // Go left for 3 blocks
        for (int i = 0; i < 3; i++) {
            app.keyCode = 37; // left
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go right for 3 blocks
        for (int i = 0; i < 3; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Center() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Left_2() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go left
        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Left_1() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go left
        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        // Go left
        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Up_2() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go up
        app.keyCode = 38; // up
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Up_1() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go up
        app.keyCode = 38; // up
        app.keyPressed();
        app.draw();

        // Go up
        app.keyCode = 38; // up
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Down_2() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go down
        app.keyCode = 40; // down
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Down_1() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go right
        app.keyCode = 39; // right
        app.keyPressed();
        app.draw();

        // Go right
        app.keyCode = 39; // right
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Right_2() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go right
        app.keyCode = 39; // right
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void BombKillPlayer_Right_1() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        // Go down
        app.keyCode = 40; // down
        app.keyPressed();
        app.draw();

        // Go down
        app.keyCode = 40; // down
        app.keyPressed();
        app.draw();

        // Wait for bomb to explode
        for(int i = 0; i < 60 * 4; i++) {
            app.draw();
        }

        assertNotNull(app.getBomb());
        assertNotNull(app.getBombs());
    }

    @Test
    public void Win() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go down
        for (int i = 0; i < 20; i++) {
            app.keyCode = 40; // down
            app.keyPressed();
            app.draw();
        }

        app.draw();

        for (int i = 0; i < 20; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        app.draw();
        app.draw();
    }

    @Test
    public void outOfTime_and_EneyMovement() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        for (int i = 0; i < 60 * 200; i++) {
            app.draw();
        }

        app.delay(1000);
        app.draw();
    }

    @Test
    public void killByEnemy() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go right for 4 blocks
        for (int i = 0; i < 4; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Go down until meet wall
        for (int i = 0; i < 5; i++) {
            app.keyCode = 40; // down
            app.keyPressed();
            app.draw();
        }

        // Go right for 7 blocks
        for (int i = 0; i < 7; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        // Wait for enemy to kill player
        for (int i = 0; i < 60 * 9; i++) {
            app.draw();
        }

        app.draw();
    }

    @Test
    public void killEnemy() {
        App app = new App();
        app.noLoop();
        app.setConfig("src/test/resources/config.json");
        PApplet.runSketch(new String[] {"App"}, app);
        app.delay(1000);
        app.draw();

        // Go down
        for (int i = 0; i < 20; i++) {
            app.keyCode = 40; // down
            app.keyPressed();
            app.draw();
        }

        app.draw();

        for (int i = 0; i < 7; i++) {
            app.keyCode = 39; // right
            app.keyPressed();
            app.draw();
        }

        for (int i = 0; i < 2; i++) {
            app.keyCode = 38; // up
            app.keyPressed();
            app.draw();
        }

        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        app.keyCode = 38; // up
        app.keyPressed();
        app.draw();

        for (int i = 0; i < 4; i++) {
            app.keyCode = 38; // up
            app.keyPressed();
            app.draw();

            // Set bomb
            app.keyCode = 32;
            app.keyPressed();
            app.draw();
        }

        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        // Set bomb
        app.keyCode = 32;
        app.keyPressed();
        app.draw();

        for (int i = 0; i < 2; i++) {
            app.keyCode = 40; // down
            app.keyPressed();
            app.draw();

            // Set bomb
            app.keyCode = 32;
            app.keyPressed();
            app.draw();
        }

        app.keyCode = 37; // left
        app.keyPressed();
        app.draw();

        app.keyCode = 40; // down
        app.keyPressed();
        app.draw();

        app.keyCode = 40; // down
        app.keyPressed();
        app.draw();

        for (int i = 0; i < 60 * 10; i++) {
            app.getPlayer().tick();
            app.draw();
        }
    }

    
}

