import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaInicial extends JFrame {
    public PaginaInicial() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel centralizado
        JPanel panelCentral = new JPanel(new GridLayout(5, 1));
        JLabel labelTitulo = new JLabel("Bem-vindo ao", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        panelCentral.add(labelTitulo);

        JLabel labelSubtitulo = new JLabel("Campo Minado", SwingConstants.CENTER);
        labelSubtitulo.setFont(new Font("Arial", Font.BOLD, 40));
        panelCentral.add(labelSubtitulo);

        JTextArea instrucoes = new JTextArea();
        instrucoes.setText("Instruções do Jogo:\n\n" +
                "1. Clique nos botões para revelar as células.\n" +
                "2. Evite clicar em bombas para vencer.\n" +
                "3. O jogo é ganho quando todas as células seguras são reveladas.\n\n" +
                "Escolha o nível de dificuldade antes de começar o jogo.");
        instrucoes.setEditable(false);
        instrucoes.setOpaque(false);
        instrucoes.setFont(new Font("Arial", Font.PLAIN, 16));
        instrucoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCentral.add(instrucoes);

        String[] opcoesDificuldade = { "Fácil", "Médio", "Difícil" };
        JComboBox<String> comboDificuldade = new JComboBox<>(opcoesDificuldade);
        comboDificuldade.setFont(new Font("Arial", Font.PLAIN, 20));
        panelCentral.add(comboDificuldade);

        JButton botaoComecar = new JButton("Começar Jogo");
        botaoComecar.setFont(new Font("Arial", Font.BOLD, 20));
        botaoComecar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a página inicial
                iniciarJogo(comboDificuldade.getSelectedIndex()); // Inicia o jogo com a dificuldade selecionada
            }
        });
        panelCentral.add(botaoComecar);

        add(panelCentral, BorderLayout.CENTER);

        // Configurações da janela em tela cheia
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setVisible(true);
    }

    private void iniciarJogo(int dificuldade) {
        SwingUtilities.invokeLater(() -> {
            CampoMinado campoMinado = new CampoMinado(dificuldade);
            campoMinado.iniciarJogo();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaginaInicial());
    }
}
