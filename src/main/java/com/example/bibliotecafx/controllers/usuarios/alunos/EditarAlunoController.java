package com.example.bibliotecafx.controllers.usuarios.alunos;

import com.example.bibliotecafx.models.Aluno;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarAlunoController {
    @FXML
    private TextField nomeField;

    @FXML
    private TextField emailInstitucionalField;

    @FXML
    private TextField matriculaField;

    private Aluno aluno; // Aluno que será editado
    private Runnable onSaveCallback; // Callback para atualizar a tabela após salvar

    /**
     * Inicializa o controlador com os dados do aluno a ser editado.
     */
        public void setAluno(Aluno aluno) {
        this.aluno = aluno;
        if (aluno != null) {
            nomeField.setText(aluno.getNome());
            emailInstitucionalField.setText(aluno.getEmailInstitucional());
            matriculaField.setText(aluno.getMatricula());
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
        if (aluno != null) {
            aluno.setNome(nomeField.getText());
            aluno.setEmailInstitucional(emailInstitucionalField.getText());
            aluno.setMatricula(matriculaField.getText());
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
