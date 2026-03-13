/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package m.b;

import classe.BoissonItem;
import classe.agent;
import classe.caisse;
import classe.commande;
import classe.connexionbd;
import classe.creefrigo;
import classe.frigo;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.swing.JCheckBox;

import java.sql.SQLException;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
//import static m.b.formlistcommande.tablcommande;
/**
 *
 * @author MHD
 */
public class formecommande extends javax.swing.JPanel {

    /**
     * Creates new form formecommande
     */


    public formecommande() {
        initComponents();

    }
    


    
    private void chargerFrigos() {
    try {
        cbofrigo.removeAllItems();
        ResultSet rs = commande.getFrigos();
        while (rs.next()) {
            cbofrigo.addItem(rs.getString("nom_frigo"));
            cbofrigo.putClientProperty(rs.getString("nom_frigo"), rs.getInt("id_frigo"));
        }
        rs.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors du chargement des frigos.");
    }
}
         
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnnouveau = new javax.swing.JButton();
        btnajouter = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        cmbboisson = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmbserveur = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbofrigo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txttotal = new javax.swing.JTextField();
        chpayer = new javax.swing.JCheckBox();
        btnimprimer = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabcommande = new javax.swing.JTable();
        btnvalider = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txtAvoir = new javax.swing.JTextField();
        btnlistcommande = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setEnabled(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel4.setText("                     BOISSONS");

        btnnouveau.setBackground(new java.awt.Color(0, 0, 102));
        btnnouveau.setForeground(new java.awt.Color(255, 255, 255));
        btnnouveau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/nouveau.png"))); // NOI18N
        btnnouveau.setText("Nouveau");
        btnnouveau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnouveauActionPerformed(evt);
            }
        });

        btnajouter.setBackground(new java.awt.Color(255, 102, 0));
        btnajouter.setForeground(new java.awt.Color(255, 255, 255));
        btnajouter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Ajouter.png"))); // NOI18N
        btnajouter.setText("Ajouter");
        btnajouter.setEnabled(false);
        btnajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnajouterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnnouveau)
                .addGap(18, 18, 18)
                .addComponent(btnajouter, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnnouveau)
                    .addComponent(btnajouter))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Boisson");

        jLabel7.setText("Nombre");

        txtnombre.setEnabled(false);
        txtnombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtnombreMouseClicked(evt);
            }
        });
        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });
        txtnombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreKeyTyped(evt);
            }
        });

        cmbboisson.setEnabled(false);
        cmbboisson.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbboissonFocusGained(evt);
            }
        });
        cmbboisson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbboissonActionPerformed(evt);
            }
        });

        jLabel5.setText("Serveur(se)");

        cmbserveur.setEnabled(false);
        cmbserveur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbserveurActionPerformed(evt);
            }
        });

        jLabel8.setText("Frigo");

        cbofrigo.setEnabled(false);
        cbofrigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbofrigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbboisson, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbserveur, 0, 163, Short.MAX_VALUE)
                                    .addComponent(cbofrigo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbofrigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbboisson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbserveur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel1.setText("COMMANDE ACTUELLE");

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel3.setText("    TOTAL");

        txttotal.setEnabled(false);
        txttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttotalActionPerformed(evt);
            }
        });

        chpayer.setText("Payer");
        chpayer.setEnabled(false);
        chpayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chpayerActionPerformed(evt);
            }
        });

        btnimprimer.setBackground(new java.awt.Color(204, 0, 0));
        btnimprimer.setForeground(new java.awt.Color(255, 255, 255));
        btnimprimer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/print.png"))); // NOI18N
        btnimprimer.setText("Imprimer");
        btnimprimer.setEnabled(false);
        btnimprimer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnimprimerActionPerformed(evt);
            }
        });

        tabcommande.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Boisson", "Prix", "Quantite", "Sous-total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabcommande.setEnabled(false);
        jScrollPane1.setViewportView(tabcommande);

        btnvalider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/valider.png"))); // NOI18N
        btnvalider.setText("Valider");
        btnvalider.setEnabled(false);
        btnvalider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnvaliderActionPerformed(evt);
            }
        });

        jLabel9.setText("Avoir");

        txtAvoir.setEnabled(false);
        txtAvoir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAvoirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(btnvalider)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnimprimer))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addComponent(chpayer, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtAvoir, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txttotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtAvoir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chpayer)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnimprimer)
                    .addComponent(btnvalider))
                .addGap(16, 16, 16))
        );

        btnlistcommande.setBackground(new java.awt.Color(0, 0, 0));
        btnlistcommande.setForeground(new java.awt.Color(255, 255, 255));
        btnlistcommande.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/list.png"))); // NOI18N
        btnlistcommande.setText("Liste commande");
        btnlistcommande.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlistcommandeActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 0, 51));

        jLabel2.setFont(new java.awt.Font("Arial Black", 3, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("    COMMANDE");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(406, 406, 406)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(332, 332, 332)
                        .addComponent(btnlistcommande))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(50, 50, 50)
                .addComponent(btnlistcommande)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttotalActionPerformed

    private void btnimprimerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnimprimerActionPerformed
        
        cmbboisson.setEnabled(false);
    txtAvoir.setEnabled(false);
    txtnombre.setEnabled(false);
    tabcommande.setEnabled(false);
    btnajouter.setEnabled(false);
    btnimprimer.setEnabled(false);
    chpayer.setEnabled(false);
    btnnouveau.setEnabled(true);
    cmbserveur.setEnabled(false);
    cbofrigo.setEnabled(false);
    btnvalider.setEnabled(false);

     try {
        // ⚡ ticket basé sur la dernière commande validée
        String idCommande = commande.genererIdCommande();
        String totalStr = txttotal.getText();
        String avoirStr = txtAvoir.getText(); // nouveau champ pour l’avoir

        // ⚡ Appel de la méthode avec 5 paramètres
        String ticket = commande.genererTicket(tabcommande, cmbserveur, idCommande, totalStr, avoirStr);

        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) return Printable.NO_SUCH_PAGE;
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
            int y = 15;
            for (String line : ticket.split("\n")) {
                g2d.drawString(line, 10, y);
                y += 12;
            }
            return Printable.PAGE_EXISTS;
        });
        job.print();
        JOptionPane.showMessageDialog(this, "Ticket imprimé avec succès !");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors de l'impression.");
    }

    }//GEN-LAST:event_btnimprimerActionPerformed

    private void btnnouveauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnouveauActionPerformed
        // TODO add your handling code here:
        cmbboisson.setEnabled(true);
    txtAvoir.setEnabled(false);
    txtnombre.setEnabled(true);
    tabcommande.setEnabled(true);
    btnajouter.setEnabled(true);
    btnimprimer.setEnabled(false);
    chpayer.setEnabled(false);
    btnnouveau.setEnabled(false);
    cmbserveur.setEnabled(true);
    cbofrigo.setEnabled(true);
    btnvalider.setEnabled(false);
    
    // Effacer le tableau
    DefaultTableModel model = (DefaultTableModel) tabcommande.getModel();
    model.setRowCount(0);

    try {
        Connection c = (Connection) connexionbd.seconnecter();

        // Vérifier caisse ouverte
        PreparedStatement psCaisse = (PreparedStatement) c.prepareStatement("SELECT id_caisse FROM caisse WHERE statut = 'OUVERTE' LIMIT 1");
        ResultSet rsCaisse = psCaisse.executeQuery();
        if (!rsCaisse.next()) {
            JOptionPane.showMessageDialog(this, "Aucune caisse ouverte !");
            return;
        }
        String idCaisse = rsCaisse.getString("id_caisse");

        // Charger le premier frigo
        PreparedStatement psFrigo = (PreparedStatement) c.prepareStatement("SELECT idFrigo FROM frigo LIMIT 1");
        ResultSet rsFrigo = psFrigo.executeQuery();
        if (rsFrigo.next()) {
            cbofrigo.addItem(rsFrigo.getString("idFrigo"));
        }

        // Charger les serveurs
        PreparedStatement psAgent = (PreparedStatement) c.prepareStatement("SELECT nom FROM agent WHERE profil = 'Serveur(se)'");
        ResultSet rsAgent = psAgent.executeQuery();
        while (rsAgent.next()) {
            cmbserveur.addItem(rsAgent.getString("nom"));
        }

        c.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

try {
        commande.chargerFrigos(cbofrigo);
        commande.chargerServeurs(cmbserveur);
        if (cbofrigo.getItemCount() > 0) {
            commande.chargerBoissons(cmbboisson, cbofrigo.getItemAt(0));
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors du chargement des données.");
    }

    }//GEN-LAST:event_btnnouveauActionPerformed

    private void btnajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnajouterActionPerformed
cmbboisson.setEnabled(true);
    txtAvoir.setEnabled(true);
    txtnombre.setEnabled(true);
    tabcommande.setEnabled(true);
    btnajouter.setEnabled(true);
    btnimprimer.setEnabled(false);
    chpayer.setEnabled(true);
    btnnouveau.setEnabled(true);
    cmbserveur.setEnabled(true);
    cbofrigo.setEnabled(true);
    btnvalider.setEnabled(true);
    try {
        Connection c = (Connection) connexionbd.seconnecter();

        // Extraire ID frigo
        String frigoItem = cbofrigo.getSelectedItem().toString();
        String idFrigo = frigoItem.split(" - ")[0];

        // Extraire nom boisson (sécurisé)
        String boissonItem = cmbboisson.getSelectedItem().toString();
        String boissonNom;
        int idx = boissonItem.indexOf("(");
        if (idx != -1) {
            boissonNom = boissonItem.substring(0, idx).trim();
        } else {
            boissonNom = boissonItem.trim();
        }

        int quantite = Integer.parseInt(txtnombre.getText());

        // Vérifier stock disponible
        PreparedStatement psStock = (PreparedStatement) c.prepareStatement(
            "SELECT bf.stock, b.id FROM boisson_frigo bf JOIN boisson b ON b.id = bf.id_boisson WHERE bf.id_frigo = ? AND b.nom = ?"
        );
        psStock.setString(1, idFrigo);
        psStock.setString(2, boissonNom);
        ResultSet rs = psStock.executeQuery();

        if (rs.next()) {
            int stock = rs.getInt("stock");
            int idBoisson = rs.getInt("id");

            if (stock < quantite) {
                JOptionPane.showMessageDialog(this, "Stock insuffisant !");
                return;
            }

            // Décrémenter le stock
            PreparedStatement psUpdate = (PreparedStatement) c.prepareStatement(
                "UPDATE boisson_frigo SET stock = stock - ? WHERE id_frigo = ? AND id_boisson = ?"
            );
            psUpdate.setInt(1, quantite);
            psUpdate.setString(2, idFrigo);
            psUpdate.setInt(3, idBoisson);
            psUpdate.executeUpdate();

            // Récupérer prix unitaire
            double prixUnitaire = commande.getPrixBoisson(boissonNom);
            double prixTotal = prixUnitaire * quantite;

            // Ajouter la ligne dans le tableau (4 colonnes)
            DefaultTableModel model = (DefaultTableModel) tabcommande.getModel();
            model.addRow(new Object[]{boissonNom, prixUnitaire, quantite, prixTotal});

            // Recalculer le total cumulé
            double total = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                total += Double.parseDouble(model.getValueAt(i, 3).toString());
            }
            txttotal.setText(total + " FCFA");
        }

        rs.close();
        psStock.close();
        c.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la boisson.");
    }
        
    }//GEN-LAST:event_btnajouterActionPerformed

     

    private void cmbserveurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbserveurActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbserveurActionPerformed

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtnombreActionPerformed

    private void btnlistcommandeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlistcommandeActionPerformed
    try {
        // Créer et afficher la fenêtre formlistcommande
        formlistcommande listFrame = new formlistcommande();
        listFrame.setVisible(true);

        // Charger les commandes dans le tableau de formlistcommande
        Connection c = (Connection) connexionbd.seconnecter();
        Statement st = (Statement) c.createStatement();
        ResultSet rs = st.executeQuery("SELECT id_commande, statut, serveur, date_commande FROM commande");

        DefaultTableModel model = (DefaultTableModel) listFrame.tablcommande.getModel();
        model.setRowCount(0);

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_commande"),
                rs.getString("statut"),
                rs.getString("serveur"),
                rs.getDate("date_commande")
            });
        }

        rs.close();
        st.close();
        c.close();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors de l'ouverture de la liste des commandes.");
    }        

    }//GEN-LAST:event_btnlistcommandeActionPerformed

    private void cbofrigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbofrigoActionPerformed

    try {
        if (cbofrigo.getSelectedItem() != null) {
            String frigoSelectionne = cbofrigo.getSelectedItem().toString();

            // ✅ Récupérer l'ID du frigo par son nom
            String idFrigo = frigo.getIdFrigoParNom(frigoSelectionne);

            cmbboisson.removeAllItems();

            // ✅ Utiliser la méthode utilitaire pour récupérer les boissons
            ResultSet rs = commande.getBoissonsParFrigo(idFrigo);

            while (rs.next()) {
                String nomBoisson = rs.getString("nom");
                int prix = rs.getInt("prix_unitaire");
                int stock = rs.getInt("stock");

                cmbboisson.addItem(nomBoisson + " (" + prix + " FCFA, stock: " + stock + ")");
            }

            rs.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors du chargement des boissons.");
    }
        


    }//GEN-LAST:event_cbofrigoActionPerformed

    private void cmbboissonFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbboissonFocusGained

    }//GEN-LAST:event_cmbboissonFocusGained

    private void cmbboissonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbboissonActionPerformed
        // TODO add your handling code here:
        txtnombre.grabFocus();
         if (cmbboisson.getSelectedItem() != null) {
        try {
            String nomBoisson = cmbboisson.getSelectedItem().toString();
            double prix = commande.getPrixBoisson(nomBoisson);
//            txttotal.setText(String.valueOf(prix)); // txtprix = champ texte pour afficher le prix
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         
         
    }//GEN-LAST:event_cmbboissonActionPerformed

    private void btnvaliderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnvaliderActionPerformed

        cmbboisson.setEnabled(false);
    txtAvoir.setEnabled(false);
    txtnombre.setEnabled(false);
    tabcommande.setEnabled(false);
    btnajouter.setEnabled(false);
    btnimprimer.setEnabled(true);
    chpayer.setEnabled(false);
    btnnouveau.setEnabled(true);
    cmbserveur.setEnabled(false);
    cbofrigo.setEnabled(false);
    btnvalider.setEnabled(false);
        try {
        // Générer un nouvel ID pour la commande
        String idCommande = commande.genererIdCommande();

        // Récupérer le serveur sélectionné
        String serveur = (cmbserveur.getSelectedItem() != null) 
            ? cmbserveur.getSelectedItem().toString() 
            : "Serveur inconnu";

        // Récupérer l'ID agent
        int idAgent = commande.getIdAgent(serveur);

        // Récupérer l'ID caisse ouverte
        String idCaisse = commande.getIdCaisseOuverte();

        // Statut payé ou non
        boolean payer = chpayer.isSelected();

        // Total
        double total = Double.parseDouble(txttotal.getText().replace("FCFA", "").trim());

        // Récupérer le modèle du tableau
        DefaultTableModel model = (DefaultTableModel) tabcommande.getModel();

        // ⚡ Enregistrer la commande
        commande.enregistrerCommande(idCommande, idAgent, idCaisse, serveur, payer, total, model);

        // ⚡ Calculer la somme des quantités
        int totalQuantite = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            totalQuantite += Integer.parseInt(model.getValueAt(i, 2).toString()); // colonne 2 = quantité
        }

        // ⚡ Mettre à jour nb_bouteilles de l’agent choisi (via la nouvelle fonction)
        agent.ajouterBouteilles(serveur, totalQuantite);

        JOptionPane.showMessageDialog(this, "Commande enregistrée et nb_bouteilles mis à jour !");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement.");
    }

    }//GEN-LAST:event_btnvaliderActionPerformed

    private void chpayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chpayerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chpayerActionPerformed

    private void txtAvoirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAvoirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAvoirActionPerformed

    private void txtnombreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtnombreMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtnombreMouseClicked

    private void txtnombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreKeyTyped
        // TODO add your handling code here:
         char c=evt.getKeyChar();
        if(!(Character.isDigit(c) || c==KeyEvent.VK_BACK_SPACE)){
        getToolkit().beep();
        evt.consume();
    }//GEN-LAST:event_txtnombreKeyTyped
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnajouter;
    public static javax.swing.JButton btnimprimer;
    private javax.swing.JButton btnlistcommande;
    private javax.swing.JButton btnnouveau;
    public static javax.swing.JButton btnvalider;
    private javax.swing.JComboBox<String> cbofrigo;
    public static javax.swing.JCheckBox chpayer;
    private javax.swing.JComboBox<String> cmbboisson;
    public static javax.swing.JComboBox<String> cmbserveur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tabcommande;
    public static javax.swing.JTextField txtAvoir;
    private javax.swing.JTextField txtnombre;
    public static javax.swing.JTextField txttotal;
    // End of variables declaration//GEN-END:variables
}
