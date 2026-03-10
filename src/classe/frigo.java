/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.PreparedStatement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import m.b.formfrigo;
import static m.b.formfrigo.tablistboisson;
import m.b.formlistefrigo;
import static m.b.formmain.*;

import static m.b.formfrigo.tablistboisson;

import static m.b.formfrigo.tablistboisson;

/**
 *
 * @author MHD
 */
public class frigo {
     private static Connection c=null;
    private static Statement st=null;
    private static ResultSet rs=null;
    private JCheckBox chk24;
private JCheckBox chk12;
private JCheckBox chk6;
private JCheckBox chk1;
    

public static void actualiserBoissonsDuFrigo(String idFrigo, DefaultTableModel tm) {
    try {
        Connection c = connexionbd.seconnecter();
        String sql = "SELECT boisson, nombre FROM frigo WHERE idFrigo = ?";
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
        ps.setString(1, idFrigo);
        ResultSet rs = ps.executeQuery();

        tm.setRowCount(0); // Vider le tableau avant de le remplir
        int i = 1;
        while (rs.next()) {
            String boisson = rs.getString("boisson");
            int nombre = rs.getInt("nombre");
            tm.addRow(new Object[]{i++, boisson, nombre});
        }

        rs.close();
        ps.close();
        c.close();
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
    }
}

 public static void ravitaillerDepuisStock(String idFrigo, int idBoisson, int quantite) {
        try {
            Connection c = connexionbd.seconnecter();

            // Décrémenter le stock global
            String sqlUpdateBoisson = "UPDATE boisson SET stock = stock - ? WHERE id = ?";
            PreparedStatement ps1 = (PreparedStatement) c.prepareStatement(sqlUpdateBoisson);
            ps1.setInt(1, quantite);
            ps1.setInt(2, idBoisson);
            ps1.executeUpdate();
            ps1.close();

            // Vérifier si la boisson existe déjà dans ce frigo
            String sqlCheck = "SELECT stock FROM boisson_frigo WHERE id_frigo = ? AND id_boisson = ?";
            PreparedStatement ps2 = (PreparedStatement) c.prepareStatement(sqlCheck);
            ps2.setString(1, idFrigo);
            ps2.setInt(2, idBoisson);
            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                // Si elle existe → UPDATE
                String sqlUpdateFrigo = "UPDATE boisson_frigo SET stock = stock + ? WHERE id_frigo = ? AND id_boisson = ?";
                PreparedStatement ps3 = (PreparedStatement) c.prepareStatement(sqlUpdateFrigo);
                ps3.setInt(1, quantite);
                ps3.setString(2, idFrigo);
                ps3.setInt(3, idBoisson);
                ps3.executeUpdate();
                ps3.close();
            } else {
// Si elle n’existe pas → INSERT
                String sqlInsertFrigo = "INSERT INTO boisson_frigo (id_frigo, id_boisson, stock) VALUES (?, ?, ?)";
                PreparedStatement ps4 = (PreparedStatement) c.prepareStatement(sqlInsertFrigo);
                ps4.setString(1, idFrigo);
                ps4.setInt(2, idBoisson);
                ps4.setInt(3, quantite);
                ps4.executeUpdate();
                ps4.close();
            }

            rs.close();
            ps2.close();
            c.close();

            JOptionPane.showMessageDialog(null, "Ravitaillement effectué avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du ravitaillement !");
        }
    }

