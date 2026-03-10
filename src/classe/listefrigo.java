/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import static m.b.formfrigo.tablistboisson;


/**
 *
 * @author MHD
 */
public class listefrigo {
    
public static void chargerDetailsFrigo(String idFrigo, JTable tabdetail) {
    DefaultTableModel model = (DefaultTableModel) tabdetail.getModel();
    model.setRowCount(0);

    try {
        Connection c = (Connection) connexionbd.seconnecter();
        String prefix = idFrigo.substring(0, 12); // les 12 premiers caractères

        String sql = "SELECT boisson, nombre FROM frigo WHERE LEFT(idFrigo, 12) = ?";
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
        ps.setString(1, prefix);
        ResultSet rs = ps.executeQuery();

        int i = 1;
        while (rs.next()) {
            String boisson = rs.getString("boisson");
            int nombre = rs.getInt("nombre");
            model.addRow(new Object[]{i++, boisson, nombre});
        }

        rs.close();
        ps.close();
        c.close();
    } catch (Exception ex) {
        Logger.getLogger(frigo.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, "Erreur lors du chargement des boissons.");
    }
}//    public static void chargerListeFrigos(JTable tabidfrigo) {
//    DefaultTableModel model = (DefaultTableModel) tabidfrigo.getModel();
//    model.setRowCount(0);
//
//    try {
//        Connection c = (Connection) connexionbd.seconnecter();
//        String sql = "SELECT DISTINCT id_frigo FROM `frigo_boissons` ORDER BY id_frigo DESC";
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//
//        int i = 1;
//        while (rs.next()) {
//            String id = rs.getString("id_frigo");
//            model.addRow(new Object[]{i++, id});
//        }
//
//        rs.close();
//        ps.close();
//        c.close();
//    } catch (Exception ex) {
//        Logger.getLogger(listefrigo.class.getName()).log(Level.SEVERE, null, ex);
//        JOptionPane.showMessageDialog(null, "Erreur lors du chargement de la liste des frigos.");
//    }
//}
     public static void chargerListeFrigos(JTable tabidfrigo) {
        DefaultTableModel model = (DefaultTableModel) tabidfrigo.getModel();
        model.setRowCount(0);

        try {
            Connection c = (Connection) connexionbd.seconnecter();
            String sql = "SELECT DISTINCT idFrigo FROM `frigo` ORDER BY idFrigo DESC";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int i = 1;
            while (rs.next()) {
                String id = rs.getString("idFrigo");
                model.addRow(new Object[]{i++, id});
            }

            rs.close();
            ps.close();
            c.close();
        } catch (Exception ex) {
            Logger.getLogger(listefrigo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement de la liste des frigos.");
        }
    }

     public static void actualiserListe(JTable tabidfrigo) {
        DefaultTableModel model = (DefaultTableModel) tabidfrigo.getModel();
        model.setRowCount(0); // Vide le tableau

        try {
            Connection c = (Connection) connexionbd.seconnecter();
            String sql = "SELECT DISTINCT idFrigo FROM `frigo` ORDER BY idFrigo DESC";
            PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int i = 1;
            while (rs.next()) {
                String id = rs.getString("idFrigo");
                model.addRow(new Object[]{i++, id});
            }

            rs.close();
            ps.close();
            c.close();
        } catch (Exception ex) {
            Logger.getLogger(listefrigo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erreur lors de l'actualisation de la liste.");
        }
    }

}
