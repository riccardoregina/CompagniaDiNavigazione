package gui;

import controller.ControllerCliente;
import unnamed.CustomRenderer;
import unnamed.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The type Home cliente.
 */
public class HomeCliente {
    private JPanel panel1;
    private JButton bCercaCorse;
    private JButton bBigliettiCliente;
    private JButton bVeicoliCliente;
    private JPanel panelInfo;
    private JComboBox comboBoxCompagnie;
    private JButton bVediInfoCompagnia;
    private JTextField textData;
    private JLabel label;
    private JScrollPane scrollPaneCorse;
    private SpinnerNumberModel modelOre = new SpinnerNumberModel(0, 0, 23, 1);
    private SpinnerNumberModel modelMinuti = new SpinnerNumberModel(0, 0, 59, 1);
    private SpinnerNumberModel modelEta = new SpinnerNumberModel(18, 1, 200, 1);
    private JSpinner spinnerMinuti;
    private JSpinner spinnerOre;
    private JCheckBox checkBoxBagaglio;
    private JLabel orarioLabel;
    private JTextField textCostoMax;
    private JCheckBox checkBoxTraghetto;
    private JCheckBox checkBoxMotonave;
    private JCheckBox checkBoxAliscafo;
    private JCheckBox checkBoxAltro;
    private JButton acquistaButton;
    private JComboBox comboBoxVeicoli;
    private JSpinner spinnerEta;
    private JLabel labelPart;
    private JLabel labelDest;
    private JComboBox comboBoxDest;
    private JComboBox comboBoxPart;
    private JLabel labelVeicolo;
    private JLabel labelEta;
    private JPanel panelLegenda;
    private JButton buttonLogout;
    private JLabel labelC;
    private JButton buttonInfoPorto;
    private JLabel labelButtonAggiungiVeicolo;
    private JLabel labelAggiungiVeicolo;
    private JPanel centralPanel;
    private JPanel sideBarDx;
    private JPanel sideBarSx;
    private JLabel labelPrezzoMax;
    private JLabel labelNatanti;
    private JLabel labelButtonSelezionaData;
    private JLabel labelBagaglio;
    private JLabel labelVeicoli;
    private JLabel labelBiglietti;
    private JLabel labelButtonBiglietti;
    private JLabel labelButtonVeicoli;
    private JLabel labelButtonLogout;
    private JLabel labelInfoPorto;
    private JLabel labelButtonInfoPorto;
    private JLabel labelLogout;
    private JTable tableCorse;

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

    private boolean veicolo = false;
    private boolean bagaglio = false;
    private int etaPass;
    private ArrayList<Integer> idCorse;
    private ArrayList<LocalDate> dateCor;
    private ArrayList<Integer> postiDispPass;
    private ArrayList<Integer> postiDispVei;
    private ArrayList<Boolean> cancellata;
    private ArrayList<Boolean> scaduta;
    private ArrayList<Float> prezzo;
    private ArrayList<LocalTime> orePart;
    private ArrayList<Integer> minutiRitardo;
    int portoPartenza;
    int portoDestinazione;
    private Integer idPortoPart;
    private Integer idPortoDest;
    private LocalDate dataSelezionata;
    private LocalTime orarioSelezionato;
    private float prezzoMax;
    private ArrayList<String> tipoNatanteSelezionato;
    private ArrayList<String> porti;
    private ArrayList<Integer> idPorti;

