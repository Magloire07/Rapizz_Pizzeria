package main;

import view.Accueil;

public class Main {
    public static void main(String[] args) {
        utils.DatabaseInitializer.initialize();
        Accueil accueil = new Accueil();
        accueil.setVisible(true);
    }
}
 