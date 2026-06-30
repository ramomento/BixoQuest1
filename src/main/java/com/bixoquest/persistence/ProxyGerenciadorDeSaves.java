package com.bixoquest.persistence;

import com.bixoquest.model.Jogo;
import java.util.HashMap;
import java.util.Map;

/**
 * Proxy para GerenciadorDeSaves.
 * Adiciona validação, cache e logging às operações de save/load.
 */
public class ProxyGerenciadorDeSaves {

    // Cache de saves carregados
    private static final Map<Integer, Jogo> cache = new HashMap<>();

    /**
     * Salva um jogo validando dados primeiro.
     */
    public static void salvar(Jogo jogo, int slot) {
        // Validação
        if (jogo == null) {
            System.err.println("[PROXY] Erro: Jogo não pode ser null");
            return;
        }

        if (slot < 0 || slot > 2) {
            System.err.println("[PROXY] Erro: Slot inválido (deve ser 0-2)");
            return;
        }

        if (jogo.getAluno() == null) {
            System.err.println("[PROXY] Erro: Aluno não pode ser null");
            return;
        }

        // Logging
        System.out.println("[PROXY] Salvando jogo no slot " + slot);

        // Delegação
        GerenciadorDeSaves.salvar(jogo, slot);

        // Atualizar cache
        cache.put(slot, jogo);
        System.out.println("[PROXY] Jogo em cache (slot " + slot + ")");
    }

    /**
     * Carrega um jogo com cache.
     * Se já foi carregado, retorna do cache; senão, carrega do arquivo.
     */
    public static Jogo carregar(int slot) {
        // Validação
        if (slot < 0 || slot > 2) {
            System.err.println("[PROXY] Erro: Slot inválido (deve ser 0-2)");
            return null;
        }

        // Verificar cache
        if (cache.containsKey(slot)) {
            System.out.println("[PROXY] Jogo carregado do CACHE (slot " + slot + ")");
            return cache.get(slot);
        }

        // Logging
        System.out.println("[PROXY] Carregando jogo do arquivo (slot " + slot + ")");

        // Delegação
        Jogo jogo = GerenciadorDeSaves.carregar(slot);

        // Armazenar no cache se carregou com sucesso
        if (jogo != null) {
            cache.put(slot, jogo);
            System.out.println("[PROXY] Jogo armazenado em cache");
        } else {
            System.out.println("[PROXY] Nenhuma partida encontrada no slot " + slot);
        }

        return jogo;
    }

    /**
     * Verifica se um slot tem dados.
     */
    public static boolean slotDisponivel(int slot) {
        return GerenciadorDeSaves.slotDisponivel(slot);
    }

    /**
     * Limpa o cache (útil quando voltando ao menu).
     */
    public static void limparCache() {
        cache.clear();
        System.out.println("[PROXY] Cache limpo");
    }
}