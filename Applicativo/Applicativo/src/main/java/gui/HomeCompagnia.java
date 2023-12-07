package gui;

import controller.ControllerCompagnia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeCompagnia {
    private JPanel panel1;
    private JPanel panelContainerButton;
    private JButton bCreaCorsa;
    private JButton bModificaCorsa;
    private JButton bAggiungiNatante;
    private JButton bRimuoviNatante;
    public ControllerCompagnia controllerCompagnia;
    public JFrame frameChiamante;
    public JFrame frame;

    public HomeCompagnia(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {

        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("homeCompagnia");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        bRimuoviNatante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RimuoviNatante rimuoviNatante = new RimuoviNatante(frame, controllerCompagnia);
                rimuoviNatante.frame.setVisible(true);
            }
        });
    }
}
