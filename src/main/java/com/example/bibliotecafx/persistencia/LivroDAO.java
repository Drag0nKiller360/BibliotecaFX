package com.example.bibliotecafx.persistencia;

import com.example.bibliotecafx.models.*;
import java.io.*;
import java.util.ArrayList;

public class LivroDAO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private static LivroDAO instance;
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<Livro> livros;

    // Caminho do arquivo para salvar os dados
    private static final String FILE_PATH = "src/main/java/com/example/bibliotecafx/persistencia/bin/livros.bin";

    private LivroDAO() {
        // Inicializa a lista de Livros
        this.livros = new ArrayList<>();
    }

    public static LivroDAO getInstance() {
        if (instance == null) {
            instance = carregarDados(); // Tenta carregar os dados do arquivo
            if (instance == null) {
                instance = new LivroDAO(); // Cria uma nova instância se o arquivo não existir
            }
        }
        return instance;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void adicionarLivro(Livro livro) {
        this.livros.add(livro);
    }

    public void removerLivro(Livro livro) {
        this.livros.remove(livro);
    }

    // Método para salvar os dados nos arquivos
    public void salvarDados() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                oos.writeObject(instance);
            } catch (IOException e) {
                System.err.println("Erro ao salvar os dados: " + e.getMessage());
            }
    }

    //Método para carregar os dados dos arquivos
    private static LivroDAO carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (LivroDAO) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Criando um novo banco de dados.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return null;
    }
}
