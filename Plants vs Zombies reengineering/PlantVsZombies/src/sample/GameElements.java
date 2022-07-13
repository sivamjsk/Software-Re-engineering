package sample;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.Serializable;


/**
 * The Class GameElements.
 */
public abstract class GameElements implements Serializable{
    
    /** The x. */
    protected int x;
    
    /** The y. */
    protected int y;
    
    /** The path. */
    protected String path;
    
    /** The img. */
    transient protected ImageView img;
    
    /** The width. */
    protected int width;
    
    /** The height. */
    protected int height;

    /**
     * Instantiates a new game elements.
     *
     * @param x_ the x
     * @param y_ the y
     * @param path the path
     * @param width the width
     * @param height the height
     */
    public GameElements(int x_, int y_, String path,int width,int height){
        this.x=x_;
        this.y=y_;
        this.path=getClass().getResource(path).toString();
//        this.pane=p;
        this.width=width;
        this.height=height;

    }

    /**
     * Make image.
     *
     * @param pane the pane
     */
    public void makeImage(Pane pane){
        img = new ImageView();
        Image im=new Image(path,(double) width,(double) height,false,false);
        img.setImage(im);
        img.setX(x);
        img.setY(y);
        pane.getChildren().add(img);
    }
    
    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX(){
        return this.x;
    }

    /**
     * Sets the x.
     *
     * @param x the new x
     */
    public void setX(int x) {
        this.x = x;
        img.setX(x);
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y.
     *
     * @param y the new y
     */
    public void setY(int y) {
        this.y = y;
        img.setY(y);
    }

}
