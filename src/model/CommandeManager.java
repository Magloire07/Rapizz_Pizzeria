package model;

import java.util.ArrayList;

public class CommandeManager {

    public ArrayList<Commande> getRandomCmd() {
        CommandeDAO commandeDAO = new CommandeDAO();
        try {
            return commandeDAO.readAllCommandesAsObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
 