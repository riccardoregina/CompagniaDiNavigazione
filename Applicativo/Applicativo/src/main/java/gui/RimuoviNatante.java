package gui;

import controller.ControllerCompagnia;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Rimuovi natante.
 */
public class RimuoviNatante {
    private JLabel Didascalia1;
    private JLabel Didascalia2;
    private JComboBox comboBox1;
    private JButton buttonElimina;
    private JTable table1;
    private JPanel panel1;
    private JPanel panel1_1;
    private JPanel panel1_2;
    private JPanel panel1_3;
    private JButton bIndietro;
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
     * Instantiates a new Rimuovi natante.
     *
     * @param frameChiamante      the frame chiamante
     * @param controllerCompagnia the controller compagnia
     */
    public RimuoviNatante(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {

        frame = new JFrame("rimuoviNatante");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ArrayList<String> nome = new ArrayList<String>();
        ArrayList<Integer> capPasseggeri = new ArrayList<Integer>();
        ArrayList<Integer> capVeicoli = new ArrayList<Integer>();
        ArrayList<String> tipo = new ArrayList<String>();

        controllerCompagnia.visualizzaNatanti(nome, capPasseggeri, capVeicoli, tipo);

        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][][][]{}, new String[]{"nome", "Tipo", "num Passeggeri", "num Veicoli"});
        table1.setModel(tableModel);
        if (tipo != null) {
            for (int i = 0; i < tipo.size(); i++) {
                tableModel.addRow(new Object[]{tipo.get(i), nome.get(i), capPasseggeri.get(i), capVeicoli.get(i)});
                comboBox1.addItem(nome.get(i));
            }
        }
        table1.setDefaultEditor(Object.class, null);

        buttonElimina.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeEliminato = (String) comboBox1.getSelectedItem();
                if(controllerCompagnia.rimuoviNatante(nomeEliminato)) {
                    JOptionPane.showMessageDialog(null, "natante eliminato");
                } else {
                    JOptionPane.showMessageDialog(null, "non è stato possibile rimuovere il natante");
                }
            }
        });
        bIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
