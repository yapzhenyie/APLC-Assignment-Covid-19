/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Yap Zhen Yie
 */
public class DialogUtils {
    
    /**
     * Following source code referenced from (bonney 2015)
     * Source: https://stackoverflow.com/questions/29098863/how-to-make-a-red-message-in-swing-joptionpane
     * 
     * Shows a titled error message box. Allow multiple line messages.
     */
    public static void showErrorMessageDialog(Component component, String message) {
        JOptionPane.showMessageDialog(component, message, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    public static void showColoredErrorMessageDialog(Component component, String message, Color color) {
        showColoredMessageDialog(component, message, color, "Error Message", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Shows a colored error message. Only allow single line message.
     */
    public static void showColoredMessageDialog(Component component, String message, Color color, String title, int option) {
        JLabel label = new JLabel(message);
        label.setForeground(color);
        JOptionPane.showMessageDialog(component, label, title, option);
    }
}
