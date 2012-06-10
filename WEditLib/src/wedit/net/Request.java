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
    
    /**
     * ASCII record separator. Special character used here to denote newlines.
     */
    public static final String NEWLINE = Character.toString((char)30);
    
    private char requestType;
    private int index;
    private String data;
    
    /**
     * Constructs a request with an index and data.
     * @param requestType Type of request.
     * @param index Index at which to perform insertion/deletion.
     * @param data If insert request, string to be inserted. If chat request,chat message.
     */
    public Request(char requestType, int index, String data) {
        this.requestType = requestType;
        this.index = index;
        this.data = data;
    }
    
    /**
     * Constructs a request without an index.
     * @param data If insert request, string to be inserted. If chat request,chat message.
     */
    public Request(char requestType, String data) {
        this.requestType = requestType;
        this.data = data;
    }
    
    /**
     * Constructs a request without data.
     * @param index Index at which to perform insertion/deletion.
     */
    public Request(char requestType, int index) {
        this.requestType = requestType;
        this.index = index;
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
}
