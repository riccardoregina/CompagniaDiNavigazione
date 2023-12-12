package gui;

import controller.ControllerCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * The type Aggiungi veicolo.
 */
public class AggiungiVeicolo {
    private JPanel panel1;
    private JButton bAggiungi;
    private JTextField tfTargaVeicolo;
    private JComboBox cbTipoVeicolo;

    public JFrame frame;
    public JFrame frameChiamante;
    public ControllerCliente controllerCliente;

    public AggiungiVeicolo(JFrame frameChiamante, ControllerCliente controllerCliente) {

        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("aggiungiVeicolo");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        for (String s : Arrays.asList("automobile", "motociclo", "mezzo pesante", "altro")) {
            cbTipoVeicolo.addItem(s);
        }

        bAggiungi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoVeicolo = (String) cbTipoVeicolo.getSelectedItem();
                String targa= tfTargaVeicolo.getText();
                if (targa.equals("Targa") || targa.equals("")) {
                    JOptionPane.showMessageDialog(null,"Inserisci la targa.");
                    return;
                } else {
                    boolean check = controllerCliente.addVeicolo(tipoVeicolo, targa);
                    if (check == false) {
                        JOptionPane.showMessageDialog(null, "non è stato possibile aggiungere il veicolo.");
                    } else {
                        JOptionPane.showMessageDialog(null, "veicolo aggiunto.");
                    }
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
            }
        });
    }
}
