/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pakar.ui;

import java.awt.Dimension;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.InternationalFormatter;
import pakar.db.KoneksiDb;
import pakar.model.Gejala;
import pakar.model.Aturan;
import pakar.model.KelompokTitikAkupuntur;

/**
 *
 * @author linuxluv
 * 
 */
public class FormKonsultasi extends javax.swing.JFrame {
    private DefaultTableModel modelTblFakta;
    private DefaultTableModel modelTblGejala;
    DecimalFormat df = new DecimalFormat("#.##");

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
        
        initTxtSearch();
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

    private void initTblFakta() {
        modelTblFakta = new DefaultTableModel(
                new Object[][]{}, new String[]{"ID Gejala", "Gejala", "CF"}
        );

        tblFakta.setModel(modelTblFakta);
        tblFakta.getColumnModel().getColumn(0).setMaxWidth(75);
        tblFakta.getColumnModel().getColumn(2).setMaxWidth(75);

        tblFakta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initTblGejala() {
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
    
    /* menghitung jumlah record tabel user */
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
        btnCancel = new javax.swing.JButton();
        btnProses = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtSearchGejala = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtMB.setToolTipText("");
        txtMB.setPreferredSize(new java.awt.Dimension(4, 30));

        txtMD.setToolTipText("");
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
        jLabel1.setToolTipText("MD = measure of increased disbelief (seberapa tidak yakin bahwa gejala itu benar)");

        jLabel2.setText("MB");
        jLabel2.setToolTipText("MB = measure of increased belief (seberapa yakin bahwa gejala itu benar)");

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

        btnTambah.setText("Pilih gejala");
        btnTambah.setPreferredSize(new java.awt.Dimension(112, 30));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnCancel.setText("Batal pilih gejala");
        btnCancel.setPreferredSize(new java.awt.Dimension(147, 30));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnProses.setText("Proses");
        btnProses.setPreferredSize(new java.awt.Dimension(85, 30));
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.setPreferredSize(new java.awt.Dimension(78, 30));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel6.setText("cari gejala:");

        txtSearchGejala.setPreferredSize(new java.awt.Dimension(80, 30));
        txtSearchGejala.setSize(new java.awt.Dimension(80, 30));

        jMenu1.setText("File");

        menuExit.setText("Exit");
        menuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Data");

        jMenuItem1.setText("Gejala");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Kel. titik akupuntur");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txtSearchGejala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 48, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtMB, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(70, 70, 70)
                                        .addComponent(jLabel1)
                                        .addGap(93, 93, 93))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMD, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnProses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtSearchGejala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)))
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
            String sql = "SELECT ru.id_aturan AS id_aturan, p.id_kel_titik AS titik_konklusi, "
                    + "ru.cf_pakar AS cf_aturan FROM aturan ru LEFT JOIN kel_titik_akp p ON ru.id_kel_titik = p.id_kel_titik "
                    + "GROUP BY ru.id_aturan";
