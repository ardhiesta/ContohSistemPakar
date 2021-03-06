/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakar.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pakar.db.KoneksiDb;

/**
 *
 * @author linux
 */
public class DialogKelompokTitikAkupuntur extends javax.swing.JDialog {
    boolean isEdit = false;
    DefaultTableModel modelTblKelTitik;
    String idKelTitik = "";

    /**
     * Creates new form DialogKelompokTitikAkupuntur
     */
    public DialogKelompokTitikAkupuntur(java.awt.Frame parent, boolean modal, 
            String title, String idKelTitik, String kelTitik, 
            boolean isEdit, DefaultTableModel modelTblKelTitik) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        labelKelTitik.setText(title);
        txtID.setText(idKelTitik);
        txtKeterangan.setText(kelTitik);
        this.isEdit = isEdit;
        this.modelTblKelTitik = modelTblKelTitik;
        if (isEdit) {
            this.idKelTitik = txtID.getText();
            System.out.println("");
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

        labelKelTitik = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        txtKeterangan = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelKelTitik.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        labelKelTitik.setText("[edit/insert] Kelompok Titik Akupuntur");

        jLabel2.setText("ID");

        jLabel3.setText("Keterangan");

        txtID.setPreferredSize(new java.awt.Dimension(4, 30));

        txtKeterangan.setPreferredSize(new java.awt.Dimension(70, 30));

        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Batal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtKeterangan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelKelTitik)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)))
                        .addGap(0, 100, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelKelTitik)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (isEdit) {
            editKelTitik();
        } else {
            insertKelTitik();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void insertKelTitik(){
        try {
            PreparedStatement ps = null;
            Connection c = KoneksiDb.getKoneksi();
            String sql = "insert into kel_titik_akp values (?, ?)";
            ps = c.prepareStatement(sql);
            ps.setString(1, txtID.getText());
            ps.setString(2, txtKeterangan.getText());
            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(rootPane, "Berhasil menambahkan gejala kel. titik akupuntur", 
                        "Insert Kel. Titik AKupuntur", JOptionPane.INFORMATION_MESSAGE);
                searchData("", modelTblKelTitik);
                this.dispose();
            } else {
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Gagal menambahkan kel. titik akupuntur! "+ex.getMessage(), 
                        "Insert Kel. Titik AKupuntur", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DialogGejala.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editKelTitik(){
        try {
            PreparedStatement ps = null;
            Connection c = KoneksiDb.getKoneksi();
            String sql = "update kel_titik_akp set id_kel_titik = ?, ket_kel_titik = ? where id_kel_titik = ?";
            ps = c.prepareStatement(sql);
            ps.setString(1, txtID.getText());
            ps.setString(2, txtKeterangan.getText());
            ps.setString(3, idKelTitik);
            String testxxx = ps.toString();
            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(rootPane, "Berhasil mengedit data kel. titik akupuntur", 
                        "Edit Kelompok Titik AKupuntur", JOptionPane.INFORMATION_MESSAGE);
                searchData("", modelTblKelTitik);
                this.dispose();
            } else {
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Gagal mengedit data kel. titik akupuntur! "+ex.getMessage(), 
                        "Edit Kelompok Titik AKupuntur", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DialogGejala.class.getName()).log(Level.SEVERE, null, ex);
        }
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
//            java.util.logging.Logger.getLogger(DialogKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(DialogKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(DialogKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(DialogKelompokTitikAkupuntur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                DialogKelompokTitikAkupuntur dialog = new DialogKelompokTitikAkupuntur(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelKelTitik;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtKeterangan;
    // End of variables declaration//GEN-END:variables
}
