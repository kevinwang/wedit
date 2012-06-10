/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.server;

import java.util.LinkedList;
import java.util.Queue;
import wedit.net.BacktracedRequest;
import wedit.net.Request;
import wedit.net.SessionManager;

/**
 *
 * @author Kevin Wang
 */
public class RequestHandler {
    private static RequestHandler instance;
    
    private Queue<BacktracedRequest> requests;
    private Thread handlerThread;
    private final Object lock = new Object();
    
    public static RequestHandler getInstance() {
        if (instance == null) {
            instance = new RequestHandler();
        }
        return instance;
    }
    
    private RequestHandler() {
        requests = new LinkedList<BacktracedRequest>();
    }
    
    public void start() {
        handlerThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1); // Magical statement that makes the loop work
                    } catch (InterruptedException e) {
                    }
                    if (!requests.isEmpty()) {
                        BacktracedRequest r;
                        synchronized (lock) {
                            r = requests.remove();
                        }
                        switch (r.getRequestType()) {
                            case Request.TYPE_INSERT:
                                try {
                                    if (r.getData().equals(Request.NEWLINE)) {
                                        WEditServer.document.insert(r.getIndex(), "\n");
                                    } else {
                                        WEditServer.document.insert(r.getIndex(), r.getData());
                                    }
                                } catch (StringIndexOutOfBoundsException e) {
                                    ServerFrame.getInstance().consoleWrite("Insert error: " + e.getMessage());
                                }
                                ServerFrame.getInstance().consoleWrite(WEditServer.document.toString());
                                break;
                            case Request.TYPE_DELETE:
                                try {
                                    WEditServer.document.deleteCharAt(r.getIndex());
                                } catch (StringIndexOutOfBoundsException e) {
                                    ServerFrame.getInstance().consoleWrite("Delete error: " + e.getMessage());
                                }
                                ServerFrame.getInstance().consoleWrite(WEditServer.document.toString());
                                break;
                            case Request.TYPE_CHAT:
                                SessionManager.getInstance().broadcastMessage(r);
                                break;
                            case Request.TYPE_NICK:
                                String oldNick = r.getOrigin().toString();
                                String newNick = r.getData();
                                r.getOrigin().setNick(newNick);
                                ServerFrame.getInstance().consoleWrite(oldNick + " is now known as " + newNick + ".");
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            
        });
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }
    
    public void addRequest(BacktracedRequest r) {
        synchronized (lock) {
            requests.add(r);
        }
    }
}
