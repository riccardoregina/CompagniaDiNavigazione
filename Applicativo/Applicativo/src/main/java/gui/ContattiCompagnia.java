package gui;

import controller.ControllerCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Contatti compagnia.
 */
public class ContattiCompagnia {
    private JLabel label1;
    private JPanel panel1;
    private JTable table1;
    private JButton bChiudi;

    public JFrame frame;
    public JFrame frameChiamante;

    public ControllerCliente controllerCliente;

    private String nomeCompagnia;

    public ContattiCompagnia (JFrame frameChiamante, ControllerCliente controllerCliente, String nomeCompagnia) {
        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        this.nomeCompagnia=nomeCompagnia;
        frame = new JFrame("contattiCompagnia");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //deve essere popolata la tabella con i contatti della compagnia


        bChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
