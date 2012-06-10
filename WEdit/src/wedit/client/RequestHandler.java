/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.client;

import java.util.LinkedList;
import java.util.Queue;
import wedit.net.Request;

/**
 *
 * @author Kevin Wang
 */
public class RequestHandler {
    private static RequestHandler instance;
    
    private Queue<Request> requests;
    private Thread handlerThread;
    private final Object lock = new Object();
    
    public static RequestHandler getInstance() {
        if (instance == null) {
            instance = new RequestHandler();
        }
        return instance;
    }
    
    private RequestHandler() {
        requests = new LinkedList<Request>();
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
                        Request r;
                        synchronized (lock) {
                            r = requests.remove();
                        }
                        switch (r.getRequestType()) {
                            case Request.TYPE_INSERT:
                                break;
                            case Request.TYPE_DELETE:
                                break;
                            case Request.TYPE_CHAT:
                                ClientFrame.getInstance().chatWrite(r.getData());
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
    
    public void addRequest(Request r) {
        synchronized (lock) {
            requests.add(r);
        }
    }
}