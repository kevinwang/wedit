/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.test;

import static org.junit.Assert.*;
import org.junit.*;
import wedit.net.Request;

/**
 *
 * @author Kevin Wang
 */
public class RequestTest {
    
    public RequestTest() {
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
    public void requestStringTest() {
        Request r = new Request(Request.TYPE_INSERT, 694, "a");
        assertEquals("Request string matches request", "i" + (char)31 + "694" + (char)31 + "a", r.toString());
        assertNotSame("Delimiter is a regular space", "i 694 a", r.toString());
    }
    
    @Test
    public void constructFromStringTest() {
        Request r = new Request("i" + (char)31 + "694" + (char)31 + "a");
        assertEquals(Request.TYPE_INSERT, r.getRequestType());
        assertEquals(694, r.getIndex(), 694);
        assertEquals("a", r.getData());
        
        Request s = new Request("c" + (char)31 + "Hello world!");
        assertEquals(Request.TYPE_CHAT, s.getRequestType());
        assertEquals("Hello world!", s.getData());
    }
}
