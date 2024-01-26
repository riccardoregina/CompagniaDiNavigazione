package gui;

import controller.ControllerCompagnia;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Locale;

public class GestisciContatti {
    private JPanel contentPane;
    private JPanel panelTelefoni;
    private JLabel labelTelefono;
    private JScrollPane scrollPaneTelefoni;
    private JTable tableTelefoni;
    private JButton buttonAddTel;
    private JButton buttonEliminaTel;
    private JLabel labelSocial;
    private JScrollPane scrollPaneSocial;
    private JTable tableSocial;
    private JButton buttonAddSocial;
    private JButton buttonEliminaSocial;
    private JPanel panelEmail;
    private JLabel labelEmail;
    private JScrollPane scrollPaneEmail;
    private JTable tableEmail;
    private JButton buttonAddEmail;
    private JButton buttonEliminaEmail;
    private JLabel labelSitoWeb;
    private JButton buttonChiudi;
    private JLabel labelContatti;
    private JButton buttonModificaSito;
    private JLabel labelFb;
    private JLabel labelIg;
    private JLabel labelYt;
    private JLabel labelTk;
    private JPanel panelSocial;
    private JPanel panelContatti;
    private JPanel panel;
    private JPanel panelSito;
    private JLabel labelAddPhone;
    private JLabel labelRemovePhone;
    private JLabel labelAddEmail;
    private JLabel labelRemoveEmail;
    private JLabel labelRemoveSocial;
    private JLabel labelAddSocial;
    private JPanel panelT;
    private JPanel panelE;
    private JPanel panelS;
    public JFrame frame;
    public JFrame frameChiamante;
    public ControllerCompagnia controllerCompagnia;

