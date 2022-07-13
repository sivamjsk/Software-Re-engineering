package sample;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.Serializable;
import java.nio.file.spi.FileSystemProvider;


/**
 * The Class Plant.
 */
public abstract class Plant extends GameElements{
    
    /** The path. */
    String path;

/** The hp. */
//    protected GridPane lawn;
    protected int hp;
    
    /** The col. */
    protected int col;
    
    /** The row. */
    protected int row;
    
    /**
     * Instantiates a new plant.
     *
     * @param x the x
     * @param y the y
     * @param path the path
     * @param hp the hp
     * @param width the width
     * @param height the height
     * @param col the col
     * @param row the row
     */
    public Plant(int x, int y, String path, int hp,int width,int height,int col,int row){
        super(x,y,path,width,height);
        this.hp=hp;
//        this.lawn=lawn;
        this.col=col;
        this.row=row;

    }

    /**
     * Make image.
     *
     * @param lawn the lawn
     */
    public void makeImage(GridPane lawn){
        img = new ImageView();
        Image im=new Image(path,(double) width,(double) height,false,false);
        img.setImage(im);
        lawn.add(img,col,row,1,1);
    }

    /**
     * Attack.
     *
     * @param p the p
     */
    public void attack(Pane p){

    }

    /**
     * Gets the hp.
     *
     * @return the hp
     */
    public int getHp(){
        return this.hp;
    }

    /**
     * Sets the hp.
     *
     * @param hp the new hp
     */
    public void setHp(int hp){
        this.hp=hp;
        if (this.hp<=0){
            GamePlayController.allPlants.remove(this);
            img.setVisible(false);
            img.setDisable(true);
        }
    }

    /**
     * End animation.
     *
     * @param t the t
     */
    public void endAnimation(Timeline t)
    {
        t.stop();
    }

}
