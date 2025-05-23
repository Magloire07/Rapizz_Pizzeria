-- Exported from QuickDBD: https://www.quickdatabasediagrams.com/
-- NOTE! If you have used non-SQL datatypes in your design, you will have to change these here.

-- Modify this code to update the DB schema diagram.
-- To reset the sample schema, replace everything with
-- two dots ('..' - without quotes).

SET XACT_ABORT ON

BEGIN TRANSACTION QUICKDBD

CREATE TABLE [CLIENT] (
    [numero_abonne] int  NOT NULL ,
    [nom] varchar  NOT NULL ,
    [prenom] varchar  NOT NULL ,
    [adresse] varchar  NOT NULL ,
    [solde] int  NOT NULL ,
    [nombre_commandes] int  NOT NULL ,
    CONSTRAINT [PK_CLIENT] PRIMARY KEY CLUSTERED (
        [numero_abonne] ASC
    )
)

CREATE TABLE [PIZZA] (
    [id_pizza] int  NOT NULL ,
    [nom] varchar  NOT NULL ,
    [prix_base] int  NOT NULL ,
    CONSTRAINT [PK_PIZZA] PRIMARY KEY CLUSTERED (
        [id_pizza] ASC
    )
)

CREATE TABLE [INGREDIENT] (
    [id_ingredient] int  NOT NULL ,
    [nom] varchar  NOT NULL ,
    [allergene] bool  NOT NULL ,
    CONSTRAINT [PK_INGREDIENT] PRIMARY KEY CLUSTERED (
        [id_ingredient] ASC
    )
)

CREATE TABLE [COMMANDE] (
    [num_commande] int  NOT NULL ,
    [date_commande] datetime  NOT NULL ,
    [montant] int  NOT NULL ,
    [numero_abonne] int  NOT NULL ,
    CONSTRAINT [PK_COMMANDE] PRIMARY KEY CLUSTERED (
        [num_commande] ASC
    )
)

CREATE TABLE [LIVRAISON] (
    [id_livraison] int  NOT NULL ,
    [date_livraison] datetime  NOT NULL ,
    [duree_minutes] int  NOT NULL ,
    [adresse_livraison] varchar  NOT NULL ,
    [num_commande] int  NOT NULL ,
    [id_livreur] int  NOT NULL ,
    [id_vehicule] int  NOT NULL ,
    CONSTRAINT [PK_LIVRAISON] PRIMARY KEY CLUSTERED (
        [id_livraison] ASC
    ),
    CONSTRAINT [UK_LIVRAISON_num_commande] UNIQUE (
        [num_commande]
    )
)

CREATE TABLE [LIVREUR] (
    [id_livreur] int  NOT NULL ,
    [nom] varchar  NOT NULL ,
    [prenom] varchar  NOT NULL ,
    [nombre_livraisons] int  NOT NULL ,
    [duree_livraisons] int  NOT NULL ,
    CONSTRAINT [PK_LIVREUR] PRIMARY KEY CLUSTERED (
        [id_livreur] ASC
    )
)

CREATE TABLE [VEHICULE] (
    [id_vehicule] int  NOT NULL ,
    [type] varchar  NOT NULL ,
    [immatriculation] varchar  NOT NULL ,
    [nombre_livraisons] int  NOT NULL ,
    [duree] int  NOT NULL ,
    CONSTRAINT [PK_VEHICULE] PRIMARY KEY CLUSTERED (
        [id_vehicule] ASC
    )
)

CREATE TABLE [COMPOSER] (
    [id_pizza] int  NOT NULL ,
    [id_ingredient] int  NOT NULL ,
    [(id_pizza,id_ingredient)] int  NOT NULL ,
    CONSTRAINT [PK_COMPOSER] PRIMARY KEY CLUSTERED (
        [(id_pizza,id_ingredient)] ASC
    )
)

CREATE TABLE [COMMANDER] (
    [num_commande] int  NOT NULL ,
    [id_pizza] int  NOT NULL ,
    [taille_pizza] varchar  NOT NULL ,
    [(num_commande,id_pizza)] int  NOT NULL ,
    CONSTRAINT [PK_COMMANDER] PRIMARY KEY CLUSTERED (
        [(num_commande,id_pizza)] ASC
    )
)

ALTER TABLE [COMMANDE] WITH CHECK ADD CONSTRAINT [FK_COMMANDE_numero_abonne] FOREIGN KEY([numero_abonne])
REFERENCES [CLIENT] ([numero_abonne])

ALTER TABLE [COMMANDE] CHECK CONSTRAINT [FK_COMMANDE_numero_abonne]

ALTER TABLE [LIVRAISON] WITH CHECK ADD CONSTRAINT [FK_LIVRAISON_num_commande] FOREIGN KEY([num_commande])
REFERENCES [COMMANDE] ([num_commande])

ALTER TABLE [LIVRAISON] CHECK CONSTRAINT [FK_LIVRAISON_num_commande]

ALTER TABLE [LIVRAISON] WITH CHECK ADD CONSTRAINT [FK_LIVRAISON_id_livreur] FOREIGN KEY([id_livreur])
REFERENCES [LIVREUR] ([id_livreur])

ALTER TABLE [LIVRAISON] CHECK CONSTRAINT [FK_LIVRAISON_id_livreur]

ALTER TABLE [LIVRAISON] WITH CHECK ADD CONSTRAINT [FK_LIVRAISON_id_vehicule] FOREIGN KEY([id_vehicule])
REFERENCES [VEHICULE] ([id_vehicule])

ALTER TABLE [LIVRAISON] CHECK CONSTRAINT [FK_LIVRAISON_id_vehicule]

ALTER TABLE [COMPOSER] WITH CHECK ADD CONSTRAINT [FK_COMPOSER_id_pizza] FOREIGN KEY([id_pizza])
REFERENCES [PIZZA] ([id_pizza])

ALTER TABLE [COMPOSER] CHECK CONSTRAINT [FK_COMPOSER_id_pizza]

ALTER TABLE [COMPOSER] WITH CHECK ADD CONSTRAINT [FK_COMPOSER_id_ingredient] FOREIGN KEY([id_ingredient])
REFERENCES [INGREDIENT] ([id_ingredient])

ALTER TABLE [COMPOSER] CHECK CONSTRAINT [FK_COMPOSER_id_ingredient]

ALTER TABLE [COMMANDER] WITH CHECK ADD CONSTRAINT [FK_COMMANDER_num_commande] FOREIGN KEY([num_commande])
REFERENCES [COMMANDE] ([num_commande])

ALTER TABLE [COMMANDER] CHECK CONSTRAINT [FK_COMMANDER_num_commande]

ALTER TABLE [COMMANDER] WITH CHECK ADD CONSTRAINT [FK_COMMANDER_id_pizza] FOREIGN KEY([id_pizza])
REFERENCES [PIZZA] ([id_pizza])

ALTER TABLE [COMMANDER] CHECK CONSTRAINT [FK_COMMANDER_id_pizza]

COMMIT TRANSACTION QUICKDBD