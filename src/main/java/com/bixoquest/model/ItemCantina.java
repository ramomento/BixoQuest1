package com.bixoquest.model;

public class ItemCantina {
    private String nome;
    private double preco;
    private int quantidadeDisponivel;

    public ItemCantina(String nome, double preco, int quantidadeDisponivel) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public boolean estaDisponivel() { return quantidadeDisponivel > 0; }
    public void consumir() { if (estaDisponivel()) quantidadeDisponivel--; }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
}