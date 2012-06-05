/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit;

import java.util.LinkedList;
import java.util.Queue;
import wedit.net.BacktracedRequest;
import wedit.net.Request;

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
                    if (!requests.isEmpty()) {
                        BacktracedRequest r;
                        synchronized (lock) {
                            r = requests.remove();
                        }
                        switch (r.getRequestType()) {
                            case Request.TYPE_INSERT:
                                WEditServer.document.insert(r.getIndex(), r.getData());
                                break;
                            case Request.TYPE_DELETE:
                                WEditServer.document.deleteCharAt(r.getIndex());
                                break;
                            case Request.TYPE_CHAT:
                                break;
                            case Request.TYPE_NICK:
                                String newNick = r.getData();
                                r.getOrigin().setNick(newNick);
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
