package com.example.myapplication.data;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class ClientData {
    private static ArrayList<Client> clients  = new ArrayList<Client>();
    ClientDB clientsDB;

    public ClientData(Context context){
        clientsDB = new ClientDB(context);
        readAll();
    }

    public Client getClient(int id){
        Client d = new Client();
        d.setId(id);
        return clientsDB.get(d);
    }

    public List<Client> findAllClient(){
        return clients;
    }

    public void addClient(String name, String tour, int isClient){
        Client client = new Client();
        client.setName(name);
        client.setTour(tour);
        client.setClient(isClient);
        clientsDB.add(client);
        readAll();
    }
    public void updateClient(int id, String name, String tour, int isClient){
        Client client = new Client();
        client.setId(id);
        client.setName(name);
        client.setTour(tour);
        client.setClient(isClient);
        clientsDB.update(client);
        readAll();
    }

    public void deleteAll(){
        clients.clear();
        clientsDB.deleteAll();
        readAll();
    }

    private void readAll(){
        List<Client> ds = clientsDB.readAll();
        clients.clear();
        for(Client client : ds){
            clients.add(client);
        }
    }
}
