package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.ControllerCliente;


/**
 * The type Login cliente.
 */
public class LoginCliente {
    private JPanel panelContainerLogin;
    private JPasswordField passwordCliente;
    private JTextField loginCliente;
    private JButton buttonInvio;
    private JLabel label1;
    private JPanel panel1;
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
     * Instantiates a new Login cliente.
     *
     * @param frameChiamante    the frame chiamante
     * @param controllerCliente the controller cliente
     */
    public LoginCliente(JFrame frameChiamante, ControllerCliente controllerCliente) {

        this.frameChiamante=frameChiamante;
        this.controllerCliente=controllerCliente;
        frame = new JFrame("loginCliente");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        buttonInvio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginCliente.getText();
                char[] pw =passwordCliente.getPassword();
                String pwd = new String (pw);

                if (login.equals("") || pwd.equals("")) {
                    JOptionPane.showMessageDialog(null,"inserisci le tue credenziali" );
                return;
                }
                else if (controllerCliente.clienteAccede(login, pwd)) {
                    HomeCliente homeCliente = new HomeCliente(frame, controllerCliente);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "connessione al DB fallita/ credenziali errate");
                }
            }
        });
    }
}
