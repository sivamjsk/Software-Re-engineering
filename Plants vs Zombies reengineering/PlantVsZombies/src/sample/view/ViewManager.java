package sample.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;


/**
 * The Class ViewManager.
 */
public class ViewManager{
    
    /** The main pane. */
    private AnchorPane mainPane;
    
    /** The main scene. */
    private Scene mainScene;
    
    /** The main stage. */
    private Stage mainStage;

    /**
     * Instantiates a new view manager.
     */
    public ViewManager()
    {

        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, 1024, 600);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
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
}
