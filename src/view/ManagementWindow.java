package view;
 
import javax.swing.*;

public class ManagementWindow extends JFrame {
    public ManagementWindow() {
        setTitle("Gestion du Personnel et Véhicules");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Livreurs", new LivreurManagementPanel());
        tabbedPane.addTab("Véhicules", new VehiculeManagementPanel());
        tabbedPane.addTab("Pizzaiolos", new PizzaioloManagementPanel());

        add(tabbedPane);
    }
}