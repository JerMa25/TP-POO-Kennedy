package com.example.myproperty;

public class stolenObjectTableViewModel{
    private String objID;
    private String ownerName;
    private String marque;
    private String modele;
    private String adresseMac;

    public stolenObjectTableViewModel(String objID, String ownerName, String marque, String modele, String adresseMac) {
        this.objID = objID;
        this.ownerName = ownerName;
        this.marque = marque;
        this.modele = modele;
        this.adresseMac = adresseMac;
    }

    // Getters et setters

    public String getObjID() { return objID; }
    public String getOwnerName() { return ownerName; }
    public String getMarque() { return marque; }
    public String getModele() { return modele; }
    public String getAdresseMac() { return adresseMac; }

    public void setObjID(String objID) {
        this.objID = objID;
    }

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
