package com.darrensills;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.AuctionSystem;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Darren Sills & Gedvydas Jucius
 * Main class is the launcher for the JavaFX application and also initializes the Auction System.
 */
public class Main extends Application {
    public final static AuctionSystem auctionSystem = new AuctionSystem(); //Creates the singular auction system, static so that the list of bidders and items can be called from anywhere

    /**
     * Starts the JavaFX scene; Main Menu.fxml
     * @param stage stage for the JavaFX application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/AuctionMain.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 563);
        stage.setTitle("Auction House");
        stage.setScene(scene);
        stage.setResizable(false); //application isn't responsive so non-resizable
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/images/auctionicon.png")))); //set the icon
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
