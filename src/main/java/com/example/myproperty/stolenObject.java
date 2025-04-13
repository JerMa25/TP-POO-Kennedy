package com.example.myproperty;

public class stolenObject {
    private int id;
    private String marque;
    private String modele;
    private String processeur;
    private String systeme_exploitation;
    private String adresseMac;
    private int ram;
    private int stockage;
    private int vitesse_max;

    public stolenObject(){};

    public int getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public String getProcesseur() {
        return processeur;
    }

    public String getSysteme_exploitation() {
        return systeme_exploitation;
    }

    public String getAdresseMac() {
        return adresseMac;
    }

    public int getRam() {
        return ram;
    }

    public int getStockage() {
        return stockage;
    }

    public int getVitesse_max() {
        return vitesse_max;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setProcesseur(String processeur) {
        this.processeur = processeur;
    }

    public void setSysteme_exploitation(String systeme_exploitation) {
        this.systeme_exploitation = systeme_exploitation;
    }

    public void setAdresseMac(String addresseMac) {
        this.adresseMac = addresseMac;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public void setStockage(int stockage) {
        this.stockage = stockage;
    }

    public void setVitesse_max(int vitesse_max) {
        this.vitesse_max = vitesse_max;
    }
}

