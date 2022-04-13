package Utilities;

import com.darrensills.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Darren Sills
 * Allows the user to provide a scene they want to switch to and handles the switching to that scene.
 */
public class SceneSwitcher {
    private static Parent scene;

    /**
     * Switches the scene to the given fxml file
     * @param sceneName name of the fxml screen the user wants to switch to
     * @throws IOException signals that an I/O exception of some sort has occurred
     */
    public static void SceneSwitch(Label label, String sceneName, String sceneTitle) throws IOException {
        Stage stage = (Stage) label.getScene().getWindow(); //get the stage from the button
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/fxml/" + sceneName)));
        stage.setTitle(sceneTitle);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches the scene to the given fxml file in a new window WITHOUT closing the old scene
     * @param actionEvent handler that reacts to the javafx root event
     * @param sceneName name of the fxml screen the user wants to switch to
     * @throws IOException signals that an I/O exception of some sort has occurred
     */
    public static void SceneSwitchNewWindow(ActionEvent actionEvent, String sceneName, String sceneTitle) throws IOException {
        Stage primaryStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Stage newStage = new Stage();
        newStage.setTitle(sceneTitle);
        newStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/auctionicon.png")))); //set the icon
        newStage.initModality(Modality.WINDOW_MODAL); //new stage set as a child window of the original
        newStage.initOwner(primaryStage);
        newStage.setResizable(false);
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/fxml/" + sceneName)));
        newStage.setScene(new Scene(scene));
        newStage.show();
    }
}
