package com.bixoquest.persistence;

import com.bixoquest.model.*;
import com.bixoquest.enums.*;

import java.io.*;
import java.util.*;

public class GerenciadorDeSaves {
    private static final String PASTA_SAVES = "saves/";
    private static final int TOTAL_SLOTS = 3;

    // construtor privado impede instanciação — classe utilitária estática
    private GerenciadorDeSaves() {}

    public static boolean slotDisponivel(int slot) {
        File arquivo = new File(PASTA_SAVES + "save" + slot + ".txt");
        return !arquivo.exists() || arquivo.length() == 0;
    }

    public static void salvar(Jogo jogo, int slot) {
        File pasta = new File(PASTA_SAVES);
        if (!pasta.exists()) pasta.mkdirs();

        File arquivo = new File(PASTA_SAVES + "save" + slot + ".txt");
        System.out.println("[SAVE DEBUG] Caminho completo: " + arquivo.getAbsolutePath());

        // Se arquivo existe, deleta antes
        if (arquivo.exists()) {
            boolean deleted = arquivo.delete();
            System.out.println("[SAVE DEBUG] Arquivo deletado: " + deleted);
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(arquivo))) {

            System.out.println("[SAVE DEBUG] Escrevendo em: " + arquivo.getName());

            Aluno aluno = jogo.getAluno();

            // atributos do aluno
            writer.write("energia=" + aluno.getEnergia()); writer.newLine();
            writer.write("saude=" + aluno.getSaude()); writer.newLine();
            writer.write("motivacao=" + aluno.getMotivacao()); writer.newLine();
            writer.write("dinheiro=" + aluno.getDinheiro()); writer.newLine();
            writer.write("conhecimentoTeorico=" + aluno.getConhecimentoTeorico()); writer.newLine();
            writer.write("conhecimentoPratico=" + aluno.getConhecimentoPratico()); writer.newLine();
            writer.write("desempenhoAcademico=" + aluno.getDesempenhoAcademico()); writer.newLine();
            writer.write("progressoHardware=" + aluno.getProgressoHardware()); writer.newLine();
            writer.write("progressoSoftware=" + aluno.getProgressoSoftware()); writer.newLine();
            writer.write("conselhosUsados=" + aluno.getConselhosUsados()); writer.newLine();

            // estado do jogo e semestre
            writer.write("turnoAtual=" + jogo.getTurnoAtual()); writer.newLine();
            writer.write("semestreNumero=" + jogo.getSemestreAtual().getNumero()); writer.newLine();
            writer.write("semestreTurnoAtual=" + jogo.getSemestreAtual().getTurnoAtual()); writer.newLine();

            // disciplinas — índice fixo garante ordem consistente na leitura
            List<Disciplina> disciplinas = jogo.getDisciplinasDisponiveis();
            for (int i = 0; i < disciplinas.size(); i++) {
                Disciplina d = disciplinas.get(i);
                writer.write("disciplina_" + i + "_estado=" + d.getEstado()); writer.newLine();

                StringBuilder notas = new StringBuilder();
                for (int j = 0; j < d.getNotas().size(); j++) {
                    if (j > 0) notas.append(",");
                    notas.append(d.getNotas().get(j));
                }
                writer.write("disciplina_" + i + "_notas=" + notas); writer.newLine();
            }

            // eventos fixos
            List<Evento> eventosFixos = jogo.getSistemaDeEventos().getEventosFixos();
            for (int i = 0; i < eventosFixos.size(); i++) {
                Evento e = eventosFixos.get(i);
                writer.write("evento_" + i + "_turno=" + e.getTurnoAplicacao()); writer.newLine();
                if (e instanceof EventoProva) {
                    writer.write("evento_" + i + "_realizada=" +
                            ((EventoProva) e).isRealizada()); writer.newLine();
                }
            }

            System.out.println("[SAVE DEBUG] Escrita concluída!");
        } catch (IOException e) {
            System.err.println("[SAVE ERROR] Erro ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Jogo carregar(int slot) {
        File arquivo = new File(PASTA_SAVES + "save" + slot + ".txt");
        if (!arquivo.exists()) return null;

        // lê todas as linhas para um Map antes de processar
        Map<String, String> dados = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // split com limite 2 preserva valores que contenham "=" (ex: notas decimais)
                String[] partes = linha.split("=", 2);
                if (partes.length == 2) dados.put(partes[0], partes[1]);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
            return null;
        }

        Jogo jogo = new Jogo();
        Aluno aluno = jogo.getAluno();

        // restaura energia: zera primeiro, depois reconstrói pelo valor salvo
        // reduzirEnergia com valor negativo incrementa (energia -= custo, custo < 0)
        aluno.reduzirEnergia(10);
        aluno.reduzirEnergia(-Integer.parseInt(dados.get("energia")));

        // restaura atributos com setters diretos
        aluno.alterarSaude(EstadoSaude.valueOf(dados.get("saude")));
        aluno.alterarMotivacao(EstadoMotivacao.valueOf(dados.get("motivacao")));
        aluno.setConselhosUsados(Integer.parseInt(dados.get("conselhosUsados")));
        aluno.setDesempenhoAcademico(Integer.parseInt(dados.get("desempenhoAcademico")));
        aluno.setProgressoHardware(Integer.parseInt(dados.get("progressoHardware")));
        aluno.setProgressoSoftware(Integer.parseInt(dados.get("progressoSoftware")));

        // restaura dinheiro: zera o saldo inicial (200) e reconstrói
        aluno.gastarDinheiro(aluno.getDinheiro());
        aluno.adicionarDinheiro(Double.parseDouble(dados.get("dinheiro")));

        // conhecimento começa em 0 no novo Aluno — soma direta funciona
        aluno.aumentarConhecimentoTeorico(Integer.parseInt(dados.get("conhecimentoTeorico")));
        aluno.aumentarConhecimentoPratico(Integer.parseInt(dados.get("conhecimentoPratico")));

        // restaura estado e notas de cada disciplina
        List<Disciplina> disciplinas = jogo.getDisciplinasDisponiveis();
        for (int i = 0; i < disciplinas.size(); i++) {
            Disciplina d = disciplinas.get(i);
            EstadoDisciplina estadoSalvo =
                    EstadoDisciplina.valueOf(dados.get("disciplina_" + i + "_estado"));

            String notasStr = dados.get("disciplina_" + i + "_notas");
            if (notasStr != null && !notasStr.isEmpty()) {
                // adicionarNota() exige estado CURSANDO — seta temporariamente
                d.setEstado(EstadoDisciplina.CURSANDO);
                for (String nota : notasStr.split(",")) {
                    d.adicionarNota(Double.parseDouble(nota));
                }
            }
            // aplica estado correto após inserir as notas
            d.setEstado(estadoSalvo);
        }

        // reconstrói o semestre com as disciplinas que estavam em CURSANDO no save
        List<Disciplina> disciplinasDeSemestre = new ArrayList<>();
        for (Disciplina d : disciplinas) {
            if (d.getEstado() == EstadoDisciplina.CURSANDO) {
                disciplinasDeSemestre.add(d);
            }
        }

        int semestreNumero = Integer.parseInt(dados.get("semestreNumero"));
        int semestreTurno = Integer.parseInt(dados.get("semestreTurnoAtual"));

        Semestre semestre = new Semestre(semestreNumero, disciplinasDeSemestre);
        semestre.setTurnoAtual(semestreTurno);
        jogo.setSemestreAtual(semestre);

        // restaura o turno global do jogo por último
        jogo.setTurnoAtual(Integer.parseInt(dados.get("turnoAtual")));

        return jogo;
    }
}