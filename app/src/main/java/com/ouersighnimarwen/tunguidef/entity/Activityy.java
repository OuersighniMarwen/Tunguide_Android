package com.ouersighnimarwen.tunguidef.entity;

public class Activityy
{
    private int id;
    private String name;
    private String adresse;
    private String image;
    private double prix;

    public Activityy(int id, String name, String adresse, String image, double prix) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
        this.image = image;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }


}

