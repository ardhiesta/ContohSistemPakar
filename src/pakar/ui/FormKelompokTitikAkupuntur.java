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
public class FormKelompokTitikAkupuntur extends javax.swing.JFrame {
    private DefaultTableModel modelTblKelTitik;

    /**
     * Creates new form FormKelompokTitikAkupuntur
     */
    public FormKelompokTitikAkupuntur() {
        initComponents();
        
        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        
        btnEdit.setEnabled(false);
        btnHapus.setEnabled(false);
        
        initTblKelTitik();
        initTxtSearch();
        initTblKelTitikSelection();
    }
    
    private void initTblKelTitikSelection(){
        tblKelTitik.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblKelTitik.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tblKelTitik.getSelectedRowCount() > 0) {
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
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchData(txtSearch.getText().toString(), modelTblKelTitik);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchData(txtSearch.getText().toString(), modelTblKelTitik);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }
    
    private void initTblKelTitik(){
        modelTblKelTitik = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID Kelompok Titik", "Keterangan"
                }
        );

        tblKelTitik.setModel(modelTblKelTitik);
        tblKelTitik.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tblKelTitik.getColumnModel().getColumn(0).setMaxWidth(175);
        searchData("", modelTblKelTitik);
    }
    
    public void searchData(String keyword, DefaultTableModel modelTbl) {
        ResultSet r = null;
        PreparedStatement ps = null;
        try {
            Connection c = KoneksiDb.getKoneksi();
            String sql = "select * from kel_titik_akp where id_kel_titik like ? or ket_kel_titik like ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            r = ps.executeQuery();
            /* clear tabel, hapus semua baris sebelum menampilkan hanya record terpilih */
            modelTbl.setRowCount(0);
            while (r.next()) {
                Object[] o = new Object[5];
                o[0] = r.getString("id_kel_titik");
                o[1] = r.getString("ket_kel_titik");
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

    private void showDialogKelTitikAKupuntur(String title, String idKelTitik, String kelTitik,
            boolean isEdit, DefaultTableModel modelTblKelTitik){
        DialogKelompokTitikAkupuntur dialogKelompokTitikAkupuntur = 
                new DialogKelompokTitikAkupuntur(this, rootPaneCheckingEnabled, title, 
                        idKelTitik, kelTitik, isEdit, modelTblKelTitik);
        dialogKelompokTitikAkupuntur.setVisible(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKelTitik = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Tambah Kel. Titik Akupuntur");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit Kel. Titik Akupuntur");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus Kel. Titik Akupuntur");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        tblKelTitik.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblKelTitik);

        jLabel1.setText("Cari kel. titik :");

        txtSearch.setPreferredSize(new java.awt.Dimension(80, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnHapus))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(btnEdit)
                    .addComponent(btnHapus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FormKonsultasi formKonsultasi = new FormKonsultasi();
        formKonsultasi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        showDialogKelTitikAKupuntur("Tambah Kel. Titik Akupuntur", "", "", 
                false, modelTblKelTitik);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        showDialogKelTitikAKupuntur("Edit Kel. Titik Akupuntur", 
                tblKelTitik.getValueAt(tblKelTitik.getSelectedRow(), 0).toString(), 
                tblKelTitik.getValueAt(tblKelTitik.getSelectedRow(), 1).toString(), 
                true, modelTblKelTitik);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(rootPane, "Anda yakin untuk menghapus kel. titik \""
                +tblKelTitik.getValueAt(tblKelTitik.getSelectedRow(), 0)+" : "
                +tblKelTitik.getValueAt(tblKelTitik.getSelectedRow(), 1)+"\"?", "Hapus Kel. Titik Akupuntur", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (confirm == 0) {
            // hapus data
            hapusKelTitik();
        } 
    }//GEN-LAST:event_btnHapusActionPerformed

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
//            java.util.logging.Logger.getLogger(FormKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormKelompokTitikAkupuntur().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKelTitik;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    private void hapusKelTitik() {
        try {
            PreparedStatement ps = null;
            Connection c = KoneksiDb.getKoneksi();
            String sql = "delete from kel_titik_akp where id_kel_titik = ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, String.valueOf(tblKelTitik.getValueAt(tblKelTitik.getSelectedRow(), 0)));
            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(rootPane, "Berhasil menghapus data kel. titik akupuntur", 
                        "Hapus Kel. Titik Akupuntur", JOptionPane.INFORMATION_MESSAGE);
                searchData("", modelTblKelTitik);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Gagal menghapus data kel. titik akupuntur! "+ex.getMessage(), 
                        "Hapus Kel. Titik Akupuntur", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FormGejala.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
