/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classe;

/**
 *
 * @author MHD
 */
public class BoissonItem {
   private int id;
    private String nom;
    private int stock;

    public BoissonItem(int id, String nom, int stock) {
        this.id = id;
        this.nom = nom;
        this.stock = stock;
    }

    // ⚡ Les getters indispensables
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return nom; // affichage dans la combo
    }




//     private int id;      // ⚡ identifiant exact de la boisson (clé primaire en BD)
//    private String nom;
//    private int stock;
//
//    public BoissonItem(int id, String nom, int stock) {
//        this.id = id;
//        this.nom = nom;
//        this.stock = stock;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getNom() {
//        return nom;
//    }
//
//    public int getStock() {
//        return stock;
//    }
//
//    @Override
//    public String toString() {
//        return nom; // affichage du nom dans la combo
//    }

}
