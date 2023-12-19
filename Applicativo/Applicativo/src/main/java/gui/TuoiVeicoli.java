package gui;

import javax.swing.*;
import controller.ControllerCliente;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Tuoi veicoli.
 */
public class TuoiVeicoli {
    private JPanel panel1;
    private JTable table1;
    private JLabel label1;
    private JButton bIndietro;
    public JFrame frame;
    public JFrame frameChiamante;
    public ControllerCliente controllerCliente;

    public TuoiVeicoli (JFrame frameChiamante, ControllerCliente controllerCliente) {
        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("homeCliente");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ArrayList<String> targhe = new ArrayList<String>();
        ArrayList<String> tipi = new ArrayList<String>();
        controllerCliente.visualizzaVeicoli(targhe, tipi);

        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableModel tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Tipo Veicolo", "Targa"});
        table1.setModel(tableModel);
        if (tipi != null) {
            for (int i = 0; i < tipi.size(); i++)
                tableModel.addRow(new Object[]{tipi.get(i), targhe.get(i)});

        }
        bIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });
    }
}
