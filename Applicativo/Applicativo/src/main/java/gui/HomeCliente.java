package gui;

import controller.ControllerCliente;
import controller.ControllerCompagnia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Home cliente.
 */
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

    /**
     * The Controller cliente.
     */
    public ControllerCliente controllerCliente;
    /**
     * The Frame chiamante.
     */
    public JFrame frameChiamante;
    /**
     * The Frame.
     */
    public JFrame frame;

    /**
     * Instantiates a new Home cliente.
     *
     * @param frameChiamante    the frame chiamante
     * @param controllerCliente the controller cliente
     */
    public HomeCliente(JFrame frameChiamante, ControllerCliente controllerCliente) {

        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("homeCliente");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        bAggiungiVeicolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiVeicolo aggiungiVeicolo = new AggiungiVeicolo(frame, controllerCliente);
                aggiungiVeicolo.frame.setVisible(true);
                frame.setVisible(false);
            }
        });

        ArrayList<String> compagnie = new ArrayList<String>();
        ArrayList<String> idsCompagnie = new ArrayList<String>();
        controllerCliente.getCompagnie(compagnie, idsCompagnie);
        for (String nomeCompagnia : compagnie) {
            comboBox1.addItem( nomeCompagnia);
        }
        bVediInfoCompagnia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeComp = (String) comboBox1.getSelectedItem();
                int i = 0;

                //devo trovare id compagnia e passarlo alla nuova gui per avere tutte le info

                String nomeCompagnia = (String) comboBox1.getSelectedItem();
                ContattiCompagnia contattiCompagnia = new ContattiCompagnia(frame, controllerCliente, nomeCompagnia);
                contattiCompagnia.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
        bBigliettiCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuoiBiglietti iTuoiBiglietti = new TuoiBiglietti(frame, controllerCliente);
                iTuoiBiglietti.frame.setVisible(true);
                frame.setVisible(false);
            }
        });
    }
}
