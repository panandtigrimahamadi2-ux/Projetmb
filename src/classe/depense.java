/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class depense {

    // Ajouter une nouvelle dépense liée à la caisse ouverte
    public static void ajouter(String motif, double montant, String date) throws SQLException, ClassNotFoundException {
        Connection c = (Connection) connexionbd.seconnecter();

        // Récupérer l'ID de la caisse ouverte
        String idCaisse = caisse.getIdCaisseOuverte();
        if (idCaisse == null) {
            throw new SQLException("Aucune caisse ouverte. Impossible d'ajouter une dépense.");
        }

        PreparedStatement ps = (PreparedStatement) c.prepareStatement(
            "INSERT INTO depense (motif, montant, date_depense, id_caisse) VALUES (?, ?, ?, ?)"
        );
        ps.setString(1, motif);
        ps.setDouble(2, montant);
        ps.setString(3, date);
        ps.setString(4, idCaisse);
        ps.executeUpdate();
        ps.close();

        // Mettre à jour le montant physique de la caisse
        double encaisse = caisse.calculerMontantEncaisse();
        double depenses = caisse.calculerDepenses();
        double physique = encaisse - depenses;

        PreparedStatement psUpdate = (PreparedStatement) c.prepareStatement(
            "UPDATE caisse SET montant_physique = ? WHERE id_caisse = ?"
        );
        psUpdate.setDouble(1, physique);
        psUpdate.setString(2, idCaisse);
        psUpdate.executeUpdate();
        psUpdate.close();

        c.close();
    }

    // Calculer le total des dépenses de la caisse ouverte
    public static double calculerTotal() throws SQLException, ClassNotFoundException {
        double total = 0;
        String idCaisse = caisse.getIdCaisseOuverte();
        if (idCaisse == null) return 0;

        Connection c = (Connection) connexionbd.seconnecter();
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(
            "SELECT SUM(montant) FROM depense WHERE id_caisse = ?"
        );
        ps.setString(1, idCaisse);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getDouble(1);
        }
        rs.close(); ps.close(); c.close();
        return total;
    }

    // Récupérer les dernières dépenses de la caisse ouverte
    public static ResultSet getDepensesRecentes() throws SQLException, ClassNotFoundException {
    String idCaisse = caisse.getIdCaisseOuverte();
    if (idCaisse == null) return null;

    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
        "SELECT motif, montant, date_depense FROM depense WHERE id_caisse = ? ORDER BY date_depense DESC LIMIT 20"
    );
    ps.setString(1, idCaisse);
    return ps.executeQuery();
}
//    public static ResultSet getDepensesRecentes() throws SQLException, ClassNotFoundException {
//        String idCaisse = caisse.getIdCaisseOuverte();
//        if (idCaisse == null) return null;
//
//        Connection c = (Connection) connexionbd.seconnecter();
//        PreparedStatement ps = (PreparedStatement) c.prepareStatement(
//            "SELECT motif, montant, date_depense FROM depense WHERE id_caisse = ? ORDER BY date_depense DESC LIMIT 20"
//        );
//        ps.setString(1, idCaisse);
//        return ps.executeQuery();
//    }

    // Récupérer les dépenses archivées
    public static ResultSet getDepensesArchive() throws SQLException, ClassNotFoundException {
        Connection c = (Connection) connexionbd.seconnecter();
        Statement st = (Statement) c.createStatement();
        return st.executeQuery(
            "SELECT motif, montant, date_depense, id_caisse, date_archive FROM depense_archive ORDER BY date_archive DESC LIMIT 50"
        );
    }
    
    public static void mettreAJourCaisse(String idCaisse) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();

    // Récupérer montant encaissé
    PreparedStatement psEncaisse = (PreparedStatement) c.prepareStatement(
        "SELECT montant_encaisse FROM caisse WHERE id_caisse = ?"
    );
    psEncaisse.setString(1, idCaisse);
    ResultSet rsEncaisse = psEncaisse.executeQuery();
    double montantEncaisse = 0;
    if (rsEncaisse.next()) {
        montantEncaisse = rsEncaisse.getDouble("montant_encaisse");
    }
    rsEncaisse.close();
    psEncaisse.close();

    // Récupérer total des dépenses
    double totalDepenses = calculerTotal();

    // Mettre à jour montant physique
    PreparedStatement psUpdate = (PreparedStatement) c.prepareStatement(
        "UPDATE caisse SET montant_physique = ? WHERE id_caisse = ?"
    );
    psUpdate.setDouble(1, montantEncaisse - totalDepenses);
    psUpdate.setString(2, idCaisse);
    psUpdate.executeUpdate();

    psUpdate.close();
    c.close();
}
}