//public static void ravitaillerDepuisStock(String idFrigo, int idBoisson, int quantite) {
//    try {
//        Connection c = connexionbd.seconnecter();
//
//        // 1. Décrémenter le stock global
//        String sqlUpdateBoisson = "UPDATE boisson SET stock = stock - ? WHERE id = ?";
//        PreparedStatement ps1 = (PreparedStatement) c.prepareStatement(sqlUpdateBoisson);
//        ps1.setInt(1, quantite);
//        ps1.setInt(2, idBoisson);
//        ps1.executeUpdate();
//        ps1.close();
//
//        // 2. Vérifier si la boisson existe déjà dans ce frigo
//        String sqlCheck = "SELECT stock FROM boisson_frigo WHERE id_frigo = ? AND id_boisson = ?";
//        PreparedStatement ps2 = (PreparedStatement) c.prepareStatement(sqlCheck);
//        ps2.setString(1, idFrigo);
//        ps2.setInt(2, idBoisson);
//        ResultSet rs = ps2.executeQuery();
//
//        if (rs.next()) {
//            // 3a. Si elle existe → UPDATE
//            String sqlUpdateFrigo = "UPDATE boisson_frigo SET stock = stock + ? WHERE id_frigo = ? AND id_boisson = ?";
//            PreparedStatement ps3 = (PreparedStatement) c.prepareStatement(sqlUpdateFrigo);
//            ps3.setInt(1, quantite);
//            ps3.setString(2, idFrigo);
//            ps3.setInt(3, idBoisson);
//            ps3.executeUpdate();
//            ps3.close();
//        } else {
//            // 3b. Si elle n’existe pas → INSERT
//            String sqlInsertFrigo = "INSERT INTO boisson_frigo (id_frigo, id_boisson, stock) VALUES (?, ?, ?)";
//            PreparedStatement ps4 = (PreparedStatement) c.prepareStatement(sqlInsertFrigo);
//            ps4.setString(1, idFrigo);
//            ps4.setInt(2, idBoisson);
//            ps4.setInt(3, quantite);
//            ps4.executeUpdate();
//            ps4.close();
//        }
//
//        rs.close();
//        ps2.close();
//        c.close();
//
//        JOptionPane.showMessageDialog(null, "Ravitaillement effectué avec succès !");
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Erreur lors du ravitaillement !");
//    }
//}
public static void ravitailler(String idFrigo, String boisson, int nombre) throws SQLException {
    try {
        Connection c = connexionbd.seconnecter();

        String checkSql = "SELECT * FROM frigo WHERE idFrigo = ? AND boisson = ?";
        PreparedStatement checkPs = (PreparedStatement) c.prepareStatement(checkSql);
        checkPs.setString(1, idFrigo);
        checkPs.setString(2, boisson);
        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {
            int ancienNombre = rs.getInt("nombre");
            int nouveauNombre = ancienNombre + nombre;

            String updateSql = "UPDATE frigo SET nombre = ? WHERE idFrigo = ? AND boisson = ?";
            PreparedStatement updatePs = (PreparedStatement) c.prepareStatement(updateSql);
            updatePs.setInt(1, nouveauNombre);
            updatePs.setString(2, idFrigo);
            updatePs.setString(3, boisson);
            updatePs.executeUpdate();
            updatePs.close();

            JOptionPane.showMessageDialog(null, "Frigo mis à jour avec succès !");
        } else {
            String insertSql = "INSERT INTO frigo (idFrigo, boisson, nombre) VALUES (?, ?, ?)";
            PreparedStatement insertPs = (PreparedStatement) c.prepareStatement(insertSql);
            insertPs.setString(1, idFrigo);
            insertPs.setString(2, boisson);
            insertPs.setInt(3, nombre);
            insertPs.executeUpdate();
            insertPs.close();

            JOptionPane.showMessageDialog(null, "Ravitaillement enregistré !");
        }

        rs.close();
        checkPs.close();
        c.close();

    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Erreur lors du ravitaillement.");
    }
}    public static void actualiserStock(DefaultTableModel tm) {
    try {
        com.mysql.jdbc.Connection c = (com.mysql.jdbc.Connection) connexionbd.seconnecter();
        String sql = "SELECT * FROM frigo"; // ta table stock avec colonnes boisson et nombre
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        int i = 1; // compteur pour la colonne N°
        while (rs.next()) {
            String boisson = rs.getString("boisson");
            int nombre = rs.getInt("nombre");

            // Ajouter une ligne au tableau
            tm.addRow(new Object[]{i, boisson, nombre});
            i++;
        }

        rs.close();
        ps.close();
        c.close();
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    


public static void sauvegarderAvantSuppression(String idFrigo, String nomFrigo) {
        try {
            Connection c = connexionbd.seconnecter();
            String sql = "SELECT b.nom, bf.stock " +
                         "FROM boisson_frigo bf " +
                         "JOIN boisson b ON bf.id_boisson = b.id " +
                         "WHERE bf.id_frigo = ?";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
            ps.setString(1, idFrigo);
            ResultSet rs = ps.executeQuery();

            StringBuilder sauvegarde = new StringBuilder();
            sauvegarde.append("=== SAUVEGARDE DU FRIGO ").append(nomFrigo).append(" ===\n");
            sauvegarde.append("Date : ").append(java.time.LocalDateTime.now()).append("\n\n");

            while (rs.next()) {
                String boisson = rs.getString("nom");
                int stock = rs.getInt("stock");
                sauvegarde.append("Boisson : ").append(boisson).append(" | Quantité : ").append(stock).append("\n");
            }

            JOptionPane.showMessageDialog(null, sauvegarde.toString());

            rs.close();
            ps.close();
            c.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erreur lors de la sauvegarde des données.");
        }
    }

 public static void viderFrigo(String idFrigo) {
        try {
            Connection c = connexionbd.seconnecter();
            String sql = "DELETE FROM boisson_frigo WHERE id_frigo = ?";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
            ps.setString(1, idFrigo);
            int lignesSupprimees = ps.executeUpdate();

            ps.close();
            c.close();

            JOptionPane.showMessageDialog(null, lignesSupprimees + " boisson(s) supprimée(s) du frigo.");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erreur lors de la suppression des boissons.");
        }
    }

//public static void viderFrigo(String idFrigo) {
//    try {
//        Connection c = connexionbd.seconnecter();
//        String sql = "DELETE FROM frigo WHERE idFrigo = ?";
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//        ps.setString(1, idFrigo);
//        int lignesSupprimees = ps.executeUpdate();
//
//        ps.close();
//        c.close();
//
//        JOptionPane.showMessageDialog(null, lignesSupprimees + " boisson(s) supprimée(s) du frigo.");
//    } catch (ClassNotFoundException | SQLException ex) {
//        Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
//        JOptionPane.showMessageDialog(null, "Erreur lors de la suppression des boissons.");
//    }
//}
public static void chargerFrigosDansCombo(JComboBox<String> combo) {
    combo.removeAllItems();
    try {
        Connection c = (Connection) connexionbd.seconnecter();
        String sql = "SELECT idFrigo, nom_frigo FROM frigo ORDER BY idFrigo DESC";
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String item = rs.getString("idFrigo") + " - " + rs.getString("nom_frigo");
            combo.addItem(item);
        }
        rs.close();
        ps.close();
        c.close();
    } catch (Exception ex) {
        Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
    }
}// public static void chargerFrigosDansComboBox(JComboBox<String> cbofrigo) {
//        try {
//            Connection c = connexionbd.seconnecter();
//            String sql = "SELECT idFrigo, nom_frigo FROM frigo";
//            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//
//            cbofrigo.removeAllItems();
//
//            while (rs.next()) {
//                String idFrigo = rs.getString("idFrigo");
//                String nomFrigo = rs.getString("nom_frigo");
//                cbofrigo.addItem(idFrigo + " - " + nomFrigo);
//            }
//
//            rs.close();
//            ps.close();
//            c.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Erreur lors du chargement du frigo !");
//        }
//    }


public static java.util.List<BoissonItem> getBoissonsParFrigo(String idFrigo) {
    java.util.List<BoissonItem> boissons = new java.util.ArrayList<>();
    try {
        Connection c = connexionbd.seconnecter();
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(
            "SELECT b.id, b.nom, bf.stock " +
            "FROM boisson_frigo bf " +
            "JOIN boisson b ON bf.id_boisson = b.id " +
            "WHERE bf.id_frigo = ? AND bf.stock > 0"
        );
        ps.setString(1, idFrigo);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int idBoisson = rs.getInt("id");
            String nomBoisson = rs.getString("nom");
            int stock = rs.getInt("stock");

            boissons.add(new BoissonItem(idBoisson, nomBoisson, stock));
        }

        rs.close();
        ps.close();
        c.close();
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
    }
    return boissons;
}

