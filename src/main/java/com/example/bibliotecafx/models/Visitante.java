package com.example.bibliotecafx.models;

public class Visitante extends Usuario {
    private String cpf;
    private String email;

    public Visitante(int id, String nome, String cpf, String email) {
        super(id, nome);
        this.cpf = cpf;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
