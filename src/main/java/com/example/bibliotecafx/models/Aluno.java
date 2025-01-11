package com.example.bibliotecafx.models;

public class Aluno extends Usuario{
    private String matricula;
    private String emailInstitucional;

    public Aluno(int id, String nome, String matricula, String emailInstitucional) {
        super(id, nome);
        this.matricula = matricula;
        this.emailInstitucional = emailInstitucional;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEmailInstitucional() {
        return emailInstitucional;
    }

    public void setEmailInstitucional(String emailInstitucional) {
        this.emailInstitucional = emailInstitucional;
    }
}
