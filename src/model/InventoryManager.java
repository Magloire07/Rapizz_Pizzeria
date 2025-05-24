package model;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {

    public List<Ingredient> getIngredients() {
        IngredientDAO ingredientDAO = new IngredientDAO();
        try {
            return ingredientDAO.readAllIngredientsAsObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void updateIngredient(Ingredient ingredient) {
        IngredientDAO ingredientDAO = new IngredientDAO();
        try {
            ingredientDAO.updateIngredient(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getPrice()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
