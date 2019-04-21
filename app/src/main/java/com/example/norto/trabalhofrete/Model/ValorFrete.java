package com.example.norto.trabalhofrete.Model;

public class ValorFrete {
    private Integer id;
    private Estado ufOrigem;
    private Estado ufDestino;
    private double valorFrete;

    public ValorFrete(Integer id, Estado ufOrigem, Estado ufDestino, double valorFrete) {
        this.id = id;
        this.ufOrigem = ufOrigem;
        this.ufDestino = ufDestino;
        this.valorFrete = valorFrete;
    }

    public ValorFrete() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Estado getUfOrigem() {
        return ufOrigem;
    }

    public void setUfOrigem(Estado ufOrigem) {
        this.ufOrigem = ufOrigem;
    }

    public Estado getUfDestino() {
        return ufDestino;
    }

    public void setUfDestino(Estado ufDestino) {
        this.ufDestino = ufDestino;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }
}
