package com.example.bibliotecafx.models;

import java.util.Date;
import java.util.HashSet;

public class Emprestimo {

    private int id;
    private Date dataEmprestimo;
    private Date dataPrevisaoDevolucao;
    private Date dataRealDevolucao;
    private int usuarioId;
    private HashSet<Integer> livroId;

    public Emprestimo(int id, Date dataEmprestimo, Date dataPrevisaoDevolucao, Date dataRealDevolucao, int usuarioId, HashSet<Integer> livroId) {
        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevisaoDevolucao = dataPrevisaoDevolucao;
        this.dataRealDevolucao = dataRealDevolucao;
        this.usuarioId = usuarioId;
        this.livroId = livroId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataPrevisaoDevolucao() {
        return dataPrevisaoDevolucao;
    }

    public void setDataPrevisaoDevolucao(Date dataPrevisaoDevolucao) {
        this.dataPrevisaoDevolucao = dataPrevisaoDevolucao;
    }

    public Date getDataRealDevolucao() {
        return dataRealDevolucao;
    }

    public void setDataRealDevolucao(Date dataRealDevolucao) {
        this.dataRealDevolucao = dataRealDevolucao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public HashSet<Integer> getLivroId() {
        return livroId;
    }

    public void setLivroId(HashSet<Integer> livroId) {
        this.livroId = livroId;
    }
}