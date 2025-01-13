package com.example.bibliotecafx.controllers.livros;

import com.example.bibliotecafx.models.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarLivroController {
    @FXML
    private TextField tituloField;

    @FXML
    private TextField autorField;

    @FXML
    private TextField paginasField;

    @FXML
    private TextField anoField;

    @FXML
    private ComboBox<String> comboGeneroField;

    private Livro livro; // Livro que será editado
    private Runnable onSaveCallback; // Callback para atualizar a tabela após salvar

    @FXML
    public void initialize() {
        // Adiciona opções ao ComboBox de gênero
        comboGeneroField.getItems().addAll("Ficção", "Romance", "Biografia", "Fantasia", "Terror", "Outros");
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
        if (livro != null) {
            tituloField.setText(livro.getTitulo());
            autorField.setText(livro.getAutor());
            paginasField.setText(String.valueOf(livro.getPaginas()));
            anoField.setText(String.valueOf(livro.getAnoLancamento()));
            comboGeneroField.setValue(livro.getGenero());
        }
    }

    /**
     * Define o callback para ser executado após salvar as alterações.
     */
    public void setOnSaveCallback(Runnable onSaveCallback) {
        this.onSaveCallback = onSaveCallback;
    }

    /**
     * Salva as alterações e fecha a janela.
     */
    @FXML
    private void salvar() {
        if (livro != null) {
            livro.setTitulo(tituloField.getText());
            livro.setAutor(autorField.getText());
            livro.setPaginas(Integer.parseInt(paginasField.getText()));
            livro.setAnoLancamento(Integer.parseInt(anoField.getText()));
            livro.setGenero(comboGeneroField.getValue());
        }

        if (onSaveCallback != null) {
            onSaveCallback.run(); // Atualiza a tabela na tela principal
        }

        fecharJanela();
    }

    /**
     * Fecha a janela sem salvar as alterações.
     */
    @FXML
    private void cancelar() {
        fecharJanela();
    }

    /**
     * Fecha a janela atual.
     */
    private void fecharJanela() {
        Stage stage = (Stage) tituloField.getScene().getWindow();
        stage.close();
    }
}
