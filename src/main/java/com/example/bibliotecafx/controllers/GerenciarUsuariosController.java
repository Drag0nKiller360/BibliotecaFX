package com.example.bibliotecafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class GerenciarUsuariosController {

    @FXML
    private TableView<?> tabelaUsuarios;

    @FXML
    private TableColumn<?, ?> colunaId;

    @FXML
    private TableColumn<?, ?> colunaNome;

    @FXML
    private TableColumn<?, ?> colunaEmail;

    @FXML
    private void telaAdicionarAluno() {
        carregarTela("/com/example/bibliotecafx/adicionar_aluno.fxml", "Cadastrar Aluno");
    }

    @FXML
    private void telaAdicionarVisitante() {
        carregarTela("/com/example/bibliotecafx/adicionar_visitante.fxml", "Cadastrar Visitante");
    }

    @FXML
    private void telaMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/menu_inicial.fxml", "Menu Inicial", stage);
    }

    @FXML
    private void adicionarAluno() {
        // Implementar funcionalidade para adicionar membro
    }

    @FXML
    private void adicionarVisitante() {
        // Implementar funcionalidade para adicionar membro
    }

    @FXML
    private void editarAluno() {
        // Implementar funcionalidade para editar membro
    }

    @FXML
    private void editarVisitante() {
        // Implementar funcionalidade para editar membro
    }

    @FXML
    private void removerAluno() {
        // Implementar funcionalidade para remover membro
    }

    @FXML
    private void removerVisitante() {
        // Implementar funcionalidade para remover membro
    }

    @FXML
    private void cancelarCadastro() {
        // Implementar funcionalidade para voltar de cadastro
    }

    private void carregarTela(String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
