package com.example.bibliotecafx.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {

    private static final String URL = "jdbc:sqlite:mydb.db"; // Use SQLite como exemplo
    private static Connection conexao;

    public static Connection conectar() {
        if (conexao == null) {
            try {
                conexao = DriverManager.getConnection(URL);
                System.out.println("Conexão com o banco de dados estabelecida.");
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            }
        }
        return conexao;
    }

    public static void desconectar() {
        if (conexao != null) {
            try {
                conexao.close();
                conexao = null;
                System.out.println("Conexão com o banco de dados encerrada.");
            } catch (SQLException e) {
                System.err.println("Erro ao desconectar do banco de dados: " + e.getMessage());
            }
        }
    }
}