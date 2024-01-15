package unnamed;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class CustomRenderer extends DefaultTableCellRenderer {
    private ArrayList<Boolean> booleanListRed;
    private ArrayList<Boolean> booleanListYellow;

    public CustomRenderer(ArrayList<Boolean> booleanList) {
        this.booleanListRed = booleanList;
        this.booleanListYellow = null;
    }

    public CustomRenderer(ArrayList<Boolean> booleanListRed, ArrayList<Boolean> booleanListYellow) {
        this.booleanListRed = booleanListRed;
        this.booleanListYellow = booleanListYellow;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (booleanListYellow == null) {
            if (row < booleanListRed.size() && booleanListRed.get(row)) {
                comp.setBackground(new Color(215, 75, 75, 255));
            } else {
                comp.setBackground(table.getBackground());
                comp.setForeground(table.getForeground());
            }
        } else {
            if ((row < booleanListRed.size() && booleanListRed.get(row)) && (row < booleanListYellow.size() && booleanListYellow.get(row))) {
                comp.setBackground(new Color(180, 135, 220, 255));
            } else if (row < booleanListRed.size() && booleanListRed.get(row)) {
                comp.setBackground(new Color(215, 75, 75, 255));
            } else if (row < booleanListYellow.size() && booleanListYellow.get(row)) {
                comp.setBackground(new Color(120, 120, 120, 255));
                comp.setForeground(Color.white);
            } else {
                comp.setBackground(table.getBackground());
                comp.setForeground(table.getForeground());
            }
        }

        if(isSelected) {
            comp.setBackground(table.getSelectionBackground());
            comp.setForeground(table.getSelectionForeground());
        }

        ((DefaultTableCellRenderer) comp).setHorizontalAlignment(CENTER);
        ((DefaultTableCellRenderer) comp).setVerticalAlignment(CENTER);

        return comp;
    }

    public static void aggiungiColoreLegenda(JPanel panelLegenda, Color colore, String descrizione) {
        JPanel colorSquare = new JPanel();
        colorSquare.setPreferredSize(new Dimension(20, 20));
        colorSquare.setBackground(colore);
        Border bordo = BorderFactory.createLineBorder(Color.BLACK);
        colorSquare.setBorder(bordo);

        JLabel labelDescrizione = new JLabel(descrizione);

        JPanel bulletPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bulletPanel.add(colorSquare);
        bulletPanel.add(labelDescrizione);
        bulletPanel.setBackground(Color.white);
        panelLegenda.add(bulletPanel);
    }
}
