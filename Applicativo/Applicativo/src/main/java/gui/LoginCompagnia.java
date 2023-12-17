package gui;

import controller.ControllerCompagnia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Login compagnia.
 */
public class LoginCompagnia {
    private JPanel panel1;
    private JPanel panelContainerLogin;
    private JPasswordField passwordCompagnia;
    private JTextField loginCompagnia;
    private JButton buttonInvio;
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
     * Instantiates a new Login compagnia.
     *
     * @param frameChiamante      the frame chiamante
     * @param controllerCompagnia the controller compagnia
     */
    public LoginCompagnia(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {

        this.frameChiamante=frameChiamante;
        this.controllerCompagnia=controllerCompagnia;
        frame = new JFrame("loginCompagnia");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        buttonInvio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //prima di chiamare il nuovo frame bisogna controllare le credenziali, funzione booleana
                String login = loginCompagnia.getText();
                char[] pw =passwordCompagnia.getPassword();
                String pwd = new String (pw);

                if (login.isEmpty() || pwd.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"inserisci le tue credenziali" );
                    return;
                }
                else if (controllerCompagnia.compagniaAccede(login, pwd)) {
                    HomeCompagnia homeCompagnia = new HomeCompagnia(frame, controllerCompagnia);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "connessione al DB fallita/ credenziali errate");
                }
            }
        });
    }
}
