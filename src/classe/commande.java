/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static m.b.formecommande.tabcommande;

/**
 *
 * @author MHD
 */
public class commande {
    

    // Générer un ID unique basé sur date/heure
   public static String genererIdCommande() {
    // Format : jour + mois + année + heure + minute + seconde + millisecondes
    String timestamp = new java.text.SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new java.util.Date());

    // Ajouter un petit suffixe aléatoire pour éviter les doublons même si deux commandes arrivent à la même milliseconde
    String randomSuffix = java.util.UUID.randomUUID().toString().substring(0, 4);

    return timestamp + randomSuffix;
}

    // Vérifier si une caisse est ouverte
   public static String getIdCaisseOuverte() throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT id_caisse FROM caisse WHERE statut = 'OUVERTE' LIMIT 1");
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getString("id_caisse");
    }
    rs.close();
    ps.close();
    c.close();
    return null;
}

   public static int getIdAgent(String nomServeur) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
        "SELECT id FROM agent WHERE nom = ?"
    );
    ps.setString(1, nomServeur);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return rs.getInt("id");
    }
    return -1; // ⚡ si aucun agent trouvé
}


    // Ajouter une commande
    public static void ajouterCommande(String idCommande, int idAgent, String idCaisse, String serveur, boolean payer, double total) throws SQLException, ClassNotFoundException {
        Connection c = (Connection) connexionbd.seconnecter();
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(
            "INSERT INTO commande (id_commande, id_agent, id_caisse, date_commande, statut, serveur, total) VALUES (?, ?, ?, NOW(), ?, ?, ?)"
        );
        ps.setString(1, idCommande);
        ps.setInt(2, idAgent);
        ps.setString(3, idCaisse);
        ps.setString(4, payer ? "PAYÉ" : "EN COURS");
        ps.setString(5, serveur);
        ps.setDouble(6, total);
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    // Ajouter un détail de commande
    public static void ajouterDetail(String idCommande, int idBoisson, double prix, int quantite) throws SQLException, ClassNotFoundException {
        Connection c = (Connection) connexionbd.seconnecter();
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(
            "INSERT INTO commande_details (id_commande, id_boisson, prix, quantite) VALUES (?, ?, ?, ?)"
        );
        ps.setString(1, idCommande);
        ps.setInt(2, idBoisson);
        ps.setDouble(3, prix);
        ps.setInt(4, quantite);
        ps.executeUpdate();
        ps.close();
        c.close();
    }
 public static void chargerFrigos(JComboBox<String> cmbfrigo) throws SQLException, ClassNotFoundException {
        Connection c = (Connection) connexionbd.seconnecter();
        PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT idFrigo FROM frigo");
        ResultSet rs = ps.executeQuery();
        cmbfrigo.removeAllItems();
        while (rs.next()) {
            cmbfrigo.addItem(rs.getString("idFrigo"));
        }
        rs.close(); ps.close(); c.close();
    }

   public static void chargerBoissons(JComboBox<String> cmbboisson, String idFrigo) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
        "SELECT b.nom FROM boisson b JOIN boisson_frigo bf ON b.id = bf.id_boisson WHERE bf.id_frigo = ? AND bf.stock > 0"
    );
    ps.setString(1, idFrigo);
    ResultSet rs = ps.executeQuery();

    cmbboisson.removeAllItems();
    while (rs.next()) {
        cmbboisson.addItem(rs.getString("nom"));
    }

    rs.close();
    ps.close();
    c.close();
}
    public static void chargerServeurs(JComboBox<String> cmbserveur) throws SQLException, ClassNotFoundException {
        Connection c = (Connection) connexionbd.seconnecter();
        PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT nom FROM agent WHERE profil = 'Serveur(se)'");
        ResultSet rs = ps.executeQuery();
        cmbserveur.removeAllItems();
        while (rs.next()) {
            cmbserveur.addItem(rs.getString("nom"));
        }
        rs.close(); ps.close(); c.close();
    }
public static double getPrixBoisson(String nomBoisson) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT prix_unitaire FROM boisson WHERE nom = ?");
    ps.setString(1, nomBoisson);
    ResultSet rs = ps.executeQuery();
    double prix = 0;
    if (rs.next()) {
        prix = rs.getDouble("prix_unitaire");
    }
    rs.close();
    ps.close();
    c.close();
    return prix;
}

public static ResultSet getFrigos() throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    Statement st = (Statement) c.createStatement();
    return st.executeQuery("SELECT id_frigo, nom_frigo FROM frigo");
}

