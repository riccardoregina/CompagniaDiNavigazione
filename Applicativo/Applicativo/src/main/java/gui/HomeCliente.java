package gui;

import controller.ControllerCliente;
import unnamed.CustomRenderer;
import unnamed.DatePicker;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * The type Home cliente.
 */
public class HomeCliente {
    private JPanel panel1;
    private JButton bCercaCorse;
    private JComboBox comboBoxCompagnie;
    private JButton bVediInfoCompagnia;
    private JTextField textData;
    private JScrollPane scrollPaneCorse;
    private SpinnerNumberModel modelOre = new SpinnerNumberModel(0, 0, 23, 1);
    private SpinnerNumberModel modelMinuti = new SpinnerNumberModel(0, 0, 59, 1);
    private SpinnerNumberModel modelEta = new SpinnerNumberModel(18, 1, 200, 1);
    private JSpinner spinnerMinuti;
    private JSpinner spinnerOre;
    private JCheckBox checkBoxBagaglio;
    private JTextField textCostoMax;
    private JCheckBox checkBoxTraghetto;
    private JCheckBox checkBoxMotonave;
    private JCheckBox checkBoxAliscafo;
    private JCheckBox checkBoxAltro;
    private JButton acquistaButton;
    private JComboBox comboBoxVeicoli;
    private JSpinner spinnerEta;
    private JComboBox comboBoxDest;
    private JComboBox comboBoxPart;
    private JPanel panelLegendaCorse;
    private JPanel sideBarDx;
    private JPanel sideBarSx;
    private JLabel labelButtonSelezionaData;
    private JLabel labelButtonBiglietti;
    private JLabel labelButtonVeicoli;
    private JLabel labelButtonLogout;
    private JLabel labelButtonInfoPorto;
    private JPanel centralPanel;
    private JScrollPane scrollPaneVeicoli;
    private JScrollPane scrollPaneBiglietti;
    private JButton bAggiungi;
    private JTextField tfTargaVeicolo;
    private JComboBox cbTipoVeicolo;
    private JPanel panelVeicoli;
    private JPanel panelBiglietti;
    private JPanel panelAggiungiVeicolo;
    private JPanel panelCorse;
    private JPanel panelTabellaCorse;
    private JPanel panelLegendaBiglietti;
    private JPanel panelTabellaBiglietti;
    private JPanel panelLogout;
    private JPanel panelInfo;
    private JLabel labelInfoPorto;
    private JPanel panelAcquista;
    private JLabel labelVeicoli;
    private JLabel labelBiglietti;
    private JPanel panelAreaPersonale;
    private JLabel labelPart;
    private JLabel labelDest;
    private JLabel labelData;
    private JLabel labelOrario;
    private JPanel panelOrario;
    private JLabel labelVeicolo;
    private JLabel labelBagaglio;
    private JLabel labelEta;
    private JLabel labelPrezzoMax;
    private JLabel labelNatanti;
    private JPanel panelNatanti;
    private JPanel panelButtonCerca;
    private JPanel panelParametri;
    private JLabel labelButtonAggiungiVeicolo;
    private JLabel labelLogout;
    private JPanel panelLogoutVeicoli2;
    private JPanel panelLogoutVeicoli1;
    private JPanel panelLogoutBiglietti2;
    private JPanel panelLogoutBiglietti1;
    private JLabel labelButtonLogoutBiglietti;
    private JLabel labelButtonLogoutVeicoli;
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
    private JTable tableVeicoli;

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
        frame.setSize((int) (screenSize.width / 1.1), (int) (screenSize.height / 1.1));
        frame.setMinimumSize(new Dimension(1300, 800));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        acquistaButton.setEnabled(false);

        CardLayout cardLayout = (CardLayout) centralPanel.getLayout();

        Color white = Color.white;
        Color gray = new Color(216, 232, 240);
        Color accent = new Color(81, 115, 248);
        Color accentHovered = new Color(182, 146, 248);
        Color secondary = new Color(145, 209, 241);

