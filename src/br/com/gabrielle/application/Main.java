package br.com.gabrielle.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class Main extends Application {


    public static void main(String[] args) {
        Locale.setDefault(new Locale("pt", "BR"));
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {

            Parent parent = FXMLLoader.load(getClass().getResource("../gui/View.fxml"));
            Scene scene = new Scene(parent);
            primaryStage.setTitle("Gerenciamento de Pessoa");

            scene.getStylesheets().add(Main.class.getResource("./bootstrap.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }
    }

}





