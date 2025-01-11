package com.example.bibliotecafx.persistencia;

import com.example.bibliotecafx.models.*;
import java.io.*;
import java.util.HashSet;

public class VisitanteDAO implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private static VisitanteDAO instance;
    @SuppressWarnings("FieldMayBeFinal")
    private HashSet<Visitante> visitantes;

    // Caminho do arquivo para salvar os dados
    private static final String FILE_PATH= "src/main/java/com/example/bibliotecafx/persistencia/bin/visitantes.bin";

    private VisitanteDAO() {
        //Inicializa a lista de Visitantes
        this.visitantes = new HashSet<>();
    }

    public static VisitanteDAO getInstance() {
        if (instance == null) {
            instance = carregarDados(); // Tenta carregar os dados do arquivo
            if (instance == null) {
                instance = new VisitanteDAO(); // Cria uma nova instância se o arquivo não existir
            }
        }
        return instance;
    }

    public HashSet<Visitante> getVisitantes() {
        return visitantes;
    }

    public void adicionarVisitante(Visitante visitante) {
        this.visitantes.add(visitante);
    }

    public void removerVisitante(Visitante visitante) {
        this.visitantes.remove(visitante);
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
    private static VisitanteDAO carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (VisitanteDAO) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado. Criando um novo banco de dados.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return null;
    }
}
