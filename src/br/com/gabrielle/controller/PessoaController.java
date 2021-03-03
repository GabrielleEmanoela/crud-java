package br.com.gabrielle.controller;

import br.com.gabrielle.entities.Pessoa;
import br.com.gabrielle.gui.util.Alerts;
import br.com.gabrielle.service.PessoaService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PessoaController implements Initializable {

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
    private Button btnLimpar;
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


    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.formatDatePicker();
        this.loadValues();

    }

    @FXML
    private void onSave() {
        try {
            PessoaService pessoaService = new PessoaService();
            if (!telefone.getText().isEmpty() && !nome.getText().isEmpty() && !endereco.getText().isEmpty() && !email.getText().isEmpty())
                if (!id.getText().isEmpty()) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(Integer.parseInt(id.getText()));
                    pessoa.setNome(nome.getText());
                    pessoa.setEndereco(endereco.getText());
                    pessoa.setDataNascimento(Date.from(LocalDate.parse(dataNascimento.getValue().toString()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    pessoa.setTelefone(telefone.getText());
                    pessoa.setEmail(email.getText());

                    loadValues();
                    pessoaService.update(pessoa);


                } else {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setNome(nome.getText());
                    pessoa.setEndereco(endereco.getText());
                    pessoa.setDataNascimento(Date.from(LocalDate.parse(dataNascimento.getValue().toString()).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    pessoa.setTelefone(telefone.getText());
                    pessoa.setEmail(email.getText());


                    pessoaService.insert(pessoa);

                    loadValues();


                }
            else {
                Alerts.showAlert("Aconteceu um erro ", null, "Por favor verifique os campos, e entre novamente", javafx.scene.control.Alert.AlertType.ERROR);

            }


        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    @FXML
    private void onRemove() {
        try {
            var pessoa = this.tablePessoaView.getSelectionModel().getSelectedItem();
            if (pessoa != null) {
                if (new PessoaService().delete(pessoa))
                    tablePessoaView.setItems(FXCollections.observableArrayList(tablePessoaView.getItems().filtered(pes -> pes.getId() != pessoa.getId())));
                else
                    Alerts.showAlert("Aconteceu um erro", null, "Não foi possível remover, tente novamente.", javafx.scene.control.Alert.AlertType.ERROR);
            } else
                Alerts.showAlert("Aconteceu um erro", null, "Nenhuma linha foi selecionada.", javafx.scene.control.Alert.AlertType.ERROR);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML

    private void onEdit() {
        try {

            var pessoa = tablePessoaView.getSelectionModel().getSelectedItem();
            id.setText(String.valueOf(pessoa.getId()));
            nome.setText(pessoa.getNome());
            endereco.setText(pessoa.getEndereco());
            telefone.setText(pessoa.getTelefone());
            email.setText(pessoa.getEmail());
            if (pessoa != null) {

            } else {
                Alerts.showAlert(" Error ", null, "Não foi possível remover, tente novamente.", javafx.scene.control.Alert.AlertType.ERROR);
            }

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
        var pessoas = new PessoaService().findAll();

        tablePessoaView.setItems(FXCollections.observableArrayList(pessoas));

    }

    private void formatDatePicker() {
        dataNascimento.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }

        });
    }

    public void setBtnLimpar(ActionEvent evt) {
        nome.clear();
        endereco.clear();
        telefone.clear();
        email.clear();
        dataNascimento.getEditor().clear();
    }
}







