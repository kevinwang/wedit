/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.test;

import java.io.IOException;
import java.net.Socket;
import org.junit.*;
import static org.junit.Assert.*;
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
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            s.write(new Request(Request.TYPE_NICK, 0, "Kevin"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            assertEquals(1, SessionManager.getInstance().getNumActiveSessions());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            assertEquals(1, SessionManager.getInstance().getNumActiveSessions());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            Session t = new Session(new Socket("127.0.0.1", 23343));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            s.write(new Request(Request.TYPE_NICK, 0, "Shan"));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
            assertEquals(2, SessionManager.getInstance().getNumActiveSessions());
        } catch (IOException e) {
        }
    }
}
