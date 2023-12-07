package gui;

import controller.ControllerCliente;
import controller.ControllerCompagnia;

import javax.swing.*;

public class HomeCliente {
    private JPanel panel1;
    private JPanel panelContainerbutton;
    private JButton bVisualizzaCorse;
    private JButton bAggiungiVeicolo;
    private JButton bBigliettiCliente;
    private JButton bVeicoliCliente;
    private JPanel panelInfoCompagnia;
    private JComboBox comboBox1;
    private JButton bVediInfoCompagnia;

    public ControllerCliente controllerCliente;
    public JFrame frameChiamante;
    public JFrame frame;

    public HomeCliente(JFrame frameChiamante, ControllerCliente controllerCliente) {

        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("homeCliente");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
