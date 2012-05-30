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
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            session.write(request);
            while (!reader.ready());
            assertEquals(request.toString(), reader.readLine());
            
            session.write(request);
            while (!reader.ready());
            Request receivedRequest = new Request(reader.readLine());
            assertEquals(request.getRequestType(), receivedRequest.getRequestType());
            assertEquals(request.getData(), receivedRequest.getData());
            assertNotSame("\n", receivedRequest.getData().substring(receivedRequest.getData().length() - 1));
            
            socket.close();
            serverSocket.close();
            session.close();
        } catch (IOException e) {
        }
    }
}
