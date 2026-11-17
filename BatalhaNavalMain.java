import java.awt.*;
import java.util.*;
import javax.swing.*;

public class BatalhaNavalMain {
    static int tiro;
    static int acertos = 0;
    static int totalPartes;
    static int Tlinha;
    static int Tcoluna;
    static int [][] tabuleiro;
    static JButton[][] botoesBarco;

    static void fimDeJogo(boolean venceu) {
        String msg = venceu ? "Parabéns, você venceu!" : "Fim de jogo, você perdeu!";
        JOptionPane.showMessageDialog(janela, msg);
        mostrarMenuEscolha();
    }

    static int calcularTotalPartes(int[] quantidades) {
        int total = 0;
        for (int i = 0; i < quantidades.length; i++) {
            int tamanho = 5 - i;
            total += tamanho * quantidades[i];
        }
        return total;
    }

    static JFrame janela = new JFrame("Batalha Naval");

    public static void main(String[] args) {
    janela.setSize(500, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mostrarMenuEscolha();
        
        janela.setVisible(true);
    }

    public static void mostrarMenuEscolha() {
        JPanel painelMenu = new JPanel();
        JButton btnPequeno = new JButton("Fácil (6x6)");
        JButton btnGrande = new JButton("Difícil (8x8)");

        btnPequeno.addActionListener(e -> configurarCampoBatalha(6, 6, 30, new int[]{1,1,1,2,4}));
        btnGrande.addActionListener(e -> configurarCampoBatalha(8, 8, 40, new int[]{1,2,2,2,6}));

        painelMenu.add(btnPequeno);
        painelMenu.add(btnGrande);

        janela.setContentPane(painelMenu);
        janela.revalidate();
    }
    
    public static void configurarCampoBatalha(int linha, int coluna, int tiros, int[] quantidades) {
        Tlinha = linha;
        Tcoluna = coluna;
        tiro = tiros;
        acertos = 0;
        totalPartes = calcularTotalPartes(quantidades);

        tabuleiro= new int[Tlinha][Tcoluna];
        botoesBarco = new JButton[Tlinha][Tcoluna];

            posicionarBarcos(quantidades);

        JPanel painelCampo = criarPainelCampoBatalha();
        
        janela.setContentPane(painelCampo);
        janela.revalidate();
    }
    
    public static void posicionarBarcos(int[] quantidades) {
    Random rand = new Random();
    int[] tamanhos ={5,4,3,2,1}; 

    for (int i = 0; i < tamanhos.length; i++) {
        int tamanhoBarco = tamanhos[i];
        int quant = 0;

        while (quant < quantidades[i]) {
            int rlinha=rand.nextInt(Tlinha);
            int rcoluna=rand.nextInt(Tcoluna-(tamanhoBarco-1));boolean livre = true;
            if(tabuleiro[rlinha][rcoluna]== 0){      
                for (int j = 0; j <= tamanhoBarco-1; j++) {
                    if(tabuleiro[rlinha][rcoluna+j]==0){
                        livre=true;
                    } else{
                        livre=false;
                        break;
                    }
                }
                if(livre){
                    for (int d = 0; d <= tamanhoBarco-1; d++) {
                        tabuleiro[rlinha][rcoluna+d] = tamanhoBarco+1;
                    }
                quant++;
                }
                
            }
        }
    }
    }

    public static JPanel criarPainelCampoBatalha() {
        JPanel painelCampo = new JPanel(new GridLayout(Tlinha, Tcoluna));
        
        for (int i = 0; i < Tlinha; i++) {
            for (int j = 0; j < Tcoluna; j++) {

                botoesBarco[i][j] = new JButton();
                botoesBarco[i][j].setBackground(Color.BLUE);

                final int linha = i;
                final int coluna = j;
                
                botoesBarco[i][j].addActionListener(e -> verificarTiros(linha, coluna));

                painelCampo.add(botoesBarco[i][j]);
            }
        }
        return painelCampo;
    }
 
    public static void verificarTiros(int linha, int coluna) {
        JButton b = botoesBarco[linha][coluna];
        b.setEnabled(false);
        tiro--;
        int tipo = tabuleiro[linha][coluna];

        if (tipo == 0) {
            b.setBackground(Color.CYAN);
        } else {
            acertos++;
            b.setBackground(Color.RED);
        }
        atualizarTitulo();

        if (acertos == totalPartes) {
            fimDeJogo(true);
        } else if (tiro == 0) {
            fimDeJogo(false);
        }
    }

    public static void atualizarTitulo() {
        janela.setTitle("Batalha Naval - Tiros restantes: " + tiro + " - Acertos: " + acertos + "/" + totalPartes);
    }   
}