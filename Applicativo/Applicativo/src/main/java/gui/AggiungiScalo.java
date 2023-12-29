package gui;

import controller.ControllerCliente;
import controller.ControllerCompagnia;

import javax.swing.*;
import java.awt.*;

public class AggiungiScalo {
    private JPanel panel1;
    private JComboBox cbPortoScalo;
    private JTextField tfDataArrivo;

    JFrame frame;
    JFrame frameChiamante;
    ControllerCompagnia controllerCompagnia;

    public AggiungiScalo (JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {
        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("crea Corsa");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
