package com.example.myapplication.data;

public class Client {
    private int id;
    private String name;
    private String  tour;
    private int isClient;

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

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public int getIsClient() {
        return isClient;
    }

    public void setClient(int isClient) {
        this.isClient = isClient;
    }


    public String isClientToString(int isClient) {
        String word = "";
        if (isClient == 0) {
            word = "no";
        } else {
            word = "yes";
        }
        return word;

    }

    @Override
    public String toString() {
        if (tour.isEmpty()) {
            return "name: " + name + ", isClient: " + isClientToString(isClient);
        }
        return "name: " + name + ", tour: " + tour + ", isClient: " + isClientToString(isClient);
    }
}
