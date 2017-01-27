/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakar.ui;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.InternationalFormatter;
import pakar.db.KoneksiDb;
import pakar.model.Gejala;
import pakar.model.Rule;
import pakar.model.TitikAkupuntur;

/**
 *
 * @author linuxluv
 */
public class FormKonsultasi extends javax.swing.JFrame {

    private DefaultTableModel modelTblFakta;
    private DefaultTableModel modelTblGejala;

    /**
     * Creates new form FormKonsultasi
     */
    public FormKonsultasi() {
        initComponents();

        this.setMinimumSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);

        initTblGejala();
        setTblGejalaSelectionAction();

        initTblFakta();

        setFormatDesimal(txtMB);
        setFormatDesimal(txtMD);

    }

    private void initTblFakta() {
        modelTblFakta = new DefaultTableModel(
                new Object[][]{}, new String[]{"ID Gejala", "Gejala", "CF"}
        );

        tblFakta.setModel(modelTblFakta);
    }

    private void initTblGejala() {
        modelTblGejala = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID Gejala", "Gejala"
                }
        );

        tblGejala.setModel(modelTblGejala);

        loadGejala();
    }

    private void setTblGejalaSelectionAction() {
        final ListSelectionModel tblGejalaSelectionModel = tblGejala.getSelectionModel();
        tblGejalaSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblGejalaSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!tblGejalaSelectionModel.isSelectionEmpty()) {
                    taGejala.setText("");
                    taGejala.append(tblGejala.getValueAt(tblGejala.getSelectedRow(), 1).toString());
                }
            }
        });
    }

    //menampilkan data gejala ke tabel (tabel kiri atas)
    public void loadGejala() {
        // menghapus seluruh data
        modelTblGejala.getDataVector().removeAllElements();
        // memberi tahu bahwa data telah kosong
        modelTblGejala.fireTableDataChanged();

        try {
            Connection c = KoneksiDb.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM gejala";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                // lakukan penelusuran baris
                Object[] o = new Object[5];
                o[0] = r.getString("id_gejala");
                o[1] = r.getString("nama_gejala");
                modelTblGejala.addRow(o);
            }
            r.close();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // mengatur format desimal MB dan MD
    private void setFormatDesimal(JFormattedTextField jFormattedTextField) {
        jFormattedTextField.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
            @Override
            public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
                NumberFormat format = DecimalFormat.getInstance();
                format.setMinimumFractionDigits(2);
                format.setMaximumFractionDigits(2);
                format.setRoundingMode(RoundingMode.HALF_UP);
                InternationalFormatter formatter = new InternationalFormatter(format);
                formatter.setAllowsInvalid(false);
                formatter.setMinimum(0.0);
                formatter.setMaximum(1.0);
                return formatter;
            }
        });

        jFormattedTextField.setText("0.0");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMB = new javax.swing.JFormattedTextField();
        txtMD = new javax.swing.JFormattedTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGejala = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taGejala = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblFakta = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        taKesimpulan = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnProses = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtMB.setPreferredSize(new java.awt.Dimension(4, 30));

        txtMD.setPreferredSize(new java.awt.Dimension(4, 30));

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

        jLabel1.setText("MD");

        jLabel2.setText("MB");

        jLabel3.setText("Gejala yang dipilih");

        taGejala.setEditable(false);
        taGejala.setColumns(20);
        taGejala.setRows(5);
        jScrollPane2.setViewportView(taGejala);

        tblFakta.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblFakta);

        jLabel4.setText("Kesimpulan");

        taKesimpulan.setEditable(false);
        taKesimpulan.setColumns(20);
        taKesimpulan.setRows(5);
        jScrollPane4.setViewportView(taKesimpulan);

        jLabel5.setText("Gejala-gejala yang diinputkan");

        btnTambah.setText("Tambahkan");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");

        btnProses.setText("Proses");
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 28, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnClear)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(txtMB, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(70, 70, 70)
                                            .addComponent(jLabel1)
                                            .addGap(93, 93, 93))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtMD, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(73, 73, 73)
                                        .addComponent(btnTambah))))
                            .addComponent(btnProses, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah)
                            .addComponent(btnClear))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnProses)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuExitActionPerformed
        // keluar dari program
        System.exit(0);
    }//GEN-LAST:event_menuExitActionPerformed

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        int jmlFakta = tblFakta.getRowCount();
        if (jmlFakta > 0) {
            ArrayList<Gejala> arrGejalaFakta = new ArrayList<Gejala>();
            for (int i = 0; i < jmlFakta; i++) {
                Gejala gejala = new Gejala();
                gejala.setIdGejala(tblFakta.getValueAt(i, 0).toString());
                gejala.setDeskripsi(tblFakta.getValueAt(i, 1).toString());
                gejala.setCfValue(Double.parseDouble(tblFakta.getValueAt(i, 2).toString()));
                arrGejalaFakta.add(gejala);
            }
            // get rule
            getRule(arrGejalaFakta);
        } else {
            JOptionPane.showMessageDialog(rootPane, "pilih gejala terleih dulu!");
        }
    }//GEN-LAST:event_btnProsesActionPerformed

    public void getRule(ArrayList<Gejala> arrGejalaFakta) {
        try {
            Connection c = KoneksiDb.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT ru.id_rule AS id_rule, " + "p.id_titik AS penyakit_konklusi, "
                    + "ru.cf_pakar AS cf_rule FROM rule ru "
                    + "LEFT JOIN titik_akupuntur p ON ru.id_titik = p.id_titik " + "GROUP BY ru.id_rule;";
            ResultSet r = s.executeQuery(sql);
            ArrayList<Rule> arrRule = new ArrayList<Rule>();
            while (r.next()) {
                Rule rule = new Rule();
                rule.setIdRule(r.getString("id_rule"));
                rule.setArrGejala(getGejalaPremis(r.getString("id_rule"), arrGejalaFakta));
                rule.setTitikAkupuntur(getTitikAkupunturById(r.getString("penyakit_konklusi")));
                rule.setCfValue(Double.parseDouble(r.getString("cf_rule")));

                arrRule.add(rule);
            }

            // debug1
            showMatchedRules(arrRule);

            ArrayList<Rule> arrRuleWithCFComposite = prosesHitungCF(arrRule);

            sortRuleByCFComposite(arrRuleWithCFComposite);

            r.close();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // mengurutkan rule berdasarkan nilai CF komposit yang telah dihitung
    private void sortRuleByCFComposite(ArrayList<Rule> arrRule) {
        Collections.sort(arrRule, new Comparator<Rule>() {
            @Override
            public int compare(Rule c1, Rule c2) {
                return Double.compare(c2.getCfValue(), c1.getCfValue());
            }
        });

        for (int i = 0; i < arrRule.size(); i++) {
            taKesimpulan.append("ID Rule : " + arrRule.get(i).getIdRule() + " ~ ");
            taKesimpulan.append("ID Titik akupuntur : " + arrRule.get(i).getTitikAkupuntur().getIdTitik() + " ~ ");
            taKesimpulan.append("CF : " + arrRule.get(i).getCfValue()+"\n");
        }
    }

    private ArrayList<Rule> prosesHitungCF(ArrayList<Rule> arrRule) {
        // simpan ke ArrayList<Rule>
        // data CF komposit hasil perhitungan included
        ArrayList<Rule> arrCFHasil = new ArrayList<>();
        for (int i = 0; i < arrRule.size(); i++) {
            Rule ruleCFKomposit = new Rule();
            ruleCFKomposit.setIdRule(arrRule.get(i).getIdRule());
            ArrayList<Gejala> arrG = arrRule.get(i).getArrGejala();

            double minCFGejala = 0;
            double minCFPenyakit = 0;
            if (arrG != null) {
                List<Double> lCFGejala = new ArrayList<Double>();
                for (int j = 0; j < arrG.size(); j++) {
                    lCFGejala.add(arrG.get(j).getCfValue());
                }
                if (lCFGejala.size() > 0) {
                    minCFGejala = Collections.min(lCFGejala);
                }
            }

            if (arrG.size() > 0) {
                ruleCFKomposit.setCfValue(minCFGejala * arrRule.get(i).getCfValue());
            }

            ruleCFKomposit.setTitikAkupuntur(arrRule.get(i).getTitikAkupuntur());
            arrCFHasil.add(ruleCFKomposit);
        }

        // debug 2
        // menampilkan CF komposit untuk masing-masing titik akupuntur yang Rule-nya tereksekusi
        showRules(arrCFHasil);

        return arrCFHasil;
    }

    // menampilkan CF untuk masing-masing titik akupuntur yang Rule-nya tereksekusi
    private void showRules(ArrayList<Rule> arrCFHasil) {
        System.out.println("--> DEBUG 2 : CF untuk masing-masing titik akupuntur yang Rule-nya tereksekusi");
        for (int i = 0; i < arrCFHasil.size(); i++) {
            System.out.print("ID Rule : " + arrCFHasil.get(i).getIdRule() + " ~ ");
            System.out.print("ID Titik akupuntur : " + arrCFHasil.get(i).getTitikAkupuntur().getIdTitik() + " ~ ");
            System.out.println("CF : " + arrCFHasil.get(i).getCfValue());
        }
        System.out.println(">>>-----------------------<<<\n");
    }

    // menampilkan semua rule yang ada, mencocokan gejala pada rule dengan gejala yang diinputkan
    private void showMatchedRules(ArrayList<Rule> arrRule) {
        System.out.println("---> daftar rule : ");
        for (int i = 0; i < arrRule.size(); i++) {
            System.out.println("--> Rule : " + arrRule.get(i).getIdRule());
            System.out.println("--> Premis Rule : ");
            if (arrRule.get(i).getArrGejala() != null) {
                for (int j = 0; j < arrRule.get(i).getArrGejala().size(); j++) {
                    System.out.println("ID Gejala : " + arrRule.get(i).getArrGejala().get(j).getIdGejala()
                            + " ~ CF : " + arrRule.get(i).getArrGejala().get(j).getCfValue());
                }
            }
            System.out.println("--> Kesimpulan Rule : ");
            System.out.println("ID Penyakit : " + arrRule.get(i).getTitikAkupuntur().getIdTitik() + " ~ "
                    + " CF Rule : " + arrRule.get(i).getCfValue());
            System.out.println("-----------------------------------------------");
        }
    }

    public TitikAkupuntur getTitikAkupunturById(String id) {
        TitikAkupuntur titikAkupuntur = new TitikAkupuntur();
        try {
            Connection c = KoneksiDb.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM titik_akupuntur WHERE id_titik ='" + id + "'";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                titikAkupuntur.setIdTitik(r.getString("id_titik"));
                titikAkupuntur.setNamaTitik(r.getString("nama_titik"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return titikAkupuntur;
    }

    // isi CF gejala dari fakta
    public Gejala getGejalaById(String id, ArrayList<Gejala> arrGejalaFakta) {
        Gejala gejala = new Gejala();
        try {
            Connection c = KoneksiDb.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM gejala WHERE id_gejala='" + id + "'";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                gejala.setIdGejala(r.getString("id_gejala"));
                gejala.setDeskripsi(r.getString("nama_gejala"));
                gejala.setCfValue(0);
                for (int i = 0; i < arrGejalaFakta.size(); i++) {
                    Gejala gejalaFakta = arrGejalaFakta.get(i);
                    if (gejalaFakta.getIdGejala().equals(r.getString("id_gejala"))) {
                        gejala.setCfValue(gejalaFakta.getCfValue());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return gejala;
    }

    private ArrayList<Gejala> getGejalaPremis(String id, ArrayList<Gejala> arrGejalaFakta) {
        ArrayList<Gejala> arrGejala = new ArrayList<Gejala>();
        try {
            Connection c = KoneksiDb.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM relasi_rule_gejala WHERE id_rule='" + id + "'";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                arrGejala.add(getGejalaById(r.getString("id_gejala"), arrGejalaFakta));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return arrGejala;
    }

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        double mb = Double.parseDouble(txtMB.getText());
        double md = Double.parseDouble(txtMD.getText());
        if (!taGejala.getText().toString().equals("") && mb + md == 1.0) {
            double selisihMbMd = mb - md;
            double minimal = mb;
            if (md < minimal) {
                minimal = md;
            }
            double selisihMin = 1 - minimal;
            double CF = selisihMbMd / selisihMin;

            String idGejala = "";
            if (!tblGejala.getSelectionModel().isSelectionEmpty()) {
                idGejala = tblGejala.getValueAt(tblGejala.getSelectedRow(), 0).toString();
            }
            String cfValue = String.valueOf(CF);
            addTblFaktaRow(idGejala, tblGejala.getValueAt(tblGejala.getSelectedRow(), 1).toString(), cfValue);
            taGejala.setText("");
            txtMB.setText("0.0");
            txtMD.setText("0.0");
        } else {
            if (mb + md != 1.0) {
                JOptionPane.showMessageDialog(rootPane, "harap masukkan nilai MB dan MD yang benar!");
            }
            if (taGejala.getText().toString().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "pilih dahulu satu buah gejala!");
            }
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    public void addTblFaktaRow(String idGejala, String deskripsi, String cfValue) {
        modelTblFakta.fireTableDataChanged();
        Object[] o = new Object[5];
        o[0] = idGejala;
        o[1] = deskripsi;
        o[2] = cfValue;
        modelTblFakta.addRow(o);
        tblGejala.clearSelection();
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
//            java.util.logging.Logger.getLogger(FormKonsultasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FormKonsultasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FormKonsultasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FormKonsultasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new FormKonsultasi().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem menuExit;
    private javax.swing.JTextArea taGejala;
    private javax.swing.JTextArea taKesimpulan;
    private javax.swing.JTable tblFakta;
    private javax.swing.JTable tblGejala;
    private javax.swing.JFormattedTextField txtMB;
    private javax.swing.JFormattedTextField txtMD;
    // End of variables declaration//GEN-END:variables
}
