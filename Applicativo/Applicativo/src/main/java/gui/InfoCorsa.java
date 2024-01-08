package gui;

import controller.ControllerCompagnia;
import unnamed.DateOverLap;
import unnamed.DatePicker;
import unnamed.NonStandardStringFunctions;
import unnamed.Pair;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
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
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class InfoCorsa {
    private JPanel contentPane;
    private JLabel label1;
    private JLabel label2;
    private JLabel labelMain;
    private JTable tableCorse;
    private JTable tablePeriodi;
    private JScrollPane scrollPaneCorse;
    private JButton buttonChiudi;
    private JScrollPane scrollPanePeriodi;
    private JLabel labelModifica;
    private JLabel labelPeriodi;
    private JPanel panelPeriodo;
    private JTextField tfDataInizioPeriodo;
    private JTextField tfDataFinePeriodo;
    private JLabel dip;
    private JLabel dfp;
    private JButton buttonCreaPeriodo;
    private JButton bCal1;
    private JButton bCal2;
    private JButton bEliminaPeriodo;
    private JButton buttonCreaScalo;
    private JLabel labelX;
    private JLabel labelAttracco;
    private JLabel labelScalo;
    private JLabel labelRipartenza;
    private JButton buttonAggiungiScalo;
    private JButton buttonEliminaScalo;
    private JCheckBox checkLun;
    private JCheckBox checkMar;
    private JCheckBox checkMer;
    private JCheckBox checkGio;
    private JCheckBox checkVen;
    private JCheckBox checkSab;
    private JCheckBox checkDom;
    public JFrame frame;
    public JFrame frameChiamante;
    public ControllerCompagnia controllerCompagnia;
    private int idCorsaSup;
    public ArrayList<String> portoPartenza;
    public ArrayList<String> portoArrivo;
    public ArrayList<String> natante;
    public ArrayList<LocalTime> orarioPartenza;
    public ArrayList<LocalTime> orarioArrivo;
    public ArrayList<Float> costoIntero;
    public ArrayList<Float> scontoRidotto;
    public ArrayList<Float> costoBagaglio;
    public ArrayList<Float> costoPrevendita;
    public ArrayList<Float> costoVeicolo;
    public ArrayList<Integer> listaIdPeriodo;
    public ArrayList<LocalDate> inizioPer;
    public ArrayList<LocalDate> finePer;
    public ArrayList<String> giorniAttivi;
    public ArrayList<Integer> idSottoCorse;
    public ArrayList<Pair> portoScalo;
    public ArrayList<LocalTime> oraAttracco;
    public ArrayList<LocalTime> oraRipartenza;

    public InfoCorsa(JFrame frameChiamante, ControllerCompagnia controllerCompagnia, int idCorsa) {
        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("Info Corsa");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width / 1.2), (int) (screenSize.height / 1.2));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        tfDataInizioPeriodo.setText(LocalDate.now().format(formatter));
        tfDataFinePeriodo.setText(LocalDate.now().format(formatter));

        if (controllerCompagnia.isSottoCorsa(idCorsa)) {
            idCorsaSup = controllerCompagnia.getCorsaSup(idCorsa);
            label1.setText("La corsa selezionata è una sottocorsa della corsa " + idCorsaSup);

        } else {
            idCorsaSup = idCorsa;
            label1.setText("La corsa selezionata è una corsa principale");
        }

        if (controllerCompagnia.haveSottoCorse(idCorsaSup)) {
            buttonCreaScalo.setEnabled(false);
            buttonAggiungiScalo.setEnabled(false);
            buttonEliminaScalo.setVisible(false);
            buttonEliminaScalo.setEnabled(false);
        } else {
            buttonCreaScalo.setEnabled(true);
            buttonAggiungiScalo.setEnabled(false);
            buttonEliminaScalo.setVisible(false);
            buttonEliminaScalo.setEnabled(false);
        }

        aggiornaTabellaCorse();
        aggiornaTabellaPeriodi();

        buttonCreaScalo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Pair> porti = new ArrayList<Pair>();
                portoScalo = new ArrayList<Pair>();
                oraAttracco = new ArrayList<LocalTime>();
                oraRipartenza = new ArrayList<LocalTime>();
                controllerCompagnia.visualizzaPorti(porti);
                AggiungiScalo aggiungiScalo = new AggiungiScalo(frame, porti, portoScalo, oraAttracco, oraRipartenza);
                frame.setVisible(false);
            }
        });

        buttonAggiungiScalo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!controllerCompagnia.aggiungiScalo(idCorsaSup, (Integer) portoScalo.getFirst().first, oraAttracco.getFirst(), oraRipartenza.getFirst())) {
                    JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere lo scalo");
                    return;
                }

                aggiornaTabellaCorse();
                portoScalo.clear();
                oraAttracco.clear();
                oraRipartenza.clear();
                buttonEliminaScalo.setEnabled(false);
                buttonEliminaScalo.setVisible(false);
                buttonCreaScalo.setEnabled(false);
                buttonAggiungiScalo.setEnabled(false);
            }
        });

        buttonEliminaScalo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portoScalo.clear();
                oraAttracco.clear();
                oraRipartenza.clear();
                labelScalo.setVisible(false);
                labelAttracco.setVisible(false);
                labelRipartenza.setVisible(false);
                labelX.setText("Nessuno scalo aggiunto");
                buttonEliminaScalo.setVisible(false);
                buttonEliminaScalo.setEnabled(false);
                buttonAggiungiScalo.setEnabled(false);
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
                    buttonAggiungiScalo.setEnabled(true);
                    buttonEliminaScalo.setVisible(true);
                    buttonEliminaScalo.setEnabled(true);
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

                StringBuilder sb = new StringBuilder();

                sb.append(checkDom.isSelected() ? '1' : '0');
                sb.append(checkLun.isSelected() ? '1' : '0');
                sb.append(checkMar.isSelected() ? '1' : '0');
                sb.append(checkMer.isSelected() ? '1' : '0');
                sb.append(checkGio.isSelected() ? '1' : '0');
                sb.append(checkVen.isSelected() ? '1' : '0');
                sb.append(checkSab.isSelected() ? '1' : '0');

                checkDom.setSelected(false);
                checkLun.setSelected(false);
                checkMar.setSelected(false);
                checkMer.setSelected(false);
                checkGio.setSelected(false);
                checkVen.setSelected(false);
                checkSab.setSelected(false);

                AtomicInteger idPeriodo = new AtomicInteger(-1);

                inizioPer.add(dataInizioP);
                finePer.add(dataFineP);
                giorniAttivi.add(sb.toString());
                if (DateOverLap.getNumberOfOverlap(inizioPer, finePer, giorniAttivi) > 0) {
                    JOptionPane.showMessageDialog(null, "Non è possibile inserire periodi che si intersecano");
                } else {
                    if (controllerCompagnia.aggiungiPeriodo(sb.toString(), dataInizioP, dataFineP, idPeriodo)) {
                        listaIdPeriodo.add(idPeriodo.get());
                        if (controllerCompagnia.attivaCorsaInPeriodo(idCorsaSup, idPeriodo.get())) {
                            JOptionPane.showMessageDialog(null, "Periodo di attività aggiunto!");
                            aggiornaTabellaPeriodi();
                        } else {
                            JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere il periodo");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere il periodo");
                    }
                }
            }
        });

        bEliminaPeriodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tablePeriodi.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona un periodo");
                    return;
                }

                controllerCompagnia.eliminaPeriodoAttivitaPerCorsa(idCorsaSup, listaIdPeriodo.get(selectedRow));
                listaIdPeriodo.remove(selectedRow);
                inizioPer.remove(selectedRow);
                finePer.remove(selectedRow);
                giorniAttivi.remove(selectedRow);
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
    }

    private boolean isColumnEditable(int col) {
        if (col == 0 || col == 1 || col == 3 || col == 10) {
            return false;
        } else {
            if (col == 9) {
                return controllerCompagnia.isTraghetto((String) tableCorse.getValueAt(0, 10));
            }
            return true;
        }
    }

    private void aggiornaTabellaCorse() {
        portoPartenza = new ArrayList<>();
        portoArrivo = new ArrayList<>();
        natante = new ArrayList<>();
        orarioPartenza = new ArrayList<>();
        orarioArrivo = new ArrayList<>();
        costoIntero = new ArrayList<>();
        scontoRidotto = new ArrayList<>();
        costoBagaglio = new ArrayList<>();
        costoPrevendita = new ArrayList<>();
        costoVeicolo = new ArrayList<>();
        listaIdPeriodo = new ArrayList<Integer>();
        inizioPer = new ArrayList<>();
        finePer = new ArrayList<>();
        giorniAttivi = new ArrayList<>();
        idSottoCorse = new ArrayList<>();

        controllerCompagnia.visualizzaInfoCorsa(idCorsaSup, portoPartenza, portoArrivo, natante, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo, listaIdPeriodo, inizioPer, finePer, giorniAttivi, idSottoCorse);

        String[] col = new String[]{"ID", "Partenza", "Ore", "Arrivo", "Ore", "C. Intero", "Sconto", "C. Bagaglio", "C. Prevendita", "C. Veicolo", "Natante"};
        Object[][] data = new Object[idSottoCorse.size() + 1][11];
        data[0][0] = idCorsaSup;
        data[0][1] = portoPartenza.getFirst();
        data[0][2] = orarioPartenza.getFirst();
        data[0][3] = portoArrivo.getFirst();
        data[0][4] = orarioArrivo.getFirst();
        data[0][5] = costoIntero.getFirst();
        data[0][6] = scontoRidotto.getFirst();
        data[0][7] = costoBagaglio.getFirst();
        data[0][8] = costoPrevendita.getFirst();
        data[0][9] = costoVeicolo.getFirst();
        data[0][10] = natante.getFirst();
        for (int i = 0; i < idSottoCorse.size(); i++) {
            controllerCompagnia.visualizzaInfoCorsa(idSottoCorse.get(i), portoPartenza, portoArrivo, natante, orarioPartenza, orarioArrivo, costoIntero, scontoRidotto, costoBagaglio, costoPrevendita, costoVeicolo);
            data[i + 1][0] = idSottoCorse.get(i);
            data[i + 1][1] = portoPartenza.getFirst();
            data[i + 1][2] = orarioPartenza.getFirst();
            data[i + 1][3] = portoArrivo.getFirst();
            data[i + 1][4] = orarioArrivo.getFirst();
            data[i + 1][5] = costoIntero.getFirst();
            data[i + 1][6] = scontoRidotto.getFirst();
            data[i + 1][7] = costoBagaglio.getFirst();
            data[i + 1][8] = costoPrevendita.getFirst();
            data[i + 1][9] = costoVeicolo.getFirst();
            data[i + 1][10] = natante.getFirst();
        }
        DefaultTableModel modelCorse = new DefaultTableModel(data, col) {
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Integer.class;
                }
                return super.getColumnClass(columnIndex);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableRowSorter<DefaultTableModel> sorterCorse = new TableRowSorter<>(modelCorse);

        tableCorse = new JTable(modelCorse);
        tableCorse.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableCorse.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tableCorse.setDefaultRenderer(Object.class, centerRenderer);
        tableCorse.setRowSorter(sorterCorse);
        sorterCorse.setSortKeys(List.of(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        for (int i = 0; i < tableCorse.getColumnCount(); i++) {
            sorterCorse.setSortable(i, false);
        }
        ListSelectionModel selectionModelCorse = tableCorse.getSelectionModel();
        selectionModelCorse.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneCorse.setViewportView(tableCorse);

        tableCorse.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tableCorse.getSelectedRow();
                    int selectedColumn = tableCorse.getSelectedColumn();

                    if (selectedRow != -1 && selectedColumn != -1) {
                        if (isColumnEditable(selectedColumn)) {
                            if (selectedColumn == 2 && selectedRow == 1) {
                                JOptionPane.showMessageDialog(null, "Per cambiare questo valore devi cambiare l'orario di partenza della corsa principale");
                                tableCorse.clearSelection();
                                return;
                            }

                            if (selectedColumn == 4 && selectedRow == 2) {
                                JOptionPane.showMessageDialog(null, "Per cambiare questo valore devi cambiare l'orario di arrivo della corsa principale");
                                tableCorse.clearSelection();
                                return;
                            }

                            Object selectedValue = tableCorse.getValueAt(selectedRow, selectedColumn);
                            String inputValue = JOptionPane.showInputDialog("Inserisci il nuovo valore:", selectedValue);

                            if (inputValue != null) {
                                LocalTime orario = null;
                                Float floatValue = null;
                                if (selectedColumn == 2 || selectedColumn == 4) {
                                    try {
                                        orario = LocalTime.parse(inputValue, DateTimeFormatter.ofPattern("HH:mm"));
                                    } catch (Exception e1) {
                                        JOptionPane.showMessageDialog(null, "L'orario inserito non è nel formato HH:mm");
                                        tableCorse.clearSelection();
                                        return;
                                    }

                                    LocalTime oraPartenza = (LocalTime) tableCorse.getValueAt(0, 2);
                                    LocalTime oraArrivo = (LocalTime) tableCorse.getValueAt(0, 4);
                                    if (selectedRow == 1 || selectedRow == 2) {
                                        if (orario.isBefore(oraPartenza) || orario.isAfter(oraArrivo)) {
                                            JOptionPane.showMessageDialog(null, "L'orario deve essere incluso tra l'orario di partenza e l'orario di arrivo della tratta principale!");
                                            tableCorse.clearSelection();
                                            return;
                                        }
                                    } else {
                                        if (selectedColumn == 2) {
                                            tableCorse.setValueAt(inputValue, 1, 2);
                                        } else {
                                            tableCorse.setValueAt(inputValue, 2, 4);
                                        }
                                    }
                                }
                                if (selectedColumn == 5 || selectedColumn == 6 || selectedColumn == 7 || selectedColumn == 8 || selectedColumn == 9) {
                                    try {
                                        floatValue = Float.parseFloat(inputValue);
                                    } catch (Exception e2) {
                                        JOptionPane.showMessageDialog(null, "Il valore inserito non è un valore numerico");
                                        tableCorse.clearSelection();
                                        return;
                                    }
                                }
                                tableCorse.setValueAt(inputValue, selectedRow, selectedColumn);
                                int idCorsa = (int) tableCorse.getValueAt(selectedRow, 0);
                                switch (selectedColumn) {
                                    case 2:
                                        controllerCompagnia.modificaOrarioPartenza(idCorsa, orario);
                                        break;
                                    case 4:
                                        controllerCompagnia.modificaOrarioArrivo(idCorsa, orario);
                                        break;
                                    case 5:
                                        controllerCompagnia.modificaCostoIntero(idCorsa, floatValue);
                                        break;
                                    case 6:
                                        controllerCompagnia.modificaScontoRidotto(idCorsa, floatValue);
                                        break;
                                    case 7:
                                        controllerCompagnia.modificaCostoBagaglio(idCorsa, floatValue);
                                        break;
                                    case 8:
                                        controllerCompagnia.modificaCostoPrevendita(idCorsa, floatValue);
                                        break;
                                    case 9:
                                        controllerCompagnia.modificaCostoVeicolo(idCorsa, floatValue);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Non puoi modificare il valore di " + col[selectedColumn]);
                        }
                        tableCorse.clearSelection();
                    }
                }
            }
        });
    }

    private void aggiornaTabellaPeriodi() {
        String[] colPeriodi = new String[]{"Inizio Periodo", "Fine Periodo", "Giorni"};
        Object[][] periodi = new Object[inizioPer.size()][3];
        for (int i = 0; i < inizioPer.size(); i++) {
            periodi[i][0] = inizioPer.get(i);
            periodi[i][1] = finePer.get(i);
            periodi[i][2] = NonStandardStringFunctions.bitStringToGiorni(giorniAttivi.get(i));
        }

        DefaultTableModel modelPeriodi = new DefaultTableModel(periodi, colPeriodi) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePeriodi = new JTable(modelPeriodi);
        tablePeriodi.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tablePeriodi.setDefaultRenderer(Object.class, centerRenderer);
        ListSelectionModel selectionModelPeriodi = tablePeriodi.getSelectionModel();
        selectionModelPeriodi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPanePeriodi.setViewportView(tablePeriodi);
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
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        label1 = new JLabel();
        label1.setText("La corsa selezionata è una");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(291, 249), new Dimension(-1, 200), 0, false));
        label2 = new JLabel();
        label2.setText("Tappe ed informazioni riguardo la corsa intera");
        panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneCorse = new JScrollPane();
        panel2.add(scrollPaneCorse, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 100), new Dimension(-1, 100), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelModifica = new JLabel();
        labelModifica.setText("Per modificare orari e tariffe della corsa cliccare sulla tabella");
        panel2.add(labelModifica, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 500), new Dimension(-1, 500), 0, false));
        labelPeriodi = new JLabel();
        labelPeriodi.setText("Attiva nei periodi");
        panel5.add(labelPeriodi, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPanePeriodi = new JScrollPane();
        panel5.add(scrollPanePeriodi, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(0, 300), new Dimension(-1, 300), 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel6.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        buttonChiudi = new JButton();
        buttonChiudi.setText("Chiudi");
        panel6.add(buttonChiudi, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelX = new JLabel();
        labelX.setText("Nessuno scalo aggiunto");
        panel6.add(labelX, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelAttracco = new JLabel();
        labelAttracco.setText("");
        panel6.add(labelAttracco, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRipartenza = new JLabel();
        labelRipartenza.setText("");
        panel6.add(labelRipartenza, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelScalo = new JLabel();
        labelScalo.setText("");
        panel6.add(labelScalo, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonCreaScalo = new JButton();
        buttonCreaScalo.setText("Crea Scalo");
        panel7.add(buttonCreaScalo, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAggiungiScalo = new JButton();
        buttonAggiungiScalo.setText("Aggiungi Scalo");
        panel7.add(buttonAggiungiScalo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonEliminaScalo = new JButton();
        buttonEliminaScalo.setText("Elimina Scalo");
        panel6.add(buttonEliminaScalo, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel8, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelPeriodo = new JPanel();
        panelPeriodo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel8.add(panelPeriodo, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfDataInizioPeriodo = new JTextField();
        tfDataInizioPeriodo.setText("yyyy-mm-dd");
        panelPeriodo.add(tfDataInizioPeriodo, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panelPeriodo.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        final JLabel label3 = new JLabel();
        label3.setText("Aggiungi un nuovo periodo di attività");
        panel8.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 8, new Insets(0, 0, 0, 0), -1, -1));
        panel8.add(panel9, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        checkLun = new JCheckBox();
        checkLun.setText("Lun");
        panel9.add(checkLun, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel9.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(0, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel9.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        checkMar = new JCheckBox();
        checkMar.setText("Mar");
        panel9.add(checkMar, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkMer = new JCheckBox();
        checkMer.setSelected(false);
        checkMer.setText("Mer");
        panel9.add(checkMer, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkGio = new JCheckBox();
        checkGio.setText("Gio");
        panel9.add(checkGio, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkVen = new JCheckBox();
        checkVen.setText("Ven");
        panel9.add(checkVen, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkSab = new JCheckBox();
        checkSab.setText("Sab");
        panel9.add(checkSab, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkDom = new JCheckBox();
        checkDom.setText("Dom");
        panel9.add(checkDom, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("");
        panel8.add(label4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelMain = new JLabel();
        Font labelMainFont = this.$$$getFont$$$(null, -1, 20, labelMain.getFont());
        if (labelMainFont != null) labelMain.setFont(labelMainFont);
        labelMain.setText("Info Corsa");
        contentPane.add(labelMain, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("");
        contentPane.add(label5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("");
        contentPane.add(label6, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("");
        contentPane.add(label7, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return contentPane;
    }

}
