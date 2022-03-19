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
public class tableclients {
    String id,identifiant_unique,id_client,nom_client,date_de_naissance_client,lieu_de_naissance_client,adress_mail_client,telephone_client,adress_client;

    public tableclients(String id,String identifiant_unique,String id_client,String nom_client,String date_de_naissance_client,String lieu_de_naissance_client,String adress_mail_client,String telephone_client,String adress_client) {
        this.id = id;
        this.identifiant_unique = identifiant_unique;
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.date_de_naissance_client = date_de_naissance_client;
        this.lieu_de_naissance_client = lieu_de_naissance_client;
        this.adress_mail_client = adress_mail_client;
        this.telephone_client = telephone_client;
        this.adress_client = adress_client;
        
    }

    public String getIdentifiant_unique() {
        return identifiant_unique;
    }

    public void setIdentifiant_unique(String identifiant_unique) {
        this.identifiant_unique = identifiant_unique;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDate_de_naissance_client() {
        return date_de_naissance_client;
    }

    public void setDate_de_naissance_client(String date_de_naissance_client) {
        this.date_de_naissance_client = date_de_naissance_client;
    }

    public String getLieu_de_naissance_client() {
        return lieu_de_naissance_client;
    }

    public void setLieu_de_naissance_client(String lieu_de_naissance_client) {
        this.lieu_de_naissance_client = lieu_de_naissance_client;
    }

    public String getAdress_mail_client() {
        return adress_mail_client;
    }

    public void setAdress_mail_client(String adress_mail_client) {
        this.adress_mail_client = adress_mail_client;
    }

    public String getTelephone_client() {
        return telephone_client;
    }

    public void setTelephone_client(String telephone_client) {
        this.telephone_client = telephone_client;
    }

    public String getAdress_client() {
        return adress_client;
    }

    public void setAdress_client(String adress_client) {
        this.adress_client = adress_client;
    }
    
}
