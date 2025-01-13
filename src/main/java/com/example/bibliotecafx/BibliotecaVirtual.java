package com.example.bibliotecafx;

import com.example.bibliotecafx.persistencia.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class BibliotecaVirtual extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        LivroDAO livroDAO = LivroDAO.getInstance();
        AlunoDAO alunoDAO  = AlunoDAO.getInstance();
        VisitanteDAO visitanteDAO = VisitanteDAO.getInstance();
        EmprestimoDAO emprestimoDAO = EmprestimoDAO.getInstance();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> livroDAO.salvarDados()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> alunoDAO.salvarDados()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> visitanteDAO.salvarDados()));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> emprestimoDAO.salvarDados()));

        // Carregar a tela inicial
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu_inicial.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Gerenciamento de Biblioteca Virtual");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
