package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;


/**
 * The Class GamePlayController_progress.
 */
public class GamePlayController_progress {
    
    /** The game play controller. */
    private final GamePlayController gamePlayController;

    /**
     * Instantiates a new game play controller progress.
     *
     * @param gamePlayController the game play controller
     */
    public GamePlayController_progress(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    /**
     * Game progress.
     */
    public void gameProgress() {
        Timeline gameStatus = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    GamePlayController.timeElapsed = (GamePlayController.numZombiesKilled / GamePlayController.l.getTotalZombies());
                    gamePlayController.getProgressBar().setProgress(GamePlayController.timeElapsed);
                    if (GamePlayController.wonGame == (-1)) {
                        GamePlayController.numZombiesKilled = 0;
                        gameLost();
                    } else if (GamePlayController.wonGame == 0 && GamePlayController.allZombies.size() == 0 && GamePlayController.l.getTotalZombies() == gamePlayController.getSpawnedZombies()) {
                        GamePlayController.numZombiesKilled = 0;
                        gameWon();
                    } else if (gamePlayController.getProgressBar().getProgress() >= 1) {
                        GamePlayController.spZ1.stop();
                        GamePlayController.spZ2.stop();
                        gameWon();
                    }
                    GamePlayController_animation.endAnimations();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }));
        gameStatus.setCycleCount(Timeline.INDEFINITE);
        gameStatus.play();
        GamePlayController.animationTimelines.add(gameStatus);
    }

    /**
     * Game lost.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void gameLost() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(gamePlayController.getClass().getResource("EndGame.fxml"));
        AnchorPane pane = fxmlLoader.load();
        EndGameController controller = fxmlLoader.<EndGameController>getController();
        controller.initData(gamePlayController.getLevelNumber(), false, GamePlayController.d);
        gamePlayController.getGamePlayRoot().getChildren().setAll(pane);

    }

    /**
     * Game won.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void gameWon() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(gamePlayController.getClass().getResource("EndGame.fxml"));
        AnchorPane pane = fxmlLoader.load();
        EndGameController controller = fxmlLoader.<EndGameController>getController();
        controller.initData(gamePlayController.getLevelNumber(), true, GamePlayController.d);
        gamePlayController.getGamePlayRoot().getChildren().setAll(pane);

    }


}