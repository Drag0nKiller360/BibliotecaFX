package com.example.bibliotecafx.controllers.emprestimos;

import com.example.bibliotecafx.models.*;
import com.example.bibliotecafx.persistencia.AlunoDAO;
import com.example.bibliotecafx.persistencia.EmprestimoDAO;
import com.example.bibliotecafx.persistencia.LivroDAO;
import com.example.bibliotecafx.persistencia.VisitanteDAO;
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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RegistrarEmprestimoController {

    @FXML
    private TableView<Usuario> tabelaUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> colunaUsuarioId;

    @FXML
    private TableColumn<Usuario, String> colunaUsuarioNome;

    @FXML
    private TableView<Livro> tabelaLivros;

    @FXML
    private TableColumn<Livro, Integer> colunaLivroId;

    @FXML
    private TableColumn<Livro, String> colunaLivroTitulo;

    @FXML
    private DatePicker campoDataEmprestimo;

    private final EmprestimoDAO emprestimoDAO = EmprestimoDAO.getInstance();
    private final AlunoDAO alunoDAO = AlunoDAO.getInstance();
    private final VisitanteDAO visitanteDAO = VisitanteDAO.getInstance();
    private final LivroDAO livroDAO = LivroDAO.getInstance();

    @FXML
    public void initialize() {
        configurarTabelas();
        carregarDados();
    }

    private void configurarTabelas() {
        // Configuração da tabela de usuários
        colunaUsuarioId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colunaUsuarioNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));

        // Configuração da tabela de livros
        colunaLivroId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colunaLivroTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
    }

    private void carregarDados() {
        // Unir Alunos e Visitantes em um único array e ordenar pelo ID
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(alunoDAO.getAlunos());
        usuarios.addAll(visitanteDAO.getVisitantes());

        usuarios = usuarios.stream()
                .sorted(Comparator.comparingInt(Usuario::getId))
                .collect(Collectors.toList());

        // Carregar usuários na tabela
        ObservableList<Usuario> usuariosObservables = FXCollections.observableArrayList(usuarios);
        tabelaUsuarios.setItems(usuariosObservables);

        // Carregar livros disponíveis na tabela
        List<Livro> livrosDisponiveis = livroDAO.getLivros().stream()
                .filter(Livro::isDisponivel) // Apenas livros disponíveis
                .collect(Collectors.toList());
        ObservableList<Livro> livrosObservables = FXCollections.observableArrayList(livrosDisponiveis);
        tabelaLivros.setItems(livrosObservables);
    }

    @FXML
    private void registrarEmprestimo(ActionEvent event) {
        Usuario usuarioSelecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();
        Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
        LocalDate dataEmprestimo = campoDataEmprestimo.getValue();


        if (usuarioSelecionado == null || livroSelecionado == null || dataEmprestimo == null) {
            mostrarAlerta("Erro", "Selecione um usuário, um livro e a data de empréstimo.");
            return;
        }

        // Registrar o empréstimo
        livroSelecionado.setDisponivel(false); // Marca o livro como indisponível
        livroDAO.salvarDados();// Atualiza os dados do livro no DAO

        int id = 1;
        LocalDate dataPrevistaDevolucao = dataEmprestimo.plusDays(15);

        if(!emprestimoDAO.getEmprestimos().isEmpty()){
            id = emprestimoDAO.getEmprestimos().getLast().getId() + 1;
        }

        Emprestimo novoEmprestimo = new Emprestimo(id, dataEmprestimo, null, dataPrevistaDevolucao, Status.EMPRESTADO, usuarioSelecionado.getId(), livroSelecionado.getId());
        emprestimoDAO.adicionarEmprestimo(novoEmprestimo);
        
        mostrarAlerta("Sucesso", "Empréstimo registrado com sucesso!");
        carregarDados(); // Atualiza os dados das tabelas
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/gerenciar_emprestimos.fxml", "Gerenciar Emprestimos", stage);
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

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
