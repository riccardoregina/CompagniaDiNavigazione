package unnamed;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class AlternativeCustomRenderer extends DefaultTableCellRenderer {
    private ArrayList<Boolean> booleanListCancellata;
    private ArrayList<Boolean> booleanListInCorso;
    private ArrayList<Boolean> booleanListTerminata;

    public AlternativeCustomRenderer(ArrayList<Boolean> booleanListCancellata, ArrayList<Boolean> booleanListInCorso, ArrayList<Boolean> booleanListTerminata) {
        this.booleanListCancellata = booleanListCancellata;
        this.booleanListInCorso = booleanListInCorso;
        this.booleanListTerminata = booleanListTerminata;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row < booleanListCancellata.size() && booleanListCancellata.get(row)) {
            comp.setBackground(new Color(255, 83, 83));
        } else if (row < booleanListInCorso.size() && booleanListInCorso.get(row)) {
            comp.setBackground(new Color(181, 255, 182));
        } else if (row < booleanListTerminata.size() && booleanListTerminata.get(row)) {
            comp.setBackground(new Color(203, 203, 203));
        } else {
            comp.setBackground(table.getBackground());
            comp.setForeground(table.getForeground());
        }

        if (isSelected) {
            comp.setBackground(table.getSelectionBackground());
            comp.setForeground(table.getSelectionForeground());
        }

        ((DefaultTableCellRenderer) comp).setHorizontalAlignment(CENTER);
        ((DefaultTableCellRenderer) comp).setVerticalAlignment(CENTER);

        return comp;
    }
}