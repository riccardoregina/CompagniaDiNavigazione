package gui;

import controller.ControllerCompagnia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RimuoviNatante {
    private JLabel Didascalia1;
    private JLabel Didascalia2;
    private JComboBox comboBox1;
    private JButton buttonElimina;
    private JTable table1;
    private JPanel panel1;
    private JPanel panel1_1;
    private JPanel panel1_2;
    public ControllerCompagnia controllerCompagnia;
    public JFrame frameChiamante;
    public JFrame frame;


    public RimuoviNatante(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {

        frame = new JFrame("rimuoviNatante");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        buttonElimina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //chiamare il metodo per eliminare l'id della combobox affianco
                frame.dispose();
            }
        });
    }
}