public static ResultSet getBoissonsParFrigo(int idFrigo) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT id, nom, prix_unitaire, stock FROM boisson WHERE id_frigo = ?");
    ps.setInt(1, idFrigo);
    return ps.executeQuery();
}

// Récupérer les boissons d’un frigo donné
    public static ResultSet getBoissonsParFrigo(String idFrigo) throws Exception {
        Connection c = (Connection) connexionbd.seconnecter();
        String sql = "SELECT b.nom, b.prix_unitaire, bf.stock " +
                     "FROM boisson b " +
                     "JOIN boisson_frigo bf ON b.id = bf.id_boisson " +
                     "WHERE bf.id_frigo = ?";
        PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
        ps.setString(1, idFrigo);
        return ps.executeQuery(); // ⚠️ Attention : ne pas fermer ici, le ResultSet doit être lu avant
    }
public static void enregistrerCommande(String idCommande, int idAgent, String idCaisse, 
                                       String serveur, boolean payer, double total, 
                                       DefaultTableModel model) throws ClassNotFoundException, SQLException {
    Connection c = (Connection) connexionbd.seconnecter();

    // Insertion commande
    PreparedStatement psCommande = (PreparedStatement) c.prepareStatement(
        "INSERT INTO commande (id_commande, id_agent, id_caisse, date_commande, statut, serveur, total) VALUES (?, ?, ?, NOW(), ?, ?, ?)"
    );
    psCommande.setString(1, idCommande);
    psCommande.setInt(2, idAgent);
    psCommande.setString(3, idCaisse);
    psCommande.setString(4, payer ? "PAYÉ" : "EN COURS");
    psCommande.setString(5, serveur);
    psCommande.setDouble(6, total);
    psCommande.executeUpdate();
    psCommande.close();

    // Détails
    for (int i = 0; i < model.getRowCount(); i++) {
        String boisson = model.getValueAt(i, 0).toString();
        double prix = Double.parseDouble(model.getValueAt(i, 1).toString());
        int quantite = Integer.parseInt(model.getValueAt(i, 2).toString());

        PreparedStatement psBoisson = (PreparedStatement) c.prepareStatement("SELECT id FROM boisson WHERE nom = ?");
        psBoisson.setString(1, boisson);
        ResultSet rsBoisson = psBoisson.executeQuery();
        int idBoisson = -1;
        if (rsBoisson.next()) idBoisson = rsBoisson.getInt("id");
        rsBoisson.close(); psBoisson.close();

        PreparedStatement psDetail = (PreparedStatement) c.prepareStatement(
            "INSERT INTO commande_details (id_commande, id_boisson, prix, quantite) VALUES (?, ?, ?, ?)"
        );
        psDetail.setString(1, idCommande);
        psDetail.setInt(2, idBoisson);
        psDetail.setDouble(3, prix);
        psDetail.setInt(4, quantite);
        psDetail.executeUpdate();
        psDetail.close();
    }

    // Mise à jour caisse si payé
    if (payer) {
        PreparedStatement psCaisse = (PreparedStatement) c.prepareStatement(
            "UPDATE caisse SET montant_encaisse = montant_encaisse + ? WHERE id_caisse = ?"
        );
        psCaisse.setDouble(1, total);
        psCaisse.setString(2, idCaisse);
        psCaisse.executeUpdate();
        psCaisse.close();
    }

    c.close();
}

// Ancienne version (sans avoir)
public static String genererTicket(JTable tabcommande, JComboBox<String> cmbserveur, 
                                   String idCommande, String totalStr) {
    return genererTicket(tabcommande, cmbserveur, idCommande, totalStr, null);
}

