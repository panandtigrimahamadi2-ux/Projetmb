/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MHD
 */
public class utilisateur {
     public static void enregistrer(String nom, String identifiant, String motdepasse, String profil) {
        try {
            Connection c = (Connection) connexionbd.seconnecter();
            String sql = "INSERT INTO utilisateur (nom, identifiant, motdepasse, profil) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setString(2, identifiant);
            ps.setString(3, motdepasse);
            ps.setString(4, profil);
            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            Logger.getLogger(utilisateur.class.getName()).log(Level.SEVERE, null, e);
        }
    }
public static Vector<String> getListeProfils() {
        Vector<String> profils = new Vector<>();
        try {
            Connection c = (Connection) connexionbd.seconnecter();
            Statement st = (Statement) c.createStatement();
            ResultSet rs = st.executeQuery("SELECT droit FROM profil ORDER BY droit");

            while (rs.next()) {
                profils.add(rs.getString("droit"));
            }

            rs.close();
            st.close();
            c.close();
        } catch (Exception e) {
            Logger.getLogger(utilisateur.class.getName()).log(Level.SEVERE, null, e);
        }
        return profils;
    }
public static boolean modifierMotDePasse(String identifiant, String ancienMDP, String nouveauMDP) {
    try {
        Connection c = (Connection) connexionbd.seconnecter();

        // Vérifier si l'identifiant et l'ancien mot de passe sont corrects
        String sqlVerif = "SELECT * FROM utilisateur WHERE identifiant = ? AND motdepasse = ?";
        PreparedStatement psVerif = (PreparedStatement) c.prepareStatement(sqlVerif);
        psVerif.setString(1, identifiant);
        psVerif.setString(2, ancienMDP);
        ResultSet rs = psVerif.executeQuery();

        if (rs.next()) {
            // Mise à jour du mot de passe
            String sqlUpdate = "UPDATE utilisateur SET motdepasse = ? WHERE identifiant = ?";
            PreparedStatement psUpdate = (PreparedStatement) c.prepareStatement(sqlUpdate);
            psUpdate.setString(1, nouveauMDP);
            psUpdate.setString(2, identifiant);
            psUpdate.executeUpdate();
            psUpdate.close();
            rs.close();
            psVerif.close();
            c.close();
            return true;
        } else {
            rs.close();
            psVerif.close();
            c.close();
            return false;
        }
    } catch (Exception e) {
        Logger.getLogger(utilisateur.class.getName()).log(Level.SEVERE, null, e);
        return false;
    }
}

 public static String hacherMD5(String motdepasse) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(motdepasse.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b)); // Convertir chaque byte en hexadécimal
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
