package com.example.bibliotecafx.controllers.usuarios.visitantes;

import com.example.bibliotecafx.models.Visitante;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarVisitanteController {
    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField cpfField;

    private Visitante visitante; // Visitante que será editado
    private Runnable onSaveCallback; // Callback para atualizar a tabela após salvar

    /**
     * Inicializa o controlador com os dados do visitante a ser editado.
     */
    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
        if (visitante != null) {
            nomeField.setText(visitante.getNome());
            cpfField.setText(visitante.getCpf());
            emailField.setText(visitante.getEmail());
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
        if (visitante != null) {
            visitante.setNome(nomeField.getText());
            visitante.setEmail(emailField.getText());
            visitante.setCpf(cpfField.getText());
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
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
    }
}
