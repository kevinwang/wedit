/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit;

/**
 *
 * @author Kevin Wang
 */
public class WEditServer {
    public static StringBuffer document = new StringBuffer();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ServerFrame().setVisible(true);
    }
}
