package com.example.bibliotecafx.controllers.usuarios.alunos;

import com.example.bibliotecafx.models.Aluno;
import com.example.bibliotecafx.persistencia.AlunoDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class GerenciarAlunosController {
    @FXML
    private TableView<Aluno> tabelaAlunos;

    @FXML
    private TableColumn<Aluno, Integer> colunaId;

    @FXML
    private TableColumn<Aluno, String> colunaNome;

    @FXML
    private TableColumn<Aluno, String> colunaMatricula;

    @FXML
    private TableColumn<Aluno, String> colunaEmail;

    private AlunoDAO alunoDAO;

    @FXML
    public void initialize() {
        alunoDAO = AlunoDAO.getInstance(); // Carrega o DAO
        configurarColunas();
        carregarDadosTabela();
    }

    //Métodos
    private void configurarColunas() {
        // Associa as colunas aos atributos da classe Aluno
        colunaId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colunaMatricula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMatricula()));
        colunaEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailInstitucional()));
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
    }

    private void carregarDadosTabela() {
        // Carrega os com.example.bibliotecafx.controllers.alunos do DAO para a tabela
        ObservableList<Aluno> alunos = FXCollections.observableArrayList(alunoDAO.getAlunos());
        tabelaAlunos.setItems(alunos);
    }

    @FXML
    private void editarAluno() {
        try {
            Aluno alunoSelecionado = tabelaAlunos.getSelectionModel().getSelectedItem();
            if (alunoSelecionado != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotecafx/editar_aluno.fxml"));
                Parent root = loader.load();

                EditarAlunoController editarAlunoController = loader.getController();
                editarAlunoController.setAluno(alunoSelecionado);
                editarAlunoController.setOnSaveCallback(() -> atualizarTabela());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Editar Aluno");
                stage.show();
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar a janela de edição: " + e.getMessage());
        }
    }

    @FXML
    private void removerAluno() {
        Aluno alunoSelecionado = tabelaAlunos.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            tabelaAlunos.getItems().remove(alunoSelecionado);
            alunoDAO.removerAluno(alunoSelecionado);
            System.out.println("Aluno removido: " + alunoSelecionado.getNome());
        } else {
            System.out.println("Nenhum aluno selecionado!");
        }
    }

    @FXML
    private void telaAdicionarAluno(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/adicionar_aluno.fxml", "Cadastrar Aluno", stage);
    }

    @FXML
    private void telaMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/menu_inicial.fxml", "Menu Inicial", stage);
    }

    private void trocarTela(String fxml, String titulo, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Scene novaCena = new Scene(root);
            stage.setScene(novaCena);
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao trocar de tela: " + e.getMessage());
        }
    }

    private void atualizarTabela() {
        tabelaAlunos.refresh();
    }

}
