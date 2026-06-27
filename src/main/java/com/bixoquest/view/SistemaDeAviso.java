package com.bixoquest.view;

import java.util.ArrayList;
import java.util.List;

public class SistemaDeAviso {

    public enum TipoAviso {
        TEXTO,      // mensagem de feedback
        ITENS,      // exibição de itens da cantina
        AVISOS      // avisos/notificações
    }

    private String textoAviso;
    private TipoAviso tipoAviso;
    private List<Item> itensDisponiveis;
    private int itemSelecionado;

    public SistemaDeAviso() {
        this.textoAviso = "";
        this.tipoAviso = TipoAviso.TEXTO;
        this.itensDisponiveis = new ArrayList<>();
        this.itemSelecionado = 0;
    }

    // ===== GERENCIAMENTO DE AVISOS =====

    public void exibirMensagem(String mensagem) {
        this.textoAviso = mensagem;
        this.tipoAviso = TipoAviso.TEXTO;
    }

    public void exibirAviso(String aviso) {
        this.textoAviso = "⚠️ " + aviso;
        this.tipoAviso = TipoAviso.AVISOS;
    }

    public void exibirItens(List<Item> itens) {
        this.itensDisponiveis = itens;
        this.tipoAviso = TipoAviso.ITENS;
        this.itemSelecionado = 0;
    }

    // ===== NAVEGAÇÃO DE ITENS =====

    public void proximoItem() {
        if (itemSelecionado < itensDisponiveis.size() - 1) {
            itemSelecionado++;
        }
    }

    public void itemAnterior() {
        if (itemSelecionado > 0) {
            itemSelecionado--;
        }
    }

    public Item obterItemSelecionado() {
        if (itensDisponiveis.isEmpty()) return null;
        return itensDisponiveis.get(itemSelecionado);
    }

    // ===== GETTERS =====

    public String getTextoAviso() { return textoAviso; }
    public TipoAviso getTipoAviso() { return tipoAviso; }
    public List<Item> getItensDisponiveis() { return itensDisponiveis; }
    public int getItemSelecionado() { return itemSelecionado; }

    // ===== CLASSE INTERNA ITEM =====

    public static class Item {
        private String nome;
        private double preco;
        private String caminhoSprite;

        public Item(String nome, double preco, String caminhoSprite) {
            this.nome = nome;
            this.preco = preco;
            this.caminhoSprite = caminhoSprite;
        }

        public String getNome() { return nome; }
        public double getPreco() { return preco; }
        public String getCaminhoSprite() { return caminhoSprite; }
    }
}