        panel1.setBackground(gray);
        centralPanel.setBackground(white);
        panelCorse.setBackground(white);
        panelTabellaCorse.setBackground(white);
        scrollPaneCorse.setBackground(white);
        panelLegendaCorse.setBackground(white);
        sideBarDx.setBackground(white);
        panelAcquista.setBackground(white);
        panelInfo.setBackground(white);
        comboBoxCompagnie.setBackground(white);
        panelLogout.setBackground(white);
        panelVeicoli.setBackground(white);
        scrollPaneVeicoli.setBackground(white);
        panelAggiungiVeicolo.setBackground(white);
        panelBiglietti.setBackground(white);
        panelTabellaBiglietti.setBackground(white);
        panelLegendaBiglietti.setBackground(white);
        panelLogoutBiglietti1.setBackground(white);
        panelLogoutBiglietti2.setBackground(white);
        panelLogoutVeicoli1.setBackground(white);
        panelLogoutVeicoli2.setBackground(white);
        scrollPaneBiglietti.setBackground(white);
        sideBarSx.setBackground(gray);
        panelAreaPersonale.setBackground(gray);
        panelButtonCerca.setBackground(gray);
        panelParametri.setBackground(gray);
        checkBoxTraghetto.setBackground(gray);
        checkBoxAliscafo.setBackground(gray);
        checkBoxMotonave.setBackground(gray);
        checkBoxAltro.setBackground(gray);
        checkBoxBagaglio.setBackground(gray);
        comboBoxPart.setBackground(white);
        comboBoxDest.setBackground(white);
        comboBoxVeicoli.setBackground(white);
        textCostoMax.setBackground(white);
        textData.setBackground(white);
        panelOrario.setBackground(gray);
        panelNatanti.setBackground(gray);

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
        ImageIcon imageAcquista = new ImageIcon("resources/icons/icons8-carrello-48.png");
        labelButtonSelezionaData.setIcon(imageDataPicker);
        labelButtonBiglietti.setIcon(imageBiglietto);
        labelButtonVeicoli.setIcon(imageGarage);
        labelButtonLogout.setIcon(imageLogout);
        labelButtonLogoutBiglietti.setIcon(imageLogout);
        labelButtonLogoutVeicoli.setIcon(imageLogout);
        labelButtonInfoPorto.setIcon(imageInfo);
        bCercaCorse.setIcon(imageCerca);
        bCercaCorse.setBackground(accent);
        acquistaButton.setBackground(accent);
        acquistaButton.setIcon(imageAcquista);
        labelButtonAggiungiVeicolo.setIcon(imageAggiungiVeicolo);
        labelButtonAggiungiVeicolo.setHorizontalTextPosition(SwingConstants.LEFT);

        CustomRenderer.aggiungiColoreLegenda(panelLegendaCorse, new Color(215, 75, 75, 255), "Corse cancellate");
        CustomRenderer.aggiungiColoreLegenda(panelLegendaCorse, new Color(120, 120, 120, 255), "Corse vecchie");
        CustomRenderer.aggiungiColoreLegenda(panelLegendaCorse, new Color(180, 135, 220, 255), "Corse vecchie e cancellate");
        CustomRenderer.aggiungiColoreLegenda(panelLegendaCorse, Color.white, "Corse acquistabili");

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

