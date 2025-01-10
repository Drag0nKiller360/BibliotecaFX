package com.example.bibliotecafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuInicialController {

    @FXML
    private void abrirGerenciarLivros(ActionEvent event) {
        carregarTela("gerenciar_livros.fxml", "Gerenciar Livros");
    }

    @FXML
    private void abrirGerenciarMembros(ActionEvent event) {
        carregarTela("gerenciar_membros.fxml", "Gerenciar Membros");
    }

    @FXML
    private void abrirGerenciarEmprestimos(ActionEvent event) {
        carregarTela("gerenciar_emprestimos.fxml", "Gerenciar Empréstimos");
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
}
