package com.ouersighnimarwen.tunguidef.entity;

public class Restaurant
{
    private int id;
    private String name;
    private String adresse;
    private String image;
    private int stars;

    public Restaurant(int id, String name, String adresse, String image, int stars) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
        this.image = image;
        this.stars = stars;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString()
    {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adresse='" + adresse + '\'' +
                ", image='" + image + '\'' +
                ", stars=" + stars +
                '}';
    }
}
