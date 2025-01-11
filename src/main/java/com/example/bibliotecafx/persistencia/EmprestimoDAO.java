package com.example.bibliotecafx.persistencia;

import com.example.bibliotecafx.models.*;
import java.io.*;
import java.util.HashSet;

public class EmprestimoDAO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private static EmprestimoDAO instance;
    @SuppressWarnings("FieldMayBeFinal")
    private HashSet<Emprestimo> emprestimos;

    // Caminho do arquivo para salvar os dados
    private static final String FILE_PATH = "src/main/java/com/example/bibliotecafx/persistencia/bin/emprestimos.bin";

    private EmprestimoDAO() {
        // Inicializa a lista de Empréstimos
        this.emprestimos = new HashSet<>();
    }

    public static EmprestimoDAO getInstance() {
        if (instance == null) {
            instance = carregarDados(); // Tenta carregar os dados do arquivo
            if (instance == null) {
                instance = new EmprestimoDAO(); // Cria uma nova instância se o arquivo não existir
            }
        }
        return instance;
    }

    public HashSet<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.add(emprestimo);
    }

    public void removerEmprestimo(Emprestimo emprestimo) {
        this.emprestimos.remove(emprestimo);
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
    private static EmprestimoDAO carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (EmprestimoDAO) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Criando um novo banco de dados.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return null;
    }
}
