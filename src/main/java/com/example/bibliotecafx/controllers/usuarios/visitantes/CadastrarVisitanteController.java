package com.example.bibliotecafx.controllers.usuarios.visitantes;

import com.example.bibliotecafx.models.Aluno;
import com.example.bibliotecafx.models.Visitante;
import com.example.bibliotecafx.persistencia.AlunoDAO;
import com.example.bibliotecafx.persistencia.VisitanteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CadastrarVisitanteController {
    private final VisitanteDAO visitanteDAO = VisitanteDAO.getInstance();
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCpf;

    @FXML
    private TextField txtEmail;

    @FXML
    private void salvarVisitante(ActionEvent event) {
        try {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String email = txtEmail.getText();
            int id = 1;

            int ultimoIdAluno = alunoDAO.getAlunos().isEmpty() ? 0 : alunoDAO.getAlunos().getLast().getId();
            int ultimoIdVisitante = visitanteDAO.getVisitantes().isEmpty() ? 0 : visitanteDAO.getVisitantes().getLast().getId();

            if (ultimoIdAluno > 0 || ultimoIdVisitante > 0) {
                id = Math.max(ultimoIdAluno, ultimoIdVisitante) + 1;
            }

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty()) {
                System.out.println("Preencha todos os campos obrigatórios!");
                return;
            }

            Visitante novoVisitante = new Visitante(id, nome, cpf, email);
            visitanteDAO.adicionarVisitante(novoVisitante);
            System.out.println("Visitante cadastrado com sucesso!");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            trocarTela("/com/example/bibliotecafx/gerenciar_visitantes.fxml", "Gerenciar Visitantes", stage);
        } catch (NumberFormatException e) {
            System.out.println("Certifique-se de preencher os campos numéricos corretamente!");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/gerenciar_visitantes.fxml", "Gerenciar Visitantes", stage);
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