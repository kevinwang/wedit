/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.net;

/**
 *
 * @author Kevin Wang
 */
public class Request {
    public static final char TYPE_INSERT = 'i';
    public static final char TYPE_DELETE = 'd';
    public static final char TYPE_CHAT = 'c';
    public static final char TYPE_NICK = 'n';
    
    /**
     * ASCII unit separator.
     */
    private static final String DELIMITER = Character.toString((char)31);
    
    private char requestType;
    private int index;
    private String data;
    private Session origin;
    
    /**
     * Constructs a request. If a particular parameter is not relevant to the
     * type of request being constructed, its value is irrelevant.
     * @param requestType Type of request (insert, delete, chat, etc.).
     * @param index Index at which to perform insertion/deletion.
     * @param data If insert request, string to be inserted. If chat request,chat message.
     */
    public Request(char requestType, int index, String data) {
        this.requestType = requestType;
        this.index = index;
        this.data = data;
    }
    
    /**
     * Constructs a request from a formatted request string.
     * @param requestString Request string to construct request with.
     */
    public Request(String requestString) {
        String[] tokens = requestString.split(DELIMITER);
        requestType = tokens[0].charAt(0);
        if (requestType == TYPE_CHAT || requestType == TYPE_NICK) {
            data = tokens[1];
        }
        else {
            index = Integer.parseInt(tokens[1]);
            if (requestType == TYPE_INSERT) {
                data = tokens[2];
            }
        }
    }

    public Request(char requestType, int index, String data, Session origin) {
        this(requestType, index, data);
        this.origin = origin;
    }

    public Request(String requestString, Session origin) {
        this(requestString);
        this.origin = origin;
    }
    
    /**
     * Creates a formatted request string.
     * @return Formatted request string.
     */
    @Override
    public String toString() {
        String s = "";
        s += requestType + DELIMITER;
        switch (requestType) {
            case TYPE_INSERT:
                s += index + DELIMITER + data;
                break;
            case TYPE_DELETE:
                s += index;
                break;
            case TYPE_CHAT: case TYPE_NICK:
                s += data;
                break;
        }
        return s;
    }
    
    public char getRequestType() {
        return requestType;
    }
    
    public int getIndex() {
        return index;
    }
    
    public String getData() {
        return data;
    }

    public Session getOrigin() {
        return origin;
    }
}
