/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import wedit.server.RequestHandler;
import wedit.server.ServerFrame;
import wedit.server.WEditServer;

/**
 *
 * @author Kevin Wang
 */
public class SessionManager {
    private static SessionManager instance;
    
    private ServerSocket server;
    private Set<Session> activeSessions;
    private ArrayList<InetAddress> banlist = new ArrayList<InetAddress>();
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    private SessionManager() {
        try {
            server = new ServerSocket(Constants.WEDIT_PORT);
        } catch (IOException e) {
            ServerFrame.getInstance().consoleWrite("Error constructing server socket: " + e.getMessage());
        }
        activeSessions = Collections.synchronizedSet(new HashSet<Session>());
    }
    
    private void acceptNextSession() {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                
                try {
                    Socket s = server.accept();
                    Session ses = new Session(s);
                    if(!banlist.contains(s.getInetAddress())){
                        activeSessions.add(ses);
                        sendDocument(ses);
                        SessionManager.getInstance().serverBroadcast(ses + " has opened the document.");
                    }
                } catch (IOException e) {
                }
                
                acceptNextSession();
            }
            
        });
        t.setPriority(Thread.MIN_PRIORITY);
        t.start();
    }
    
    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                acceptNextSession();
                while (true) {
                    try {
                        for (Session s : activeSessions) {
                            try {
                                if (s.ready()) {
                                    RequestHandler.getInstance().addRequest(new BacktracedRequest(s.readLine(), s));
                                }
                            } catch (IOException e) {
                                activeSessions.remove(s);
                                ServerFrame.getInstance().consoleWrite(s + " has left.");
                            }
                        }
                    } catch (ConcurrentModificationException e) {
                    }
                }
            }

        }).start();
    }
    
    public void broadcastMessage(BacktracedRequest r) {
        for (Session s : activeSessions) {
            s.write(new Request(Request.TYPE_CHAT, 0, "<" + r.getOrigin() + "> " + r.getData()));
        }
        ServerFrame.getInstance().consoleWrite("[CHAT] <" + r.getOrigin() + "> " + r.getData());
    }
    
    public void kick(String name){
        boolean kicked = false;
        for(Session s : activeSessions){
            if(s.toString().equals(name)){
                kicked = true;
                banlist.add(s.getAddr());
                s.write(new Request(Request.TYPE_KICK));
                activeSessions.remove(s); 
                s.close();
            }
        }
        if(kicked){
            SessionManager.getInstance().serverBroadcast(name + " has been kicked from the server.");
        } else {
            ServerFrame.getInstance().consoleWrite("User " + name + " does not exist.");
        }
    }
    
    public void serverBroadcast(String str) {
        for (Session s : activeSessions) {
            s.write(new Request(Request.TYPE_CHAT, 0, str));
        }
        ServerFrame.getInstance().consoleWrite(str);
    }
    
    public void broadcastRequest(BacktracedRequest r) {
        for (Session s : activeSessions) {
            if (!s.equals(r.getOrigin())) {
                s.write(r);
            }
        }
    }
    
    public void sendDocument(Session s) {
        String doc = WEditServer.document.toString();
        for (int i = 0; i < doc.length(); i++) {
            if (doc.charAt(i) == '\n') {
                s.write(new Request(Request.TYPE_INSERT, i, Request.NEWLINE));
            } else {
                s.write(new Request(Request.TYPE_INSERT, i, Character.toString(doc.charAt(i))));
            }
        }
    }
    
    public int getNumActiveSessions() {
        return activeSessions.size();
    }
}
