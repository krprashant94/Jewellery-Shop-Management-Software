/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewelleryshop.object;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ErrorHandler extends JFrame {

    public void SQLError(int code) {
        switch (code) {
            case 1:
                JOptionPane.showMessageDialog(this, "Data is successfully saved in Record.", "Successfully data saved", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 0:
            case 1366:
            case 1265:
                JOptionPane.showMessageDialog(this, "Illegal input supplied in place of number value.\nID, prices and weight must be an integer number.", "Data truncation: Incorrect integer value for column", JOptionPane.ERROR_MESSAGE);
                break;
            case 1062:
                JOptionPane.showMessageDialog(this, "(Duplicate Entry)A entry already exists for entered Tag ID.\nPlease use different Tag ID or edit this Details.", "Duplicate entry for PRIMARY key", JOptionPane.ERROR_MESSAGE);
                break;
            default:
        }
    }

    public void SomethingMissing() {
        JOptionPane.showMessageDialog(this, "It seems that you are leaving some entry.\nPlease fill all important entry before saving.", "Important fields are empty", JOptionPane.WARNING_MESSAGE);
    }

    public void showDeleteCompleted() {
        JOptionPane.showMessageDialog(this, "Data you want to delete is successfully deleted.", "Deleted...!", JOptionPane.INFORMATION_MESSAGE);

    }

    public void alreadyCompleted() {
        JOptionPane.showMessageDialog(this, "The taks you want to perform is already done.", "Already Completed", JOptionPane.INFORMATION_MESSAGE);
    }

    public void notAvailable() {
        JOptionPane.showMessageDialog(this, "The operation you want to perform is currently not available or you are not allowed to do this.", "Already Completed", JOptionPane.INFORMATION_MESSAGE);
    }
}
