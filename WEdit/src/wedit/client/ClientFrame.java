/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wedit.client;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
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
    private boolean soundToggle = true;

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
        ((DefaultCaret) chatArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        documentArea.setTabSize(Constants.TAB_SIZE);
    }

    public String toggleSound() {
        soundToggle = !soundToggle;
        return soundToggle ? "on" : "off";
    }

    public void chatWrite(String s) {
        String text = chatArea.getText();
        chatArea.append((text.equals("") ? "" : "\n") + s);
        if (soundToggle) {
            AudioStream as = null;
            try {
                as = new AudioStream(new FileInputStream("src/sounds/notif.wav"));
                AudioPlayer.player.start(as);
            } catch (Exception e) {
            }
        }
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

    public void kickMsg() {
        documentArea.setEditable(false);
        JOptionPane.showMessageDialog(ClientFrame.getInstance(), "You have been kicked from the server.", "Kicked", JOptionPane.WARNING_MESSAGE);
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();

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

        jMenu1.setText("File");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Save As...");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Print...");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Tools");

        jMenuItem9.setText("Sync with server");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Sound notification");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Help");

        jMenuItem10.setText("About WEdit");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

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
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
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
                } else if (spl[0].equals("sound")) {
                    chatWrite("sound is " + toggleSound());
                    jCheckBoxMenuItem1.setState(soundToggle);
                } else if (spl[0].equals("help")) {
                    chatWrite(
                            "Commands:\n"
                            + "nick <new name>\tChange your nickname\n"
                            + "sync\t\tSynchronize local document\n"
                            + "sound\t\tToggle sound on/off\n"
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

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.showSaveDialog(getInstance());
        if (fc.getSelectedFile() != null) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(fc.getSelectedFile()));
                out.write(documentArea.getText());
                out.close();
            } catch (IOException e) {
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        try {
            documentArea.print();
        } catch (Exception pe) {
            JOptionPane.showMessageDialog(getInstance(), "Printing failed", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        WEdit.getInstance().makeRequest(new Request(Request.TYPE_SYNC));
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        soundToggle = jCheckBoxMenuItem1.getState();
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        JOptionPane.showMessageDialog(getInstance(), "WEdit is a collaborative real time text editor with integrated chat functionality.\n"
                + "Copyright \u00a9 2012  Kevin Wang and Shan Shi\n"
                + "\n"
                + "This program is free software: you can redistribute it and/or modify\n"
                + "it under the terms of the GNU General Public License as published by\n"
                + "the Free Software Foundation, either version 3 of the License, or\n"
                + "(at your option) any later version.\n"
                + "\n"
                + "This program is distributed in the hope that it will be useful,\n"
                + "but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
                + "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
                + "GNU General Public License for more details.\n"
                + "\n"
                + "You should have received a copy of the GNU General Public License\n"
                + "along with this program.  If not, see <http://www.gnu.org/licenses/>.", "About", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

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
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
