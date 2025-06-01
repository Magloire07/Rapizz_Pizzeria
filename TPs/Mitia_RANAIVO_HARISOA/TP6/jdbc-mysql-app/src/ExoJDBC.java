package src;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import salle.Salle;
import salle.SalleDAO;

public class ExoJDBC extends Panel implements ActionListener {
    TextField nomDriver;
    TextField urlConnection;
    TextField nomLogin;
    TextField motPasse;
    Button boutonConnection;
    TextField requeteSQL;
    List resultatRequete;
    Button boutonExecuter;

    // 1. Ajout de la variable Connection
    private Connection connection = null;

    public ExoJDBC() {
        Panel haut;
        Panel bas;
        haut = new Panel();
        bas = new Panel();
        boutonConnection = new Button("Connection");
        boutonConnection.addActionListener(this);
        boutonExecuter = new Button("Execution");
        boutonExecuter.addActionListener(this);
        Panel p1 = new Panel();
        p1.setLayout(new GridLayout(4, 2));
        p1.add(new Label("Driver :"));
        p1.add(nomDriver = new TextField(32));
        p1.add(new Label("URL jdbc :"));
        p1.add(urlConnection = new TextField(32));
        p1.add(new Label("login :"));
        p1.add(nomLogin = new TextField(32));
        p1.add(new Label("password :"));
        p1.add(motPasse = new TextField(32));
        haut.setLayout(new BorderLayout());
        haut.add(p1, BorderLayout.NORTH);
        haut.add(boutonConnection, BorderLayout.SOUTH);
        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout());
        p2.add(new Label("requete"), BorderLayout.WEST);
        p2.add(requeteSQL = new TextField(32), BorderLayout.CENTER);
        Panel p3 = new Panel();
        p3.setLayout(new BorderLayout());
        p3.add(p2, BorderLayout.NORTH);
        p3.add(boutonExecuter, BorderLayout.SOUTH);
        bas.setLayout(new BorderLayout());
        bas.add(p3, BorderLayout.NORTH);
        bas.add(resultatRequete = new List(20));
        setLayout(new BorderLayout());
        add(haut, BorderLayout.NORTH);
        add(bas, BorderLayout.CENTER);
        nomDriver.setText("com.mysql.cj.jdbc.Driver");
        urlConnection.setText("jdbc:mysql://localhost:3306/Parc");
        nomLogin.setText("root");
        motPasse.setText("316MIQq~+X"); // Mets ton mot de passe si besoin
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == boutonConnection) {
            // 2. Connexion à la base
            try {
                String driver = nomDriver.getText().trim();
                String url = urlConnection.getText().trim();
                String user = nomLogin.getText().trim();
                String pass = motPasse.getText().trim();
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, pass);
                resultatRequete.add("Connexion réussie !");
            } catch (Exception e) {
                resultatRequete.add("Erreur connexion : " + e.getMessage());
            }
        } else if (evt.getSource() == boutonExecuter) {
            // 3. Exécution de la requête SQL
            if (connection == null) {
                resultatRequete.add("Pas de connexion à la base !");
                return;
            }
            String req = requeteSQL.getText().trim();
            try (Statement stmt = connection.createStatement()) {
                boolean hasResultSet = stmt.execute(req);
                resultatRequete.removeAll();
                if (hasResultSet) {
                    ResultSet rs = stmt.getResultSet();
                    ResultSetMetaData meta = rs.getMetaData();
                    int colCount = meta.getColumnCount();
                    // Affiche les noms de colonnes
                    StringBuilder header = new StringBuilder();
                    for (int i = 1; i <= colCount; i++) {
                        header.append(meta.getColumnName(i)).append("\t");
                    }
                    resultatRequete.add(header.toString());
                    // Affiche les lignes
                    while (rs.next()) {
                        StringBuilder row = new StringBuilder();
                        for (int i = 1; i <= colCount; i++) {
                            row.append(rs.getString(i)).append("\t");
                        }
                        resultatRequete.add(row.toString());
                    }
                } else {
                    int count = stmt.getUpdateCount();
                    resultatRequete.add(count + " ligne(s) affectée(s).");
                }
            } catch (SQLException e) {
                resultatRequete.add("Erreur requête : " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Frame f = new Frame("ExoJDBC - Consultation Base Parc Informatique");
        ExoJDBC panel = new ExoJDBC();
        f.add(panel);
        f.setSize(800, 600);
        f.setVisible(true);
        f.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }
}