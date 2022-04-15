package com.prima.view.model;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;



/**
 * @version 1.0 11/09/98
 */
public class ButtonDeleteEditor extends DefaultCellEditor {

    protected JButton button;
    private int label;
    private boolean isPushed;

    public ButtonDeleteEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.setText("Delete");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        //label = (value == null) ? "" : value.toString();
        label = row;
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