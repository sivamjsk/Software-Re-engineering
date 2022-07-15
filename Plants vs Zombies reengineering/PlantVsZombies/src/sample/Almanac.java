package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


/**
 * The Class Almanac.
 */
public class Almanac extends GlowImage {
    
    /** The almanac pane. */
    @FXML
    private AnchorPane almanacPane;

    /** The entry. */
    @FXML
    private ImageView entry;

    /**
     * Bucketalmanac.
     *
     * @param event the event
     */
    @FXML
    void bucketalmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/buc.png").toString()));

    }

    /**
     * Cherryalmanac.
     *
     * @param event the event
     */
    @FXML
    void cherryalmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/cher.png").toString()));

    }


    /**
     * Sunalmanac.
     *
     * @param event the event
     */
    @FXML
    void sunalmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/su.png").toString()));

    }

    /**
     * Walnutalmanac.
     *
     * @param event the event
     */
    @FXML
    void walnutalmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/wal.png").toString()));

    }
    
    /**
     * Jalapenoalmanac.
     *
     * @param event the event
     */
    @FXML
    void jalapenoalmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/jal.png").toString()));

    }

    /**
     * Normal almanac.
     *
     * @param event the event
     */
    @FXML
    void normalAlmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/nor.png").toString()));

    }

    /**
     * Peaalmanac.
     *
     * @param event the event
     */
    @FXML
    void peaalmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/pee.png").toString()));

    }
    
    /**
     * Repeateralmanac.
     *
     * @param event the event
     */
    @FXML
    void repeateralmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/rep.png").toString()));
    }
    
    /**
     * Conealmanac.
     *
     * @param event the event
     */
    @FXML
    void conealmanac(MouseEvent event) {
        entry.setImage(new Image(getClass().getResource("/assets/almanac/con.png").toString()));
    }

    
    /**
     * Load prev menu.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void loadPrevMenu(MouseEvent event) throws IOException{
        AnchorPane pane= FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        almanacPane.getChildren().setAll(pane);
    }

}

