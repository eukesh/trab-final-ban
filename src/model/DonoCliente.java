package model;

import database.EnderecoDAO;

import java.util.Objects;

public class DonoCliente {
    private int cpf;
    private String nome;
    private int telefone;
    private Endereco endereco;

    public DonoCliente() {
    }

    public DonoCliente(int cpf, String nome, int telefone, Endereco endereco){
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public int getCpf() {
        return this.cpf;
    }
    public void setCpf(int cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getTelefone() {
        return this.telefone;
    }
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
    public Endereco getEndereco() {
        return this.endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonoCliente that = (DonoCliente) o;
        return cpf == that.cpf;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public String toString() {
        return "DonoCliente{" +
                "cpf=" + cpf +
                ", nome='" + nome + '\'' +
                ", telefone=" + telefone +
                ", endereco=" + endereco +
                '}';
    }
}
