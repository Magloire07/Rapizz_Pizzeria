/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import controller.InventoryController;
import model.Pizza;
import model.Commande;
import controller.CommandController;
import controller.MenuController; 
import controller.FicheLivraisonControlle; 
import controller.DashboardController; 
import controller.PizzaioloController;
import controller.LivreurController;
import controller.VehiculeController;
import model.Pizzaiolo;
import model.Livreur;
import model.Vehicule;
import model.PersonnelManager;


/**
 *
 * @author kokoudevops
 */
public class OrderBoard extends javax.swing.JFrame {

    private InventoryController inventoryController;
    private CommandController commandeController;
    private MenuController menuController; 
    private FicheLivraisonControlle flivs; 
    private DashboardController dash ; 

    // Add these fields to store the selected pizza and size
    private Pizza selectedPizza;
    private String selectedSize;

    // Add these fields to store the current order to process and simulate wallet
    private Commande currentCommande;
    private double solde = 200.0; // Initial wallet

    // Ajouts en haut de la classe
    private DefaultListModel<String> notificationModel = new DefaultListModel<>();
    private JList<String> notificationList = new JList<>(notificationModel);
    private JPanel pizzaioloProgressPanel = new JPanel(new GridLayout(0, 1));
    private JPanel livreurProgressPanel = new JPanel(new GridLayout(0, 1));
    private Timer commandeTimer;
    private int commandesLivrees = 0;
    private int satisfaction = 100; // Pourcentage fictif
    private JLabel statsLabel = new JLabel("Commandes livrées: 0 | Satisfaction: 100%");

    // Simulations de listes de personnel/véhicules
    private List<String> pizzaiolos = new ArrayList<>();
    private List<String> livreurs = new ArrayList<>();
    private List<String> vehicules = new ArrayList<>();
    private JPanel synthesePanel = new JPanel(new GridLayout(0, 1));

    // New field to track command arrival times
    private java.util.Map<Commande, Long> commandeArriveeTime = new java.util.HashMap<>();

    /**
     * Creates new form OrderBoard
     */
    public OrderBoard() {
        utils.DatabaseInitializer.initialize();
        initComponents();
        inventoryController = new InventoryController(this);
        commandeController = new CommandController();
        menuController = new MenuController();
        flivs = new FicheLivraisonControlle();
        dash = new DashboardController();

        // Simplified pizzaiolo initialization
        PersonnelManager personnel = PersonnelManager.getInstance();
        personnel.chargerPizzaiolosDepuisDB();
        if (personnel.getPizzaiolos().isEmpty()) {
            new model.PizzaioloDAO().addPizzaiolo(new model.Pizzaiolo("Mario", "Rossi"));
            personnel.chargerPizzaiolosDepuisDB();
            notificationModel.addElement("Un pizzaiolo a été embauché automatiquement.");
        }

        // Load livreurs from DB into simulation
        personnel.chargerLivreursDepuisDB();

        commandeController.initWaitingCommand();
        updateOrderLists();
        showRandomCommande();
        setSolde(solde);

        // Notifications
        Droite.add(new JLabel("Notifications:"));
        JScrollPane notificationScrollPane = new JScrollPane(notificationList);
        notificationScrollPane.setPreferredSize(new Dimension(250, 250));
        Droite.add(notificationScrollPane);

        // Remove the finished commands scroll pane and its addition to Droite
        // JScrollPane finishedCmdScrollPane = new JScrollPane(listCmdPrete);
        // finishedCmdScrollPane.setPreferredSize(new Dimension(200, 150));
        // Droite.add(finishedCmdScrollPane);

        // Progress panels moved to Gauche
        Gauche.add(new JLabel("Préparation:"));
        pizzaioloProgressPanel.setPreferredSize(new Dimension(250, 40));
        Gauche.add(pizzaioloProgressPanel);

        Gauche.add(new JLabel("Livraison:"));
        livreurProgressPanel.setPreferredSize(new Dimension(250, 40));
        Gauche.add(livreurProgressPanel);

        

        updateSynthesePanel();

        // Commande timer
        commandeTimer = new Timer(20000, e -> {
            commandeController.genererCommandeAleatoireEtRafraichir();
            updateOrderLists();
            showRandomCommande();
            notificationModel.addElement("Nouvelle commande reçue !");
        });
        commandeTimer.start();

        retardTimer.start();

        initPizzaPanel();

        setPreferredSize(new Dimension(1256, 1000));
        setMinimumSize(new Dimension(1000, 1000));
        setResizable(true);
        pack();
        setLocationRelativeTo(null); // Center on screen

        // Add this line to enable multi-line rendering for prepared commands
        listCmdPrete.setCellRenderer(new MultiLineCellRenderer());
    }

