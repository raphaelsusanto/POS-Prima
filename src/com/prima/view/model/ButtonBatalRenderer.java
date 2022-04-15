package com.prima.view.model;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @version 1.0 11/09/98
 */
public class ButtonBatalRenderer extends JButton implements TableCellRenderer {

    public ButtonBatalRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
//        if (isSelected) {
//            setForeground(table.getSelectionForeground());
//            setBackground(table.getSelectionBackground());
//        } else {
//            setForeground(table.getForeground());
//            setBackground(UIManager.getColor("Button.background"));
//        }
//        this.setBorderPainted(false);
//        setContentAreaFilled(false);
        //setSize(200, 200);
        //setIcon(new ImageIcon(getClass().getResource("/icon/pencil_1.png")));
        setText("Batal");
        return this;
    }
}