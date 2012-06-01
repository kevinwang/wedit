/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import wedit.RequestHandler;

/**
 *
 * @author Kevin Wang
 */
public class SessionManager {
    private static SessionManager instance;
    
    private ServerSocket server;
    private ArrayList<Session> activeSessions;
    private final Object lock = new Object();
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    private SessionManager() {
        try {
            server = new ServerSocket(23343);
        } catch (IOException e) {
            // TODO: Report errors to console output
        }
        activeSessions = new ArrayList<Session>();
    }
    
    private void acceptNextSession() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        activeSessions.add(new Session(server.accept()));
                    }
                } catch (IOException e) {
                }
                acceptNextSession();
            }
            
        }).start();
    }
    
    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                acceptNextSession();
                while (true) {
                    for (Session s : activeSessions) {
                        try {
                            if (s.ready()) {
                                RequestHandler.getInstance().addRequest(new BacktracedRequest(s.readLine(), s));
                            }
                        } catch (IOException e) {
                            synchronized (lock) {
                                activeSessions.remove(s);
                            }
                        }
                    }
                }
            }
            
        }).start();
    }
    
    public Object getLock() {
        return lock;
    }
}
