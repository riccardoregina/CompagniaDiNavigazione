package gui;

import controller.ControllerCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Contatti compagnia.
 */
public class ContattiCompagnia {
    private JLabel label1;
    private JPanel panel1;
    private JTable table1;
    private JButton bChiudi;
    private JLabel lSito;

    public JFrame frame;
    public JFrame frameChiamante;

    public ControllerCliente controllerCliente;

    private String nomeCompagnia;

    public ContattiCompagnia (JFrame frameChiamante, ControllerCliente controllerCliente, String idCompagnia) {
        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        this.nomeCompagnia=nomeCompagnia;
        frame = new JFrame("contattiCompagnia");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ArrayList<String> nomeSocial = new ArrayList<String>();
        ArrayList<String> tagSocial = new ArrayList<String>();
        ArrayList<String> email = new ArrayList<String>();
        ArrayList<String> telefono = new ArrayList<String>();
        String sito = null;
        controllerCliente.visualizzaContatti(idCompagnia, nomeSocial, tagSocial, email, telefono, sito);

        lSito.setText("sito:" +sito);

        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"tipo", "contatto"});
        table1.setModel(tableModel);
        if (telefono != null) {
            for (String s : telefono)
                tableModel.addRow(new Object[]{"num telefono", s});
        }
        if (email != null) {
            for (String s : email)
                tableModel.addRow(new Object[]{"email", s});
        }
        for (int i = 0; i < nomeSocial.size(); i++)
            tableModel.addRow(new Object[]{nomeSocial.get(i), tagSocial.get(i)});


        bChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
