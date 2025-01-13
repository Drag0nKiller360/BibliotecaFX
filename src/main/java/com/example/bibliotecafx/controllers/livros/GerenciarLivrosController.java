package com.example.bibliotecafx.controllers.livros;

import com.example.bibliotecafx.models.Livro;
import com.example.bibliotecafx.persistencia.AlunoDAO;
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
    private TableColumn<Livro, Integer> colunaId;

    @FXML
    private TableColumn<Livro, String> colunaTitulo;

    @FXML
    private TableColumn<Livro, String> colunaAutor;

    @FXML
    private TableColumn<Livro, Integer> colunaPaginas;

    @FXML
    private TableColumn<Livro, Integer> colunaAnoLancamento;

    @FXML
    private TableColumn<Livro, String> colunaGenero;

    @FXML
    private TableColumn<Livro, String> colunaDisponivel;

    private ObservableList<Livro> listaLivros;
    private LivroDAO livroDAO;

    public void initialize() {
        livroDAO = LivroDAO.getInstance();
        configurarColunas();
        carregarDadosTabela();
    }

    private void configurarColunas() {
        colunaId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colunaTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colunaAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));
        colunaPaginas.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPaginas()).asObject());
        colunaAnoLancamento.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAnoLancamento()).asObject());
        colunaGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));
        colunaDisponivel.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isDisponivel() ? "Sim" : "Não"));
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
            if(livroSelecionado.isDisponivel()){
                tabelaLivros.getItems().remove(livroSelecionado);
                livroDAO.removerLivro(livroSelecionado);
                System.out.println("Livro removido: " + livroSelecionado.getTitulo());
            } else {
                System.out.println("Não é possível remover um livro indisponível!");
            }
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