// Nouvelle version (avec avoir)
public static String genererTicket(JTable tabcommande, JComboBox<String> cmbserveur, 
                                   String idCommande, String totalStr, String avoirStr) {
    StringBuilder ticket = new StringBuilder();
    String serveur = (cmbserveur.getSelectedItem() != null) ? cmbserveur.getSelectedItem().toString() : "Serveur inconnu";
    double total = Double.parseDouble(totalStr.replace("FCFA", "").trim());

    ticket.append("       *** M&B Café ***\n");
    ticket.append("   Bobo-Dioulasso, Burkina Faso\n");
    ticket.append("Tel: +226 XX XX XX XX\n");
    ticket.append("----------------------------\n");
    ticket.append("Commande: ").append(idCommande).append("\n");
    ticket.append("Serveur : ").append(serveur).append("\n");
    ticket.append("Date    : ").append(new java.sql.Timestamp(System.currentTimeMillis())).append("\n");
    ticket.append("----------------------------\n");
    ticket.append(String.format("%-12s %5s %8s\n", "Boisson", "Qté", "Sous-total"));
    ticket.append("----------------------------\n");

    for (int i = 0; i < tabcommande.getRowCount(); i++) {
        String boisson = tabcommande.getValueAt(i, 0).toString();
        double prix = Double.parseDouble(tabcommande.getValueAt(i, 1).toString());
        int quantite = Integer.parseInt(tabcommande.getValueAt(i, 2).toString());
        double sousTotal = prix * quantite;
        ticket.append(String.format("%-12s %5d %8.2f\n", boisson, quantite, sousTotal));
    }

    ticket.append("----------------------------\n");
    ticket.append(String.format("%-12s %13.2f FCFA\n", "TOTAL", total));

    // ⚡ Champ Avoir (optionnel)
    if (avoirStr != null && !avoirStr.trim().isEmpty()) {
        ticket.append(String.format("%-12s %13s\n", "AVOIR", avoirStr.trim()));
    }

    ticket.append("****************************\n");
    ticket.append("Merci pour votre visite !\n");

    return ticket.toString();
}


// Charger toutes les commandes dans un JTable
public static void chargerListeCommandes(javax.swing.JTable tablcommande) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    Statement st = (Statement) c.createStatement();
    ResultSet rs = st.executeQuery("SELECT id_commande, serveur, date_commande, statut, total FROM commande");

    DefaultTableModel model = (DefaultTableModel) tablcommande.getModel();
    model.setRowCount(0);

    while (rs.next()) {
        Object[] ligne = {
            rs.getString("id_commande"),
            rs.getString("statut"),
            rs.getString("serveur"),
            rs.getTimestamp("date_commande"),
            rs.getDouble("total")
        };
        model.addRow(ligne);
    }

    rs.close();
    st.close();
    c.close();
}


// Mettre à jour le statut d'une commande existante
public static void updateStatutCommande(String idCommande, boolean payer) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    String nouveauStatut = payer ? "PAYÉ" : "EN COURS";

    PreparedStatement ps = (PreparedStatement) c.prepareStatement("UPDATE commande SET statut=? WHERE id_commande=?");
    ps.setString(1, nouveauStatut);
    ps.setString(2, idCommande);
    ps.executeUpdate();
    ps.close();

    if (payer) {
        double total = getTotalCommande(idCommande);
        String idCaisse = getIdCaisseOuverte();
        PreparedStatement psCaisse = (PreparedStatement) c.prepareStatement(
            "UPDATE caisse SET montant_encaisse = montant_encaisse + ? WHERE id_caisse = ?"
        );
        psCaisse.setDouble(1, total);
        psCaisse.setString(2, idCaisse);
        psCaisse.executeUpdate();
        psCaisse.close();
    }

    c.close();
}


public static boolean commandeExiste(String idCommande) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT id_commande FROM commande WHERE id_commande=?");
    ps.setString(1, idCommande);
    ResultSet rs = ps.executeQuery();
    boolean existe = rs.next();
    rs.close(); ps.close(); c.close();
    return existe;
}

// Méthode combinée : mise à jour ou insertion
public static void validerOuInsererCommande(String idCommande, int idAgent, String idCaisse,
                                            String serveur, boolean payer, double total,
                                            javax.swing.table.DefaultTableModel model) throws SQLException, ClassNotFoundException {
    if (commandeExiste(idCommande)) {
        // ⚡ Mise à jour du statut
        updateStatutCommande(idCommande, payer);
    } else {
        // ⚡ Nouvelle insertion
        enregistrerCommande(idCommande, idAgent, idCaisse, serveur, payer, total, model);
    }
}
// Récupérer le total d'une commande existante
public static double getTotalCommande(String idCommande) throws SQLException, ClassNotFoundException {
    Connection c = (Connection) connexionbd.seconnecter();
    PreparedStatement ps = (PreparedStatement) c.prepareStatement(
        "SELECT total FROM commande WHERE id_commande = ?"
    );
    ps.setString(1, idCommande);
    ResultSet rs = ps.executeQuery();
    double total = 0;
    if (rs.next()) {
        total = rs.getDouble("total");
    }
    rs.close();
    ps.close();
    c.close();
    return total;
}
}
