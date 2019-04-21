package com.example.norto.trabalhofrete.Model;

import java.io.Serializable;
import java.util.List;

public class Estado implements Serializable {
    private int id;
    private String sigla;
    private String descricao;
    private List<Cidade> cidadeList;

    public Estado(int id, String sigla, String descricao, List<Cidade> cidadeList) {
        this.id = id;
        this.sigla = sigla;
        this.descricao = descricao;
        this.cidadeList = cidadeList;
    }

    public Estado() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Cidade> getCidadeList() {
        return cidadeList;
    }

    public void setCidadeList(List<Cidade> cidadeList) {
        this.cidadeList = cidadeList;
    }

    @Override
    public String toString() {
        return id + " - " + sigla + " - " + descricao;
    }
}
