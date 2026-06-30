package com.bixoquest.view;

import com.bixoquest.view.SistemaDeAviso;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class PopUpView {

    private Pane pane;
    private ImageView fundo;
    private String titulo;
    private String conteudo;
    private String[] opcoes;
    private int opcaoSelecionada = 0;
    private List<Text> textosOpcoes = new ArrayList<>();
    private List<SistemaDeAviso.Item> itens = new ArrayList<>();
    private int itemSelecionado = 0;
    private boolean exibeItens = false;

    // Sistema de paginação de conteúdo
    private List<String> paginas = new ArrayList<>();
    private int paginaAtual = 0;
    private boolean usaPaginacao = false;

    private static final double POS_X = 100;
    private static final double POS_Y = 55;
    private static final double LARGURA = 600;
    private static final double ALTURA = 360;

    public PopUpView() {
        this.pane = new Pane();
        this.titulo = "";
        this.conteudo = "";
        this.opcoes = new String[]{"OK"};
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
        // Detectar automaticamente se precisa paginação
        this.paginas = dividirEmPaginas(conteudo);
        this.paginaAtual = 0;
        this.usaPaginacao = paginas.size() > 1;
    }

    /**
     * Divide o conteúdo em páginas (máx 2-3 disciplinas por página).
     * Cada disciplina termina com "\n\n", então usamos isso como divisor.
     */
    private List<String> dividirEmPaginas(String conteudo) {
        List<String> resultado = new ArrayList<>();
        String[] linhas = conteudo.split("\n");

        StringBuilder paginaAtual = new StringBuilder();
        int linhasNaPagina = 0;
        int maxLinhasPorPagina = 8; // ~2-3 disciplinas

        for (String linha : linhas) {
            paginaAtual.append(linha).append("\n");
            linhasNaPagina++;

            // Se atingiu o limite e é fim de disciplina (linha vazia)
            if (linhasNaPagina >= maxLinhasPorPagina && linha.trim().isEmpty()) {
                resultado.add(paginaAtual.toString().trim());
                paginaAtual = new StringBuilder();
                linhasNaPagina = 0;
            }
        }

        // Adicionar última página
        if (paginaAtual.length() > 0) {
            resultado.add(paginaAtual.toString().trim());
        }

        // Se ficou vazio, adicionar conteúdo original
        if (resultado.isEmpty()) {
            resultado.add(conteudo);
        }

        return resultado;
    }

    public void setOpcoes(String[] opcoes) {
        this.opcoes = opcoes;
        this.opcaoSelecionada = 0;
    }

    public void setItens(List<SistemaDeAviso.Item> itens) {
        this.itens = itens;
        this.itemSelecionado = 0;
        this.exibeItens = true;
    }

    public Pane criarPopUp() {
        pane.setPrefSize(LARGURA, ALTURA);
        pane.setLayoutX(POS_X);
        pane.setLayoutY(POS_Y);

        // Fundo do pop-up
        fundo = carregarImagem("/images/ui/popUp.png", 0, 0, LARGURA, ALTURA);
        pane.getChildren().add(fundo);

        // Título (topo, seção 1)
        renderizarTitulo();

        // Conteúdo (meio, seção 2) - texto ou itens
        if (exibeItens) {
            renderizarItens();
        } else {
            renderizarConteudo();
        }

        // Opções (base, seção 3)
        renderizarOpcoes();

        return pane;
    }

    private void renderizarTitulo() {
        Text tituloText = new Text(titulo);
        tituloText.setFont(GameFont.medium());
        tituloText.setX(30);
        tituloText.setY(45);
        tituloText.setFill(Color.web("#FFD700"));
        pane.getChildren().add(tituloText);
    }

    private void renderizarConteudo() {
        String conteudoExibido = usaPaginacao ?
                paginas.get(paginaAtual) : conteudo;

        Text conteudoText = new Text(conteudoExibido);
        conteudoText.setFont(GameFont.small());
        conteudoText.setX(30);
        conteudoText.setY(130);
        conteudoText.setFill(Color.WHITE);
        conteudoText.setWrappingWidth(420);
        pane.getChildren().add(conteudoText);

        // Indicador de páginas (se usar paginação)
        if (usaPaginacao && paginas.size() > 1) {
            String indicador = "Página " + (paginaAtual + 1) + "/" + paginas.size();
            Text indicadorText = new Text(indicador);
            indicadorText.setFont(GameFont.small());
            indicadorText.setX(380);
            indicadorText.setY(310);
            indicadorText.setFill(Color.web("#999999"));
            pane.getChildren().add(indicadorText);
        }
    }

    private void renderizarItens() {
        int espacoEntreItens = 110; // Para 4 itens
        int startX = 40;
        int startY = 120;

        for (int i = 0; i < itens.size(); i++) {
            SistemaDeAviso.Item item = itens.get(i);
            double x = startX + (i * espacoEntreItens);

            // Sprite do item
            ImageView itemSprite = carregarImagem(item.getCaminhoSprite(), x, startY, 70, 70);
            pane.getChildren().add(itemSprite);

            // Nome
            Text nomeItem = new Text(item.getNome());
            nomeItem.setFont(GameFont.small());
            nomeItem.setX(x - 10);
            nomeItem.setY(200);
            nomeItem.setFill(Color.WHITE);
            pane.getChildren().add(nomeItem);

            // Preço
            Text precoItem = new Text("R$ " + String.format("%.1f", item.getPreco()));
            precoItem.setFont(GameFont.small());
            precoItem.setX(x - 10);
            precoItem.setY(215);
            precoItem.setFill(Color.web("#FFD700"));
            pane.getChildren().add(precoItem);

            // Indicador seleção
            if (i == itemSelecionado) {
                Text seta = new Text("▼");
                seta.setFont(GameFont.medium());
                seta.setX(x + 25);
                seta.setY(110);
                seta.setFill(Color.web("#FFD700"));
                pane.getChildren().add(seta);
            }
        }
    }

    private void renderizarOpcoes() {
        textosOpcoes.forEach(text -> pane.getChildren().remove(text));
        textosOpcoes.clear();

        int espacoEntreOpcoes = 120;
        int startX = 40;
        int startY = 300;

        for (int i = 0; i < opcoes.length; i++) {
            Text opcaoText = new Text(opcoes[i]);
            opcaoText.setFont(GameFont.small());
            opcaoText.setX(startX + (i * espacoEntreOpcoes));
            opcaoText.setY(startY);

            if (i == opcaoSelecionada) {
                opcaoText.setFill(Color.web("#FFD700"));
            } else {
                opcaoText.setFill(Color.WHITE);
            }

            textosOpcoes.add(opcaoText);
            pane.getChildren().add(opcaoText);
        }
    }

    public void proximaOpcao() {
        if (opcaoSelecionada < opcoes.length - 1) {
            opcaoSelecionada++;
            renderizarOpcoes();
        }
    }

    public void opcaoAnterior() {
        if (opcaoSelecionada > 0) {
            opcaoSelecionada--;
            renderizarOpcoes();
        }
    }

    /**
     * Navegar para próxima página (usada com paginação de conteúdo).
     * Se houver itens, navega entre itens; caso contrário, navega entre páginas.
     */
    public void proximoItem() {
        if (exibeItens) {
            // Modo itens: navegar entre itens
            if (itemSelecionado < itens.size() - 1) {
                itemSelecionado++;
                pane.getChildren().removeIf(node -> node instanceof ImageView ||
                        (node instanceof Text && ((Text) node).getText().equals("▼")));
                renderizarItens();
            }
        } else if (usaPaginacao) {
            // Modo paginação: navegar entre páginas
            if (paginaAtual < paginas.size() - 1) {
                paginaAtual++;
                pane.getChildren().removeIf(node -> node instanceof Text);
                renderizarTitulo();
                renderizarConteudo();
                renderizarOpcoes();
            }
        }
    }

    /**
     * Navegar para página anterior (usada com paginação de conteúdo).
     * Se houver itens, navega entre itens; caso contrário, navega entre páginas.
     */
    public void itemAnterior() {
        if (exibeItens) {
            // Modo itens: navegar entre itens
            if (itemSelecionado > 0) {
                itemSelecionado--;
                pane.getChildren().removeIf(node -> node instanceof ImageView ||
                        (node instanceof Text && ((Text) node).getText().equals("▼")));
                renderizarItens();
            }
        } else if (usaPaginacao) {
            // Modo paginação: navegar entre páginas
            if (paginaAtual > 0) {
                paginaAtual--;
                pane.getChildren().removeIf(node -> node instanceof Text);
                renderizarTitulo();
                renderizarConteudo();
                renderizarOpcoes();
            }
        }
    }

    public int getOpcaoSelecionada() {
        return opcaoSelecionada;
    }

    public int getItemSelecionado() {
        return itemSelecionado;
    }

    public SistemaDeAviso.Item obterItemSelecionado() {
        if (exibeItens && itemSelecionado < itens.size()) {
            return itens.get(itemSelecionado);
        }
        return null;
    }

    public String getOpcaoAtual() {
        return opcoes[opcaoSelecionada];
    }

    public Pane getPane() {
        return pane;
    }

    private ImageView carregarImagem(String caminho, double x, double y, double largura, double altura) {
        Image imagem = new Image(getClass().getResourceAsStream(caminho));
        ImageView view = new ImageView(imagem);
        view.setX(x);
        view.setY(y);
        view.setFitWidth(largura);
        view.setFitHeight(altura);
        return view;
    }
}