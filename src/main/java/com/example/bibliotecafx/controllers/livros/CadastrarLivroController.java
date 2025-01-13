package com.example.bibliotecafx.controllers.livros;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.example.bibliotecafx.models.Livro;
import com.example.bibliotecafx.persistencia.LivroDAO;
import javafx.stage.Stage;

import java.io.IOException;

public class CadastrarLivroController {
    private final LivroDAO livroDAO = LivroDAO.getInstance();

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtPaginas;

    @FXML
    private TextField txtAnoLancamento;

    @FXML
    private ComboBox<String> comboGenero;


    @FXML
    public void initialize() {
        // Adiciona opções ao ComboBox de gênero
        comboGenero.getItems().addAll("Ficção", "Romance", "Biografia", "Fantasia", "Terror", "Outros");
    }

    @FXML
    private void salvarLivro(ActionEvent event) {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int paginas = Integer.parseInt(txtPaginas.getText());
            int anoLancamento = Integer.parseInt(txtAnoLancamento.getText());
            String genero = comboGenero.getValue();
            boolean disponivel = true;
            int id = 1;

            if(!livroDAO.getLivros().isEmpty()){
                id = livroDAO.getLivros().getLast().getId() + 1;
            }

            if (titulo.isEmpty() || autor.isEmpty() || genero == null) {
                System.out.println("Preencha todos os campos obrigatórios!");
                return;
            }

            Livro novoLivro = new Livro(id, titulo, autor, paginas, anoLancamento, genero, disponivel);
            livroDAO.adicionarLivro(novoLivro);
            System.out.println("Livro cadastrado com sucesso!");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            trocarTela("/com/example/bibliotecafx/gerenciar_livros.fxml", "Gerenciar Livros", stage);
        } catch (NumberFormatException e) {
            System.out.println("Certifique-se de preencher os campos numéricos corretamente!");
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/gerenciar_livros.fxml", "Gerenciar Livros", stage);
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