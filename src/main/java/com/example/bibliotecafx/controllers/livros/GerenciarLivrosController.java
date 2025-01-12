package com.example.bibliotecafx.controllers.livros;

import com.example.bibliotecafx.models.Livro;
import com.example.bibliotecafx.persistencia.LivroDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class GerenciarLivrosController {

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private TableColumn<Livro, Integer> id;

    @FXML
    private TableColumn<Livro, String> titulo;

    @FXML
    private TableColumn<Livro, String> autor;

    @FXML
    private TableColumn<Livro, Boolean> disponivel;

    private LivroDAO livroDAO;

    @FXML
    public void initialize() {
        livroDAO = LivroDAO.getInstance(); // Carrega o DAO
        configurarColunas();
        carregarDadosTabela();
    }

    //Métodos
    private void configurarColunas() {
        // Associa as colunas aos atributos da classe Livro
        id.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        titulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        autor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));
        disponivel.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isDisponivel()).asObject());
    }

    private void carregarDadosTabela() {
        // Carrega os com.example.bibliotecafx.controllers.livros do DAO para a tabela
        ObservableList<Livro> livros = FXCollections.observableArrayList(livroDAO.getLivros());
        tabelaLivros.setItems(livros);
    }

    @FXML
    private void editarLivro() {
        try {
            Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
            if (livroSelecionado != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotecafx/editar_livro.fxml"));
                Parent root = loader.load();

                EditarLivroController editarLivroController = loader.getController();
                editarLivroController.setLivro(livroSelecionado);
                editarLivroController.setOnSaveCallback(() -> atualizarTabela());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Editar Livro");
                stage.show();
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar a janela de edição: " + e.getMessage());
        }
    }

    @FXML
    private void removerLivro() {
        Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            tabelaLivros.getItems().remove(livroSelecionado);
            livroDAO.removerLivro(livroSelecionado);
            System.out.println("Livro removido: " + livroSelecionado.getTitulo());
        } else {
            System.out.println("Nenhum livro selecionado!");
        }
    }

    @FXML
    private void telaAdicionarLivro(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/adicionar_livro.fxml", "Cadastrar Livro", stage);
    }

    @FXML
    private void telaMenu(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/menu_inicial.fxml", "Menu Inicial", stage);
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

    private void atualizarTabela() {
        tabelaLivros.refresh();
    }

}
