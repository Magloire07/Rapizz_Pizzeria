package salle;

import java.sql.*;
import java.util.ArrayList;

public class SalleDAO {
    private Connection connection;

    public SalleDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Salle> getSalles() {
        ArrayList<Salle> salles = new ArrayList<>();
        String query = "SELECT * FROM Salle";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Salle salle = new Salle(
                    rs.getString("nSalle"),
                    rs.getString("nomSalle"),
                    rs.getInt("nbPoste"),
                    rs.getString("indIP")
                );
                salles.add(salle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salles;
    }

    public void addSalle(Salle salle) {
        String query = "INSERT INTO Salle (nSalle, nomSalle, nbPoste, indIP) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, salle.getNSalle());
            pstmt.setString(2, salle.getNomSalle());
            pstmt.setInt(3, salle.getNbPoste());
            pstmt.setString(4, salle.getIndIP());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSalle(Salle salle) {
        String query = "UPDATE Salle SET nomSalle = ?, nbPoste = ?, indIP = ? WHERE nSalle = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, salle.getNomSalle());
            pstmt.setInt(2, salle.getNbPoste());
            pstmt.setString(3, salle.getIndIP());
            pstmt.setString(4, salle.getNSalle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSalle(String nSalle) {
        String query = "DELETE FROM Salle WHERE nSalle = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nSalle);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSalleWithCursor(String nSalle) {
        String query = "SELECT * FROM Salle";
        try (Statement stmt = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("nSalle").equals(nSalle)) {
                    rs.deleteRow();
                    System.out.println("Salle supprimée (nSalle=" + nSalle + ")");
                    return;
                }
            }
            System.out.println("Salle non trouvée (nSalle=" + nSalle + ")");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1451) {
                System.out.println("Erreur 1451 : contrainte référentielle !");
            } else {
                e.printStackTrace();
            }
        }
    }
}