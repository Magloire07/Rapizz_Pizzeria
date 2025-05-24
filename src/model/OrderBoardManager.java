package model;

import java.util.ArrayList;
import java.util.List;

public class OrderBoardManager {

    public float getSolde() {
        ClientDAO clientDAO = new ClientDAO();
        try {
            List<Client> clients = clientDAO.readAllClientsAsObjects();
            if (!clients.isEmpty()) {
                return (float) clients.get(0).getSolde();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    public ArrayList<Pizza> getListPizza() {
        PIzzaDAO pizzaDAO = new PIzzaDAO();
        try {
            return pizzaDAO.readAllPizzasAsObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
 