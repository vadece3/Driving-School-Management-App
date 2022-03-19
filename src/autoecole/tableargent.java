/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoecole;

/**
 *
 * @author KAMDEM VADECE
 */
public class tableargent {
     String id_client,nom_client,argent_percu,date_de_reception,reste_a_payer;

    public tableargent(String id_client,String nom_client,String argent_percu,String date_de_reception,String reste_a_payer) {
       
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.argent_percu = argent_percu;
        this.date_de_reception = date_de_reception;
        this.reste_a_payer = reste_a_payer;
    
}

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getArgent_percu() {
        return argent_percu;
    }

    public void setArgent_percu(String argent_percu) {
        this.argent_percu = argent_percu;
    }

    public String getDate_de_reception() {
        return date_de_reception;
    }

    public void setDate_de_reception(String date_de_reception) {
        this.date_de_reception = date_de_reception;
    }

    public String getReste_a_payer() {
        return reste_a_payer;
    }

    public void setReste_a_payer(String reste_a_payer) {
        this.reste_a_payer = reste_a_payer;
    }
    
    
}