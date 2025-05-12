package controller;

import model.OrderBoardManager;
import view.OrderBoard;


public class OrderBoardController {
    private OrderBoard ob;
    public void initOrderBoard( OrderBoard wind)
    {   this.ob=wind;
        OrderBoardManager orderBoardManager = new OrderBoardManager();
        wind.setSolde(orderBoardManager.getSolde());
        wind.setListPizza(orderBoardManager.getListPizza());
    }
    
}
