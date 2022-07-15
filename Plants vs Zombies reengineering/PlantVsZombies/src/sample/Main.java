package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * The Class Main.
 */
public class Main extends Application {
    
    /** The media player. */
    static MediaPlayer mediaPlayer;
    
    /** The currentd. */
    private static Database currentd;

    /**
     * Start.
     *
     * @param primaryStage the primary stage
     * @throws Exception the exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        deserialize();
        addMusic();
        Parent mainPage=FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Scene scene = new Scene(mainPage,1024,600);
        primaryStage.setTitle("Plants VS Zombies");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Adds the music.
     */
    public void addMusic() {
        Media sound = new Media(getClass().getResource("/assets/background.wav").toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setStartTime(Duration.seconds(0));
        mediaPlayer.setStopTime(Duration.seconds(50));
        mediaPlayer.play();
    }

    /**
     * Gets the database.
     *
     * @return the database
     */
    public static Database getDatabase(){
        return currentd;
    }
    
    /**
     * Serialize.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void serialize() throws IOException {
        ObjectOutputStream out=null;
        try {
            out = new ObjectOutputStream (new FileOutputStream("database.txt"));
            out.writeObject(currentd);
        }
        finally {
            out.close();
            System.exit(0);
        }
    }
    
    /**
     * Deserialize.
     *
     * @throws ClassNotFoundException the class not found exception
     * @throws FileNotFoundException the file not found exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void deserialize() throws ClassNotFoundException, FileNotFoundException, IOException{
        ObjectInputStream in = null;
        try {
            in=new ObjectInputStream (new FileInputStream("database.txt"));
            currentd=(Database) in.readObject();
            in.close();
        }
        catch (FileNotFoundException e){
            currentd=new Database();
        }
        catch (NullPointerException e) {
            currentd=new Database();

        }
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
