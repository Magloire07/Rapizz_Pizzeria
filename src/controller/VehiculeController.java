package controller;

import model.Vehicule;
import model.VehiculeDAO;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VehiculeController {
    private final VehiculeDAO vehiculeDAO;

    public VehiculeController() {
        this.vehiculeDAO = new VehiculeDAO();
    }

    public void loadVehicules(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);
        List<Vehicule> vehicules = vehiculeDAO.readAllVehicules();
        for (Vehicule v : vehicules) {
            tableModel.addRow(new Object[]{
                v.getId(),
                v.getMarque(),
                v.getModele(),
                v.getType(),
                v.isDisponible()
            });
        }
    } 

    public void addVehicule(String marque, String modele, String type, boolean disponible) {
        Vehicule v = new Vehicule(marque, modele, type, disponible);
        vehiculeDAO.addVehicule(v);
    }

    public void updateVehicule(int id, String marque, String modele, String type, boolean disponible) {
        Vehicule v = new Vehicule(id, marque, modele, type, disponible);
        vehiculeDAO.updateVehicule(v);
    }

    public void deleteVehicule(int id) {
        vehiculeDAO.deleteVehicule(id);
    }

    public List<Vehicule> getAllVehicules() {
        return vehiculeDAO.readAllVehicules();
    }
}