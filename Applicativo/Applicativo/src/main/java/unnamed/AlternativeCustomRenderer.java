package unnamed;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class AlternativeCustomRenderer extends DefaultTableCellRenderer {
    private ArrayList<Boolean> booleanListRed;
    private ArrayList<Boolean> booleanListGreen;
    private ArrayList<Boolean> booleanListBlue;

    public AlternativeCustomRenderer(ArrayList<Boolean> booleanListRed, ArrayList<Boolean> booleanListGreen, ArrayList<Boolean> booleanListBlue) {
        this.booleanListRed = booleanListRed;
        this.booleanListGreen = booleanListGreen;
        this.booleanListBlue = booleanListBlue;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row < booleanListRed.size() && booleanListRed.get(row)) {
            comp.setBackground(new Color(255, 70, 70, 255));
        } else if (row < booleanListGreen.size() && booleanListGreen.get(row)) {
            comp.setBackground(new Color(50, 255, 60, 255));
        } else if (row < booleanListBlue.size() && booleanListBlue.get(row)) {
            comp.setBackground(new Color(80, 155, 255, 255));
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