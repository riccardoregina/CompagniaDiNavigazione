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

public class ModificaCorsaRegolare {
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
    private JLabel labelPortoScalo;
    private JLabel labelRipartenza;
    private JButton buttonAttivaScalo;
    private JButton buttonAnnulla;
    private JCheckBox checkLun;
    private JCheckBox checkMar;
    private JCheckBox checkMer;
    private JCheckBox checkGio;
    private JCheckBox checkVen;
    private JCheckBox checkSab;
    private JCheckBox checkDom;
    private JPanel panelCorse;
    private JPanel panelTabellaCorse;
    private JPanel panelModifiche;
    private JPanel panelTabellaPeriodi;
    private JPanel panelScali;
    private JPanel panelCreaScalo;
    private JPanel panelCreaPeriodo;
    private JPanel panelGiorni;
    private JPanel panelGiorniAggiungiPeriodo;
    private JLabel labelPeriodiAttivita;
    private JPanel panelGestioneScali;
    private JLabel panelInfoEliminazioneScalo;
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

    public ModificaCorsaRegolare(JFrame frameChiamante, ControllerCompagnia controllerCompagnia, int idCorsa) {
        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("Info Corsa");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width / 1.2), (int) (screenSize.height / 1.2));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("resources/icons/logo.png").getImage().getScaledInstance(400, 400, 1));
        frame.setVisible(true);

        contentPane.setBackground(Color.white);
        panelCorse.setBackground(Color.white);
        panelGiorni.setBackground(Color.white);
        panelCreaPeriodo.setBackground(Color.white);
        panelModifiche.setBackground(Color.white);
        panelScali.setBackground(Color.white);
        panelGestioneScali.setBackground(Color.white);
        panelGiorniAggiungiPeriodo.setBackground(Color.white);
        panelInfoEliminazioneScalo.setBackground(Color.white);
        panelPeriodo.setBackground(Color.white);
        panelTabellaCorse.setBackground(Color.white);
        panelTabellaPeriodi.setBackground(Color.white);

        checkLun.setBackground(Color.white);
        checkMar.setBackground(Color.white);
        checkMer.setBackground(Color.white);
        checkGio.setBackground(Color.white);
        checkVen.setBackground(Color.white);
        checkSab.setBackground(Color.white);
        checkDom.setBackground(Color.white);

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
            buttonAttivaScalo.setEnabled(false);
            buttonAnnulla.setVisible(false);
            buttonAnnulla.setEnabled(false);
        } else {
            buttonCreaScalo.setEnabled(true);
            buttonAttivaScalo.setEnabled(false);
            buttonAnnulla.setVisible(false);
            buttonAnnulla.setEnabled(false);
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

        buttonAttivaScalo.addActionListener(new ActionListener() {
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
                buttonAnnulla.setEnabled(false);
                buttonAnnulla.setVisible(false);
                buttonCreaScalo.setEnabled(false);
                buttonAttivaScalo.setEnabled(false);
            }
        });

        buttonAnnulla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portoScalo.clear();
                oraAttracco.clear();
                oraRipartenza.clear();
                labelPortoScalo.setVisible(false);
                labelAttracco.setVisible(false);
                labelRipartenza.setVisible(false);
                labelX.setText("Nessuno scalo aggiunto");
                buttonAnnulla.setVisible(false);
                buttonAnnulla.setEnabled(false);
                buttonAttivaScalo.setEnabled(false);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                if (oraAttracco == null || oraAttracco.isEmpty()) {
                    labelX.setText("Nessuno scalo aggiunto");
                } else {
                    labelX.setText("Uno scalo è stato aggiunto:");
                    labelPortoScalo.setText("Porto: " + portoScalo.getFirst().last.toString().toUpperCase());
                    labelPortoScalo.setVisible(true);
                    labelAttracco.setText("Ora Attracco: " + oraAttracco.getFirst());
                    labelAttracco.setVisible(true);
                    labelRipartenza.setText("OraRipartenza: " + oraRipartenza.getFirst());
                    labelRipartenza.setVisible(true);
                    buttonAttivaScalo.setEnabled(true);
                    buttonAnnulla.setVisible(true);
                    buttonAnnulla.setEnabled(true);
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
        tableCorse.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
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

    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelCorse = new JPanel();
        panelCorse.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panelCorse, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.ITALIC, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("La corsa selezionata è una");
        panelCorse.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelTabellaCorse = new JPanel();
        panelTabellaCorse.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelCorse.add(panelTabellaCorse, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(291, 249), new Dimension(-1, 200), 0, false));
        label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Tappe ed informazioni riguardo alla corsa intera");
        panelTabellaCorse.add(label2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneCorse = new JScrollPane();
        panelTabellaCorse.add(scrollPaneCorse, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 100), new Dimension(-1, 100), 0, false));
        labelModifica = new JLabel();
        Font labelModificaFont = this.$$$getFont$$$(null, Font.ITALIC, -1, labelModifica.getFont());
        if (labelModificaFont != null) labelModifica.setFont(labelModificaFont);
        labelModifica.setText("Per modificare orari e tariffe della corsa cliccare sulle singole celle della tabella.");
        panelTabellaCorse.add(labelModifica, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panelTabellaCorse.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panelCorse.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelModifiche = new JPanel();
        panelModifiche.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelCorse.add(panelModifiche, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelTabellaPeriodi = new JPanel();
        panelTabellaPeriodi.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelModifiche.add(panelTabellaPeriodi, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelPeriodi = new JLabel();
        Font labelPeriodiFont = this.$$$getFont$$$(null, -1, 14, labelPeriodi.getFont());
        if (labelPeriodiFont != null) labelPeriodi.setFont(labelPeriodiFont);
        labelPeriodi.setText("Periodi attuali");
        panelTabellaPeriodi.add(labelPeriodi, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPanePeriodi = new JScrollPane();
        panelTabellaPeriodi.add(scrollPanePeriodi, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(50, 100), null, null, 0, false));
        bEliminaPeriodo = new JButton();
        bEliminaPeriodo.setText("Elimina Periodo");
        panelTabellaPeriodi.add(bEliminaPeriodo, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelCreaPeriodo = new JPanel();
        panelCreaPeriodo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelModifiche.add(panelCreaPeriodo, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelPeriodo = new JPanel();
        panelPeriodo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelCreaPeriodo.add(panelPeriodo, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfDataInizioPeriodo = new JTextField();
        tfDataInizioPeriodo.setText("yyyy-mm-dd");
        panelPeriodo.add(tfDataInizioPeriodo, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panelPeriodo.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tfDataFinePeriodo = new JTextField();
        tfDataFinePeriodo.setText("yyyy-mm-dd");
        panelPeriodo.add(tfDataFinePeriodo, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dip = new JLabel();
        dip.setText("Data di inizio del periodo");
        panelPeriodo.add(dip, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dfp = new JLabel();
        dfp.setText("Data di fine del periodo");
        panelPeriodo.add(dfp, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCreaPeriodo = new JButton();
        buttonCreaPeriodo.setText("Aggiungi Periodo");
        panelPeriodo.add(buttonCreaPeriodo, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bCal1 = new JButton();
        bCal1.setText("...");
        panelPeriodo.add(bCal1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(25, -1), new Dimension(25, -1), 0, false));
        bCal2 = new JButton();
        bCal2.setText("...");
        panelPeriodo.add(bCal2, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(25, -1), new Dimension(25, -1), 0, false));
        panelGiorniAggiungiPeriodo = new JPanel();
        panelGiorniAggiungiPeriodo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelPeriodo.add(panelGiorniAggiungiPeriodo, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        checkLun = new JCheckBox();
        checkLun.setText("Lun");
        panelGiorniAggiungiPeriodo.add(checkLun, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkMar = new JCheckBox();
        checkMar.setText("Mar");
        panelGiorniAggiungiPeriodo.add(checkMar, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkMer = new JCheckBox();
        checkMer.setSelected(false);
        checkMer.setText("Mer");
        panelGiorniAggiungiPeriodo.add(checkMer, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkSab = new JCheckBox();
        checkSab.setText("Sab");
        panelGiorniAggiungiPeriodo.add(checkSab, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkVen = new JCheckBox();
        checkVen.setText("Ven");
        panelGiorniAggiungiPeriodo.add(checkVen, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkGio = new JCheckBox();
        checkGio.setText("Gio");
        panelGiorniAggiungiPeriodo.add(checkGio, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkDom = new JCheckBox();
        checkDom.setText("Dom");
        panelGiorniAggiungiPeriodo.add(checkDom, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Giorni di attivita del periodo");
        panelPeriodo.add(label3, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        Font label4Font = this.$$$getFont$$$(null, -1, 14, label4.getFont());
        if (label4Font != null) label4.setFont(label4Font);
        label4.setText("Aggiungi un nuovo periodo di attività");
        panelCreaPeriodo.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelGiorni = new JPanel();
        panelGiorni.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelCreaPeriodo.add(panelGiorni, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panelGiorni.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panelGiorni.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("");
        panelCreaPeriodo.add(label5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPeriodiAttivita = new JLabel();
        Font labelPeriodiAttivitaFont = this.$$$getFont$$$(null, -1, 18, labelPeriodiAttivita.getFont());
        if (labelPeriodiAttivitaFont != null) labelPeriodiAttivita.setFont(labelPeriodiAttivitaFont);
        labelPeriodiAttivita.setText("Gestione dei periodi di attivita");
        panelModifiche.add(labelPeriodiAttivita, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelGestioneScali = new JPanel();
        panelGestioneScali.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelModifiche.add(panelGestioneScali, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelScali = new JPanel();
        panelScali.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelGestioneScali.add(panelScali, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonCreaScalo = new JButton();
        buttonCreaScalo.setHideActionText(false);
        buttonCreaScalo.setText("Crea Scalo");
        panelScali.add(buttonCreaScalo, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAttivaScalo = new JButton();
        buttonAttivaScalo.setText("Attiva Scalo");
        panelScali.add(buttonAttivaScalo, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRipartenza = new JLabel();
        labelRipartenza.setText("");
        panelScali.add(labelRipartenza, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelAttracco = new JLabel();
        labelAttracco.setText("");
        panelScali.add(labelAttracco, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelX = new JLabel();
        Font labelXFont = this.$$$getFont$$$(null, Font.ITALIC, 11, labelX.getFont());
        if (labelXFont != null) labelX.setFont(labelXFont);
        labelX.setText("Nessuno scalo da attivare");
        panelScali.add(labelX, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPortoScalo = new JLabel();
        labelPortoScalo.setText("");
        panelScali.add(labelPortoScalo, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        Font label6Font = this.$$$getFont$$$(null, -1, 14, label6.getFont());
        if (label6Font != null) label6.setFont(label6Font);
        label6.setText("Aggiungi un nuovo scalo");
        panelScali.add(label6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonAnnulla = new JButton();
        buttonAnnulla.setText("Annulla");
        panelScali.add(buttonAnnulla, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panelGestioneScali.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelInfoEliminazioneScalo = new JLabel();
        Font panelInfoEliminazioneScaloFont = this.$$$getFont$$$(null, Font.ITALIC, -1, panelInfoEliminazioneScalo.getFont());
        if (panelInfoEliminazioneScaloFont != null) panelInfoEliminazioneScalo.setFont(panelInfoEliminazioneScaloFont);
        panelInfoEliminazioneScalo.setText("Per eliminare uno scalo,");
        panelGestioneScali.add(panelInfoEliminazioneScalo, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        Font label7Font = this.$$$getFont$$$(null, Font.ITALIC, -1, label7.getFont());
        if (label7Font != null) label7.setFont(label7Font);
        label7.setText("eliminare una delle due sottocorse.");
        panelGestioneScali.add(label7, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        Font label8Font = this.$$$getFont$$$(null, Font.ITALIC, -1, label8.getFont());
        if (label8Font != null) label8.setFont(label8Font);
        label8.setText("Verra' eliminato automaticamente lo");
        panelGestioneScali.add(label8, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        Font label9Font = this.$$$getFont$$$(null, Font.ITALIC, -1, label9.getFont());
        if (label9Font != null) label9.setFont(label9Font);
        label9.setText("scalo ed entrambe le sottocorse.");
        panelGestioneScali.add(label9, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("");
        panelGestioneScali.add(label10, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        Font label11Font = this.$$$getFont$$$(null, -1, 18, label11.getFont());
        if (label11Font != null) label11.setFont(label11Font);
        label11.setText("Gestione degli scali");
        panelModifiche.add(label11, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("                   ");
        panelModifiche.add(label12, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("");
        panelModifiche.add(label13, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelMain = new JLabel();
        Font labelMainFont = this.$$$getFont$$$(null, -1, 20, labelMain.getFont());
        if (labelMainFont != null) labelMain.setFont(labelMainFont);
        labelMain.setText("Modifica corsa regolare");
        contentPane.add(labelMain, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("");
        contentPane.add(label14, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("");
        contentPane.add(label15, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("");
        contentPane.add(label16, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonChiudi = new JButton();
        buttonChiudi.setText("Chiudi");
        contentPane.add(buttonChiudi, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("");
        contentPane.add(label17, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

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

    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
