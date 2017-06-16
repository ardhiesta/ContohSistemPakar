/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakar.ui;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import pakar.db.KoneksiDb;

/**
 *
 * @author wonderlabs
 */
public class FormGejala extends javax.swing.JFrame {
    private DefaultTableModel modelTblGejala;

    /**
     * Creates new form FormGejala
     */
    public FormGejala() {
        initComponents();
        
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        
        btnEdit.setEnabled(false);
        btnHapus.setEnabled(false);
        
        initTblGejala();
        initTxtSearch();
        initTblGejalaSelection();
    }
    
    private void initTblGejalaSelection(){
        tblGejala.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblGejala.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tblGejala.getSelectedRowCount() > 0) {
                    btnEdit.setEnabled(true);
                    btnHapus.setEnabled(true);
                } else {
                    btnEdit.setEnabled(false);
                    btnHapus.setEnabled(false);
                }
            }
        });
    }
    
    private void initTxtSearch(){
        txtSearchGejala.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDataGejala(txtSearchGejala.getText().toString(), modelTblGejala);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDataGejala(txtSearchGejala.getText().toString(), modelTblGejala);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }
    
    private void initTblGejala(){
        modelTblGejala = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID Gejala", "Gejala"
                }
        );

        tblGejala.setModel(modelTblGejala);
        tblGejala.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tblGejala.getColumnModel().getColumn(0).setMaxWidth(75);
        searchDataGejala("", modelTblGejala);
        
//        modelTblGejala.addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//                if (tblGejala.getSelectedRowCount() > 0) {
//                    btnEdit.setEnabled(true);
//                } else {
//                    btnEdit.setEnabled(false);
//                }
//            }
//        });
//        
//        tblGejala.setSelectionMode(
    }
    
    public void searchDataGejala(String keyword, DefaultTableModel modelTbl) {
        ResultSet r = null;
        PreparedStatement ps = null;
        try {
            Connection c = KoneksiDb.getKoneksi();
            String sql = "select * from gejala where id_gejala like ? or nama_gejala like ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            r = ps.executeQuery();
            /* clear tabel, hapus semua baris sebelum menampilkan hanya record terpilih */
            modelTbl.setRowCount(0);
            while (r.next()) {
                Object[] o = new Object[5];
                o[0] = r.getString("id_gejala");
                o[1] = r.getString("nama_gejala");
                modelTbl.addRow(o);
            }
            r.close();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormKonsultasi.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
                r.close();
            } catch (SQLException ex) {
                Logger.getLogger(FormKonsultasi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        txtSearchGejala = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGejala = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Gejala");

        btnTambah.setText("Tambah Gejala");
        btnTambah.setSize(new java.awt.Dimension(97, 30));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit Gejala");
        btnEdit.setPreferredSize(new java.awt.Dimension(110, 30));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus Gejala");
        btnHapus.setPreferredSize(new java.awt.Dimension(126, 30));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        txtSearchGejala.setMinimumSize(new java.awt.Dimension(10, 30));
        txtSearchGejala.setPreferredSize(new java.awt.Dimension(10, 30));
        txtSearchGejala.setSize(new java.awt.Dimension(80, 30));

        jLabel1.setText("Cari Gejala :");

        tblGejala.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblGejala);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 4, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSearchGejala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearchGejala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        showDialogGejala("Tambah Gejala", "", "", false, modelTblGejala);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        showDialogGejala("Edit Gejala", tblGejala.getValueAt(tblGejala.getSelectedRow(), 0).toString(),
                tblGejala.getValueAt(tblGejala.getSelectedRow(), 1).toString(), true, modelTblGejala);
    }//GEN-LAST:event_btnEditActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FormKonsultasi formKonsultasi = new FormKonsultasi();
        formKonsultasi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(rootPane, "Anda yakin untuk menghapus gejala \""
                +tblGejala.getValueAt(tblGejala.getSelectedRow(), 0)+" : "
                +tblGejala.getValueAt(tblGejala.getSelectedRow(), 1)+"\"?", "Hapus Gejala", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == 0) {
            // hapus data
            hapusGejala();
        } 
    }//GEN-LAST:event_btnHapusActionPerformed

    private void hapusGejala(){
        try {
            PreparedStatement ps = null;
            Connection c = KoneksiDb.getKoneksi();
            String sql = "delete from gejala where id_gejala = ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, String.valueOf(tblGejala.getValueAt(tblGejala.getSelectedRow(), 0)));
            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(rootPane, "Berhasil menghapus data gejala", 
                        "Hapus Gejala", JOptionPane.INFORMATION_MESSAGE);
                searchDataGejala("", modelTblGejala);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Gagal menghapus data gejala! "+ex.getMessage(), 
                        "Hapus Gejala", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FormGejala.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void showDialogGejala(String title, String idGejala, String gejala, boolean isEdit, DefaultTableModel modelTblGejala){
        DialogGejala dialogGejala = new DialogGejala(this, rootPaneCheckingEnabled, title, idGejala, gejala, isEdit, modelTblGejala);
        dialogGejala.setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FormGejala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormGejala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormGejala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormGejala.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormGejala().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblGejala;
    private javax.swing.JTextField txtSearchGejala;
    // End of variables declaration//GEN-END:variables
}
