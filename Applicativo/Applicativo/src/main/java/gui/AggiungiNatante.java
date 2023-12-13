package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
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

    public ControllerCompagnia controllerCompagnia;
    public JFrame frame;
    public JFrame frameChiamante;


    public AggiungiNatante(JFrame frameChiamnte, ControllerCompagnia controllerCompagnia) {

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

        tfCapPasseggeri.setEnabled(false);
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


                if (tipoNatante == "traghetto" || tipoNatante == "altro") {
                    tfCapPasseggeri.setEnabled(true);

                    String temp = tfCapVeicoli.getText();

                    try {
                        capVeicoli = Integer.parseInt(temp);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Inserisci un valore numerico in Capienza Veicoli");
                        return;
                    }
                } else {
                    capVeicoli = 0;
                }


                if (nome == "Nome Natante" || nome == "") {
                    JOptionPane.showMessageDialog(null, "inserisci il nome correttamente.");
                    return;
                } else {
                    boolean check = controllerCompagnia.aggiungiNatante(nome, tipoNatante, capPasseggeri, capVeicoli);
                    if (check == false) {
                        JOptionPane.showMessageDialog(null, "non è stato possibile aggiungere il natante.");
                    } else {
                        JOptionPane.showMessageDialog(null, "natante aggiunto.");
                    }
                    frameChiamante.setVisible(true);
                    frame.dispose();


                }
            }
        });
    }
}