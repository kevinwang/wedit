/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.server;

import javax.swing.text.DefaultCaret;
import wedit.net.Constants;
import wedit.net.SessionManager;

/**
 *
 * @author Kevin Wang
 */
public class ServerFrame extends javax.swing.JFrame {
    private static ServerFrame instance;
    
    public static ServerFrame getInstance() {
        if (instance == null) {
            instance = new ServerFrame();
        }
        return instance;
    }

    /**
     * Creates new form ServerFrame
     */
    private ServerFrame() {
        WEditServer server = new WEditServer();
        initComponents();
        inputField.requestFocusInWindow();
        ((DefaultCaret)consoleArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consoleArea.setTabSize(Constants.TAB_SIZE);
    }
    
    public void consoleWrite(String s) {
        String text = consoleArea.getText();
        consoleArea.append((text.equals("") ? "" : "\n") + s);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        inputField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        consoleArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WEdit Server");

        inputField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputFieldActionPerformed(evt);
            }
        });

        consoleArea.setColumns(20);
        consoleArea.setEditable(false);
        consoleArea.setLineWrap(true);
        consoleArea.setRows(5);
        consoleArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(consoleArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                    .addComponent(inputField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputFieldActionPerformed
        if (!inputField.getText().isEmpty()) {
            ServerFrame.getInstance().consoleWrite("$ " + inputField.getText());
            String[] spl = inputField.getText().split(" ");
            if (spl[0].equals("say")) {
                if (spl.length > 1) {
                    SessionManager.getInstance().serverBroadcast("[SERVER] " + inputField.getText().substring("say".length() + 1));
                } else {
                    ServerFrame.getInstance().consoleWrite("What do you want to say?");
                }
            } else if (spl[0].equals("ban")) {
                if (spl.length > 1) {
                    SessionManager.getInstance().ban(spl[1]);
                }else{
                    ServerFrame.getInstance().consoleWrite("Who do you want to ban?");
                }
            } else if (spl[0].equals("display")) {
                ServerFrame.getInstance().consoleWrite(WEditServer.document.toString());
            } else if (spl[0].equals("info")) {
                ServerFrame.getInstance().consoleWrite(
                        "Document info:\n"
                        + "# characters:\t" + WEditServer.document.length() + "\n"
                        + "# words:\t" + (WEditServer.document.length() == 0 ? 0 : WEditServer.document.toString().split("[ \n\t]").length));
            } else if (spl[0].equals("help")) {
                ServerFrame.getInstance().consoleWrite(
                        "Commands:\n"
                        + "display\t\t\tDisplay the document\n"
                        + "info\t\t\tShow character and word counts\n"
                        + "say <message>\tBroadcast chat message\n"
                        + "help\t\t\tDisplay this list\n"
                        + "ban <nick>\t\tKick and ban a client");
            } else {
                ServerFrame.getInstance().consoleWrite("Command " + spl[0] + " not recognized.\n"
                        + "Type 'help' for a list of available commands.");
            }
            inputField.setText("");
        }
    }//GEN-LAST:event_inputFieldActionPerformed

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
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea consoleArea;
    private javax.swing.JTextField inputField;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
