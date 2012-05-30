/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.test;

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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void requestStringTest() {
        Request r = new Request(Request.TYPE_INSERT, 694, "a");
        Assert.assertEquals("Request string matches request", "i" + (char)31 + "694" + (char)31 + "a", r.toString());
        Assert.assertNotSame("Delimiter is a regular space", "i 694 a", r.toString());
    }
    
    @Test
    public void constructFromStringTest() {
        Request r = new Request("i" + (char)31 + "694" + (char)31 + "a");
        Assert.assertEquals(Request.TYPE_INSERT, r.getRequestType());
        Assert.assertEquals(694, r.getIndex(), 694);
        Assert.assertEquals("a", r.getData());
        
        Request s = new Request("c" + (char)31 + "Hello world!");
        Assert.assertEquals(Request.TYPE_CHAT, s.getRequestType());
        Assert.assertEquals("Hello world!", s.getData());
    }
}
