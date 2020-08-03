package com.nokhba.nokhbahmd.Model;

public class Valunteer {
    private String nom,prenom,phone,service,Token;
    private String date;

    public Valunteer() {
    }

    public Valunteer(String nom, String prenom, String phone, String service, String date,String Token) {
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.service = service;
        this.date = date;
        this.Token =Token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
