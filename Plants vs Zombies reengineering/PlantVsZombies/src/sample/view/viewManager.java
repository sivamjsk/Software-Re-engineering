package sample.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;


/**
 * The Class viewManager.
 */
public class viewManager {

    /** The Constant height. */
    private static final int height = 600;
    
    /** The Constant width. */
    private static final int width = 1024;
    
    /** The main pane. */
    private AnchorPane mainPane;
    
    /** The main scene. */
    private Scene mainScene;
    
    /** The main stage. */
    private Stage mainStage;

    /**
     * Instantiates a new view manager.
     */
    public viewManager()
    {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, width, height);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
//        createButtons();
    }

    /**
     * Gets the main stage.
     *
     * @return the main stage
     */
    public Stage getMainStage()
    {
        return mainStage;
    }

//    private void createButtons()
//    {
//        Button button = new Button("Click me!");
//        mainPane.getChildren().add(button);
//    }
}
