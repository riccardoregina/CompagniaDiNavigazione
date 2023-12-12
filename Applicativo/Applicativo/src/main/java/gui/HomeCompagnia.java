package gui;

import controller.ControllerCompagnia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Home compagnia.
 */
public class HomeCompagnia {
    private JPanel panel1;
    private JPanel panelContainerButton;
    private JButton bCreaCorsa;
    private JButton bModificaCorsa;
    private JButton bAggiungiNatante;
    private JButton bRimuoviNatante;
    /**
     * The Controller compagnia.
     */
    public ControllerCompagnia controllerCompagnia;
    /**
     * The Frame chiamante.
     */
    public JFrame frameChiamante;
    /**
     * The Frame.
     */
    public JFrame frame;

    /**
     * Instantiates a new Home compagnia.
     *
     * @param frameChiamante      the frame chiamante
     * @param controllerCompagnia the controller compagnia
     */
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
                frame.setVisible(false);
            }
        });
        bAggiungiNatante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiNatante aggiungiNatante = new AggiungiNatante(frame, controllerCompagnia);
                aggiungiNatante.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}
