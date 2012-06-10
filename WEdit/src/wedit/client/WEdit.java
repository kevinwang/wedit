/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.client;

import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import wedit.net.Constants;
import wedit.net.Request;
import wedit.net.Session;

/**
 *
 * @author Kevin Wang
 */
public class WEdit {
    private static WEdit instance;
    
    private Session session;
    
    public static WEdit getInstance(String address, String nick) {
        if (instance == null) {
            instance = new WEdit(address, nick);
        }
        return instance;
    }
    
    public static WEdit getInstance() {
        return instance;
    }
    
    private WEdit(String address, String nick) {
        try {
            session = new Session(new Socket(address, Constants.WEDIT_PORT));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server at "
                    + address + ".\nError: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (!nick.isEmpty()) {
            session.write(new Request(Request.TYPE_NICK, nick));
        }
        ClientFrame.getInstance().setVisible(true);
        RequestHandler.getInstance().start();
    }
    
    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        if (session.ready()) {
                            RequestHandler.getInstance().addRequest(new Request(session.readLine()));
                        }
                    } catch (IOException e) {
                    }
                }
            }
            
        }).start();
    }
    
    public void chat(String s) {
        session.write(new Request(Request.TYPE_CHAT, s));
    }
    
    public void makeRequest(Request r) {
        session.write(r);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ConnectDialog().setVisible(true);
    }
}
