package com.nokhba.nokhbahmd.Model;

import java.util.Map;

public class Help {
    private String nom,prenom,phone,covid_you,Fcovid,service,desc,date;
    private int numCovide;
    private Map<String,Double> localisation;

    public Help(){

    }


    public Help(String nom, String prenom, String phone, String covid_you, String fcovid, String service, String desc, int numCovide, String date, Map<String,Double> loc) {
        this.nom = nom;
        this.prenom = prenom;
        this.phone = phone;
        this.covid_you = covid_you;
        this.Fcovid = fcovid;
        this.service = service;
        this.desc = desc;
        this.numCovide = numCovide;
        this.date = date;
        this.localisation=loc;
    }

    public Map<String, Double> getLocalisation() {
        return localisation;
    }

    public void setLocalisation(Map<String, Double> localisation) {
        this.localisation = localisation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getCovid_you() {
        return covid_you;
    }

    public void setCovid_you(String covid_you) {
        this.covid_you = covid_you;
    }

    public String getFcovid() {
        return Fcovid;
    }

    public void setFcovid(String fcovid) {
        Fcovid = fcovid;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNumCovide() {
        return numCovide;
    }

    public void setNumCovide(int numCovide) {
        this.numCovide = numCovide;
    }
}
