package br.com.gabrielle.controller;

import br.com.gabrielle.entities.Pessoa;
import br.com.gabrielle.gui.util.Alerts;
import br.com.gabrielle.gui.util.Utils;
import br.com.gabrielle.service.PessoaService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PessoaController implements Initializable {

    PessoaService pessoaService;
    @FXML
    private TextField id;
    @FXML
    private TextField nome;
    @FXML
    private TextField endereco;
    @FXML
    private DatePicker dataNascimento;
    @FXML
    public TextField telefone;
    @FXML
    private TextField email;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Pessoa> tablePessoaView;
    @FXML
    private TableColumn<Pessoa, Integer> colId;
    @FXML
    private TableColumn<Pessoa, String> colNome;
    @FXML
    private TableColumn<Pessoa, String> colTelefone;
    @FXML
    private TableColumn<Pessoa, String> colEndereco;
    @FXML
    private TableColumn<Pessoa, Date> colNascimento;
    @FXML
    private TableColumn<Pessoa, String> colEmail;
    @FXML
    private TableColumn<Pessoa, Boolean> edit;


    @FXML
    private void buttonAction() {
        try {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(nome.getText());
            pessoa.setEndereco(endereco.getText());
            pessoa.setDataNascimento(new Date(dataNascimento.getValue().toEpochDay()));
            pessoa.setTelefone(telefone.getText());
            pessoa.setEmail(email.getText());

            PessoaService pessoaService = new PessoaService();
            pessoaService.insert(pessoa);
            loadValues();


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    @FXML
    private void onRemove() {
        try {
            var pessoa = tablePessoaView.getSelectionModel().getSelectedItem();
            if (pessoa != null) {
                if (new PessoaService().delete(pessoa))
                    tablePessoaView.setItems(FXCollections.observableArrayList(tablePessoaView.getItems().filtered(pes -> pes.getId() != pessoa.getId())));
                else
                    Alerts.showAlert("Aconteceu um erro", null, "Não foi possível remover, tente novamente.", Alert.AlertType.ERROR);
            } else Alerts.showAlert("Aconteceu um erro", null, "Nenhuma linha foi selecionada.", Alert.AlertType.ERROR);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void loadValues() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colNascimento.setCellValueFactory(new PropertyValueFactory<>("DataNascimento"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        var pessoas = new PessoaService().buscarPessoas();

        tablePessoaView.setItems(FXCollections.observableArrayList(pessoas));

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadValues();
        tablePessoaView.setEditable(true);



    }

}
