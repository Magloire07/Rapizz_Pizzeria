package model;

import java.util.List;
import java.util.ArrayList;

public class ClientManager {

    public List<Client> getClients() {
        ClientDAO clientDAO = new ClientDAO();
        try {
            return clientDAO.readAllClientsAsObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateClient(Client client) {
        ClientDAO clientDAO = new ClientDAO();
        try {
            clientDAO.updateClient(
                client.getId(),
                client.getNom(),
                client.getPrenom(),
                client.getAdresse(),
                client.getSolde(),
                client.getCagnote()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}