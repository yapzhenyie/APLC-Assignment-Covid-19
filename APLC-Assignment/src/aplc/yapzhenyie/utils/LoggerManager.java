/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplc.yapzhenyie.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.JTextArea;

/**
 *
 * @author Yap Zhen Yie
 */
public class LoggerManager {

    public static void addLogMessage(JTextArea textArea, String message) {
        if (message == null) {
            return;
        }
        String newMsg = getDateFormat() + " [Log]: " + message;
        String currentMessage = textArea.getText();
        if (currentMessage.isEmpty()) {
            currentMessage += newMsg;
        } else {
            currentMessage += "\n" + newMsg;
        }
        textArea.setText(currentMessage);
    }

    public static void addErrorMessage(JTextArea textArea, String message) {
        if (message == null) {
            return;
        }
        String newMsg = getDateFormat() + " [Error]: " + message;
        String currentMessage = textArea.getText();
        if (currentMessage.isEmpty()) {
            currentMessage += newMsg;
        } else {
            currentMessage += "\n" + newMsg;
        }
        textArea.setText(currentMessage);
    }

    /**
     * Get the current date time for creating log message.
     *
     * @return date time "[MM/dd/yyyy HH:mm:ss]"
     */
    public static String getDateFormat() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // S is the millisecond
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        return "[" + simpleDateFormat.format(timestamp) + "]";
    }
}
