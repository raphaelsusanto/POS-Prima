package com.prima.view.model;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

/**
 *
 * @author raphael
 */
public class NumberTextbox extends JTextField {

    public NumberTextbox() {
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                 if (!(((int) e.getKeyChar()) >= 46 && ((int) e.getKeyChar()) <= 57)) {
                    e.consume();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
}
