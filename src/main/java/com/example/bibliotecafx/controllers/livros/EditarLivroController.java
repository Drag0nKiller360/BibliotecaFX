package com.example.bibliotecafx.controllers.livros;

import com.example.bibliotecafx.models.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarLivroController {
    @FXML
    private TextField tituloField;

    @FXML
    private TextField autorField;

    @FXML
    private TextField anoField;

    private Livro livro; // Livro que será editado
    private Runnable onSaveCallback; // Callback para atualizar a tabela após salvar

    /**
     * Inicializa o controlador com os dados do livro a ser editado.
     */
    public void setLivro(Livro livro) {
        this.livro = livro;
        if (livro != null) {
            tituloField.setText(livro.getTitulo());
            autorField.setText(livro.getAutor());
            anoField.setText(String.valueOf(livro.getAnoLancamento()));
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
            livro.setAnoLancamento(Integer.parseInt(anoField.getText()));
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
