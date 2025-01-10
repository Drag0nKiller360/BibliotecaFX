package com.example.bibliotecafx.persistencia;

import com.example.bibliotecafx.models.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public static void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS livros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                autor TEXT NOT NULL,
                genero TEXT,
                disponivel BOOLEAN NOT NULL
            );
        """;

        try (Connection conn = BancoDeDados.conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de livros: " + e.getMessage());
        }
    }

    public static void inserirLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor, genero, disponivel) VALUES (?, ?, ?, ?)";

        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getGenero());
            pstmt.setBoolean(4, livro.isDisponivel());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir livro: " + e.getMessage());
        }
    }

    public static List<Livro> listarLivros() {
        String sql = "SELECT * FROM livros";
        List<Livro> livros = new ArrayList<>();

        try (Connection conn = BancoDeDados.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getBoolean("disponivel")
                );
                livros.add(livro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar livros: " + e.getMessage());
        }

        return livros;
    }

    public static void atualizarLivro(Livro livro) {
        String sql = "UPDATE livros SET titulo = ?, autor = ?, genero = ?, disponivel = ? WHERE id = ?";

        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, livro.getTitulo());
            pstmt.setString(2, livro.getAutor());
            pstmt.setString(3, livro.getGenero());
            pstmt.setBoolean(4, livro.isDisponivel());
            pstmt.setInt(5, livro.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar livro: " + e.getMessage());
        }
    }

    public static void removerLivro(int id) {
        String sql = "DELETE FROM livros WHERE id = ?";

        try (Connection conn = BancoDeDados.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao remover livro: " + e.getMessage());
        }
    }
}
