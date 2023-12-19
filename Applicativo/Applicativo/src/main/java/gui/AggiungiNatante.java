package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

import controller.ControllerCompagnia;

/**
 * The type Aggiungi natante.
 */
public class AggiungiNatante {
    private JPanel panel1;
    private JButton bAggiungiNatante;
    private JComboBox cbTipoNatante;
    private JTextField tfCapPasseggeri;
    private JTextField tfCapVeicoli;
    private JTextField tfNomeNatante;
    private JButton bIndietro;

    public ControllerCompagnia controllerCompagnia;
    public JFrame frame;
    public JFrame frameChiamante;


    public AggiungiNatante(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {

        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("aggiungiNatante");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        for (String s : Arrays.asList("traghetto", "motonave", "aliscafo", "altro")) {
            cbTipoNatante.addItem(s);
        }

        bAggiungiNatante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int capVeicoli;
                String nome = tfNomeNatante.getText();
                int capPasseggeri;
                String temp1 = tfCapPasseggeri.getText();
                String tipoNatante = (String) cbTipoNatante.getSelectedItem();

                try {
                    capPasseggeri = Integer.parseInt(temp1);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore numerico in Capienza Passeggeri");
                    return;
                }


                if (tipoNatante.equals("traghetto") || tipoNatante.equals("altro")) {
                    String temp = tfCapVeicoli.getText();

                    try {
                        capVeicoli = Integer.parseInt(temp);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Inserisci un valore numerico in Capienza Veicoli");
                        return;
                    }
                } else {
                    capVeicoli = 0;
                    JOptionPane.showMessageDialog(null, "siccome il tipo selezionato non ha disponibilità di veicoli, la capienza è 0");
                }


                if (nome.equals("Nome Natante") || nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "inserisci il nome correttamente.");
                    return;
                } else {
                    boolean check = controllerCompagnia.aggiungiNatante(nome, tipoNatante, capPasseggeri, capVeicoli);
                    if (!check) {
                        JOptionPane.showMessageDialog(null, "non è stato possibile aggiungere il natante.");
                    } else {
                        JOptionPane.showMessageDialog(null, "natante aggiunto.");
                    }
                    frameChiamante.setVisible(true);
                    frame.dispose();


                }
            }
        });
        bIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
