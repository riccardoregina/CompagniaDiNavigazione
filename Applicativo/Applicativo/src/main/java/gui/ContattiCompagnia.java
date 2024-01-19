package gui;

import controller.ControllerCliente;

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
import java.util.ArrayList;
import java.util.Locale;

/**
 * The type Contatti compagnia.
 */
public class ContattiCompagnia {
    private JPanel contentPane;
    private JLabel labelInfoCompagnia;
    private JScrollPane scrollPaneTelefoni;
    private JTable tableTelefoni;
    private JScrollPane scrollPaneEmail;
    private JTable tableEmail;
    private JScrollPane scrollPaneSocial;
    private JTable tableSocial;
    private JLabel labelSitoWeb;
    private JButton buttonChiudi;
    private JPanel panelTelefoni;
    private JLabel labelTelefono;
    private JLabel labelSocial;
    private JPanel panelEmail;
    private JLabel labelEmail;
    private JLabel labelFb;
    private JLabel labelIg;
    private JLabel labelYt;
    private JLabel labelTk;
    private JPanel panelSocial;
    private JPanel panelContatti;
    private JPanel panel1;
    private JTable tableCompagnie;
    private JButton bChiudi;

    public JFrame frame;
    public JFrame frameChiamante;

    public ControllerCliente controllerCliente;

    public ContattiCompagnia(JFrame frameChiamante, ControllerCliente controllerCliente, String compagnia, JButton buttonChiamante) {
        this.frameChiamante = frameChiamante;
        this.controllerCliente = controllerCliente;
        frame = new JFrame("Contatti Compagnia");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (screenSize.width / 1.5), (int) (screenSize.height / 1.8));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("resources/icons/logo.png").getImage().getScaledInstance(400, 400, 1));
        frame.setVisible(true);

        contentPane.setBackground(Color.white);
        panel1.setBackground(Color.white);
        panelContatti.setBackground(Color.white);
        panelTelefoni.setBackground(Color.white);
        panelEmail.setBackground(Color.white);
        panelSocial.setBackground(Color.white);

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

        ArrayList<String> nomeSocial = new ArrayList<String>();
        ArrayList<String> tag = new ArrayList<String>();
        ArrayList<String> email = new ArrayList<String>();
        ArrayList<String> telefono = new ArrayList<String>();
        ArrayList<String> sitoWeb = new ArrayList<String>();

        controllerCliente.visualizzaContatti(compagnia, nomeSocial, tag, email, telefono, sitoWeb);

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

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                buttonChiamante.setEnabled(true);
            }
        });

        buttonChiudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonChiamante.setEnabled(true);
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

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        labelInfoCompagnia = new JLabel();
        Font labelInfoCompagniaFont = this.$$$getFont$$$(null, -1, 22, labelInfoCompagnia.getFont());
        if (labelInfoCompagniaFont != null) labelInfoCompagnia.setFont(labelInfoCompagniaFont);
        labelInfoCompagnia.setText("Info Compagnia");
        contentPane.add(labelInfoCompagnia, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("");
        contentPane.add(label1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("");
        contentPane.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("");
        contentPane.add(label3, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("");
        contentPane.add(label4, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelContatti = new JPanel();
        panelContatti.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panelContatti, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 300), new Dimension(-1, 300), new Dimension(-1, 300), 0, false));
        panelTelefoni = new JPanel();
        panelTelefoni.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelContatti.add(panelTelefoni, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelTelefono = new JLabel();
        Font labelTelefonoFont = this.$$$getFont$$$(null, -1, 18, labelTelefono.getFont());
        if (labelTelefonoFont != null) labelTelefono.setFont(labelTelefonoFont);
        labelTelefono.setText("Telefoni");
        panelTelefoni.add(labelTelefono, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneTelefoni = new JScrollPane();
        panelTelefoni.add(scrollPaneTelefoni, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panelSocial = new JPanel();
        panelSocial.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 5, new Insets(0, 0, 0, 0), -1, -1));
        panelContatti.add(panelSocial, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelSocial = new JLabel();
        Font labelSocialFont = this.$$$getFont$$$(null, -1, 18, labelSocial.getFont());
        if (labelSocialFont != null) labelSocial.setFont(labelSocialFont);
        labelSocial.setText("Social");
        panelSocial.add(labelSocial, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneSocial = new JScrollPane();
        panelSocial.add(scrollPaneSocial, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        panelEmail.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelContatti.add(panelEmail, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        labelEmail = new JLabel();
        Font labelEmailFont = this.$$$getFont$$$(null, -1, 18, labelEmail.getFont());
        if (labelEmailFont != null) labelEmail.setFont(labelEmailFont);
        labelEmail.setText("Email");
        panelEmail.add(labelEmail, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPaneEmail = new JScrollPane();
        panelEmail.add(scrollPaneEmail, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        labelSitoWeb = new JLabel();
        Font labelSitoWebFont = this.$$$getFont$$$(null, -1, 18, labelSitoWeb.getFont());
        if (labelSitoWebFont != null) labelSitoWeb.setFont(labelSitoWebFont);
        labelSitoWeb.setText("Sito Web: ");
        panel1.add(labelSitoWeb, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonChiudi = new JButton();
        buttonChiudi.setHorizontalAlignment(0);
        buttonChiudi.setText("Chiudi");
        panel1.add(buttonChiudi, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(140, -1), new Dimension(140, -1), 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
