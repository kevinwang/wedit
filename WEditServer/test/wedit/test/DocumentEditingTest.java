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
import wedit.WEditServer;
import wedit.net.Request;
import wedit.net.Session;
import wedit.net.SessionManager;

/**
 *
 * @author Kevin Wang
 */
public class DocumentEditingTest {
    
    public DocumentEditingTest() {
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
        ServerFrame.getInstance();
        try {
            Session s = new Session(new Socket("localhost", 23343));
            delay();
            s.write(new Request(Request.TYPE_INSERT, 0, "h"));
            s.write(new Request(Request.TYPE_INSERT, 1, "e"));
            s.write(new Request(Request.TYPE_INSERT, 2, "l"));
            s.write(new Request(Request.TYPE_INSERT, 3, "l"));
            s.write(new Request(Request.TYPE_INSERT, 4, "o"));
            delay();
            assertEquals("hello", WEditServer.document.toString());
            s.write(new Request(Request.TYPE_DELETE, 4));
            delay();
            assertEquals("hell", WEditServer.document.toString());
            s.write(new Request(Request.TYPE_INSERT, 4, "o"));
            s.write(new Request(Request.TYPE_DELETE, 3));
            delay();
            assertEquals("helo", WEditServer.document.toString());
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
