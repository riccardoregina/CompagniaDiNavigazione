package gui;

import controller.ControllerCompagnia;
import controller.ControllerCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class homeMain {
    private JPanel panel1;
    private JLabel TextBenvenuto;
    private JButton buttonCompagnia;
    private JButton buttonCliente;

    public static JFrame frame;
    public ControllerCliente controllerCliente;
    public ControllerCompagnia controllerCompagnia;

    public homeMain() {
        buttonCompagnia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerCompagnia = new ControllerCompagnia();
                LoginCompagnia credenzialiCompagnia = new LoginCompagnia(frame, controllerCompagnia);
                credenzialiCompagnia.frame.setVisible(true);
                frame.dispose();
            }
        });

        buttonCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerCliente= new ControllerCliente();
                LoginCliente credenzialiCliente = new LoginCliente(frame, controllerCliente);
                credenzialiCliente.frame.setVisible(true);
                frame.dispose();
            }
        });
    }



    public static void main(String[] args) {
        frame = new JFrame("homeMain");
        frame.setContentPane(new homeMain().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
