/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.text.MessageFormat;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 *
 * @author MHD
 */
public class statistique {
    
    public static void chargerStatistiquesCaisse(JTable tabStatistiques, String idCaisse) {
    try {
        Connection c = (Connection) connexionbd.seconnecter();
        String sql = "SELECT b.nom, SUM(cd.quantite) AS total_bouteilles " +
                     "FROM commande_details cd " +
                     "JOIN commande co ON co.id_commande = cd.id_commande " +
                     "JOIN boisson b ON b.id = cd.id_boisson " +
                     "WHERE co.id_caisse = ? " +
                     "GROUP BY b.nom ORDER BY total_bouteilles DESC";
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
        ps.setString(1, idCaisse);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = (DefaultTableModel) tabStatistiques.getModel();
        model.setRowCount(0);

        int compteur = 1;
        while (rs.next()) {
            model.addRow(new Object[]{
                compteur++, 
                rs.getString("nom"), 
                rs.getInt("total_bouteilles")
            });
        }

        rs.close();
        ps.close();
        c.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
//    public static void chargerStatistiquesCaisse(JTable tabStatistiques, String idCaisse) {
//    try {
//        Connection c = (Connection) connexionbd.seconnecter();
//        String sql = "SELECT b.nom, SUM(cd.quantite) AS total_bouteilles " +
//                     "FROM commande_details cd " +
//                     "JOIN commande co ON co.id = cd.id_commande " +
//                     "JOIN boisson b ON b.id = cd.id_boisson " +
//                     "WHERE co.id_caisse = ? " +
//                     "GROUP BY b.nom ORDER BY total_bouteilles DESC";
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
//        ps.setString(1, idCaisse);
//        ResultSet rs = ps.executeQuery();
//
//        DefaultTableModel model = (DefaultTableModel) tabStatistiques.getModel();
//        model.setRowCount(0);
//
//        int compteur = 1;
//        while (rs.next()) {
//            model.addRow(new Object[]{compteur++, rs.getString("nom"), rs.getInt("total_bouteilles")});
//        }
//
//        rs.close();
//        ps.close();
//        c.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
    
    public static void viderStatistiques(JTable tabStatistiques) {
    DefaultTableModel model = (DefaultTableModel) tabStatistiques.getModel();
    model.setRowCount(0);
}
    public static void imprimerStatistiques(JTable tabStatistiques) {
    try {
        tabStatistiques.print(JTable.PrintMode.FIT_WIDTH,
                              new MessageFormat("Statistiques de la caisse"),
                              new MessageFormat("Page - {0}"));
    } catch (PrinterException e) {
        e.printStackTrace();
    }
}
    public static void exporterStatistiquesCSV(JTable tabStatistiques, String nomFichier) {
    try {
        DefaultTableModel model = (DefaultTableModel) tabStatistiques.getModel();
        FileWriter fw = new FileWriter(nomFichier);

        // En-têtes
        for (int i = 0; i < model.getColumnCount(); i++) {
            fw.write(model.getColumnName(i) + (i < model.getColumnCount()-1 ? ";" : ""));
        }
        fw.write("\n");

        // Lignes
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                fw.write(model.getValueAt(i, j).toString() + (j < model.getColumnCount()-1 ? ";" : ""));
            }
            fw.write("\n");
        }

        fw.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
