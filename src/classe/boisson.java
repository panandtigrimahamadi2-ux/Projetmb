/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;

/**
 *
 * @author MHD
 */
public class boisson {
    private static Connection c=null;
    private static Statement st=null;
    private static ResultSet rs=null;
    
public static void valider(String nom, int prix_unitaire) {
    try {
        c = connexionbd.seconnecter();

        // Vérifier si la boisson existe déjà
        String checkSql = "SELECT * FROM boisson WHERE nom = ?";
        PreparedStatement checkPs = c.prepareStatement(checkSql);
        checkPs.setString(1, nom);
        rs = checkPs.executeQuery();

        if (rs.next()) {
            // Si une ligne est trouvée, la boisson existe déjà
            JOptionPane.showMessageDialog(null, "Boisson déjà enregistrée !");
        } else {
            // Sinon, on insère la nouvelle boisson
            String insertSql = "INSERT INTO boisson (nom, prix_unitaire) VALUES (?, ?)";
            PreparedStatement insertPs = c.prepareStatement(insertSql);
            insertPs.setString(1, nom);
            insertPs.setInt(2, prix_unitaire);
            insertPs.executeUpdate();

            JOptionPane.showMessageDialog(null, "Enregistrement effectué !");
            insertPs.close();
        }
        

        // Fermer les ressources
        rs.close();
        checkPs.close();
        c.close();

    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(boisson.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    public static void actualiser(DefaultTableModel tm) {
    try {
        c = connexionbd.seconnecter();
        st = c.createStatement();
        rs = st.executeQuery("SELECT * FROM boisson");
        int n = 0;
        while (rs.next()) {
            n++;
            tm.addRow(new Object[]{
                n,
                rs.getString("nom"),
                rs.getInt("prix_unitaire") // ou "prix" selon ta table
            });
        }
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(boisson.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
     public static void supprimer(String nom){
        try {
            c=connexionbd.seconnecter();
            st=c.createStatement();
            st.executeUpdate("DELETE FROM boisson WHERE nom='"+nom+"'");
            JOptionPane.showMessageDialog(null,"La boisson '"+nom+"' a été supprimé!");
        } catch (Exception ex) {
            Logger.getLogger(boisson.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public static void modifier(String ancienNom, String nouveauNom, int nouveauPrix) {
    try {
        Connection c = connexionbd.seconnecter();
        String sql = "UPDATE boisson SET nom = ?, prix_unitaire = ? WHERE nom = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, nouveauNom);
        ps.setInt(2, nouveauPrix);
        ps.setString(3, ancienNom);
        ps.executeUpdate();
        ps.close();
        c.close();
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(boisson.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}
//   
//    public static void modifier(int ataux, int ntaux){
//        try {
//            c=connexionbd.seconnecter();
//            st=c.createStatement();
//            st.executeUpdate(" UPDATE parametres SET taux= "+ntaux+" WHERE taux="+ataux+"");
//            JOptionPane.showMessageDialog(null, "Modification effectué avec succès!");
//        } catch (HeadlessException | ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(parametres.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    }

    