    /**
     * Instantiates a new Home cliente.
     *
     * @param frameChiamante    the frame chiamante
     * @param controllerCliente the controller cliente
     */
    public HomeCliente(JFrame frameChiamante, ControllerCliente controllerCliente) {

        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("homeCliente");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width / 1.1), (int) (screenSize.height / 1.2));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        acquistaButton.setEnabled(false);

        Color white = Color.white;
        Color accent = new Color(241, 81, 82);
        Color accentHovered = new Color(177, 143, 207);
        Color secondary = new Color(217, 202, 179);

        panel1.setBackground(white);
        sideBarSx.setBackground(white);
        sideBarDx.setBackground(white);
        centralPanel.setBackground(white);
        scrollPaneCorse.setBackground(white);

        //ImageIcon imagePorto = new ImageIcon("resources/icons/icons8-faro-48.png");
        ImageIcon imageAggiungiVeicolo = new ImageIcon("resources/icons/icons8-automobile-48.png");
        ImageIcon imageAggiungiVeicoloHovered = new ImageIcon("resources/icons/icons8-automobile-48-hovered.png");
        ImageIcon imageDataPicker = new ImageIcon("resources/icons/icons8-calendario-48.png");
        ImageIcon imageDataPickerHovered = new ImageIcon("resources/icons/icons8-calendario-48-hovered.png");
        ImageIcon imageBiglietto = new ImageIcon("resources/icons/icons8-biglietto-48.png");
        ImageIcon imageBigliettoHovered = new ImageIcon("resources/icons/icons8-biglietto-48-hovered.png");
        ImageIcon imageGarage = new ImageIcon("resources/icons/icons8-garage-chiuso-48.png");
        ImageIcon imageGarageHovered = new ImageIcon("resources/icons/icons8-garage-chiuso-48-hovered.png");
        ImageIcon imageLogout = new ImageIcon("resources/icons/icons8-ritorno-48.png");
        ImageIcon imageLogoutHovered = new ImageIcon("resources/icons/icons8-ritorno-48-hovered.png");
        ImageIcon imageInfo = new ImageIcon("resources/icons/icons8-informazioni-48.png");
        ImageIcon imageInfoHovered = new ImageIcon("resources/icons/icons8-informazioni-48-hovered.png");
        ImageIcon imageCerca = new ImageIcon("resources/icons/icons8-ricerca-48.png");
        ImageIcon imageAcquista = new ImageIcon("resources/icons/icons8-carrello-2-48.png");
        //labelPart.setIcon(imagePorto);
        //labelDest.setIcon(imagePorto);
        labelButtonAggiungiVeicolo.setIcon(imageAggiungiVeicolo);
        labelButtonSelezionaData.setIcon(imageDataPicker);
        labelButtonBiglietti.setIcon(imageBiglietto);
        labelButtonVeicoli.setIcon(imageGarage);
        labelButtonLogout.setIcon(imageLogout);
        labelButtonInfoPorto.setIcon(imageInfo);
        bCercaCorse.setIcon(imageCerca);
        bCercaCorse.setBackground(accent);
        acquistaButton.setBackground(accent);
        acquistaButton.setIcon(imageAcquista);

        CustomRenderer.aggiungiColoreLegenda(panelLegenda, new Color(208, 92, 92, 255), "Corse cancellate");
        CustomRenderer.aggiungiColoreLegenda(panelLegenda, new Color(141, 141, 141, 255), "Corse vecchie");
        CustomRenderer.aggiungiColoreLegenda(panelLegenda, new Color(141, 141, 141, 255), "Corse vecchie e cancellate");
        CustomRenderer.aggiungiColoreLegenda(panelLegenda, Color.white, "Corse acquistabili");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        textData.setEditable(false);
        textData.setText(String.valueOf(LocalDate.now().format(formatter)));
        checkBoxAliscafo.setSelected(true);
        checkBoxTraghetto.setSelected(true);
        checkBoxAltro.setSelected(true);
        checkBoxMotonave.setSelected(true);

        spinnerEta.setModel(modelEta);
        spinnerOre.setModel(modelOre);
        spinnerOre.setValue(LocalTime.now().getHour());
        spinnerMinuti.setModel(modelMinuti);
        spinnerMinuti.setValue(LocalTime.now().getMinute());

        ArrayList<String> targhe = new ArrayList<String>();
        ArrayList<String> tipi = new ArrayList<String>();
        controllerCliente.visualizzaVeicoli(targhe, tipi);
        comboBoxVeicoli.addItem("Nessuno");
        for (int i = 0; i < targhe.size(); i++) {
            comboBoxVeicoli.addItem(tipi.get(i) + " " + targhe.get(i));
        }

        porti = new ArrayList<String>();
        idPorti = new ArrayList<>();
        controllerCliente.visualizzaPorti(idPorti, porti);
        for (String p : porti) {
            comboBoxPart.addItem(p);
            comboBoxDest.addItem(p);
        }


        ArrayList<String> compagnie = new ArrayList<String>();
        ArrayList<String> idsCompagnie = new ArrayList<String>();
        controllerCliente.getCompagnie(idsCompagnie, compagnie);
        for (String nomeCompagnia : compagnie) {
            comboBoxCompagnie.addItem(nomeCompagnia);
        }
        bVediInfoCompagnia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idxCompagnia = comboBoxCompagnie.getSelectedIndex();
                String idCompagnia = idsCompagnie.get(idxCompagnia);
                ContattiCompagnia contattiCompagnia = new ContattiCompagnia(frame, controllerCliente, idCompagnia);
                frame.setVisible(false);
            }
        });

        labelButtonAggiungiVeicolo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                AggiungiVeicolo aggiungiVeicolo = new AggiungiVeicolo(frame, controllerCliente);
                frame.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonAggiungiVeicolo.setIcon(imageAggiungiVeicoloHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonAggiungiVeicolo.setIcon(imageAggiungiVeicolo);
            }
        });
        labelButtonSelezionaData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                textData.setText(new DatePicker(frame).setPickedDate());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonSelezionaData.setIcon(imageDataPickerHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonSelezionaData.setIcon(imageDataPicker);
            }
        });
        labelButtonBiglietti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TuoiBiglietti iTuoiBiglietti = new TuoiBiglietti(frame, controllerCliente);
                frame.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonBiglietti.setIcon(imageBigliettoHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonBiglietti.setIcon(imageBiglietto);
            }
        });
        labelButtonVeicoli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TuoiVeicoli iTuoiVeicoli = new TuoiVeicoli(frame, controllerCliente);
                frame.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonVeicoli.setIcon(imageGarageHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonVeicoli.setIcon(imageGarage);
            }
        });
        labelButtonLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                HomeMain homeMain = new HomeMain();
                frame.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonLogout.setIcon(imageLogoutHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonLogout.setIcon(imageLogout);
            }
        });
        labelButtonInfoPorto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ContattiPorto contattiPorto = new ContattiPorto(frame, controllerCliente);
                frame.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonInfoPorto.setIcon(imageInfoHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonInfoPorto.setIcon(imageInfo);
            }
        });
        bCercaCorse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                portoPartenza = comboBoxPart.getSelectedIndex();
                portoDestinazione = comboBoxDest.getSelectedIndex();
                idPortoPart = idPorti.get(portoPartenza);
                idPortoDest = idPorti.get(portoDestinazione);

                if (textData.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inserire una data");
                    return;
                }

                dataSelezionata = LocalDate.parse(textData.getText(), formatter);
                orarioSelezionato = LocalTime.of((int) spinnerOre.getValue(), (int) spinnerMinuti.getValue());

                etaPass = (int) spinnerEta.getValue();
                bagaglio = checkBoxBagaglio.isSelected();
                if (!(comboBoxVeicoli.getSelectedItem().equals("Nessuno"))) {
                    if (etaPass >= 18) {
                        veicolo = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Un minorenne non può portare con sè un veicolo");
                        veicolo = false;
                        return;
                    }
                } else {
                    veicolo = false;
                }

                String inputCostoMax = textCostoMax.getText();
                try {
                    int costo = Integer.parseInt(inputCostoMax);
                    prezzoMax = (float) costo;
                } catch (NumberFormatException e1) {
                    try {
                        prezzoMax = Float.parseFloat(inputCostoMax);
                    } catch (NumberFormatException e2) {
                        JOptionPane.showMessageDialog(null, "Inserisci un prezzo massimo");
                        return;
                    }
                }

                tipoNatanteSelezionato = new ArrayList<String>();
                if (checkBoxTraghetto.isSelected()) {
                    tipoNatanteSelezionato.add(checkBoxTraghetto.getText().toLowerCase());
                }
                if (checkBoxAliscafo.isSelected()) {
                    tipoNatanteSelezionato.add(checkBoxAliscafo.getText().toLowerCase());
                }
                if (checkBoxMotonave.isSelected()) {
                    tipoNatanteSelezionato.add(checkBoxMotonave.getText().toLowerCase());
                }
                if (checkBoxAltro.isSelected()) {
                    tipoNatanteSelezionato.add(checkBoxAltro.getText().toLowerCase());
                }

                aggiornaTabelloneCorse();

                acquistaButton.setEnabled(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                bCercaCorse.setBackground(accentHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                bCercaCorse.setBackground(accent);
            }
        });
        acquistaButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!acquistaButton.isEnabled()) {
                    return;
                }
                if (tableCorse.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Non esistono corse in questa data e questo orario");
                    return;
                }

                int selectedRow = tableCorse.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Selezionare una corsa dal tabellone");
                    return;
                }

                if (dateCor.get(selectedRow).isBefore(LocalDate.now()) || (dateCor.get(selectedRow).isEqual(LocalDate.now()) && orePart.get(selectedRow).plusMinutes(minutiRitardo.get(selectedRow)).isBefore(LocalTime.now()))) {
                    JOptionPane.showMessageDialog(null, "Non puoi più comprare biglietti per questa corsa");
                    return;
                }

                if (cancellata.get(selectedRow)) {
                    JOptionPane.showMessageDialog(null, "La corsa selezionata è stata cancellata!");
                    return;
                }

                if (postiDispPass.get(selectedRow) <= 0) {
                    JOptionPane.showMessageDialog(null, "Biglietti esauriti!");
                    return;
                }

                String targaVeicolo = null;

                if (veicolo && postiDispVei.get(selectedRow) <= 0) {
                    JOptionPane.showMessageDialog(null, "Non ci sono posti per veicoli!");
                    return;
                } else if (veicolo) {
                    String[] str = ((String) comboBoxVeicoli.getSelectedItem()).split(" ");
                    targaVeicolo = str[1];
                }

                if (controllerCliente.acquistaBiglietto(idCorse.get(selectedRow), dateCor.get(selectedRow), targaVeicolo, false, bagaglio, etaPass, prezzo.get(selectedRow))) {
                    JOptionPane.showMessageDialog(null, "Biglietto acquistato!");
                } else {
                    JOptionPane.showMessageDialog(null, "Errore :(");
                }

                aggiornaTabelloneCorse();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                acquistaButton.setBackground(accentHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                acquistaButton.setBackground(accent);
            }
        });
    }

    private void aggiornaTabelloneCorse() {
        idCorse = new ArrayList<Integer>();
        dateCor = new ArrayList<LocalDate>();
        postiDispPass = new ArrayList<Integer>();
        postiDispVei = new ArrayList<Integer>();
        cancellata = new ArrayList<Boolean>();
        scaduta = new ArrayList<Boolean>();
        prezzo = new ArrayList<Float>();
        orePart = new ArrayList<LocalTime>();
        minutiRitardo = new ArrayList<Integer>();
        ArrayList<LocalTime> oreDest = new ArrayList<LocalTime>();
        ArrayList<String> natanti = new ArrayList<String>();
        ArrayList<String> nomeCompagnia = new ArrayList<String>();

        controllerCliente.visualizzaCorse(idPortoPart, idPortoDest, dataSelezionata, orarioSelezionato, prezzoMax, tipoNatanteSelezionato, etaPass, veicolo, bagaglio, idCorse, nomeCompagnia, dateCor, orePart, oreDest, postiDispPass, postiDispVei, minutiRitardo, natanti, cancellata, prezzo);
        String[] col = new String[]{"Compagnia", "Partenza", "Ore", "Arrivo", "Ore", "Prezzo", "Posti Passeggeri", "Posti Veicoli", "Ritardo", "Natante", "Data", "ID"};
        Object[][] data = new Object[idCorse.size()][12];
        for (int i = 0; i < idCorse.size(); i++) {
            data[i][0] = nomeCompagnia.get(i);
            data[i][1] = porti.get(portoPartenza);
            data[i][2] = orePart.get(i);
            data[i][3] = porti.get(portoDestinazione);
            data[i][4] = oreDest.get(i);
            data[i][5] = prezzo.get(i);
            data[i][6] = postiDispPass.get(i);
            data[i][7] = postiDispVei.get(i);
            data[i][8] = minutiRitardo.get(i) + "'";
            data[i][9] = natanti.get(i);
            data[i][10] = dateCor.get(i);
            data[i][11] = idCorse.get(i);

            if (dateCor.get(i).isBefore(LocalDate.now()) || (dateCor.get(i).isEqual(LocalDate.now()) && orePart.get(i).plusMinutes(minutiRitardo.get(i)).isBefore(LocalTime.now()))) {
                scaduta.add(true);
            } else {
                scaduta.add(false);
            }
        }
        DefaultTableModel model = new DefaultTableModel(data, col) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCorse = new JTable(model);
        tableCorse.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableCorse.setDefaultRenderer(Object.class, centerRenderer);
        tableCorse.setDefaultRenderer(Object.class, new CustomRenderer(cancellata, scaduta));
        ListSelectionModel selectionModel = tableCorse.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneCorse.setViewportView(tableCorse);
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
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        sideBarSx = new JPanel();
        sideBarSx.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(22, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(sideBarSx);
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        sideBarSx.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        labelDest = new JLabel();
        labelDest.setText("Arrivo a");
        sideBarSx.add(labelDest, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelPart = new JLabel();
        labelPart.setText("Partenza da");
        sideBarSx.add(labelPart, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBoxPart = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBoxPart.setModel(defaultComboBoxModel1);
        sideBarSx.add(comboBoxPart, new com.intellij.uiDesigner.core.GridConstraints(6, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBoxDest = new JComboBox();
        sideBarSx.add(comboBoxDest, new com.intellij.uiDesigner.core.GridConstraints(7, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        sideBarSx.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(10, 10), null, null, 0, false));
        orarioLabel = new JLabel();
        orarioLabel.setText("Orario");
        sideBarSx.add(orarioLabel, new com.intellij.uiDesigner.core.GridConstraints(10, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerMinuti = new JSpinner();
        sideBarSx.add(spinnerMinuti, new com.intellij.uiDesigner.core.GridConstraints(11, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerOre = new JSpinner();
        sideBarSx.add(spinnerOre, new com.intellij.uiDesigner.core.GridConstraints(10, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxVeicoli = new JComboBox();
        sideBarSx.add(comboBoxVeicoli, new com.intellij.uiDesigner.core.GridConstraints(12, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelEta = new JLabel();
        labelEta.setText("Età Passeggero");
        sideBarSx.add(labelEta, new com.intellij.uiDesigner.core.GridConstraints(14, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textCostoMax = new JTextField();
        textCostoMax.setText("prezzo max");
        sideBarSx.add(textCostoMax, new com.intellij.uiDesigner.core.GridConstraints(15, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelPrezzoMax = new JLabel();
        labelPrezzoMax.setText("Prezzo massimo");
        sideBarSx.add(labelPrezzoMax, new com.intellij.uiDesigner.core.GridConstraints(15, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerEta = new JSpinner();
        sideBarSx.add(spinnerEta, new com.intellij.uiDesigner.core.GridConstraints(14, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelButtonAggiungiVeicolo = new JLabel();
        labelButtonAggiungiVeicolo.setText("");
        sideBarSx.add(labelButtonAggiungiVeicolo, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelAggiungiVeicolo = new JLabel();
        labelAggiungiVeicolo.setText("Aggiungi un veicolo");
        sideBarSx.add(labelAggiungiVeicolo, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxTraghetto = new JCheckBox();
        checkBoxTraghetto.setText("Traghetto");
        sideBarSx.add(checkBoxTraghetto, new com.intellij.uiDesigner.core.GridConstraints(16, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxMotonave = new JCheckBox();
        checkBoxMotonave.setText("Motonave");
        sideBarSx.add(checkBoxMotonave, new com.intellij.uiDesigner.core.GridConstraints(17, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxAliscafo = new JCheckBox();
        checkBoxAliscafo.setText("Aliscafo");
        sideBarSx.add(checkBoxAliscafo, new com.intellij.uiDesigner.core.GridConstraints(18, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxAltro = new JCheckBox();
        checkBoxAltro.setText("Altro");
        sideBarSx.add(checkBoxAltro, new com.intellij.uiDesigner.core.GridConstraints(19, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        sideBarSx.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(21, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        sideBarSx.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        checkBoxBagaglio = new JCheckBox();
        checkBoxBagaglio.setText("Bagaglio");
        sideBarSx.add(checkBoxBagaglio, new com.intellij.uiDesigner.core.GridConstraints(13, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelVeicolo = new JLabel();
        labelVeicolo.setText("Veicolo");
        sideBarSx.add(labelVeicolo, new com.intellij.uiDesigner.core.GridConstraints(12, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelBagaglio = new JLabel();
        labelBagaglio.setText("Bagaglio");
        sideBarSx.add(labelBagaglio, new com.intellij.uiDesigner.core.GridConstraints(13, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelVeicoli = new JLabel();
        labelVeicoli.setText("I tuoi veicoli");
        sideBarSx.add(labelVeicoli, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textData = new JTextField();
        sideBarSx.add(textData, new com.intellij.uiDesigner.core.GridConstraints(9, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelButtonSelezionaData = new JLabel();
        labelButtonSelezionaData.setText("");
        sideBarSx.add(labelButtonSelezionaData, new com.intellij.uiDesigner.core.GridConstraints(8, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label = new JLabel();
        label.setText("Data");
        sideBarSx.add(label, new com.intellij.uiDesigner.core.GridConstraints(8, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelBiglietti = new JLabel();
        labelBiglietti.setText("I tuoi biglietti");
        sideBarSx.add(labelBiglietti, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelNatanti = new JLabel();
        labelNatanti.setText("Natanti");
        sideBarSx.add(labelNatanti, new com.intellij.uiDesigner.core.GridConstraints(16, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        sideBarSx.add(separator1, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelButtonBiglietti = new JLabel();
        labelButtonBiglietti.setText("");
        sideBarSx.add(labelButtonBiglietti, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelButtonVeicoli = new JLabel();
        labelButtonVeicoli.setText("");
        sideBarSx.add(labelButtonVeicoli, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bCercaCorse = new JButton();
        bCercaCorse.setForeground(new Color(-16777216));
        bCercaCorse.setText("Cerca");
        sideBarSx.add(bCercaCorse, new com.intellij.uiDesigner.core.GridConstraints(20, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        centralPanel = new JPanel();
        centralPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        centralPanel.setMinimumSize(new Dimension(800, 179));
        panel1.add(centralPanel);
        scrollPaneCorse = new JScrollPane();
        centralPanel.add(scrollPaneCorse, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(800, 150), new Dimension(800, 400), null, 0, false));
        panelLegenda = new JPanel();
        panelLegenda.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        centralPanel.add(panelLegenda, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sideBarDx = new JPanel();
        sideBarDx.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(sideBarDx);
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        sideBarDx.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        sideBarDx.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        comboBoxCompagnie = new JComboBox();
        sideBarDx.add(comboBoxCompagnie, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(180, -1), new Dimension(180, -1), 0, false));
        bVediInfoCompagnia = new JButton();
        bVediInfoCompagnia.setText("Contatti Compagnia");
        sideBarDx.add(bVediInfoCompagnia, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(180, -1), new Dimension(180, -1), 0, false));
        acquistaButton = new JButton();
        acquistaButton.setText("Acquista");
        sideBarDx.add(acquistaButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelInfoPorto = new JLabel();
        labelInfoPorto.setText("Info Porto");
        sideBarDx.add(labelInfoPorto, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Contatta la compagnia");
        sideBarDx.add(label1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelButtonInfoPorto = new JLabel();
        labelButtonInfoPorto.setText("");
        sideBarDx.add(labelButtonInfoPorto, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelButtonLogout = new JLabel();
        labelButtonLogout.setText("");
        sideBarDx.add(labelButtonLogout, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelLogout = new JLabel();
        labelLogout.setText("Esci");
        sideBarDx.add(labelLogout, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator2 = new JSeparator();
        sideBarDx.add(separator2, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JSeparator separator3 = new JSeparator();
        sideBarDx.add(separator3, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