        for (String s : Arrays.asList("automobile", "motociclo", "mezzo pesante", "altro")) {
            cbTipoVeicolo.addItem(s);
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
                ContattiCompagnia contattiCompagnia = new ContattiCompagnia(frame, controllerCliente, idCompagnia, bVediInfoCompagnia);
                bVediInfoCompagnia.setEnabled(false);
            }
        });

        labelButtonInfoPorto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (labelButtonInfoPorto.isEnabled()) {
                    ContattiPorto contattiPorto = new ContattiPorto(frame, controllerCliente, labelButtonInfoPorto);
                    labelButtonInfoPorto.setEnabled(false);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (labelButtonInfoPorto.isEnabled()) {
                    labelButtonInfoPorto.setIcon(imageInfoHovered);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonInfoPorto.setIcon(imageInfo);
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
                JTable tableBiglietti;

                panelLegendaBiglietti.removeAll();
                CustomRenderer.aggiungiColoreLegenda(panelLegendaBiglietti, new Color(215, 75, 75, 255), "Biglietti non più attivi");
                CustomRenderer.aggiungiColoreLegenda(panelLegendaBiglietti, Color.white, "Biglietti ancora attivi");

                ArrayList<Integer> idBiglietti = new ArrayList<Integer>();
                ArrayList<Integer> idCorse = new ArrayList<Integer>();
                ArrayList<LocalDate> dataCor = new ArrayList<LocalDate>();
                ArrayList<LocalTime> orePart = new ArrayList<LocalTime>();
                ArrayList<String> portoPar = new ArrayList<String>();
                ArrayList<String> portoDest = new ArrayList<String>();
                ArrayList<Boolean> bagagli = new ArrayList<Boolean>();
                ArrayList<String> targaVeicolo = new ArrayList<String>();
                ArrayList<Integer> etaPass = new ArrayList<Integer>();
                ArrayList<LocalDate> dataAcquisto = new ArrayList<LocalDate>();
                ArrayList<Float> prezzi = new ArrayList<Float>();
                ArrayList<Integer> minutiRitardo = new ArrayList<Integer>();

                controllerCliente.visualizzaBigliettiAcquistati(idBiglietti, idCorse, dataCor, orePart, minutiRitardo, portoPar, portoDest, bagagli, targaVeicolo, etaPass, dataAcquisto, prezzi);

                String[] col = new String[]{"N° Biglietto", "ID Corsa", "Data", "Ore", "Da", "A", "Bagaglio", "Veicolo", "Età Passeggero", "Data Acquisto", "Prezzo"};
                Object[][] data = new Object[idCorse.size()][11];
                for (int i = 0; i < idBiglietti.size(); i++) {
                    data[i][0] = idBiglietti.get(i);
                    data[i][1] = idCorse.get(i);
                    data[i][2] = dataCor.get(i);
                    data[i][3] = orePart.get(i);
                    data[i][4] = portoPar.get(i);
                    data[i][5] = portoDest.get(i);
                    data[i][6] = bagagli.get(i);
                    data[i][7] = targaVeicolo.get(i);
                    data[i][8] = etaPass.get(i);
                    data[i][9] = dataAcquisto.get(i);
                    data[i][10] = prezzi.get(i);
                }
                DefaultTableModel model = new DefaultTableModel(data, col) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                ArrayList<Boolean> booleanList = new ArrayList<Boolean>();
                for (int i = 0; i < idBiglietti.size(); i++) {
                    if (dataCor.get(i).isBefore(LocalDate.now()) || (dataCor.get(i).isEqual(LocalDate.now()) && orePart.get(i).plusMinutes(minutiRitardo.get(i)).isBefore(LocalTime.now()))) {
                        booleanList.add(true);
                    } else {
                        booleanList.add(false);
                    }
                }

                tableBiglietti = new JTable(model);
                tableBiglietti.getTableHeader().setReorderingAllowed(false);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                tableBiglietti.setDefaultRenderer(Object.class, new CustomRenderer(booleanList));
                ListSelectionModel selectionModel = tableBiglietti.getSelectionModel();
                selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scrollPaneBiglietti.setViewportView(tableBiglietti);

                cardLayout.show(centralPanel, "CardBiglietti");
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

                tfTargaVeicolo.setText("Targa");

                ArrayList<String> targhe = new ArrayList<String>();
                ArrayList<String> tipi = new ArrayList<String>();
                controllerCliente.visualizzaVeicoli(targhe, tipi);

                String[] col = new String[]{"Tipo", "Targa"};
                Object[][] data = new Object[targhe.size()][2];

                for (int i = 0; i < targhe.size(); i++) {
                    data[i][0] = tipi.get(i);
                    data[i][1] = targhe.get(i);
                }

                DefaultTableModel model = new DefaultTableModel(data, col) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

                tableVeicoli = new JTable(model);
                tableVeicoli.getTableHeader().setReorderingAllowed(false);
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                tableVeicoli.setDefaultRenderer(Object.class, centerRenderer);
                tableVeicoli.setRowSorter(sorter);
                ListSelectionModel selectionModel = tableVeicoli.getSelectionModel();
                selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scrollPaneVeicoli.setViewportView(tableVeicoli);

                cardLayout.show(centralPanel, "CardVeicoli");
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

        labelButtonLogoutVeicoli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                HomeMain homeMain = new HomeMain();
                frame.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonLogoutVeicoli.setIcon(imageLogoutHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonLogoutVeicoli.setIcon(imageLogout);
            }
        });

        labelButtonLogoutBiglietti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                HomeMain homeMain = new HomeMain();
                frame.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelButtonLogoutBiglietti.setIcon(imageLogoutHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelButtonLogoutBiglietti.setIcon(imageLogout);
            }
        });

        bCercaCorse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                cardLayout.show(centralPanel, "CardCorse");

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
                if (acquistaButton.isEnabled()) {
                    acquistaButton.setBackground(accentHovered);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                acquistaButton.setBackground(accent);
            }
        });

        labelButtonAggiungiVeicolo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String tipoVeicolo = (String) cbTipoVeicolo.getSelectedItem();
                String targa = tfTargaVeicolo.getText();
                if (targa.equals("Targa") || targa.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inserisci la targa.");
                    return;
                } else {
                    boolean check = controllerCliente.addVeicolo(tipoVeicolo, targa);
                    if (!check) {
                        JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere il veicolo.");
                    } else {
                        try {
                            controllerCliente.buildVeicoli(controllerCliente.getLoginCliente());
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere il veicolo.");
                        }
                        JOptionPane.showMessageDialog(null, "veicolo aggiunto.");
                        DefaultTableModel model = (DefaultTableModel) tableVeicoli.getModel();
                        model.addRow(new Object[]{tipoVeicolo, targa});
                    }
                }
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
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        centralPanel = new JPanel();
        centralPanel.setLayout(new CardLayout(0, 0));
        panel1.add(centralPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelCorse = new JPanel();
        panelCorse.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        centralPanel.add(panelCorse, "CardCorse");
        panelTabellaCorse = new JPanel();
        panelTabellaCorse.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelCorse.add(panelTabellaCorse, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrollPaneCorse = new JScrollPane();
        scrollPaneCorse.setEnabled(false);
        panelTabellaCorse.add(scrollPaneCorse, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(800, 150), new Dimension(800, 400), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panelTabellaCorse.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panelTabellaCorse.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelLegendaCorse = new JPanel();
        panelLegendaCorse.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelTabellaCorse.add(panelLegendaCorse, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panelTabellaCorse.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        sideBarDx = new JPanel();
        sideBarDx.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelCorse.add(sideBarDx, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelAcquista = new JPanel();
        panelAcquista.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        sideBarDx.add(panelAcquista, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        acquistaButton = new JButton();
        Font acquistaButtonFont = this.$$$getFont$$$(null, Font.BOLD, 24, acquistaButton.getFont());
        if (acquistaButtonFont != null) acquistaButton.setFont(acquistaButtonFont);
        acquistaButton.setForeground(new Color(-1));
        acquistaButton.setHorizontalTextPosition(2);
        acquistaButton.setText("Acquista");
        panelAcquista.add(acquistaButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(210, 60), new Dimension(210, 60), 0, false));
        panelInfo = new JPanel();
        panelInfo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        sideBarDx.add(panelInfo, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelInfoPorto = new JLabel();
        labelInfoPorto.setText("Info Porti");
        panelInfo.add(labelInfoPorto, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelButtonInfoPorto = new JLabel();
        labelButtonInfoPorto.setText("");
        panelInfo.add(labelButtonInfoPorto, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Compagnie");
        panelInfo.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxCompagnie = new JComboBox();
        panelInfo.add(comboBoxCompagnie, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), new Dimension(100, -1), 0, false));
        bVediInfoCompagnia = new JButton();
        bVediInfoCompagnia.setText("Contatta");
        bVediInfoCompagnia.setVerticalAlignment(0);
        panelInfo.add(bVediInfoCompagnia, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), new Dimension(100, -1), 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("");
        sideBarDx.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelLogout = new JPanel();
        panelLogout.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        sideBarDx.add(panelLogout, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelLogout = new JLabel();
        labelLogout.setHorizontalAlignment(4);
        labelLogout.setText("Esci");
        panelLogout.add(labelLogout, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(75, 17), null, 0, false));
        labelButtonLogout = new JLabel();
        labelButtonLogout.setText("");
        panelLogout.add(labelButtonLogout, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("");
        sideBarDx.add(label3, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelVeicoli = new JPanel();
        panelVeicoli.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 3, new Insets(0, 0, 0, 0), -1, -1));
        centralPanel.add(panelVeicoli, "CardVeicoli");
        scrollPaneVeicoli = new JScrollPane();
        panelVeicoli.add(scrollPaneVeicoli, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(600, 400), new Dimension(600, 400), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panelVeicoli.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panelVeicoli.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelAggiungiVeicolo = new JPanel();
        panelAggiungiVeicolo.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelVeicoli.add(panelAggiungiVeicolo, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tfTargaVeicolo = new JTextField();
        tfTargaVeicolo.setText("Targa");
        panelAggiungiVeicolo.add(tfTargaVeicolo, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cbTipoVeicolo = new JComboBox();
        cbTipoVeicolo.setToolTipText("");
        panelAggiungiVeicolo.add(cbTipoVeicolo, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panelAggiungiVeicolo.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer7 = new com.intellij.uiDesigner.core.Spacer();
        panelAggiungiVeicolo.add(spacer7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        labelButtonAggiungiVeicolo = new JLabel();
        labelButtonAggiungiVeicolo.setText("Aggiungi");
        panelAggiungiVeicolo.add(labelButtonAggiungiVeicolo, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelLogoutVeicoli1 = new JPanel();
        panelLogoutVeicoli1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelVeicoli.add(panelLogoutVeicoli1, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelLogoutVeicoli2 = new JPanel();
        panelLogoutVeicoli2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelLogoutVeicoli1.add(panelLogoutVeicoli2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setHorizontalAlignment(4);
        label4.setText("Esci");
        panelLogoutVeicoli2.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(75, 17), null, 0, false));
        labelButtonLogoutVeicoli = new JLabel();
        labelButtonLogoutVeicoli.setText("");
        panelLogoutVeicoli2.add(labelButtonLogoutVeicoli, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer8 = new com.intellij.uiDesigner.core.Spacer();
        panelLogoutVeicoli1.add(spacer8, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("");
        panelVeicoli.add(label5, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("");
        panelVeicoli.add(label6, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelBiglietti = new JPanel();
        panelBiglietti.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        centralPanel.add(panelBiglietti, "CardBiglietti");
        panelTabellaBiglietti = new JPanel();
        panelTabellaBiglietti.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelBiglietti.add(panelTabellaBiglietti, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelLegendaBiglietti = new JPanel();
        panelLegendaBiglietti.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelTabellaBiglietti.add(panelLegendaBiglietti, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(800, -1), new Dimension(800, -1), 0, false));
        scrollPaneBiglietti = new JScrollPane();
        panelTabellaBiglietti.add(scrollPaneBiglietti, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(800, 400), new Dimension(800, 400), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer9 = new com.intellij.uiDesigner.core.Spacer();
        panelBiglietti.add(spacer9, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer10 = new com.intellij.uiDesigner.core.Spacer();
        panelBiglietti.add(spacer10, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelLogoutBiglietti1 = new JPanel();
        panelLogoutBiglietti1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelBiglietti.add(panelLogoutBiglietti1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelLogoutBiglietti2 = new JPanel();
        panelLogoutBiglietti2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelLogoutBiglietti1.add(panelLogoutBiglietti2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setHorizontalAlignment(4);
        label7.setText("Esci");
        panelLogoutBiglietti2.add(label7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(75, 17), null, 0, false));
        labelButtonLogoutBiglietti = new JLabel();
        labelButtonLogoutBiglietti.setText("");
        panelLogoutBiglietti2.add(labelButtonLogoutBiglietti, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer11 = new com.intellij.uiDesigner.core.Spacer();
        panelLogoutBiglietti1.add(spacer11, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("");
        panelBiglietti.add(label8, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("");
        panelBiglietti.add(label9, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sideBarSx = new JPanel();
        sideBarSx.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(sideBarSx, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelAreaPersonale = new JPanel();
        panelAreaPersonale.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        sideBarSx.add(panelAreaPersonale, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelVeicoli = new JLabel();
        labelVeicoli.setText("I tuoi veicoli");
        panelAreaPersonale.add(labelVeicoli, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelButtonVeicoli = new JLabel();
        labelButtonVeicoli.setText("");
        panelAreaPersonale.add(labelButtonVeicoli, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelBiglietti = new JLabel();
        labelBiglietti.setText("I tuoi biglietti");
        panelAreaPersonale.add(labelBiglietti, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelButtonBiglietti = new JLabel();
        labelButtonBiglietti.setText("");
        panelAreaPersonale.add(labelButtonBiglietti, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelButtonCerca = new JPanel();
        panelButtonCerca.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        sideBarSx.add(panelButtonCerca, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        bCercaCorse = new JButton();
        Font bCercaCorseFont = this.$$$getFont$$$(null, Font.BOLD, 24, bCercaCorse.getFont());
        if (bCercaCorseFont != null) bCercaCorse.setFont(bCercaCorseFont);
        bCercaCorse.setForeground(new Color(-1));
        bCercaCorse.setHideActionText(false);
        bCercaCorse.setHorizontalTextPosition(2);
        bCercaCorse.setText("Cerca");
        panelButtonCerca.add(bCercaCorse, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(180, -1), new Dimension(180, -1), 0, false));
        panelParametri = new JPanel();
        panelParametri.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(10, 2, new Insets(0, 0, 0, 0), -1, -1));
        sideBarSx.add(panelParametri, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelPart = new JLabel();
        labelPart.setText("Partenza da");
        panelParametri.add(labelPart, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        comboBoxPart = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        comboBoxPart.setModel(defaultComboBoxModel1);
        panelParametri.add(comboBoxPart, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(90, -1), new Dimension(90, -1), 0, false));
        comboBoxDest = new JComboBox();
        panelParametri.add(comboBoxDest, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(90, -1), new Dimension(90, -1), 0, false));
        labelDest = new JLabel();
        labelDest.setText("Arrivo a");
        panelParametri.add(labelDest, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelButtonSelezionaData = new JLabel();
        labelButtonSelezionaData.setText("");
        panelParametri.add(labelButtonSelezionaData, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelData = new JLabel();
        labelData.setText("Data");
        panelParametri.add(labelData, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textData = new JTextField();
        panelParametri.add(textData, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, -1), new Dimension(90, -1), 0, false));
        labelOrario = new JLabel();
        labelOrario.setText("Orario");
        panelParametri.add(labelOrario, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelOrario = new JPanel();
        panelOrario.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelParametri.add(panelOrario, new com.intellij.uiDesigner.core.GridConstraints(4, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        spinnerOre = new JSpinner();
        panelOrario.add(spinnerOre, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), new Dimension(40, -1), 0, false));
        spinnerMinuti = new JSpinner();
        panelOrario.add(spinnerMinuti, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, -1), new Dimension(40, -1), 0, false));
        comboBoxVeicoli = new JComboBox();
        panelParametri.add(comboBoxVeicoli, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, -1), new Dimension(90, -1), 0, false));
        labelVeicolo = new JLabel();
        labelVeicolo.setText("Veicolo");
        panelParametri.add(labelVeicolo, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxBagaglio = new JCheckBox();
        checkBoxBagaglio.setText("");
        panelParametri.add(checkBoxBagaglio, new com.intellij.uiDesigner.core.GridConstraints(6, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelBagaglio = new JLabel();
        labelBagaglio.setText("Bagaglio");
        panelParametri.add(labelBagaglio, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerEta = new JSpinner();
        panelParametri.add(spinnerEta, new com.intellij.uiDesigner.core.GridConstraints(7, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, -1), new Dimension(90, -1), 0, false));
        labelEta = new JLabel();
        labelEta.setText("Età Passeggero");
        panelParametri.add(labelEta, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textCostoMax = new JTextField();
        textCostoMax.setText("1000");
        panelParametri.add(textCostoMax, new com.intellij.uiDesigner.core.GridConstraints(8, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(90, -1), new Dimension(90, -1), 0, false));
        labelPrezzoMax = new JLabel();
        labelPrezzoMax.setText("Prezzo massimo");
        panelParametri.add(labelPrezzoMax, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelNatanti = new JLabel();
        labelNatanti.setText("Natanti");
        labelNatanti.setVerticalAlignment(1);
        panelParametri.add(labelNatanti, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelNatanti = new JPanel();
        panelNatanti.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelParametri.add(panelNatanti, new com.intellij.uiDesigner.core.GridConstraints(9, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        checkBoxTraghetto = new JCheckBox();
        checkBoxTraghetto.setText("Traghetto");
        panelNatanti.add(checkBoxTraghetto, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxMotonave = new JCheckBox();
        checkBoxMotonave.setText("Motonave");
        panelNatanti.add(checkBoxMotonave, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxAliscafo = new JCheckBox();
        checkBoxAliscafo.setText("Aliscafo");
        panelNatanti.add(checkBoxAliscafo, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxAltro = new JCheckBox();
        checkBoxAltro.setText("Altro");
        panelNatanti.add(checkBoxAltro, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("");
        sideBarSx.add(label10, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("");
        sideBarSx.add(label11, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("");
        panel1.add(label12, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
