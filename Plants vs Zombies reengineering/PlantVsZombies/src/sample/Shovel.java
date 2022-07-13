package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;


/**
 * The Class Shovel.
 */
public class Shovel extends GameElements{
    
    /** The is disabled. */
    private static boolean isDisabled=true;
    
    /** The shovel. */
    private static Shovel shovel;

    /**
     * Instantiates a new shovel.
     */
    private Shovel(){
        super(500,10,"/assets/Shovel.png",60,60);
        this.path = getClass().getResource("/assets/Shovel.png").toString();
    }
    
    /**
     * Checks if is checks if is disabled.
     *
     * @return true, if is checks if is disabled
     */
    public boolean isIsDisabled(){
        return isDisabled;
    }

    /**
     * Gets the single instance of Shovel.
     *
     * @return single instance of Shovel
     */
    public static Shovel getInstance(){
        if (shovel==null){
            shovel=new Shovel();

        }
        return shovel;
    }

    /**
     * Make image.
     *
     * @param p the p
     */
    @Override
    public void makeImage(Pane p)
    {
        super.makeImage(p);
        shovel.img.setOnMouseClicked(e-> {
            shovel.isDisabled = false;
            shovel.enable();
            SidebarElement.setCardSelectedToNull();
        });
    }

    /**
     * Enable.
     */
    public void enable(){
        Glow glow=new Glow();
        shovel.img.setEffect(glow);
        glow.setLevel(0.4);
    }

    /**
     * Disable.
     */
    public void disable(){
        if (!isDisabled){
            Glow glow=(Glow) shovel.img.getEffect();
            glow.setLevel(0.0);
            shovel.isDisabled=true;
        }
    }
}