// Méthode qui remplit directement un DefaultTableModel
public static void chargerBoissonsDansTable(String idFrigo, JTable tabBoissons) {
        try {
            Connection c = connexionbd.seconnecter();
            String sql = "SELECT b.nom, bf.stock " +
                         "FROM boisson_frigo bf " +
                         "JOIN boisson b ON bf.id_boisson = b.id " +
                         "WHERE bf.id_frigo = ?";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
            ps.setString(1, idFrigo);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Boisson");
            model.addColumn("Stock");

            while (rs.next()) {
                String nomBoisson = rs.getString("nom");
                int stock = rs.getInt("stock");
                model.addRow(new Object[]{nomBoisson, stock});
            }

            tabBoissons.setModel(model);

            rs.close();
            ps.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des boissons !");
        }
    }

//public static void chargerBoissonsDansTable(String idFrigo, JTable tabBoissons) {
//    try {
//        Connection c = connexionbd.seconnecter();
//
//        // Requête pour récupérer les boissons d'un frigo
//        String sql = "SELECT b.nom, bf.stock " +
//                     "FROM boisson_frigo bf " +
//                     "JOIN boisson b ON bf.id_boisson = b.id " +
//                     "WHERE bf.id_frigo = ?";
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//        ps.setString(1, idFrigo);
//        ResultSet rs = ps.executeQuery();
//
//        // Préparer le modèle du tableau
//        DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("Boisson");
//        model.addColumn("Stock");
//
//        // Remplir le tableau avec les résultats
//        while (rs.next()) {
//            String nomBoisson = rs.getString("nom");
//            int stock = rs.getInt("stock");
//            model.addRow(new Object[]{nomBoisson, stock});
//        }
//
//        // Appliquer le modèle au JTable
//        tabBoissons.setModel(model);
//
//        rs.close();
//        ps.close();
//        c.close();
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Erreur lors du chargement des boissons !");
//    }
//}
    public static void ravitaillerDepuisStock(String idFrigo, String nomBoisson, int quantite) {
        try {
            Connection c = connexionbd.seconnecter();

            // Décrémenter le stock global
            String sqlUpdateBoisson = "UPDATE boisson SET stock = stock - ? WHERE nom = ?";
            PreparedStatement ps1 = (PreparedStatement) c.prepareStatement(sqlUpdateBoisson);
            ps1.setInt(1, quantite);
            ps1.setString(2, nomBoisson);
            ps1.executeUpdate();
            ps1.close();

            // Vérifier si la boisson existe déjà dans ce frigo
            String sqlCheck = "SELECT stock FROM boisson_frigo WHERE id_frigo = ? AND id_boisson = " +
                              "(SELECT id FROM boisson WHERE nom = ?)";
            PreparedStatement ps2 = (PreparedStatement) c.prepareStatement(sqlCheck);
            ps2.setString(1, idFrigo);
            ps2.setString(2, nomBoisson);
            ResultSet rs = ps2.executeQuery();

            if (rs.next()) {
                // Si elle existe → UPDATE
                String sqlUpdateFrigo = "UPDATE boisson_frigo SET stock = stock + ? " +
                                        "WHERE id_frigo = ? AND id_boisson = (SELECT id FROM boisson WHERE nom = ?)";
                PreparedStatement ps3 = (PreparedStatement) c.prepareStatement(sqlUpdateFrigo);
                ps3.setInt(1, quantite);
                ps3.setString(2, idFrigo);
                ps3.setString(3, nomBoisson);
                ps3.executeUpdate();
                ps3.close();
            } else {
                // Si elle n’existe pas → INSERT
                String sqlInsertFrigo = "INSERT INTO boisson_frigo (id_frigo, id_boisson, stock) " +
                                        "VALUES (?, (SELECT id FROM boisson WHERE nom = ?), ?)";
                PreparedStatement ps4 = (PreparedStatement) c.prepareStatement(sqlInsertFrigo);
                ps4.setString(1, idFrigo);
                ps4.setString(2, nomBoisson);
                ps4.setInt(3, quantite);
                ps4.executeUpdate();
                ps4.close();
            }

            rs.close();
            ps2.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du ravitaillement !");
        }
    }
    
