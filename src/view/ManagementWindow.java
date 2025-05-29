package view;
 
import javax.swing.*;

public class ManagementWindow extends JFrame {
    private final OrderBoard orderBoard;

    public ManagementWindow(OrderBoard orderBoard) {
        this.orderBoard = orderBoard;
        setTitle("Gestion du Personnel et Véhicules");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Livreurs", new LivreurManagementPanel());
        tabbedPane.addTab("Véhicules", new VehiculeManagementPanel(orderBoard));
        PizzaioloManagementPanel pizzaioloPanel = new PizzaioloManagementPanel(orderBoard);
        tabbedPane.addTab("Pizzaiolos", pizzaioloPanel);

        add(tabbedPane);
    }
}