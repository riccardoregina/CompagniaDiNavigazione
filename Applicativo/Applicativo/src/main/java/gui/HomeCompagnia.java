package gui;

import controller.ControllerCompagnia;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The type Home compagnia.
 */
public class HomeCompagnia {
    private JPanel panel1;
    private JPanel panelCorse;
    private JButton bCreaCorsa;
    private JButton bModificaCorsa;
    private JButton bAggiungiNatante;
    private JButton bRimuoviNatante;
    private JPanel panelNatanti;
    private JScrollPane scrollPaneCorse;
    private JTable tableCorse;
    private JScrollPane scrollPaneNatanti;
    private JLabel corseErogateLabel;
    private JLabel natantiDisponibiliLabel;
    private JButton buttonEliminaCorsa;
    private JTable tableNatanti;
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
     * Instantiates a new Home compagnia.
     *
     * @param frameChiamante      the frame chiamante
     * @param controllerCompagnia the controller compagnia
     */
    public HomeCompagnia(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {

        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("homeCompagnia");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width / 1.2), (int) (screenSize.height / 1.2));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        bRimuoviNatante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idxNatante = tableNatanti.getSelectedRow();
                if (idxNatante == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona il natante da eliminare");
                    return;
                }

                String nat = (String) tableNatanti.getValueAt(idxNatante, 0);
                controllerCompagnia.rimuoviNatante(nat);
                aggiornaTabellaNatanti();
                aggiornaTabellaCorse();
            }
        });
        bAggiungiNatante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiNatante aggiungiNatante = new AggiungiNatante(frame, controllerCompagnia);
                frame.setVisible(false);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                aggiornaTabellaNatanti();
                aggiornaTabellaCorse();
            }
        });
        bCreaCorsa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreaCorse creaCorse = new CreaCorse(frame, controllerCompagnia);
                frame.setVisible(false);
            }
        });
        bModificaCorsa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificaCorsa modificaCorsa = new ModificaCorsa(frame, controllerCompagnia);
                frame.setVisible(false);
            }
        });

        buttonEliminaCorsa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCorse.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona la corsa da eliminare");
                    return;
                }

                int idCorsaDaEliminare = (int) tableCorse.getValueAt(selectedRow, 0);
                controllerCompagnia.eliminaCorsaRegolare(idCorsaDaEliminare);
                aggiornaTabellaCorse();
            }
        });
    }

    private void aggiornaTabellaCorse() {
        DefaultTableModel modelCorse;
        String[] col;
        Object[][] data;

        ArrayList<Integer> idCorsa = new ArrayList<Integer>();
        ArrayList<String> portoPartenza = new ArrayList<String>();
        ArrayList<String> portoArrivo = new ArrayList<String>();
        ArrayList<String> natante = new ArrayList<String>();
        ArrayList<LocalTime> orarioPartenza = new ArrayList<LocalTime>();
        ArrayList<LocalTime> orarioArrivo = new ArrayList<LocalTime>();

        controllerCompagnia.visualizzaCorseRegolari(idCorsa, portoPartenza, portoArrivo, natante, orarioPartenza, orarioArrivo);

        col = new String[]{"ID", "Partenza", "Orario", "Arrivo", "Orario", "Natante"};
        data = new Object[idCorsa.size()][6];
        for (int i = 0; i < idCorsa.size(); i++) {
            data[i][0] = idCorsa.get(i);
            data[i][1] = portoPartenza.get(i);
            data[i][2] = orarioPartenza.get(i);
            data[i][3] = portoArrivo.get(i);
            data[i][4] = orarioArrivo.get(i);
            data[i][5] = natante.get(i);
        }
        modelCorse = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCorse = new JTable(modelCorse);
        tableCorse.getTableHeader().setReorderingAllowed(false);
        ListSelectionModel selectionModel = tableCorse.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneCorse.setViewportView(tableCorse);
    }

    private void aggiornaTabellaNatanti() {
        DefaultTableModel modelNatanti;
        String[] col;
        Object[][] data;

        ArrayList<String> nome = new ArrayList<String>();
        ArrayList<Integer> capPasseggeri = new ArrayList<Integer>();
        ArrayList<Integer> capVeicoli = new ArrayList<Integer>();
        ArrayList<String> tipo = new ArrayList<String>();

        controllerCompagnia.visualizzaNatanti(nome, capPasseggeri, capVeicoli, tipo);

        col = new String[]{"Nome", "Tipo", "Passeggeri", "Veicoli"};
        data = new Object[nome.size()][4];
        for (int i = 0; i < nome.size(); i++) {
            data[i][0] = nome.get(i);
            data[i][1] = tipo.get(i);
            data[i][2] = capPasseggeri.get(i);
            data[i][3] = capVeicoli.get(i);
        }

        modelNatanti = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableNatanti = new JTable(modelNatanti);
        tableNatanti.getTableHeader().setReorderingAllowed(false);
        ListSelectionModel selectionModel = tableNatanti.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneNatanti.setViewportView(tableNatanti);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        scrollPaneNatanti = new JScrollPane();
        panel1.add(scrollPaneNatanti, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        natantiDisponibiliLabel = new JLabel();
        Font natantiDisponibiliLabelFont = this.$$$getFont$$$(null, -1, 20, natantiDisponibiliLabel.getFont());
        if (natantiDisponibiliLabelFont != null) natantiDisponibiliLabel.setFont(natantiDisponibiliLabelFont);
        natantiDisponibiliLabel.setText("Natanti Disponibili");
        panel1.add(natantiDisponibiliLabel, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelCorse = new JPanel();
        panelCorse.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelCorse, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        bModificaCorsa = new JButton();
        bModificaCorsa.setText("Modifica Corsa");
        panelCorse.add(bModificaCorsa, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bCreaCorsa = new JButton();
        bCreaCorsa.setText("Crea Corsa ");
        panelCorse.add(bCreaCorsa, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelNatanti = new JPanel();
        panelNatanti.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelNatanti, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        bAggiungiNatante = new JButton();
        bAggiungiNatante.setText("Aggiungi Natante");
        panelNatanti.add(bAggiungiNatante, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bRimuoviNatante = new JButton();
        bRimuoviNatante.setText("Rimuovi Natante");
        panelNatanti.add(bRimuoviNatante, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        corseErogateLabel = new JLabel();
        Font corseErogateLabelFont = this.$$$getFont$$$(null, -1, 20, corseErogateLabel.getFont());
        if (corseErogateLabelFont != null) corseErogateLabel.setFont(corseErogateLabelFont);
        corseErogateLabel.setText("Corse Erogate");
        panel1.add(corseErogateLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneCorse = new JScrollPane();
        panel1.add(scrollPaneCorse, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonEliminaCorsa = new JButton();
        buttonEliminaCorsa.setText("EliminaCorsa");
        panel1.add(buttonEliminaCorsa, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), new Dimension(140, -1), 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
