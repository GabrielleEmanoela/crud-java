package br.com.gabrielle.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../gui/View.fxml"));
            primaryStage.setTitle("Gerenciamento de Pessoa");
            Scene scene = new Scene(parent);
            scene.getStylesheets().add(Main.class.getResource("../resources/bootstrap.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }

    }




    public static void main(String[] args) {
        launch(args);
    }
}


