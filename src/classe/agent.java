
package classe;

import java.sql.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class agent {

    // Méthode utilitaire pour sécuriser la récupération des dates
    private static Object safeDate(ResultSet rs, String column) {
        try {
            String dateStr = rs.getString(column);
            if (dateStr != null && !dateStr.equals("0000-00-00")) {
                return java.sql.Date.valueOf(dateStr);
            }
        } catch (Exception e) {
            // ignorer l'erreur
        }
        return null; // ou "N/A" si tu veux afficher du texte
    }

    public static void enregistrer(String nom, Date dateNaissance, String profil, String typeSalaire,
                                   double salaire, double prixParBouteille) {
        try {
            Connection c = connexionbd.seconnecter();
            String sql = "INSERT INTO agent (nom, date_naissance, profil, type_salaire, salaire, prix_par_bouteille) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setDate(2, dateNaissance);
            ps.setString(3, profil);
            ps.setString(4, typeSalaire);
            ps.setDouble(5, salaire);
            ps.setDouble(6, prixParBouteille);
            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            Logger.getLogger(agent.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static Vector<Vector<Object>> getListeAgents() throws SQLException {
        Vector<Vector<Object>> data = new Vector<>();
        try {
            Connection c = connexionbd.seconnecter();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT nom, date_naissance, profil, type_salaire, salaire, prix_par_bouteille, nb_bouteilles FROM agent"
            );

            while (rs.next()) {
                String type = rs.getString("type_salaire");
                double salaireBrut;
                String nbBouteillesAffiche;

                if ("par bouteille".equalsIgnoreCase(type)) {
                    double prix = rs.getDouble("prix_par_bouteille");
                    int nb = rs.getInt("nb_bouteilles");
                    salaireBrut = prix * nb;
                    nbBouteillesAffiche = String.valueOf(nb);
                } else {
                    salaireBrut = rs.getDouble("salaire");
                    nbBouteillesAffiche = "-";
                }

                Vector<Object> ligne = new Vector<>();
                ligne.add(rs.getString("nom"));
                ligne.add(safeDate(rs, "date_naissance")); // ⚡ utilisation sécurisée
                ligne.add(rs.getString("profil"));
                ligne.add(type);
                ligne.add(nbBouteillesAffiche);
                ligne.add(salaireBrut);

                data.add(ligne);
            }

            rs.close();
            st.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
public static void ajouterBouteilles(String nom, int nbBouteilles) {
    try {
        Connection c = connexionbd.seconnecter();
        String sql = "UPDATE agent SET nb_bouteilles = nb_bouteilles + ? WHERE nom = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, nbBouteilles);
        ps.setString(2, nom);
        ps.executeUpdate();
        ps.close();
        c.close();
    } catch (Exception e) {
        Logger.getLogger(agent.class.getName()).log(Level.SEVERE, null, e);
    }
}


    public static void modifier(int id, String nom, Date dateNaissance, String profil, String typeSalaire,
                                double salaire, double prixParBouteille) {
        try {
            Connection c = connexionbd.seconnecter();
            String sql = "UPDATE agent SET nom=?, date_naissance=?, profil=?, type_salaire=?, salaire=?, prix_par_bouteille=? WHERE id=?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, nom);
            ps.setDate(2, dateNaissance);
            ps.setString(3, profil);
            ps.setString(4, typeSalaire);
            ps.setDouble(5, salaire);
            ps.setDouble(6, prixParBouteille);
            ps.setInt(7, id);
            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            Logger.getLogger(agent.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void supprimer(int id) {
        try {
            Connection c = connexionbd.seconnecter();
            PreparedStatement ps = c.prepareStatement("DELETE FROM agent WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            Logger.getLogger(agent.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}

