package com.example.norto.trabalhofrete.Model;

import java.io.Serializable;
import java.util.List;

    public class Cidade implements Serializable {
    private int id;
    private String nome;
    private List<Integer> cep;

    public Cidade() {
    }

    public Cidade(int id, String nome, List<Integer> cep) {
        this.id = id;
        this.nome = nome;
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Integer> getCep() {
        return cep;
    }

    public void setCep(List<Integer> cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
