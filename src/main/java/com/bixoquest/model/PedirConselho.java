package com.bixoquest.model;

import java.util.List;

public class PedirConselho extends Acao {

    private static final List<String> CONSELHOS = List.of(
            "Gerencie bem seu dinheiro, as despesas aparecem quando menos se espera!",
            "Você pode estudar na sala de aula ou no laboratório para ganhar conhecimento.",
            "Fique de olho na sua energia — sem ela você não consegue realizar ações.",
            "Vez ou outra você pode ficar doente. Cuide da sua saúde!",
            "O colegiado pode te orientar sobre seu progresso no curso.",
            "Lembre-se de pegar o ônibus para finalizar o turno."
    );

    private String ultimoConselhoSorteado;  // ← Armazenar conselho sorteado

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        if (!podeExecutar(aluno)) return;

        // sorteia um conselho aleatório
        int indice = (int) (Math.random() * CONSELHOS.size());
        ultimoConselhoSorteado = CONSELHOS.get(indice);

        System.out.println("Conselho sorteado: " + ultimoConselhoSorteado);

        aluno.incrementarConselhosUsados();
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        // limite de 3 conselhos por turno
        return aluno.getConselhosUsados() < 3;
    }

    /**
     * Retorna o último conselho sorteado.
     */
    public String getUltimoConselho() {
        return ultimoConselhoSorteado;
    }
}