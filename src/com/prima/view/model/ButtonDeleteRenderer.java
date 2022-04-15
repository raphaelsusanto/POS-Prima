package com.prima.view.model;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * @version 1.0 11/09/98
 */
public class ButtonDeleteRenderer extends JButton implements TableCellRenderer {

    public ButtonDeleteRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText("Delete");
        return this;
    }
}