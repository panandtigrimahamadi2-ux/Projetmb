/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.util.Date;
import java.sql.*;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfWriter;
public class caisse {

    // Vérifier si une caisse est déjà ouverte (date_fermeture = NULL)
   public static boolean existeCaisseOuverte() throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    Statement st = (Statement) c.createStatement();
    ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM caisse WHERE statut = 'OUVERTE'");
    boolean ouverte = false;
    if (rs.next()) {
        ouverte = rs.getInt(1) > 0;
    }
    rs.close(); st.close(); c.close();
    return ouverte;
}


  // Récupérer l'ID de la caisse ouverte
public static String getIdCaisseOuverte() throws SQLException, ClassNotFoundException {
    String idCaisse = null;
    Connection c = (Connection) connexionbd.seconnecter();
    Statement st = (Statement) c.createStatement();
    ResultSet rs = st.executeQuery("SELECT id_caisse FROM caisse WHERE statut = 'OUVERTE' LIMIT 1");
    if (rs.next()) {
        idCaisse = rs.getString("id_caisse");
    }
    rs.close(); st.close(); c.close();
    return idCaisse;
}

// Ajouter une nouvelle caisse (ouverte par défaut)
// Ajouter une nouvelle caisse (ouverte par défaut)
// Ajouter une nouvelle caisse (ouverte par défaut)
public static void ajouter(String id, String nom, String dateCreation) throws SQLException, ClassNotFoundException {
    if (existeCaisseOuverte()) {
        throw new SQLException("Impossible de créer une nouvelle caisse : une caisse est déjà ouverte.");
    }

    Connection c = (Connection) connexionbd.seconnecter();

    // 1️⃣ Archiver toutes les dépenses existantes
    Statement st = (Statement) c.createStatement();
    st.executeUpdate(
        "INSERT INTO depense_archive (motif, montant, date_depense, id_caisse, date_archive) " +
        "SELECT motif, montant, date_depense, '" + id + "', NOW() FROM depense"
    );

    // 2️⃣ Vider la table depense
    st.executeUpdate("DELETE FROM depense");
    st.close();

    // 3️⃣ Créer la nouvelle caisse
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
        "INSERT INTO caisse (id_caisse, nom_caisse, date_creation, montant_encaisse, montant_physique, statut) VALUES (?, ?, ?, 0, 0, 'OUVERTE')"
    );
    ps.setString(1, id);
    ps.setString(2, nom);
    ps.setString(3, dateCreation);
    ps.executeUpdate();
    ps.close();

    c.close();
}
//public static void ajouter(String id, String nom, String dateCreation) throws SQLException, ClassNotFoundException {
//    if (existeCaisseOuverte()) {
//        throw new SQLException("Impossible de créer une nouvelle caisse : une caisse est déjà ouverte.");
//    }
//    Connection c = (Connection) connexionbd.seconnecter();
//    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
//        "INSERT INTO caisse (id_caisse, nom_caisse, date_creation, montant_encaisse, montant_physique, statut) VALUES (?, ?, ?, 0, 0, 'OUVERTE')"
//    );
//    ps.setString(1, id);
//    ps.setString(2, nom);
//    ps.setString(3, dateCreation);
//    ps.executeUpdate();
//    ps.close(); c.close();
//}


    // Calculer le montant encaissé (somme des totaux des commandes)
//    public static double calculerMontantEncaisse() throws SQLException, ClassNotFoundException {
//        double total = 0;
//        Connection c = (Connection) connexionbd.seconnecter();
//        Statement st = (Statement) c.createStatement();
//        ResultSet rs = st.executeQuery("SELECT SUM(total) FROM commande");
//        if (rs.next()) {
//            total = rs.getDouble(1);
//        }
//        rs.close();
//        st.close();
//        c.close();
//        return total;
//    }

    // Calculer les dépenses
//    public static double calculerDepenses() throws SQLException, ClassNotFoundException {
//        double total = 0;
//        Connection c = (Connection) connexionbd.seconnecter();
//        Statement st = (Statement) c.createStatement();
//        ResultSet rs = st.executeQuery("SELECT SUM(montant) FROM depense"); // ⚠️ Vérifie que ta table depense a bien une colonne 'montant'
//        if (rs.next()) {
//            total = rs.getDouble(1);
//        }
//        rs.close();
//        st.close();
//        c.close();
//        return total;
//    }

   // Fermer une caisse
public static void fermer(String idCaisse) throws SQLException, ClassNotFoundException {
    double encaisse = calculerMontantEncaisse();
    double depenses = calculerDepenses();
    double physique = encaisse - depenses;

    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
        "UPDATE caisse SET montant_encaisse = ?, montant_physique = ?, date_fermeture = ?, statut = 'FERMEE' WHERE id_caisse = ?"
    );
    ps.setDouble(1, encaisse);
    ps.setDouble(2, physique);
    ps.setDate(3, new java.sql.Date(new Date().getTime()));
    ps.setString(4, idCaisse);
    ps.executeUpdate();
    ps.close(); c.close();
}

    // Récupérer la dernière caisse créée
    public static ResultSet getDerniereCaisse() throws SQLException, ClassNotFoundException {
        Connection c = (Connection) connexionbd.seconnecter();
        Statement st = (Statement) c.createStatement();
        return st.executeQuery("SELECT * FROM caisse ORDER BY date_creation DESC LIMIT 1");
    }
    
    // Générer un PDF lors de la fermeture de la caisse
    
    public static void genererPDF(String idCaisse, String nomCaisse, double montantEncaisse, double montantPhysique) {
    try {
        Document document = new Document();
        String fileName = "Caisse_" + nomCaisse + "_" + idCaisse + ".pdf";
        PdfWriter.getInstance(document, new java.io.FileOutputStream(fileName));

        document.open();

        // Titre
        Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titre = new Paragraph("Rapport de fermeture de caisse", fontTitre);
        titre.setAlignment(Element.ALIGN_CENTER);
        document.add(titre);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Nom de la caisse : " + nomCaisse));
        document.add(new Paragraph("ID caisse : " + idCaisse));
        document.add(new Paragraph("Montant encaissé : " + montantEncaisse + " FCFA"));
        document.add(new Paragraph("Montant physique : " + montantPhysique + " FCFA"));
        document.add(new Paragraph("Date de fermeture : " + new java.util.Date()));

        document.close();

        System.out.println("PDF généré : " + fileName);

    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    // Calculer le montant encaissé (somme des commandes payées pour la caisse ouverte)
public static double calculerMontantEncaisse() throws SQLException, ClassNotFoundException {
    double total = 0;
    String idCaisse = getIdCaisseOuverte();
    if (idCaisse == null) return 0;

    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
        "SELECT SUM(total) FROM commande WHERE id_caisse = ? AND statut = 'PAYÉ'"
    );
    ps.setString(1, idCaisse);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        total = rs.getDouble(1);
    }
    rs.close(); ps.close(); c.close();
    return total;
}

// Calculer les dépenses (uniquement celles de la caisse ouverte)
public static double calculerDepenses() throws SQLException, ClassNotFoundException {
    double total = 0;
    String idCaisse = getIdCaisseOuverte();
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
}



