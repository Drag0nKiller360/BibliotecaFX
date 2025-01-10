package com.example.bibliotecafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
}