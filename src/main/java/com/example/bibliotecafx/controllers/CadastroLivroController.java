package com.example.bibliotecafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.bibliotecafx.models.Livro;
import com.example.bibliotecafx.persistencia.LivroDAO;

public class CadastroLivroController {
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
    private CheckBox checkDisponivel;

    @FXML
    public void initialize() {
        // Adiciona opções ao ComboBox de gênero
        comboGenero.getItems().addAll("Ficção", "Romance", "Biografia", "Fantasia", "Terror", "Outros");
    }

    @FXML
    private void salvarLivro() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            int paginas = Integer.parseInt(txtPaginas.getText());
            int anoLancamento = Integer.parseInt(txtAnoLancamento.getText());
            String genero = comboGenero.getValue();
            boolean disponivel = checkDisponivel.isSelected();

            if (titulo.isEmpty() || autor.isEmpty() || genero == null) {
                System.out.println("Preencha todos os campos obrigatórios!");
                return;
            }

            Livro novoLivro = new Livro(0, titulo, autor, paginas, anoLancamento, genero.hashCode(), disponivel);
            livroDAO.adicionarLivro(novoLivro);
            System.out.println("Livro cadastrado com sucesso!");
            limparCampos();
        } catch (NumberFormatException e) {
            System.out.println("Certifique-se de preencher os campos numéricos corretamente!");
        }
    }

    @FXML
    private void cancelar() {
        //Implementar
    }

    private void limparCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtPaginas.clear();
        txtAnoLancamento.clear();
        comboGenero.setValue(null);
        checkDisponivel.setSelected(false);
    }
}