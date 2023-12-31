package gui;

import controller.ControllerCliente;
import unnamed.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The type Home cliente.
 */
public class HomeCliente {
    private JPanel panel1;
    private JPanel panelContainerbutton;
    private JButton bCercaCorse;
    private JButton bAggiungiVeicolo;
    private JButton bBigliettiCliente;
    private JButton bVeicoliCliente;
    private JPanel panelInfoCompagnia;
    private JComboBox comboBoxCompagnie;
    private JButton bVediInfoCompagnia;
    private JTextField textData;
    private JButton bCal;
    private JLabel label;
    private JScrollPane scrollPaneCorse;
    private SpinnerNumberModel modelOre = new SpinnerNumberModel(0, 0, 23, 1);
    private SpinnerNumberModel modelMinuti = new SpinnerNumberModel(0, 0, 59, 1);
    private SpinnerNumberModel modelEta = new SpinnerNumberModel(1, 1, 200, 1);
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
    private JPanel panelDate;

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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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

        ArrayList<String> porti = new ArrayList<String>();
        ArrayList<Integer> idPorti = new ArrayList<>();
        controllerCliente.visualizzaPorti(idPorti, porti);
        for (String p : porti) {
            comboBoxPart.addItem(p);
            comboBoxDest.addItem(p);
        }

        bCercaCorse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int portoPartenza = comboBoxPart.getSelectedIndex();
                int portoDestinazione = comboBoxDest.getSelectedIndex();
                Integer idPortoPart = idPorti.get(portoPartenza);
                Integer idPortoDest = idPorti.get(portoDestinazione);

                if (textData.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Inserire una data");
                    return;
                }

                LocalDate dataSelezionata = LocalDate.parse(textData.getText(), formatter);
                LocalTime orarioSelezionato = LocalTime.of((int) spinnerOre.getValue(), (int) spinnerMinuti.getValue());

                int etaPass = (int) spinnerEta.getValue();

                boolean bagaglio = checkBoxBagaglio.isSelected();
                boolean veicolo = false;
                if (!(comboBoxVeicoli.getSelectedItem().equals("Nessuno"))) {
                    if (etaPass >= 18) {
                        veicolo = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Un minorenne non può portare con sè un veicolo");
                        return;
                    }
                }

                String inputCostoMax = textCostoMax.getText();
                float prezzoMax;
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

                ArrayList<String> tipoNatanteSelezionato = new ArrayList<String>();
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

                ArrayList<Integer> idCorse = new ArrayList<Integer>();
                ArrayList<LocalDate> dateCor = new ArrayList<LocalDate>();
                ArrayList<Integer> postiDispPass = new ArrayList<Integer>();
                ArrayList<Integer> postiDispVei = new ArrayList<Integer>();
                ArrayList<Integer> minutiRitardo = new ArrayList<Integer>();
                ArrayList<Boolean> cancellata = new ArrayList<Boolean>();
                ArrayList<Float> prezzo = new ArrayList<Float>();

