package com.example.bibliotecafx.models;

public enum Status {
    DEVOLVIDO(1, "Devolvido"),
    EMPRESTADO(0, "Emprestado"),
    ATRASADO(-1, "Atrasado");

    private final int codigo;
    private final String descricao;

    // Construtor do enum
    Status(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    // Método para obter o código associado
    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    // Método para obter o enum a partir do código
    public static Status fromCodigo(int codigo) {
        for (Status status : Status.values()) {
            if (status.getCodigo() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para Status: " + codigo);
    }
}
