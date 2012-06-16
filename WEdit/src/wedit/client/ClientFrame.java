/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.client;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import wedit.net.Constants;
import wedit.net.Request;
/**
 *
 * @author owner
 */
public class ClientFrame extends javax.swing.JFrame {
    private static ClientFrame instance;
    private AudioPlayer p = AudioPlayer.player;
    private AudioStream as;
    public static ClientFrame getInstance() {
        if (instance == null) {
            instance = new ClientFrame();
        }
        return instance;
    }

    /**
     * Creates new form WEditUI
     */
    private ClientFrame() {
        initComponents();
        ((DefaultCaret)chatArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        documentArea.setTabSize(Constants.TAB_SIZE);
        chatArea.setTabSize(Constants.TAB_SIZE);
        try{
            as = new AudioStream(new FileInputStream("src/sounds/notif.wav"));
        }catch(Exception e){
        }
    }
    
    public void chatWrite(String s) {
        String text = chatArea.getText();
        chatArea.append((text.equals("") ? "" : "\n") + s);
    }
    
    public void insert(int index, String data) {
        int caretPos = documentArea.getCaretPosition();
        StringBuilder s = new StringBuilder(documentArea.getText());
        s.insert(index, data);
        documentArea.setText(s.toString());
        if (caretPos > index) {
            documentArea.setCaretPosition(caretPos + data.length());
        } else {
            documentArea.setCaretPosition(caretPos);
        }
    }
    
    public void delete(int index) {
        int caretPos = documentArea.getCaretPosition();
        StringBuilder s = new StringBuilder(documentArea.getText());
        s.deleteCharAt(index);
        documentArea.setText(s.toString());
        if (caretPos > documentArea.getText().length()) {
            documentArea.setCaretPosition(documentArea.getText().length());
        } else {
            documentArea.setCaretPosition(caretPos);
        }
    }
    
    public void notif(){
        if(!(chatField.isFocusOwner() && documentArea.isFocusOwner())){
            p.start(as);
        }
    }
    
    public void kickMsg(){
        JOptionPane.showMessageDialog(ClientFrame.getInstance(), "You were kicked and banned from the server.", "Kicked", JOptionPane.PLAIN_MESSAGE);
    }
    
    public void clear() {
        documentArea.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        documentArea = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        chatField = new javax.swing.JTextField();

        jScrollPane3.setViewportView(jEditorPane2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WEdit");

        documentArea.setColumns(20);
        documentArea.setLineWrap(true);
        documentArea.setRows(5);
        documentArea.setWrapStyleWord(true);
        documentArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                documentAreaKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(documentArea);

        chatArea.setColumns(10);
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setRows(5);
        chatArea.setWrapStyleWord(true);
        jScrollPane5.setViewportView(chatArea);

        chatField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                    .addComponent(chatField))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chatFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatFieldActionPerformed
        if (!chatField.getText().isEmpty()) {
            if (chatField.getText().charAt(0) == '/') {
                String[] spl = chatField.getText().substring(1).split(" ");
                if (spl[0].equals("nick")) {
                    if (spl.length > 1) {
                        WEdit.getInstance().makeRequest(new Request(Request.TYPE_NICK, spl[1]));
                    } else {
                        chatWrite("No nick specified.");
                    }
                } else if (spl[0].equals("sync")) {
                    WEdit.getInstance().makeRequest(new Request(Request.TYPE_SYNC));
                } else if (spl[0].equals("help")) {
                    chatWrite(
                            "Commands:\n"
                            + "nick <new name>\tChange your nickname\n"
                            + "sync\t\tSynchronize local document\n"
                            + "help\t\tDisplay this list");
                } else {
                    chatWrite("Command " + spl[0] + " not recognized.\n"
                            + "Type '/help' for a list of available commands.");
                }
            } else {
                WEdit.getInstance().chat(chatField.getText());
            }
            chatField.setText("");
        }
    }//GEN-LAST:event_chatFieldActionPerformed

    private void documentAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_documentAreaKeyTyped
        if (evt.getKeyChar() == KeyEvent.VK_BACK_SPACE || evt.getKeyChar() == KeyEvent.VK_DELETE) {
            WEdit.getInstance().makeRequest(new Request(Request.TYPE_DELETE, documentArea.getCaretPosition()));
        } else if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            WEdit.getInstance().makeRequest(new Request(Request.TYPE_INSERT, documentArea.getCaretPosition() - 1, Request.NEWLINE));
        } else if (evt.getKeyChar() == KeyEvent.VK_TAB) {
            WEdit.getInstance().makeRequest(new Request(Request.TYPE_INSERT, documentArea.getCaretPosition() - 1, Character.toString(evt.getKeyChar())));
        } else {
            WEdit.getInstance().makeRequest(new Request(Request.TYPE_INSERT, documentArea.getCaretPosition(), Character.toString(evt.getKeyChar())));
        }
    }//GEN-LAST:event_documentAreaKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ClientFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JTextField chatField;
    private javax.swing.JTextArea documentArea;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
