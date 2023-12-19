package gui;

import controller.ControllerCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Tuoi biglietti.
 */
public class TuoiBiglietti {
    private JPanel panel1;
    private JTable table1;
    private JButton bChiudi;
    public JFrame frame;
    public JFrame frameChiamante;
    public ControllerCliente controllerCliente;

    public TuoiBiglietti (JFrame frameChiamante, ControllerCliente controllerCliente) {
        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("bigliettiAttivi");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        // popolare la tabella con i biglietti necessari
        table1.setDefaultEditor(Object.class, null);
        bChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
