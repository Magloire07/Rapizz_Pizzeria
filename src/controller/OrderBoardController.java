package controller;

import model.OrderBoardManager;
import view.OrderBoard;

public class OrderBoardController {
 
    public void initOrderBoard( OrderBoard wind)
    {
        OrderBoardManager orderBoardManager = new OrderBoardManager();
        wind.setSolde(orderBoardManager.getSolde());
        wind.setListPizza(orderBoardManager.getListPizza());
    }
}
