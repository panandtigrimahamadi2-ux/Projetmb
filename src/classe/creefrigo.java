/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.*;

/**
 *
 * @author MHD
 */
public class creefrigo {
//   public static String getIdentifiantParNom(String nomFrigo) throws ClassNotFoundException, ClassNotFoundException, ClassNotFoundException {
//    String identifiant = null;
//    try {
//        Connection c = (Connection) connexionbd.seconnecter();
//        String sql = "SELECT identifiant FROM creefrigo WHERE frigo = ?";
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//        ps.setString(1, nomFrigo);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            identifiant = rs.getString("identifiant");
//        }
//        rs.close();
//        ps.close();
//        c.close();
//    } catch (Exception ex) {
//        Logger.getLogger(creefrigo.class.getName()).log(Level.SEVERE, null, ex);
//    }
//    return identifiant;
//}
    
//    public static String genererIdentifiant() {
//        return new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
//    }
//
//public static void enregistrerFrigo(String identifiant, String nomFrigo) {
//    try {
//        Connection c = (Connection) connexionbd.seconnecter();
//
//        String sql = "INSERT INTO creefrigo (identifiant, frigo) VALUES (?, ?)";
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//        ps.setString(1, identifiant);
//        ps.setString(2, nomFrigo);
//        ps.executeUpdate();
//
//        ps.close();
//        c.close();
//
//        JOptionPane.showMessageDialog(null, "Frigo créé avec succès !");
//    } catch (SQLIntegrityConstraintViolationException ex) {
//        JOptionPane.showMessageDialog(null, "Cet identifiant existe déjà.");
//    } catch (Exception ex) {
//        Logger.getLogger(creefrigo.class.getName()).log(Level.SEVERE, null, ex);
//        JOptionPane.showMessageDialog(null, "Erreur lors de la création du frigo.");
//    }
//}
    
    // Générer un identifiant unique basé sur la date/heure
    public static String genererIdentifiant() {
        return new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date());
    }

    public static void enregistrerFrigo(String identifiant, String nomFrigo) {
    try {
        Connection c = (Connection) connexionbd.seconnecter();

        // Vérifier s'il existe déjà un frigo
        String checkSql = "SELECT COUNT(*) FROM frigo";
        PreparedStatement checkPs = (PreparedStatement) c.prepareStatement(checkSql);
        ResultSet rs = checkPs.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        checkPs.close();

        if (count > 0) {
            JOptionPane.showMessageDialog(null, "Un frigo existe déjà. Impossible d'en créer un autre.");
            c.close();
            return;
        }

        // Sinon, créer le frigo
        String sql = "INSERT INTO frigo (idFrigo, nom_frigo) VALUES (?, ?)";
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
        ps.setString(1, identifiant);
        ps.setString(2, nomFrigo);
        ps.executeUpdate();

        ps.close();
        c.close();

        JOptionPane.showMessageDialog(null, "Frigo créé avec succès !");
    } catch (Exception ex) {
        Logger.getLogger(creefrigo.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Erreur lors de la création du frigo.");
    }
}
    // Enregistrer un nouveau frigo dans la table frigo
//    public static void enregistrerFrigo(String identifiant, String nomFrigo) {
//        try {
//            Connection c = (Connection) connexionbd.seconnecter();
//
//            String sql = "INSERT INTO frigo (idFrigo, nom_frigo) VALUES (?, ?)";
//            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//            ps.setString(1, identifiant);
//            ps.setString(2, nomFrigo);
//            ps.executeUpdate();
//
//            ps.close();
//            c.close();
//
//            JOptionPane.showMessageDialog(null, "Frigo créé avec succès !");
//        } catch (SQLIntegrityConstraintViolationException ex) {
//            JOptionPane.showMessageDialog(null, "Cet identifiant existe déjà.");
//        } catch (Exception ex) {
//            Logger.getLogger(creefrigo.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Erreur lors de la création du frigo.");
//        }
//    }

// public static void chargerFrigosDansCombo(JComboBox<String> combo) {
//        combo.removeAllItems();
//        try {
//            Connection c = (Connection) connexionbd.seconnecter();
//            String sql = "SELECT identifiant, frigo FROM creefrigo ORDER BY identifiant DESC";
//            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                String item = rs.getString("identifiant") + " - " + rs.getString("frigo");
//                combo.addItem(item);
//            }
//            rs.close();
//            ps.close();
//            c.close();
//        } catch (Exception ex) {
//            Logger.getLogger(creefrigo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
     // Charger les frigos dans une ComboBox
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
            Logger.getLogger(creefrigo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// // Récupérer l'identifiant d'un frigo par son nom
    public static String getIdentifiantParNom(String nomFrigo) {
        String identifiant = null;
        try {
            Connection c = (Connection) connexionbd.seconnecter();

            String sql = "SELECT idFrigo FROM frigo WHERE nom_frigo = ?";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
            ps.setString(1, nomFrigo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                identifiant = rs.getString("idFrigo");
            }

            rs.close();
            ps.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors de la récupération de l'identifiant du frigo !");
        }
        return identifiant;
    }

}