    public GestisciContatti(JFrame frameChiamante, ControllerCompagnia controllerCompagnia) {
        this.frameChiamante = frameChiamante;
        this.controllerCompagnia = controllerCompagnia;
        frame = new JFrame("Contatti");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.8));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("resources/icons/logo.png").getImage().getScaledInstance(400, 400, 1));
        frame.setVisible(true);

        contentPane.setBackground(Color.white);
        panel.setBackground(Color.white);
        panelContatti.setBackground(Color.white);
        panelTelefoni.setBackground(Color.white);
        panelT.setBackground(Color.white);
        panelEmail.setBackground(Color.white);
        panelE.setBackground(Color.white);
        panelSocial.setBackground(Color.white);
        panelS.setBackground(Color.white);
        panelSito.setBackground(Color.white);


        ImageIcon imageTelefono = new ImageIcon("resources/icons/icons_contatti/icons8-telefono-48.png");
        labelTelefono.setIcon(imageTelefono);
        labelTelefono.setHorizontalTextPosition(SwingConstants.LEFT);

        ImageIcon imageEmail = new ImageIcon("resources/icons/icons_contatti/icons8-mail-with-wings-25.png");
        labelEmail.setIcon(imageEmail);
        labelEmail.setHorizontalTextPosition(SwingConstants.LEFT);

        ImageIcon imageFb = new ImageIcon("resources/icons/icons_contatti/icons8-facebook-48.png");
        ImageIcon imageIg = new ImageIcon("resources/icons/icons_contatti/icons8-instagram-old-48.png");
        ImageIcon imageYt = new ImageIcon("resources/icons/icons_contatti/icons8-youtube-48.png");
        ImageIcon imageTk = new ImageIcon("resources/icons/icons_contatti/icons8-tic-toc-25.png");
        labelFb.setIcon(imageFb);
        labelIg.setIcon(imageIg);
        labelYt.setIcon(imageYt);
        labelTk.setIcon(imageTk);

        ImageIcon imageWww = new ImageIcon("resources/icons/icons_contatti/icons8-web-48.png");
        labelSitoWeb.setIcon(imageWww);
        labelSitoWeb.setHorizontalTextPosition(SwingConstants.LEFT);


        ImageIcon imageAdd = new ImageIcon("resources/icons/icons8-aggiungi-48.png");
        ImageIcon imageRemove = new ImageIcon("resources/icons/icons8-elimina-48.png");
        ImageIcon imageAddHovered = new ImageIcon("resources/icons/icons8-aggiungi-48_hovered.png");
        ImageIcon imageRemoveHovered = new ImageIcon("resources/icons/icons8-elimina-48_hovered.png");

        labelAddPhone.setIcon(imageAdd);
        labelRemovePhone.setIcon(imageRemove);
        labelAddEmail.setIcon(imageAdd);
        labelRemoveEmail.setIcon(imageRemove);
        labelAddSocial.setIcon(imageAdd);
        labelRemoveSocial.setIcon(imageRemove);

        ArrayList<String> nomeSocial = new ArrayList<String>();
        ArrayList<String> tag = new ArrayList<String>();
        ArrayList<String> email = new ArrayList<String>();
        ArrayList<String> telefono = new ArrayList<String>();
        ArrayList<String> sitoWeb = new ArrayList<String>();

        controllerCompagnia.visualizzaContatti(nomeSocial, tag, email, telefono, sitoWeb);

        labelSitoWeb.setText("Sito Web: " + sitoWeb.getFirst());

        String[] colSocial = new String[]{"Social", "Tag"};
        Object[][] soc = new Object[nomeSocial.size()][2];
        for (int i = 0; i < nomeSocial.size(); i++) {
            soc[i][0] = nomeSocial.get(i);
            soc[i][1] = tag.get(i);
        }

        String[] colTel = new String[]{"Numeri di Telefono"};
        Object[][] tel = new Object[telefono.size()][1];
        for (int i = 0; i < telefono.size(); i++) {
            tel[i][0] = telefono.get(i);
        }

        String[] colEmail = new String[]{"Indirizzi Email"};
        Object[][] em = new Object[email.size()][1];
        for (int i = 0; i < email.size(); i++) {
            em[i][0] = email.get(i);
        }

        DefaultTableModel modelSocial = new DefaultTableModel(soc, colSocial) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableModel modelTel = new DefaultTableModel(tel, colTel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableModel modelEmail = new DefaultTableModel(em, colEmail) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        tableSocial = new JTable(modelSocial);
        tableSocial.getTableHeader().setReorderingAllowed(false);
        tableSocial.setDefaultRenderer(Object.class, centerRenderer);
        ListSelectionModel selectionModelSocial = tableSocial.getSelectionModel();
        selectionModelSocial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneSocial.setViewportView(tableSocial);

        tableEmail = new JTable(modelEmail);
        tableEmail.getTableHeader().setReorderingAllowed(false);
        tableEmail.setDefaultRenderer(Object.class, centerRenderer);
        ListSelectionModel selectionModelEmail = tableEmail.getSelectionModel();
        selectionModelEmail.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneEmail.setViewportView(tableEmail);

        tableTelefoni = new JTable(modelTel);
        tableTelefoni.getTableHeader().setReorderingAllowed(false);
        tableTelefoni.setDefaultRenderer(Object.class, centerRenderer);
        ListSelectionModel selectionModelTel = tableSocial.getSelectionModel();
        selectionModelTel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPaneTelefoni.setViewportView(tableTelefoni);

        labelAddPhone.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String inputValue = JOptionPane.showInputDialog("Inserisci un nuovo numero di telefono");
                if (inputValue != null && !inputValue.isEmpty()) {
                    if (controllerCompagnia.aggiungiTelefono(inputValue)) {
                        modelTel.addRow(new Object[]{inputValue});
                        JOptionPane.showMessageDialog(null, "Numero di telefono aggiunto!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere il numero di telefono");
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelAddPhone.setIcon(imageAddHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelAddPhone.setIcon(imageAdd);
            }
        });

        labelRemovePhone.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = tableTelefoni.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona un numero di telefono");
                    return;
                }
                String decision = JOptionPane.showInputDialog("Sei sicuro di voler eliminare l'oggetto selezionato? (S, n)");
                if (decision == null) return;
                if (decision.charAt(0) == 's' || decision.charAt(0) == 'S') {
                    if (controllerCompagnia.eliminaTelefono((String) tableTelefoni.getValueAt(selectedRow, 0))) {
                        modelTel.removeRow(selectedRow);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelRemovePhone.setIcon(imageRemoveHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelRemovePhone.setIcon(imageRemove);
            }
        });

        labelAddEmail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String inputValue = JOptionPane.showInputDialog("Inserisci un nuovo indirizzo email");
                if (inputValue != null && !inputValue.isEmpty()) {
                    if (controllerCompagnia.aggiungiEmail(inputValue)) {
                        modelEmail.addRow(new Object[]{inputValue});
                        JOptionPane.showMessageDialog(null, "Indirizzo email aggiunto!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere l'indirizzo email");
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelAddEmail.setIcon(imageAddHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelAddEmail.setIcon(imageAdd);
            }
        });

        labelRemoveEmail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = tableEmail.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona un indirizzo email");
                    return;
                }

                String decision = JOptionPane.showInputDialog("Sei sicuro di voler eliminare l'oggetto selezionato? (S, n)");
                if (decision == null) return;
                if (decision.charAt(0) == 's' || decision.charAt(0) == 'S') {
                    if (controllerCompagnia.eliminaEmail((String) tableEmail.getValueAt(selectedRow, 0))) {
                        modelEmail.removeRow(selectedRow);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelRemoveEmail.setIcon(imageRemoveHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelRemoveEmail.setIcon(imageRemove);
            }
        });

        labelAddSocial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String inputSocial = JOptionPane.showInputDialog("Inserisci il nome del social");
                if (inputSocial != null && !inputSocial.isEmpty()) {
                    String inputTag = JOptionPane.showInputDialog("Inserisci il tag del profilo");
                    if (inputTag != null && !inputTag.isEmpty()) {
                        if (controllerCompagnia.aggiungiSocial(inputSocial, inputTag)) {
                            modelSocial.addRow(new Object[]{inputSocial, inputTag});
                            JOptionPane.showMessageDialog(null, "Profilo social aggiunto!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Non è stato possibile aggiungere il profilo social");
                        }
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelAddSocial.setIcon(imageAddHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelAddSocial.setIcon(imageAdd);
            }
        });

        labelRemoveSocial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = tableSocial.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleziona un profilo social");
                    return;
                }

                String decision = JOptionPane.showInputDialog("Sei sicuro di voler eliminare l'oggetto selezionato? (S, n)");
                if (decision == null) return;
                if (decision.charAt(0) == 's' || decision.charAt(0) == 'S') {
                    if (controllerCompagnia.eliminaSocial((String) tableSocial.getValueAt(selectedRow, 0), (String) tableSocial.getValueAt(selectedRow, 1))) {
                        modelSocial.removeRow(selectedRow);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                labelRemoveSocial.setIcon(imageRemoveHovered);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                labelRemoveSocial.setIcon(imageRemove);
            }
        });

        buttonModificaSito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Inserisci il nuovo nome del sito web");
                if (inputValue != null && !inputValue.isEmpty()) {
                    if (!controllerCompagnia.modificaSitoWeb(inputValue)) {
                        JOptionPane.showMessageDialog(null, "Non è stato possibile modificare il nome del sito web");
                    } else {
                        labelSitoWeb.setText("Sito Web: " + inputValue);
                    }
                }
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel = new JPanel();
        panel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelContatti = new JPanel();
        panelContatti.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panelContatti, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 300), new Dimension(-1, 300), new Dimension(-1, 300), 0, false));
        panelTelefoni = new JPanel();
        panelTelefoni.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelContatti.add(panelTelefoni, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelTelefono = new JLabel();
        Font labelTelefonoFont = this.$$$getFont$$$(null, -1, 18, labelTelefono.getFont());
        if (labelTelefonoFont != null) labelTelefono.setFont(labelTelefonoFont);
        labelTelefono.setText("Telefoni");
        panelTelefoni.add(labelTelefono, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneTelefoni = new JScrollPane();
        panelTelefoni.add(scrollPaneTelefoni, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelT = new JPanel();
        panelT.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelTelefoni.add(panelT, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelAddPhone = new JLabel();
        labelAddPhone.setText("");
        panelT.add(labelAddPhone, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRemovePhone = new JLabel();
        labelRemovePhone.setText("");
        panelT.add(labelRemovePhone, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelSocial = new JPanel();
        panelSocial.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelContatti.add(panelSocial, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelSocial = new JLabel();
        Font labelSocialFont = this.$$$getFont$$$(null, -1, 18, labelSocial.getFont());
        if (labelSocialFont != null) labelSocial.setFont(labelSocialFont);
        labelSocial.setText("Social");
        panelSocial.add(labelSocial, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneSocial = new JScrollPane();
        panelSocial.add(scrollPaneSocial, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelS = new JPanel();
        panelS.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelSocial.add(panelS, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelAddSocial = new JLabel();
        labelAddSocial.setText("");
        panelS.add(labelAddSocial, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRemoveSocial = new JLabel();
        labelRemoveSocial.setText("");
        panelS.add(labelRemoveSocial, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelTk = new JLabel();
        labelTk.setText("");
        panelSocial.add(labelTk, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelFb = new JLabel();
        labelFb.setText("");
        panelSocial.add(labelFb, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelIg = new JLabel();
        labelIg.setText("");
        panelSocial.add(labelIg, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelYt = new JLabel();
        labelYt.setText("");
        panelSocial.add(labelYt, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelEmail = new JPanel();
        panelEmail.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelContatti.add(panelEmail, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelEmail = new JLabel();
        Font labelEmailFont = this.$$$getFont$$$(null, -1, 18, labelEmail.getFont());
        if (labelEmailFont != null) labelEmail.setFont(labelEmailFont);
        labelEmail.setText("Email");
        panelEmail.add(labelEmail, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneEmail = new JScrollPane();
        panelEmail.add(scrollPaneEmail, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelE = new JPanel();
        panelE.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelEmail.add(panelE, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelAddEmail = new JLabel();
        labelAddEmail.setText("");
        panelE.add(labelAddEmail, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRemoveEmail = new JLabel();
        labelRemoveEmail.setText("");
        panelE.add(labelRemoveEmail, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonChiudi = new JButton();
        buttonChiudi.setHorizontalAlignment(0);
        buttonChiudi.setText("Chiudi");
        panel.add(buttonChiudi, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), new Dimension(140, -1), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelSito = new JPanel();
        panelSito.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panelSito, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelSitoWeb = new JLabel();
        Font labelSitoWebFont = this.$$$getFont$$$(null, -1, 18, labelSitoWeb.getFont());
        if (labelSitoWebFont != null) labelSitoWeb.setFont(labelSitoWebFont);
        labelSitoWeb.setText("Sito Web: ");
        panelSito.add(labelSitoWeb, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panelSito.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        buttonModificaSito = new JButton();
        buttonModificaSito.setText("Modifica Sito");
        panelSito.add(buttonModificaSito, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_EAST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelContatti = new JLabel();
        Font labelContattiFont = this.$$$getFont$$$(null, -1, 22, labelContatti.getFont());
        if (labelContattiFont != null) labelContatti.setFont(labelContattiFont);
        labelContatti.setText("Contatti");
        contentPane.add(labelContatti, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("");
        contentPane.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("");
        contentPane.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("");
        contentPane.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("");
        contentPane.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
