package CampoMinado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

public class CampoMinado {
    private static final int TAMANHO_FACIL = 8;
    private static final int BOMBAS_FACIL = 10;
    private static final int TAMANHO_MEDIO = 12;
    private static final int BOMBAS_MEDIO = 20;
    private static final int TAMANHO_DIFICIL = 16;
    private static final int BOMBAS_DIFICIL = 30;
    private static final int TAMANHO_BOMBA = 50;

    private JButton[][] botoes;
    private boolean[][] bombas;
    private boolean[][] revelado;
    private int tamanho;
    private int bombasTotais;
    private JFrame frameJogo;

    public CampoMinado(int dificuldade) {
        escolherDificuldade(dificuldade);

        botoes = new JButton[tamanho][tamanho];
        bombas = new boolean[tamanho][tamanho];
        revelado = new boolean[tamanho][tamanho];
    }

    private void escolherDificuldade(int dificuldade) {
        switch (dificuldade) {
            case 0: // Fácil
                tamanho = TAMANHO_FACIL;
                bombasTotais = BOMBAS_FACIL;
                break;
            case 1: // Médio
                tamanho = TAMANHO_MEDIO;
                bombasTotais = BOMBAS_MEDIO;
                break;
            case 2: // Difícil
                tamanho = TAMANHO_DIFICIL;
                bombasTotais = BOMBAS_DIFICIL;
                break;
            default:
                throw new IllegalArgumentException("Nível de dificuldade inválido.");
        }
    }

    public void iniciarJogo() {
        frameJogo = new JFrame("Campo Minado");
        frameJogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameJogo.setLayout(new BorderLayout());

        JPanel panelBotoes = new JPanel(new GridLayout(tamanho, tamanho));
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                JButton botao = new JButton();
                botao.addActionListener(new BotaoListener(i, j));
                panelBotoes.add(botao);
                botoes[i][j] = botao;
            }
        }

        frameJogo.add(panelBotoes, BorderLayout.CENTER);

        frameJogo.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frameJogo.setUndecorated(true);
        frameJogo.setLocationRelativeTo(null);
        frameJogo.setVisible(true);

        inicializarBombas();
        configurarImagens();
        adicionarBotoesControle();
    }

    private void adicionarBotoesControle() {
        JButton botaoVoltar = new JButton("Voltar");
        JButton botaoEscolherModo = new JButton("Escolher Modo");
        JButton botaoReiniciar = new JButton("Reiniciar");

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameJogo.dispose();
                new PaginaInicial();
            }
        });

        botaoEscolherModo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameJogo.dispose();
                new PaginaInicial();
            }
        });

        botaoReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJogo();
            }
        });

        JPanel panelControle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelControle.add(botaoVoltar);
        panelControle.add(botaoEscolherModo);
        panelControle.add(botaoReiniciar);

        frameJogo.add(panelControle, BorderLayout.SOUTH);
    }

    private void configurarImagens() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                setImagemBotao(i, j, "empty.png");
            }
        }
    }

    private void setImagemBotao(int linha, int coluna, String imagem) {
        URL imageURL = getClass().getResource(imagem);
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            Image image = icon.getImage().getScaledInstance(TAMANHO_BOMBA, TAMANHO_BOMBA, Image.SCALE_SMOOTH);
            botoes[linha][coluna].setIcon(new ImageIcon(image));
        } else {
            System.err.println("Imagem não encontrada: " + imagem);
        }
    }

    private void inicializarBombas() {
        Random random = new Random();
        int bombasRestantes = bombasTotais;

        while (bombasRestantes > 0) {
            int i = random.nextInt(tamanho);
            int j = random.nextInt(tamanho);

            if (!bombas[i][j]) {
                bombas[i][j] = true;
                bombasRestantes--;
            }
        }
    }

    private void reiniciarJogo() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                bombas[i][j] = false;
                revelado[i][j] = false;
                botoes[i][j].setIcon(null);
                botoes[i][j].setEnabled(true);
                botoes[i][j].setText(""); // Limpar qualquer texto anterior
            }
        }

        inicializarBombas();
        configurarImagens();
    }

    private int contarBombasVizinhas(int linha, int coluna) {
        int contagem = 0;

        for (int i = Math.max(0, linha - 1); i <= Math.min(tamanho - 1, linha + 1); i++) {
            for (int j = Math.max(0, coluna - 1); j <= Math.min(tamanho - 1, coluna + 1); j++) {
                if (bombas[i][j]) {
                    contagem++;
                }
            }
        }

        return contagem;
    }

    private void revelarVizinhanca(int linha, int coluna) {
        if (linha < 0 || coluna < 0 || linha >= tamanho || coluna >= tamanho || revelado[linha][coluna]) {
            return;
        }

        int bombasVizinhas = contarBombasVizinhas(linha, coluna);
        revelado[linha][coluna] = true;
        botoes[linha][coluna].setEnabled(false);

        if (bombasVizinhas == 0) {
            for (int i = Math.max(0, linha - 1); i <= Math.min(tamanho - 1, linha + 1); i++) {
                for (int j = Math.max(0, coluna - 1); j <= Math.min(tamanho - 1, coluna + 1); j++) {
                    revelarVizinhanca(i, j);
                }
            }
        } else {
            botoes[linha][coluna].setText(String.valueOf(bombasVizinhas));
        }
    }

    private class BotaoListener implements ActionListener {
        private int linha;
        private int coluna;

        public BotaoListener(int linha, int coluna) {
            this.linha = linha;
            this.coluna = coluna;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (bombas[linha][coluna]) {
                setImagemBotao(linha, coluna, "bomb.png");
                JOptionPane.showMessageDialog(null, "Você perdeu!");
                reiniciarJogo();
            } else {
                revelarVizinhanca(linha, coluna);
                verificarVitoria();
            }
        }
    }

    private void verificarVitoria() {
        boolean vitoria = true;

        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (!bombas[i][j] && !revelado[i][j]) {
                    vitoria = false;
                    break;
                }
            }
        }

        if (vitoria) {
            JOptionPane.showMessageDialog(null, "Parabéns! Você venceu!");
            reiniciarJogo();
        }
    }
}
