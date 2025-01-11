package com.example.bibliotecafx.persistencia;

import com.example.bibliotecafx.models.*;
import java.io.*;
import java.util.HashSet;

public class AlunoDAO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private static AlunoDAO instance;
    @SuppressWarnings("FieldMayBeFinal")
    private HashSet<Aluno> alunos;

    // Caminho do arquivo para salvar os dados
    private static final String FILE_PATH = "src/main/java/com/example/bibliotecafx/persistencia/bin/alunos.bin";

    private AlunoDAO() {
        // Inicializa a lista de Alunos
        this.alunos = new HashSet<>();
    }

    public static AlunoDAO getInstance() {
        if (instance == null) {
            instance = carregarDados(); // Tenta carregar os dados do arquivo
            if (instance == null) {
                instance = new AlunoDAO(); // Cria uma nova instância se o arquivo não existir
            }
        }
        return instance;
    }

    public HashSet<Aluno> getAlunos() {
        return alunos;
    }

    public void adicionarAluno(Aluno aluno) {
        this.alunos.add(aluno);
    }

    public void removerAluno(Aluno aluno) {
        this.alunos.remove(aluno);
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
    private static AlunoDAO carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (AlunoDAO) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Criando um novo banco de dados.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return null;
    }
}
