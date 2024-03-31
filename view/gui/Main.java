package view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader listLoader = new FXMLLoader();
        listLoader.setLocation(getClass().getResource("examples.fxml"));
        Parent root = listLoader.load();
        ExamplesController examplesController = listLoader.getController();
        primaryStage.setTitle("Examples");
        primaryStage.setScene(new Scene(root, 400, 450));
        primaryStage.show();

        FXMLLoader programLoader = new FXMLLoader();
        programLoader.setLocation(getClass().getResource("program.fxml"));
        Parent programRoot = programLoader.load();
        ProgramController programController = programLoader.getController();
        examplesController.setProgramController(programController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Tables");
        secondaryStage.setScene(new Scene(programRoot, 1400, 985));
        secondaryStage.show(); 
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
