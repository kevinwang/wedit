/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Kevin Wang
 */
public class Session {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String nickname;
    
    private static int guestID = 1;

    public Session(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        nickname = "Guest" + guestID++;
    }
    
    public void write(Request request) {
        out.println(request);
    }
    
    public boolean ready() throws IOException {
        return in.ready();
    }
    
    public String readLine() throws IOException {
        return in.readLine();
    }
    
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
        } catch (NullPointerException n) {
        }
    }

    public void setNick(String nick) {
        this.nickname = nick;
    }
    
    @Override
    public String toString() {
        return nickname;
    }
}
