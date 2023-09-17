package demolition;

import processing.core.PApplet;
import processing.core.PImage;

public class Icon {

    /**
     * Icon coordinate: x
     */
    protected int x;
    /**
     * Icon coordinate: y
     */
    protected int y;
    /**
     * sprite for the icon
     */
    protected PImage sprite;

    /**
     * Icon constructor
     * @param x, coordinate x of the icon
     * @param y, coordinate y of the icon
     * @param sprite, sprite of the icon
     */
    public Icon(int x, int y, PImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    /**
     * Draw icon on the map
     * @param app, the icon itself
     */
    public void draw(PApplet app) {
        app.image(this.sprite, x, y);
    }    
}
