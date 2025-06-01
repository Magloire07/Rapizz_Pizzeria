// Source de l’interface graphique
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class JDBCTest extends Panel
implements ActionListener
    {
    TextField nomDriver;
    TextField urlConnection;
    TextField nomLogin;
    TextField motPasse;
    Button boutonConnection;
    TextField requeteSQL;
    List resultatRequete;
    Button boutonExecuter;
    public JDBCTest()
        {
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
        }
    public void actionPerformed(ActionEvent evt)
        {
            //System.out.prinln("Not implemented !!");
            resultatRequete.add("au boulot les gars !!!");
        }
    public static void main(String[] arg)
        {
        JDBCTest test;
        Frame f = new Frame();
        f.setSize(500, 400);
        test = new JDBCTest( );
        f.add(test, BorderLayout.CENTER);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        } );
        f.setVisible(true);
        }
    }