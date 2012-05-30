/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.net;

/**
 *
 * @author Kevin Wang
 */
public class BacktracedRequest extends Request {
    private Session origin;

    public BacktracedRequest(char requestType, int index, String data, Session origin) {
        super(requestType, index, data);
        this.origin = origin;
    }

    public BacktracedRequest(String requestString, Session origin) {
        super(requestString);
        this.origin = origin;
    }

    public Session getOrigin() {
        return origin;
    }
}
