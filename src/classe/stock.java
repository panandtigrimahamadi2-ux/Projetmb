/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MHD
 */
public class stock {
    private static java.sql.Connection c=null;
    private static Statement st=null;
    private static ResultSet rs=null;
    
    public static void validerApprovisionnement(String boisson, int nombre) throws SQLException {
    try {
        Connection c = (Connection) connexionbd.seconnecter();

        // Vérifier si la boisson existe déjà dans le stock
        String checkSql = "SELECT * FROM stock WHERE boisson = ?";
        PreparedStatement checkPs = (PreparedStatement) c.prepareStatement(checkSql);
        checkPs.setString(1, boisson);
        ResultSet rs = checkPs.executeQuery();

        if (rs.next()) {
            // Si elle existe, on met à jour le nombre
            int ancienNombre = rs.getInt("nombre");
            int nouveauNombre = ancienNombre + nombre;

            String updateSql = "UPDATE stock SET nombre = ? WHERE boisson = ?";
            PreparedStatement updatePs = (PreparedStatement) c.prepareStatement(updateSql);
            updatePs.setInt(1, nouveauNombre);
            updatePs.setString(2, boisson);
            updatePs.executeUpdate();

            JOptionPane.showMessageDialog(null, "Stock mis à jour avec succès !");
            updatePs.close();
        } else {
            // Sinon, on insère une nouvelle ligne
            String insertSql = "INSERT INTO stock (boisson, nombre) VALUES (?, ?)";
            PreparedStatement insertPs = (PreparedStatement) c.prepareStatement(insertSql);
            insertPs.setString(1, boisson);
            insertPs.setInt(2, nombre);
            insertPs.executeUpdate();

            JOptionPane.showMessageDialog(null, "Approvisionnement enregistré !");
            insertPs.close();
        }

        // Nettoyage
        rs.close();
        checkPs.close();
        c.close();

    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(stock.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    public static void actualiserStock(DefaultTableModel tm) {
    try {
        Connection c = (Connection) connexionbd.seconnecter();
        String sql = "SELECT * FROM stock"; // ta table stock avec colonnes boisson et nombre
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
        Logger.getLogger(stock.class.getName()).log(Level.SEVERE, null, ex);
    }
}

}