//                    "SELECT ru.id_rule AS id_rule, " + "p.id_titik AS penyakit_konklusi, "
//                    + "ru.cf_pakar AS cf_rule FROM rule ru "
//                    + "LEFT JOIN titik_akupuntur p ON ru.id_titik = p.id_titik " + "GROUP BY ru.id_rule;";
            ResultSet r = s.executeQuery(sql);
            ArrayList<Aturan> arrRule = new ArrayList<Aturan>();
            while (r.next()) {
                Aturan rule = new Aturan();
                rule.setIdAturan(r.getString("id_aturan"));
                rule.setArrGejala(getGejalaPremis(r.getString("id_aturan"), arrGejalaFakta));
                rule.setTitikAkupuntur(getTitikAkupunturById(r.getString("titik_konklusi")));
                rule.setCfValue(Double.parseDouble(r.getString("cf_aturan")));

                arrRule.add(rule);
            }

            // debug1
            showMatchedRules(arrRule);

            ArrayList<Aturan> arrRuleWithCFComposite = prosesHitungCF(arrRule);

            sortRuleByCFComposite(arrRuleWithCFComposite);

            r.close();
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // mengurutkan rule berdasarkan nilai CF komposit yang telah dihitung
    private void sortRuleByCFComposite(ArrayList<Aturan> arrRule) {
        Collections.sort(arrRule, new Comparator<Aturan>() {
            @Override
            public int compare(Aturan c1, Aturan c2) {
                return Double.compare(c2.getCfValue(), c1.getCfValue());
            }
        });

        for (int i = 0; i < arrRule.size(); i++) {
            // hanya menampilkan aturan yang nilai CF nya tidak 0
            if (arrRule.get(i).getCfValue() > 0) {
                taKesimpulan.append("ID Aturan : " + arrRule.get(i).getIdAturan() + " ~ ");
                taKesimpulan.append("ID Kelompok Titik akupuntur : " + arrRule.get(i).getTitikAkupuntur().getIdKelTitik() + " ~ ");
                taKesimpulan.append("CF : " + df.format(arrRule.get(i).getCfValue()) + "\n");
            }
        }
    }

    private ArrayList<Aturan> prosesHitungCF(ArrayList<Aturan> arrRule) {
        // simpan ke ArrayList<Rule>
        // data CF komposit hasil perhitungan included
        ArrayList<Aturan> arrCFHasil = new ArrayList<>();
        for (int i = 0; i < arrRule.size(); i++) {
            Aturan ruleCFKomposit = new Aturan();
            ruleCFKomposit.setIdAturan(arrRule.get(i).getIdAturan());
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
    private void showRules(ArrayList<Aturan> arrCFHasil) {
        System.out.println("--> DEBUG 2 : CF untuk masing-masing titik akupuntur yang Aturannya-nya tereksekusi");
        for (int i = 0; i < arrCFHasil.size(); i++) {
            System.out.print("ID Aturan : " + arrCFHasil.get(i).getIdAturan() + " ~ ");
            System.out.print("ID Kelompok Titik akupuntur : " + arrCFHasil.get(i).getTitikAkupuntur().getIdKelTitik() + " ~ ");
            System.out.println("CF : " + arrCFHasil.get(i).getCfValue());
        }
        System.out.println(">>>-----------------------<<<\n");
    }

    // menampilkan semua rule yang ada, mencocokan gejala pada rule dengan gejala yang diinputkan
    private void showMatchedRules(ArrayList<Aturan> arrRule) {
        System.out.println("---> daftar aturan : ");
        for (int i = 0; i < arrRule.size(); i++) {
            System.out.println("--> Aturan : " + arrRule.get(i).getIdAturan());
            System.out.println("--> Premis Aturan : ");
            if (arrRule.get(i).getArrGejala() != null) {
                for (int j = 0; j < arrRule.get(i).getArrGejala().size(); j++) {
                    System.out.println("ID Gejala : " + arrRule.get(i).getArrGejala().get(j).getIdGejala()
                            + " ~ CF : " + arrRule.get(i).getArrGejala().get(j).getCfValue());
                }
            }
            System.out.println("--> Kesimpulan Aturan : ");
            System.out.println("ID Kelompok Titik Akupuntur : " + arrRule.get(i).getTitikAkupuntur().getIdKelTitik() + " ~ "
                    + " CF Aturan : " + arrRule.get(i).getCfValue());
            System.out.println("-----------------------------------------------");
        }
    }

    public KelompokTitikAkupuntur getTitikAkupunturById(String id) {
        KelompokTitikAkupuntur titikAkupuntur = new KelompokTitikAkupuntur();
        try {
            Connection c = KoneksiDb.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM kel_titik_akp WHERE id_kel_titik ='" + id + "'";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                titikAkupuntur.setIdKelTitik(r.getString("id_kel_titik"));
                titikAkupuntur.setKetKelTitik(r.getString("ket_kel_titik"));
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
            String sql = "SELECT * FROM relasi_aturan_gejala WHERE id_aturan='" + id + "'";
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
        if (!taGejala.getText().toString().equals("") && (mb != md) && !isGejalaSelected(taGejala.getText().toString())) {
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
            
            String cfValue = "";
            cfValue = df.format(Double.parseDouble(String.valueOf(CF)));
            
            addTblFaktaRow(idGejala, tblGejala.getValueAt(tblGejala.getSelectedRow(), 1).toString(), cfValue);
            taGejala.setText("");
            txtMB.setText("0.0");
            txtMD.setText("0.0");
        } else {
            System.out.println("");
            if (taGejala.getText().toString().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "pilih dahulu satu buah gejala!");
            } else if (mb == md) {
                JOptionPane.showMessageDialog(rootPane, "nilai MB sama dengan MD, harap periksa kembali nilai yang anda masukkan");
            } else if (isGejalaSelected(taGejala.getText().toString())) {
                JOptionPane.showMessageDialog(rootPane, "gejala sudah dimasukkan!");
            }
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private boolean isGejalaSelected(String namaGejala){
        boolean ada = false;
        for (int i = 0; i < tblFakta.getRowCount(); i++) {
            if (namaGejala.equalsIgnoreCase(tblFakta.getValueAt(i, 1).toString())) {
                ada = true;
            }
        }
        return ada;
    }
    
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if (tblFakta.getSelectedRow() > -1) {
            modelTblFakta.removeRow(tblFakta.getSelectedRow());
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // mengulang proses perhitungan
        modelTblFakta.setRowCount(0);
        taKesimpulan.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        FormGejala formGejala = new FormGejala();
        formGejala.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        FormKelompokTitikAkupuntur formKelompokTitikAkupuntur = new FormKelompokTitikAkupuntur();
        formKelompokTitikAkupuntur.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
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
    private javax.swing.JTextField txtSearchGejala;
    // End of variables declaration//GEN-END:variables
}