    /**
     * Updates both the waiting and ready command lists in the UI.
     */
    public void updateOrderLists() {
        // Waiting commands
        List<Commande> waiting = commandeController.getWaitingCommands();
        DefaultListModel<String> waitingModel = new DefaultListModel<>();
        for (Commande cmd : waiting) {
            waitingModel.addElement(cmd.getNomClient() + " - N°: " + String.format("%04d", cmd.getNumeroCommande()));
            commandeArriveeTime.putIfAbsent(cmd, System.currentTimeMillis());
        }
        lstCmdAttent.setModel(waitingModel);

        // Ready commands (prepared)
        List<Commande> ready = commandeController.getReadyCommands();
        DefaultListModel<String> readyModel = new DefaultListModel<>();
        for (Commande cmd : ready) {
            // Compose a bill summary
            String bill = "Facture\n"
                + "Client : " + cmd.getNomClient() + "\n"
                + "Pizza  : " + cmd.getNomPizza() + "\n"
                + "Taille : " + (cmd.getTaille() != null ? cmd.getTaille() : "Non spécifiée") + "\n"
                + "Prix   : " + getPrixCommande(cmd) + "€\n"
                + "Commande N°: " + String.format("%04d", cmd.getNumeroCommande()) + "\n"
                + "--------------------\n";
            readyModel.addElement(bill);
        }
        listCmdPrete.setModel(readyModel);
    }

