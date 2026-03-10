/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MHD
 */
public class profil {
    static Connection c;
    static Statement st;
    static ResultSet rs;

    public static void valider(String droit, int commande, int depense, int frigo, int boisson, int caisse, int stock, int agent, int statistique, int parametre) throws SQLException {
        try {
            c = (Connection) connexionbd.seconnecter();
            st = (Statement) c.createStatement();

            rs = st.executeQuery("SELECT * FROM profil WHERE droit='" + droit + "'");
            if (!rs.next()) {
                String sql = "INSERT INTO profil(droit, commande, depense, frigo, boisson, caisse, stock, agent, statistique, parametre) " +
                             "VALUES('" + droit + "'," + commande + "," + depense + "," + frigo + "," + boisson + "," + caisse + "," + stock + "," + agent + "," + statistique + "," + parametre + ")";
                st.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Profil enregistré avec succès !");
            } else {
                JOptionPane.showMessageDialog(null, droit + " est déjà enregistré !");
            }

            c.close(); st.close(); rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
public static ResultSet getTousLesProfils() throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    Statement st = (Statement) c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    return st.executeQuery("SELECT * FROM profil");
}
    public static void supprimer(String droit) {
    try {
        Connection c = (Connection) connexionbd.seconnecter();
        PreparedStatement ps = c.prepareStatement("DELETE FROM profil WHERE droit = ?");
        ps.setString(1, droit);
        ps.executeUpdate();
        ps.close(); c.close();
    } catch (Exception e) {
        Logger.getLogger(profil.class.getName()).log(Level.SEVERE, null, e);
    }
}
    
   public static void mettreAJour(String droit, int commande, int depense, int frigo, int boisson, int caisse, int stock, int agent, int statistique, int parametre) {
    try {
        Connection c = (Connection) connexionbd.seconnecter();
        String sql = "UPDATE profil SET commande=?, depense=?, frigo=?, boisson=?, caisse=?, stock=?, agent=?, statistique=?, parametre=? WHERE droit=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, commande);
        ps.setInt(2, depense);
        ps.setInt(3, frigo);
        ps.setInt(4, boisson);
        ps.setInt(5, caisse);
        ps.setInt(6, stock);
        ps.setInt(7, agent);
        ps.setInt(8, statistique);
        ps.setInt(9, parametre);
        ps.setString(10, droit);
        ps.executeUpdate();
        ps.close(); c.close();
    } catch (Exception e) {
        Logger.getLogger(profil.class.getName()).log(Level.SEVERE, null, e);
    }
}
    
   public static ResultSet getProfilParNom(String droit) {
    try {
        Connection c = (Connection) connexionbd.seconnecter();
        PreparedStatement ps = c.prepareStatement("SELECT * FROM profil WHERE droit = ?");
        ps.setString(1, droit);
        return ps.executeQuery();
    } catch (Exception e) {
        Logger.getLogger(profil.class.getName()).log(Level.SEVERE, null, e);
        return null;
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
        Logger.getLogger(profil.class.getName()).log(Level.SEVERE, null, e);
    }
    return profils;
}
   
   
}
