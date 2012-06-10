/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import wedit.net.Ports;
import wedit.net.Request;
import wedit.net.Session;

/**
 *
 * @author Kevin Wang
 */
public class WEdit {
    Session session;
    
    public WEdit(String address, String nick) {
        try {
            session = new Session(new Socket(address, Ports.WEDIT_PORT));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server at "
                    + address + ".\nError: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (!nick.isEmpty()) {
            session.write(new Request(Request.TYPE_NICK, nick));
        }
        ClientFrame.getInstance().setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ConnectDialog().setVisible(true);
    }
}
