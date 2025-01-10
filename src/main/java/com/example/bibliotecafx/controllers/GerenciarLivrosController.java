package com.example.bibliotecafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GerenciarLivrosController {

    @FXML
    private TableView<?> tabelaLivros;

    @FXML
    private TableColumn<?, ?> colunaId;

    @FXML
    private TableColumn<?, ?> colunaTitulo;

    @FXML
    private TableColumn<?, ?> colunaAutor;

    @FXML
    private TableColumn<?, ?> colunaDisponivel;

    @FXML
    private void adicionarLivro() {
        // Implementar funcionalidade para adicionar livro
    }

    @FXML
    private void editarLivro() {
        // Implementar funcionalidade para editar livro
    }

    @FXML
    private void removerLivro() {
        // Implementar funcionalidade para remover livro
    }
}