//public void chargerBoissonsDuFrigo(String idFrigo) {
//    try {
//        Connection c = connexionbd.seconnecter();
//
//        String sql = "SELECT b.nom, bf.stock " +
//                     "FROM boisson_frigo bf " +
//                     "JOIN boisson b ON bf.id_boisson = b.id " +
//                     "WHERE bf.id_frigo = ?";
//
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//        ps.setString(1, idFrigo);
//
//        ResultSet rs = ps.executeQuery();
//
//        DefaultTableModel model = new DefaultTableModel();
//        model.addColumn("Boisson");
//        model.addColumn("Stock");
//
//        while (rs.next()) {
//
//            String nomBoisson = rs.getString("nom");
//            int stock = rs.getInt("stock");
//
//            model.addRow(new Object[]{nomBoisson, stock});
//        }
//
//        tablistboisson.setModel(model);
//
//        rs.close();
//        ps.close();
//        c.close();
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Erreur lors du chargement des boissons du frigo !");
//    }
//}
    public static void supprimerBoisson(String idFrigo, int idBoisson) {

        try {

            Connection c = connexionbd.seconnecter();

            String sql = "DELETE FROM boisson_frigo WHERE id_frigo=? AND id_boisson=?";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);

            ps.setString(1, idFrigo);
            ps.setInt(2, idBoisson);

            ps.executeUpdate();

            ps.close();
            c.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
}
    public static int getIdBoissonParNom(String nomBoisson) throws SQLException, ClassNotFoundException {
    Connection c = connexionbd.seconnecter();
    String sql = "SELECT id FROM boisson WHERE nom = ?";
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
    ps.setString(1, nomBoisson);
    ResultSet rs = ps.executeQuery();

    int id = -1;
    if (rs.next()) {
        id = rs.getInt("id");
    }

    rs.close();
    ps.close();
    c.close();
    return id;
}
}
