package controller;

import model.Pizza;
import view.OrderBoard;
import model.Commande;

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
}
