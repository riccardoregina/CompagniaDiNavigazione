package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.ControllerCliente;


public class LoginCliente {
    private JPanel panelContainerLogin;
    private JPasswordField passwordCliente;
    private JTextField loginCliente;
    private JButton buttonInvio;
    private JLabel label1;
    private JPanel panel1;
    public ControllerCliente controllerCliente;
    public JFrame frameChiamante;
    public JFrame frame;

    public LoginCliente(JFrame frameChiamante, ControllerCliente controllerCliente) {

        this.frameChiamante=frameChiamante;
        this.controllerCliente=controllerCliente;
        frame = new JFrame("loginCompagnia");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        buttonInvio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //prima di chiamare il nuovo frame bisogna controllare le credenziali, funzione booleana
                HomeCliente homeCliente = new HomeCliente(frame, controllerCliente);
                homeCliente.frame.setVisible(true);
                frame.dispose();
            }
        });
    }
}
