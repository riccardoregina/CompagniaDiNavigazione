package unnamed;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class CustomRenderer extends DefaultTableCellRenderer {
    private ArrayList<Boolean> booleanList;

    public CustomRenderer(ArrayList<Boolean> booleanList) {
        this.booleanList = booleanList;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row < booleanList.size() && booleanList.get(row)) {
            comp.setBackground(new Color(255, 120, 120));
        } else {
            comp.setBackground(table.getBackground());
            comp.setForeground(table.getForeground());
        }

        if(isSelected) {
            comp.setBackground(table.getSelectionBackground());
            comp.setForeground(table.getSelectionForeground());
        }

        return comp;
    }
}
