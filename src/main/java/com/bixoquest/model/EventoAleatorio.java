package com.bixoquest.model;

import java.util.Random;

public class EventoAleatorio extends Evento {

    @Override
    public void aplicar(Aluno aluno, Jogo jogo) {
        Random random = new Random();
        // 0 = energia, 1 = motivação, 2 = dinheiro
        int efeito = random.nextInt(3);

        switch (efeito) {
            case 0 -> aluno.reduzirEnergia(2);
            case 1 -> aluno.reduzirMotivacao();
            case 2 -> aluno.gastarDinheiro(20.0);
        }
    }
}