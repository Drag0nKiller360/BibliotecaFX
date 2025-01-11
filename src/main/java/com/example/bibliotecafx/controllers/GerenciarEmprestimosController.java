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

public class GerenciarEmprestimosController {

    @FXML
    private TableView<?> tabelaEmprestimos;

    @FXML
    private TableColumn<?, ?> colunaId;

    @FXML
    private TableColumn<?, ?> colunaLivro;

    @FXML
    private TableColumn<?, ?> colunaMembro;

    @FXML
    private TableColumn<?, ?> colunaDataEmprestimo;

    @FXML
    private TableColumn<?, ?> colunaDataDevolucao;

    @FXML
    private void registrarEmprestimo() {
        // Implementar funcionalidade para registrar empréstimo
    }

    @FXML
    private void registrarDevolucao() {
        // Implementar funcionalidade para registrar devolução
    }

    @FXML
    private void telaRegistrarEmprestimo() {
        carregarTela("/com/example/bibliotecafx/registrar_emprestimo.fxml", "Registrar Emprestimo");
    }

    @FXML
    private void telaMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/menu_inicial.fxml", "Menu Inicial", stage);
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
            System.err.println("Erro ao apresentar tela: " + e.getMessage());
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