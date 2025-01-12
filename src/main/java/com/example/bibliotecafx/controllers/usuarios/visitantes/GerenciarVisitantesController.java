package com.example.bibliotecafx.controllers.usuarios.visitantes;

import com.example.bibliotecafx.models.Visitante;
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

public class GerenciarVisitantesController {
    @FXML
    private TableView<Visitante> tabelaVisitantes;

    @FXML
    private TableColumn<Visitante, Integer> colunaId;

    @FXML
    private TableColumn<Visitante, String> colunaNome;

    @FXML
    private TableColumn<Visitante, String> colunaCpf;

    @FXML
    private TableColumn<Visitante, String> colunaEmail;

    private VisitanteDAO visitanteDAO;

    @FXML
    public void initialize() {
        visitanteDAO = VisitanteDAO.getInstance(); // Carrega o DAO
        configurarColunas();
        carregarDadosTabela();
    }

    //Métodos
    private void configurarColunas() {
        // Associa as colunas aos atributos da classe Visitante
        colunaId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colunaCpf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCpf()));
        colunaEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
    }

    private void carregarDadosTabela(){
        // Carrega os com.example.bibliotecafx.controllers.visitantes do DAO para a tabela
        ObservableList<Visitante> visitantes = FXCollections.observableArrayList(visitanteDAO.getVisitantes());
        tabelaVisitantes.setItems(visitantes);
    }

    @FXML
    private void editarVisitante() {
        try {
            Visitante visitanteSelecionado = tabelaVisitantes.getSelectionModel().getSelectedItem();
            if (visitanteSelecionado != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/bibliotecafx/editar_visitante.fxml"));
                Parent root = loader.load();

                EditarVisitanteController editarVisitanteController = loader.getController();
                editarVisitanteController.setVisitante(visitanteSelecionado);
                editarVisitanteController.setOnSaveCallback(() -> atualizarTabela());

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Editar Visitante");
                stage.show();
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar a janela de edição: " + e.getMessage());
        }
    }

    @FXML
    private void removerVisitante() {
        Visitante visitanteSelecionado = tabelaVisitantes.getSelectionModel().getSelectedItem();
        if (visitanteSelecionado != null) {
            tabelaVisitantes.getItems().remove(visitanteSelecionado);
            visitanteDAO.removerVisitante(visitanteSelecionado);
            System.out.println("Visitante removido: " + visitanteSelecionado.getNome());
        } else {
            System.out.println("Nenhum visitante selecionado!");
        }
    }

    @FXML
    private void telaAdicionarVisitante(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        trocarTela("/com/example/bibliotecafx/adicionar_visitante.fxml", "Cadastrar Visitante", stage);
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
        tabelaVisitantes.refresh();
    }

}