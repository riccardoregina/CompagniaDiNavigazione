package gui;

import javax.swing.*;
import controller.ControllerCliente;

/**
 * The type Tuoi veicoli.
 */
public class TuoiVeicoli {
    private JPanel panel1;
    private JTable table1;
    private JLabel label1;
    public JFrame frame;
    public JFrame frameChiamante;
    public ControllerCliente controllerCliente;

    public TuoiVeicoli (JFrame frameChiamante, ControllerCliente controllerCliente) {
        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("homeCliente");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