    /**
     * Shows the oldest waiting order to the user (FIFO).
     */
    public void showRandomCommande() {
        List<Commande> waiting = commandeController.getWaitingCommands();
        if (waiting.isEmpty()) {
            // Refill the waiting commands if empty
            commandeController.initWaitingCommand();
            waiting = commandeController.getWaitingCommands();
            updateOrderLists();
        }
        if (!waiting.isEmpty()) {
            // FIFO: get the first command in the list
            currentCommande = waiting.get(0);
            setCurrentCommand(currentCommande.toString());
        } else {
            currentCommande = null;
            setCurrentCommand("Aucune commande en attente.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Gauche = new javax.swing.JPanel();
        commande = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        currentCommand = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstCmdAttent = new javax.swing.JList<>();
        Centre = new javax.swing.JPanel();
        CNord = new javax.swing.JPanel();
        soldeText = new javax.swing.JLabel();
        CCentreListPizza = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        photoPiz = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        detailPiz = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        photoPiz1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        detailPiz1 = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        photoPiz2 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        detailPiz2 = new javax.swing.JTextArea();
        CSud = new javax.swing.JPanel();
        SNord = new javax.swing.JPanel();
        btSizeS = new javax.swing.JButton();
        btSizeM = new javax.swing.JButton();
        btSizeL = new javax.swing.JButton();
        SSud = new javax.swing.JPanel();
        btValider = new javax.swing.JButton();
        Droite = new javax.swing.JPanel();
        btInventaire = new javax.swing.JButton();
        btDashB = new javax.swing.JButton();
        btMenu = new javax.swing.JButton();
        btFiche = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listCmdPrete = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1256, 650));
        setResizable(false);

        Gauche.setPreferredSize(new java.awt.Dimension(280, 1000));

        commande.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        commande.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        commande.setText("COMMANDES");
        commande.setPreferredSize(new java.awt.Dimension(120, 40));
        Gauche.add(commande);

        jScrollPane4.setFocusable(false);
        jScrollPane4.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jScrollPane4.setPreferredSize(new java.awt.Dimension(250, 120));

        currentCommand.setColumns(20);
        currentCommand.setRows(5);
        currentCommand.setAutoscrolls(false);
        currentCommand.setFocusable(false);
        currentCommand.setMinimumSize(new java.awt.Dimension(10, 10));
        currentCommand.setPreferredSize(new java.awt.Dimension(80, 80));
        jScrollPane4.setViewportView(currentCommand);

        Gauche.add(jScrollPane4);

        jScrollPane2.setPreferredSize(new java.awt.Dimension(250, 450));

        jScrollPane2.setViewportView(lstCmdAttent);

        Gauche.add(jScrollPane2);

        // Add to Gauche after the command lists:
        Gauche.add(new JLabel("Préparation:"));
        pizzaioloProgressPanel.setPreferredSize(new Dimension(250, 40));
        Gauche.add(pizzaioloProgressPanel);

        Gauche.add(new JLabel("Livraison:"));
        livreurProgressPanel.setPreferredSize(new Dimension(250, 40));
        Gauche.add(livreurProgressPanel);

        getContentPane().add(Gauche, java.awt.BorderLayout.WEST);

        Centre.setLayout(new java.awt.BorderLayout(0, 30));

        CNord.setPreferredSize(new java.awt.Dimension(613, 50));

        soldeText.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        soldeText.setText("Solde Actuel: 10 000€");

        javax.swing.GroupLayout CNordLayout = new javax.swing.GroupLayout(CNord);
        CNord.setLayout(CNordLayout);
        CNordLayout.setHorizontalGroup(
            CNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
            .addGroup(CNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CNordLayout.createSequentialGroup()
                    .addContainerGap(189, Short.MAX_VALUE)
                    .addComponent(soldeText)
                    .addContainerGap(189, Short.MAX_VALUE)))
        );
        CNordLayout.setVerticalGroup(
            CNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
            .addGroup(CNordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CNordLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(soldeText, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        Centre.add(CNord, java.awt.BorderLayout.NORTH);

        CCentreListPizza.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        photoPiz.setForeground(new java.awt.Color(255, 255, 255));
        photoPiz.setIcon(new javax.swing.ImageIcon("/home/kokoudevops/Documents/BD/Rapizz_Pizzeria/src/images/Margherita.jpeg")); // NOI18N
        photoPiz.setBorder(null);
        photoPiz.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        photoPiz.setPreferredSize(new java.awt.Dimension(290, 174));
        photoPiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                photoPizActionPerformed(evt);
            }
        });
        jPanel1.add(photoPiz);

        detailPiz.setColumns(20);
        detailPiz.setRows(5);
        detailPiz.setText("Margherita\n15€");
        detailPiz.setAutoscrolls(false);
        detailPiz.setFocusable(false);
        jScrollPane1.setViewportView(detailPiz);

        jPanel1.add(jScrollPane1);

