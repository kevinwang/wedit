/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.net;

import java.net.Socket;

/**
 *
 * @author Kevin Wang
 */
public class Session {
    private Socket socket;
    private String nick;

    public Session(Socket socket) {
        this.socket = socket;
    }

    public void setNick() {
    }

    public String getNick() {
        return nick;
    }
}
