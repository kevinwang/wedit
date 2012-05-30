/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import static org.junit.Assert.*;
import org.junit.*;
import wedit.net.Request;
import wedit.net.Session;

/**
 *
 * @author Kevin Wang
 */
public class SessionTest {
    Request request = new Request('i', 694, "a");
    String newNick = "Kevin";
    Request nickRequest = new Request('n', 0, newNick);
    ServerSocket serverSocket;
    Socket socket;
    Session session;
    BufferedReader reader;
    
    public SessionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void writeTest() {
        try {
            serverSocket = new ServerSocket(23343);
            Thread serverThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                    }
                }
                
            });
            serverThread.start();
            session = new Session(new Socket("localhost", 23343));
            while (socket == null);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println(session.getNick());
            
            session.write(request);
            while (!reader.ready());
            assertEquals("Request string is sent intact", request.toString(), reader.readLine());
            
            socket.close();
            serverSocket.close();
            session.close();
        } catch (IOException e) {
        }
    }
    
    @Test
    public void readRequestTest() {
        try {
            serverSocket = new ServerSocket(23343);
            Thread serverThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                    }
                }
                
            });
            serverThread.start();
            session = new Session(new Socket("localhost", 23343));
            while (socket == null);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println(session.getNick());
            
            session.write(request);
            while (!reader.ready());
            
            Request receivedRequest = new Request(reader.readLine());
            assertEquals("Request type is read properly", request.getRequestType(), receivedRequest.getRequestType());
            assertEquals("Request data is read properly", request.getData(), receivedRequest.getData());
            
            socket.close();
            serverSocket.close();
            session.close();
        } catch (IOException e) {
        }
    }
    
    @Test
    public void noNewlineTest() {
        try {
            serverSocket = new ServerSocket(23343);
            Thread serverThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                    }
                }
                
            });
            serverThread.start();
            session = new Session(new Socket("localhost", 23343));
            while (socket == null);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println(session.getNick());
            
            session.write(request);
            while (!reader.ready());
            
            Request receivedRequest = new Request(reader.readLine());
            assertNotSame("Last character of data string is not a newline character", "\n", receivedRequest.getData().substring(receivedRequest.getData().length() - 1));
            
            socket.close();
            serverSocket.close();
            session.close();
        } catch (IOException e) {
        }
    }
    
    @Test
    public void nickTest() {
        try {
            serverSocket = new ServerSocket(23343);
            Thread serverThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        socket = serverSocket.accept();
                    } catch (IOException e) {
                    }
                }
                
            });
            serverThread.start();
            session = new Session(new Socket("localhost", 23343));
            while (socket == null);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            System.out.println(session.getNick());
            
            session.write(nickRequest);
            while (!reader.ready());
            
            Request receivedRequest = new Request(reader.readLine());
            session.setNick(receivedRequest.getData());
            assertEquals("Session nickname changes properly", newNick, session.getNick());
            
            System.out.println(session.getNick());
            
            socket.close();
            serverSocket.close();
            session.close();
        } catch (IOException e) {
        }
    }
}
