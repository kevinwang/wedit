/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit;

import wedit.net.SessionManager;

/**
 *
 * @author Kevin Wang
 */
public class WEditServer {
    public static StringBuffer document = new StringBuffer();
    
    public WEditServer() {
        RequestHandler.getInstance().start();
        SessionManager.getInstance().start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ServerFrame().setVisible(true);
    }
}
