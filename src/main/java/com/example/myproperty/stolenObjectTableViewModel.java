package com.example.myproperty;

public class stolenObjectTableViewModel{
    private String ownerName;
    private String marque;
    private String modele;
    private String adresseMac;

    public stolenObjectTableViewModel(String ownerName, String marque, String modele, String adresseMac) {
        this.ownerName = ownerName;
        this.marque = marque;
        this.modele = modele;
        this.adresseMac = adresseMac;
    }

    // Getters et setters
    public String getOwnerName() { return ownerName; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public String getAdresseMac() { return adresseMac; }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setAdresseMac(String adresseMac) {
        this.adresseMac = adresseMac;
    }
}
