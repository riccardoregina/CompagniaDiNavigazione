package gui;

import controller.ControllerCompagnia;
import controller.ControllerCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Home main.
 */
public class HomeMain {
    private JPanel panel1;
    private JLabel TextBenvenuto;
    private JButton buttonCompagnia;
    private JButton buttonCliente;

    /**
     * The constant frame.
     */
    public static JFrame frame;
    /**
     * The Controller cliente.
     */
    public ControllerCliente controllerCliente;
    /**
     * The Controller compagnia.
     */
    public ControllerCompagnia controllerCompagnia;

    /**
     * Instantiates a new Home main.
     */
    public HomeMain() {
        buttonCompagnia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerCompagnia = new ControllerCompagnia();
                LoginCompagnia credenzialiCompagnia = new LoginCompagnia(frame, controllerCompagnia);
                frame.dispose();
            }
        });

        buttonCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerCliente= new ControllerCliente();
                LoginCliente credenzialiCliente = new LoginCliente(frame, controllerCliente);
                frame.dispose();
            }
        });
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        frame = new JFrame("homeMain");
        frame.setContentPane(new HomeMain().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
