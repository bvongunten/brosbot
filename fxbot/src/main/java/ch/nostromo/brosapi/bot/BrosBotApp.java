package ch.nostromo.brosapi.bot;

import ch.nostromo.brosapi.api.BrosApi;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BrosBotApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        GamePane gamePane = new GamePane();
        Scene scene = new Scene(gamePane);

        primaryStage.setScene(scene);
        primaryStage.show();

        BrosApi brosApi = new BrosApi();
        brosApi.setListener(gamePane);
        brosApi.launchApi();

    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
