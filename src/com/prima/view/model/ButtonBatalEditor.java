package com.prima.view.model;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @version 1.0 11/09/98
 */
public class ButtonBatalEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonBatalEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        //button.setIcon(new ImageIcon(getClass().getResource("/icon/pencil_1.png")));
        button.setText("Batal");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
//        if (isSelected) {
//            button.setForeground(Color.BLUE);
//            button.setBackground(Color.BLACK);
//        } else {
//            button.setForeground(table.getForeground());
//            button.setBackground(table.getBackground());
//        }
//        button.setBorderPainted(false);
//        button.setContentAreaFilled(false);
        label = (value == null) ? "" : value.toString();
        //button.setText("Ubah");
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
//        if (isPushed) {
//            System.out.println(label + "");
//        }
        isPushed = false;
        //button.setText("Ubah");
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        // button.setText("Ubah");
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
//        super.fireEditingStopped();
    }

    public JButton getButton() {
        return button;
    }
}