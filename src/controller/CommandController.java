package controller;

import model.Pizza;
import view.OrderBoard;
import model.Commande;

import java.util.ArrayList;
import java.util.List;
import model.CommandeManager;
import javax.swing.Timer;
 
public class CommandController {
    private List<Commande> waitingCommand;
    private List<Commande> readyCommands;
    private Commande currentCommand;

    public CommandController() {
        this.waitingCommand = new ArrayList<>();
        this.readyCommands = new ArrayList<>();
    }

    public void addPizzaToCommand(Commande cmd) {
        waitingCommand.add(cmd);
    }

    // Validate the current command and move it to readyCommands
    public void validateCommand(Commande cmd) {
        if (cmd != null) {
            readyCommands.add(cmd);
            waitingCommand.remove(cmd);
            currentCommand = null;
        }
    }

    // Overload for compatibility (if you want to use the currentCommand field)
    public void validateCommand() {
        if (currentCommand != null) {
            readyCommands.add(currentCommand);
            waitingCommand.remove(currentCommand);
            currentCommand = null;
        }
    }

    public void initWaitingCommand() {
        ArrayList<Commande> randomCmd = getRandomCmd();
        for (Commande cmd : randomCmd) {
            waitingCommand.add(cmd);
        }
    }

    // Set the current command to process (from UI/game loop)
    public void setCurrentCommand(Commande cmd) {
        this.currentCommand = cmd;
    }

    // Get the current command
    public Commande getCurrentCommand() {
        return currentCommand;
    }

    public List<Commande> getReadyCommands() {
        return readyCommands;
    }

    // Return a list of random commands (simulate orders to process)
    public ArrayList<Commande> getRandomCmd() {
        CommandeManager commandeManager = new CommandeManager();
        return commandeManager.getRandomCmd();
    }

    // Get the list of waiting commands (not yet processed)
    public List<Commande> getWaitingCommands() {
        return waitingCommand;
    }

    // Enum pour l'état d'une commande
    public enum EtatCommande {
        EN_ATTENTE,
        EN_PREPARATION,
        EN_LIVRAISON,
        LIVREE
    }

    // Ajout d'une classe interne pour suivre l'affectation du personnel (optionnel)
    public static class AffectationPersonnel {
        public String pizzaiolo;
        public String livreur;
        public AffectationPersonnel(String pizzaiolo, String livreur) {
            this.pizzaiolo = pizzaiolo;
            this.livreur = livreur;
        }
    }

    // Ajout d'une map pour suivre l'état et l'affectation des commandes
    private java.util.Map<Commande, EtatCommande> etatsCommandes = new java.util.HashMap<>();
    private java.util.Map<Commande, AffectationPersonnel> affectations = new java.util.HashMap<>();

    // Affecter un pizzaiolo à une commande
    public void assignPizzaioloToCommande(Commande cmd, String pizzaiolo) {
        if (cmd != null && waitingCommand.contains(cmd)) {
            etatsCommandes.put(cmd, EtatCommande.EN_PREPARATION);
            affectations.put(cmd, new AffectationPersonnel(pizzaiolo, null));
        }
    }

    // Affecter un livreur à une commande (à appeler après préparation)
    public void assignLivreurToCommande(Commande cmd, String livreur) {
        if (cmd != null && etatsCommandes.get(cmd) == EtatCommande.EN_PREPARATION) {
            etatsCommandes.put(cmd, EtatCommande.EN_LIVRAISON);
            AffectationPersonnel aff = affectations.get(cmd);
            if (aff != null) aff.livreur = livreur;
            else affectations.put(cmd, new AffectationPersonnel(null, livreur));
        }
    }

    // Mettre à jour l'état d'une commande (ex : après livraison)
    public void setEtatCommande(Commande cmd, EtatCommande etat) {
        if (cmd != null) {
            etatsCommandes.put(cmd, etat);
        }
    }

    // Savoir où en est une commande
    public EtatCommande getEtatCommande(Commande cmd) {
        return etatsCommandes.getOrDefault(cmd, EtatCommande.EN_ATTENTE);
    }

    // Génération automatique de commandes (à appeler depuis un timer dans OrderBoard)
    public void genererCommandeAleatoire() {
        ArrayList<Commande> randomCmd = getRandomCmd();
        for (Commande cmd : randomCmd) {
            waitingCommand.add(cmd);
            etatsCommandes.put(cmd, EtatCommande.EN_ATTENTE);
        }
    }

    public void genererCommandeAleatoireEtRafraichir() {
        try {
            model.CommandeDAO dao = new model.CommandeDAO();
            dao.genererCommandeAleatoire();
            // Recharge les commandes depuis la base
            waitingCommand.clear();
            waitingCommand.addAll(getRandomCmd());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Optionnel : méthodes pour récupérer les commandes par état
    public List<Commande> getCommandesParEtat(EtatCommande etat) {
        List<Commande> res = new ArrayList<>();
        for (Commande cmd : waitingCommand) {
            if (getEtatCommande(cmd) == etat) res.add(cmd);
        }
        return res;
    }

    // Timer pour la génération automatique de commandes
    private Timer commandeTimer;

    public void startCommandeGenerationTimer() {
        commandeTimer = new Timer(20000, e -> {
            genererCommandeAleatoire();
            // updateOrderLists(); // Assurez-vous que cette méthode est accessible ici
            // notificationModel.addElement("Nouvelle commande générée !"); // Idem
        });
        commandeTimer.start();
    }

    public void stopCommandeGenerationTimer() {
        if (commandeTimer != null) {
            commandeTimer.stop();
        }
    }
}
