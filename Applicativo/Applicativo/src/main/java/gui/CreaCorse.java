package gui;

import controller.ControllerCompagnia;
import unnamed.DatePicker;
import unnamed.NonStandardStringFunctions;
import unnamed.Pair;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class CreaCorse {
    private JPanel panel1;
    private JButton bCrea;
    private JComboBox cbPortoArrivo;
    private JComboBox cbPortoPartenza;
    private JComboBox cbNatante;
    private JPanel panel1_1;
    private JCheckBox checkLun;
    private JCheckBox checkMer;
    private JCheckBox checkGio;
    private JCheckBox checkVen;
    private JCheckBox checkSab;
    private JCheckBox checkMar;
    private JCheckBox checkDom;
    private JTextField tfCosto;
    private JTextField tfSconto;
    private JTextField tfCostoPrevendita;
    private JTextField tfCostoVeicolo;
    private JTextField tfCostoBagaglio;
    private SpinnerNumberModel modelOreP = new SpinnerNumberModel(0, 0, 23, 1);
    private SpinnerNumberModel modelMinutiP = new SpinnerNumberModel(0, 0, 59, 1);
    private SpinnerNumberModel modelOreA = new SpinnerNumberModel(0, 0, 23, 1);
    private SpinnerNumberModel modelMinutiA = new SpinnerNumberModel(0, 0, 59, 1);
    private JPanel panel1_2;
    private JSpinner spinnerOraP;
    private JSpinner spinnerOraA;
    private JSpinner spinnerMinP;
    private JSpinner spinnerMinA;
    private JPanel panelPeriodo;
    private JButton buttonCreaPeriodo;
    private JLabel dip;
    private JLabel dfp;
    private JTextField tfDataFinePeriodo;
    private JButton bScalo;
    private JTextField tfDataInizioPeriodo;
    private JButton buttonChiudi;
    private JLabel labelCreaCorsa;
    private JButton bCal1;
    private JButton bCal2;
    private JButton bEliminaPeriodo;
    private JScrollPane scrollPanePeriodi;
    private JLabel labelX;
    private JLabel labelAttracco;
    private JLabel labelRipartenza;
    private JLabel labelScalo;
    private JButton bEliminaScalo;
    private JLabel labelCostoIntero;
    private JLabel labelPrevendita;
    private JLabel labelBagaglio;
    private JLabel labelSconto;
    private JLabel labelVeicolo;
    private JLabel labelDest;
    private JLabel labelPart;
    private JLabel labelNatante;
    private JTable tablePeriodi;
    public JFrame frame;
    public JFrame frameChiamante;
    public ControllerCompagnia controllerCompagnia;
    public ArrayList<LocalDate> listaInizioPer;
    public ArrayList<LocalDate> listaFinePer;
    public ArrayList<String> listaGiorniAttivi;
    public ArrayList<Pair> portoScalo = null;
    public ArrayList<LocalTime> oraAttracco = null;
    public ArrayList<LocalTime> oraRipartenza = null;

    public CreaCorse(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {
        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("crea Corsa");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width / 1.2), (int) (screenSize.height / 1.2));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        labelScalo.setVisible(false);
        labelAttracco.setVisible(false);
        labelRipartenza.setVisible(false);
        bEliminaScalo.setEnabled(false);

        portoScalo = new ArrayList<Pair>();
        oraAttracco = new ArrayList<LocalTime>();
        oraRipartenza = new ArrayList<LocalTime>();

        listaInizioPer = new ArrayList<LocalDate>();
        listaFinePer = new ArrayList<LocalDate>();
        listaGiorniAttivi = new ArrayList<String>();

        tfDataInizioPeriodo.setEditable(false);
        tfDataInizioPeriodo.setText(String.valueOf(LocalDate.now().format(formatter)));
        tfDataFinePeriodo.setEditable(false);
        tfDataFinePeriodo.setText(String.valueOf(LocalDate.now().format(formatter)));

        spinnerMinA.setModel(modelMinutiA);
        spinnerMinP.setModel(modelMinutiP);
        spinnerOraP.setModel(modelOreP);
        spinnerOraA.setModel(modelOreA);

        ArrayList<Pair> porti = new ArrayList<Pair>();
        ArrayList<String> natanti = new ArrayList<String>();

        controllerCompagnia.visualizzaPorti(porti);
        for (Pair porto : porti) {
            cbPortoPartenza.addItem(porto.last);
            cbPortoArrivo.addItem(porto.last);
        }

        controllerCompagnia.visualizzaNatanti(natanti);
        for (String natante : natanti) {
            cbNatante.addItem(natante);
        }
        if (controllerCompagnia.isTraghetto((String) cbNatante.getSelectedItem())) {
            tfCostoVeicolo.setEditable(true);
            tfCostoVeicolo.setText("");
        } else {
            tfCostoVeicolo.setEditable(false);
            tfCostoVeicolo.setText("0");
        }

        cbNatante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controllerCompagnia.isTraghetto((String) cbNatante.getSelectedItem())) {
                    tfCostoVeicolo.setEditable(true);
                    tfCostoVeicolo.setText("");
                } else {
                    tfCostoVeicolo.setEditable(false);
                    tfCostoVeicolo.setText("0");
                }
            }
        });

        bCal1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfDataInizioPeriodo.setText(new DatePicker(frame).setPickedDate());
            }
        });

        bCal2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfDataFinePeriodo.setText(new DatePicker(frame).setPickedDate());
            }
        });

        bCrea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idxPortoPartenza = cbPortoPartenza.getSelectedIndex();
                int idPortoPartenza = (int) porti.get(idxPortoPartenza).first;

                int idxPortoArrivo = cbPortoArrivo.getSelectedIndex();
                int idPortoArrivo = (int) porti.get(idxPortoArrivo).first;

                String costo = tfCosto.getText();
                float costoIntero;
                String valoreSconto = tfSconto.getText();
                float sconto;
                String prevendita = tfCostoPrevendita.getText();
                float costoPrevendita;
                String bagaglio = tfCostoBagaglio.getText();
                float costoBagaglio;
                String veicolo = tfCostoVeicolo.getText();
                float costoVeicolo;

                String nomeNatante = (String) cbNatante.getSelectedItem();

                int oraPart = (int) spinnerOraP.getValue();
                int oraArrivo = (int) spinnerOraA.getValue();
                int minPart = (int) spinnerMinP.getValue();
                int minArrivo = (int) spinnerMinA.getValue();

                LocalTime orarioPartenza = LocalTime.of(oraPart, minPart);
                LocalTime orarioArrivo = LocalTime.of(oraArrivo, minArrivo);

                try {
                    costoIntero = Float.parseFloat(costo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Inserisci un valore numerico in Costo Intero");
                    return;
                }
                if (costoIntero < 0) {
                    JOptionPane.showMessageDialog(null, "Inserisci un valore positivo in Costo Intero");
                    return;
                }

                try {
                    sconto = Float.parseFloat(valoreSconto);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Inserisci un valore numerico in Sconto");
                    return;
                }
                if (sconto < 0 || sconto > 100) {
                    JOptionPane.showMessageDialog(null, "inserisci uno Sconto compreso tra 0 e 100");
                    return;
                }

                try {
                    costoPrevendita = Float.parseFloat(prevendita);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Inserisci un valore numerico in Prevendita");
                    return;
                }
                if (costoPrevendita < 0) {
                    JOptionPane.showMessageDialog(null, "Inserisci in sconto un valore positivo in Prevendita");
                    return;
                }

                try {
                    costoVeicolo = Float.parseFloat(veicolo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore numerico in Veicolo");
                    return;
                }
                if (costoVeicolo < 0) {
                    JOptionPane.showMessageDialog(null, "Inserisci un valore positivo in Veicolo");
                    return;
                }

                try {
                    costoBagaglio = Float.parseFloat(bagaglio);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Inserisci un valore numerico in Bagaglio");
                    return;
                }
                if (costoBagaglio < 0) {
                    JOptionPane.showMessageDialog(null, "Inserisci un valore positivo in Bagaglio");
                    return;
                }

                if (getNumberOfOverlap(listaInizioPer, listaFinePer, listaGiorniAttivi) > 1) {
                    JOptionPane.showMessageDialog(null, "Non è possibile inserire periodi che si intersecano");
                    return;
                }

                AtomicInteger idCorsa = new AtomicInteger(-1);

                if (!controllerCompagnia.creaCorsa(idPortoPartenza, idPortoArrivo, orarioPartenza, orarioArrivo, costoIntero, sconto, costoBagaglio, costoPrevendita, costoVeicolo, nomeNatante, idCorsa)) {
                    JOptionPane.showMessageDialog(null, "Non è stato possibile creare la corsa");
                    return;
                } else {
                    if (!portoScalo.isEmpty()) {
                        if (!controllerCompagnia.aggiungiScalo(idCorsa.get(), (Integer) portoScalo.getFirst().first, oraAttracco.getFirst(), oraRipartenza.getFirst())) {
                            JOptionPane.showMessageDialog(null, "Corsa creata ma non è stato possibile aggiungere lo scalo");
                        }
                    }
                }

                if (!listaInizioPer.isEmpty()) {
                    for (int i = 0; i < listaInizioPer.size(); i++) {
                        AtomicInteger idPeriodo = new AtomicInteger(-1);
                        if (controllerCompagnia.aggiungiPeriodo(listaGiorniAttivi.get(i), listaInizioPer.get(i), listaFinePer.get(i), idPeriodo)) {
                            if (!controllerCompagnia.attivaCorsaInPeriodo(idCorsa.get(), idPeriodo.get())) {
                                JOptionPane.showMessageDialog(null, "Corsa creata ma non è stato possibile attaccare dal periodo" + (i + 1) + " inserito in poi");
                                return;
                            }
                        }
                    }
                }

                JOptionPane.showMessageDialog(null, "Corsa creata!");
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        buttonCreaPeriodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfDataInizioPeriodo.getText().isEmpty() || tfDataFinePeriodo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inserire entrambe le date");
                    return;
                }

                LocalDate dataInizioP = LocalDate.parse(tfDataInizioPeriodo.getText(), formatter);
                LocalDate dataFineP = LocalDate.parse(tfDataFinePeriodo.getText(), formatter);

                if (dataFineP.isBefore(dataInizioP)) {
                    JOptionPane.showMessageDialog(null, "La data di fine periodo deve essere maggiore della data di inizio periodo!");
                    return;
                }

                listaInizioPer.add(dataInizioP);
                listaFinePer.add(dataFineP);

                StringBuilder sb = new StringBuilder();

                sb.append(checkDom.isSelected() ? '1' : '0');
                sb.append(checkLun.isSelected() ? '1' : '0');
                sb.append(checkMar.isSelected() ? '1' : '0');
                sb.append(checkMer.isSelected() ? '1' : '0');
                sb.append(checkGio.isSelected() ? '1' : '0');
                sb.append(checkVen.isSelected() ? '1' : '0');
                sb.append(checkSab.isSelected() ? '1' : '0');

                listaGiorniAttivi.add(sb.toString());

                checkDom.setSelected(false);
                checkLun.setSelected(false);
                checkMar.setSelected(false);
                checkMer.setSelected(false);
                checkGio.setSelected(false);
                checkVen.setSelected(false);
                checkSab.setSelected(false);

                aggiornaTabellaPeriodi();
            }
        });

        bEliminaPeriodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaInizioPer.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nessun periodo da eliminare");
                    return;
                }

                int selectedRow = tablePeriodi.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona il periodo da eliminare");
                    return;
                }

                listaInizioPer.remove(selectedRow);
                listaFinePer.remove(selectedRow);
                listaGiorniAttivi.remove(selectedRow);

                aggiornaTabellaPeriodi();
            }
        });

        buttonChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });


        bScalo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiScalo aggiungiScalo = new AggiungiScalo(frame, porti, portoScalo, oraAttracco, oraRipartenza);
                frame.setVisible(false);
            }
        });

        bEliminaScalo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portoScalo.clear();
                oraAttracco.clear();
                oraRipartenza.clear();
                labelScalo.setVisible(false);
                labelAttracco.setVisible(false);
                labelRipartenza.setVisible(false);
                labelX.setText("Nessuno scalo aggiunto");
                bEliminaScalo.setEnabled(false);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                if (oraAttracco == null || oraAttracco.isEmpty()) {
                    labelX.setText("Nessuno scalo aggiunto");
                } else {
                    labelX.setText("Uno scalo è stato aggiunto:");
                    labelScalo.setText("Porto: " + portoScalo.getFirst().last.toString().toUpperCase());
                    labelScalo.setVisible(true);
                    labelAttracco.setText("Ora Attracco: " + oraAttracco.getFirst());
                    labelAttracco.setVisible(true);
                    labelRipartenza.setText("OraRipartenza: " + oraRipartenza.getFirst());
                    labelRipartenza.setVisible(true);
                    bEliminaScalo.setEnabled(true);
                }
            }
        });
    }

    private void aggiornaTabellaPeriodi() {
        DefaultTableModel model;
        String[] col = new String[]{"Inizio Periodo", "Fine Periodo", "Giorni"};
        Object[][] data = new Object[listaInizioPer.size()][3];

        for (int i = 0; i < listaInizioPer.size(); i++) {
            data[i][0] = listaInizioPer.get(i);
            data[i][1] = listaFinePer.get(i);
            data[i][2] = NonStandardStringFunctions.bitStringToGiorni(listaGiorniAttivi.get(i));
        }
        model = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePeriodi = new JTable(model);
        tablePeriodi.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablePeriodi.setDefaultRenderer(Object.class, centerRenderer);
        ListSelectionModel selectionModel = tablePeriodi.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPanePeriodi.setViewportView(tablePeriodi);
    }

    private ArrayList<LocalDate> getDateOverlap(ArrayList<LocalDate> inizioPer, ArrayList<LocalDate> finePer) {
        ArrayList<LocalDate> dateOverlap = new ArrayList<LocalDate>();
        HashSet<LocalDate> dateViste = new HashSet<LocalDate>();

        for (int i = 0; i < inizioPer.size(); i++) {
            LocalDate firstDate = inizioPer.get(i);
            LocalDate lastDate = finePer.get(i);

            for (LocalDate currentDate = firstDate; !currentDate.isAfter(lastDate); currentDate = currentDate.plusDays(1)) {
                if (!dateViste.contains(currentDate)) {
                    dateViste.add(currentDate);
                } else {
                    dateOverlap.add(currentDate);
                }
            }
        }

        return dateOverlap;
    }

    private int countDay(ArrayList<String> giorni, int day) {
        int cont = 0;
        for (int i = 0; i < giorni.size(); i++) {
            if (day != 7) {
                if (giorni.get(i).charAt(day) == '1') {
                    cont++;
                }
            }
        }
        return (cont > 1) ? 1 : 0;
    }

    private int getNumberOfOverlap(ArrayList<LocalDate> inizioPer, ArrayList<LocalDate> finePer, ArrayList<String> giorni) {
        ArrayList<LocalDate> dateOverlap = getDateOverlap(inizioPer, finePer);
        int overlap = 0;
        for (LocalDate currentDate : dateOverlap) {
            int day = currentDate.getDayOfWeek().getValue();
            overlap += countDay(giorni, day);
        }

        return overlap;
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
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(12, 9, new Insets(0, 0, 0, 0), -1, -1));
        panel1_1 = new JPanel();
        panel1_1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 8, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel1_1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 7, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        checkLun = new JCheckBox();
        checkLun.setText("Lun");
        panel1_1.add(checkLun, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1_1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        checkMar = new JCheckBox();
        checkMar.setText("Mar");
        panel1_1.add(checkMar, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkMer = new JCheckBox();
        checkMer.setSelected(false);
        checkMer.setText("Mer");
        panel1_1.add(checkMer, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkGio = new JCheckBox();
        checkGio.setText("Gio");
        panel1_1.add(checkGio, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkVen = new JCheckBox();
        checkVen.setText("Ven");
        panel1_1.add(checkVen, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkSab = new JCheckBox();
        checkSab.setText("Sab");
        panel1_1.add(checkSab, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkDom = new JCheckBox();
        checkDom.setText("Dom");
        panel1_1.add(checkDom, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1_2 = new JPanel();
        panel1_2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel1_2, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spinnerOraP = new JSpinner();
        spinnerOraP.setEnabled(true);
        panel1_2.add(spinnerOraP, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerOraA = new JSpinner();
        panel1_2.add(spinnerOraA, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerMinP = new JSpinner();
        panel1_2.add(spinnerMinP, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerMinA = new JSpinner();
        panel1_2.add(spinnerMinA, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Orario Partenza");
        panel1_2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Orario Arrivo");
        panel1_2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonChiudi = new JButton();
        buttonChiudi.setText("CHIUDI");
        panel1_2.add(buttonChiudi, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bCrea = new JButton();
        bCrea.setText("CREA");
        panel1_2.add(bCrea, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1_2.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel1_2.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelPeriodo = new JPanel();
        panelPeriodo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelPeriodo, new com.intellij.uiDesigner.core.GridConstraints(4, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfDataInizioPeriodo = new JTextField();
        tfDataInizioPeriodo.setText("yyyy-mm-dd");
        panelPeriodo.add(tfDataInizioPeriodo, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panelPeriodo.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(10, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tfDataFinePeriodo = new JTextField();
        tfDataFinePeriodo.setText("yyyy-mm-dd");
        panelPeriodo.add(tfDataFinePeriodo, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dip = new JLabel();
        dip.setText("Data inizio periodo");
        panelPeriodo.add(dip, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dfp = new JLabel();
        dfp.setText("Data fine periodo");
        panelPeriodo.add(dfp, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCreaPeriodo = new JButton();
        buttonCreaPeriodo.setText("Aggiungi Periodo");
        panelPeriodo.add(buttonCreaPeriodo, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bCal1 = new JButton();
        bCal1.setText("...");
        panelPeriodo.add(bCal1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(25, -1), new Dimension(25, -1), 0, false));
        bCal2 = new JButton();
        bCal2.setText("...");
        panelPeriodo.add(bCal2, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(25, -1), new Dimension(25, -1), 0, false));
        bEliminaPeriodo = new JButton();
        bEliminaPeriodo.setText("Elimina Periodo");
        panelPeriodo.add(bEliminaPeriodo, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelX = new JLabel();
        labelX.setText("Nessuno scalo aggiunto");
        panelPeriodo.add(labelX, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelAttracco = new JLabel();
        labelAttracco.setText("");
        panelPeriodo.add(labelAttracco, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelScalo = new JLabel();
        labelScalo.setText("");
        panelPeriodo.add(labelScalo, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRipartenza = new JLabel();
        labelRipartenza.setText("");
        panelPeriodo.add(labelRipartenza, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bEliminaScalo = new JButton();
        bEliminaScalo.setText("Elimina Scalo");
        panelPeriodo.add(bEliminaScalo, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        scrollPanePeriodi = new JScrollPane();
        panel1.add(scrollPanePeriodi, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(300, 250), new Dimension(300, 250), 0, false));
        final JLabel label3 = new JLabel();
        label3.setHorizontalAlignment(0);
        label3.setText("Nessun periodo aggiunto");
        label3.setVerticalAlignment(1);
        scrollPanePeriodi.setViewportView(label3);
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(7, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(11, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelPrevendita = new JLabel();
        labelPrevendita.setText("Costo Prevendita");
        panel2.add(labelPrevendita, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelCostoIntero = new JLabel();
        labelCostoIntero.setText("Costo Intero");
        panel2.add(labelCostoIntero, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelBagaglio = new JLabel();
        labelBagaglio.setText("Costo Bagaglio");
        panel2.add(labelBagaglio, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCosto = new JTextField();
        tfCosto.setText("");
        panel2.add(tfCosto, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelSconto = new JLabel();
        labelSconto.setText("Percentuale Sconto");
        panel2.add(labelSconto, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfSconto = new JTextField();
        tfSconto.setText("");
        panel2.add(tfSconto, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfCostoPrevendita = new JTextField();
        tfCostoPrevendita.setText("");
        panel2.add(tfCostoPrevendita, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tfCostoBagaglio = new JTextField();
        tfCostoBagaglio.setText("");
        panel2.add(tfCostoBagaglio, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelVeicolo = new JLabel();
        labelVeicolo.setText("Costo Veicolo");
        panel2.add(labelVeicolo, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tfCostoVeicolo = new JTextField();
        tfCostoVeicolo.setText("");
        panel2.add(tfCostoVeicolo, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cbNatante = new JComboBox();
        panel3.add(cbNatante, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        labelNatante = new JLabel();
        labelNatante.setText("Natante");
        panel3.add(labelNatante, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cbPortoPartenza = new JComboBox();
        panel4.add(cbPortoPartenza, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelDest = new JLabel();
        labelDest.setText("Destinazione");
        panel4.add(labelDest, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPart = new JLabel();
        labelPart.setText("Partenza");
        panel4.add(labelPart, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cbPortoArrivo = new JComboBox();
        panel4.add(cbPortoArrivo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bScalo = new JButton();
        bScalo.setText("Aggiungi Scalo");
        panel4.add(bScalo, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelCreaCorsa = new JLabel();
        Font labelCreaCorsaFont = this.$$$getFont$$$(null, -1, 20, labelCreaCorsa.getFont());
        if (labelCreaCorsaFont != null) labelCreaCorsa.setFont(labelCreaCorsaFont);
        labelCreaCorsa.setText("Crea Corsa");
        panel1.add(labelCreaCorsa, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
