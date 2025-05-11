package model;

import java.util.ArrayList;

public class InventoryManager {
    
    public ArrayList<Ingredient>  getIngredients(){
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        //TODO: Récupérer les ingrédients de la base de données
        // les ingrédients sont ajoutés ici pour la démonstration
        
        ingredients.add(new Ingredient("Tomato Sauce", 50, 0.50f));
        ingredients.add(new Ingredient("Cheese", 30, 1.00f));
        ingredients.add(new Ingredient("Pepperoni", 20, 1.50f));
        ingredients.add(new Ingredient("Mushrooms", 40, 0.80f));

        return ingredients;
    }

    public void updateIngredient(Ingredient ingredient) {
        // TODO: Implémenter la mise à jour de l'ingrédient  et le solde dans la base de données  
    }
}
