/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit;

import java.io.IOException;
import java.net.Socket;
import wedit.net.Session;

/**
 *
 * @author Kevin Wang
 */
public class WEdit {
    Session session;
    
    public WEdit(String address) {
        try {
            session = new Session(new Socket(address, 23343));
        } catch (IOException e) {
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
