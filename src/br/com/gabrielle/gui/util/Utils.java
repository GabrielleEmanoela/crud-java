package br.com.gabrielle.gui.util;

import br.com.gabrielle.entities.Pessoa;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Utils {


    public static Stage currentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }



}