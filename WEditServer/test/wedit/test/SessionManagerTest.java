/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.test;

import java.io.IOException;
import java.net.Socket;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import wedit.ServerFrame;
import wedit.net.Request;
import wedit.net.Session;
import wedit.net.SessionManager;

/**
 *
 * @author Kevin Wang
 */
public class SessionManagerTest {
    
    public SessionManagerTest() {
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
    
    @Test
    public void sessionTest() {
        ServerFrame.getInstance().setVisible(true);
        try {
            Session s = new Session(new Socket("localhost", 23343));
            delay();
            s.write(new Request(Request.TYPE_NICK, 0, "Kevin"));
            delay();
            assertEquals(1, SessionManager.getInstance().getNumActiveSessions());
            delay();
            Session t = new Session(new Socket("127.0.0.1", 23343));
            delay();
            t.write(new Request(Request.TYPE_NICK, 0, "Shan"));
            delay();
            s.write((new Request(Request.TYPE_CHAT, 0, "hello")));
            delay();
            t.write((new Request(Request.TYPE_CHAT, 0, "sup")));
            assertEquals(2, SessionManager.getInstance().getNumActiveSessions());
            delay();
            // Uncomment to see console output
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//            }
        } catch (IOException e) {
        }
    }
    
    public void delay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
    }
}
