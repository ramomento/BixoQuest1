package com.bixoquest.model;

public class ConsultarProgresso extends Acao {

    @Override
    public void executarAcao(Aluno aluno, Jogo jogo) {
        int aprovadosHardware = 0;
        int aprovadosSoftware = 0;

        System.out.println("=== Progresso Acadêmico ===");
        for (Disciplina d : jogo.getDisciplinasDisponiveis()) {
            System.out.println(d.getNome() + " | " + d.getEstado() + " | Média: " + d.calcularMedia());
            if (d.getEstado() == com.bixoquest.enums.EstadoDisciplina.APROVADO) {
                if (d.getTipo() == com.bixoquest.enums.TipoDisciplina.HARDWARE) aprovadosHardware++;
                else aprovadosSoftware++;
            }
        }
        System.out.println("Hardware: " + aprovadosHardware + "/5");
        System.out.println("Software: " + aprovadosSoftware + "/5");
    }

    @Override
    public boolean podeExecutar(Aluno aluno) {
        return true;
    }
}
