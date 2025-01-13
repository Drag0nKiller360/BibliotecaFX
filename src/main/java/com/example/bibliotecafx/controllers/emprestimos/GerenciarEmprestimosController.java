package com.example.bibliotecafx.controllers.emprestimos;

import com.example.bibliotecafx.models.Emprestimo;
import com.example.bibliotecafx.models.Livro;
import com.example.bibliotecafx.models.Status;
import com.example.bibliotecafx.persistencia.EmprestimoDAO;
import com.example.bibliotecafx.persistencia.LivroDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GerenciarEmprestimosController {

    @FXML
    private TableView<Emprestimo> tabelaEmprestimos;

    @FXML
    private TableColumn<Emprestimo, Integer> colunaId;

    @FXML
    private TableColumn<Emprestimo, Integer> colunaLivro;

    @FXML
    private TableColumn<Emprestimo, Integer> colunaUsuario;

    @FXML
    private TableColumn<Emprestimo, String> colunaDataEmprestimo;

    @FXML
    private TableColumn<Emprestimo, String> colunaDataPrevistaDevolucao;

    @FXML
    private TableColumn<Emprestimo, String> colunaDataDevolucao;

    @FXML
    private TableColumn<Emprestimo, String> colunaStatus;

    private final LivroDAO livroDAO = LivroDAO.getInstance();
    private final EmprestimoDAO emprestimoDAO = EmprestimoDAO.getInstance();

    @FXML
    public void initialize() {
        configurarColunas();
        carregarDadosTabela();
    }

    private void configurarColunas() {
        // Usando DateTimeFormatter para formatar LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colunaId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colunaLivro.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLivroId()).asObject());
        colunaUsuario.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUsuarioId()).asObject());

        // Usando LocalDate com DateTimeFormatter
        colunaDataEmprestimo.setCellValueFactory(cellData -> {
            LocalDate dataEmprestimo = cellData.getValue().getDataEmprestimo();
            return new SimpleStringProperty(dataEmprestimo != null ? dataEmprestimo.format(dateFormatter) : "");
        });

        // Usando LocalDate com DateTimeFormatter
        colunaDataPrevistaDevolucao.setCellValueFactory(cellData -> {
            LocalDate dataPrevistaDevolucao = cellData.getValue().getDataPrevistaDevolucao();
            return new SimpleStringProperty(dataPrevistaDevolucao  != null ? dataPrevistaDevolucao .format(dateFormatter) : "");
        });

        // Verificando se a data de devolução é nula
        colunaDataDevolucao.setCellValueFactory(cellData -> {
            LocalDate dataDevolucao = cellData.getValue().getDataDevolucao();
            return new SimpleStringProperty(dataDevolucao == null ? "Não devolvido" : dataDevolucao.format(dateFormatter));
        });

        // Configuração do status
        colunaStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
    }

    private void carregarDadosTabela() {
        ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList(emprestimoDAO.getEmprestimos());
        for(Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getStatus() != Status.DEVOLVIDO){
                if (emprestimo.getDataPrevistaDevolucao().isBefore(LocalDate.now())) {
                    emprestimo.setStatus(Status.ATRASADO);
                }
            }
        }
        tabelaEmprestimos.setItems(emprestimos);
    }

    @FXML
    private void telaRegistrarEmprestimo(ActionEvent event) {
        trocarTela(event, "/com/example/bibliotecafx/registrar_emprestimo.fxml", "Registrar Empréstimo");
    }

    @FXML
    private void registrarDevolucao(ActionEvent event) {
        try {
            Emprestimo emprestimoSelecionado = tabelaEmprestimos.getSelectionModel().getSelectedItem();
            if (emprestimoSelecionado != null && emprestimoSelecionado.getStatus() != Status.DEVOLVIDO ) {

                Livro livroSelecionado = buscarLivroPorId(emprestimoSelecionado.getLivroId());
                livroSelecionado.setDisponivel(true);
                livroDAO.salvarDados();// Atualiza os dados do livro no DAO

                emprestimoSelecionado.setStatus(Status.DEVOLVIDO);
                emprestimoSelecionado.setDataDevolucao(LocalDate.now());
                emprestimoDAO.salvarDados();
                carregarDadosTabela();
                tabelaEmprestimos.refresh();
                System.out.println("Devolução registrada com sucesso!");
            } else {
                System.out.println("Selecione um empréstimo válido para registrar a devolução.");
            }
        } catch (Exception e) {
            System.err.println("Erro ao registrar devolução: " + e.getMessage());
        }
    }

    public Livro buscarLivroPorId(int livroId) {
        // Supondo que livrosDAO seja uma lista ou banco de dados com todos os livros
        for (Livro livro : livroDAO.getLivros()) {
            if (livro.getId() == livroId) {
                return livro;
            }
        }
        return null; // Retorna null caso o livro não seja encontrado
    }

    @FXML
    private void telaMenu(ActionEvent event) {
        trocarTela(event, "/com/example/bibliotecafx/menu_inicial.fxml", "Menu Inicial");
    }

    private void trocarTela(ActionEvent event, String fxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao trocar de tela: " + e.getMessage());
        }
    }
}