package unnamed;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
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
                comp.setBackground(new Color(255, 70, 70, 255));
            } else {
                comp.setBackground(table.getBackground());
                comp.setForeground(table.getForeground());
            }
        } else {
            if ((row < booleanListRed.size() && booleanListRed.get(row)) && (row < booleanListYellow.size() && booleanListYellow.get(row))) {
                comp.setBackground(new Color(15, 115, 245, 255));
            } else if (row < booleanListRed.size() && booleanListRed.get(row)) {
                comp.setBackground(new Color(255, 70, 70, 255));
            } else if (row < booleanListYellow.size() && booleanListYellow.get(row)) {
                comp.setBackground(new Color(255, 250, 60, 255));
            } else {
                comp.setBackground(table.getBackground());
                comp.setForeground(table.getForeground());
            }
        }

        if(isSelected) {
            comp.setBackground(table.getSelectionBackground());
            comp.setForeground(table.getSelectionForeground());
        }

        return comp;
    }
}
