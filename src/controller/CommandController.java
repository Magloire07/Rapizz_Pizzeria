package controller;

import model.Pizza;
import view.OrderBoard;
import model.Commande;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import model.CommandeManager; 

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

    public void validateCommand() {
        readyCommands.add(currentCommand);
    }
    public void initWaitingCommand() {
        ArrayList<Commande> randomCmd = getRandomCmd();
        for (Commande cmd : randomCmd) {
            waitingCommand.add(cmd);
        }
    }
    
    public void setCurrentCommand(OrderBoard orderBoard) {
        if (!waitingCommand.isEmpty()) {
            currentCommand = waitingCommand.remove(0); // Retrieve and remove the oldest command
            orderBoard.setCurrentCommand(currentCommand.toString()); // Update the JTextArea in OrderBoard
        }
    }

    public List<Commande> getReadyCommands() {
        return readyCommands;
    }
    public ArrayList<Commande> getRandomCmd() {
        CommandeManager commandeManager = new CommandeManager();
        return commandeManager.getRandomCmd();
    }
}
