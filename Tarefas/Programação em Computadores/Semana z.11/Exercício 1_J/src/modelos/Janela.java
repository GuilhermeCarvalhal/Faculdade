package modelos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;

public class Janela extends JFrame implements ActionListener {

    JTextField caixa1, caixa2, caixa3;
    JLabel rot5;

    public Janela() {
        this.setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Poupex");
        setLocation(600, 250);
        inicomponentes();
    }

    private void inicomponentes() {
        // Criando componentes
        JPanel painel = new JPanel(new GridLayout(5, 2, 5, 5));
        JLabel rot1 = new JLabel("Juro ao mês %: ", SwingConstants.LEFT);
        JLabel rot2 = new JLabel("Num. de anos: ", SwingConstants.LEFT);
        JLabel rot3 = new JLabel("Depósito Mensal R$: ", SwingConstants.LEFT);
        JLabel rot4 = new JLabel("Total poupado R$: ", SwingConstants.LEFT);
        rot5 = new JLabel("");
        JLabel rot6 = new JLabel("");

        caixa1 = new JTextField(5);
        caixa2 = new JTextField(5);
        caixa3 = new JTextField(5);
        JButton b1 = new JButton("CLIQUE");
        this.getContentPane().add(painel);

        // Setando valores Painel
        painel.setOpaque(true);

        // Setando valores rótulo
        rot1.setBounds(getBounds());
        rot1.setForeground(Color.BLACK);
        rot1.setOpaque(true);
        rot1.setBackground(getForeground());

        // Setando valores TextField
        caixa1.setBounds(50, 50, 100, 100);

        // Setando valores Botão
        b1.setBounds(100, 200, 80, 40);
        b1.setBackground(Color.WHITE);
        b1.setEnabled(true);
        b1.setForeground(Color.BLACK);
        b1.setFont(new Font("", Font.ITALIC, 8));

        // Botão Funções

        b1.addActionListener(this);

        // Adições
        painel.add(rot1);
        painel.add(caixa1);
        painel.add(rot2);
        painel.add(caixa2);
        painel.add(rot3);
        painel.add(caixa3);
        painel.add(rot4);
        painel.add(rot5);
        painel.add(rot6);
        painel.add(b1);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        // 1 - juro, 2 - anos, 3 - mensal
        Integer n1 = Integer.parseInt(caixa1.getText());
        Integer n2 = Integer.parseInt(caixa2.getText());
        Integer n3 = Integer.parseInt(caixa3.getText());
        double montanteAcumulado = n3 * Math.pow(1 + (n1 / 100.0), (n2 * 12));
        DecimalFormat df = new DecimalFormat("#.##");
        String montanteAcumuladoString = df.format(montanteAcumulado);
        rot5.setText(montanteAcumuladoString);
        ;
    }
}