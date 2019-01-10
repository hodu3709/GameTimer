package be.mormont.gametimer;

import be.mormont.gametimer.ui.FXMLBuilder;
import be.mormont.gametimer.ui.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.logging.Level;


/**
 * Created by Romain on 28-06-17.
 * This is THE (main) class.
 */
public class Main extends Application {
    private static String ROOT_FXML = "/be/mormont/gametimer/ui/main.fxml";
    private static String ROOT_CSS = "/be/mormont/gametimer/ui/style.css";

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            Lg.getLogger(Main.class).log(Level.SEVERE, "Unhandled exception", e);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // register on close event
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        Pair<Parent, MainController> rootScene = FXMLBuilder.build(ROOT_FXML);
        Scene scene = new Scene(rootScene.getKey());
        scene.getStylesheets().add(ROOT_CSS);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String getCssPath() {
        return ROOT_CSS;
    }
}