        CCentreListPizza.add(jPanel1);

        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        photoPiz1.setForeground(new java.awt.Color(255, 255, 255));
        photoPiz1.setIcon(new javax.swing.ImageIcon("/home/kokoudevops/Documents/BD/Rapizz_Pizzeria/src/images/Margherita.jpeg")); // NOI18N
        photoPiz1.setBorder(null);
        photoPiz1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        photoPiz1.setPreferredSize(new java.awt.Dimension(290, 174));
        photoPiz1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                photoPiz1ActionPerformed(evt);
            }
        });
        jPanel4.add(photoPiz1);

        detailPiz1.setColumns(20);
        detailPiz1.setRows(5);
        detailPiz1.setText("Margherita\n15€");
        detailPiz1.setAutoscrolls(false);
        detailPiz1.setFocusable(false);
        jScrollPane5.setViewportView(detailPiz1);

        jPanel4.add(jScrollPane5);

        CCentreListPizza.add(jPanel4);

        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        photoPiz2.setForeground(new java.awt.Color(255, 255, 255));
        photoPiz2.setIcon(new javax.swing.ImageIcon("/home/kokoudevops/Documents/BD/Rapizz_Pizzeria/src/images/Margherita.jpeg")); // NOI18N
        photoPiz2.setBorder(null);
        photoPiz2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        photoPiz2.setPreferredSize(new java.awt.Dimension(290, 174));
        photoPiz2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                photoPiz2ActionPerformed(evt);
            }
        });
        jPanel5.add(photoPiz2);

        detailPiz2.setColumns(20);
        detailPiz2.setRows(5);
        detailPiz2.setText("Margherita\n15€");
        detailPiz2.setAutoscrolls(false);
        detailPiz2.setFocusable(false);
        jScrollPane6.setViewportView(detailPiz2);

        jPanel5.add(jScrollPane6);

        CCentreListPizza.add(jPanel5);

        Centre.add(CCentreListPizza, java.awt.BorderLayout.CENTER);

        CSud.setLayout(new java.awt.BorderLayout());

        SNord.setLayout(new java.awt.GridLayout(1, 3));

        btSizeS.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        btSizeS.setText("S");
        btSizeS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSizeSActionPerformed(evt);
            }
        });
        SNord.add(btSizeS);

        btSizeM.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        btSizeM.setText("M");
        btSizeM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSizeMActionPerformed(evt);
            }
        });
        SNord.add(btSizeM);

        btSizeL.setFont(new java.awt.Font("Liberation Sans", 0, 24)); // NOI18N
        btSizeL.setText("L");
        btSizeL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSizeLActionPerformed(evt);
            }
        });
        SNord.add(btSizeL);

        CSud.add(SNord, java.awt.BorderLayout.NORTH);

        btValider.setBackground(new java.awt.Color(51, 153, 0));
        btValider.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        btValider.setForeground(new java.awt.Color(255, 255, 255));
        btValider.setText("VALIDER");
        btValider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btValiderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SSudLayout = new javax.swing.GroupLayout(SSud);
        SSud.setLayout(SSudLayout);
        SSudLayout.setHorizontalGroup(
            SSudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
            .addGroup(SSudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SSudLayout.createSequentialGroup()
                    .addContainerGap(239, Short.MAX_VALUE)
                    .addComponent(btValider)
                    .addContainerGap(239, Short.MAX_VALUE)))
        );
        SSudLayout.setVerticalGroup(
            SSudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(SSudLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SSudLayout.createSequentialGroup()
                    .addContainerGap(36, Short.MAX_VALUE)
                    .addComponent(btValider, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)))
        );

        CSud.add(SSud, java.awt.BorderLayout.SOUTH);

        Centre.add(CSud, java.awt.BorderLayout.SOUTH);

        getContentPane().add(Centre, java.awt.BorderLayout.CENTER);

        // Make the right panel (Droite) scrollable to ensure all content is visible
        Droite = new javax.swing.JPanel();
        Droite.setMinimumSize(new java.awt.Dimension(34, 34));
        Droite.setPreferredSize(new java.awt.Dimension(320, 800)); // Slightly wider for more space
        Droite.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        btInventaire.setText("INVENTAIRE");
        btInventaire.setMargin(new java.awt.Insets(2, 32, 2, 32));
        btInventaire.setMinimumSize(new java.awt.Dimension(24, 24));
        btInventaire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInventaireActionPerformed(evt);
            }
        });
        Droite.add(btInventaire);

        btDashB.setText("DASHBOARD");
        btDashB.setMargin(new java.awt.Insets(2, 30, 2, 30));
        btDashB.setMinimumSize(new java.awt.Dimension(24, 24));
        btDashB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDashBActionPerformed(evt);
            }
        });
        Droite.add(btDashB);

        btMenu.setLabel("MENU");
        btMenu.setMargin(new java.awt.Insets(2, 50, 2, 50));
        btMenu.setMaximumSize(new java.awt.Dimension(155, 24));
        btMenu.setMinimumSize(new java.awt.Dimension(24, 24));
        btMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btMenuActionPerformed(evt);
            }
        });
        Droite.add(btMenu);

        btFiche.setText("Fiche livraison");
        btFiche.setMargin(new java.awt.Insets(2, 24, 2, 24));
        btFiche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFicheActionPerformed(evt);
            }
        });
        Droite.add(btFiche);

        // Gestion buttons
        JButton gestionButton = new JButton("Gestion du Personnel & Véhicules");
        gestionButton.addActionListener(e -> new ManagementWindow(this).setVisible(true));
        Droite.add(gestionButton);

        JButton manageStockButton = new JButton("Réapprovisionner le stock");
        manageStockButton.addActionListener(e -> inventoryController.showInventoryWindow());
        Droite.add(manageStockButton);

        // Remove the finished commands JList from Droite in the initComponents method as well
        jScrollPane3.setPreferredSize(new java.awt.Dimension(250, 410));

        listCmdPrete.setPreferredSize(new java.awt.Dimension(200, 370));
        jScrollPane3.setViewportView(listCmdPrete);
        Droite.add(jScrollPane3);

        // Instead of adding Droite directly, wrap it in a JScrollPane:
        JScrollPane droiteScrollPane = new JScrollPane(Droite);
        droiteScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        droiteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        droiteScrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scrolling

        getContentPane().add(droiteScrollPane, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btSizeSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSizeSActionPerformed
        selectedSize = "S";
        JOptionPane.showMessageDialog(this, "Taille S sélectionnée !");
    }//GEN-LAST:event_btSizeSActionPerformed

    private void btSizeMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSizeMActionPerformed
        selectedSize = "M";
        JOptionPane.showMessageDialog(this, "Taille M sélectionnée !");
    }//GEN-LAST:event_btSizeMActionPerformed

    private void btSizeLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSizeLActionPerformed
        selectedSize = "L";
        JOptionPane.showMessageDialog(this, "Taille L sélectionnée !");
    }//GEN-LAST:event_btSizeLActionPerformed

    private void photoPizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_photoPizActionPerformed
        // Select the first pizza (example: Margherita)
        selectedPizza = new Pizza(1, "Margherita", 15.0, "Margherita.jpeg", "S");
        JOptionPane.showMessageDialog(this, "Pizza Margherita sélectionnée !");
    }//GEN-LAST:event_photoPizActionPerformed

    private void photoPiz1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_photoPiz1ActionPerformed
        // Select the second pizza (example: Regina)
        selectedPizza = new Pizza(2, "Regina", 18.0, "Regina.jpeg", "S");
        JOptionPane.showMessageDialog(this, "Pizza Regina sélectionnée !");
    }//GEN-LAST:event_photoPiz1ActionPerformed

    private void photoPiz2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_photoPiz2ActionPerformed
        // Select the third pizza (example: 4 Fromages)
        selectedPizza = new Pizza(3, "4 Fromages", 20.0, "4Fromages.jpeg", "S");
        JOptionPane.showMessageDialog(this, "Pizza 4 Fromages sélectionnée !");
    }//GEN-LAST:event_photoPiz2ActionPerformed

    private void btInventaireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInventaireActionPerformed
        inventoryController.showInventoryWindow();
    }//GEN-LAST:event_btInventaireActionPerformed

    private void btMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btMenuActionPerformed
        // TODO add your handling code here:
        menuController.showMenuWindow();       
    }//GEN-LAST:event_btMenuActionPerformed

    private void btDashBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDashBActionPerformed
        // TODO add your handling code here:
        dash.showDash(); 
    }//GEN-LAST:event_btDashBActionPerformed

    private void btFicheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFicheActionPerformed
        // TODO add your handling code here:
        flivs.showFicheLivraison(); 
    }//GEN-LAST:event_btFicheActionPerformed
    public void setSolde(double solde) {
        soldeText.setText("Solde Actuel: " + solde + "€");
    }

    public void setCurrentCommand(String commande) {
        currentCommand.setText(commande);
    }

    /**
     * Handles the validation of the current command and continues the game loop.
     */
    private void btValiderActionPerformed(java.awt.event.ActionEvent evt) {
        if (currentCommande == null) {
            JOptionPane.showMessageDialog(this, "Aucune commande à traiter !");
            return;
        }
        if (selectedPizza == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une pizza !");
            return;
        }
        if (selectedSize == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une taille !");
            return;
        }
        // Vérification du stock
        if (!inventoryController.hasEnoughIngredients(selectedPizza)) {
            notificationModel.addElement("Stock insuffisant pour " + selectedPizza.getName());
            return;
        }
        inventoryController.deductIngredients(selectedPizza);

        // 1. Valider la commande (déplace dans la liste des prêtes)
        commandeController.validateCommand(currentCommande);

        PersonnelManager personnel = PersonnelManager.getInstance();
        if (personnel.getPizzaioloDisponible() != null) {
            lancerPreparation(currentCommande);
            notificationModel.addElement("Préparation lancée pour la commande " + currentCommande.getNumeroCommande());
        } else {
            notificationModel.addElement("Aucun pizzaiolo disponible, la commande reste en attente.");
            // La commande reste dans la file d'attente, rien à faire de plus
        }
        updateOrderLists();
        showRandomCommande();
    }//GEN-LAST:event_btValiderActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CCentreListPizza;
    private javax.swing.JPanel CNord;
    private javax.swing.JPanel CSud;
    private javax.swing.JPanel Centre;
    private javax.swing.JPanel Droite;
    private javax.swing.JPanel Gauche;
    private javax.swing.JPanel SNord;
    private javax.swing.JPanel SSud;
    private javax.swing.JButton btDashB;
    private javax.swing.JButton btFiche;
    private javax.swing.JButton btInventaire;
    private javax.swing.JButton btMenu;
    private javax.swing.JButton btSizeL;
    private javax.swing.JButton btSizeM;
    private javax.swing.JButton btSizeS;
    private javax.swing.JButton btValider;
    private javax.swing.JLabel commande;
    private javax.swing.JTextArea currentCommand;
    private javax.swing.JTextArea detailPiz;
    private javax.swing.JTextArea detailPiz1;
    private javax.swing.JTextArea detailPiz2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JList<String> listCmdPrete;
    private javax.swing.JList<String> lstCmdAttent;
    private javax.swing.JButton photoPiz;
    private javax.swing.JButton photoPiz1;
    private javax.swing.JButton photoPiz2;
    private javax.swing.JLabel soldeText;
    // End of variables declaration//GEN-END:variables

    public void updateSynthesePanel() {
        synthesePanel.removeAll();
        PersonnelManager personnel = PersonnelManager.getInstance();
        for (PersonnelManager.PizzaioloState p : personnel.getPizzaiolos()) {
            String etat = p.available ? "Disponible" : "Occupé";
            synthesePanel.add(new JLabel("Pizzaiolo: " + p.name + " | " + etat));
        }
        for (PersonnelManager.LivreurState l : personnel.getLivreurs()) {
            String etat = l.available ? "Disponible" : "Occupé";
            synthesePanel.add(new JLabel("Livreur: " + l.name + " | " + etat));
        }
        synthesePanel.revalidate();
        synthesePanel.repaint();
    }
    
    private void lancerPreparation(Commande commande) {
        PersonnelManager personnel = PersonnelManager.getInstance();
        PersonnelManager.PizzaioloState pizzaiolo = personnel.getPizzaioloDisponible();
        if (pizzaiolo == null) {
            notificationModel.addElement("Aucun pizzaiolo disponible !");
            return;
        }
        personnel.assignerCommandePizzaiolo(pizzaiolo, commande);

        JProgressBar prepBar = new JProgressBar(0, 100);
        prepBar.setString(pizzaiolo.name + " prépare " + commande.getNomPizza());
        prepBar.setStringPainted(true);
        pizzaioloProgressPanel.add(prepBar);
        pizzaioloProgressPanel.revalidate();
        pizzaioloProgressPanel.repaint();

        // Préparation en thread séparé
        new Thread(() -> {
            for (int i = 0; i <= 100; i += 10) {
                try { Thread.sleep(150); } catch (InterruptedException ignored) {}
                prepBar.setValue(i);
            }
            // Fin de préparation
            SwingUtilities.invokeLater(() -> {
                pizzaioloProgressPanel.remove(prepBar);
                pizzaioloProgressPanel.revalidate();
                pizzaioloProgressPanel.repaint();
                notificationModel.addElement("Pizza prête par " + pizzaiolo.name + " !");
                personnel.libererPizzaiolo(pizzaiolo); // Libération
                updateSynthesePanel();
                assignerLivreur(commande); // Enchaîne sur la livraison
            });
        }).start();
    }

    private void assignerLivreur(Commande commande) {
        PersonnelManager personnel = PersonnelManager.getInstance();
        PersonnelManager.LivreurState livreur = personnel.getLivreurDisponible();
        Vehicule vehicule = getVehiculeDisponible(); // New method to get a free vehicule

        if (livreur == null) {
            notificationModel.addElement("Aucun livreur disponible !");
            return;
        }
        if (vehicule == null) {
            notificationModel.addElement("Aucun véhicule disponible !");
            return;
        }

        personnel.assignerCommandeLivreur(livreur, commande);
        setVehiculeOccupe(vehicule, true); // Mark vehicule as occupied

        JProgressBar livBar = new JProgressBar(0, 100);
        livBar.setString(livreur.name + " livre " + commande.getNomPizza() + " avec " + vehicule.getMarque() + " " + vehicule.getModele());
        livBar.setStringPainted(true);
        livreurProgressPanel.add(livBar);
        livreurProgressPanel.revalidate();
        livreurProgressPanel.repaint();

        new Thread(() -> {
            for (int i = 0; i <= 100; i += 10) {
                try { Thread.sleep(120); } catch (InterruptedException ignored) {}
                livBar.setValue(i);
            }
            SwingUtilities.invokeLater(() -> {
                livreurProgressPanel.remove(livBar);
                livreurProgressPanel.revalidate();
                livreurProgressPanel.repaint();
                notificationModel.addElement("Commande livrée par " + livreur.name + " avec " + vehicule.getMarque() + " " + vehicule.getModele() + " !");
                personnel.libererLivreur(livreur);
                setVehiculeOccupe(vehicule, false); // Free the vehicule

                // Ajout du prix de la commande au portefeuille
                double prixCommande = getPrixCommande(commande);
                solde += prixCommande;
                setSolde(solde);
                notificationModel.addElement("+" + prixCommande + "€ ajoutés au portefeuille (livraison).");
                updateSynthesePanel();
            });
        }).start();
    }

    // Helper to get a free vehicule
    private Vehicule getVehiculeDisponible() {
        for (Vehicule v : new VehiculeController().getAllVehicules()) {
            if (v.isDisponible()) return v;
        }
        return null;
    }

    // Helper to set vehicule status
    private void setVehiculeOccupe(Vehicule vehicule, boolean occupe) {
        vehicule.setDisponible(!occupe);
        new VehiculeController().updateVehicule(
            vehicule.getId(),
            vehicule.getMarque(),
            vehicule.getModele(),
            vehicule.getType(),
            vehicule.isDisponible()
        );
    }

    private Timer retardTimer = new Timer(5000, e -> checkRetards());

    private void checkRetards() {
        long now = System.currentTimeMillis();
        long seuil = 30000; // 30 secondes
        for (Commande cmd : commandeController.getWaitingCommands()) {
            Long arrivee = commandeArriveeTime.get(cmd);
            if (arrivee != null && now - arrivee > seuil) {
                notificationModel.addElement("Commande " + cmd.getNumeroCommande() + " en retard !");
            }
        }
    }

    public void addNotification(String message) {
        notificationModel.addElement(message);
    }

    // Ajoute cette méthode utilitaire dans OrderBoard
    private double getPrixCommande(Commande commande) {
        // À adapter selon ta logique métier
        // Exemple : prix fixe ou récupération depuis la pizza
        if (commande.getNomPizza().equals("Margherita")) return 8.0;
        if (commande.getNomPizza().equals("4 Fromages")) return 10.0;
        if (commande.getNomPizza().equals("Diavola")) return 11.0;
        if (commande.getNomPizza().equals("Parma")) return 11.0;
        return 9.0; // prix par défaut
    }

    public double getSolde() {
        return solde;
    }

    public void resetGame() {
        // Clear Commande table in DB
        try {
            model.CommandeDAO.clearAllCommandes();
        } catch (Exception e) {
            e.printStackTrace();
            addNotification("Erreur lors de la réinitialisation des commandes.");
        }

        // Reset wallet
        solde = 200.0;
        setSolde(solde);

        // Clear notifications
        notificationModel.clear();

        // Clear progress panels
        pizzaioloProgressPanel.removeAll();
        livreurProgressPanel.removeAll();
        pizzaioloProgressPanel.revalidate();
        livreurProgressPanel.revalidate();
        pizzaioloProgressPanel.repaint();
        livreurProgressPanel.repaint();

        // Reset personnel/vehicles state
        PersonnelManager.getInstance().resetAll();

        // Reset commands (now empty)
        commandeController.initWaitingCommand();
        updateOrderLists();
        showRandomCommande();

        // Reset pizza/size selection
        selectedPizza = null;
        selectedSize = null;

        // Reset synthese panel
        updateSynthesePanel();
    }

    // Simplified pizza panel initialization
    private void initPizzaPanel() {
        CCentreListPizza.removeAll();
        CCentreListPizza.setLayout(new java.awt.GridLayout(4, 1, 0, 5));
        addPizzaButton("Margherita", 8.0, "S", "Margherita.jpeg");
        addPizzaButton("4 Fromages", 10.0, "S", "4Fromages.jpg");
        addPizzaButton("Diavola", 11.0, "S", "diavola.jpeg");
        addPizzaButton("Parma", 11.0, "S", "parma.jpeg");
        CCentreListPizza.revalidate();
        CCentreListPizza.repaint();
    }

    // Simplified pizza button creation
    private void addPizzaButton(String name, double price, String size, String imageFile) {
        JPanel pizzaPanel = new JPanel(new java.awt.GridLayout(1, 2));
        JButton pizzaButton = new JButton();
        pizzaButton.setIcon(new javax.swing.ImageIcon("src/images/" + imageFile));
        pizzaButton.setBorder(null);
        pizzaButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pizzaButton.addActionListener(e -> {
            selectedPizza = new Pizza(0, name, price, size, imageFile);
            JOptionPane.showMessageDialog(this, "Pizza " + name + " sélectionnée !");
        });
        JTextArea pizzaDetails = new JTextArea(name + "\n" + price + "€");
        pizzaDetails.setFocusable(false);
        pizzaDetails.setAutoscrolls(false);
        pizzaPanel.add(pizzaButton);
        pizzaPanel.add(new JScrollPane(pizzaDetails));
        CCentreListPizza.add(pizzaPanel);
    }

    // Add this inside your OrderBoard class
    private static class MultiLineCellRenderer extends JTextArea implements ListCellRenderer<String> {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                     boolean isSelected, boolean cellHasFocus) {
            setText(value);
            setFont(list.getFont());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }
}
