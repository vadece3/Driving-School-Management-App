
package autoecole;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author KAMDEM VADECE
 */
public class Autoecole extends Application {
    
    
       @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("homeFXML.fxml"));
       
       Scene scene = new Scene(root);
       
       primaryStage.setScene(scene);
       primaryStage.setTitle("AUTO ECOLE");
       primaryStage.setResizable(true);
       primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