                controllerCliente.visualizzaCorse(idPortoPart, idPortoDest, dataSelezionata, orarioSelezionato, prezzoMax, tipoNatanteSelezionato, etaPass, veicolo, bagaglio, idCorse, dateCor, postiDispPass, postiDispVei, minutiRitardo, cancellata, prezzo);

            }
        });

        bCal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textData.setText(new DatePicker(frame).setPickedDate());
            }
        });

        bAggiungiVeicolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AggiungiVeicolo aggiungiVeicolo = new AggiungiVeicolo(frame, controllerCliente);
                frame.setVisible(false);
            }
        });

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
        bBigliettiCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuoiBiglietti iTuoiBiglietti = new TuoiBiglietti(frame, controllerCliente);
                frame.setVisible(false);
            }
        });
        bVeicoliCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuoiVeicoli iTuoiVeicoli = new TuoiVeicoli(frame, controllerCliente);
                frame.setVisible(false);
            }
        });
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
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(11, 7, new Insets(0, 0, 0, 0), -1, -1));
        panelContainerbutton = new JPanel();
        panelContainerbutton.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 14, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelContainerbutton, new com.intellij.uiDesigner.core.GridConstraints(0, 5, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(169, 260), new Dimension(-1, 260), 0, false));
        spinnerOre = new JSpinner();
        panelContainerbutton.add(spinnerOre, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerMinuti = new JSpinner();
        panelContainerbutton.add(spinnerMinuti, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxBagaglio = new JCheckBox();
        checkBoxBagaglio.setText("Bagaglio");
        panelContainerbutton.add(checkBoxBagaglio, new com.intellij.uiDesigner.core.GridConstraints(2, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        orarioLabel = new JLabel();
        orarioLabel.setText("Orario: ");
        panelContainerbutton.add(orarioLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textCostoMax = new JTextField();
        textCostoMax.setText("prezzo max");
        panelContainerbutton.add(textCostoMax, new com.intellij.uiDesigner.core.GridConstraints(2, 8, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panelContainerbutton.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 12, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        bCercaCorse = new JButton();
        bCercaCorse.setText("Cerca Corse");
        panelContainerbutton.add(bCercaCorse, new com.intellij.uiDesigner.core.GridConstraints(2, 10, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxTraghetto = new JCheckBox();
        checkBoxTraghetto.setText("Traghetto");
        panelContainerbutton.add(checkBoxTraghetto, new com.intellij.uiDesigner.core.GridConstraints(1, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelEta = new JLabel();
        labelEta.setText("Età Passeggero:");
        panelContainerbutton.add(labelEta, new com.intellij.uiDesigner.core.GridConstraints(0, 8, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxMotonave = new JCheckBox();
        checkBoxMotonave.setText("Motonave");
        panelContainerbutton.add(checkBoxMotonave, new com.intellij.uiDesigner.core.GridConstraints(1, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxAliscafo = new JCheckBox();
        checkBoxAliscafo.setText("Aliscafo");
        panelContainerbutton.add(checkBoxAliscafo, new com.intellij.uiDesigner.core.GridConstraints(1, 10, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        checkBoxAltro = new JCheckBox();
        checkBoxAltro.setText("Altro");
        panelContainerbutton.add(checkBoxAltro, new com.intellij.uiDesigner.core.GridConstraints(1, 11, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panelContainerbutton.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 7, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panelContainerbutton.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 6, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        comboBoxVeicoli = new JComboBox();
        panelContainerbutton.add(comboBoxVeicoli, new com.intellij.uiDesigner.core.GridConstraints(2, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerEta = new JSpinner();
        panelContainerbutton.add(spinnerEta, new com.intellij.uiDesigner.core.GridConstraints(0, 9, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelVeicolo = new JLabel();
        labelVeicolo.setText("Veicolo:");
        panelContainerbutton.add(labelVeicolo, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelDate = new JPanel();
        panelDate.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panelContainerbutton.add(panelDate, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 2, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        label = new JLabel();
        label.setText("Data:");
        panelDate.add(label, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textData = new JTextField();
        panelDate.add(textData, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        bCal = new JButton();
        bCal.setText("...");
        panelDate.add(bCal, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelInfoCompagnia = new JPanel();
        panelInfoCompagnia.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelInfoCompagnia, new com.intellij.uiDesigner.core.GridConstraints(10, 5, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(-1, 40), new Dimension(-1, 40), 0, false));
        bVediInfoCompagnia = new JButton();
        bVediInfoCompagnia.setText("visualizza contatti compagnia");
        panelInfoCompagnia.add(bVediInfoCompagnia, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(220, -1), new Dimension(220, -1), 0, false));
        comboBoxCompagnie = new JComboBox();
        comboBoxCompagnie.setEditable(false);
        panelInfoCompagnia.add(comboBoxCompagnie, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(120, -1), new Dimension(120, -1), 0, false));
        scrollPaneCorse = new JScrollPane();
        panel1.add(scrollPaneCorse, new com.intellij.uiDesigner.core.GridConstraints(3, 5, 7, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 150), new Dimension(-1, 400), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(7, 6, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer5 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer5, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        bBigliettiCliente = new JButton();
        bBigliettiCliente.setText("I tuoi Biglietti");
        panel1.add(bBigliettiCliente, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), new Dimension(140, -1), 0, false));
        bAggiungiVeicolo = new JButton();
        bAggiungiVeicolo.setText("Aggiungi Veicolo");
        panel1.add(bAggiungiVeicolo, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), new Dimension(140, -1), 0, false));
        bVeicoliCliente = new JButton();
        bVeicoliCliente.setText("I tuoi veicoli");
        panel1.add(bVeicoliCliente, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), new Dimension(140, -1), 0, false));
        acquistaButton = new JButton();
        acquistaButton.setText("Acquista");
        panel1.add(acquistaButton, new com.intellij.uiDesigner.core.GridConstraints(5, 1, 2, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer6 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer6, new com.intellij.uiDesigner.core.GridConstraints(1, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, new Dimension(20, -1), 0, false));
        labelPart = new JLabel();
        labelPart.setText("Partenza:");
        panel1.add(labelPart, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxPart = new JComboBox();
        panel1.add(comboBoxPart, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboBoxDest = new JComboBox();
        panel1.add(comboBoxDest, new com.intellij.uiDesigner.core.GridConstraints(4, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(150, -1), 0, false));
        labelDest = new JLabel();
        labelDest.setText("Destinazione:");
        panel1.add(labelDest, new com.intellij.uiDesigner.core.GridConstraints(4, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
