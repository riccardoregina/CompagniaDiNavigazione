package gui;

import controller.ControllerCompagnia;
import unnamed.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.BitSet;

public class CreaCorse {
    private JPanel panel1;
    private JButton bCrea;
    private JButton bPeriodo;
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
    private SpinnerModel modelOre = new SpinnerNumberModel(0, 0, 24, 1);
    private SpinnerModel modelMinuti = new SpinnerNumberModel(0, 0, 60,1);
    private JPanel panel1_2;
    private JSpinner spinnerOraPOre = new JSpinner(modelOre);
    private JSpinner spinnerOraAOre = new JSpinner(modelOre);
    private JSpinner spinnerOraPMin = new JSpinner(modelMinuti);
    private JSpinner spinnerOraAMin =new JSpinner(modelMinuti);
    private JTextField tfDataPart;
    private JTextField tfDataArrivo;

    public JFrame frame;

    public JFrame frameChiamante;

    public ControllerCompagnia controllerCompagnia;

    public CreaCorse (JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {
        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("crea Corsa");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        ArrayList<Pair> porti = new ArrayList<Pair>();
        ArrayList<String> natanti = new ArrayList<String>();

        controllerCompagnia.visualizzaPorti(porti);
        for (Pair porto : porti) {
            cbPortoPartenza.addItem(porto);
            cbPortoArrivo.addItem(porto);
        }

        controllerCompagnia.visualizzaNatanti(natanti);
        for (String natante : natanti ) {
            cbNatante.addItem(natante);
        }


        bCrea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idxPortoPartenza = cbPortoPartenza.getSelectedIndex();
                int idPortoPartenza = (int) porti.get(idxPortoPartenza).first;

                int idxPortoArrivo = cbPortoArrivo.getSelectedIndex();
                int idPortoArrivo = (int) porti.get(idxPortoArrivo).first;

                String giorniAttivi;
                BitSet giorniPeriodo = new BitSet(7);
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

                int oraPart = (int) spinnerOraPOre.getValue();
                int oraArrivo= (int) spinnerOraAOre.getValue();
                int minPart = (int) spinnerOraPMin.getValue();
                int minArrivo = (int) spinnerOraAMin.getValue();
                String dataP = tfDataPart.getText();
                String dataA = tfDataArrivo.getText();
                LocalDate dataPartenza;
                LocalDate dataArrivo;
                LocalTime orarioPartenza;
                LocalTime orarioArrivo;

                try {
                    dataPartenza = LocalDate.parse(dataP);
                    dataArrivo = LocalDate.parse(dataA);
                    orarioPartenza = LocalTime.of(oraPart,minPart);
                    orarioArrivo = LocalTime.of(oraArrivo,minArrivo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci le date in maniera corretta (d-mm-yyyy)");
                    return;
                }
                if ( dataPartenza.isAfter(dataArrivo) ) {
                    JOptionPane.showMessageDialog(null, "La partenza non può essere dopo dell'arrivo");
                    return;
                } else if (dataPartenza.equals(dataArrivo) && orarioPartenza.isAfter(orarioArrivo)) {
                    JOptionPane.showMessageDialog(null, "se le date coincidono l'arrivo deve avvenire dopo la partenza. Inserisci correttamente gli orari");
                }

                // controllare che le date siano giute e successivamente le ore anche

                //creo il bitset giusto per i giorni di attivita
                if (checkDom.isSelected())
                    giorniPeriodo.set(0);
                if (checkLun.isSelected())
                    giorniPeriodo.set(1);
                if (checkMar.isSelected())
                    giorniPeriodo.set(2);
                if (checkMer.isSelected())
                    giorniPeriodo.set(3);
                if (checkGio.isSelected())
                    giorniPeriodo.set(4);
                if (checkVen.isSelected())
                    giorniPeriodo.set(5);
                if (checkSab.isSelected())
                    giorniPeriodo.set(6);

                giorniAttivi = giorniPeriodo.toString();

                try {
                    costoIntero = Float.parseFloat(costo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore numerico in Costo Intero");
                    return;
                }

                try {
                    sconto = Float.parseFloat(valoreSconto);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore numerico in Sconto");
                    return;
                }
                if (sconto <0 || sconto >100) {
                    JOptionPane.showMessageDialog(null, "inserisci in sconto un valore tra 0 e 100");
                    return;
                }

                try {
                    costoPrevendita = Float.parseFloat(prevendita);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore numerico in prevendita");
                    return;
                }
                if (costoPrevendita<0) {
                    JOptionPane.showMessageDialog(null, "inserisci in sconto un valore positivo in prevendita");
                    return;
                }

                try {
                    costoVeicolo = Float.parseFloat(veicolo);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore numerico in veicolo");
                    return;
                }
                if (costoVeicolo<0) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore positivo in veicolo");
                    return;
                }

                try {
                    costoBagaglio = Float.parseFloat(bagaglio);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore numerico in bagaglio");
                    return;
                }
                if (costoPrevendita<0) {
                    JOptionPane.showMessageDialog(null, "inserisci un valore positivo in prevendita");
                    return;
                }



            }
        });


    }
}